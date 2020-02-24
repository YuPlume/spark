package com.ibeifeng.sparkproject.jdbc;

import com.ibeifeng.sparkproject.conf.ConfigurationManager;
import com.ibeifeng.sparkproject.constant.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class JDBCHelper {

    static {
        try {
            Class.forName(ConfigurationManager.getProperty(Constants.JDBC_DRIVER));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private static JDBCHelper instance = null ;

    public static JDBCHelper getInstance(){
        if (instance==null){
            synchronized (JDBCHelper.class){
                if (instance==null){
                    instance = new JDBCHelper();
                }
            }
        }
        return instance;
    }

    private LinkedList<Connection> datasource = new LinkedList<Connection>();

    private JDBCHelper () {


    int datasourcesize = ConfigurationManager.getInteger(Constants.JDBC_DATASOURCE);

    for (int i=0;i<datasourcesize;i++){
       try {

           Connection conn = DriverManager.getConnection(ConfigurationManager.getProperty(Constants.JDBC_URL),ConfigurationManager.getProperty(Constants.JDBC_NAME),ConfigurationManager.getProperty(Constants.JDBC_PASSWORD));
           datasource.push(conn);

       } catch (Exception e){
           e.printStackTrace();
       }
    }

    }

    public synchronized Connection getConnection(){
            while (datasource.size()==0){
                try {
                    Thread.sleep(1000);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        return datasource.poll();
    }


}
