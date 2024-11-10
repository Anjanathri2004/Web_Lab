import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.*;
import java.util.regex.*;

@MultipartConfig // Add this annotation to handle file uploads
public class WarrantyRegistrationServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set content type for response
        response.setContentType("text/html");

        // Get form parameters
        String productName = request.getParameter("product_name");
        String serialNumber = request.getParameter("serial_number");
        String purchaseDate = request.getParameter("purchase_date");
        String purchaseLocation = request.getParameter("purchase_location");
        String userName = request.getParameter("user_name");
        String email = request.getParameter("email");
        String warrantyDuration = request.getParameter("warranty_duration");
        
        // Get the uploaded file
        Part receiptPart = request.getPart("receipt");

        PrintWriter out = response.getWriter();
        
        // Validate input fields
        if (productName == null || productName.isEmpty()) {
            out.println("<p>Product Name is required.</p>");
            return;
        }
        if (serialNumber == null || serialNumber.isEmpty()) {
            out.println("<p>Serial Number is required.</p>");
            return;
        }
        if (purchaseDate == null || purchaseDate.isEmpty()) {
            out.println("<p>Date of Purchase is required.</p>");
            return;
        }
        if (purchaseLocation == null || purchaseLocation.isEmpty()) {
            out.println("<p>Purchase Location is required.</p>");
            return;
        }
        if (userName == null || userName.isEmpty()) {
            out.println("<p>User's Name is required.</p>");
            return;
        }
        
        // Validate email format
        if (email == null || email.isEmpty() || !isValidEmail(email)) {
            out.println("<p>Valid Email Address is required.</p>");
            return;
        }
        
        // Validate warranty duration
        if (warrantyDuration == null || warrantyDuration.isEmpty()) {
            out.println("<p>Warranty Duration is required.</p>");
            return;
        }
        
        // Validate receipt upload (file upload validation)
        if (receiptPart == null || receiptPart.getSize() == 0) {
            out.println("<p>Receipt upload is required.</p>");
            return;
        }
        
        // Check if the file type is valid (for example, .jpg, .jpeg, .png, .pdf)
        String fileName = receiptPart.getSubmittedFileName();
        String fileType = getFileExtension(fileName).toLowerCase();
        if (!fileType.equals("jpg") && !fileType.equals("jpeg") && !fileType.equals("png") && !fileType.equals("pdf")) {
            out.println("<p>Invalid file type. Please upload a .jpg, .jpeg, .png, or .pdf file.</p>");
            return;
        }
        
        // If all validations pass, proceed to process the data
        out.println("<h3>Warranty Registration Successful!</h3>");
        out.println("<p>Product: " + productName + "</p>");
        out.println("<p>Serial Number: " + serialNumber + "</p>");
        out.println("<p>Purchase Date: " + purchaseDate + "</p>");
        out.println("<p>Location: " + purchaseLocation + "</p>");
        out.println("<p>User Name: " + userName + "</p>");
        out.println("<p>Email: " + email + "</p>");
        out.println("<p>Warranty Duration: " + warrantyDuration + "</p>");
        out.println("<p>Receipt: " + fileName + "</p>");

        // You can save the data to the database or perform further processing here

    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to get file extension
    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return ""; // No extension
        }
        return fileName.substring(lastIndexOfDot + 1);
    }
}
