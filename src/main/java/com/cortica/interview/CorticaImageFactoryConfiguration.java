package com.cortica.interview;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.DatabaseConfiguration;
import io.dropwizard.db.PooledDataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class CorticaImageFactoryConfiguration extends Configuration {

    /*@Valid
    @NotNull
    @JsonProperty("database")
    private DatabaseConfiguration database = configuration -> {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setDriverClass("org.hsqldb.jdbc.JDBCDriver");
        dataSourceFactory.setUser("omri");
        dataSourceFactory.setUrl("jdbc:hsqldb:mem:imagesDB");
        Map<String, String> databaseProperties = new HashMap<>();
        databaseProperties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        databaseProperties.put("hibernate.hbm2ddl.auto", "create-drop");
        databaseProperties.put("hibernate.show_sql", "true");
        dataSourceFactory.setProperties(databaseProperties);
        return dataSourceFactory;
    };*/

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    public PooledDataSourceFactory getDataSourceFactory() {
        return database;
    }
}
