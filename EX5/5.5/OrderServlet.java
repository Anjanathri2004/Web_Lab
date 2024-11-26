import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/OrderFormServlet")
public class OrderFormServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String customerName = request.getParameter("customerName");
        String customerEmail = request.getParameter("customerEmail");
        String phone = request.getParameter("phone");
        String gadgetModel = request.getParameter("gadgetModel");
        String quantity = request.getParameter("quantity");
        String sessionTracking = request.getParameter("sessionTracking");

        // Process based on the selected session tracking method
        switch (sessionTracking) {
            case "cookies":
                handleCookiesSession(request, response, customerName, gadgetModel, quantity);
                break;
            case "hiddenFields":
                handleHiddenFieldsSession(request, response, customerName, gadgetModel, quantity);
                break;
            case "urlRewriting":
                handleUrlRewritingSession(request, response, customerName, gadgetModel, quantity);
                break;
            case "httpSession":
                handleHttpSession(request, response, customerName, gadgetModel, quantity);
                break;
            default:
                response.getWriter().println("Invalid session tracking method");
        }
    }

    // Method to handle Cookies session tracking
    private void handleCookiesSession(HttpServletRequest request, HttpServletResponse response, String customerName, String gadgetModel, String quantity) throws IOException {
        Cookie nameCookie = new Cookie("customerName", customerName);
        Cookie gadgetCookie = new Cookie("gadgetModel", gadgetModel);
        Cookie quantityCookie = new Cookie("quantity", quantity);

        response.addCookie(nameCookie);
        response.addCookie(gadgetCookie);
        response.addCookie(quantityCookie);

        response.sendRedirect("cookiesConfirmation");
    }

    // Method to handle Hidden Fields session tracking
    private void handleHiddenFieldsSession(HttpServletRequest request, HttpServletResponse response, String customerName, String gadgetModel, String quantity) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<form action='hiddenFieldsConfirmation' method='post'>");
        response.getWriter().println("<input type='hidden' name='customerName' value='" + customerName + "'>");
        response.getWriter().println("<input type='hidden' name='gadgetModel' value='" + gadgetModel + "'>");
        response.getWriter().println("<input type='hidden' name='quantity' value='" + quantity + "'>");
        response.getWriter().println("<input type='submit' value='Confirm Order'>");
        response.getWriter().println("</form></body></html>");
    }

    // Method to handle URL Rewriting session tracking
    private void handleUrlRewritingSession(HttpServletRequest request, HttpServletResponse response, String customerName, String gadgetModel, String quantity) throws IOException {
        response.sendRedirect("urlRewritingConfirmation?customerName=" + customerName + "&gadgetModel=" + gadgetModel + "&quantity=" + quantity);
    }

    // Method to handle HttpSession session tracking
    private void handleHttpSession(HttpServletRequest request, HttpServletResponse response, String customerName, String gadgetModel, String quantity) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("customerName", customerName);
        session.setAttribute("gadgetModel", gadgetModel);
        session.setAttribute("quantity", quantity);

        response.sendRedirect("sessionConfirmation");
    }
}
