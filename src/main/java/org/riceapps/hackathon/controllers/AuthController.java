package org.riceapps.hackathon.controllers;

import static lightning.enums.HTTPMethod.*;
import static lightning.server.Context.*;

import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import lightning.ann.*;
import lightning.auth.AuthException;
import lightning.users.User;
import lightning.users.Users.UsersException;

/**
 * Provides log-in, log-out, sign-up.
 */
public class AuthController extends AbstractController {
  @Route(path="/log-in", methods={GET})
  @Template("login.ftl")
  public Map<String, ?> handleLogInGET() throws Exception {
    redirectIfLoggedIn("/");
    final ImmutableMap.Builder<String, Object> viewModel = ImmutableMap.<String, Object>builder();
    viewModel.put("xsrf", session().getXSRFToken());
    viewModel.put("errors", validator().getErrors());
    viewModel.put("defaults", queryParamsExcepting(ImmutableList.of("password")));
    viewModel.put("success", false);
    return viewModel.build();
  }
  
  @Route(path="/log-in", methods={POST})
  @Template("login.ftl")
  public Map<String, ?> handleLogInPOST() throws Exception {
    validate("username").isNotEmpty();
    validate("password").isNotEmpty();
    
    if (passesValidation()) {
      try {
        auth().attempt(
            queryParam("username").stringValue(), 
            queryParam("password").stringValue(), 
            queryParam("remember").isChecked(), 
            null);
        
        redirect(url().to("/"));
        return null;
      } catch (AuthException e) {
        switch (e.getType()) {
          case INVALID_PASSWORD:
          case INVALID_USERNAME:
            validator().addError("username", "You entered an incorrect username/password combination.");
            break;
          case IP_THROTTLED:
          case NO_USER:
          case USER_BANNED:
          case USER_THROTTLED:
            validator().addError("username", e.getMessage());
            break;
          case MISCONFIGURED:
          case NOT_IMPLEMENTED:
          case NOT_SUPPORTED:
          case DRIVER_ERROR:
          default:
            throw e;
        }
      }
    }
    
    return handleLogInGET();
  }
  
  @Route(path="/log-out", methods={GET})
  @RequireAuth
  public void handleLogOutGET() throws Exception {
    if (auth().isLoggedIn()) {
      auth().logout(true);
    } 
    
    redirect(url().to("/"));
  }
  
  @Route(path="/sign-up", methods={GET})
  @Template("sign_up.ftl")
  public Map<String, ?> handleSignUpGET() throws Exception {
    redirectIfLoggedIn("/");
    final ImmutableMap.Builder<String, Object> viewModel = ImmutableMap.<String, Object>builder();
    // TODO(mschurr): Add RECAPTCHA.
    viewModel.put("xsrf", session().getXSRFToken());
    viewModel.put("errors", validator().getErrors());
    viewModel.put("defaults", queryParamsExcepting(ImmutableList.of("password", "password2")));
    
    return viewModel.build();
  }
  
  @Route(path="/sign-up", methods={POST})
  @Template("sign_up.ftl")
  @RequireXsrfToken
  public Map<String, ?> handleSignUpPOST() throws Exception {
    redirectIfLoggedIn("/");
    validate("username").isEmail();
    validate("password").isNotEmpty();
    validate("password").matches((v) -> {
      return users().isValidPassword(queryParam("username").stringValue(), queryParam("password").stringValue()) ? null : "You must enter a valid password.";
    });
    validate("password2").matches((v) -> {
      return v.stringValue().equals(queryParam("password").stringValue()) ? null : "Your passwords must match.";
    });
    
    if (!validator().hasErrors()) {
      try {
        if (users().getByName(queryParam("username").stringValue()) != null) {
          validator().addError("username", "The requested username is already in use.");
          return handleSignUpGET();
        }
        
        User user = users().create(
            queryParam("username").stringValue(), 
            queryParam("username").stringValue(), 
            queryParam("password").stringValue());
        auth().loginAs(user, false, false);
        redirect(url().to("/"));
        return null;
      } catch (UsersException e) {
        validator().addError("username", e.getMessage());
      }
    }
    
    return handleSignUpGET();
  }
}
