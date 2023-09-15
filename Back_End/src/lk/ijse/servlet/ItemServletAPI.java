package lk.ijse.servlet;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.util.ResponseUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author : Chavindu
 * created : 8/24/2023-3:41 PM
 **/
@WebServlet(urlPatterns = {"/pages/item"})
public class ItemServletAPI extends HttpServlet {
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection = pool.getConnection()) {

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();

            ArrayList<ItemDTO> all = itemBO.getAllItems(connection);

            for (ItemDTO itemDTO : all) {
                JsonObjectBuilder item = Json.createObjectBuilder();

                item.add("code", itemDTO.getCode());
                item.add("description", itemDTO.getDescription());
                item.add("unitPrice", itemDTO.getUnitPrice());
                item.add("qty", itemDTO.getQty());

                allCustomers.add(item.build());
            }

            resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allCustomers.build()));
        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("item-ID");
        String description = req.getParameter("item-Name");
        double price = Double.parseDouble(req.getParameter("item-Price"));
        int qty = Integer.parseInt(req.getParameter("item-Quantity"));

        ServletContext servletContext = getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection = pool.getConnection()) {
            ItemDTO itemDTO = new ItemDTO(code, description, price, qty);

            if (itemBO.saveItem(connection, itemDTO)) {
                resp.getWriter().print(ResponseUtil.genJson("OK", "Successfully Added.!"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("item-ID");

        ServletContext servletContext = getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection = pool.getConnection()) {
            boolean deleteCustomer = itemBO.deleteItem(connection, code);
            if (deleteCustomer) {
                resp.getWriter().print(ResponseUtil.genJson("OK", "Successfully Deleted !"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String code = jsonObject.getString("itemCode");
        String description = jsonObject.getString("itemDescription");
        double price = Double.parseDouble(jsonObject.getString("unitPrice"));
        int qty = Integer.parseInt(jsonObject.getString("qtyOnHand"));

        ServletContext servletContext = getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection = pool.getConnection()) {

            ItemDTO itemDTO = new ItemDTO(code, description, price, qty);
            if (itemBO.updateItem(connection, itemDTO)) {
                resp.getWriter().print(ResponseUtil.genJson("OK", "Successfully Updated !"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }

    }

}
