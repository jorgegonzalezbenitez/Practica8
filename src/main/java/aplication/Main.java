package aplication;

import architecture.AirRoute;
import architecture.Datalake;
import architecture.Storage;

public class Main {

    public static void main(String[] args) {

        String storageMode = System.getenv().getOrDefault("STORAGE_MODE", "LOCAL");

        Storage storage = StorageFactory.createStorage(storageMode);
        Datalake datalake = new Datalake(storage);

        Runnable generatorTask = () -> {
            while (true) {
                try {
                    AirRoute route = AirRoute.generateRandomVuelo();
                    datalake.saveAirRoute(route);
                    Thread.sleep(30_000);
                } catch (InterruptedException e) {
                    System.out.println("Hilo interrumpido");
                    break;
                }
            }
        };

        new Thread(generatorTask).start();
        System.out.println("✈️ Servicio de AirRoutes iniciado en modo: " + storageMode);
    }
}
