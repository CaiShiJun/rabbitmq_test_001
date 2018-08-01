package com.rabbitmq.service;

import java.io.IOException;
import java.sql.*;

/**
 * Created by jialianqing on 2016/10/23.
 */
public class MyJdbc {
    /**
     * 执行 UPDATE 语句, 使用 PreparedStatement
     * @param sFlag 是否是select查询
     * @param sql
     * @param args: 填写 SQL 占位符的可变参数
     */
    public static boolean executeUpdate(int sFlag, String sql, Object... args){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean flag=false;
        try {
            connection = MyJdbc.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for(int i = 0; i < args.length; i++){
                preparedStatement.setObject(i + 1, args[i]);
            }
            if(sFlag==1){
                ResultSet re=preparedStatement.executeQuery();
                if(re.next()){
                    flag=true;
                }
            }else {
               int count = preparedStatement.executeUpdate();
                if(count>0){
                    flag=true;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            MyJdbc.releaseDB(null, preparedStatement, connection);
        }
        return flag;
    }

    /**
     * 获取数据库连接的方法
     */
    public static Connection getConnection() throws IOException,
            ClassNotFoundException, SQLException {
        // 0. 读取 jdbc.properties
        /**
         * 1). 属性文件对应 Java 中的 Properties 类 2). 可以使用类加载器加载 bin 目录(类路径下)的文件
         */
       /* Properties properties = new Properties();
        File file= new File(MyJdbc.class.getResource("/jdbc.properties").getFile());
        FileInputStream inStream = new FileInputStream(file);
        properties.load(inStream);*/

        // 1. 准备获取连接的 4 个字符串: user, password, jdbcUrl, driverClass
        String user = "root";
        String password = "nbs2o13";
        String jdbcUrl = "jdbc:mysql://10.128.0.47/javatest";
        String driverClass = "com.mysql.jdbc.Driver";

        // 2. 加载驱动: Class.forName(driverClass)
        Class.forName(driverClass);

        // 3. 调用
        // DriverManager.getConnection(jdbcUrl, user, password)
        // 获取数据库连接
        Connection connection = DriverManager.getConnection(jdbcUrl, user,
                password);
        return connection;
    }

    /**
     * 释放数据库资源的方法
     *
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void releaseDB(ResultSet resultSet, Statement statement,
                                 Connection connection) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}

