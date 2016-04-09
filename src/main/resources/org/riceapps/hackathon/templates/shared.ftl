<#macro content title username>
  <!DOCTYPE HTML>
  <html>
    <head>
      <title>${title} - Hack Rice</title>
      <link rel="stylesheet" type="text/css" media="screen" href="/static/styles.css" />
      <link rel="stylesheet" type="text/css" media="screen" href="/static/fontawesome/css/font-awesome.css" />
      <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700,400italic,600,600italic,700italic"
            rel="stylesheet" type="text/css" />
      <link rel="shortcut icon" href="/static/images/favicon.png" />
    </head>
    <body>
      <div class="page_wrapper">
        <div class="navbar">
          <div class="wrapper">
            <div class="navbar-header">
              <a href="/">
                <img src="/static/images/navbar-logo-w.svg" />
              </a>
            </div>
            <div class="navbar-content">
              <ul>
                <li>
                  <a href="javascript:;">
                    <i class="fa fa-laptop fa-fw"></i>
                    Hackers
                    <span class="caret"></span>
                  </a>
                  <ul class="dropdown-menu">
                    <li>
                      <a href="/h/application">
                        <i class="fa fa-edit fa-fw"></i>
                        Application
                      </a>
                    </li>
                    <li>
                      <a href="/h/waivers">
                        <i class="fa fa-legal fa-fw"></i>
                        Waivers
                      </a>
                    </li>
                    <!--
                    <li class="divider"></li>
                    <li>
                      <a href="/h/request-mentor">
                        <i class="fa fa-male fa-fw"></i>
                        Request Mentor
                      </a>
                    </li>
                    <li>
                      <a href="/h/live">
                        <i class="fa fa-feed fa-fw"></i>
                        Live Feed
                      </a>
                    </li>
                    <li class="divider"></li>-->
                    <li>
                      <a href="/h/reimbursements">
                        <i class="fa fa-money fa-fw"></i>
                        Reimbursements
                      </a>
                    </li>
                    <!--<li>
                      <a href="/h/feedback">
                        <i class="fa fa-comment-o fa-fw"></i>
                        Feedback
                      </a>
                    </li>-->
                  </ul>
                </li>
                <!--<li>
                  <a href="javascript:;">
                    <i class="fa fa-bed fa-fw"></i>
                    Hosts
                    <span class="caret"></span>
                  </a>
                  <ul class="dropdown-menu">
                    <li>
                      <a href="/ho/application">
                        <i class="fa fa-edit fa-fw"></i>
                        Application
                      </a>
                    </li>
                    <li>
                      <a href="/ho/waivers">
                        <i class="fa fa-legal fa-fw"></i>
                        Waivers
                      </a>
                    </li>
                    <li>
                      <a href="/a/link-netid">
                        <i class="fa fa-link fa-fw"></i>
                        Link Rice NetID
                      </a>
                    </li>
                    <li>
                      <a href="/a/verify-email">
                        <i class="fa fa-send-o fa-fw"></i>
                        Verify Email
                      </a>
                    </li>
                    <li>
                      <a href="/a/verify-phone">
                        <i class="fa fa-mobile fa-fw"></i>
                        Verify Phone
                      </a>
                    </li>
                  </ul>
                </li>-->
                <li>
                  <a href="javascript:;">
                    <i class="fa fa-balance-scale fa-fw"></i>
                    Organizers
                    <span class="caret"></span>
                  </a>
                  <ul class="dropdown-menu">
                    <li>
                      <a href="/o/hackers">
                        <i class="fa fa-envelope fa-fw"></i>
                        Hacker Applications
                      </a>
                    </li>
                    <!--<li>
                      <a href="/o/hosts">
                        <i class="fa fa-envelope-o fa-fw"></i>
                        Host Applications
                      </a>
                    </li>-->
                    <li class="divider"></li>
                    <li>
                      <a href="/o/check-in">
                        <i class="fa fa-edit fa-fw"></i>
                        Check In
                      </a>
                    </li>
                    <!--<li>
                      <a href="/o/live">
                        <i class="fa fa-feed fa-fw"></i>
                        Live Feed
                      </a>
                    </li>
                    <li>
                      <a href="/o/mass-email">
                        <i class="fa fa-send-o fa-fw"></i>
                        Mass Email
                      </a>
                    </li>
                    <li>
                      <a href="/o/mass-text">
                        <i class="fa fa-mobile fa-fw"></i>
                        Mass Text
                      </a>
                    </li>
                    <li>
                      <a href="/o/contacts">
                        <i class="fa fa-phone fa-fw"></i>
                        Contacts
                      </a>
                    </li>-->
                    <li class="divider"></li>
                    <li>
                      <a href="/o/reimbursements">
                        <i class="fa fa-money fa-fw"></i>
                        Reimbursements
                      </a>
                    </li>
                    <!--<li>
                      <a href="/o/feedback">
                        <i class="fa fa-comments-o fa-fw"></i>
                        Feedback
                      </a>
                    </li>
                    <li>
                      <a href="/o/resumes">
                        <i class="fa fa-book fa-fw"></i>
                        Resumes
                      </a>
                    </li>-->
                    <li>
                      <a href="/o/stats">
                        <i class="fa fa-line-chart fa-fw"></i>
                        Statistics
                      </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                      <a href="/o/users">
                        <i class="fa fa-group fa-fw"></i>
                        Users
                      </a>
                    </li>
                    <li>
                      <a href="/o/settings">
                        <i class="fa fa-cog fa-fw"></i>
                        Settings
                      </a>
                    </li>
                  </ul>
                </li>
                <!--<li>
                  <a href="javascript:;">
                    <i class="fa fa-group fa-fw"></i>
                    Mentors
                    <span class="caret"></span>
                  </a>
                  <ul class="dropdown-menu">
                    <li>
                      <a href="/m/requests">
                        <i class="fa fa-envelope-o fa-fw"></i>
                        Requests
                      </a>
                    </li>
                  </ul>
                </li>-->
                <!--<li>
                  <a href="javascript:;">
                    <i class="fa fa-bank fa-fw"></i>
                    Sponsors
                    <span class="caret"></span>
                  </a>
                  <ul class="dropdown-menu">
                    <li>
                      <a href="/s/resume-book">
                        <i class="fa fa-book fa-fw"></i>
                        Resume Book
                      </a>
                    </li>
                  </ul>
                </li>-->
              </ul>
              <ul class="right">
                <#if username != "">
                  <li>
                    <a href="javascript:;">
                      <i class="fa fa-user"></i>
                      ${username}
                      <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                      <li>
                        <a href="/a/link-netid">
                          <i class="fa fa-link fa-fw"></i>
                          Link Rice NetID
                        </a>
                      </li>
                      <li>
                        <a href="/a/verify-email">
                          <i class="fa fa-send-o fa-fw"></i>
                          Verify Email
                        </a>
                      </li>
                      <!--<li>
                        <a href="/a/verify-phone">
                          <i class="fa fa-mobile fa-fw"></i>
                          Verify Phone
                        </a>
                      </li>-->
                      <li class="divider"></li>
                      <li>
                        <a href="/a/preferences">
                          <i class="fa fa-user fa-fw"></i>
                          Profile
                        </a>
                      </li>
                      <li>
                        <a href="/a/preferences">
                          <i class="fa fa-cog fa-fw"></i>
                          Settings
                        </a>
                      </li>
                      <li>
                        <a href="/a/change-password">
                          <i class="fa fa-key fa-fw"></i>
                          Change Password
                        </a>
                      </li>
                      <li class="divider"></li>
                      <li>
                        <a href="/log-out">
                          <i class="fa fa-sign-out fa-fw"></i>
                          Log Out
                        </a>
                      </li>
                    </ul>
                  </li>
                <#else>
                  <li>
                    <a href="/sign-up">
                      <i class="fa fa-user-plus fa-fw"></i>
                      Sign Up
                    </a>
                  </li>
                  <li>
                    <a href="/log-in">
                      <i class="fa fa-sign-in fa-fw"></i>
                      Log In
                    </a>
                  </li>
                </#if>
              </ul>
            </div>
          </div>
        </div>

        <div class="content">
          <div class="wrapper">
            <#nested>
          </div>
        </div>
        <div class="footer_push"></div>
      </div>

      <div class="footer">
        <div class="wrapper">
          &copy; 2016 Hack Rice, All Rights Reserved.
          <span style="float: right;">
            <a href="http://www.rice.edu/" target="_blank">Rice University</a>
            &bull;
            <a href="http://csclub.rice.edu" target="_blank">Rice CS Club</a>
            &bull;
            <a href="http://www.riceapps.org/" target="_blank">Rice Apps</a>
            &bull;
            <a href="https://www.github.com/rice-apps/hackathon-commander" target="_blank">Source Code</a>
          </span>
        </div>
      </div>
    </body>
  </html>
</#macro>
