package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:55 PM
 **/

public class ItemBOImpl implements ItemBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll(connection);
        for (Item i : all) {
            allItems.add(new ItemDTO(i.getCode(), i.getDescription(), i.getUnitPrice(), i.getQty()));
        }
        return allItems;
    }

    @Override
    public boolean saveItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(connection, new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQty()));
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(connection, new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQty()));
    }

    @Override
    public boolean deleteItem(Connection connection, String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(connection, code);
    }
}
