package com.gildedrose;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.util.IsolationLevel;
import org.apache.tomcat.util.http.parser.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Maj Datasource - Generic Datasourcer for any surported database type
 */


@Configuration
public class GildedDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(GildedDataSource.class);

    private String defPoolName = "majDatasource";
    private int defGetMinimumIdle=2;
    private int defMaxPoolSize=255;
    private int defConnectionTimeout = 28740000;
    private int defMaxLifeTime = 28740000;

    private final int defMinimumIdleTimeout = 34000;
    private final int defMinimumIdleCon = 5;


    // Set this config value to one of the values in com.zaxxer.hikari.util.IsolationLevel, eg TRANSACTION_READ_COMMITTED
    // Leave as blank for the database server default isolation level

    public GildedDataSource(){

    }

    @Bean
    public HikariDataSource getHikariDataSource() {

        LOGGER.info("Creating HikariDataSource");

        HikariConfig conf = new HikariConfig();

        String url = null;

        String HOST = "localhost";
        String USER = "gilded_user";
        String PASS = "gilded_pass";
        String DB = "gilded";

        // Provide settings.
        // Ref: http://blog.zenika.com/2013/01/30/using-tomcat-jdbc-connection-pool-in-a-standalone-environment/


        url = String.format(
                        "jdbc:mysql://%s/%s?zeroDateTimeBehavior=convertToNull&useSSL=%s"+"" +
                                "&verifyServerCertificate=%s&allowPublicKeyRetrieval=%s",
                        HOST,
                        DB,
                        false,
                        false,
                        true
        );




        conf.setJdbcUrl(url);

        if (!url.contains("user")) {
            conf.setUsername(USER);
        }
        if (!url.contains("password")) {
            conf.setPassword(PASS);
        }



        conf.setConnectionTestQuery("SELECT 1");



        conf.setPoolName("gilded");
        conf.setMinimumIdle( 10);
        conf.setMaximumPoolSize( 20);
        conf.setConnectionTimeout( 600000);
        conf.setIdleTimeout( 300000);


        HikariDataSource hds = new HikariDataSource(conf);

        return hds;

    }

}