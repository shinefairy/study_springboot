<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>登录-后台管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="icon" href="/images/logo.jpg" type="image/x-icon" />
    <link rel="shortcut icon" href="/images/logo.jpg" type="image/x-icon" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all"/>
</head>
<body class="loginBody">
<form class="layui-form" id="useLogin" onsubmit=" return checkForm()">
    <div class="layui-form-item input-item">
        <label for="userName">用户名</label>
        <input type="text" placeholder="请输入用户名" autocomplete="off" id="username" name="username" class="layui-input"
               lay-verify="required|userName">
    </div>
    <div class="layui-form-item input-item">
        <label for="password">密码</label>
        <input type="password" placeholder="请输入密码" autocomplete="off" id="password" name="password" class="layui-input"
               lay-verify="required|password">
    </div>
    <div class="layui-form-item input-item" id="imgCode">
        <label for="code">验证码</label>
        <input type="text" maxlength="4" placeholder="请输入验证码" id="imageCode" name="imageCode" autocomplete="off" id="code"
               class="layui-input">
        <#--验证码通过接口获取-->
        <img id="imgObj" title="看不清，换一张" src="/getKaptchaImage" onclick="this.src='/getKaptchaImage?d='+new Date().getTime()"/>
    </div>
    <div class="layui-form-item">
        <button class="layui-btn layui-block" lay-filter="login" lay-submit>登录</button>
    </div>

</form>
<script type="text/javascript" src="/layui/layui.js"></script>
<script type="text/javascript" src="/js/login.js"></script>