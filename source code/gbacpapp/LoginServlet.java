package fii.student.gbacpapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;


@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.getRequestDispatcher("/WEB-INF/views/loginView.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Connection conn = null;
            try {
                    conn = DatabaseConnection.getConnection();
                    conn.setAutoCommit(false);

                    UserModel user = ModelUtils.findUser(conn, request.getParameter("username"),
                    request.getParameter("password"));
                    if(user != null) {
                            HttpSession session = request.getSession();
                            session.setAttribute("user", user);
                            response.sendRedirect(request.getContextPath() + "/gbacpForm");
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
