import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    private static final String JDBC_URL = "jdbc:sqlite:transactions.db";

    public String getAllTransactionsAsJson() throws SQLException {
        List<Transaction> transactions = getAllTransactions();

        // Convert transactions to JSON (You may use a library like Gson or Jackson)
        // For simplicity, we are creating a simple JSON representation here.
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (Transaction transaction : transactions) {
            jsonBuilder.append(transaction.toJson()).append(",");
        }
        if (transactions.size() > 0) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Remove the trailing comma
        }
        jsonBuilder.append("]");

        return jsonBuilder.toString();
    }

    private List<Transaction> getAllTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                        resultSet.getString("transaction_id"),
                        resultSet.getString("account_id"),
                        resultSet.getInt("amount"),
                        resultSet.getString("created_at")
                );
                transactions.add(transaction);
            }
        }

        return transactions;
    }
}
