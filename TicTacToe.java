package gee_p1;
/*
 * GEE
 * This is free and unencumbered software released into the public domain.
 */
import java.util.Arrays;

/**
 * Represents the Tic-Tac-Toe game.
 * This class provides methods for playing the game, checking for a winner,
 * and keeping track of game statistics.
 */
public class TicTacToe {
    //Fields
    private char[][] board;         // The game board
    private char currentPlayer;     // The current player ('X' or 'O')
    private int size;               // The size of the game board
    private int moves;              // The total number of moves made
    private int playerXScore;       // Player 'X' score
    private int playerOScore;       // Player 'O' score
    private int tieGames;            // Number of tied games

    private static final char EMPTY = ' ';

    /**
     * Constructs a TicTacToe game with the specified board size.
     *
     * @param size The size of the game board (e.g., 3 for a 3x3 board).
     */
    public TicTacToe(int size) {
        this.size = size;
        this.board = new char[size][size];
        this.currentPlayer = 'X';
        this.moves = 0;
        this.playerXScore = 0;
        this.playerOScore = 0;
        this.tieGames = 0;
        initializeBoard();
    }

    /**
     * Copy constructor for creating a deep copy of a TicTacToe game.
     *
     * @param other The original TicTacToe game to be copied.
     */
    public TicTacToe(TicTacToe other) {
        this.size = other.size;
        this.currentPlayer = other.currentPlayer;
        this.moves = other.moves;
        this.playerXScore = other.playerXScore;
        this.playerOScore = other.playerOScore;
        this.board = new char[size][size];

        //Deep copy of the board
        for (int i = 0; i < size; i++) {
            this.board[i] = Arrays.copyOf(other.board[i], size);
        }
    }

    /**
     * Initializes the game board with empty spaces.
     */
    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /**
     * Checks if the TicTacToe board is full.
     *
     * @return True if the board is full, false otherwise.
     */
    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == ' ') {
                    return false; // There is an empty space, so the board
                                 // is not full
                }
            }
        }
        return true; // All spaces on the board are filled
    }


    /**
     * Displays the current game board with coordinates.
     */
    public void displayBoardWithCoordinates() {
        System.out.print("    ");
        for (int i = 0; i < size; i++) {
            System.out.print(i + "    ");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.print(i + "    ");
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n   ---------");
        }
    }

    /**
     * Makes a move at the specified row and column.
     *
     * @param row The row index.
     * @param col The column index.
     * @return True if the move is valid and results in a win or draw;
     *         false otherwise.
     */
    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size ||
                board[row][col] != ' ') {
            return false; // Invalid move
        }

        board[row][col] = currentPlayer;
        moves++;

        if (checkWinner(row, col)) {
            updateScore();
            displayBoardWithCoordinates();
            System.out.println("Player " + currentPlayer + " wins!");
            initializeBoard();
            return true;
        } else if (moves == size * size) {
            displayBoardWithCoordinates();
            System.out.println("It's a draw!");
            tieGames++;
            initializeBoard();
            return true;
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        return false;
    }

    /**
     * Checks for a winner after a move at the specified row and column.
     *
     * @param row The row index.
     * @param col The column index.
     * @return True if there is a winner, false otherwise.
     */
    private boolean checkWinner(int row, int col) {
        return checkRow(row) || checkColumn(col) || checkDiagonals();
    }

    /**
     * Checks if there is a winner on the TicTacToe board.
     *
     * @return True if there is a winner, false otherwise.
     */
    public boolean hasWinner() {
        // Check rows, columns, and diagonals for a winning combination
        for (int i = 0; i < size; i++) {
            if (checkRow(i) || checkColumn(i)) {
                return true;
            }
        }

        // Check diagonals
        return checkDiagonals();
    }

    /**
     * Checks for a winner in the specified row.
     *
     * @param row The row index.
     * @return True if there is a winner in the row, false otherwise.
     */
    private boolean checkRow(int row) {
        for (int i = 1; i < size ; i++) {
            if (board[row][i] != currentPlayer || board[row][i] !=
                    board[row][i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for a winner in the specified column.
     *
     * @param col The column index.
     * @return True if there is a winner in the column, false otherwise.
     */
    private boolean checkColumn(int col) {
        for (int i = 0; i < size; i++) {
            if (board[i][col] != currentPlayer || board[i][col] !=
                    board[i + 1][col]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for a winner in the diagonals based on the last move.
     *
     * @return True if there is a winner in the diagonals, false otherwise.
     */
    private boolean checkDiagonals() {
        boolean mainDiagonal = true;
        boolean antiDiagonal = true;

        for (int i = 0; i < size - 1; i++) {
            // Check the main diagonal
            mainDiagonal &= (board[i][i] == currentPlayer && board[i][i] ==
                             board[i + 1][i + 1]);

            // Check the anti-diagonal
            antiDiagonal &= (board[i][size - 1 - i] == currentPlayer &&
                             board[i][size - 1 - i] ==
                                    board[i + 1][size - 2 - i]);
        }

        return mainDiagonal || antiDiagonal;
    }
    public boolean isValidMove(int row, int col) {
        // Check if the row and column are within the valid range
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }

        // Check if the cell is empty
        return board[row][col] == EMPTY;
    }
    /**
     * Updates the scores based on the current player.
     */
    private void updateScore() {
        if (currentPlayer == 'X') {
            playerXScore++;
        } else {
            playerOScore++;
        }
    }

    /**
     * Gets the symbol of the current player ('X' or 'O').
     *
     * @return The symbol of the current player.
     */
    public char getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the current score of player 'X'.
     *
     * @return The score of player 'X'.
     */
    public int getPlayerXScore() {
        return playerXScore;
    }

    /**
     * Gets the current score of player 'O'.
     *
     * @return The score of player 'O'.
     */
    public int getPlayerOScore() {
        return playerOScore;
    }

    public int getSize() {
        return size;
    }

    /**
     * Gets the symbol of the winner ('X' or 'O').
     *
     * @return The symbol of the winner, or ' ' (empty char) if there is
     *          no winner.
     */
    public char getWinner() {
        // Check rows, columns, and diagonals for a winning combination
        for (int i = 0; i < size; i++) {
            if (checkRow(i)) {
                return board[i][0];
            } else if (checkColumn(i)) {
                return board[0][i];
            }
        }

        // Check diagonals
        char mainDiagonalWinner = checkMainDiagonal();
        if (mainDiagonalWinner != ' ') {
            return mainDiagonalWinner;
        }

        char antiDiagonalWinner = checkAntiDiagonal();
        if (antiDiagonalWinner != ' ') {
            return antiDiagonalWinner;
        }

        // No winner
        return ' ';
    }

    /**
     * Checks for a winner in the main diagonal.
     *
     * @return The symbol of the winner in the main diagonal, or ' '
     *          (empty char) if there is no winner.
     */
    private char checkMainDiagonal() {
        for (int i = 0; i < size - 1; i++) {
            if (board[i][i] != board[i + 1][i + 1] || board[i][i] == ' ') {
                return ' '; // No winner in the main diagonal
            }
        }
        return board[0][0]; // Winner in the main diagonal
    }

    /**
     * Checks for a winner in the anti-diagonal.
     *
     * @return The symbol of the winner in the anti-diagonal, or ' '
     *          (empty char) if there is no winner.
     */
    private char checkAntiDiagonal() {
        for (int i = 0; i < size - 1; i++) {
            if (board[i][size - 1 - i] != board[i + 1][size - 2 - i] ||
                    board[i][size - 1 - i] == ' ') {
                return ' '; // No winner in the anti-diagonal
            }
        }
        return board[0][size - 1]; // Winner in the anti-diagonal
    }
    /**
     *Gets the number of tied games.
     *
     * @return The count of games that ended in a draw.
     */
    public int getTieGames() {
        return tieGames;
    }

    /**
     * Increments the count of tied games.
     */
    public void incrementTieGames() {
        tieGames++;
    }

    /**
     * Returns a string representation of the current game board.
     *
     * @return The string representation of the game board.
     */
    @Override
    public String toString() {
        String result = "-------------\n";
        for (int i = 0; i < size; i++) {
            result += "| ";
            for (int j = 0; j < size; j++) {
                result += board[i][j] + " | ";
            }
            result += "\n-------------\n";
        }
        return result;
    }

    /**
     * Checks if the current TicTacToe object is equal to another object.
     *
     * @param obj The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TicTacToe other = (TicTacToe) obj;
        return size == other.size &&
                moves == other.moves &&
                playerXScore == other.playerXScore &&
                playerOScore == other.playerOScore &&
                currentPlayer == other.currentPlayer &&
                Arrays.deepEquals(board, other.board);
    }
}
