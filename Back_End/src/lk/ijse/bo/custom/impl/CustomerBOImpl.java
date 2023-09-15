package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:55 PM
 **/

public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll(connection);
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getId(), c.getName(), c.getAddress(), c.getSalary()));
        }
        return allCustomers;
    }

    @Override
    public boolean save(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(connection, new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(connection, new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
    }

    @Override
    public boolean deleteCustomer(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(connection, id);
    }
}
