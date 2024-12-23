import java.util.Scanner;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TempratureConvert{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> history = new ArrayList<>();

        // Print header
        printHeader();

        while (true) {
            // Get user input
            System.out.print("Enter Tempraturet(C,F,c,f): ");
            String tempInput = scanner.nextLine().trim();

            if (tempInput.equalsIgnoreCase("exit")) {
                // Exit the program
                System.out.println("Goodbye!");
                break;
            }

            try {
                // Convert to lowercase
                tempInput = tempInput.toLowerCase();

                // Extract value and unit
                double value = Double.parseDouble(tempInput.substring(0, tempInput.length() - 1));
                char unit = tempInput.charAt(tempInput.length() - 1);

                // Perform conversion
                String result = performConversion(value, unit);

                // Get current time
                String timeStamp = getCurrentTime();

                // Save history with timestamp
                String historyEntry = timeStamp + " - " + result;
                history.add(historyEntry);

                // Display result
                printDivider();
                System.out.println(result);
                printDivider();

            } catch (Exception e) {
                printError("Invalid format! Add 'C' for Celsius or 'F' for Fahrenheit.");
            }

            // Option to show history
            System.out.print("Show history? (y/n): ");
            String showHistory = scanner.nextLine().trim();
            if (showHistory.equalsIgnoreCase("y")) {
                printHistory(history);
            }
        }

        scanner.close();
    }

    // Print header
    private static void printHeader() {
        printDivider();
        System.out.println("Welcome To Temperature Converter");
        printDivider();
    }

    // Print divider
    private static void printDivider() {
        System.out.println("--------------------------------");
    }

    // Print error message
    private static void printError(String message) {
        printDivider();
        System.out.println("ERROR: " + message);
        printDivider();
    }

    // Perform conversion
    private static String performConversion(double value, char unit) {
        String result = "";
        if (unit == 'c') {
            double fahrenheit = (value * 9 / 5) + 32;
            result = String.format("%.2fC = %.2fF", value, fahrenheit);
        } else if (unit == 'f') {
            double celsius = (value - 32) * 5 / 9;
            result = String.format("%.2fF = %.2fC", value, celsius);
        } else {
            result = "ERROR: Add 'C' for Celsius or 'F' for Fahrenheit.";
        }
        return result;
    }

    // Get current time in HH:MM:SS format
    private static String getCurrentTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(formatter);
    }

    // Print history
    private static void printHistory(ArrayList<String> history) {
        if (history.isEmpty()) {
            System.out.println("No conversions yet.");
        } else {
            System.out.println("History:");
            for (String record : history) {
                System.out.println(record);
            }
        }
        printDivider();
    }
}

/* Type "exit"/"Exit" to exit the code*/
