package com.yantianpeng.mybatis04;

import com.alibaba.fastjson.JSON;
import com.yantianpeng.Cache.Dao.MybatisCache;
import com.yantianpeng.Cache.entity.EmployeeCache;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class MybatisCacheTest  {


    public static void main(String[] args) throws Exception{

      //  test01();//测试Cache缓存
        test02();//开启二级缓存
    }

    /**
     *  测试二级缓存开启时，Mybatis的执行情况
     * @throws Exception
     */
    public static void test02() throws Exception{
        //获取sqlsession1
        SqlSession sqlSession1 = getSqlFactory().openSession();
        //获取Sqlsession2
        SqlSession sqlSession2 = getSqlFactory().openSession();
        MybatisCache mapper1 = sqlSession1.getMapper(MybatisCache.class);
        MybatisCache mapper2 = sqlSession2.getMapper(MybatisCache.class);

        List<EmployeeCache> empByidCache = mapper1.getEmpByidCache(1);
        String string = JSON.toJSONString(empByidCache, true);
        System.out.println(string);
        sqlSession1.close();
        System.out.println("=====================================>>>>>>>");

        List<EmployeeCache> empByidCache1 = mapper2.getEmpByidCache(1);
        String string1 = JSON.toJSONString(empByidCache1, true);
        System.out.println(string1);
        sqlSession2.close();
    }


    /**
     *   测试Mybatis 缓存 Cache
     * @throws Exception
     */
    public static void test01() throws Exception{
        SqlSession sqlSession = getSqlFactory().openSession();
        MybatisCache mapper = sqlSession.getMapper(MybatisCache.class);
        List<EmployeeCache> empByidCache = mapper.getEmpByidCache(3);
        String string = JSON.toJSONString(empByidCache, true);
        System.out.println(string);

        List<EmployeeCache> empByidCache1 =  mapper.getEmpByidCache(1);
       String str =  JSON.toJSONString(empByidCache1,true);
        System.out.println(str);
    }

    /**
     *获取SqlsessionFactoryd对象
     * @return
     * @throws Exception
     */
    public static SqlSessionFactory getSqlFactory() throws Exception{
        InputStream resourceAsReader = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsReader);
        return build;
    }
}
