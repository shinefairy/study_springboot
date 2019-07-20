package com.wsy.mybatis_plus_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsy.mybatis_plus_demo.entity.User;
import com.wsy.mybatis_plus_demo.mapper.UserDao;
import com.wsy.mybatis_plus_demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shine
 * @since 2019-07-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
