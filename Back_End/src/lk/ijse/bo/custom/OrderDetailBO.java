package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:56 PM
 **/

public interface OrderDetailBO extends SuperBO {
    ArrayList<OrderDetailDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException;

    boolean saveOrderDetail(Connection connection, OrderDetailDTO dto) throws SQLException, ClassNotFoundException;

    boolean mangeItemQty(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;
}
