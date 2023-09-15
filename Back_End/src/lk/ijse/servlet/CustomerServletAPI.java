package lk.ijse.servlet;

import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dto.CustomerDTO;
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
@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServletAPI extends HttpServlet {
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection = pool.getConnection()) {

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();

            ArrayList<CustomerDTO> all = customerBO.getAllCustomers(connection);

            for (CustomerDTO customerDTO : all) {
                JsonObjectBuilder customer = Json.createObjectBuilder();

                customer.add("id", customerDTO.getId());
                customer.add("name", customerDTO.getName());
                customer.add("address", customerDTO.getAddress());
                customer.add("salary", customerDTO.getSalary());

                allCustomers.add(customer.build());
            }

            resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allCustomers.build()));
        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        double cusSalary = Double.parseDouble(req.getParameter("cusSalary"));

        ServletContext servletContext = getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection = pool.getConnection()) {
            CustomerDTO customerDTO = new CustomerDTO(cusID, cusName, cusAddress, cusSalary);

            if (customerBO.save(connection, customerDTO)) {
                resp.getWriter().print(ResponseUtil.genJson("OK", "Successfully Added.!"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");

        ServletContext servletContext = getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection = pool.getConnection()) {
            boolean deleteCustomer = customerBO.deleteCustomer(connection, cusID);
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
        String cusID = jsonObject.getString("customerId");
        String cusName = jsonObject.getString("customerName");
        String cusAddress = jsonObject.getString("customerAddress");
        double cusSalary = Double.parseDouble(jsonObject.getString("customerSalary"));

        ServletContext servletContext = getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection = pool.getConnection()) {

            CustomerDTO customerDTO = new CustomerDTO(cusID, cusName, cusAddress, cusSalary);
            if (customerBO.updateCustomer(connection, customerDTO)) {
                resp.getWriter().print(ResponseUtil.genJson("OK", "Successfully Updated !"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }

    }

}
