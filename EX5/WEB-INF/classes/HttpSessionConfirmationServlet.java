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
        // Retrieve the session object
        HttpSession session = request.getSession();

        // Retrieve attributes from the session
        String customerName = (String) session.getAttribute("customerName");
        String gadget = (String) session.getAttribute("gadget");
        String quantity = (String) session.getAttribute("quantity");

        // Generate confirmation response
        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        if (customerName != null && gadget != null && quantity != null) {
            response.getWriter().println("<h1>Thank you, " + customerName + "</h1>");
            response.getWriter().println("<p>Your order for " + quantity + " " + gadget + "(s) has been placed using HttpSession.</p>");
        } else {
            response.getWriter().println("<h1>Invalid Order</h1>");
            response.getWriter().println("<p>It seems like we couldn't retrieve your order details from the session.</p>");
        }
        response.getWriter().println("</body></html>");
    }
}
