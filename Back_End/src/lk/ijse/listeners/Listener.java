package lk.ijse.listeners;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author : Chavindu
 * created : 9/14/2023-4:49 PM
 **/
@WebListener
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Context was created");

        ServletContext servletContext = servletContextEvent.getServletContext();
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName("com.mysql.jdbc.Driver");
        pool.setUrl("jdbc:mysql://localhost:3306/javaee_pos");
        pool.setUsername("root");
        pool.setPassword("1234");
        pool.setInitialSize(3);
        pool.setMaxTotal(3);
        servletContext.setAttribute("dbcp", pool);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Context Destroyed");
    }
}
