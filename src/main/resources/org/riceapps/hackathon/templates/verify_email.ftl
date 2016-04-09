<#include "shared.ftl">
<#include "forms.ftl">

<@content title="Email Verification" username=username!>
  <@form action="/a/verify-email" xsrf=xsrf title="Email Verification">
    <#if request_sent>
      <div class="notice">An email has been dispatched to <strong>${email}</strong> containing further instructions.</div>
      <p>Please check your email to complete the verification process. The verification email may take up to
         15 minutes to arrive.</p>
      <p>Don't forget to check your spam folder!</p>
      <p>If the verification email does not arrive, you can visit this page again in one hour to re-send the email.</p>
    <#else>
      <#if verified>
        <div class="success">You have successfully verified your email.</div>
        <p>${message}</p>
      <#else>
        <div class="notice">You have not yet verified your email address.</div>
        <p>You may verify your email address to increase the strength of your application by verifying that you are
           a member of an educational institution.</p>
        <p>If you are a student at Rice, verifying your <b>@rice.edu</b> email address will allow you to access the
           application to host a visiting hacker and will grant you automatic acceptance if you choose to submit an
           application to hack.</p>
        <p>If you are a student at University of Houston, verifying your <b>@uh.edu</b> email address will grant you
           automatic acceptance if you choose to submit an application to hack.</p>
        <input type="submit" value="Send Verification Email" />
       </#if>
    </#if>
  </@form>
</@content>
