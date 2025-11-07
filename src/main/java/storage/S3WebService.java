package storage;

import architecture.Storage;
import architecture.Transaction;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class S3WebService implements Storage {

    private final String bucketName;
    private final S3Client s3;

    public S3WebService(S3Client s3Client, String bucketName) {
        this.bucketName = bucketName;
        this.s3 = s3Client;
        ensureBucketExists();
    }

    private void ensureBucketExists() {
        try {
            boolean exists = s3.listBuckets()
                    .buckets()
                    .stream()
                    .anyMatch(b -> b.name().equals(bucketName));

            if (!exists) {
                System.out.println("🪣 Bucket no encontrado, creando: " + bucketName);
                s3.createBucket(CreateBucketRequest.builder()
                        .bucket(bucketName)
                        .build());
                System.out.println("✅ Bucket creado exitosamente: " + bucketName);
            } else {
                System.out.println("✅ Bucket existente detectado: " + bucketName);
            }
        } catch (S3Exception e) {
            System.err.println("❌ Error al verificar o crear el bucket: " + e.awsErrorDetails().errorMessage());
        }
    }

    @Override
    public void saveTransaction(Transaction tx) {
        String folderName = "datalake/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String key = folderName + "/transaction_" + System.currentTimeMillis() + ".json";
        String json = tx.toJson();

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes())) {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType("application/json")
                    .build();

            s3.putObject(request, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(inputStream, json.length()));
            System.out.println("☁️ Transacción subida a S3: " + key);
        } catch (IOException e) {
            System.err.println("❌ Error al subir transacción a S3: " + e.getMessage());
        }
    }
}