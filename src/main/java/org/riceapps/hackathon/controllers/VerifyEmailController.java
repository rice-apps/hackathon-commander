package org.riceapps.hackathon.controllers;

import static lightning.enums.HTTPMethod.GET;
import static lightning.enums.HTTPMethod.POST;
import static lightning.server.Context.auth;
import static lightning.server.Context.badRequestIf;
import static lightning.server.Context.config;
import static lightning.server.Context.db;
import static lightning.server.Context.illegalStateIf;
import static lightning.server.Context.isPOST;
import static lightning.server.Context.mail;
import static lightning.server.Context.notFoundIf;
import static lightning.server.Context.renderToString;
import static lightning.server.Context.request;
import static lightning.server.Context.routeParam;
import static lightning.server.Context.session;
import static lightning.server.Context.tokens;
import static lightning.server.Context.url;
import static lightning.server.Context.user;
import static lightning.server.Context.users;

import java.sql.ResultSet;
import java.util.Map;

import lightning.ann.RequireAuth;
import lightning.ann.RequireXsrfToken;
import lightning.ann.Route;
import lightning.ann.Template;
import lightning.crypt.TokenSets.TokenSet;
import lightning.db.NamedPreparedStatement;
import lightning.mail.Message;
import lightning.users.User;
import lightning.util.Time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

public class VerifyEmailController extends AbstractController {
  private static final Logger logger = LoggerFactory.getLogger(VerifyEmailController.class);
  
  @RequireAuth
  @Template("verify_email.ftl")
  @Route(path="/a/verify-email", methods={GET})
  public Map<String, ?> handleGET() throws Exception {    
    String message = "";
    if (userUtil().isFromHostUniversity()) {
      message = "You are now eligible to host visiting hackers and your hacking application will be automatically accepted.";
    } else if (userUtil().isAutoAccepted()) {
      message = "Your hacking application will be automatically accepted.";
    } else {
      message = "You have increased the strength of your hacker application.";
    }
    
    boolean request_sent = isPOST();
    try (
        NamedPreparedStatement query = db().prepare(
            "SELECT * FROM email_verification_tokens WHERE user_id = :user_id AND expires > :now;",
            ImmutableMap.of("user_id", user().getId(), "now", Time.now()));
        ResultSet result = query.executeQuery();
    ){
      request_sent = request_sent || result.next();
    }
    
    return ImmutableMap.<String, Object>builder()
      .put("verified", user().emailIsVerified())
      .put("request_sent", request_sent)
      .put("email", user().getEmail())
      .put("username", auth().getUser().getUserName())
      .put("xsrf", session().getXSRFToken())
      .put("message", message)
      .build();
  }
  
  @RequireAuth
  @RequireXsrfToken
  @Template("verify_email.ftl")
  @Route(path="/a/verify-email", methods={POST})
  public Map<String, ?> handlePOST() throws Exception {
    badRequestIf(user().emailIsVerified());
    
    // Invalidate any existing email verification tokens.
    db().prepare("DELETE FROM email_verification_tokens WHERE user_id = :user_id;", 
        ImmutableMap.of("user_id", user().getId())).executeUpdateAndClose();
    
    // Create a new token and place it in the DB.
    TokenSet tokens = tokens().createNew();
    
    db().prepareInsert("email_verification_tokens", 
        ImmutableMap.of(
          "user_id", user().getId(), 
          "token_hash", tokens.getServerToken(), 
          "origin_ip", request().ip(),
          "expires", Time.now() + 3600) // 1 hour from now
        ) 
        .executeUpdateAndClose();
    
    // Send the user an email with the token.
    Message message = mail().createMessage();
    message.addRecipient(user().getEmail());
    message.setSubject("Email Verification for " + user().getUserName());
    String text = renderToString("email_verification_email.ftl", ImmutableMap.of(
      "username", user().getUserName(),
      "link", url().to("/a/verify-email/" + tokens.getSignedClientToken())
    ));
    message.setHTMLText(text);    
    mail().send(message);
    
    if (config().enableDebugMode) {
      logger.debug("Sent Email: {}, {}", user().getEmail(), text);
    }
    
    return handleGET();
  }
  
  @Template("verify_email_success.ftl")
  @Route(path="/a/verify-email/:token", methods={GET})
  public Map<String, ?> handleRequest() throws Exception {
    badRequestIf(routeParam("token").isEmpty());
    Optional<TokenSet> tokens = tokens().fromSignedClientToken(routeParam("token").stringValue());
    notFoundIf(!tokens.isPresent());
    
    // Retrieve the token and the associated user.
    User user = null;
    try (
        NamedPreparedStatement query = db().prepare(
            "SELECT * FROM email_verification_tokens WHERE token_hash = :token_hash AND expires > :now;",
            ImmutableMap.of("token_hash", tokens.get().getServerToken(), "now", Time.now()));
        ResultSet result = query.executeQuery();
    ){
      notFoundIf(!result.next());
      final long userId = result.getLong("user_id");
      user = users().getById(userId);
      illegalStateIf(user == null);
    }
    
    // Update state.
    user.setEmailIsVerified(true);
    user.save();
    
    db().prepare("DELETE FROM email_verification_tokens WHERE token_hash = :token_hash;", 
        ImmutableMap.of("token_hash", tokens.get().getServerToken())).executeUpdateAndClose();
    
    return ImmutableMap.of(
      "username", auth().isLoggedIn() ? user().getUserName() : "",
      "xsrf", session().getXSRFToken()
    );
  }
}
