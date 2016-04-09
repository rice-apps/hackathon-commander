package org.riceapps.hackathon.controllers;

import static lightning.enums.HTTPMethod.GET;
import static lightning.enums.HTTPMethod.POST;
import static lightning.server.Context.auth;
import static lightning.server.Context.isPOST;
import static lightning.server.Context.queryParam;
import static lightning.server.Context.session;
import static lightning.server.Context.user;
import static lightning.server.Context.users;
import static lightning.server.Context.validate;
import static lightning.server.Context.validator;

import java.util.Map;

import lightning.ann.RequireAuth;
import lightning.ann.Route;
import lightning.ann.Template;
import lightning.auth.AuthException;

import com.google.common.collect.ImmutableMap;

public class ChangePasswordController extends AbstractController {
  @Route(path="/a/change-password", methods={GET})
  @Template("change_password.ftl")
  @RequireAuth
  public Map<String, ?> handleChangePasswordGET() throws Exception {
    final ImmutableMap.Builder<String, Object> viewModel = ImmutableMap.builder();
    viewModel.put("username", user().getUserName());
    viewModel.put("errors", validator().getErrors());    
    viewModel.put("success", isPOST() && validator().passes());
    viewModel.put("xsrf", session().getXSRFToken());
    return viewModel.build();
  }
  
  @Route(path="/a/change-password", methods={POST})
  @Template("change_password.ftl")
  @RequireAuth
  public Map<String, ?> handleChangePasswordPOST() throws Exception {
    validate("old_password").isNotEmpty();
    validate("password").isNotEmpty();
    validate("password2").isNotEmpty();
    
    // Verify that the old password is correct.
    try {
      if (!auth().checkPassword(queryParam("old_password").stringValue())) {
        validator().addError("old_password", "You entered an incorrect old password.");
      }
    } catch (AuthException e) {
      if (e.getType() == AuthException.Type.DRIVER_ERROR) {
        throw e;
      } else {
        validator().addError("old_password", e.getMessage());
      }
    }

    String newPassword = queryParam("password").stringValue();
    String newPassword2 = queryParam("password2").stringValue();
    
    // Verify the new password meets constraints.
    if (!users().isValidPassword(user().getUserName(), newPassword)) {
      validator().addError("password", "The password you entered is not valid.");
    }
    
    // Verify confirmation password is correct.
    if (!newPassword.equals(newPassword2)) {
      validator().addError("password2", "The passwords you entered do not match.");
    }
    
    // Update the password.
    if (validator().passes()) {
      user().setPassword(newPassword);
      user().save();
    }
    
    return handleChangePasswordGET();
  }  
}
