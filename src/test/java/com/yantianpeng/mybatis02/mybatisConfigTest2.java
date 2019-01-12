package com.yantianpeng.mybatis02;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yantianpeng.MybatisHello.dao.DepartmentMapperDao;
import com.yantianpeng.MybatisHello.dao.EmployeeMapperResultMapDao;
import com.yantianpeng.MybatisHello.entity.Department;
import com.yantianpeng.MybatisHello.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class mybatisConfigTest2 {

    public static void main(String[] args) throws Exception{
        //test01();//测试resultMap方法1
        //test02();//测试resultMap方法2
       // test03();// 测试连表查询并返回json
        //test04();//测试使用association
       // test05();//测试使用测试使用association进行分步查询
        //test06();//测试collection查询的结果是List元素。
       // test07();
        test08();//测试鉴别器的使用
    }

    /**
     *  鉴别器的使用
     * @throws Exception
     */

    public static void test08() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperResultMapDao mapper = sqlSession.getMapper(EmployeeMapperResultMapDao.class);
        Employee empByIdStepDis = mapper.getEmpByIdStepDis(4);
        String string = JSON.toJSONString(empByIdStepDis, true);
        System.out.println(string);
    }
    /**
     *  测试collection分步查询
     * @throws Exception
     */
    public static void test07()throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DepartmentMapperDao mapper = sqlSession.getMapper(DepartmentMapperDao.class);
        Department depByIdStep = mapper.getDepByIdStep(4);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(depByIdStep));
        String string = JSON.toJSONString(depByIdStep, true);
        System.out.println(string);
    }
    /**
     * 测试使用collection 嵌套结果集是的list的元素
     * @throws Exception
     */
    public static void test06() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DepartmentMapperDao mapper = sqlSession.getMapper(DepartmentMapperDao.class);
        Department depByIdPlus = mapper.getDepByIdPlus(3);
        String string = JSON.toJSONString(depByIdPlus, true);
        System.out.println(string);
    }

    /**
     * 测试使用测试使用association进行分步查询
     * @throws Exception
     */
    public static void test05() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperResultMapDao mapper = sqlSession.getMapper(EmployeeMapperResultMapDao.class);
        Employee empBystep = mapper.getEmpBystep(1);
        String string = JSON.toJSONString(empBystep, true);
        System.out.println(string);
    }
    /**
     * 测试使用association
     * @throws Exception
     */
    public static void test04() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperResultMapDao mapper = sqlSession.getMapper(EmployeeMapperResultMapDao.class);
        Employee empByIdResultMap3 = mapper.getEmpByIdResultMap3(1);
        String string = JSON.toJSONString(empByIdResultMap3, true);
        System.out.println(string);
    }
    /**
     * 测试连表查询的 并返回json
     * @throws Exception
     */
    public static void test03() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperResultMapDao mapper = sqlSession.getMapper(EmployeeMapperResultMapDao.class);
        Employee empByIdResultMap2 = mapper.getEmpByIdResultMap2(1);
        String string = JSON.toJSONString(empByIdResultMap2, true);
        System.out.println(string);
    }

    /**
     * 测试resultMap方法2
     * @throws Exception
     */
    public static void test02() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperResultMapDao mapper = sqlSession.getMapper(EmployeeMapperResultMapDao.class);
        Employee empByIdRresultMap = mapper.getEmpByIdRresultMap(1);
        System.out.println(empByIdRresultMap.getEmail());

    }

    /**
     *测试resultMap
     * @throws Exception
     */
    public static void test01()throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperResultMapDao mapper = sqlSession.getMapper(EmployeeMapperResultMapDao.class);
        Employee empById = mapper.getEmpById(1);
        System.out.println(empById.getEmail());
    }

    /**
     * 获取SqlsessionFactory对象
     * @return
     * @throws Exception
     */
    public static SqlSessionFactory getSqlSessionFactory()throws Exception{
        InputStream resourceAsReader = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsReader);
        return build;
    }

}
