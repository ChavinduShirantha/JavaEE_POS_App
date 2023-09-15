package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.entity.Customer;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:40 PM
 **/

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = CrudUtil.execute(connection, "SELECT * FROM Customer");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean save(Connection connection, Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "INSERT INTO Customer (id,name, address,salary) VALUES (?,?,?,?)", entity.getId(), entity.getName(), entity.getAddress(), entity.getSalary());
    }

    @Override
    public boolean update(Connection connection, Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "UPDATE Customer SET name=?, address=?,salary=? WHERE id=?", entity.getName(), entity.getAddress(), entity.getSalary(), entity.getId());
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "DELETE FROM Customer WHERE id=?", id);
    }
}
