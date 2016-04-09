<#include "shared.ftl">
<#include "forms.ftl">

<@content title="Email Verification" username=username!>
  <@form action="/a/verify-email" xsrf=xsrf title="Email Verification">
    <div class="success">You have successfully verified your email!</div>
  </@form>
</@content>
