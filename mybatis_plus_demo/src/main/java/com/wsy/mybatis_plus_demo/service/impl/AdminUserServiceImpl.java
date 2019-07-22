package com.wsy.mybatis_plus_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsy.mybatis_plus_demo.entity.AdminUser;
import com.wsy.mybatis_plus_demo.mapper.AdminUserDao;
import com.wsy.mybatis_plus_demo.service.AdminUserService;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl  extends ServiceImpl<AdminUserDao, AdminUser> implements AdminUserService {
}
