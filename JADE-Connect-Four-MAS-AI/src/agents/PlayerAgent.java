package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import game.GameBoard;
import game.AIStrategy;

public class PlayerAgent extends Agent {
    private int playerNumber;
    private GameBoard gameBoard;
    
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            playerNumber = (Integer) args[0];
            gameBoard = (GameBoard) args[1];
        }
        
        System.out.println("Agent Joueur " + playerNumber + " (" + 
                         getLocalName() + ") est prêt.");
        
        // Comportement pour recevoir les demandes de coup
        addBehaviour(new PlayTurnBehaviour());
    }
    
    private class PlayTurnBehaviour extends CyclicBehaviour {
        public void action() {
            // Attendre un message REQUEST du coordinateur
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage msg = myAgent.receive(mt);
            
            if (msg != null) {
                String content = msg.getContent();
                
                if (content.equals("YOUR_TURN")) {
                    // Calculer le meilleur coup avec l'IA
                    System.out.println("Agent " + playerNumber + " réfléchit...");
                    
                    // Simulation de temps de réflexion
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    int bestMove = AIStrategy.findBestMove(gameBoard, playerNumber);
                    
                    System.out.println("Agent " + playerNumber + 
                                     " joue dans la colonne " + bestMove);
                    
                    // Envoyer le coup au coordinateur
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("MOVE:" + bestMove);
                    myAgent.send(reply);
                }
                
            } else {
                block();
            }
        }
    }
    
    protected void takeDown() {
        System.out.println("Agent Joueur " + playerNumber + 
                         " (" + getLocalName() + ") se termine.");
    }
}