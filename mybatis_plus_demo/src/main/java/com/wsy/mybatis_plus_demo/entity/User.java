package com.wsy.mybatis_plus_demo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author shine
 * @since 2019-07-19
 */
@TableName("tb_user")
@Data
public class User {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    private Integer managerId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /*排除这个列，即这个列的信息不会插入到数据库中 或使用transient*/
    @TableField(exist = false)
    private String remark;
    /*用transient 标注的字段不参与序列化*/
//    private transient String notExist;

}
