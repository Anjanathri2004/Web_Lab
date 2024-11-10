import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.regex.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

@WebServlet("/WarrantyRegistrationServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
    maxFileSize = 1024 * 1024 * 10,      // 10 MB
    maxRequestSize = 1024 * 1024 * 15    // 15 MB
)
public class WarrantyRegistrationServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    // Define warranty durations based on product names
    private String determineWarrantyDuration(String productName) {
        // Example logic: Different products have different warranty durations
        // This can be customized as per requirements
        if (productName.equalsIgnoreCase("Smartphone")) {
            return "2 Years";
        } else if (productName.equalsIgnoreCase("Laptop")) {
            return "3 Years";
        } else if (productName.equalsIgnoreCase("Tablet")) {
            return "1 Year";
        } else if (productName.equalsIgnoreCase("Camera")) {
            return "2 Years";
        } else {
            return "1 Year"; // Default warranty duration
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set content type for response
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Get form parameters
            String productName = request.getParameter("product_name");
            String serialNumber = request.getParameter("serial_number");
            String purchaseDateStr = request.getParameter("purchase_date");
            String purchaseLocation = request.getParameter("purchase_location");
            String userName = request.getParameter("user_name");
            String email = request.getParameter("email");
            
            // Get the uploaded file
            Part receiptPart = request.getPart("receipt");
            String receiptFileName = getFileName(receiptPart);
            
            // Validate input fields
            StringBuilder errorMessage = new StringBuilder();
            
            if (productName == null || productName.trim().isEmpty()) {
                errorMessage.append("<p>Product Name is required.</p>");
            }
            if (serialNumber == null || serialNumber.trim().isEmpty()) {
                errorMessage.append("<p>Serial Number is required.</p>");
            }
            if (purchaseDateStr == null || purchaseDateStr.trim().isEmpty()) {
                errorMessage.append("<p>Date of Purchase is required.</p>");
            }
            if (purchaseLocation == null || purchaseLocation.trim().isEmpty()) {
                errorMessage.append("<p>Purchase Location is required.</p>");
            }
            if (userName == null || userName.trim().isEmpty()) {
                errorMessage.append("<p>User's Name is required.</p>");
            }
            if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
                errorMessage.append("<p>Valid Email Address is required.</p>");
            }
            if (receiptPart == null || receiptPart.getSize() == 0) {
                errorMessage.append("<p>Receipt upload is required.</p>");
            }
            
            // Check if the file type is valid (e.g., .jpg, .jpeg, .png, .pdf)
            if (receiptPart != null && receiptPart.getSize() > 0) {
                String fileType = getFileExtension(receiptFileName).toLowerCase();
                if (!(fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png") || fileType.equals("pdf"))) {
                    errorMessage.append("<p>Invalid file type. Please upload a .jpg, .jpeg, .png, or .pdf file.</p>");
                }
            }
            
            // If there are validation errors, display them and stop processing
            if (errorMessage.length() > 0) {
                out.println("<div style='color: red;'>" + errorMessage.toString() + "</div>");
                return;
            }
            
            // Determine warranty duration based on product name
            String warrantyDuration = determineWarrantyDuration(productName);
            
            // Save the uploaded receipt file to the server
            String uploadsDirPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadsDir = new File(uploadsDirPath);
            if (!uploadsDir.exists()) {
                uploadsDir.mkdirs();
            }
            String receiptFilePath = uploadsDirPath + File.separator + receiptFileName;
            receiptPart.write(receiptFilePath);
            
            // Save registration details to XML
            String xmlFilePath = getServletContext().getRealPath("") + File.separator + "warranty_registration.xml";
            saveToXML(xmlFilePath, productName, serialNumber, purchaseDateStr, purchaseLocation, userName, email, warrantyDuration, receiptFileName);
            
            // Display success message with registration details and receipt image
            out.println("<div style='color: green;'>");
            out.println("<h3>Warranty Registration Successful!</h3>");
            out.println("<p><strong>Product Name:</strong> " + escapeHtml(productName) + "</p>");
            out.println("<p><strong>Serial Number:</strong> " + escapeHtml(serialNumber) + "</p>");
            out.println("<p><strong>Date of Purchase:</strong> " + escapeHtml(purchaseDateStr) + "</p>");
            out.println("<p><strong>Purchase Location:</strong> " + escapeHtml(purchaseLocation) + "</p>");
            out.println("<p><strong>User Name:</strong> " + escapeHtml(userName) + "</p>");
            out.println("<p><strong>Email:</strong> " + escapeHtml(email) + "</p>");
            out.println("<p><strong>Warranty Duration:</strong> " + escapeHtml(warrantyDuration) + "</p>");
            out.println("<p><strong>Receipt:</strong></p>");
            
            // Display the receipt image if it's an image file
            String fileType = getFileExtension(receiptFileName).toLowerCase();
            if (fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png")) {
                String receiptUrl = request.getContextPath() + "/uploads/" + receiptFileName;
                out.println("<img id='receiptImage' src='" + receiptUrl + "' alt='Receipt Image'/>");
            } else {
                out.println("<p><a href='" + request.getContextPath() + "/uploads/" + receiptFileName + "' target='_blank'>View Receipt PDF</a></p>");
            }
            
            out.println("</div>");
            
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<div style='color: red;'><h3>Error during registration!</h3></div>");
        } finally {
            out.close();
        }
    }
    
    // Helper method to extract file name from Part
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                String fileName = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
                // Handle Internet Explorer file path
                return fileName.substring(fileName.lastIndexOf(File.separator) + 1);
            }
        }
        return "";
    }
    
    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    
    // Helper method to get file extension
    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex == -1) {
            return "";
        }
        return fileName.substring(lastIndex + 1);
    }
    
    // Helper method to escape HTML to prevent XSS
    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;");
    }
    
    // Helper method to save registration details to XML
    private void saveToXML(String xmlFilePath, String productName, String serialNumber, String purchaseDate, String purchaseLocation,
                          String userName, String email, String warrantyDuration, String receiptFileName) throws Exception {
        
        File xmlFile = new File(xmlFilePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc;
        
        if (xmlFile.exists()) {
            doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
        } else {
            doc = dBuilder.newDocument();
            Element rootElement = doc.createElement("warrantyRegistrations");
            doc.appendChild(rootElement);
        }
        
        // Create registration element
        Element registration = doc.createElement("registration");
        
        // Add child elements
        createElement(doc, registration, "productName", productName);
        createElement(doc, registration, "serialNumber", serialNumber);
        createElement(doc, registration, "purchaseDate", purchaseDate);
        createElement(doc, registration, "purchaseLocation", purchaseLocation);
        createElement(doc, registration, "userName", userName);
        createElement(doc, registration, "email", email);
        createElement(doc, registration, "warrantyDuration", warrantyDuration);
        createElement(doc, registration, "receiptFile", receiptFileName);
        
        // Append to root
        doc.getDocumentElement().appendChild(registration);
        
        // Write the content into XML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // Beautify the format
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
    }
    
    // Helper method to create and append child elements
    private void createElement(Document doc, Element parent, String tagName, String textContent) {
        Element elem = doc.createElement(tagName);
        elem.appendChild(doc.createTextNode(textContent));
        parent.appendChild(elem);
    }
}
