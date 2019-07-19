package com.wsy.mybatis_plus_demo.service.impl;

import com.wsy.mybatis_plus_demo.entity.Student;
import com.wsy.mybatis_plus_demo.mapper.StudentDao;
import com.wsy.mybatis_plus_demo.service.IStudentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements IStudentService {

}
