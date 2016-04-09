<#include "shared.ftl">
<#include "forms.ftl">

<@content title="Password Reset" username=username!>
  <@form action="/forgot-password" xsrf=xsrf title="Password Reset">
    <@errorbar errors=errors />
    <@successbar show=success message="An email has been dispatched containing further instructions." />
    <@field label="Email Address" name="username" icon="user" value=defaults.username! error=errors.username! required=true autofocus=true />
    <div style="height: 10px;"></div>

    <input type="submit" value="Continue" />
  </@form>
</@content>
