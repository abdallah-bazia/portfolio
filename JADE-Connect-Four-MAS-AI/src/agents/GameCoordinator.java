package agents;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import game.GameBoard;
import game.GameGUI;

public class GameCoordinator extends Agent {
    private GameBoard gameBoard;
    private GameGUI gui;
    private AID player1Agent;
    private AID player2Agent;
    private boolean gameInProgress;
    private boolean waitingForMove;
    
    protected void setup() {
        gameBoard = new GameBoard();
        gui = new GameGUI(gameBoard, this);
        
        System.out.println("Coordinateur de jeu démarré: " + getLocalName());
        
        // Récupérer les références des agents joueurs
        Object[] args = getArguments();
        if (args != null && args.length >= 2) {
            player1Agent = (AID) args[0];
            player2Agent = (AID) args[1];
        }
        
        gameInProgress = false;
        waitingForMove = false;
        
        // Comportement pour gérer le jeu
        addBehaviour(new GameManagementBehaviour(this, 100));
    }
    
    public void startGame() {
        gameBoard.reset();
        gui.updateBoard();
        gameInProgress = true;
        waitingForMove = false;
        System.out.println("\n=== NOUVELLE PARTIE ===");
        System.out.println("Agent 1 (Jaune) vs Agent 2 (Rouge)\n");
    }
    
    private class GameManagementBehaviour extends TickerBehaviour {
        
        public GameManagementBehaviour(Agent a, long period) {
            super(a, period);
        }
        
        protected void onTick() {
            if (!gameInProgress) {
                return;
            }
            
            if (!waitingForMove) {
                // Demander au joueur courant de jouer
                requestMove();
                waitingForMove = true;
                
            } else {
                // Vérifier si on a reçu une réponse
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                ACLMessage msg = myAgent.receive(mt);
                
                if (msg != null) {
                    String content = msg.getContent();
                    
                    if (content.startsWith("MOVE:")) {
                        int column = Integer.parseInt(content.substring(5));
                        processMove(column);
                        waitingForMove = false;
                    }
                }
            }
        }
    }
    
    private void requestMove() {
        AID currentAgent = (gameBoard.getCurrentPlayer() == 1) ? 
                          player1Agent : player2Agent;
        
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(currentAgent);
        msg.setContent("YOUR_TURN");
        msg.setConversationId("game-session");
        send(msg);
    }
    
    private void processMove(int column) {
        int currentPlayer = gameBoard.getCurrentPlayer();
        
        if (gameBoard.isValidMove(column)) {
            int row = gameBoard.makeMove(column);
            gui.animateMove(column, row, currentPlayer);
            
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            gui.updateBoard();
            
            // Vérifier victoire
            if (gameBoard.checkWin(currentPlayer)) {
                String winner = (currentPlayer == 1) ? 
                              "Agent 1 (Jaune)" : "Agent 2 (Rouge)";
                System.out.println("\n*** " + winner + " a gagné! ***\n");
                gui.showWinner(winner);
                gameInProgress = false;
                return;
            }
            
            // Vérifier match nul
            if (gameBoard.isFull()) {
                System.out.println("\n*** Match nul! ***\n");
                gui.showDraw();
                gameInProgress = false;
                return;
            }
            
            // Changer de joueur
            gameBoard.switchPlayer();
            
        } else {
            System.out.println("Coup invalide! Colonne: " + column);
        }
    }
    
    protected void takeDown() {
        if (gui != null) {
            gui.dispose();
        }
        System.out.println("Coordinateur de jeu se termine.");
    }
}