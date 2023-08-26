package lk.ijse.servlet;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author : Chavindu
 * created : 8/24/2023-3:42 PM
 **/
@WebServlet(urlPatterns = {"/pages/purchase"})
public class PurchaseOrderServletAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaee_pos", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("SELECT oid FROM Orders ORDER BY oid DESC LIMIT 1");
            ResultSet rst = pstm.executeQuery();

            resp.addHeader("Access-Control-Allow-Origin", "*");

            resp.addHeader("Content-Type", "application/json");


            JsonArrayBuilder allOrders = Json.createArrayBuilder();
            while (rst.next()) {
                String oId = rst.getString(1);

                JsonObjectBuilder orderIdObject = Json.createObjectBuilder();
                orderIdObject.add("oid", oId);


                allOrders.add(orderIdObject.build());
            }

            resp.getWriter().print(allOrders.build());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String code = jsonObject.getString("itemCode");
        String description = jsonObject.getString("itemDescription");
        String price = jsonObject.getString("unitPrice");
        String qty = jsonObject.getString("qtyOnHand");

        resp.addHeader("Access-Control-Allow-Origin", "*");

        resp.addHeader("Content-Type", "application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaee_pos", "root", "1234");

            PreparedStatement pstm3 = connection.prepareStatement("update Item set description=?,unitPrice=?,qty=? where code=?");
            pstm3.setObject(4, code);
            pstm3.setObject(1, description);
            pstm3.setObject(2, price);
            pstm3.setObject(3, qty);
            if (pstm3.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("state", "OK");
                response.add("message", "Successfully Updated ! ");
                response.add("data", "");
                resp.setStatus(200);
                resp.getWriter().print(response.build());
            }

        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state", "Error");
            objectBuilder.add("message", e.getMessage());
            objectBuilder.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "PUT");
        resp.addHeader("Access-Control-Allow-Headers", "content-type");
    }
}
