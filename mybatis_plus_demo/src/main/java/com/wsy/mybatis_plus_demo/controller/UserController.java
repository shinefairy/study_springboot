package com.wsy.mybatis_plus_demo.controller;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.wsy.mybatis_plus_demo.entity.User;
import com.wsy.mybatis_plus_demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shine
 * @since 2019-07-19
 */
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
}

