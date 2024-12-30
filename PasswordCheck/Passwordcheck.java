import java.util.*;
import java.util.regex.*;
import java.security.SecureRandom;

public class Passwordcheck {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final List<String> COMMON_PASSWORDS = Arrays.asList(
        "123456", "password", "12345678", "qwerty", "letmein", "welcome", "123456789"
    );
    
    private static final Set<String> BLACKLISTED_PASSWORDS = new HashSet<>(Arrays.asList(
        "password123", "admin123", "qwerty123", "123abc", "letmein1", "trustno1"
    ));

    private static final List<String> PASSWORD_HISTORY = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // -------------------------------
        // USER INPUT SECTION
        // -------------------------------
        System.out.println("\n----------------------------------------");
        
        System.out.println(" Ultra-Secure Password Strength Checker ");
        System.out.println("----------------------------------------");
        System.out.print("Enter a password to check its strength: ");
        String password = scanner.nextLine();

        System.out.print("Enter your username (optional): ");
        String username = scanner.nextLine();

        // -------------------------------
        // PASSWORD ANALYSIS SECTION
        // -------------------------------
        // Analyze password strength
        String analysis = analyzePassword(password, username);
        System.out.println(analysis);

        // -------------------------------
        // PASSWORD SUGGESTION SECTION
        // -------------------------------
        // Generate strong password suggestions
        if (!isStrongPassword(password)) {
            System.out.println("\nRecommended Strong Passwords:");
            for (int i = 0; i < 3; i++) {
                System.out.println(" - " + generateSecurePassword(16));
            }
        }

        scanner.close();
    }

    // -------------------------------
    // PASSWORD ANALYSIS METHODS
    // -------------------------------
    private static String analyzePassword(String password, String username) {
        int score = 0;
        StringBuilder feedback = new StringBuilder();
        
        feedback.append("Password Analysis:\n");

        // Length check
        if (password.length() < 12) {
            feedback.append("Weak: Password is too short. Minimum length should be 12 characters.\n");
        } else if (password.length() > 64) {
            feedback.append("Weak: Password is too long. Maximum length should be 64 characters.\n");
        } else {
            score += 2;
        }

        // Character variety
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecialChar = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));

        if (!hasUpperCase) {
            feedback.append("Weak: Add at least one uppercase letter.\n");
        } else {
            score += 2;
        }
        if (!hasLowerCase) {
            feedback.append("Weak: Add at least one lowercase letter.\n");
        } else {
            score += 2;
        }
        if (!hasDigit) {
            feedback.append("Weak: Include at least one numeric digit.\n");
        } else {
            score += 2;
        }
        if (!hasSpecialChar) {
            feedback.append("Weak: Add at least one special character (e.g., !, @, #).\n");
        } else {
            score += 3;
        }

        // Common password check
        if (COMMON_PASSWORDS.contains(password.toLowerCase())) {
            feedback.append("Critical: Your password is too common.\n");
        }

        // Check against blacklisted passwords
        if (BLACKLISTED_PASSWORDS.contains(password.toLowerCase())) {
            feedback.append("Critical: This password is on the blacklist.\n");
        }

        // Levenshtein Distance Check (detects slight variations of common passwords)
        for (String common : COMMON_PASSWORDS) {
            if (levenshteinDistance(password.toLowerCase(), common) <= 2) {
                feedback.append("Weak: Password is too similar to a common password.\n");
                break;
            }
        }

        // Dictionary word check (detects simple dictionary words or predictable patterns)
        if (containsDictionaryWord(password)) {
            feedback.append("Weak: Avoid using dictionary words.\n");
        }

        // Check for password reuse (cross-checking against previous passwords)
        if (PASSWORD_HISTORY.contains(password)) {
            feedback.append("Critical: Password has been used before.\n");
        }

        // Check for common patterns
        if (hasSequentialPattern(password)) {
            feedback.append("Weak: Avoid sequential characters (e.g., 1234, abcd).\n");
        }

        // Check for entropy
        double entropy = calculateEntropy(password);
        feedback.append(String.format("Entropy Score: %.2f bits.\n", entropy));
        if (entropy < 50) {
            feedback.append("Weak: Password entropy is too low.\n");
        } else if (entropy >= 50 && entropy < 75) {
            feedback.append("Moderate: Password entropy is acceptable.\n");
            score += 2;
        } else {
            feedback.append("Strong: Password entropy is high.\n");
            score += 4;
        }

        // Policy compliance
        if (!isMFAEnabled(password)) {
            feedback.append("Recommendation: Enable Multi-Factor Authentication (MFA) for added security.\n");
        }

        // Final feedback based on score
        if (score < 10) {
            feedback.append("Final Verdict: Very Weak Password.\n");
        } else if (score < 15) {
            feedback.append("Final Verdict: Weak Password.\n");
        } else if (score < 20) {
            feedback.append("Final Verdict: Moderate Password.\n");
        } else {
            feedback.append("Final Verdict: Strong Password.\n");
        }

        return feedback.toString();
    }

    // -------------------------------
    // HELPER METHODS
    // -------------------------------
    private static boolean containsDictionaryWord(String password) {
        String[] dictionary = {"password", "welcome", "dragon", "football"};
        for (String word : dictionary) {
            if (password.toLowerCase().contains(word)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasSequentialPattern(String password) {
        String pattern = "(012|123|234|345|456|567|678|789|abc|bcd|cde|def)";
        return password.toLowerCase().matches(".*" + pattern + ".*");
    }

    private static double calculateEntropy(String password) {
        int variety = 0;
        if (password.chars().anyMatch(Character::isUpperCase)) variety += 26;
        if (password.chars().anyMatch(Character::isLowerCase)) variety += 26;
        if (password.chars().anyMatch(Character::isDigit)) variety += 10;
        if (password.chars().anyMatch(c -> !Character.isLetterOrDigit(c))) variety += 32;

        return password.length() * (Math.log(variety) / Math.log(2));
    }

    private static boolean isStrongPassword(String password) {
        return password.length() >= 12 && calculateEntropy(password) >= 60;
    }

    // -------------------------------
    // PASSWORD SUGGESTION METHODS
    // -------------------------------
    private static String generateSecurePassword(int length) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()-_=+[]{}|;:,.<>?";
        String allChars = upper + lower + digits + special;

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(allChars.charAt(RANDOM.nextInt(allChars.length())));
        }

        return password.toString();
    }

    private static boolean isMFAEnabled(String password) {
        // Placeholder for actual MFA check. In real-world, this would integrate with authentication systems.
        return true;
    }

    // -------------------------------
    // UTILITY METHODS
    // -------------------------------
    private static int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }
}
