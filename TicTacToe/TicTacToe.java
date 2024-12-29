import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToe {
    private static final char EMPTY = ' ';
    private static final int SIZE = 3;
    private static char[][] board = new char[SIZE][SIZE];
    private static char currentPlayer = 'X';
    private static int scorePlayer1 = 0;
    private static int scorePlayer2 = 0;
    private static String player1Name;
    private static String player2Name;
    private static ArrayList<String> moveHistory = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------------------------------------");
        System.out.println("         Welcome to Tic-Tac-Toe         ");
        System.out.println("----------------------------------------");

        // Get player names
        System.out.print("Enter Player 1's name (X): ");
        player1Name = scanner.nextLine();
        System.out.print("Enter Player 2's name (O): ");
        player2Name = scanner.nextLine();

        boolean playAgain = true;

        while (playAgain) {
            System.out.println("\n--------------- New Round ---------------");
            initializeBoard();
            moveHistory.clear();
            boolean gameWon = false;

            System.out.println("\nLet the game begin!");
            while (!isBoardFull() && !gameWon) {
                printBoard();
                System.out.println("Turn: " + getCurrentPlayerName() + " (" + currentPlayer + ")");
                System.out.print("Enter row (1-3): ");
                int row = scanner.nextInt() - 1;
                System.out.print("Enter column (1-3): ");
                int col = scanner.nextInt() - 1;

                if (isValidMove(row, col)) {
                    board[row][col] = currentPlayer;
                    logMove(row, col); // Log the move
                    if (checkWinner()) {
                        gameWon = true;
                        printBoard();
                        System.out.println("\n----------------------------------------");
                        System.out.println("  Congratulations, " + getCurrentPlayerName() + "! You win!");
                        System.out.println("----------------------------------------");
                        updateScore();
                    } else {
                        currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
                    }
                } else {
                    System.out.println("Invalid move. The cell is either occupied or out of bounds.");
                }
            }

            if (!gameWon) {
                printBoard();
                System.out.println("\n----------------------------------------");
                System.out.println("              It's a draw!");
                System.out.println("----------------------------------------");
            }

            displayScores();

            // Ask if the players want to view history
            System.out.print("\nWould you like to view the move history? (yes/no): ");
            if (scanner.next().equalsIgnoreCase("yes")) {
                showMoveHistory();
            }

            System.out.print("\nWould you like to play another round? (yes/no): ");
            playAgain = scanner.next().equalsIgnoreCase("yes");
        }

        System.out.println("\n----------------------------------------");
        System.out.println("               Final Scores             ");
        System.out.println("----------------------------------------");
        System.out.println(player1Name + " (X): " + scorePlayer1);
        System.out.println(player2Name + " (O): " + scorePlayer2);
        System.out.println("----------------------------------------");
        System.out.println("  Thank you for playing Tic-Tac-Toe!   ");
        System.out.println("----------------------------------------");
        scanner.close();
    }

    private static void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
        currentPlayer = 'X';
    }

    private static void printBoard() {
        System.out.println("\n     1   2   3");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("  " + (i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j < SIZE - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < SIZE - 1) {
                System.out.println("    ---+---+---");
            }
        }
    }

    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col] == EMPTY;
    }

    private static boolean checkWinner() {
        // Check rows and columns
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer)
                return true;
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)
                return true;
        }

        // Check diagonals
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer)
            return true;
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
            return true;

        return false;
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void updateScore() {
        if (currentPlayer == 'X') {
            scorePlayer1++;
        } else {
            scorePlayer2++;
        }
    }

    private static void displayScores() {
        System.out.println("\n----------------------------------------");
        System.out.println("            Current Scores              ");
        System.out.println("----------------------------------------");
        System.out.println(player1Name + " (X): " + scorePlayer1);
        System.out.println(player2Name + " (O): " + scorePlayer2);
        System.out.println("----------------------------------------");
    }

    private static void logMove(int row, int col) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String move = "Player " + getCurrentPlayerName() + " (" + currentPlayer + ") placed at [" + (row + 1) + ", " + (col + 1) + "] on " + timestamp;
        moveHistory.add(move);
    }

    private static void showMoveHistory() {
        System.out.println("\n-------------------------------------------------------------");
        System.out.println("             Move History               ");
        System.out.println("-------------------------------------------------------------");
        for (String move : moveHistory) {
            System.out.println(move);
        }
        System.out.println("-------------------------------------------------------------");
    }

    private static String getCurrentPlayerName() {
        return currentPlayer == 'X' ? player1Name : player2Name;
    }
}
