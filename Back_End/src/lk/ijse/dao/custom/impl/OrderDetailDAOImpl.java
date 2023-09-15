package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.entity.Item;
import lk.ijse.entity.OrderDetail;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:42 PM
 **/

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public ArrayList<OrderDetail> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> allOrderDetails = new ArrayList<>();
        ResultSet rst = CrudUtil.execute(connection, "SELECT * FROM OrderDetails");
        while (rst.next()) {
            OrderDetail order = new OrderDetail(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7));
            allOrderDetails.add(order);
        }
        return allOrderDetails;
    }

    @Override
    public boolean save(Connection connection, OrderDetail entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "INSERT INTO OrderDetails (oId,cusID,name,code,description,qty,price) VALUES (?,?,?,?,?,?,?)", entity.getOId(), entity.getCusID(), entity.getName(), entity.getCode(), entity.getDescription(), entity.getQty(), entity.getPrice());
    }

    @Override
    public boolean update(Connection connection, OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean mangeItemQty(Connection connection, Item entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "UPDATE Item SET description=?, unitPrice=?, qty=? WHERE code=?", entity.getDescription(), entity.getUnitPrice(), entity.getQty(), entity.getCode());
    }
}
