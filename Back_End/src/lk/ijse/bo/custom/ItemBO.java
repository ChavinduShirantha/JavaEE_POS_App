package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:55 PM
 **/

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException;

    boolean saveItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteItem(Connection connection, String code) throws SQLException, ClassNotFoundException;
}
