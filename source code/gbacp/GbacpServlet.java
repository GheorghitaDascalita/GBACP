package fii.student.gbacp;

import java.io.*;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;


@WebServlet(name = "gbacpForm", urlPatterns = "/gbacpForm")
public class GbacpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GbacpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		if(session.getAttribute("user") != null)
			request.getRequestDispatcher("/WEB-INF/views/GbacpFormView.jsp").forward(request, response);
		else
			response.sendRedirect(request.getContextPath() + "/login");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
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

	    body = stringBuilder.toString();
		System.out.println("nj" + body);
		JSONObject obj = new JSONObject(body);
		int y = obj.getInt("years");
		System.out.println(y);
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    
//		ProblemModel p = new ProblemModel();
//		p.setY(Integer.parseInt(request.getParameter("years")));
//		p.setPy(Integer.parseInt(request.getParameter("year_periods")));
//		p.setA(Integer.parseInt(request.getParameter("min_credits")));
//		p.setB(Integer.parseInt(request.getParameter("max_credits")));
//		p.setC(Integer.parseInt(request.getParameter("min_courses")));
//		p.setD(Integer.parseInt(request.getParameter("max_courses")));
		
//		// bd
//		
//		GbacpSolver gbacp = new GbacpSolver();
//		gbacp.creeazaSolver();
//		gbacp.construiesteModel(p);
//		int[] sol = gbacp.rezolva(p);
//		request.setAttribute("cost", sol[p.getN()]);
//		request.getRequestDispatcher("/WEB-INF/views/solutionView.jsp").forward(request, response);
	}

}
