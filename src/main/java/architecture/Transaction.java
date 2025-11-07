package architecture;

import com.google.gson.Gson;
import java.util.Random;


public class Transaction {
    private final String id;
    private final double amount;
    private final long timestamp;

    public Transaction(String id, double amount, long timestamp) {
        this.id = id;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public static Transaction generateRandomTransaction() {
        Random rand = new Random();
        return new Transaction(
                "TX-" + System.currentTimeMillis(),
                Math.round(rand.nextDouble() * 10000.0) / 100.0,
                System.currentTimeMillis()
        );
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
