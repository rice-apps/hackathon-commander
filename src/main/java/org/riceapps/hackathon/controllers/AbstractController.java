package org.riceapps.hackathon.controllers;

import static lightning.server.Context.db;
import static lightning.server.Context.user;

import org.riceapps.hackathon.db.UserUtil;

import lightning.ann.Controller;
import lightning.ann.Initializer;

@Controller
public abstract class AbstractController {
  public UserUtil userUtil() throws Exception {
    return new UserUtil(db(), user());
  }
  
  @Initializer
  public final void init() {}
}
