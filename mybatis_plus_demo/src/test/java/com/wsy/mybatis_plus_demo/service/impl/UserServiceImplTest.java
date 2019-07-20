package com.wsy.mybatis_plus_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wsy.mybatis_plus_demo.entity.User;
import com.wsy.mybatis_plus_demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Test
    /**
     * 查找符合条件的一条记录，记得加上false 参数 默认是true 相当于list.get（0）
     */
    public void testFindOne(){
        User one = userService.getOne(Wrappers.<User> lambdaQuery().gt(User::getAge,25),false);
        System.out.println(one);
    }
    @Test
    /**
     * 测试批量插入
     */
    public void testSaveBatch(){
        User user1 =new User();
        user1.setName("徐丽1");
        user1.setAge(28);

        User user2 =new User();
        user2.setName("徐丽2");
        user2.setAge(29);
        List<User> users = Arrays.asList(user1, user2);
        boolean result = userService.saveBatch(users);
        System.out.println(result?"批量插入成功":"插入失败");
    }


    @Test
    public void testUpdateBatch(){
        User user1 =new User();
        user1.setName("徐丽3");
        user1.setAge(29);

        User user2 =new User();
        user2.setId(9);
        user2.setName("徐力");
        user2.setAge(29);
        List<User> users = Arrays.asList(user1, user2);
        boolean result = userService.saveOrUpdateBatch(users);
        System.out.println(result?"批量插入或修改成功":"插入失败");
    }
    @Test
    /**
     * 查询的链式调用
     */
    public void testSelectChain(){
        List<User> userList = userService.lambdaQuery().gt(User::getAge,25).like(User::getName,"雨").list();
        userList.forEach(System.out::println);
    }
    @Test
    /**
     * 年龄统统改为从25改为26岁
     */
    public void testUpdateChain(){
        boolean result = userService.lambdaUpdate().eq(User::getAge, 25).set(User::getAge, 26).update();
        System.out.println(result);
    }
    @Test
    /**
     * 可以针对条件进行删除
     */
    public void testDeleteChain(){
        boolean result = userService.lambdaUpdate().eq(User::getAge, 28).remove();
        System.out.println(result);
    }
}