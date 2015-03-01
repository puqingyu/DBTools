<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN" "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >  
<generatorConfiguration>  
    <!-- 引入配置文件 -->  
    <properties resource="generator.properties"/>  
      
    <!-- 一个数据库一个context -->  
    <context id="dev" targetRuntime="MyBatis3">  
        <!-- 注释 -->  
        <commentGenerator >  
            <property name="suppressAllComments" value="true"/><!-- 是否取消注释 -->  
            <property name="suppressDate" value="true" /> <!-- 是否生成注释代时间戳-->  
        </commentGenerator>  
          
        <!-- jdbc连接 -->  
        <jdbcConnection driverClass="${jdbc_driver}"  
            connectionURL="${jdbc_url}" userId="${jdbc_user}"  
            password="${jdbc_password}" />  
          
        <!-- 类型转换 -->  
        <javaTypeResolver>  
            <property name="forceBigDecimals" value="false"/>  
        </javaTypeResolver>  
          
        <javaModelGenerator targetPackage="${model_package}"  
            targetProject="${project}" >  
            <property name="enableSubPackages" value="false"/>  
            <property name="trimStrings" value="true"/>  
        </javaModelGenerator>  
          
        <sqlMapGenerator targetPackage="${mapper_dir}"  
            targetProject="${resource}" > 
            <property name="enableSubPackages" value="false" />  
        </sqlMapGenerator>  
          
        <javaClientGenerator targetPackage="${dao_package}" implementationPackage="${dao_impl_package}" 
            targetProject="${project}" type="XMLMAPPER" >  
            <property name="enableSubPackages" value="false" />  
        </javaClientGenerator>  
          
  		{{{tables}}}
    </context>  
</generatorConfiguration>  