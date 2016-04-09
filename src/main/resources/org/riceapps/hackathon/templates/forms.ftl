
<#macro form action xsrf title method="POST" enctype="application/x-www-form-urlencoded" id="">
  <div class="form-wrapper">
    <form method="${method}" action="${action}" enctype="${enctype}" id="${id}" novalidate="true">
      <input type="hidden" name="_xsrf" value="${xsrf}" />
      <h1>${title}</h1>
      <#nested>
      <div class="clearfix"></div>
    </form>
  </div>
</#macro>

<#macro field label name icon="" type="text" value="" error="" required=true autofocus=false placeholder="">
  <#if label?has_content>
    <#if required>
      <div class="form-label" required>${label}</div class="form-label">
    <#else>
      <div class="form-label">${label}</div class="form-label">
    </#if>
  </#if>

  <#if error?has_content>
    <div class="input-error">${error}</div>
  </#if>

  <div class="input-group"
    <#if error?has_content>error</#if>
    >
    <#if icon?has_content>
      <span class="input-group-addon">
        <i class="fa fa-${icon} fa-fw"></i>
      </span>
    </#if>
    <input type="${type}"
           name="${name}"
           class="form-control"
           <#if placeholder?has_content>placeholder="${placeholder}"<#else>placeholder="${label}"</#if>
           <#if value?has_content>value="${value}"</#if>
           <#if required>required</#if>
           <#if autofocus>autofocus</#if> />
  </div>
</#macro>

<#macro successbar show=false message="Submission Saved!">
  <#if show>
    <div class="success">
      ${message}
    </div>
  </#if>
</#macro>

<#macro errorbar errors={} message="Your submission contains errors. Please correct them and try again.">
  <#if errors?keys?size != 0>
    <div class="error">${message}</div>
  </#if>
</#macro>
