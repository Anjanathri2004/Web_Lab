import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cookiesConfirmation")
public class CookiesConfirmationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerName = null, gadget = null, quantity = null;

        // Retrieve cookies and extract relevant data
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "customerName":
                        customerName = cookie.getValue();
                        break;
                    case "gadget":
                        gadget = cookie.getValue();
                        break;
                    case "quantity":
                        quantity = cookie.getValue();
                        break;
                }
            }
        }

        // Generate response
        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        if (customerName != null && gadget != null && quantity != null) {
            response.getWriter().println("<h1>Thank you, " + customerName + "</h1>");
            response.getWriter().println("<p>Your order for " + quantity + " " + gadget + "(s) has been placed using Cookies.</p>");
        } else {
            response.getWriter().println("<h1>Invalid Order</h1>");
            response.getWriter().println("<p>It seems like we couldn't retrieve your order details from cookies.</p>");
        }
        response.getWriter().println("</body></html>");
    }
}
