package com.wsy.mybatis_plus_demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tb_admin_user")
@Data
public class AdminUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String sysUserName;

    private String sysUserPwd;

    private Integer roleId;

    private String userPhone;

    private String regTime;

    private String userStatus;

}
