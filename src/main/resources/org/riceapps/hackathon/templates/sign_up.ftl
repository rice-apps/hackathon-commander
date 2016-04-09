<#include "shared.ftl">
<#include "forms.ftl">

<@content title="Sign Up" username=username!>
  <@form action="/sign-up" xsrf=xsrf title="Sign Up">
    <p>All hackers, hosts, mentors, sponsors, and organizers need an account. Once you've signed up, we'll guide you through the next steps.</p>
    <@errorbar errors=errors />
    <input type="hidden" name="_next" value="" />
    <@field label="Email Address" name="username" icon="user" value=defaults.username! error=errors.username! required=true autofocus=true />
    <@field label="Password" type="password" name="password" icon="lock" value="" error=errors.password! required=true />
    <@field label="" type="password" placeholder="Password (Verify)" name="password2" icon="check-square-o" value="" error=errors.password2! required=true />
    <div style="height: 10px;"></div>


      <div style="height: 10px;"></div>

      <input type="submit" value="Continue" />
  </@form>
</@content>
