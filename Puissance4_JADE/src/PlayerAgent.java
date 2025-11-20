import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class PlayerAgent extends Agent {
    private Puissance4GUI gui;
    private String playerColor;
    private String opponentName;

    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            playerColor = (String) args[0];
            if (args.length > 1) {
                opponentName = (String) args[1];
            }
        }

        gui = new Puissance4GUI(this, playerColor);

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String content = msg.getContent();

                    if (content.startsWith("MOVE:")) {
                        int col = Integer.parseInt(content.split(":")[1]);
                        gui.opponentMove(col);
                    }
                    else if (content.equals("START")) {
                        gui.setTurn(playerColor.equals("ROUGE"));
                    }
                    else if (content.equals("RESET")) {
                        gui.resetGame();
                    }
                } else {
                    block();
                }
            }
        });
    }

    public void sendMove(int column) {
        if (opponentName != null) {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(new jade.core.AID(opponentName, jade.core.AID.ISLOCALNAME));
            msg.setContent("MOVE:" + column);
            send(msg);
        }
    }

    public void sendReset() {
        if (opponentName != null) {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(new jade.core.AID(opponentName, jade.core.AID.ISLOCALNAME));
            msg.setContent("RESET");
            send(msg);
        }
    }
}