package hiber.config;


import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(value = "hiber")
public class AppConfig {

   @Autowired
   private Environment env;

   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getProperty("db.driver"));
      dataSource.setUrl(env.getProperty("db.url"));
      dataSource.setUsername(env.getProperty("db.username"));
      dataSource.setPassword(env.getProperty("db.password"));
      return dataSource;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(getDataSource());
      em.setPersistenceUnitName(getClass().getSimpleName());
      em.setPersistenceProvider(new HibernatePersistenceProvider());
      em.setPackagesToScan("/hiber/model");
      em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      Properties hibernateProperties = new Properties();
      hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
      hibernateProperties.setProperty("hibernate.show_sql", "true");
      hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "validate");
      hibernateProperties.setProperty("spring.thymeleaf.mode", "HTML5");
      em.setJpaProperties(hibernateProperties);
      return em;
   }

   public Properties getHibernateProperties(){

      try {
         Properties properties= new Properties();
         InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties");
         properties.load(is);
         return properties;
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   @Bean
   public PlatformTransactionManager getTransactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
      return transactionManager;
   }
}

