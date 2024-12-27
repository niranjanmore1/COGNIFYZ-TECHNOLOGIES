import java.util.Scanner;

public class GradeCalc{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println("=================================================");
        System.out.println("               STUDENT GRADE CALCULATOR          ");
        System.out.println("=================================================");

        // Collect student details
        System.out.print("\nEnter Student Name: ");
        String studentName = scanner.nextLine();

        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();

        // Prompt user to enter the number of grades
        System.out.print("\nEnter the total number of grades to calculate: ");
        int numberOfGrades = scanner.nextInt();

        // Validate input for number of grades
        if (numberOfGrades <= 0) {
            System.out.println("\nInvalid input! Please restart the program with a valid number.");
            return;
        }

        // Array to store grades
        double[] grades = new double[numberOfGrades];

        // Input grades with validation
        System.out.println("\nPlease input the grades (between 0 and 100):");
        for (int i = 0; i < numberOfGrades; i++) {
            System.out.printf("Grade %d: ", i + 1);
            grades[i] = scanner.nextDouble();

            if (grades[i] < 0 || grades[i] > 100) {
                System.out.println("  [Error] Grade must be between 0 and 100. Please re-enter.");
                i--; // Re-do the current grade input
            }
        }

        // Calculate the total and average
        double total = 0;
        double highestGrade = Double.MIN_VALUE;
        double lowestGrade = Double.MAX_VALUE;

        for (double grade : grades) {
            total += grade;
            if (grade > highestGrade) highestGrade = grade;
            if (grade < lowestGrade) lowestGrade = grade;
        }
        double average = total / numberOfGrades;

        // Display report
        System.out.println("\n=================================================");
        System.out.println("               STUDENT GRADE REPORT              ");
        System.out.println("=================================================");
        System.out.println("\nStudent Details:");
        System.out.println("----------------");
        System.out.printf("Name          : %s%n", studentName);
        System.out.printf("Student ID    : %s%n", studentID);
        
        
        System.out.println("---------------");
        System.out.println("\nGrades Summary:");
        System.out.println("---------------");
        System.out.printf("Total Subjects       : %d%n", numberOfGrades);
        System.out.print("Grades Entered       : [");
        for (int i = 0; i < grades.length; i++) {
            System.out.print(grades[i]);
            if (i < grades.length - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.printf("Sum of Grades        : %.2f%n", total);
        System.out.printf("Average Grade        : %.2f%n", average);
        System.out.println("---------------------");
        System.out.println("\nPerformance Analysis:");
        System.out.println("---------------------");
        System.out.printf("Dear %s,%n", studentName);
        System.out.printf("\nYour average grade is **%.2f%%**, which indicates a ", average);
        if (average >= 90) {
            System.out.println("**strong academic performance**. Excellent work!");
        } else if (average >= 75) {
            System.out.println("**strong academic performance**. You are doing well!");
        } else if (average >= 50) {
            System.out.println("**satisfactory academic performance**. There is room for improvement.");
        } else {
            System.out.println("**poor academic performance**. Keep working hard and don't give up!");
        }

        System.out.println("\nRecommendations:");
        System.out.println("- Maintain your focus on subjects you excel in (e.g., scoring " + (int) highestGrade + ").");
        System.out.println("- Review areas with relatively lower scores (e.g., scoring " + (int) lowestGrade + ").");
        System.out.println("- Don't hesitate to reach out to instructors or peers for help in challenging topics.");

        System.out.println("\n====================================================");
        System.out.println("   Thank you for using the Student Grade Calculator!   ");
        System.out.println("====================================================");
    }
}
