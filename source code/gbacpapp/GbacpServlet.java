package fii.student.gbacpapp;

import java.io.*;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.json.JSONArray;


@WebServlet(name = "gbacpForm", urlPatterns = "/gbacpForm")
public class GbacpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public GbacpServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("user") != null)
                request.getRequestDispatcher("/WEB-INF/views/gbacpFormView.jsp")
                .forward(request, response);
        else
                response.sendRedirect(request.getContextPath() + "/login");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String body = bodyToString(request);
        JSONObject obj = new JSONObject(body);
        ProblemModel p = new ProblemModel(obj);

        GbacpSolver gbacp = new GbacpSolver();
        gbacp.createSolver();
        gbacp.buildModel(p);
        int[] sol = gbacp.solve(p);

        JSONObject objResponse = new JSONObject();
        objResponse.put("solution", new JSONArray(Arrays.stream(sol).boxed().toList()));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(objResponse);
        response.getWriter().flush();
    }

    public String bodyToString(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = bufferedReader.read(charBuffer);
                // bytesRead = -1 => s-a citit tot din bufferedReader (EOF)
                while (bytesRead > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                    bytesRead = bufferedReader.read(charBuffer);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        
        return stringBuilder.toString();
    }
}
