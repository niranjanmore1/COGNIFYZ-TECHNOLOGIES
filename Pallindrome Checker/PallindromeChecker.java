import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class PallindromeChecker {

    // Class to store history entries
    static class HistoryEntry {
        String input;
        String result;
        String timestamp;

        public HistoryEntry(String input, String result, String timestamp) {
            this.input = input;
            this.result = result;
            this.timestamp = timestamp;
        }
    }

    // Function to check if a given string is a palindrome
    public static boolean isPalindrome(String input) {
        if (input == null || input.isEmpty()) {
            return false; // Handle null or empty strings
        }

        // Remove spaces, punctuation, and convert to lowercase
        String cleanedString = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        // Check if cleaned string is a palindrome
        int left = 0, right = cleanedString.length() - 1;

        while (left < right) {
            if (cleanedString.charAt(left) != cleanedString.charAt(right)) {
                return false; // Early exit if a mismatch is found
            }
            left++;
            right--;
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<HistoryEntry> history = new ArrayList<>();
        HashMap<String, HistoryEntry> historyMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("===============================================================");
        System.out.println("                 Welcome to the Palindrome Checker   ");
        System.out.println("===============================================================");
        
        while (true) {
            System.out.println("\nEnter text (or 'exit' to quit):");
            String userInput = scanner.nextLine();

            // Exit condition
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("\n================================================================");
                System.out.println("    History of Inputs: ");
                System.out.println("================================================================");
                for (HistoryEntry entry : history) {
                    System.out.printf("%s | Input: \"%s\" | Result: %s%n",
                            entry.timestamp, entry.input, entry.result);
                }
                System.out.println("\nThank you for using the Palindrome Checker!");
                System.out.println("\n================================================================");
                break;
            }

            // Check if input was previously checked
            if (historyMap.containsKey(userInput)) {
                HistoryEntry previousEntry = historyMap.get(userInput);
                System.out.println("----------------------------------------------------------------");
                System.out.println("This input was already checked on " + previousEntry.timestamp + ".");
                System.out.println(" Result: " + previousEntry.result);
                System.out.println("----------------------------------------------------------------");
                continue;
            }

            // Check if input is a palindrome
            boolean result = isPalindrome(userInput);
            String resultText = result ? "Palindrome" : "Not a Palindrome";

            // Save the result to history with a timestamp
            String timestamp = sdf.format(new Date());
            HistoryEntry entry = new HistoryEntry(userInput, resultText, timestamp);
            history.add(entry);
            historyMap.put(userInput, entry);

            // Display results
            System.out.println("----------------------------------------------------------------");
            System.out.println(" Result: " + resultText);
            System.out.println("----------------------------------------------------------------");
        }

        scanner.close();
    }
}
