package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:54 PM
 **/

public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException;

    boolean save(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(Connection connection, String id) throws SQLException, ClassNotFoundException;
}
