package org.riceapps.hackathon.controllers;

import static lightning.server.Context.*;
import static lightning.enums.HTTPMethod.*;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import lightning.ann.*;

public final class HomeController extends AbstractController {
  @Route(path="/", methods={GET})
  @Template("home.ftl")
  public Map<String, ?> handleHome() throws Exception {
    return ImmutableMap.of(
      "username", isLoggedIn() ? user().getUserName() : ""
    );
  }
}
