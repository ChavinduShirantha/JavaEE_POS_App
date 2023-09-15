package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.OrderDetailBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDetailDTO;
import lk.ijse.entity.Item;
import lk.ijse.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:55 PM
 **/

public class OrderDetailBOImpl implements OrderDetailBO {
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public ArrayList<OrderDetailDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> allOrderDetails = new ArrayList<>();
        ArrayList<OrderDetail> all = orderDetailDAO.getAll(connection);
        for (OrderDetail o : all) {
            allOrderDetails.add(new OrderDetailDTO(o.getOId(), o.getCusID(), o.getName(), o.getCode(), o.getDescription(), o.getQty(), o.getPrice()));
        }
        return allOrderDetails;
    }

    @Override
    public boolean saveOrderDetail(Connection connection, OrderDetailDTO dto) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.save(connection, new OrderDetail(dto.getOId(), dto.getCusID(), dto.getName(), dto.getCode(), dto.getDescription(), dto.getQty(), dto.getPrice()));
    }

    @Override
    public boolean mangeItemQty(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.mangeItemQty(connection, new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQty()));
    }
}
