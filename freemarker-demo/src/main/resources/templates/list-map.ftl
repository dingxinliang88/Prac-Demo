<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>

<#-- list 数据的展示 -->
<h2>展示list中的stu数据:</h2>
<br>
<br>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#list stuList as stu>
        <tr>
            <td>${stu_index+1}</td>
            <td>${stu.name}</td>
            <td>${stu.age}</td>
            <td>${stu.money}</td>
        </tr>
    </#list>
</table>
<hr>

<#-- Map 数据的展示 -->
<h2>map数据的展示：</h2>
<br/>
<h3>方式一：通过map['key-name'].property</h3><br/>
输出stu1的学生信息：<br/>
姓名：${stuMap['stu1'].name}<br/>
年龄：${stuMap['stu2'].age}<br/>
<br/>
<h3>方式二：通过map.key-name.property</h3><br/>
输出stu2的学生信息：<br/>
姓名：${stuMap.stu1.name}<br/>
年龄：${stuMap.stu2.age}<br/>

<br/>
<h3>遍历map中两个学生信息：</h3><br/>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#list stuMap?keys as key>
        <tr>
            <td>${key_index}</td>
            <td>${stuMap[key].name}</td>
            <td>${stuMap[key].age}</td>
            <td>${stuMap[key].money}</td>
        </tr>
    </#list>
</table>
<hr>

</body>
</html>