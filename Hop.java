import javax.swing.*; //pour le GUI (JFrame etc..) 
import java.awt.*; //tools pour dessiner
import java.awt.event.ActionEvent; //

public class Hop {
    public static final int WIDTH = 400; //largeur de la fenêtre de jeu
    public static final int HEIGHT = 600; //hauteur de la fenêtre de jeu
    public static final int DELAY = 40; //délai en millisecondes entre chaque round


    private final JFrame frame; //fenêtre principale du jeu
    private final Field field; //terrain de jeu où Axel évolue (class Fild)
    private final Axel axel; //personnage contrôlé par le joueur (calss Axel)
    private Timer timer; //minuterie pour gérer le déroulement du jeu
    private GamePanel gamePanel; //pour the graphics


    //constructeur qui initialise le jeu
    public Hop() { 
        this.field = new Field(WIDTH, HEIGHT); //initialise le terrain avec les dimensions du jeu
        this.axel = new Axel(field, WIDTH/2, field.START_ALTITUDE); //Axel commence au milieu horizontalement et à l'altitude de départ définie par le champ
        this.gamePanel = new GamePanel(field, axel); //initailise le game Panel (fenetre graphique)

        this.frame = new JFrame("rima&malak");
        frame.add(gamePanel);
        frame.pack();
        frame.setVisible(true); //rend la fenêtre visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ferme le window quan on clique sur X
    }

    public void round() { //execute un cycle de jeu
        axel.update(); //met à jour l'état d'axel
        field.update(); // Met à jour l'état de field 
        frame.repaint();  //dessine l'interface graphique


        //vérifie si le score d'Axel dépasse le seuil du prochain niveau
        if (axel.score() > field.getAugAltitude()[field.getLevel()+1]) {
            field.augmenteLevel(); //si oui il augmente le niveau 
        }
    
    }

     //vérifier si le jeu est terminé
    public boolean over() { 
        if(!axel.getSurviving()) return true; //si axel n'est plus vivant le jeu est terminer

        return false; 
    }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> { //s'assure que le jeu est lancé sur le file graphique
                Hop game = new Hop(); //initialise le jeu

                //crée une minuterie pour exécuter des cycles de jeu à des intervalles réguliers
                game.timer = new Timer(DELAY, (ActionEvent e) -> { // juste au lieu d'ecrire explicitement
                    game.round(); //exécute un cycle de jeu
                    if (game.over()) { //verifie si le jeu est terminer 
                        game.timer.stop();  //si oui arreter la minuterie
                    }
                });
     
                game.timer.start(); //demarre la minuterie pour lancer le jeu
            });
        }

}