package com.yantianpeng.MybatisHello.entity;

import lombok.Data;
@Data
public class Employee {
    /**
    员工编号
     */
    private Integer id;
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 员工性别
     */
    private String gender;
    /**
     * 员工邮箱
     */
    private String email;
    /**
     * 部门id
     */
    private Integer d_id;
    /**
     * 部门信息
     */
     private Department department;
}
