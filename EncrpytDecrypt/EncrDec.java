import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;
import java.util.Scanner;

public class EncrDec{

    private static final String AES = "AES";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Encrypt a file");
        System.out.println("2. Decrypt a file");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        if (choice != 1 && choice != 2) {
            System.out.println("Invalid option.");
            return;
        }

        System.out.print("Enter the file path: ");
        String filePath = scanner.nextLine();

        System.out.print("Enter the output file path: ");
        String outputFilePath = scanner.nextLine();

        System.out.print("Enter the secret key (16 characters): ");
        String secretKey = scanner.nextLine();

        if (secretKey.length() != 16) {
            System.out.println("Error: The secret key must be 16 characters long.");
            return;
        }

        try {
            if (choice == 1) {
                encryptFile(filePath, outputFilePath, secretKey);
                System.out.println("File encrypted successfully. Saved to " + outputFilePath);
            } else {
                decryptFile(filePath, outputFilePath, secretKey);
                System.out.println("File decrypted successfully. Saved to " + outputFilePath);
            }
        } catch (Exception e) {
            System.err.println("Error during processing: " + e.getMessage());
        }
    }

    private static void encryptFile(String inputFilePath, String outputFilePath, String secretKey) throws Exception {
        processFile(inputFilePath, outputFilePath, secretKey, Cipher.ENCRYPT_MODE);
    }

    private static void decryptFile(String inputFilePath, String outputFilePath, String secretKey) throws Exception {
        processFile(inputFilePath, outputFilePath, secretKey, Cipher.DECRYPT_MODE);
    }

    private static void processFile(String inputFilePath, String outputFilePath, String secretKey, int cipherMode) throws Exception {
        SecretKey key = new SecretKeySpec(secretKey.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(cipherMode, key);

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             FileOutputStream fos = new FileOutputStream(outputFilePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    fos.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                fos.write(outputBytes);
            }
        }
    }
}
