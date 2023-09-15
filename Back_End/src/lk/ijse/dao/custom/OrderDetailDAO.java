package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Item;
import lk.ijse.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:41 PM
 **/

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
    boolean mangeItemQty(Connection connection, Item entity) throws SQLException, ClassNotFoundException;
}
