package net.jmp.spring.java.app.configs;

/*
 * (#)JpaConfig.java    0.7.0   12/23/2024
 *
 * @author   Jonathan Parker
 *
 * MIT License
 *
 * Copyright (c) 2024 Jonathan M. Parker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.util.Properties;

import javax.sql.DataSource;

import net.jmp.spring.java.app.services.EmployeeService;

import org.springframework.beans.factory.config.BeanDefinition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.transaction.annotation.EnableTransactionManagement;

/// The Spring JPA configuration.
///
/// @version    0.7.0
/// @since      0.7.0
@Configuration
@EnableJpaRepositories(basePackages = "net.jmp.spring.java.app.repositories")
@EnableTransactionManagement
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class JpaConfig {
    /// The data source.
    private final DataSource dataSource;

    /// The constructor.
    ///
    /// @param dataSource  javax.sql.DataSource
    public JpaConfig(final DataSource dataSource) {
        super();

        this.dataSource = dataSource;
    }

    /// Create and return the entity manager factory.
    ///
    /// @return org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(this.dataSource);
        em.setPackagesToScan("net.jmp.spring.java.app.entities");

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalHibernateProperties());

        return em;
    }

    /// Create and return the transaction manager.
    ///
    /// @return org.springframework.transaction.PlatformTransactionManager
    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    /// Return a bean post processor that looks up beans in the data access layer
    /// and marks them as @Repository, which in turn triggers the
    /// translation of exceptions.  This is used as a convenience to avoid
    /// having to mark each of the various data access layer beans as @Repository.
    ///
    /// @return org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /// Return the employee service.
    ///
    /// @return  net.jmp.spring.java.app.services.EmployeeService
    @Bean
    public EmployeeService employeeService() {
        return new EmployeeService();
    }

    /// Add some additional hibernate properties.  Currently,
    /// this is to auto-create and drop the database tables.
    ///
    /// @return  java.util.Properties
    private Properties additionalHibernateProperties() {
        final Properties properties = new Properties();

        properties.setProperty("hibernate.hbm2ddl.auto", "none");

        return properties;
    }
}
