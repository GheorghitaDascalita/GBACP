package fii.student.gbacpapp;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "register", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/registerView.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Connection conn = null;
        try {
                conn = DatabaseConnection.getConnection();
                conn.setAutoCommit(false);

                if(ModelUtils.insertUser(conn, request.getParameter("username"),
                        request.getParameter("password")) == true) {
                        response.sendRedirect(request.getContextPath() + "/login");
                }
                else {
                        doGet(request, response);
                }

                conn.commit();
        } catch (Exception e) {
                e.printStackTrace();
                DatabaseConnection.rollback(conn);
                throw new ServletException();
        } finally {
                DatabaseConnection.closeConnection(conn);
        }
    }

}
