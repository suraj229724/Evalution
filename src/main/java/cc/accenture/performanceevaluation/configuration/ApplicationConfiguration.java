/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.configuration;

import cc.accenture.performanceevaluation.security.applicationListener.BadCredentialsEvent;
import cc.accenture.performanceevaluation.security.applicationListener.SuccessEvent;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 *
 * @author Suraj
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = {"WEB-INF/jdbc.properties"})
@ComponentScan(basePackages = {"cc.accenture.performanceevaluation.dao", "cc.accenture.performanceevaluation.service", "cc.accenture.performanceevaluation.aop"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApplicationConfiguration implements TransactionManagementConfigurer {

    @Autowired
    private Environment environment;

    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url.data"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        dataSource.setMaxActive(environment.getProperty("jdbc.maxActive", Integer.class));
        dataSource.setMinIdle(environment.getRequiredProperty("jdbc.maxIdle", Integer.class));
        dataSource.setMaxWait(environment.getRequiredProperty("jdbc.maxWait", Integer.class));
        dataSource.setInitialSize(environment.getRequiredProperty("jdbc.initialSize", Integer.class));
        dataSource.setRemoveAbandoned(environment.getRequiredProperty("jdbc.removeAbandoned", Boolean.class));
        dataSource.setRemoveAbandonedTimeout(environment.getRequiredProperty("jdbc.removeAbandonedTimeout", Integer.class));
        dataSource.setLogAbandoned(environment.getRequiredProperty("jdbc.logAbandoned", Boolean.class));
        dataSource.setTestOnReturn(environment.getRequiredProperty("jdbc.testOnReturn", Boolean.class));
        dataSource.setValidationQuery(environment.getRequiredProperty("jdbc.validationQuery"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager();
    }

    @Bean(name = "badCredentialsEvent")
    public BadCredentialsEvent badCredentialsEvent() {
        return new BadCredentialsEvent();
    }

    @Bean(name = "successEvent")
    public SuccessEvent successEvent() {
        return new SuccessEvent();
    }

    @Bean(name = "settings")
    public static PropertiesFactoryBean mapper() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("settings.properties"));
        return bean;
    }
}
