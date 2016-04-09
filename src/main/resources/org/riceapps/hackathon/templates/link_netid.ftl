<#include "shared.ftl">
<#include "forms.ftl">

<@content title="Link Rice NetID" username=username!>
  <@form action="/a/link-netid" xsrf=xsrf title="Link Rice NetID">
    <#if error>
      <div class="error">Failed to link NetID: That NetID is already claimed by another user.</div>
    </#if>

    <#if linked>
      <div class="success">
        You have linked your account to
        <span style="font-weight: bold;">${netid}@netid.rice.edu</span>.
      </div>
      <p>You are now eligible to host visiting hackers and your hacking application will be automatically accepted.</p>
      <#--<p><input type="submit" value="Unlink" /></p>-->
    <#else>
      <div class="notice">You have not linked your account to a Rice NetID.</div>
      <p>If you are a student at Rice University, you may link your account to a Rice NetID to verify that you are
         a member of the university. This verification will grant you automatic acceptance to Hack Rice if you submit
         a hacker application and will allow you to fill out the application to host visiting hackers.</p>
      <a class="button" href="/a/verify-netid">Link NetID</a>
    </#if>
  </@form>
</@content>
