package org.billing.api.datasource.config;


import org.billing.api.datasource.config.util.DataSourceConfig;
import org.billing.api.datasource.config.util.DataSourcePoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DataSourceConnectivity {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Autowired
    private DataSourcePoolConfig dataSourcePoolConfig;

    @Bean("dataSource")
    DataSource dataSource()
    {
        DriverManagerDataSource driverManagerDataSource= new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(dataSourceConfig.getDriver());
        driverManagerDataSource.setUrl(dataSourceConfig.getConnectionUrl());
        driverManagerDataSource.setUsername(dataSourceConfig.getUserName());
        driverManagerDataSource.setPassword(dataSourceConfig.getPassword());
        driverManagerDataSource.setConnectionProperties(initializePoolProperties());
        return driverManagerDataSource;
        /*DriverManagerDataSource driverManagerDataSource;
        driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:2315/platform?zeroDateTimeBehavior=convertToNull");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("sumit@2017");
        return driverManagerDataSource;*/
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql",true);
        properties.put("hibernate.id.new_generator_mappings",false);
        properties.put("hibernate.connection.zeroDateTimeBehavior", "convertToNull");
        return properties;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        // Classpath scanning of @Component, @Service, etc annotated class
        entityManagerFactory.setPackagesToScan("org.billing.api.identity.provider.entities");
        // Vendor adapter
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setJpaProperties(getHibernateProperties());
        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager =   new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory.getObject());
        return transactionManager;
    }


    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    protected Properties initializePoolProperties()
    {
        Properties properties  = new Properties();
        properties.setProperty("type",dataSourcePoolConfig.getType());
        properties.setProperty("factory",dataSourcePoolConfig.getFactory());
        properties.setProperty("initialSize",String.valueOf(dataSourcePoolConfig.getInitialSize()));
        properties.setProperty("maxWaitMillis",String.valueOf(dataSourcePoolConfig.getMaxWaitMillis()));
        properties.setProperty("maxTotal",String.valueOf(dataSourcePoolConfig.getMaxTotal()));
        properties.setProperty("maxIdle",String.valueOf(dataSourcePoolConfig.getMaxIdle()));
        properties.setProperty("minIdle",String.valueOf(dataSourcePoolConfig.getMinIdle()));
        properties.setProperty("removeAbandoned",String.valueOf(dataSourcePoolConfig.isRemoveAbandoned()));
        properties.setProperty("removeAbandonedTimeout",String.valueOf(dataSourcePoolConfig.getRemoveAbandonedTimeout()));
        properties.setProperty("validationQuery",dataSourcePoolConfig.getValidationQuery());
        properties.setProperty("validationInterval",String.valueOf(dataSourcePoolConfig.getValidationInterval()));
        properties.setProperty("testOnBorrow",String.valueOf(dataSourcePoolConfig.isTestOnBorrow()));
        properties.setProperty("timeBetweenEvictionRunsMillis",String.valueOf(dataSourcePoolConfig.getTimeBetweenEvictionRunsMillis()));
        properties.setProperty("minEvictableIdleTimeMillis",String.valueOf(dataSourcePoolConfig.getMinEvictableIdleTimeMillis()));
        properties.setProperty("useSSL",String.valueOf(dataSourcePoolConfig.isUseSSL()));
        return properties;
    }

}
