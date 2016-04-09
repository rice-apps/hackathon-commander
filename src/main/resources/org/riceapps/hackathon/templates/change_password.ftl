<#include "shared.ftl">
<#include "forms.ftl">

<@content title="Change Password" username=username!>
   <@form action="/a/change-password" xsrf=xsrf title="Change Password">
    <@errorbar errors=errors />
    <@successbar show=success message="Your password has been updated!" />
    <@field label="Old Password" type="password" name="old_password" icon="lock" value="" error=errors.old_password! required=true />

    <@field label="New Password" type="password" name="password" icon="lock" value="" error=errors.password! required=true />
    <@field label="" type="password" placeholder="New Password (Verify)" name="password2" icon="check-square-o" value="" error=errors.password2! required=true />
    <div style="height: 10px;"></div>

    <input type="submit" value="Save" />
  </@form>
</@content>
