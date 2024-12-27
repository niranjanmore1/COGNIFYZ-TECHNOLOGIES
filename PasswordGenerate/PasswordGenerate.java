import java.security.SecureRandom;
import java.util.Scanner;

public class PasswordGenerate {

    // Character pools for password generation
    private static final String NUMBERS = "0123456789";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{}|;:,.<>?/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Loop for generating multiple passwords
        while (true) {
            System.out.println("===============================");
            System.out.println("Realistic Password Generator");
            System.out.println("===============================");

            // Get user input for password length
            int length = getPasswordLength(scanner);

            // Ask for character preferences using both binary and text inputs
            boolean useNumbers = getBooleanInput(scanner, "Include numbers? (1 or yes for yes, 0 or no for no): ");
            boolean useLowercase = getBooleanInput(scanner, "Include lowercase letters? (1 or yes for yes, 0 or no for no): ");
            boolean useUppercase = getBooleanInput(scanner, "Include uppercase letters? (1 or yes for yes, 0 or no for no): ");
            boolean useSpecial = getBooleanInput(scanner, "Include special characters? (1 or yes for yes, 0 or no for no): ");

            // Generate the password using SecureRandom for better randomness
            String password = generatePassword(length, useNumbers, useLowercase, useUppercase, useSpecial);

            // Ensure the password contains at least one character from each selected category
            while (!isValidPassword(password, useNumbers, useLowercase, useUppercase, useSpecial)) {
                System.out.println("The password doesn't meet your selected requirements. Regenerating...");
                password = generatePassword(length, useNumbers, useLowercase, useUppercase, useSpecial);
            }

            // Display the password
            System.out.println("===============================");
            System.out.println("Generated Password: " + password);

            // Evaluate password strength and provide feedback
            String strength = evaluatePasswordStrength(password);
            System.out.println("Password Strength: " + strength);
            System.out.println("===============================");

            // Ask if the user wants to generate another password
            System.out.print("\nWould you like to generate another password? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();

            // Terminate if the user says no
            if (response.equals("no")) {
                System.out.println("\nThank you for using the Password Generator!");
                break;
            }
        }

        scanner.close();
    }

    // Method to get password length, ensuring it's a valid number
    private static int getPasswordLength(Scanner scanner) {
        int length = 0;
        while (true) {
            System.out.print("Enter the desired password length (e.g., 12): ");
            if (scanner.hasNextInt()) {
                length = scanner.nextInt();
                if (length >= 12) {
                    break;  // Ensure length is at least 12 characters for security
                } else {
                    System.out.println("Password length must be at least 12 characters. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
        scanner.nextLine();  // Consume the newline character left by nextInt()
        return length;
    }

    // Method to get both binary and text-based inputs
    private static boolean getBooleanInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim().toLowerCase();

        // Handle both binary and text inputs
        if (input.equals("1") || input.equals("yes")) {
            return true;
        } else if (input.equals("0") || input.equals("no")) {
            return false;
        } else {
            System.out.println("Invalid input, defaulting to 'no'.");
            return false; // Default to false if invalid input
        }
    }

    // Method to generate a password using SecureRandom for better randomness
    private static String generatePassword(int length, boolean useNumbers, boolean useLowercase, 
                                           boolean useUppercase, boolean useSpecial) {
        StringBuilder characterPool = new StringBuilder();

        if (useNumbers) characterPool.append(NUMBERS);
        if (useLowercase) characterPool.append(LOWERCASE);
        if (useUppercase) characterPool.append(UPPERCASE);
        if (useSpecial) characterPool.append(SPECIAL);

        if (characterPool.length() == 0) {
            System.out.println("Error: No character types selected!");
            return "";
        }

        SecureRandom random = new SecureRandom();  // Use SecureRandom for better randomness
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterPool.length());
            password.append(characterPool.charAt(index));
        }

        return password.toString();
    }

    // Ensure password contains at least one character from each selected category
    private static boolean isValidPassword(String password, boolean useNumbers, boolean useLowercase, 
                                           boolean useUppercase, boolean useSpecial) {
        if (useNumbers && !password.matches(".*\\d.*")) {
            return false;
        }
        if (useLowercase && !password.matches(".*[a-z].*")) {
            return false;
        }
        if (useUppercase && !password.matches(".*[A-Z].*")) {
            return false;
        }
        if (useSpecial && !password.matches(".*[!@#$%^&*()-_=+\\[\\]{}|;:,.<>?/].*")) {
            return false;
        }
        return true;
    }

    // Method to evaluate password strength
    private static String evaluatePasswordStrength(String password) {
        int lengthScore = password.length() >= 16 ? 2 : 1;
        int varietyScore = (password.matches(".*[a-z].*") ? 1 : 0) + 
                           (password.matches(".*[A-Z].*") ? 1 : 0) + 
                           (password.matches(".*\\d.*") ? 1 : 0) + 
                           (password.matches(".*[!@#$%^&*()-_=+\\[\\]{}|;:,.<>?/].*") ? 1 : 0);
        
        int totalScore = lengthScore + varietyScore;

        if (totalScore >= 5) {
            return "Strong";
        } else if (totalScore == 4) {
            return "Medium";
        } else {
            return "Weak";
        }
    }
}
