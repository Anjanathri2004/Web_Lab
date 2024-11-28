import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/VisitCounterServlet")
public class VisitCounterServlet extends HttpServlet {

    private int globalCounter;

    @Override
    public void init() throws ServletException {
        // Initialize the global counter to 0
        globalCounter = 0;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set response content type to plain text for easy JavaScript parsing
        response.setContentType("text/plain; charset=UTF-8");
        HttpSession session = request.getSession(true);
        Integer userCounter = (Integer) session.getAttribute("userCounter");

        if (userCounter == null) {
            // First-time visitor
            userCounter = 1;
        } else {
            // Increment for returning visitors
            userCounter++;
        }
        session.setAttribute("userCounter", userCounter);
        try (PrintWriter out = response.getWriter()) {
            out.println(userCounter); // Output only the visit count
        }
    }
}