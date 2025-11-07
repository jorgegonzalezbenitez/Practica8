package architecture;


public class Datalake {

    private final Storage storage;

    public Datalake(Storage storage) {
        this.storage = storage;
    }

    public void saveTransaction(Transaction tx) {
        storage.saveTransaction(tx);
    }
}