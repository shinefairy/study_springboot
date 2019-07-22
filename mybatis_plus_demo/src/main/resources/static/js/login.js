layui.use(['form', 'layer', 'jquery'], function () {
    var form = layui.form;
        layer = layui.layer;
        $ = layui.jquery,
    form.on("submit(login)", function () {
        $.post("/user/login",$("#useLogin").serialize(),function(data){
            console.log(data)
            if(data.code == 1){
                window.location.href=data.url;
            }else{
                layer.alert(data.message,function(){
                    layer.closeAll();//关闭所有弹框
                });
            }
        });
        return false;
    });

});

function checkForm() {
    var username=$("#username").val();
    var password=$("#password").val();
    var imgCode = $("#imgCode").val();
    if(username ==null || username ==''){
        layer.alert("用户名不允许为空")
        return false
    }
    if(password ==null || password ==''){
        layer.alert("密码不允许为空")
        return false
    }
    if(imgCode==null || imgCode ==''){
        layer.alert("验证码不允许为空")
        return false
    }
    return true

}