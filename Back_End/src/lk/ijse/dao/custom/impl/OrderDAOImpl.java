package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.entity.Order;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:41 PM
 **/

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Order> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Order> allOrders = new ArrayList<>();
        ResultSet rst = CrudUtil.execute(connection, "SELECT * FROM Orders");
        while (rst.next()) {
            Order order = new Order(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5));
            allOrders.add(order);
        }
        return allOrders;
    }

    @Override
    public boolean save(Connection connection, Order entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "INSERT INTO Orders (oId,cusID,date,subTotal,discount) VALUES (?,?,?,?,?)", entity.getOId(), entity.getCusID(), entity.getDate(), entity.getSubTotal(), entity.getDiscount());
    }

    @Override
    public boolean update(Connection connection, Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection, "SELECT oid FROM Orders ORDER BY oid DESC LIMIT 1");
        if (result.next()) {
            return result.getString(1);
        } else {
            return null;
        }
    }
}
