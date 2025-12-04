package aplication;

import architecture.Storage;
import storage.LocalStorage;
import storage.S3WebService;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;

public class StorageFactory {

    public static Storage createStorage(String mode) {
        if ("S3".equalsIgnoreCase(mode)) {
            S3Client s3Client = S3Client.builder()
                    .region(Region.US_EAST_1)
                    .build();

            String bucketName = "datalake-" +
                    java.time.LocalDate.now()
                            .format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE);

            return new S3WebService(s3Client, bucketName);
        } else {
            return new LocalStorage("storage");
        }
    }
}
