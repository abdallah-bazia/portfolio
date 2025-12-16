package game;

import javax.swing.*;
import java.awt.*;
import agents.GameCoordinator;

public class GameGUI extends JFrame {
    private static final int CELL_SIZE = 80;
    private static final Color BOARD_COLOR = new Color(0, 102, 204);
    private static final Color EMPTY_COLOR = Color.WHITE;
    private static final Color PLAYER1_COLOR = Color.YELLOW;
    private static final Color PLAYER2_COLOR = Color.RED;
    
    private GameBoard gameBoard;
    private GameCoordinator coordinator;
    private JPanel boardPanel;
    private JButton startButton;
    private JLabel statusLabel;
    private JLabel player1Label;
    private JLabel player2Label;
    
    public GameGUI(GameBoard board, GameCoordinator coord) {
        this.gameBoard = board;
        this.coordinator = coord;
        
        setTitle("Puissance 4 - Deux Agents Intelligents");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel supérieur (info joueurs)
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        player1Label = new JLabel("Agent 1 (Jaune)", SwingConstants.CENTER);
        player1Label.setFont(new Font("Arial", Font.BOLD, 16));
        player1Label.setForeground(PLAYER1_COLOR.darker());
        player1Label.setOpaque(true);
        player1Label.setBackground(new Color(255, 255, 200));
        player1Label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        player2Label = new JLabel("Agent 2 (Rouge)", SwingConstants.CENTER);
        player2Label.setFont(new Font("Arial", Font.BOLD, 16));
        player2Label.setForeground(PLAYER2_COLOR.darker());
        player2Label.setOpaque(true);
        player2Label.setBackground(Color.WHITE);
        player2Label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        topPanel.add(player1Label);
        topPanel.add(player2Label);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Panel du plateau
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        
        int rows = GameBoard.getRows();
        int cols = GameBoard.getCols();
        boardPanel.setPreferredSize(new Dimension(
            cols * CELL_SIZE + 20, 
            rows * CELL_SIZE + 20
        ));
        boardPanel.setBackground(BOARD_COLOR);
        
        add(boardPanel, BorderLayout.CENTER);
        
        // Panel inférieur (contrôles)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        startButton = new JButton("Démarrer une nouvelle partie");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.addActionListener(e -> coordinator.startGame());
        
        statusLabel = new JLabel("Cliquez sur 'Démarrer' pour commencer", 
                                SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        bottomPanel.add(startButton, BorderLayout.NORTH);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        int[][] board = gameBoard.getBoard();
        int rows = GameBoard.getRows();
        int cols = GameBoard.getCols();
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * CELL_SIZE + 10;
                int y = row * CELL_SIZE + 10;
                
                // Dessiner la cellule
                Color cellColor;
                if (board[row][col] == 1) {
                    cellColor = PLAYER1_COLOR;
                } else if (board[row][col] == 2) {
                    cellColor = PLAYER2_COLOR;
                } else {
                    cellColor = EMPTY_COLOR;
                }
                
                g2d.setColor(cellColor);
                g2d.fillOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                
                // Bordure
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
            }
        }
    }
    
    public void updateBoard() {
        boardPanel.repaint();
        
        // Mettre à jour l'indicateur du joueur courant
        int currentPlayer = gameBoard.getCurrentPlayer();
        if (currentPlayer == 1) {
            player1Label.setBackground(new Color(255, 255, 200));
            player1Label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            player2Label.setBackground(Color.WHITE);
            player2Label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            statusLabel.setText("Tour de l'Agent 1 (Jaune)");
        } else {
            player1Label.setBackground(Color.WHITE);
            player1Label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            player2Label.setBackground(new Color(255, 200, 200));
            player2Label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            statusLabel.setText("Tour de l'Agent 2 (Rouge)");
        }
    }
    
    public void animateMove(int col, int row, int player) {
        // Animation simple de chute
        boardPanel.repaint();
    }
    
    public void showWinner(String winner) {
        statusLabel.setText("*** " + winner + " a gagné! ***");
        statusLabel.setForeground(new Color(0, 150, 0));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JOptionPane.showMessageDialog(this, 
            winner + " a gagné la partie!", 
            "Fin de partie", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void showDraw() {
        statusLabel.setText("*** Match nul! ***");
        statusLabel.setForeground(Color.BLUE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JOptionPane.showMessageDialog(this, 
            "Match nul! Le plateau est plein.", 
            "Fin de partie", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}