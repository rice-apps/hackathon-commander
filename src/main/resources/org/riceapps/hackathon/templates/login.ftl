<#include "shared.ftl">
<#include "forms.ftl">

<@content title="Log In" username=username!>
  <@form action="/log-in" xsrf=xsrf title="Log In">
    <@errorbar errors=errors />
    <input type="hidden" name="_next" value="" />

    <@field label="Email Address" name="username" icon="user" value=defaults.username! error=errors.username! required=true autofocus=true />
    <@field label="Password" type="password" name="password" icon="lock" value="" error=errors.password! required=true />

    <label><input type="checkbox" name="remember" value="1" /> Remember Me</label>
    <div style="height: 10px;"></div>
    <input type="submit" value="Log In" style="float: left;" />

    <span style="float: right; text-align: right;">
      <a href="/forgot-password">Forgot your password?</a><br />
      <a href="/sign-up">Don't have an account?</a>
    </span>
  </@form>
</@content>
