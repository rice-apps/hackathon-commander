package org.riceapps.hackathon.controllers;

import static lightning.server.Context.*;
import static lightning.enums.HTTPMethod.*;
import lightning.ann.*;

@Controller
public class HomeController {
  @Route(path="/", methods={GET})
  public void handleHome() throws Exception {
    response().write("Hello World!");
  }
}
