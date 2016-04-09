package org.riceapps.hackathon.controllers;

import static lightning.enums.HTTPMethod.GET;
import static lightning.enums.HTTPMethod.POST;
import static lightning.server.Context.*;

import java.util.Map;

import lightning.ann.RequireAuth;
import lightning.ann.RequireXsrfToken;
import lightning.ann.Route;
import lightning.ann.Template;
import lightning.plugins.cas.CASAuthenticator;
import lightning.plugins.cas.CASConfig;
import lightning.plugins.cas.CASUser;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

public class VerifyNetIdController extends AbstractController {  
  @Route(path="/a/link-netid", methods={GET})
  @RequireAuth
  @Template("link_netid.ftl")
  public Map<String, ?> handleGET() throws Exception {    
    Optional<String> netId = userUtil().getLinkedNetID();
    
    return ImmutableMap.of(
        "linked", netId.isPresent(),
        "netid", netId.or(""),
        "username", user().getUserName(),
        "xsrf", session().getXSRFToken(),
        "error", queryParam("err").isPresent()
    );
  }
  
  @Route(path="/a/link-netid", methods={POST})
  @RequireAuth
  @RequireXsrfToken
  @Template("link_netid.ftl")
  public Map<String, ?> handlePOST() throws Exception {
    //userUtil().unlinkFromNetID(); // Note: Uncomment this if you want users to be able to unlink NetID.
    return handleGET();
  }
  
  @Route(path="/a/verify-netid", methods={GET})
  @RequireAuth
  public void handleRequest() throws Exception {    
    CASConfig casConfig = CASConfig.newBuilder()
        .setHost("netid.rice.edu")
        .setPath("/cas")
        .build();
    
    CASAuthenticator casAuth = CASAuthenticator.newAuthenticator(casConfig);
    
    if (!userUtil().isLinkedToNetID()) {
      Optional<CASUser> casUser = casAuth.startAuthentication(request(), response(), url().to("/a/verify-netid"));
      
      if (casUser.isPresent()) {
        if (!userUtil().linkToNetID(casUser.get())) {
          redirect(url().to("/a/link-netid", ImmutableMap.of("err", "1")));
        } else {
          redirect(url().to("/a/link-netid"));
        }
      }
      
      return;
    }
    
    redirect(url().to("/a/link-netid"));
  }
}
