package lk.ijse.servlet;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.bo.custom.OrderBO;
import lk.ijse.bo.custom.OrderDetailBO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OrderDetailDTO;
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
 * created : 8/24/2023-3:42 PM
 **/
@WebServlet(urlPatterns = {"/pages/purchase"})
public class PurchaseOrderServletAPI extends HttpServlet {
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    OrderDetailBO orderDetailBO = (OrderDetailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER_DETAILS);
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");
        ServletContext servletContext = getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        switch (option) {
            case "generateOrderID":
                try (Connection connection = pool.getConnection()) {

                    String orderId = orderBO.generateNewOrder(connection);

                    JsonObjectBuilder ordID = Json.createObjectBuilder();
                    ordID.add("orderId", orderId);
                    ordID.build();

                    resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded"));
                } catch (ClassNotFoundException | SQLException e) {
                    resp.setStatus(500);
                    resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
                }
                break;
            case "getOrders":
                try (Connection connection = pool.getConnection()) {

                    JsonArrayBuilder allOrders = Json.createArrayBuilder();

                    ArrayList<OrderDTO> all = orderBO.getAllOrders(connection);

                    for (OrderDTO orderDTO : all) {
                        JsonObjectBuilder order = Json.createObjectBuilder();

                        order.add("oid", orderDTO.getOId());
                        order.add("cusId", orderDTO.getCusID());
                        order.add("date", orderDTO.getDate());
                        order.add("subTotal", orderDTO.getSubTotal());
                        order.add("discount", orderDTO.getDiscount());

                        allOrders.add(order.build());
                    }

                    resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allOrders.build()));
                } catch (ClassNotFoundException | SQLException e) {
                    resp.setStatus(500);
                    resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
                }
                break;
            case "getOrderDetails":
                try (Connection connection = pool.getConnection()) {

                    JsonArrayBuilder allOrderDetails = Json.createArrayBuilder();

                    ArrayList<OrderDetailDTO> all = orderDetailBO.getAllOrders(connection);

                    for (OrderDetailDTO orderDetailDTO : all) {
                        JsonObjectBuilder order = Json.createObjectBuilder();

                        order.add("oid", orderDetailDTO.getOId());
                        order.add("cusId", orderDetailDTO.getCusID());
                        order.add("cusName", orderDetailDTO.getName());
                        order.add("code", orderDetailDTO.getCode());
                        order.add("description", orderDetailDTO.getDescription());
                        order.add("qty", orderDetailDTO.getQty());
                        order.add("price", orderDetailDTO.getPrice());

                        allOrderDetails.add(order.build());
                    }

                    resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allOrderDetails.build()));
                } catch (ClassNotFoundException | SQLException e) {
                    resp.setStatus(500);
                    resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
                }
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String oid = jsonObject.getString("oid");
        String cusId = jsonObject.getString("cusID");
        String date = jsonObject.getString("date");
        String subTotal = jsonObject.getString("subTotal");
        String discount = jsonObject.getString("discount");
        String name = jsonObject.getString("name");

        ServletContext servletContext = getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");


        try (Connection connection = pool.getConnection()) {

            connection.setAutoCommit(false);

            OrderDTO orderDTO = new OrderDTO(oid, cusId, date, subTotal, discount);

            boolean saveOrder = orderBO.saveOrder(connection, orderDTO);

            if (!(saveOrder)) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new RuntimeException("Order Issue");
            } else {
                JsonArray orderDetails = jsonObject.getJsonArray("orderDetails");

                for (JsonValue orderDetail : orderDetails) {
                    String code = orderDetail.asJsonObject().getString("code");
                    String description = orderDetail.asJsonObject().getString("description");
                    String qty = orderDetail.asJsonObject().getString("qty");
                    String price = orderDetail.asJsonObject().getString("price");

                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO(oid, cusId, name, code, description, qty, price);

                    boolean saveOrderDetail = orderDetailBO.saveOrderDetail(connection, orderDetailDTO);
                    if (!(saveOrderDetail)) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        throw new RuntimeException("Order Details Issue");
                    }
                }

                connection.commit();
                connection.setAutoCommit(true);

                resp.getWriter().print(ResponseUtil.genJson("OK", "Order Successfully Purchased..!"));
            }

        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
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
