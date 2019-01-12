package com.yantianpeng.MybatisHello.dao;

import com.yantianpeng.MybatisHello.entity.Employee;
import org.apache.ibatis.annotations.Select;

public interface EmpolyeeMapperAnnotation {

     @Select(" select id,last_name as name , gender,email from tab1_employee where id = #{id}")
     Employee getemployeeById(Integer id);
}
