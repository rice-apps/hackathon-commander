<#include "shared.ftl">
<#include "forms.ftl">

<@content title="Reset Password" username=username!>
   <@form action=action xsrf=xsrf title="Reset Password">
    <@errorbar errors=errors />
    <@successbar show=success message="Your password has been updated!" />

    <#if !success>
      <@field label="Email Address" name="username" icon="user" value=defaults.username! error=errors.username! required=true autofocus=true />
      <@field label="Password" type="password" name="password" icon="lock" value="" error=errors.password! required=true />
      <@field label="" type="password" placeholder="Password (Verify)" name="password2" icon="check-square-o" value="" error=errors.password2! required=true />
      <div style="height: 10px;"></div>

      <input type="submit" value="Update Password" />
    </#if>
  </@form>
</@content>
