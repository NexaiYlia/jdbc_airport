package com.nexai.task4.pool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;


public class DataSource {
    private static final Logger log = LogManager.getLogger();
    private static final ReentrantLock locker = new ReentrantLock();
    private static final int MAX_CONNECTION_COUNT = 5;
    private static final int MIN_CONNECTION_COUNT = 10;
    private static final int CONNECTION_COUNT = 7;
    private static DataSource instance;
    private static boolean initialized;
    private ComboPooledDataSource comboPooledDataSource;
    private BlockingQueue<Connection> free = new LinkedBlockingQueue<>(CONNECTION_COUNT);
    private BlockingQueue<Connection> used = new LinkedBlockingQueue<>(CONNECTION_COUNT);

    private DataSource() {
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream("src/main/resources/db.properties")) {
            properties.load(inputStream);
            log.info("Connection with db is correct");
        } catch (FileNotFoundException e) {
            log.error("Connection with db is failed. File with properties not found." + e);

        } catch (IOException exception) {
            log.error("Connection with db is failed. File with properties can't be read." + exception);

        }
        comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setJdbcUrl(properties.getProperty("db.url"));
        comboPooledDataSource.setUser(properties.getProperty("db.user"));
        comboPooledDataSource.setPassword(properties.getProperty("db.password"));

        comboPooledDataSource.setMinPoolSize(MIN_CONNECTION_COUNT);
        comboPooledDataSource.setMaxPoolSize(MAX_CONNECTION_COUNT);

    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            log.error("Connection  not established." + e);
        }
        return connection;
    }

    public static DataSource getInstance() {
        if (!initialized) {
            try {
                locker.lock();
                if (instance == null) {
                    instance = new DataSource();
                }
                initialized = true;
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }
    public void destroyPool() {
        for (int i = 0; i < CONNECTION_COUNT; i++) {
            try {
                free.take().close();
            } catch (SQLException | InterruptedException e) {
                log.error("Connection can't be destroyed");
                throw new ExceptionInInitializerError(e);

            }
        }
    }
}
