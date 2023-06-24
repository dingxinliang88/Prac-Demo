<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
<#if stuList??>
    <#list stuList as stu>
        ${stu.name}
    </#list>
</#if>
${name!'null'}

</body>
</html>