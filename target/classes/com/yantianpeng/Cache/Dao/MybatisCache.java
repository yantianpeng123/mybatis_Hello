package com.yantianpeng.Cache.Dao;

import com.yantianpeng.Cache.entity.EmployeeCache;
import java.util.*;

/**

* @Description:    Mybatis缓存机制
 *
 * 两级缓存
 *  一级缓存：
 *      与数据库同一次会话期间查询到数据会放到本地缓存中
 *      以后如果需要获取相同的数据，直接从缓存中拿出来，没必要在取查询数据库。
 *  二级缓存：
 *

* @Author:        yantianpeng

* @CreateDate:     2019/1/11 上午10:45


*/
public interface MybatisCache {

    /**
     * 根据id查询与员工信息
     * @param id
     * @return
     */
    List<EmployeeCache> getEmpByidCache(Integer id);
}
