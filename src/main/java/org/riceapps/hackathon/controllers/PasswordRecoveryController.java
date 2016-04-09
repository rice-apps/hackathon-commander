package org.riceapps.hackathon.controllers;

import static lightning.server.Context.*;
import static lightning.enums.HTTPMethod.*;

import java.sql.ResultSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import lightning.ann.*;
import lightning.crypt.Hasher;
import lightning.db.NamedPreparedStatement;
import lightning.mail.Message;
import lightning.users.User;
import lightning.util.Time;

public class PasswordRecoveryController extends AbstractController {
  private static final Logger logger = LoggerFactory.getLogger(PasswordRecoveryController.class);
  
  @Route(path="/forgot-password", methods={GET})
  @Template("password_recovery_request.ftl")
  public Map<String, ?> handleGET() throws Exception {
    redirectIfLoggedIn(url().to("/"));
    ImmutableMap.Builder<String, Object> viewModel = ImmutableMap.<String, Object>builder();
    viewModel.put("success", isPOST() && !validator().hasErrors());
    viewModel.put("xsrf", session().getXSRFToken());
    viewModel.put("defaults", queryParams());
    viewModel.put("errors", validator().getErrors());
    return viewModel.build();
  }
  
  @Route(path="/forgot-password", methods={POST})
  @Template("password_recovery_request.ftl")
  @RequireXsrfToken
  public Object handlePOST() throws Exception {    
    redirectIfLoggedIn(url().to("/"));
    validate("username").isNotEmpty().isEmail();
    
    if (validator().hasErrors()) {
      return handleGET();
    }
    
    User user = users().getByEmail(queryParam("username").stringValue());
    
    if (user == null) {
      validator().addError("username", "You must enter a valid email address.");
      return handleGET();
    }
    
    // TODO(mschurr): Rate limit the number of recovery requests that can be sent from one IP.
    
    // Make sure there isn't already a valid token for the user
    try (
      NamedPreparedStatement query = db().prepare("SELECT * FROM recovery_tokens WHERE user_id = :user_id AND expires > :expires;", 
          ImmutableMap.of("user_id", user.getId(), "expires", Time.now()));
      ResultSet result = query.executeQuery();
    ) {
      if (result.next()) {
        return handleGET(); // Show success message, don't send another email.
      }
    }
    
    // Generate a random token and put it in the DB
    String token = Hasher.generateToken(64, (x) -> false);
    String signedToken = hasher().sign(token);
    String hashToken = Hasher.hash(token);
    hasher().verify(signedToken);
        
    db().prepareInsert("recovery_tokens", ImmutableMap.of(
        "user_id", user.getId(), 
        "token_hash", hashToken, 
        "origin_ip", request().ip(),
        "expires", Time.now() + 3600)) // 1 hour from now
        .executeUpdateAndClose();
    
    // Send user an email with the token
    Message message = mail().createMessage();
    message.addRecipient(user.getEmail());
    message.setSubject("Password Recovery for " + user.getUserName());
    String text = renderToString("password_recovery_email.ftl", ImmutableMap.of(
      "username", user.getUserName(),
      "link", url().to("/forgot-password/" + signedToken)
    ));
    
    if (config().enableDebugMode) {
      logger.debug(text);
    }
    
    message.setHTMLText(text);
    mail().send(message);
    
    return handleGET();
  }
  
  @Route(path="/forgot-password/:token", methods={GET, POST})
  @Template("password_recovery_reset.ftl")
  public Map<String, ?> handleResetRequest() throws Exception {
    redirectIfLoggedIn("/");
    badRequestIf(routeParam("token").isEmpty());
    
    final String token = hasher().verify(routeParam("token").stringValue());
    notFoundIf(token == null);
    final String hashedToken = Hasher.hash(token);

    // Verify that token is valid and get its user.
    User user = null;
    try (
        NamedPreparedStatement query = db().prepare(
            "SELECT * FROM recovery_tokens WHERE token_hash = :token_hash AND expires > :now;",
            ImmutableMap.of("token_hash", hashedToken, "now", Time.now()));
        ResultSet result = query.executeQuery();
    ){
      notFoundIf(!result.next());
      final long userId = result.getLong("user_id");
      user = users().getById(userId);
      illegalStateIf(user == null);
    }

    return isPOST() ? handleResetPOST(user) : handleResetGET(user);
  }

  public Map<String, ?> handleResetGET(final User user) throws Exception {
    final ImmutableMap.Builder<String, Object> viewModel = ImmutableMap.<String, Object>builder();
    viewModel.put("action", "/forgot-password/" + routeParam("token").stringValue());
    viewModel.put("success", isPOST() && !validator().hasErrors());
    viewModel.put("errors", validator().getErrors());
    viewModel.put("defaults", queryParamsExcepting(ImmutableList.of("password", "password2")));
    viewModel.put("xsrf", session().getXSRFToken());
    return viewModel.build();
  }

  public Map<String, ?> handleResetPOST(final User user) throws Exception {
    requireXsrf();
    validate("password").isNotEmpty();
    validate("password").matches((v) -> {
      return users().isValidPassword(user.getUserName(), queryParam("password").stringValue()) ? null : "You must enter a valid password.";
    });
    validate("password2").matches((v) -> {
      return v.stringValue().equals(queryParam("password").stringValue()) ? null : "Your passwords must match.";
    });
    validate("username").matches((v) -> {
      return v.stringValue().equalsIgnoreCase(user.getEmail()) ? null : "You must enter your email address.";
    });
    
    if (!validator().hasErrors()) {
      user.setPassword(request().queryParam("password").stringValue());
      user.save();
      db().prepare("DELETE FROM recovery_tokens WHERE token_hash = :token_hash;", ImmutableMap.of(
            "token_hash", Hasher.hash(hasher().verify(routeParam("token").stringValue()))
          )).executeUpdateAndClose();
      auth().loginAs(user, false, false);
      redirect(url().to("/"));
      return null;
    }

    return handleResetGET(user);
  }
}
