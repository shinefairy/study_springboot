package com.wsy.mybatis_plus_demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.wsy.mybatis_plus_demo.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("name","三").lt("age",40);
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }
    @Test
    /**
     *名字中包含三并且年龄大于等于10切小于等于20并且email不为空
     */
    public void testSelectByWrapper2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","三").between("age",20,40).isNotNull("email");
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }
    @Test
    /**
     * 名字为王性或者年龄大于等于25，按照年龄降序排列，年龄相同的按照id升序排列
     * name like '王%' or age >=25 order by age desc,id asc
     */
    public void testSelectByWrapper3(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","王").or().eq("age",25).orderByDesc("name").orderByAsc("id");
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }
    @Test
    /**
     * 创建日期为2019年2月14日并且上级为名字是王姓
     * date_format(create_time,%Y-%m-%d) and manager_id in (select id from user where name like '王%')
     */
    public void testSelectByWrapper4(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}","2019-02-14")
                .inSql("manager_id","select id from tb_user where name like '王%'");
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }
    @Test
    /**
     * 名字为王姓并且年龄小于40或者邮箱不为空
     * like name like '王%' and (age <40 or email is not null)
     * 引入好用的lambda表达式
     */
    public void testSelectByWrapper5(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","王").and(wq->wq.lt("age",40).or().isNotNull("email"));
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }
    @Test
    /**
     * 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     * name like '王%' or（age<40 and age >20  and email is not null）
     */
    public void testSelectByWrapper6(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","王")
                .or(wq->wq.lt("age",40).gt("age",20).isNotNull("email"));
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }
    @Test
    /**
     * （年龄小于40或者邮箱不为空）并且名字为王姓
     */
    public void testSelectByWrapper7(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.nested(wq->wq.lt("age",40).or().isNotNull("email")).likeRight("name","王");
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }
    @Test
    /**
     * 年龄为30、31、34、35
     * age in(30,31,34、35)
     */
    public void testSelectByWrapper8(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age",Arrays.asList(30,31,34,35));
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }
    @Test
    /**
     * 只返回满足条件的其中一条语句即可
     * limit 1
     * 可能会存在sql 注入的风险
     */
    public void testSelectByWrapper9(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age",Arrays.asList(30,31,34,35)).last("limit 1");
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }

    @Test
    /**
     * 1.查询名字中包含三并且年龄小于40
     * name like '%雨% and age<40'
     *(只查询id name 不查询所有字段)
     */
    public void testSelectByWrapperSupper(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name").like("name","雨").lt("age",40);
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }
    @Test
    /**
     * (只排除实体类中的几个属性 排除 manager_id 和 create_time)
     */
    public void testSelectByWrapperSupper2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").lt("age",40).select(User.class,info->!info.getColumn().equals("create_time")&&
                !info.getColumn().equals("manager_id"));
        List<User> users = userDao.selectList(queryWrapper);
        System.out.println(users);
    }
    @Test
    /**
     * 相当于 <if test name!="null"></>
     */
    public void testCondition() {

        String name = "王";
        String email = "";
        condition(name, email);
    }
    /**
     * 当点击查询按钮时,查询搜索框中默认为null,也支持传入条件
     *当传入的条件不为null或者不为空""时,会拼接在where 的后边当做条件
     * @param name
     * @param email
     */
    private void condition(String name, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (StringUtils.isNotEmpty(email)) {
            queryWrapper.like("email", email);
        }
        List<User> userList = userDao.selectList(queryWrapper);
        userList.forEach(System.out::println);

    }
    @Test
    /**
     * 上述方法简化版
     *
     * sql形式：SELECT id,name,age,email,manager_id,create_time FROM user WHERE name LIKE ?
     */
    public void testCondition2(){
        String name = "王";
        String email = "";
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StringUtils.isNotEmpty(name),"name",name)
                .like(StringUtils.isNotEmpty(email),"email",email);
        List<User> userList = userDao.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    @Test
    /**
     * 直接使用实体传值，默认是等值匹配
     * 如果不期望等值匹配，可以在实体类的属性上添加 @TableField(condition="SqlCondition.%like%") 也可以自己定义
     *
     */
    public void testSelectByWrapperEntity(){
        User whereUser =new User();
        whereUser.setName("刘雨红");
        whereUser.setAge(32);
        QueryWrapper<User> queryWrapper =new QueryWrapper<>(whereUser);
        List<User> userList =userDao.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
    @Test
    public void testSelectByWrapperAllEq(){
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        Map<String,Object> params =new HashMap<>();
        params.put("name","王先锋");
        params.put("age","25");
//        params.put("age",null);
        //如果为null 在where 中为ignore ，如果选择false,则直接忽略null值的字段
//        queryWrapper.allEq(params);//        where name = ? and age is null
//        queryWrapper.allEq(params,false); //where name = ?
        //过滤 name
        //where age is NULL
        //也可以过滤
        queryWrapper.allEq((k,v)->!k.equals("name"),params);
        List<User> userList = userDao.selectList(queryWrapper);
        userList.forEach(System.out::println);

    }
    @Test
    public void testSelectPage(){
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
//        Page<User> page = new Page<>(1, 2);
        //如果Ajax 上滑页面不需要获取到总记录数
        Page<User> page = new Page<>(1, 2,false);
        //方法一
        /*IPage<User> iPage = userDao.selectPage(page, queryWrapper);

        System.out.println("总页数:"+iPage.getPages());

        System.out.println("总记录数:"+iPage.getTotal());
        List<User> userList = iPage.getRecords();
        userList.forEach(System.out::println);*/
        //方法二
        IPage<Map<String, Object>> iPage = userDao.selectMapsPage(page, queryWrapper);
        System.out.println("总记录数:"+iPage.getTotal());
        List<Map<String, Object>> userList = iPage.getRecords();
        //返回map类型
        userList.forEach(System.out::println);
    }
    @Test
    public void testMyPage(){
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        //年龄大于26
        queryWrapper.gt("age",26);
        Page<User> page = new Page<>(1, 2);
        IPage<User> iPage = userDao.selectUserPage(page, queryWrapper);
        System.out.println("总记录数:"+iPage.getTotal());
        List<User> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

    @Test
    /**1.防误写
     * lambda防止nameColumn列误写
     */
    public void testSelectByLambda(){
       // LambdaQueryWrapper<Object> lambda = new QueryWrapper<>().lambda();
        //LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lambda = Wrappers.<User>lambdaQuery();
        lambda.like(User::getName,"雨").lt(User::getAge,40);//where name like "%雨%"
        List<User> userList = userDao.selectList(lambda);
        userList.forEach(System.out::println);
    }
    @Test
    public void testSelectByLambda2(){
        LambdaQueryWrapper<User> lambda = Wrappers.<User>lambdaQuery();
        lambda.likeRight(User::getName,"王")
                .and(lqw->lqw.lt(User::getAge,40)
                        .or().isNotNull(User::getEmail));
        List<User> userList = userDao.selectList(lambda);
        userList.forEach(System.out::println);
    }
    @Test
    public void testNewLambda(){
        List<User> userList = new LambdaQueryChainWrapper<User>(userDao).like(User::getName, "雨").ge(User::getAge, 20).list();
        userList.forEach(System.out::println);
    }

}