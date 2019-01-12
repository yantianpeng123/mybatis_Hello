package com.yantianpeng.Cache.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeCache  implements Serializable {
    /**
     * # 主键id
     */
   private Integer id;
    /**
     *姓名
     */
    private String last_name ;
    /**
     * #性别 男或者女
     */
    private String gender ;
    /**
     # 邮箱
     */
    private String email ;
}
