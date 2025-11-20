public class Puissance4Main {
    public static void main(String[] args) {
        jade.core.Runtime rt = jade.core.Runtime.instance();
        jade.core.Profile p = new jade.core.ProfileImpl();

        p.setParameter(jade.core.Profile.MAIN_HOST, "localhost");
        p.setParameter(jade.core.Profile.GUI, "true");

        jade.wrapper.AgentContainer mc = rt.createMainContainer(p);

        try {
            jade.wrapper.AgentController joueur1 = mc.createNewAgent(
                "Joueur1",
                "PlayerAgent",
                new Object[]{"ROUGE", "Joueur2"}
            );

            jade.wrapper.AgentController joueur2 = mc.createNewAgent(
                "Joueur2",
                "PlayerAgent",
                new Object[]{"JAUNE", "Joueur1"}
            );

            joueur1.start();
            joueur2.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}