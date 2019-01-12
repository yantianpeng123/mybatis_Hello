package com.yantianpeng.MybatisHello.dao;

import com.yantianpeng.MybatisHello.entity.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapperDao {
     /**
      * 根据员工id查询员工
      * @param id
      * @return
      */
     Employee getemployeeById(Integer id);

     /**
      * 增加一个员工
      * @param employee
      * @return
      */
     boolean addEmp(Employee employee);

     /**
      * 删除一个员工
      * @param employee
      * @return
      */
     int delEmp(Employee employee);

     /**
      * 修改一个员工
      * @param employee
      * @return
      */
     int updateemp(Employee employee);

     /**
      * 添加员工并获取该员工的id值
      * @param employee
      * @return
      */
     boolean addEmpAndGetId(Employee employee);

     /**
      * 通过Id和name查找用户
      * @param id
      * @param name
      * @return
      */
     Employee  getEmployeeByNameAndId(Integer id,String name);

     /**
      * 通过name和id查找用户
      * @param name
      * @param id
      * @return
      */
     Employee getEmployeeByIdAndName(@Param("name") String name, @Param("id") Integer id);

     /**
      * 测试使用参数map
      * @param map
      * @return
      */
     Employee getemployeeByMap(Map<String,Object> map);

     /**
      * #号和$f符号的区别
      * @return
      */
     Employee getemployeeByIdAndName(@Param("id") Integer id,@Param("name") String name);

     /**
      *   动态表名的生成和添加
      * @param map
      * @return
      */
     Employee getEmployeeActivity (Map<String,Object> map);

     /**
      * 根据姓名查询
      * @param name
      * @return
      */
     List<Employee> getListEmpByName (@Param("name") String name);

     /**
      * 返回值是Map的取值
      * @return
      */
     Map<String,Object> getMapEmpById(Map<String ,Object> map);

     /**
      * 返回值是多个时候封装到Map里面
      * 使用对象的id作为map的key值，需要时用MapKey指定map中key的取值
      * MapKey
      * @param name
      * @return
      */
     @MapKey("id")
     Map<Integer,Employee> getMapEmployeeByList(@Param("name") String name);

     /**
      * 返回的map对象封装多个对象 使用name作为作为key值
      * @return
      */
     @MapKey("name")
     Map<String,Employee>getMapEmployeeBylistName();
}
