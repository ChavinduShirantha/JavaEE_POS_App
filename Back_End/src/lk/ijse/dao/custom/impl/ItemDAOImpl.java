package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.entity.Item;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:41 PM
 **/

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = CrudUtil.execute(connection, "SELECT * FROM Item");
        while (rst.next()) {
            Item item = new Item(rst.getString("code"), rst.getString("description"), rst.getDouble("unitPrice"), rst.getInt("qty"));
            allItems.add(item);
        }
        return allItems;
    }

    @Override
    public boolean save(Connection connection, Item entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "INSERT INTO Item (code,description,unitPrice,qty) VALUES (?,?,?,?)", entity.getCode(), entity.getDescription(), entity.getUnitPrice(), entity.getQty());
    }

    @Override
    public boolean update(Connection connection, Item entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "UPDATE Item SET description=?, unitPrice=?, qty=? WHERE code=?", entity.getDescription(), entity.getUnitPrice(), entity.getQty(), entity.getCode());
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "DELETE FROM Item WHERE code=?", id);
    }
}
