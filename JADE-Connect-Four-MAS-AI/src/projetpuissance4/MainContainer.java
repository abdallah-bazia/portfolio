package projetpuissance4;  // ← CHANGÉ DE "main" À "projetpuissance4"

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import game.GameBoard;
import jade.core.AID;

public class MainContainer {
    
    public static void main(String[] args) {
        try {
            // Obtenir l'instance du runtime JADE
            Runtime runtime = Runtime.instance();
            
            // Créer le profil pour le conteneur principal
            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            profile.setParameter(Profile.GUI, "true"); // Active le RMA GUI
            
            System.out.println("Démarrage de la plateforme JADE...");
            
            // Créer le conteneur principal
            AgentContainer mainContainer = runtime.createMainContainer(profile);
            
            // Créer le plateau de jeu partagé
            GameBoard gameBoard = new GameBoard();
            
            // Créer les agents joueurs
            System.out.println("Création des agents joueurs...");
            
            Object[] player1Args = new Object[]{1, gameBoard};
            AgentController player1 = mainContainer.createNewAgent(
                "Player1Agent", 
                "agents.PlayerAgent", 
                player1Args
            );
            player1.start();
            
            Object[] player2Args = new Object[]{2, gameBoard};
            AgentController player2 = mainContainer.createNewAgent(
                "Player2Agent", 
                "agents.PlayerAgent", 
                player2Args
            );
            player2.start();
            
            // Attendre que les agents soient prêts
            Thread.sleep(1000);
            
            // Créer le coordinateur avec les références des agents
            System.out.println("Création du coordinateur de jeu...");
            
            // Utiliser les AIDs (Agent Identifiers)
            AID aid1 = new AID("Player1Agent", AID.ISLOCALNAME);
            AID aid2 = new AID("Player2Agent", AID.ISLOCALNAME);
            
            Object[] coordinatorArgs = new Object[]{aid1, aid2};
            
            AgentController coordinator = mainContainer.createNewAgent(
                "GameCoordinator", 
                "agents.GameCoordinator", 
                coordinatorArgs
            );
            coordinator.start();
            
            System.out.println("\n=================================");
            System.out.println("Système multi-agents démarré avec succès!");
            System.out.println("=================================");
            System.out.println("Agents actifs:");
            System.out.println("  - Player1Agent (Joueur Jaune)");
            System.out.println("  - Player2Agent (Joueur Rouge)");
            System.out.println("  - GameCoordinator (Coordinateur)");
            System.out.println("=================================\n");
            System.out.println("Cliquez sur 'Démarrer' dans l'interface pour commencer!");
            
        } catch (StaleProxyException e) {
            System.err.println("Erreur lors de la création des agents:");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Erreur d'attente:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur générale:");
            e.printStackTrace();
        }
    }
}