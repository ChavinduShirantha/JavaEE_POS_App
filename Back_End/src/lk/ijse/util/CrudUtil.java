package lk.ijse.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:47 PM
 **/

public class CrudUtil {
    public static <T> T execute(Connection connection, String sql, Object... args) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            pstm.setObject((i + 1), args[i]);
        }
        if (sql.startsWith("SELECT")) {
            return (T) pstm.executeQuery();
        } else {
            return (T) (Boolean) (pstm.executeUpdate() > 0);
        }
    }
}
