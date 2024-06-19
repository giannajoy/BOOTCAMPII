package gee_p1;
/*
 * Gee
 * This is free and unencumbered software released into the public domain.
 */
import java.util.Scanner;

/**
 * Represents a console-based Tic-Tac-Toe game application.
 * Allows users to play Tic-Tac-Toe, view game statistics, and play again.
 */
public class P1 {
    /**
     * The main entry point for the Tic-Tac-Toe game application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {

        // Create a Scanner object to read user input from the console
        Scanner scanner = new Scanner(System.in);

        // Welcome message for the Tic-Tac-Toe game
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println("The TicTacToe Game allows you to enter a row and " +
                            "column as desired. There is a display of the " +
                            "position where you can place an X or O. The game" +
                            "provides you with statistics of who has won the " +
                            "game, lost the game and finally the ties. It " +
                            "will generally provide with the number of wins " +
                            "each player has. Let's go and get started. ");

        // Prompt the user to enter the size of the Tic-Tac-Toe board
        System.out.print("Enter the size of the board (e.g., 3 for 3x3: ");
        int size = scanner.nextInt();

        // Initialize a TicTacToe object with the specified size
        TicTacToe ticTacToe = new TicTacToe(size);

        // Start the main game loop
        do {
            // Call the playGame method to allow players to make moves and
            // determine the game outcome
            playGame(scanner, ticTacToe);

            // Display game statistics after each game iteration
            System.out.println("Game Stats");
            System.out.println("X has won " + ticTacToe.getPlayerXScore() +
                                " games.");
            System.out.println("O has won " + ticTacToe.getPlayerOScore() +
                                " games.");
            System.out.println("There have been " + ticTacToe.getTieGames() +
                                " tie games.");

            // Test the copy constructor by creating a copy of the current
            // game state
            TicTacToe copiedGame = new TicTacToe(ticTacToe);
            System.out.println("Original Game:\n" + ticTacToe);
            System.out.println("Copied Game:\n" + copiedGame);
            System.out.println("Are the original and copied games equal? " +
                                ticTacToe.equals(copiedGame));

            // Continue the game loop as long as the player chooses to
            // play again
        } while (playAgain(scanner));

        // Display a farewell message and final scores after the game loop ends
        System.out.println("Thanks for playing! Final Scores:");
        System.out.println("Player X: " + ticTacToe.getPlayerXScore());
        System.out.println("Player O: " + ticTacToe.getPlayerOScore());
        System.out.println("I hope you enjoyed the game, I look forward to " );
        System.out.println("see you come back and play. ");
        System.out.println("So Long, Friend!");
    }

    /**
     * Plays a single game of Tic-Tac-Toe.
     *
     * @param scanner    The scanner for user input.
     * @param ticTacToe  The TicTacToe game instance to play.
     */
    private static void playGame(Scanner scanner, TicTacToe ticTacToe) {
        // Create a copy of the current game state
        TicTacToe originalState = new TicTacToe(ticTacToe);

        // Control variable for the game loop
        boolean continuePlaying = true;

        // Repeat the following block until there is a winner, the board is
        // full, or the player chooses to play again
        do {
            // Display the current state of the TicTacToe board
            System.out.println(ticTacToe);

            // Prompt the current player for their move
            System.out.println("Player " + ticTacToe.getCurrentPlayer() +
                                ", it is your turn.");
            ticTacToe.displayBoardWithCoordinates();
            System.out.print("Which row? ");
            int row = scanner.nextInt();
            System.out.print("Which column? ");
            int col = scanner.nextInt();

            // Keep prompting the player for new coordinates until a valid
            // move is entered
            while (!isValidMove(ticTacToe, row, col)) {
                // Inform the player that the location is invalid
                System.out.println("Bad location, try again...");

                // Prompt for new coordinates
                System.out.print("Which row? ");
                row = scanner.nextInt();
                System.out.print("Which column? ");
                col = scanner.nextInt();
            }

            // Attempt to make a move
            ticTacToe.makeMove(row, col);

            // Check if there is a winner or if the game ended in a tie
            if (ticTacToe.hasWinner()) {
                System.out.println(ticTacToe);
                System.out.println("Player " + ticTacToe.getWinner() +
                                    " wins!");
                continuePlaying = false;
            } else if (ticTacToe.isBoardFull()) {
                System.out.println(ticTacToe);
                System.out.println("No winner - it was a tie!");
                ticTacToe.incrementTieGames();
                continuePlaying = false;
            }

            // Continue the loop as long as the game state has not changed and
            // the player chooses to play again
        } while (continuePlaying && !ticTacToe.equals(originalState) &&
                playAgain(scanner));
    }



    /**
     * Checks if the specified move (row, col) is a valid move on the TicTacToe
     * board.
     *
     * @param ticTacToe The TicTacToe game instance to validate the move
     *                  against.
     * @param row       The row index of the move.
     * @param col       The column index of the move.
     * @return {true} if the move is valid, indicating that the cell is empty
     *          and within the board boundaries; {false} otherwise.
     *
     */
    private static boolean isValidMove(TicTacToe ticTacToe, int row, int col) {
        return ticTacToe.isValidMove(row, col);
    }

    /**
     * Asks the user if they want to play the game again.
     *
     * @param scanner The scanner for user input.
     * @return True if the user wants to play again, false otherwise.
     */
    private static boolean playAgain(Scanner scanner) {
        boolean validInput = false;     // Flag to track if the input is valid
        boolean play = false;   // Flag to determine if the user wants to play
                                // again

        // Keep prompting until valid input is received
        while (!validInput) {
            System.out.print("Do you want to play again? (yes/no): ");
            String playAgain = scanner.next().toLowerCase();

            // Check if the user wants to play again
            if (playAgain.equals("yes")) {
                validInput = true;  // Valid input received, exit the loop
                play = true;    // Set the flag to indicate the user wants to
                                // play again
            } else if (playAgain.equals("no")) {
                validInput = true;  // Valid input received, exit the loop
            } else {
                // Invalid input, prompt the user to enter 'yes' or 'no'
                System.out.println("Invalid input. Please enter 'yes' or " +
                                    "'no'.");
            }
        }

        return play;   // Return the final decision on playing again
    }
}
