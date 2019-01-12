package com.yantianpeng.MybatisHello.dao;

import com.yantianpeng.MybatisHello.entity.Employee;

import java.util.List;

/**
 * 测试resultMap
 */
public interface EmployeeMapperResultMapDao {
    /**
     * 测试resultMap方法1
     * @param id
     * @return
     */
    Employee getEmpById(Integer id);

    /**
     * 测试resultMap 方法2
     * @param id
     * @return
     */
    Employee getEmpByIdRresultMap(Integer id);

    /**
     * 测试连表查询
     * @param id
     * @return
     */
    Employee getEmpByIdResultMap2(Integer id);

    /**
     * 测试连表查询3使用association 标签
     * @param id
     * @return
     */
    Employee getEmpByIdResultMap3(Integer id);

    /**
     * 根据员工编号查询ID，分步进行查询
     * @param id
     * @return
     */
    Employee getEmpBystep(Integer id);

    /**
     *  根据部门id查询员工
     * @param id
     * @return
     */
    List<Employee> getEmpsByDepId(Integer id);

    /**
     *  鉴别器的使用
     * @param id
     * @return
     */
    Employee getEmpByIdStepDis(Integer id);
}
