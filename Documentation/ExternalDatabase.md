# External Database

## External database storage

### AWS RDS Instance

We run an Amazon RDS database that uses MySQL database to store historical data. This is run from [Link](db-mysql-weatherapp.c5hi3iqblfad.ap-southeast-2.rds.amazonaws.com:3306/db_mysql_weatherapp)

### Setup
We connect using a JDBC driver and using a username and password to connect to the database.
```java

    private static final String url = "jdbc:mysql://db-mysql-weatherapp.c5hi3iqblfad.ap-southeast-2.rds.amazonaws.com:3306/db_mysql_weatherapp";
    private static final String user = "admin";
    private static final String pass = "jackandharper";
    
    Connection con = DriverManager.getConnection(url, user, pass);
```

### Reading


### Writing
