import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "TransactionHistoryServlet", urlPatterns = "/transactions")
public class TransactionHistoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Assuming you have a class handling database operations
        TransactionDAO transactionDAO = new TransactionDAO();

        try {
            // Fetch transactions from the database
            String transactionsJson = transactionDAO.getAllTransactionsAsJson();

            // Set response content type to JSON
            response.setContentType("application/json");

            // Write the JSON response to the client
            PrintWriter out = response.getWriter();
            out.print(transactionsJson);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal Server Error");
        }
    }
}
