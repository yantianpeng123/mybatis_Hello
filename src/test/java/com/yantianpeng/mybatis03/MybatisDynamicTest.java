package com.yantianpeng.mybatis03;


import com.alibaba.fastjson.JSON;
import com.yantianpeng.Dynamic.Dao.EmployeeDao;
import com.yantianpeng.Dynamic.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MybatisDynamicTest {
    public static void main(String[] args) throws Exception {
        //test01();//测试if标签
        //test02();//测试wehre语句拼接起来某些语句会导致sql错误
      //  test03();//测试trim的使用
      // test04();//测试choose的用法
       // test05();//测试使用if更新employee
       // test06();//测试使用trim标签更新元素
        //test07();//测绘foreach 批量操作
        test08();
    }

    public static void test08() throws Exception{
        SqlSession sqlSession = getSqlSessionFactory().openSession();
        EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
        List<Employee> list = new ArrayList<Employee>();
        //employee 一号
        Employee employee =new Employee();
        employee.setName("汪洋");
        employee.setEmail("wangyang@163.com");
        employee.setD_id(2);
        employee.setGender("男");
        //employee  二号
        Employee employee1 =new Employee();
        employee1.setD_id(2);
        employee1.setGender("男");
        employee1.setName("王沪宁");
        employee1.setEmail("wanghuning@163.com");
        list.add(employee1);
        list.add(employee);
        int i = mapper.insertEmpByForeach(list);
        System.out.println(i);
        sqlSession.commit();
    }
    /**
     * 测试foreach批量查询
     * @throws Exception
     */
    public static void test07() throws Exception{
        SqlSession sqlSession = getSqlSessionFactory().openSession();
        EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
        List<Employee> empByForeach = mapper.getEmpByForeach(Arrays.asList(1, 2, 3, 4, 5));
        String string = JSON.toJSONString(empByForeach, true);
        System.out.println(string);
    }

    /**
     *  测试s使用trim标签更新元素
     * @throws Exception
     */
    public static void test06() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
        Employee employee= new Employee();
        employee.setId(1);
        employee.setName("李克强");
        employee.setEmail("likeqiong@qq.com");
        int i = mapper.updateEmpByIdTrim(employee);
        sqlSession.commit();
        System.out.println(i);
    }

    /**
     *  测试使用if更新employee
     * @throws Exception
     */
    public static void test05 () throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
        Employee employee= new Employee();
        employee.setId(1);
        employee.setName("李克强");
        employee.setEmail("likeqiong@qq.com");
        int i = mapper.updateEmpById(employee);
        System.out.println(i);
        sqlSession.commit();
    }

    /**
     *  测试choose的用法
     * @throws Exception
     */

    public static void test04()throws Exception{
        EmployeeDao mapper = getSqlSessionFactory().openSession().getMapper(EmployeeDao.class);
        Employee employee =new Employee();
        employee.setId(1);
        //employee.setName("闫天蓬");
        List<Employee> empByChoose = mapper.getEmpByChoose(employee);
        String string = JSON.toJSONString(empByChoose, true);
        System.out.println(string);
    }
    /**
     * 使用trim解决某些sql拼接错误
     * @throws Exception
     */
    public static void test03() throws Exception{
        EmployeeDao mapper = getSqlSessionFactory().openSession().getMapper(EmployeeDao.class);
        Employee employee = new Employee();
        employee.setId(3);
        employee.setName("闫天蓬");
        employee.setEmail("yantianpeng@163.com");
        List<Employee> empByContionTrim = mapper.getEmpByContionTrim(employee);
        String string = JSON.toJSONString(empByContionTrim, true);
        System.out.println(string);

    }

    /**
     *  测试where语句的拼接是会出来某些语句带有and的导致sql错误的现象
     * @throws Exception
     */
    public static void test02() throws Exception{
        EmployeeDao mapper = getSqlSessionFactory().openSession().getMapper(EmployeeDao.class);
        Employee employee = new Employee();
        employee.setName("闫天蓬");
     //   employee.setId(1);
      //  employee.setEmail("yantianpeng@163.com");
        List<Employee> empByWhere = mapper.getEmpByWhere(employee);
        String string = JSON.toJSONString(empByWhere, true);
        System.out.println(string);
    }

    /**
     *测试if标签
     * @throws Exception
     */
    public static void test01 () throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
        Employee employee = new Employee();
       employee.setD_id(1);
        employee.setId(1);
        employee.setName("闫天蓬");
        List<Employee> empByIdList = mapper.getEmpByIdList(employee);
        String string = JSON.toJSONString(empByIdList, true);
        System.out.println(string);
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
