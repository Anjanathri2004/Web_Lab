import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/sessionConfirmation")
public class HttpSessionConfirmationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("name");
        String cuisine = (String) session.getAttribute("cuisine");
        String quantity = (String) session.getAttribute("quantity");

        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Thank you, " + name + "</h1>");
        response.getWriter().println("<p>Your order for " + quantity + " " + cuisine + " has been placed using HttpSession.</p>");
        response.getWriter().println("</body></html>");
    }
}
