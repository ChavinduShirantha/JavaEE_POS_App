package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.OrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:55 PM
 **/

public class OrderBOImpl implements OrderBO {
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public ArrayList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> allOrders = new ArrayList<>();
        ArrayList<Order> all = orderDAO.getAll(connection);
        for (Order o : all) {
            allOrders.add(new OrderDTO(o.getOId(), o.getCusID(), o.getDate(), o.getSubTotal(), o.getDiscount()));
        }
        return allOrders;
    }

    @Override
    public String generateNewOrder(Connection connection) throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID(connection);
    }

    @Override
    public boolean saveOrder(Connection connection, OrderDTO dto) throws SQLException, ClassNotFoundException {
        return orderDAO.save(connection, new Order(dto.getOId(), dto.getCusID(), dto.getDate(), dto.getSubTotal(), dto.getDiscount()));
    }
}
