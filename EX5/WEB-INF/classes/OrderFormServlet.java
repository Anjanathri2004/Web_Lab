import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/OrderFormServlet")
public class OrderFormServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data for electronic gadgets order
        String name = request.getParameter("customerName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gadget = request.getParameter("gadget");
        String quantity = request.getParameter("quantity");
        String sessionTracking = request.getParameter("sessionTracking");

        // Process based on the selected session tracking method
        switch (sessionTracking) {
            case "cookies":
                handleCookiesSession(request, response, name, gadget, quantity);
                break;
            case "hiddenFields":
                handleHiddenFieldsSession(request, response, name, gadget, quantity);
                break;
            case "urlRewriting":
                handleUrlRewritingSession(request, response, name, gadget, quantity);
                break;
            case "httpSession":
                handleHttpSession(request, response, name, gadget, quantity);
                break;
            default:
                response.getWriter().println("Invalid session tracking method");
        }
    }

    // Method to handle Cookies session tracking
    private void handleCookiesSession(HttpServletRequest request, HttpServletResponse response, String name, String gadget, String quantity) throws IOException {
        response.addCookie(new javax.servlet.http.Cookie("customerName", name));
        response.addCookie(new javax.servlet.http.Cookie("gadget", gadget));
        response.addCookie(new javax.servlet.http.Cookie("quantity", quantity));

        response.sendRedirect("cookiesConfirmation");
    }

    // Method to handle Hidden Fields session tracking
    private void handleHiddenFieldsSession(HttpServletRequest request, HttpServletResponse response, String name, String gadget, String quantity) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<form action='hiddenFieldsConfirmation' method='post'>");
        response.getWriter().println("<input type='hidden' name='customerName' value='" + name + "'>");
        response.getWriter().println("<input type='hidden' name='gadget' value='" + gadget + "'>");
        response.getWriter().println("<input type='hidden' name='quantity' value='" + quantity + "'>");
        response.getWriter().println("<input type='submit' value='Confirm Order'>");
        response.getWriter().println("</form></body></html>");
    }

    // Method to handle URL Rewriting session tracking
    private void handleUrlRewritingSession(HttpServletRequest request, HttpServletResponse response, String name, String gadget, String quantity) throws IOException {
        response.sendRedirect("urlRewritingConfirmation?customerName=" + name + "&gadget=" + gadget + "&quantity=" + quantity);
    }

    // Method to handle HttpSession session tracking
    private void handleHttpSession(HttpServletRequest request, HttpServletResponse response, String name, String gadget, String quantity) throws IOException {
        javax.servlet.http.HttpSession session = request.getSession();
        session.setAttribute("customerName", name);
        session.setAttribute("gadget", gadget);
        session.setAttribute("quantity", quantity);

        response.sendRedirect("sessionConfirmation");
    }
}
