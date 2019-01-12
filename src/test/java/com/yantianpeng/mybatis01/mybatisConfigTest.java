package com.yantianpeng.mybatis01;

import com.yantianpeng.MybatisHello.dao.EmployeeMapperDao;
import com.yantianpeng.MybatisHello.dao.EmpolyeeMapperAnnotation;
import com.yantianpeng.MybatisHello.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

* @Description:    Mybatis测试类

* @Author:        yantianpeng

* @CreateDate:     2019/1/4 上午10:11


*/
public class mybatisConfigTest {

    public static void main(String[] args) throws Exception{
       // test01();
        // test02();
        //test03();
      // test04();//测试添加员工
       //test05();
       //test06();
       //test07();// 测试添加员工并返回该员工的编号
        //test08();//根据员工姓名和编号查询该员工
      //  test09();//测试使用@param("id"),
       // test10();//测试使用map
       // test11();//测试#和$符号的区别
      //  test12();//测试动态表名的生成。
        //test13();//测试返回值是list的数据
      //  test14();//测试返回的是map
      //  test15();// 测试Map里面返回多个对象
        test16();//使用name作为key值
    }
    /**
     * 1.根据配置文件获取SqlSessionFactory
     * 2.创建SqlSession对象；
     * 3.将sql映射文件注册在配置文件中
     * 4.写代码。
     *      通过全局配置文件得到SqlSessionFactory。
     *      使用SqlSessionFactory工厂获取到SqlSession对象 来执行增删该查。
     *      关闭session。
     * 使用唯一标示来告诉mybatis需要执行的那个sql，sql都是保存在sql映射文件当中的
     *SqlSession 和conection一样是非线程安全的 每次使用必须去获取新的实例
     *SqlSession 代表和数据库一次会话，使用完毕之后必须每次关闭
     * mapper接口没有实现类，但是mybatis会为该接口生成一个代理对象。（将接口和xml绑定在一起。）
     * 两个重要的配置文件： mybatis配置文件(包含数据库管理信息，事物管理器) 。 sql映射文件：保存了每一个sql的映射信息，
     * @throws Exception
     */
    public static void test01() throws Exception{
        SqlSessionFactory xx = getSqlSessionFactory();
        //创建Sqlsession对象 可以直接执行已经映射的SQL语句
        SqlSession sqlSession = xx.openSession();
        Employee employee = sqlSession.selectOne("selectemp","1");
        System.out.println("name:"+employee.getName());
        System.out.println("id:"+employee.getId());
        System.out.println("性别："+employee.getGender());
        System.out.println("邮箱："+employee.getEmail());
        sqlSession.close();
    }

    /**
     * 使用name作为map的key值
     * @throws Exception
     */
    public static void test16() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao mapper = sqlSession.getMapper(EmployeeMapperDao.class);
        Map<String, Employee> mapEmployeeBylistName = mapper.getMapEmployeeBylistName();
        for (Map.Entry<String,Employee> entry:mapEmployeeBylistName.entrySet()) {
            System.out.println("key="+entry.getKey()+", value="+entry.getValue());
        }
    }
    /**
     * map里面封装多个对象
     * @throws Exception
     */
    public static void test15() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao mapper = sqlSession.getMapper(EmployeeMapperDao.class);
        Map<Integer, Employee> mapEmployeeByList = mapper.getMapEmployeeByList("闫天蓬");
        for (Map.Entry<Integer,Employee> entry:mapEmployeeByList.entrySet()) {
            System.out.println("key="+entry.getKey()+", value="+entry.getValue());
        }
    }

    /**
     *  测试封装成Map的返回值
     * @throws Exception
     */
    public static void test14() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao mapper = sqlSession.getMapper(EmployeeMapperDao.class);
        Map<String,Object> map =new HashMap<String, Object>();
        map.put("id",1);
        Map<String, Object> mapEmpById = mapper.getMapEmpById(map);
        for (Map.Entry<String,Object> entry:mapEmpById.entrySet()) {
            System.out.println("key="+entry.getKey()+", value="+entry.getValue());
        }
    }
    /**
     * 封装成List返回
     * @throws Exception
     */
    public static void test13() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao mapper = sqlSession.getMapper(EmployeeMapperDao.class);
        List<Employee> list = mapper.getListEmpByName("闫天蓬");
        for (Employee emp:list) {
            System.out.println("name:"+emp.getName());
            System.out.println("id:"+emp.getId());
            System.out.println("gender:"+emp.getGender());
            System.out.println("email:"+emp.getEmail());
        }
    }

    /**
     *  动态表名的生成
     * @throws Exception
     */
    public static void test12()throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao mapper = sqlSession.getMapper(EmployeeMapperDao.class);
        Map<String,Object> map =new HashMap<String, Object>();
        map.put("tableName","tab1_employee");
        map.put("id",1);
        map.put("name","周恩来");
        Employee employeeActivity = mapper.getEmployeeActivity(map);
        System.out.println(employeeActivity.getName());
        System.out.println(employeeActivity.getGender());
        System.out.println(employeeActivity.getId());
        System.out.println(employeeActivity.getEmail());
    }

    /**
     * #号和$f符号的区别
     * @throws Exception
     */
    public static void test11() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao mapper = sqlSession.getMapper(EmployeeMapperDao.class);
        Employee employee = mapper.getemployeeByIdAndName(1, "周恩来");
        System.out.println("name:"+employee.getName());
    }
    /**
     *  测试使用map组装参数对象。
     * @throws Exception
     */
    public static void test10() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao mapper = sqlSession.getMapper(EmployeeMapperDao.class);
        Map<String ,Object> map= new HashMap<String ,Object>();
        map.put("id",1);
        map.put("name","周恩来");
        Employee employee = mapper.getemployeeByMap(map);
        System.out.println(employee.getGender());
        System.out.println(employee.getName());
    }

    /**
     * 测试参数使用@param
     * @throws Exception
     */
    public static void test09() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao mapper = sqlSession.getMapper(EmployeeMapperDao.class);
        Employee employee= mapper.getEmployeeByIdAndName("闫天蓬", 3);
        System.out.println("id:"+employee.getId());
        System.out.println("name:"+employee.getName());
    }
    /**
     * 根据id和name查询用户信息
     * @throws Exception
     */
    public static void test08() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao mapper = sqlSession.getMapper(EmployeeMapperDao.class);
        Employee employee = mapper.getEmployeeByNameAndId(1, "周恩来");
        System.out.println("name:"+employee.getName());
        System.out.println("id:"+employee.getId());
        System.out.println("email"+employee.getEmail());
        System.out.println("gener:"+employee.getGender());
    }
    /**
     *
     * 接口式编程
     * @throws Exception
     */
    public static void test02() throws Exception {
        SqlSessionFactory build = getSqlSessionFactory();
        SqlSession sqlSession = build.openSession();
        EmployeeMapperDao mapper = sqlSession.getMapper(EmployeeMapperDao.class);
        Employee employee = mapper.getemployeeById(1);
        System.out.println("name:"+employee.getName());
        System.out.println("id:"+employee.getId());
        System.out.println("性别："+employee.getGender());
        System.out.println("邮箱："+employee.getEmail());
        sqlSession.close();
    }
    public static void test07()throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao mapper = sqlSession.getMapper(EmployeeMapperDao.class);
        Employee employee = new Employee();
        employee.setEmail("maozedong@163.com");
        employee.setName("毛泽东");
        employee.setGender("男");
       //employee.setId(4);
        boolean b = mapper.addEmpAndGetId(employee);
        System.out.println("成功添加员工——"+b);
        System.out.println("-----"+employee.getId()+"----");
        sqlSession.commit();
    }

    /**
     * 根据员工编号查询员工
     * @throws Exception
     */
    public static void test03()throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpolyeeMapperAnnotation mapper = sqlSession.getMapper(EmpolyeeMapperAnnotation.class);
        Employee employee = mapper.getemployeeById(1);
        System.out.println("name:"+employee.getName());
        System.out.println("id:"+employee.getId());
        System.out.println("性别："+employee.getGender());
        System.out.println("邮箱："+employee.getEmail());
        sqlSession.close();
    }

    /**
     *  测试删除员工
     * @throws Exception
     */
    public static void test06() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao dao = sqlSession.getMapper(EmployeeMapperDao.class);
        Employee employee = new Employee();
        employee.setId(3);
        int i = dao.delEmp(employee);
        System.out.println("成功删除员工："+i+"条");
            sqlSession.commit();
    }
    /**
     * 成功修改的条数是
     * @throws Exception
     */
    public static void test05() throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao dao = sqlSession.getMapper(EmployeeMapperDao.class);
        Employee employee =new Employee();
        employee.setId(1);
        employee.setGender("男");
        employee.setName("周恩来");
        employee.setEmail("zhouenlai@163.com");
        int updateemp = dao.updateemp(employee);
        System.out.println("成功修改员工的条数是："+ updateemp);
        sqlSession.commit();
    }

    /**
     * 测试添加员工
     * @throws Exception
     */
    public static void test04()throws Exception{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapperDao mapper = sqlSession.getMapper(EmployeeMapperDao.class);
        Employee employee =new Employee();
        employee.setName("闫天蓬");
        employee.setEmail("闫天蓬@163.com");
        employee.setGender("男");
        employee.setId(4);
        boolean i = mapper.addEmp(employee);
        System.out.println("成功添加员工："+i);
        sqlSession.commit();
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
