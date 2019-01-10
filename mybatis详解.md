

#               mybatis笔记

### mybatis入门

+ mybatis是支持定制sql，储存过程以及高级映射的持久层框架。
+ Mybatis 避免几乎所有的JDBC代码和手动设置参数以及获取结果集。
+ Mybatis 可以使用简单的XML或者注解用于配置和原始映射，将接口和Java的POJO映射成数据库中的记录。
+ Mybatis 是一个半自动化的持久层框架。
+ JDBC
  + SQl夹杂在代码中。耦合度高导致硬编码内伤。
  + 维护不容易在实际的开发中需求中sql是有变化的，频繁修改的情况多见。

+ Mybatis下载地址：

  + [GitHupMybatis下载地址](https://github.com/mybatis/mybatis-3/)  

+ Mybatis入门实例：

  创建一张测试表

  (```)

  ```
  create table tab1_employee(
    id int(11) primary key ,# 主键id
    last_name varchar(20), # 姓名
    gender char(1),#性别 男或者女
    email varchar(255)# 邮箱
  );
  ```

  (```)

​    创建对应的实体类

​	

```
@Data
public class Employee {
    private Integer id;
    private String name;
    private String gender;
    private String email;
}
```

创建Mybatis全局配置文件：

​	MyBatis-config.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://127.0.0.1:3306/mybatisTest"/>
                <property name="username" value="root"/>
                <property name="password" value="Yantianpeng@123_"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!--Mybatis映射文件-->
        <!--
            写好的sql映射文件注册到全局配置文件当中
        -->
        <mapper resource = "com/yantianpeng/MybatisHello/empMapper/empMapper.xml"/>
        <mapper resource = "com/yantianpeng/MybatisHello/empMapper/employeeDaoMapper.xml"/>
    </mappers>
</configuration>
```

配置pom文件：

​	

```
<!--Mybatis依赖Jar-->
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.6</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.4</version>
    <scope>provided</scope>
</dependency>
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.47</version>
</dependency>
```

配置sql映射文件：

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yantianpeng.MybatisHello.empMapper">
    <!--
        namespace:命名空间 暂时可以随便命名(后面会讲到该命名空间的作用)
        id:唯一表示
        resultType:返回值类型
        #{id}: 表示从传递过来的参数取值。
    -->
    <select id="selectemp" resultType="com.yantianpeng.MybatisHello.entity.Employee">
    select id,last_name as name , gender,email from tab1_employee where id = #{id}
  </select>
</mapper>
```

获取SqlsessionFactory对象

```
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
```

编写测试方法1：

```
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
```

第二种实现方式(接口式编程)：

+ 接口：

  ​	

  ```
  public interface EmployeeMapperDao {
  
       Employee getemployeeById(Integer id);
  }
  ```

+ 配置EmployeeMapperDao.xml文件。

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yantianpeng.MybatisHello.dao.EmployeeMapperDao">
    <!--
        namespace:命名空间 指定接口的全类名
        id:唯一表示 指定接口中方法名
        resultType:返回值类型
        #{id}: 表示从传递过来的参数取值。
        注意事项：需要在mybatis-config配置文件中注册改XML文件
   -->
    <select id="getemployeeById" resultType="com.yantianpeng.MybatisHello.entity.Employee">
       select id,last_name as name , gender,email from tab1_employee where id = #{id}
    </select>
</mapper>
```

+ 编写测试方法：

  ```
  /**
   * 接口式编程
   * @throws Exception
   */
  public static void test02() throws Exception {
  //先获取SqlSeeionFactory
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
  ```

+ 注意事项：

  + ```
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
    ```

### Mybatis全局配置文件详解：

+ properties：
  + 这些属性都是可以在外部配置且可动态替换的，既可以在典型的Java属性文件中配置，也可以通过properties元素的字元素来传递。

配置db.properties文件：

```
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/mybatisTest
jdbc.user=root
jdbc.password =Yantianpeng@123_
```

在mybatis-config配置文件修改如下：

![Mybatis配置文件](/Users/yantianpeng/Desktop/741546578739_.pic_thumb的副本.jpg)



其他的代码不改变的。

+ setting设置：

+ ```
  <!--  
  	包含很多重要的参数
  	name:设置项
  	value:设置值
  -->
  <settings>
      <setting name="" value=""/>
  </settings>
  ```

+ ### typeAliases 别名设置

  - 类型别名表示为某个Java类型设置一个短的名字；

    ```
    <!--
        typeAliases 别名处理器 为某个java类型起别名
                type：表示该类的全类名
                alias：表示该类的类名
    -->
    <typeAliases>
        <typeAlias type="com.yantianpeng.MybatisHello.entity.Employee" alias="emp"/>
    </typeAliases>
    ```

- ```
  <!--
      typeAliases 别名处理器 为某个java类型起别名
      package:为某个包下面的所有的类批量起别名：
              name：指定包名,(为当前包以及包下面的所有的类都起一个默认的别名。(此别名是类名的小写。))
  -->
  <typeAliases>
      <package name=""/>
      <!--<typeAlias type="com.yantianpeng.MybatisHello.entity.Employee" alias="emp"/>-->
  </typeAliases>
  ```

- 使用@Alias() 注解起别名可以避免别名冲突。

- ### typeHandlers类型处理器：

  - 无论是mybatis在预处理语句中设置参数，还是从结果集中取出一个值时。都会用到类型处理器将获取的值已合适的方式转换成Java类型。

- ### plugins

  - Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)(执行处理器)
  - ParameterHandler (getParameterObject, setParameters)(参数处理器)
  - ResultSetHandler (handleResultSets, handleOutputParameters)(结果集处理器)
  - StatementHandler (prepare, parameterize, batch, update, query(预编译处理器)

- ### 配置环境（environments）

  - 可以配置多种环境。(使用environment标签)，配置一个环境的具体的信息，必须有两个标签，

  - id表示当前环境的唯一表示。可以用来区分测试环境和开发环境的区别。

  - ```
    <environment id="">
    <!--
     transactionManager:主要用来配置事务管理器，type配置事务管理器的类型。
     JDBC – 这个配置就是直接使用了 JDBC 的提交和回滚设置，它依赖于从数据源得到的连接来管理事务作用域。
     MANAGED – 这个配置几乎没做什么。它从来不提交或回滚一个连接，而是让容器来管理事务的整个生命周期（比如 JEE 应用服务器的上下文）。 默认情况下它会关闭连接，然而一些容器并不希望这样，因此需要将 closeConnection 属性设置为 false 来阻止它默认的关闭行为。
     <transactionManager type="MANAGED">
      <property name="closeConnection" value="false"/>
    </transactionManager>
    **注意：**：
    	事务管理和数据源一般配置在Spring中。作为了解就可以了。
    	dataSource：
    		type=”[UNPOOLED|POOLED|JNDI]”
    		也可以使用自定义数据源：type自定全类名。
    -->
        <transactionManager type=""></transactionManager>
        <dataSource type=""></dataSource>
    </environment>
    ```

+ ### databaseIdProvider

  1. MyBatis 可以根据不同的数据库厂商执行不同的语句，这种多厂商的支持是基于映射语句中的 `databaseId` 属性.

+ ### mappers(映射器)

  1. SQL映射注册到全局配置文件中

     ```
     
     <mappers>
     <!-- 使用相对于类路径的资源引用 -->
       <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
        <!-- 使用完全限定资源定位符（URL） -->
       <mapper url="file:///var/mappers/AuthorMapper.xml"/>
         <!-- 使用class的值必须是接口实现类的全类名，
         	1.假如注册失败，可以sql映射文件的文件名和接口保持一致，并且放在同一个目录下面。
         	2.不存在sql映射文件的时候，所有的sql文件都写在接口上面
         -->
         <mapper class="接口实现类的全类名"/>
     </mappers>
     ```

```
批量注册,必须把sql映射文件和接口文件同名，并且在同一个下面。
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

### Mybatis--映射文件

+ 映射文件指导着Mybatis如何进行数据库增删改查，有着非常重要的意义。

+ cache—命名空间的耳机缓存配置。

+ cache-ref--其他命名空间缓存配置的引用。

+ resultMap-- 自定义结果集映射。

+ ~~paramterMap--已废弃不建议使用。老式的参数风格。~~

+ sql— 抽取可重用的语句块。

+ insert —映射插入的语句。

+ update —映射更新语句。

+ delete--映射删除语句。

+ select--映射查询语句。

  ```
  /**
   * 增加一个员工 不需要在sql映射文件中配置的返回值里面添加东西
   * @param employee
   * @return
   */
  boolean addEmp(Employee employee);
  ```

```
<!--
    添加一个员工
-->
    <insert id="addEmp" parameterType="com.yantianpeng.MybatisHello.entity.Employee">
        insert into tab1_employee (last_name,gender,email)
        values (#{name},#{gender},#{email})
    </insert>
```

```
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
```

```
    <!--
    根据员工编号更新员工信息
-->
    <update id="updateemp">
        update tab1_employee
        set last_name = #{name},
        gender =#{gender},
        email =#{email}
        where id =#{id}
    </update>
    <!--
        根据员工编号删除员工
    -->
    <delete id="delEmp">
         delete from tab1_employee where id = #{id}
    </delete>
```

**注意事项：**

 1. Mybatis允许增删改直接定义一下类型返回值：

    - Integer Long Boolean void

    - 需要我们手动提交事务。

      ```
      sqlSessionFactory.openSession()====>>>这种open打开方式需要手机提交。
      sqlSessionFactory.openSession(true)====>>>这种open打开方式需要自动提交事务。
      ```

2. 获取自增主键：

   ```
   <!--
       添加员工并返回该员工的编号
         useGeneratedKeys="true" :使用自增主键获取主键策略。
         keyProperty="id"：指定对应的主键属性，也就是mybatis获取到主键之后，将这个值封装给JavaBean的的那个属性。
         可以获取自增主键 返回给方法。
   -->
   <insert id="addEmpAndGetId" parameterType="com.yantianpeng.MybatisHello.entity.Employee"
           useGeneratedKeys="true" keyProperty="id">
       insert  into tab1_employee (id,last_name,gender,email)
       values (#{id},#{name},#{gender},#{email})
   </insert>
   ```

```
<!--
    oracle中不支持自增主键，Oracle使用序列来模拟自增，每次插入的数据的主键都是从序列里面获取的。
    selectKey:需要自增的主键 order =  after / before 表示在sql语句执行之前开始执行还是在sql语句执行之后开始执行。
-->
<insert id="addEmpOrcl" parameterType="com.yantianpeng.MybatisHello.entity.Employee">
  <selectKey keyProperty="id" order="BEFORE" resultType="Integer">
      <!--  编写查询主键的sql  keyProperty 指定主键标示-->
     select employee_SEQ.nextval from dual
   </selectKey>
     insert  into tab1_employee (id,last_name,gender,email)
     values (#{id},#{name},#{gender},#{email})
 </insert>
```

### MYbatis参数化：

+ 单个参数：mybatis不会做特殊处理。

  + 井{参数名},Mybatis不会做特殊处理。

+ 多个参数：Mybatis会对参数进行特殊处理，多个参数会被封装成map

  + key value.....	

  + sql参数取值的话，要从map里面进行取值，param1，param2...等等。

    方法：

    ```
    /**
     * 通过Id和name查找用户
     * @param id
     * @param name
     * @return
     */
    Employee  getEmployeeByNameAndId(Integer id,String name);
    ```

sql:

```
<!--
        根据ID和Name查询用户信息
        sql:映射取值，param1，param2等等取值。
-->
<select id="getEmployeeByNameAndId" resultType="com.yantianpeng.MybatisHello.entity.Employee">
    select id,last_name as name , gender,email from tab1_employee where id = #{param1} and last_name =#{param2}
</select>
```

+ 使用@Param("id"):在sql中可以指定参数

  + 明确参数指定封装参数是map的key@param("id")value:参数值。

  方法

  ```
  /**
   * 通过name和id查找用户
   * @param name
   * @param id
   * @return
   */
  Employee getEmployeeByIdAndName(@Param("name") String name, @Param("id") Integer id);
  ```

Sql:

```
<select id="getEmployeeByIdAndName" resultType="com.yantianpeng.MybatisHello.entity.Employee">
    select id,last_name as name , gender,email from tab1_employee
     where id = #{id} and last_name =#{name}
</select>
```

如果存在多个参数正好是业务逻辑的数据原型，我们就可以直接对应的JavaBean实体。

使用井{属性名}

如果多个参数不是我们业务逻辑的数据原型，我们可以直接传入一个map

井{key}:就是直接传入的参数值：

方法：

```
/**
 * 测试使用参数map
 * @param map
 * @return
 */
Employee getemployeeByMap(Map<String,Object> map);
```

sql：取值直接取出来map里面的数据。

```
<select id="getemployeeByMap" resultType="com.yantianpeng.MybatisHello.entity.Employee">
    select id,last_name as name , gender,email from tab1_employee
     where id = #{id} and last_name =#{name}
</select>
```

如果多个参数不是业务模型中的数据，但是经常被使用，推荐编写一个TO(Transfer Object)数据传输对象。(常用的分页查询)

page{

int index;

int start;

int limit;

​	}

---

public Employee getEmp(@Param("id") int id ,String name);

​		sql取值：id = #{id/param1}, last_name = #{param2}

public Employee getEmp(int id ,@Param("emp") Employee emplyee);

​		sql取值：id=#{id},last_name = #{emp.name}

如果是Collection(List,Set)类型或者数组也会进行特殊处理，也是把传入的list或者数组封装在map中,

​	key:Collection(List,Set)类型或者数组，如果是list还可以使用这个key(list),数组(array)

public Employee getEmp(List<int> ids);

​	sql取值：取出第一个id的值======>>>>#{list[0]}

***

\\#{}：可以获取map中的值或者pojo对象的属性值

${}:可以获取map中的值或者pojo对象的属性值

​	区别：\#{}是按照预编译的形式，将参数设置到SQL语句当中，PrepareStament，是个占位符

${}:取出的值直接拼装在sql语句中，会有安全问题

大多数情况下使用\#{}.

原生的sql不支持占位符的地方我们就可以使用${}进行取值。

比如分表,排序操作：(使用这种方式可以动态的生成表名)

​	select * from ${year_}salary where XXX;

​	select * from tab1_employee order by ${f_name} \${order}

****

\#{}取值更丰富的用法：

+ 规定参数的一些规则：

  javaType、jdbcType、mode(存储过程)、numericScale、
  resultMap、typeHandler、jdbcTypeName、expression(在未来的版本中会用到)	

  - jdbcType：通常在某种特定的条件下设置 在我们数据为null时不能识别mybatis对null的默认处理，比如oracle数据库,JdbcType=OTEHER,无效的类型，因为mybatis对所有的null映射都是原生的Jdbc的OTHER类型，

  - 由于全局配置中：jdbcTypeForNull=OTHER，oracle不支持。

    ​	处理方案：

    ​		1.#{email,jdbcType=OTHER}; 指定JdbcType类型。

    ​		2.jdbcTypeForNull =NULL；

+ 返回值是多条的数据：

  + 方法：

    ```
    /**
     * 根据姓名查询
     * @param name
     * @return
     */
    List<Employee> getListEmpByName (@Param("name") String name);
    ```

  - Sql：

  - ```
    <!--
        根据名字返回多个对象和实体
    
        resultType:返回实体的实体。不是List
    -->
        <select id="getListEmpByName" resultType="com.yantianpeng.MybatisHello.entity.Employee">
            select id ,last_name as name ,gender email from tab1_employee where last_name = #{name}
        </select>
    ```

 返回到是map对象：

```
/**
 * 返回值是Map的取值
 * @return
 */
Map<String,Object> getMapEmpById(Map<String ,Object> map);
```

sql：

```
<!--
    返回值是Map的封装方式
-->
    <select id="getMapEmpById" resultType="map">
        select id ,last_name as name ,gender email  from tab1_employee where  id=#{id};
    </select>
```

返回的map里面封装多个对象：

```
/**
 * 返回值是多个时候封装到Map里面
 * 使用对象的id作为map的key值，需要时用MapKey指定map中key的取值
 * MapKey
 * @param name
 * @return
 */
@MapKey("id")
Map<Integer,Employee> getMapEmployeeByList(@Param("name") String name);
```

SQL:

```
<!--

    返回值是map封装多个对象
-->
    <select id="getMapEmployeeByList" resultType="com.yantianpeng.MybatisHello.entity.Employee">
        select id ,last_name as name ,gender email  from tab1_employee where  last_name=#{name};
    </select>
```

+ resultMap的用法：

  方法：

  ```
  /**
   * 测试resultMap 方法2
   * @param id
   * @return
   */
  Employee getEmpByIdRresultMap(Integer id);
  ```

sql:

```
<!--
    测试resultMap方法2
    该处使用自定义的realutMap
-->
<select id="getEmpByIdRresultMap" resultMap="Myempfirst">
    select id ,last_name as name ,gender ,email from tab1_employee
         where id = #{id}
</select>
```

resultMap:

```
<!--
    自定义某个JavaBean的封装规则
    type：自定义规则的Java
    id：唯一id方便引用
-->
<resultMap id="Myempfirst" type="com.yantianpeng.MybatisHello.entity.Employee">
            <!--
                指定主键ID
                column ： 指定具体的那一列
                property： 指定对应JavaBean属性
            -->
             <id column="id" property="id"></id>
    <!--定义普通类的封装规则-->
            <result column="last_name" property="name"/>
    <!--其他不指定的列会自动封装，我们只要写resultMap就把全部的映射规则都写上。-->
            <result column="gender" property="gender" />
            <result column="email" property="email"/>
</resultMap>
```

方法:

```
   <select id="getEmpByIdResultMap2" resultMap="MyDepEmp">
select t1e.id as id,t1e.gender as gender ,t1e.email as email,
      t1e.last_name,t1D.departmentName from tab1_employee t1e  left join tab1_Department t1D
   on t1e.d_id = t1D.id  where t1e.id =#{id}
   </select>
```

sql:

```
   <select id="getEmpByIdResultMap2" resultMap="MyDepEmp">
select t1e.id as id,t1e.gender as gender ,t1e.email as email,
      t1e.last_name,t1D.departmentName from tab1_employee t1e  left join tab1_Department t1D
   on t1e.d_id = t1D.id  where t1e.id =#{id}
   </select>
```

resultMap:

```
<!--
    联合查询：级连属性封装结果集
      <result column="departmentName" property="department.departmentName"/>

-->
<resultMap id="MyDepEmp" type="com.yantianpeng.MybatisHello.entity.Employee">
    <id column="id" property="id"/>
    <result column="gender" property="gender"/>
    <result column="email" property="email"/>
    <result column="last_name" property="name"/>
    <result column="departmentName" property="department.departmentName"/>
</resultMap>
```

使用association 配合联合查询：

方法：

```
/**
 * 测试连表查询3使用association 标签
 * @param id
 * @return
 */
Employee getEmpByIdResultMap3(Integer id);
```

Sql:

```
<select id="getEmpByIdResultMap3" resultMap="mydifEmp2">
  select t1e.id as id,t1e.gender as gender ,t1e.email as email,
   t1e.last_name,t1D.departmentName from tab1_employee t1e  left join tab1_Department t1D
on t1e.d_id = t1D.id  where t1e.id =#{id}
</select>
```

resultMap:

```
<resultMap id="mydifEmp2" type="com.yantianpeng.MybatisHello.entity.Employee">
    <id column="id" property="id"/>
    <result column="gender" property="gender"/>
    <result column="last_name" property="name"/>
    <result column="email" property="email"/>
    <!--
        association:可以指定联合JavaBean对象。
        property：指定那个是属性是联合的对象。
        JavaType: 指定这个对象的类型。 不可以省的掉
    -->
    <association property="department" javaType="com.yantianpeng.MybatisHello.entity.Department">
        <result column="departmentName" property="departmentName"/>
    </association>
</resultMap>
```

​	使用association实现分步查询：

​	1.先根据员工编号查询员工信息：

方法：

```
/**
 * 根据员工编号查询ID，分步进行查询
 * @param id
 * @return
 */
Employee getEmpBystep(Integer id);
```

Sql:

```
<!--
    association：实现分步查询；先查询出员工的d_id 再根据d_id查询该员工的所在的部门
-->
    <select id="getEmpBystep" resultMap="MyEmp">
        select * from tab1_employee where id = #{id}
    </select>
```

根据查询的员工信息，查询查询员工所在的部门信息。

```
/**
 * 根据部门查询ID
 * @param id
 * @return
 */
Department getDepById(Integer id);
```

sql:

```
<!--
    根据id查询部门
-->
<select id="getDepById" resultType="com.yantianpeng.MybatisHello.entity.Department">
    select t1D.id as id,t1D.departmentName as departmentName from tab1_Department t1D
    where  t1D.id =#{id}
</select>
```

重点在这里：

```
<resultMap id="MyEmp" type="com.yantianpeng.MybatisHello.entity.Employee">
    <id column="id" property="id"/>
    <result column="last_name" property="name"/>
    <result column="gender" property="gender"/>
    <result column="email" property="email"/>
    <!--
        select ：
        表明当前属性是调用select指定的方法查出的结果。
        column:指定将那一列的值传给这个方法。
        流程：使用select指定的方法(传入column指定d_id) 查出对象
        并封装给property 指定的值
    -->
    <association property="department" select="com.yantianpeng.MybatisHello.dao.DepartmentMapperDao.getDepById"
    column="d_id"/>
</resultMap>
```

Mybatis:可以实现延迟加载(懒加载)：在需要的时候才出加载对象。

在Mybtis-config：中设置aggressiveLazyLoading. 为false；

设置lazyLoadingEnabled 为ture

+ 测试查询结果集是list的查询：

  方法：

  ```
  /**
   *  查询当前部门下面的所有的员工，并查询该员工的信息
   * @param id
   * @return
   */
  Department getDepByIdPlus(Integer id);
  ```

sql：

```
    <select id="getDepByIdPlus" resultMap="MyDep">
select t1D.id as id,
       t1D.departmentName as departmentName,
       t1e.id as eid,
       t1e.email as email,
       t1e.gender as gender,
       t1e.last_name as  name
from tab1_Department t1D
left join tab1_employee t1e
on t1D.id =t1e.d_id where t1D.id=#{id}
    </select>
```

resultMap：重点在这里

```
<resultMap id="MyDep" type="com.yantianpeng.MybatisHello.entity.Department">
    <id column="id" property="id"/>
    <result column="departmentName" property="departmentName"/>
   <!--
        collection:定义关联集合类型的属性的封装规则。
        ofType：指定集合里面元素的类型
   -->
    <collection property="employees" ofType="com.yantianpeng.MybatisHello.entity.Employee">
    <!--
        定义集合中元素的封装规则
    -->
        <id column="eid" property="id"/>
        <result column="email" property="email"/>
        <result column="gender" property="gender"/>
        <result column="name" property="name"/>
    </collection>
</resultMap>
```

输出结果：

{
​	"departmentName":"群众",
​	"employees":[
​		{
​			"email":"闫天蓬@163.com",
​			"gender":"男",
​			"id":3,
​			"name":"闫天蓬"
​		},
​		{
​			"email":"闫天蓬@163.com",
​			"gender":"男",
​			"id":4,
​			"name":"闫天蓬"
​		}
​	],
​	"id":3
}	

+ 扩展：多列的值的传递方法

   1.将多列的封装map传递。

   2.column="{kye1=column1,key2=column2}"

  3.fetchType:默认是lazy 是延迟加载，eager表示是立即加载

鉴别器的使用：

​	

```
<!--
    getEmpByIdStepDis 鉴别器的使用和分步查询的复习。
    1。先根据员工编号查询员工信息
    2。再根据要求是否查询部门信息
            部门id是4的查询查询出来部门，其他的不需要查询出来部门
-->
```

方法：

```
/**
 *  鉴别器的使用
 * @param id
 * @return
 */
Employee getEmpByIdStepDis(Integer id);
```

Sql:

```
<!--
    getEmpByIdStepDis 鉴别器的使用和分步查询的复习。
    1。先根据员工编号查询员工信息
    2。再根据要求是否查询部门信息
            部门id是3的查询查询出来部门，其他的不需要查询出来部门
-->
<select id="getEmpByIdStepDis" resultMap="MyEmpList">
    select * from tab1_employee where d_id=#{id}
</select>
```

resulTMap:(这是重点):

```
<resultMap id="MyEmpList" type="com.yantianpeng.MybatisHello.entity.Employee">
    <id column="id" property="id"/>
    <result column="last_name" property="name"/>
    <result column="gender" property="gender"/>
    <result column="email" property="email"/>
    <result column="d_id" property="d_id"/>
    <!--
        discriminator:
            column:指定判断的列名
            javaType：列值对应的Java类型
    -->
    <discriminator javaType="Integer" column="d_id">
        <case value="4" resultType="com.yantianpeng.MybatisHello.entity.Employee">
            <association property="department" select="com.yantianpeng.MybatisHello.dao.DepartmentMapperDao.getDepById"
                         column="d_id"/>
        </case>
    </discriminator>
</resultMap>
```

结果对比：
​	当d_id 为不是4的时候

{
​	"d_id":1,
​	"email":"pengdehuai@163.com",
​	"gender":"男",
​	"id":5,
​	"name":"彭德怀"
}

当d_id等于4的时候：

{
​	"d_id":4,
​	"department":{
​		"departmentName":"总理",
​		"id":4
​	},
​	"email":"zhouenlai@163.com",
​	"gender":"男",
​	"id":1,
​	"name":"周恩来"
}	

## 动态sql

Mybatis的强大特性之一便是动态sql，如果你使用的JDBC或者其他类似的框架，你就体会到根据不同的条件拼接sql的痛苦。例如拼接是要确保不能忘记添加必要的空格。还要注意去掉列表最后一个列名的逗号。利用动态sql这一特性可以彻底摆脱这种痛苦。

MyBatis 采用功能强大的基于 OGNL 的表达式来淘汰其它大部分元素

- if
- choose(when,otherwise)
- trim(where,set)
- Foreach

****

#### 测试if

1.测试根employee信息查询员工

方法：

```
/**
 * 测试动态sql
 * if的使用
 * @param employee
 * @return
 */
List<Employee> getEmpByIdList(Employee  employee);
```

Sql:

```
<!--
        测试if语句的使用。
        ognl 表达式 判断改元素是不是空值 是空值的话 不需要取值里面的元素 不是空值的话 拼接上sql
        
       where 1  =1 表示总是成立 可以在if中全部都使用and 标签不需要判断在第几位开始使用and
       
       
    <if test="">
    </if>
    -->
<select id="getEmpByIdList" resultType="com.yantianpeng.Dynamic.entity.Employee">
    select * from tab1_employee
    where  1 = 1
    <if test="id!=null">
     and id= #{id}
    </if>
    <if test="name!=null &amp;&amp; name!=&quot;&quot;">
        and last_name = #{name}
    </if>
    <if test="email!=null &amp;&amp; email.trim()!=&quot;&quot;">
        and email = #{email}
    </if>
    <if test="d_id==1 or d_id = 3">
        and d_id = #{d_id}
    </if>
</select>
```

测试方法：

​	1.假如id = 1 其他的为空

​		预期sql：select * from tab1_employee where 1 = 1 and id= ? 

2.    假如id = 1 name ='闫天蓬'

   ​	预期sql：select * from tab1_employee where 1 = 1 and id= ? and last_name = ? 

3. 假如id =1 name= '闫天蓬' d_id =1

   预期sql：select * from tab1_employee where 1 = 1 and id= ? and last_name = ? and d_id = ? 

+ 解决查询条件如果某些条件没带可能sql拼装存在问题。

  1.给where 后面带上 1=1

  2.mybatis使用where标签将所有的查询条件放到where标签里面

sql例子：

```
<select id="getEmpByWhere" resultType="com.yantianpeng.Dynamic.entity.Employee">
    select * from tab1_employee
    <where>
        <if test="id!=null">
            and id=#{id}
        </if>
        <if test="name!=null &amp;&amp; name!=&quot;&quot;">
           and  last_name =#{name}
        </if>
        <if test="email!=null &amp;&amp; email.trim()!=&quot;&quot;">
          and  email = #{email}
        </if>
    </where>
</select>
```

需要注意的是：使用where标签话只可以移除一个多出来的 and或者or

3.在某些情况下面and写在查询条件后面的话，就不能使用where标签进行解决了

可以属性trim解决后面多出的and或者or

sql语句如下：

```
<!--
    使用trim标签解决么某一些sql语句拼接在后面的情况
    prefix="":前缀trim标签体中使整个字符串后的结果，prefix给拼成的字符串加上一个前缀，
    suffix=""：后缀 给拼成的字符串加上一个后缀
    prefixOverrides=""前缀覆盖，标签题里面的整个字符串去掉多余的字符
    suffixOverrides=""后缀覆盖，去掉整个字符串后面多余的字符串
-->
```

```
<select id="getEmpByContionTrim" resultType="com.yantianpeng.Dynamic.entity.Employee">
  select * from tab1_employee
  <trim prefix="where" suffixOverrides="and">
      <if test="id!=null">
           id=#{id} and
      </if>
      <if test="name!=null &amp;&amp; name!=&quot;&quot;">
            last_name =#{name} and
      </if>
      <if test="email!=null &amp;&amp; email.trim()!=&quot;&quot;">
            email = #{email} and
      </if>
  </trim>
</select>
```

+ 测试choose的用法：

  + sql：语法规范

  + ```
    <!--
        使用choose选择查询
        如果带有id使用id查询
        如果带有name使用name查询
        如果带有email就使用email查询
        
        choose用在单选择的结构中  只能从以上的条件中选择一个
    -->
    ```

  + ```
    <select id="getEmpByChoose" resultType="com.yantianpeng.Dynamic.entity.Employee">
        select * from tab1_employee
        <where>
          <choose>
              <when test="id!=null">
                  id = #{id}
              </when>
              <when test="name!=null &amp;&amp; name!=&quot;&quot;">
                  last_name = #{name}
              </when>
              <when test="email!=null &amp;&amp; eamil!=&quot;&quot;">
                  email  = #{email}
              </when>
              <otherwise>
                  1 = 1
              </otherwise>
          </choose>
        </where>
    </select>
    ```

+ 测试update标签的使用

+ 根据id更新employee

  ​	关键的sql：语句,使用if更新选择需要更新的条件，其中需要吧都好放在千需要更新的条件的前面。

  ```
  <update id="updateEmpById" >
      update tab1_employee set
      <if test="id!=null">
          id = #{id}
      </if>
      <if test="name!=null &amp;&amp; name!=&quot;&quot;">
          ,last_name =#{name}
      </if>
      <if test="email!=null &amp;&amp; email!=&quot;&quot;">
          ,email =#{email}
      </if>
      where id=#{id}
  </update>
  ```

使用trim关键子处理该字符串中多余的字符

关键性sql：	

```
<!--
    使用trim标签更新元素
    prefix:表示在整个的trim标签题添加一个元素
    prefixOverrides；标签最前面里面的整个字符串去掉多余的字符
    suffix：表示在整个trim的体后面添加一个元素
    suffixOverrides：表示在标签最后面的的整个字符串去掉多余的字符
-->
    <update id="updateEmpByIdTrim" >
       update tab1_employee
       <trim prefix="set" suffixOverrides=",">
           <if test="id!=null">
               id = #{id},
           </if>
           <if test="name!=null &amp;&amp; name!=&quot;&quot;">
               last_name =#{name},
           </if>
           <if test="email!=null &amp;&amp; email!=&quot;&quot;">
               email =#{email},
           </if>
       </trim>
        where id=#{id}
    </update>
```

Foreach:

+ 使用foreach 批量查询元素

  方法：

  ```
  /**
   *  根据id批量查询employee
   * @param list
   * @return
   */
  List<Employee> getEmpByForeach(List<Integer> list);
  ```

关键性sql：

```
<!--
    根据id批量查询employee
    select* from tab1_employee where id in(1,2,3);
collection： 指定要遍历的集合，
        list类型的参数会特殊处理封装在mao里面，map的key就叫做list
        item：将当前遍历出来的元素赋值给指定的元素。
        separator：每个元素之间的分割符
        open：遍历所有结果最开始的一个拼接符号
        close：遍历所有结果最后面的一个结束字符
        index：索引，遍历list时index就是索引，item就是当前值，
                   遍历Map时index表示的就是map的key，item就是map的值

-->
```

```
<select id="getEmpByForeach" resultType="com.yantianpeng.Dynamic.entity.Employee">
    select* from tab1_employee where id in
  <foreach collection="list" open="(" close=")" item="item" separator=",">
     #{item}
  </foreach>
</select>
```

使用forech：批量插入元素在mysql数据库里面

+ 方法：

  + ```
    /**
     *  批量保存员工在mysql数据库里面
     * @param list
     * @return
     */
    int insertEmpByForeach(List<Employee> list);
    ```

关键性Sql：

```
 insert into tab1_employee (last_name,gender,email,d_id)
values ('习近平','男','xijinping@163.com',5),
       ('栗战书','男','lizhnshu@163.com',2),
       (),(),()...
```

```
    <!--
        批量保存员工在mysql环境中
        insert into tab1_employee (last_name,gender,email,d_id)
values ('习近平','男','xijinping@163.com',5),
       ('栗战书','男','lizhnshu@163.com',2)
       -->
    <insert id="insertEmpByForeach">
            insert into tab1_employee (last_name,gender,email,d_id) values
            <foreach collection="list" item="item" separator=",">
                (
                #{item.name},
                #{item.gender},
                #{item.email},
                #{item.d_id}
                )
            </foreach>
    </insert>
```

预期的sql：

insert into tab1_employee (last_name,gender,email,d_id) values ( ?, ?, ?, ? ) , ( ?, ?, ?, ? ) 

+ 在Oracle数据库中批量插入的

  Oracle中批量插入的语法：

  ```
  1. begin
      insert into tab1_employee (id,last_name,email,d_id)values(,,,,);
      inset into tab1_employee (id,last_name,email,d_id)values(,,,,);
     end;
  ```

```
insert into tab1_employee(id,last_name,email,d_id)
    select employee_SEQ,nextval,last_nanme,email,d_id from(
            select 'test01',test001 from dual
            union
            select 'test02' ,'test002' from dual
            union
            ...
    )
```

分别对应的在Mybatis的写法如下：

```
<foreach collection="list" item="item" open="begin" close="end;">
    insert into tab1_employee (id,last_name,email,d_id)
    values (#{item.name},#{item.email},#{item.d_id});
</foreach>
```

```
insert into tab1_employee(id,last_name,email,d_id)
select employee_SEQ,nextval,last_nanme,email,d_id from
<foreach collection="list" item="item" open="(" close=")" separator="union">
    select #{item.name} last_name,#{item.email} email ,#{item.d_id} from dual
</foreach>
```