package com.yantianpeng.MybatisHello.dao;

import com.yantianpeng.MybatisHello.entity.Department;


/**

* @Description:    分步查询

* @Author:        yantianpeng

* @CreateDate:     2019/1/9 下午2:42


*/
public interface DepartmentMapperDao {

    /**
     * 根据部门查询ID
     * @param id
     * @return
     */
    Department getDepById(Integer id);

    /**
     *  查询当前部门下面的所有的员工，并查询该员工的信息
     * @param id
     * @return
     */
    Department getDepByIdPlus(Integer id);

    /**
     * 查询当前部门下面的所有的员工，并查询该员工的信息  现在是分步查询
     * @param id
     * @return
     */
    Department getDepByIdStep(Integer id);
}
