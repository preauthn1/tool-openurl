package com.sz;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 代码生成器
 *
 * @Author MrPing
 * @Date 2021/11/5
 * @Version 1.0
 */
public class CodeGenerator {

    public static void main(String[] args) {

        // 数据库地址
        String url = "jdbc:mysql://106.12.120.247:3306/open_url?useSSL=false&useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&serverTimezone=Asia/Shanghai";
        // 数据库账号
        String username = "root";
        // 数据库密码
        String REDACTED;
        // 父路劲
        String parentValue = "com.sz.applet";


        // 需要要生成的表名
        List<String> tables = new ArrayList<>();
        tables.add("platform");
        tables.add("platform_url");



        FastAutoGenerator.create(url,username,password)
                .globalConfig(builder -> {
                    builder.author("MrPing")               //作者
                            .outputDir(System.getProperty("user.dir")+"\\src\\main\\java")    //输出路径(写到java目录)
//                            .enableSwagger()           //开启swagger
                            .commentDate("yyyy-MM-dd");
//                            .fileOverride();            //开启覆盖之前生成的文件

                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    // bigint 转 String
                    if (typeCode == Types.BIGINT) {
                        return DbColumnType.STRING;
                    }
                    // tinyint 转 Integer
                    if (typeCode == Types.TINYINT) {
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent(parentValue)
                            .moduleName("")
                            .entity("pojo.po")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .mapper("mapper")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.xml,System.getProperty("user.dir")+"\\src\\main\\resources\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            // 设置过滤表前缀如: sms_,oms_,crm_
                            .addTablePrefix("")
                            // 设置过滤字段前缀：sms,oms,crm

                            /**
                             * 开启 Service 策略配置
                             */
                            .serviceBuilder()
                            // User 文件 添加 IUserService
                            .formatServiceFileName("I%sService")
                            // User 文件 添加 UserServiceImpl
                            .formatServiceImplFileName("%sServiceImpl")

                            /**
                             * 开启 Entity 策略配置
                             */
                            .entityBuilder()
                            // 开 lombok 模型
                            .enableLombok()
                            // id 字段类型: 雪花id
                            .idType(IdType.ASSIGN_ID)
                            // 逻辑删除字段（数据库字段名）
                            .logicDeleteColumnName("del_flag")
                            // 逻辑删除字段（实体类字段名）
                            // .logicDeletePropertyName("DelFlag") 数据库和实体：二选一
                            // 开启生成实体时生成字段注解
                            .enableTableFieldAnnotation()
                            // 覆盖已生成文件
                            .enableFileOverride()

                            /**
                             * 开启 Controller 策略配置
                             */
                            .controllerBuilder()
                            // 文件名添加后缀
                            .formatFileName("%sController")
                            // 开启生成@RestController 控制器
                            .enableRestStyle()

                            /**
                             * 开启 Mapper 策略配置
                             */
                            .mapperBuilder()
                            // 设置父类
                            .superClass(BaseMapper.class)
                            // 格式化 mapper 文件名称
                            .formatMapperFileName("%sMapper")
                            // 开启 @Mapper 注解
                            .enableMapperAnnotation()
                            // 格式化 xml 实现类文件名称
                            .formatXmlFileName("%sMapper");
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
