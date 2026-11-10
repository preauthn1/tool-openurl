package com.sz.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;

/**
 * @author: sz
 * @date: 2022/8/26 15:14
 */
@Slf4j
@Configuration
@MapperScan({"com.sz.admin.*.mapper", "com.sz.generator.mapper", "com.sz.applet.mapper"})
public class MapperConfiguration {
    //    @Bean
//    @Primary
//    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
//        // 设置驼峰命名法映射
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.setMapUnderscoreToCamelCase(true);
//        configuration.addInterceptor(new MybatisPlusInterceptor());
//        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setConfiguration(configuration);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*/*.xml"));
//        return bean;
//    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 防止全表更新插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return interceptor;
    }
}
