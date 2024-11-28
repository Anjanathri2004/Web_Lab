import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/URLRewritingConfirmation")
public class URLRewritingConfirmationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve URL parameters
        String customerName = request.getParameter("customerName");
        String gadget = request.getParameter("gadget");
        String quantity = request.getParameter("quantity");

        // Generate confirmation response
        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Thank you, " + customerName + "</h1>");
        response.getWriter().println("<p>Your order for " + quantity + " " + gadget + "(s) has been placed using URL Rewriting.</p>");
        response.getWriter().println("</body></html>");
    }
}
