package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {


    public void execute(String sql) {
        try(PreparedStatement ps = executeUpdate(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement executeUpdate(String sql) {
        PreparedStatement ps = null;
        try {
            Connection connection = Util.getMySQLConnection();
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }
}
