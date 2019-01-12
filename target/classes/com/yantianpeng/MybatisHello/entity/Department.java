package com.yantianpeng.MybatisHello.entity;

import lombok.Data;

import java.util.List;

@Data
public class Department {

    /**
     * 部门id
     */
    private Integer id;
    /**
     *  部门的名字
     */
    private String departmentName;
    /**
     * 一个部门里面含有多个员工
     */
    private List<Employee>  employees;
}
