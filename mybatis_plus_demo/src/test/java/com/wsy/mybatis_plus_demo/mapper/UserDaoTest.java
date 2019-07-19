package com.wsy.mybatis_plus_demo.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wsy.mybatis_plus_demo.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDaoTest {
    @Autowired
    private UserDao userDao;
    @Test
    /** 基本查询方法
     * 根据单个id查询User
     */
    public void testSelectById(){
        User user = userDao.selectById(1);
        System.out.println(user.getCreateTime());
    }
    @Test
    /**
     * 根据多个id 传入id集合 查询List<user>
     */
    public void testSelectByIds(){
        List<Integer> idsList = Arrays.asList(1, 2);
        List<User> users = userDao.selectBatchIds(idsList);
        Assert.assertEquals(2,users.size());
    }
    @Test
    /**
     * 传入map匹配多个信息 key：column value :value
     */
    public void testSelectByMap(){
        Map map =new HashMap<>();
//        map.put("name","张三三");
        map.put("manager_id",1);
        //相当于查询语句中 where name ="张三三" and age=18
        List list = userDao.selectByMap(map);
        System.out.println(list.size());
    }
    /**
     * 条件构造器查询
     */
    @Test
    /**
     * 1.查询名字中包含三并且年龄小于40
     * name like '%雨% and age<40'
     */
    public void testSelectByWrapper(){
        EntityWrapper<User> queryWrapper = new EntityWrapper<>();

        queryWrapper.like("name","三").lt("age",40);
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }
    @Test
    /**
     *名字中包含三并且年龄大于等于10切小于等于20并且email不为空
     */
    public void testSelectByWrapper2(){
        EntityWrapper<User> queryWrapper =new EntityWrapper<>();
        queryWrapper.like("name","三").between("age",20,40).isNotNull("email");
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }
    @Test
    /**
     * 名字为王性或者年龄大于等于25，按照年龄降序排列，年龄相同的按照id升序排列
     * name like '王%' or age >=40 order by age desc,id asc
     */
    public void testSelectByWrapper3(){
        EntityWrapper<User> queryWrapper =new EntityWrapper<>();
        queryWrapper.like("name","张").or().eq("age",12).orderDesc(Arrays.asList("name")).orderAsc(Arrays.asList("id"));
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }

    /**
     * 创建日期为2019年2月14日并且上级为名字是王姓
     * date_format(create_time,%Y-%m-%d) and manager_id in (select id from user where name like '王%')
     */



}