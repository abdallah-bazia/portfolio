package game;

public class GameBoard {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private int[][] board;
    private int currentPlayer;
    
    public GameBoard() {
        board = new int[ROWS][COLS];
        currentPlayer = 1;
    }
    
    public int[][] getBoard() {
        return board;
    }
    
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    
    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }
    
    // Vérifie si une colonne est jouable
    public boolean isValidMove(int col) {
        return col >= 0 && col < COLS && board[0][col] == 0;
    }
    
    // Joue dans une colonne
    public int makeMove(int col) {
        if (!isValidMove(col)) {
            return -1;
        }
        
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == 0) {
                board[row][col] = currentPlayer;
                return row;
            }
        }
        return -1;
    }
    
    // Vérifie si un joueur a gagné
    public boolean checkWin(int player) {
        // Vérification horizontale
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] == player &&
                    board[row][col+1] == player &&
                    board[row][col+2] == player &&
                    board[row][col+3] == player) {
                    return true;
                }
            }
        }
        
        // Vérification verticale
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == player &&
                    board[row+1][col] == player &&
                    board[row+2][col] == player &&
                    board[row+3][col] == player) {
                    return true;
                }
            }
        }
        
        // Vérification diagonale (bas-gauche vers haut-droite)
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] == player &&
                    board[row-1][col+1] == player &&
                    board[row-2][col+2] == player &&
                    board[row-3][col+3] == player) {
                    return true;
                }
            }
        }
        
        // Vérification diagonale (haut-gauche vers bas-droite)
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] == player &&
                    board[row+1][col+1] == player &&
                    board[row+2][col+2] == player &&
                    board[row+3][col+3] == player) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    // Vérifie si le plateau est plein
    public boolean isFull() {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col] == 0) {
                return false;
            }
        }
        return true;
    }
    
    // Obtient les colonnes jouables
    public int[] getValidMoves() {
        int count = 0;
        for (int col = 0; col < COLS; col++) {
            if (isValidMove(col)) count++;
        }
        
        int[] validMoves = new int[count];
        int index = 0;
        for (int col = 0; col < COLS; col++) {
            if (isValidMove(col)) {
                validMoves[index++] = col;
            }
        }
        return validMoves;
    }
    
    // Clone le plateau pour simulation
    public GameBoard clone() {
        GameBoard copy = new GameBoard();
        for (int i = 0; i < ROWS; i++) {
            System.arraycopy(this.board[i], 0, copy.board[i], 0, COLS);
        }
        copy.currentPlayer = this.currentPlayer;
        return copy;
    }
    
    public void reset() {
        board = new int[ROWS][COLS];
        currentPlayer = 1;
    }
    
    public static int getRows() {
        return ROWS;
    }
    
    public static int getCols() {
        return COLS;
    }
}