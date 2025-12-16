package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIStrategy {
    private static final int MAX_DEPTH = 7; // Profondeur de recherche (augmentée pour plus de compétitivité)
    private static final Random random = new Random();
    
    // Trouve le meilleur coup avec l'algorithme Minimax
    public static int findBestMove(GameBoard board, int player) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;
        List<Integer> bestMoves = new ArrayList<>();
        
        int[] validMoves = board.getValidMoves();
        
        for (int col : validMoves) {
            GameBoard tempBoard = board.clone();
            tempBoard.makeMove(col);
            
            int score = minimax(tempBoard, MAX_DEPTH - 1, false, player, 
                              Integer.MIN_VALUE, Integer.MAX_VALUE);
            
            if (score > bestScore) {
                bestScore = score;
                bestMove = col;
                bestMoves.clear();
                bestMoves.add(col);
            } else if (score == bestScore) {
                // Si plusieurs coups ont le même score, les ajouter
                bestMoves.add(col);
            }
        }
        
        // Si plusieurs coups optimaux, choisir aléatoirement
        if (bestMoves.size() > 1) {
            return bestMoves.get(random.nextInt(bestMoves.size()));
        }
        
        return bestMove;
    }
    
    // Algorithme Minimax avec élagage Alpha-Beta
    private static int minimax(GameBoard board, int depth, boolean isMaximizing, 
                              int player, int alpha, int beta) {
        int opponent = (player == 1) ? 2 : 1;
        
        // Conditions de terminaison
        if (depth == 0 || board.isFull()) {
            return evaluateBoard(board, player);
        }
        
        if (board.checkWin(player)) {
            return 10000 + depth; // Favorise les victoires rapides
        }
        
        if (board.checkWin(opponent)) {
            return -10000 - depth; // Évite les défaites rapides
        }
        
        int[] validMoves = board.getValidMoves();
        
        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            
            for (int col : validMoves) {
                GameBoard tempBoard = board.clone();
                tempBoard.makeMove(col);
                
                int eval = minimax(tempBoard, depth - 1, false, player, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                
                if (beta <= alpha) {
                    break; // Élagage Beta
                }
            }
            return maxEval;
            
        } else {
            int minEval = Integer.MAX_VALUE;
            
            for (int col : validMoves) {
                GameBoard tempBoard = board.clone();
                tempBoard.makeMove(col);
                
                int eval = minimax(tempBoard, depth - 1, true, player, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                
                if (beta <= alpha) {
                    break; // Élagage Alpha
                }
            }
            return minEval;
        }
    }
    
    // Fonction d'évaluation heuristique
    private static int evaluateBoard(GameBoard board, int player) {
        int score = 0;
        int opponent = (player == 1) ? 2 : 1;
        
        int[][] grid = board.getBoard();
        int rows = GameBoard.getRows();
        int cols = GameBoard.getCols();
        
        // Évaluer toutes les fenêtres de 4 cases
        
        // Horizontales
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                score += evaluateWindow(grid[row][col], grid[row][col+1], 
                                       grid[row][col+2], grid[row][col+3], 
                                       player, opponent);
            }
        }
        
        // Verticales
        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols; col++) {
                score += evaluateWindow(grid[row][col], grid[row+1][col], 
                                       grid[row+2][col], grid[row+3][col], 
                                       player, opponent);
            }
        }
        
        // Diagonales (bas-gauche vers haut-droite)
        for (int row = 3; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                score += evaluateWindow(grid[row][col], grid[row-1][col+1], 
                                       grid[row-2][col+2], grid[row-3][col+3], 
                                       player, opponent);
            }
        }
        
        // Diagonales (haut-gauche vers bas-droite)
        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols - 3; col++) {
                score += evaluateWindow(grid[row][col], grid[row+1][col+1], 
                                       grid[row+2][col+2], grid[row+3][col+3], 
                                       player, opponent);
            }
        }
        
        // Bonus pour contrôle du centre
        int centerCol = cols / 2;
        for (int row = 0; row < rows; row++) {
            if (grid[row][centerCol] == player) {
                score += 3;
            }
        }
        
        return score;
    }
    
    // Évalue une fenêtre de 4 cases
    private static int evaluateWindow(int c1, int c2, int c3, int c4, 
                                     int player, int opponent) {
        int playerCount = 0;
        int opponentCount = 0;
        int emptyCount = 0;
        
        int[] cells = {c1, c2, c3, c4};
        
        for (int cell : cells) {
            if (cell == player) playerCount++;
            else if (cell == opponent) opponentCount++;
            else emptyCount++;
        }
        
        // Si les deux joueurs ont des jetons dans la fenêtre, pas de potentiel
        if (playerCount > 0 && opponentCount > 0) {
            return 0;
        }
        
        // Évaluation pour le joueur (offensive)
        if (playerCount == 4) return 100;
        if (playerCount == 3 && emptyCount == 1) return 10;  // Augmenté de 5 à 10
        if (playerCount == 2 && emptyCount == 2) return 3;   // Augmenté de 2 à 3
        
        // Évaluation pour l'adversaire (défensive - RENFORCÉE)
        if (opponentCount == 3 && emptyCount == 1) return -80;  // Augmenté de -50 à -80
        if (opponentCount == 2 && emptyCount == 2) return -5;   // Augmenté de -3 à -5
        
        return 0;
    }
}