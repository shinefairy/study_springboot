package com.wsy.mybatis_plus_demo.controller;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wsy.mybatis_plus_demo.entity.AdminUser;
import com.wsy.mybatis_plus_demo.entity.User;
import com.wsy.mybatis_plus_demo.service.UserService;
import com.wsy.mybatis_plus_demo.utils.CodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shine
 * @since 2019-07-19
 */
@Slf4j
@Api(tags = "用户接口")
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 根据id查找用户
     */
    @ResponseBody
    @ApiOperation(value = "获取用户详细信息",notes = "根据url传来的id获取用户对象")
    @ApiImplicitParam(name = "id",value = "用户id",required = true,dataType = "Integer",paramType = "path")
    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable(value = "id") Integer id){
        User user = userService.getById(id);
        return ObjectUtils.isNotEmpty(user)?user:null;
    }

    /**
     * 处理用户登录逻辑
     * @param imageCode 图片验证码
     * @param username 用户名
     * @param password 用户名
     * @return
     */
    @ApiOperation(value = "根据用户输入的用户名密码进行登录验证",notes = "使用shiro进行校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value ="用户名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "password",value ="密码",required = true,dataType = "String"),
            @ApiImplicitParam(name = "imageCode",value ="验证码",required = true,dataType = "String"),

    })
    @ResponseBody
    @PostMapping("/login")
    public Map<String,Object> login(String imageCode, String username, String password, HttpSession session){
        log.info("用户名:{},密码:{},验证码:{}",username,password,imageCode);
        Map<String,Object> data = new HashMap();
        if(StringUtils.isEmpty(imageCode)){
            data.put("code",0);
            data.put("message","验证码不允许为空");
        }
        boolean checkResult = CodeUtil.checkVerifyCode(imageCode,session);
        //如果验证码验证不通过
        if(!checkResult) {
            data.put("code",0);
            data.put("message","验证码错误");
        }else {
            //使用shiro进行登录
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username.trim(), password.trim());
            try {
                subject.login(token);
                //登录成功
                AdminUser adminUser = (AdminUser) subject.getPrincipal();
                session.setAttribute("user", adminUser);
                data.put("code", 1);
                data.put("url", "/home");
                log.info("{}登录成功", adminUser.getSysUserName());
            } catch (UnknownAccountException e) {
                data.put("code", 0);
                data.put("message", "账号不存在");
            } catch (AuthenticationException e) {
                data.put("code", 0);
                data.put("message", "账号密码错误");
                log.error(username, "{}密码错误");
            }
        }
        return data;

    }

    @ApiOperation(value = "用户安全退出")
    @GetMapping("/logout")
    @RequiresPermissions(value = {"安全退出"})
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/tologin";
    }


}

