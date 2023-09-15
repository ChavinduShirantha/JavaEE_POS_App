package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:55 PM
 **/

public interface OrderBO extends SuperBO {
    ArrayList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException;

    String generateNewOrder(Connection connection) throws SQLException, ClassNotFoundException;

    boolean saveOrder(Connection connection, OrderDTO dto) throws SQLException, ClassNotFoundException;
}
