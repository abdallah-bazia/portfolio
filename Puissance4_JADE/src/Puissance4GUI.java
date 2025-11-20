import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Puissance4GUI extends JFrame {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final int CELL_SIZE = 80;

    private int[][] grid = new int[ROWS][COLS];
    private JButton[] columnButtons = new JButton[COLS];
    private GamePanel gamePanel;

    private PlayerAgent agent;
    private String playerColor;
    private boolean isMyTurn;
    private int currentPlayer = 1; // 1 = Rouge, 2 = Jaune

    private JLabel statusLabel;
    private JLabel scoreLabel;
    private JButton resetButton;

    private int myWins = 0;
    private int opponentWins = 0;
    private int draws = 0;

    public Puissance4GUI(PlayerAgent agent, String color) {
        this.agent = agent;
        this.playerColor = color;
        this.isMyTurn = color.equals("ROUGE");

        setTitle("Puissance 4 - Joueur " + color);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---- Top buttons ----
        JPanel topPanel = new JPanel(new GridLayout(1, COLS));
        for (int i = 0; i < COLS; i++) {
            final int col = i;
            columnButtons[i] = new JButton("click");
            columnButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
            columnButtons[i].setBackground(new Color(46, 204, 113));
            columnButtons[i].addActionListener(e -> playMove(col));
            topPanel.add(columnButtons[i]);
        }
        add(topPanel, BorderLayout.NORTH);

        // ---- Board ----
        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        // ---- Bottom panel ----
        JPanel bottomPanel = new JPanel(new BorderLayout());

        statusLabel = new JLabel(isMyTurn ? "Votre tour!" : "Tour de l'adversaire");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.NORTH);

        JPanel scorePanel = new JPanel(new BorderLayout());

        scoreLabel = new JLabel(getScoreText());
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(scoreLabel, BorderLayout.NORTH);

        resetButton = new JButton("🔄 Nouvelle Partie");
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setBackground(new Color(46, 204, 113));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(e -> requestNewGame());
        scorePanel.add(resetButton, BorderLayout.CENTER);

        bottomPanel.add(scorePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        updateButtons();

        setSize(COLS * CELL_SIZE + 20, ROWS * CELL_SIZE + 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String getScoreText() {
        return String.format("Victoires: %d | Défaites: %d | Nuls: %d", myWins, opponentWins, draws);
    }

    private void requestNewGame() {
        resetGameLocal();
        agent.sendReset();
    }

    public void resetGame() {
        resetGameLocal();
    }

    private void resetGameLocal() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                grid[r][c] = 0;
            }
        }

        currentPlayer = 1;
        isMyTurn = playerColor.equals("ROUGE");

        statusLabel.setText(isMyTurn ? "Votre tour!" : "Tour de l'adversaire");
        updateButtons();
        gamePanel.repaint();
    }

    private void playMove(int col) {
        if (!isMyTurn) return;

        int row = -1;
        for (int r = ROWS - 1; r >= 0; r--) {
            if (grid[r][col] == 0) {
                row = r;
                break;
            }
        }
        if (row == -1) return;

        grid[row][col] = currentPlayer;
        gamePanel.repaint();

        if (checkWin(row, col)) {
            myWins++;
            scoreLabel.setText(getScoreText());
            statusLabel.setText("🎉 Vous avez gagné!");
            disableAllButtons();
            return;
        }

        if (isBoardFull()) {
            draws++;
            scoreLabel.setText(getScoreText());
            statusLabel.setText("Match nul!");
            disableAllButtons();
            return;
        }

        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        isMyTurn = false;
        statusLabel.setText("Tour de l'adversaire");
        updateButtons();

        agent.sendMove(col);
    }

    public void opponentMove(int col) {
        int row = -1;
        for (int r = ROWS - 1; r >= 0; r--) {
            if (grid[r][col] == 0) {
                row = r;
                break;
            }
        }
        if (row == -1) return;

        grid[row][col] = currentPlayer;
        gamePanel.repaint();

        if (checkWin(row, col)) {
            opponentWins++;
            scoreLabel.setText(getScoreText());
            statusLabel.setText("😞 L'adversaire a gagné!");
            disableAllButtons();
            return;
        }

        if (isBoardFull()) {
            draws++;
            scoreLabel.setText(getScoreText());
            statusLabel.setText("Match nul!");
            disableAllButtons();
            return;
        }

        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        isMyTurn = true;
        statusLabel.setText("Votre tour!");
        updateButtons();
    }

    private boolean checkWin(int row, int col) {
        int player = grid[row][col];

        // Horizontal
        int count = 1;
        for (int c = col - 1; c >= 0 && grid[row][c] == player; c--) count++;
        for (int c = col + 1; c < COLS && grid[row][c] == player; c++) count++;
        if (count >= 4) return true;

        // Vertical
        count = 1;
        for (int r = row - 1; r >= 0 && grid[r][col] == player; r--) count++;
        for (int r = row + 1; r < ROWS && grid[r][col] == player; r++) count++;
        if (count >= 4) return true;

        // Diagonal \
        count = 1;
        for (int i = 1; row - i >= 0 && col - i >= 0 && grid[row - i][col - i] == player; i++) count++;
        for (int i = 1; row + i < ROWS && col + i < COLS && grid[row + i][col + i] == player; i++) count++;
        if (count >= 4) return true;

        // Diagonal /
        count = 1;
        for (int i = 1; row - i >= 0 && col + i < COLS && grid[row - i][col + i] == player; i++) count++;
        for (int i = 1; row + i < ROWS && col - i >= 0 && grid[row + i][col - i] == player; i++) count++;
        if (count >= 4) return true;

        return false;
    }

    private boolean isBoardFull() {
        for (int c = 0; c < COLS; c++) {
            if (grid[0][c] == 0) return false;
        }
        return true;
    }

    public void setTurn(boolean myTurn) {
        this.isMyTurn = myTurn;
        updateButtons();
    }

    private void updateButtons() {
        for (int i = 0; i < COLS; i++) {
            columnButtons[i].setEnabled(isMyTurn && grid[0][i] == 0);
        }
    }

    private void disableAllButtons() {
        for (JButton btn : columnButtons) {
            btn.setEnabled(false);
        }
    }

    class GamePanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(new Color(43, 02, 20));
            g.fillRect(0, 0, getWidth(), getHeight());

            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    int x = c * CELL_SIZE + 10;
                    int y = r * CELL_SIZE + 10;

                    if (grid[r][c] == 1) {
                        g.setColor(Color.RED);
                    } else if (grid[r][c] == 2) {
                        g.setColor(Color.YELLOW);
                    } else {
                        g.setColor(Color.BLACK);
                    }

                    g.fillRect(x, y, CELL_SIZE - 20, CELL_SIZE - 20);

                    g.setColor(Color.GREEN);
                    g.drawRect(x, y, CELL_SIZE - 20, CELL_SIZE - 20);
                }
            }
        }

        public Dimension getPreferredSize() {
            return new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE);
        }
    }
}