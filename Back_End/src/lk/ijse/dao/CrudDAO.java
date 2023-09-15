package lk.ijse.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:38 PM
 **/

public interface CrudDAO<T> extends SuperDAO {
    ArrayList<T> getAll(Connection connection) throws SQLException, ClassNotFoundException;

    boolean save(Connection connection, T entity) throws SQLException, ClassNotFoundException;

    boolean update(Connection connection, T entity) throws SQLException, ClassNotFoundException;

    boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException;
}
