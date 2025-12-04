package architecture;

import com.google.gson.Gson;

import java.util.Random;

public class AirRoute {

    private final String codigoVuelo;
    private final String origen;
    private final String destino;
    private final int duracionMinutos;
    private final double precio;
    private final String aerolinea;
    private final long timestamp;

    public AirRoute(String codigoVuelo, String origen, String destino,
                 int duracionMinutos, double precio, String aerolinea, long timestamp) {

        this.codigoVuelo = codigoVuelo;
        this.origen = origen;
        this.destino = destino;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
        this.aerolinea = aerolinea;
        this.timestamp = timestamp;
    }

    public static AirRoute generateRandomVuelo() {
        Random rand = new Random();

        String[] aerolineas = {"Iberia", "Vueling", "Ryanair", "Air Europa", "Lufthansa"};
        String[] aeropuertos = {"MAD", "BCN", "LPA", "TFN", "BIO", "AGP"};

        String origen = aeropuertos[rand.nextInt(aeropuertos.length)];
        String destino = aeropuertos[rand.nextInt(aeropuertos.length)];
        while (destino.equals(origen)) {
            destino = aeropuertos[rand.nextInt(aeropuertos.length)];
        }

        return new AirRoute(
                "FL-" + System.currentTimeMillis(),
                origen,
                destino,
                30 + rand.nextInt(300),
                Math.round(rand.nextDouble() * 50000.0) / 100.0,
                aerolineas[rand.nextInt(aerolineas.length)],
                System.currentTimeMillis()
        );
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
