
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Elizabeth
 */
class ConnectionManager {

    private final Connection con;
    private final Statement statement;

    ConnectionManager() throws SQLException {
        this.con = generateConnection();
        this.statement = con.createStatement();
    }

    private Connection generateConnection() throws SQLException {
        String host = "jdbc:mysql://localhost:3306/MashUpDB";
        String userName = null;
        String password = null;
        return DriverManager.getConnection(host, userName, password);

//         statement.close();
//         con.close();
//         refactor to try with resources at some point
//         look into data leaks & closing resources
    }

    Connection getCon() {
        return con;
    }

    Statement getStatement() {
        return statement;
    }
}
