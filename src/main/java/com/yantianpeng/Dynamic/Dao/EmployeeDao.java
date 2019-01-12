package com.yantianpeng.Dynamic.Dao;

import java.util.*;
import com.yantianpeng.Dynamic.entity.Employee;

/**

* @Description:    测试动态sql

* @Author:        yantianpeng

* @CreateDate:     2019/1/10 下午3:36


*/
public interface EmployeeDao {
    /**
     * 测试动态sql
     * if的使用
     * @param employee
     * @return
     */
    List<Employee> getEmpByIdList(Employee  employee);

    /**
     * 测试wehre标签解决sql拼接带上and的语句
     * @param employee
     * @return
     */
    List<Employee> getEmpByWhere(Employee employee);

    /**
     *  sqlTrim的用法测试
     * @param employee
     * @return
     */
    List<Employee> getEmpByContionTrim(Employee employee);

    /**
     *  测试使用choose标签
     * @param employee
     * @return
     */
    List<Employee> getEmpByChoose(Employee employee);

    /**
     *  根据id更新employee
     *  带有那一列的值更新那一列的值
     * @param employee
     * @return
     */
    int updateEmpById(Employee employee);

    /**
     *  使用Trim标签更新员工
     * @param employee
     * @return
     */
    int updateEmpByIdTrim(Employee employee);
    /**
     *  根据id批量查询employee
     * @param list
     * @return
     */
    List<Employee> getEmpByForeach(List<Integer> list);

    /**
     *  批量保存员工在mysql数据库里面
     * @param list
     * @return
     */
    int insertEmpByForeach(List<Employee> list);

//    /**
//     * 在oracle环境下执行批量插入
//     * @param list
//     * @return
//     */
//
//    int inserEmpByidForeachOracl(List<Employee> list);
}
