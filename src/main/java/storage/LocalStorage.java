package storage;

import architecture.AirRoute;
import architecture.Storage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class LocalStorage implements Storage {

    private final String basePath;

    public LocalStorage(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void saveAirRoute(AirRoute route) {
        try {
            String dateFolder = LocalDate.now().toString();
            Path folderPath = Path.of(basePath, "datalake", dateFolder);
            Files.createDirectories(folderPath);

            String fileName = "airroute_" + System.currentTimeMillis() + ".json";
            Path filePath = folderPath.resolve(fileName);

            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                writer.write(route.toJson());
            }

            System.out.println("💾 AirRoute guardada localmente en: " + filePath);
        } catch (IOException e) {
            System.err.println("❌ Error al guardar AirRoute localmente: " + e.getMessage());
        }
    }
}
