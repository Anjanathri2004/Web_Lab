import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/urlRewritingConfirmation")
public class URLRewritingConfirmationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String cuisine = request.getParameter("cuisine");
        String quantity = request.getParameter("quantity");

        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Thank you, " + name + "</h1>");
        response.getWriter().println("<p>Your order for " + quantity + " " + cuisine + " has been placed using URL Rewriting.</p>");
        response.getWriter().println("</body></html>");
    }
}
