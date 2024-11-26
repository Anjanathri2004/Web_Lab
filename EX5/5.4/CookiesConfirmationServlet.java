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
        String name = null, cuisine = null, quantity = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            switch (cookie.getName()) {
                case "name":
                    name = cookie.getValue();
                    break;
                case "cuisine":
                    cuisine = cookie.getValue();
                    break;
                case "quantity":
                    quantity = cookie.getValue();
                    break;
            }
        }

        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Thank you, " + name + "</h1>");
        response.getWriter().println("<p>Your order for " + quantity + " " + cuisine + " has been placed using Cookies.</p>");
        response.getWriter().println("</body></html>");
    }
}
