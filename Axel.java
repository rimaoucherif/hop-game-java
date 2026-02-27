import java.util.ArrayList;
import java.util.List;

public class Axel {
    public static final double MAX_FALL_SPEED = -20.0; // fait en sorte que axel ne tombe pas trop vite
    public static final double JUMP_SPEED = 20.0; //determines how high axel can go per jump
    public static final double GRAVITY = 1.0; // fait en sorte que axel revient en bas after jumping
    public static final double DIVE_SPEED = 3.0 * GRAVITY; // extra force quand axel tombe
    public static final double LATERAL_SPEED = 8.0; //vitesse du mouvement horizontal

    private int x, y; // position actuelle de axel (coordonnees)
    private double dx, dy; //nextDx, nextDy; //dx = vitesseHorizentale dy = vitesseVerticale

    private boolean falling; // des booleans qui controlent l'etat de axel
    private boolean jumping;
    private boolean diving;
    private boolean left;
    private boolean right;

    private boolean surviving;

    private int altitudeMax = 0; //NOUVEAU highest vertical position axel has reached

    private final Field field;

    public Axel(Field f, int x, int y) {
        this.field = f;
        this.x = x;
        this.y = y;
        this.surviving = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getSurviving() { 
        return surviving; 
    }

    public void setJump(boolean jumping) {
        this.jumping = jumping;
     }
  
    public void setDiving(boolean diving) {
        this.diving = diving;
     }
  
    public void setLeft(boolean left) {
        this.left = left;
     }
  
    public void setRight(boolean right) {
        this.right = right;
    }

    public void computeMove() { //calcule le prochain mouvement de axel en fonction de son état actuel

        //cas où l'axel est en tombé.
        if (falling && y < field.getBottom() - 10) { //verifie si axel tombe en dehors du champ
            surviving = false; //A perd si il tombe trop bas
        }
        
        //si Axel saute et n'est pas déjà en train de tomber
        if (jumping && !falling) {
            dy = JUMP_SPEED; //applique la vitesse de saut
            falling = true; //A commence à tomber après le saut
            jumping = false; //termine l'action de saut
        }

         //calcul de la vitesse horizontale
        dx = 0.0; //reinitialise la vitesse horiz
        if (left) {
            dx = -LATERAL_SPEED; //mouvement vers la gauche
        }
        if (right) {
            dx = LATERAL_SPEED; //mouvement vers la droite
        }

        //cas où l'axel est en chute libre et possiblement en plongée.
        if (falling) {
            dy -= GRAVITY; //applique la gravité
            if (diving) {
                dy -= DIVE_SPEED; //applique une force supplémentaire si Axel plonge
            }
            if (dy < MAX_FALL_SPEED) {
                dy = MAX_FALL_SPEED; //limite la vitesse de chute
            }
        }

        //changement des coordonnées de l'axel pour montrer que les actions du joueur modifient son emplacement.
        x += dx;
        y += dy;
    }

    public Block plusProcheBlock() { //trouve le bloc le plus proche sous Axel
        Block nearestBlock = null; //initiasation le bloc le plus proche a nul 

        // Parcourir les blocs dans l'ordre inverse
        List<Block> reverseBlocks = new ArrayList<>(field.getBlocks());
        for (int i = reverseBlocks.size() - 1; i >= 0; i--) {
            Block block = reverseBlocks.get(i);

            // Vérifie si le bloc est sous ou au même niveau qu'Axel si above on fait rien
            if (block.getY() <= this.y) { 
                // Vérifie si Axel est aligné horizontalement avec le bloc
                if (this.x >= block.getX() && this.x <= block.getRight()) {
                    nearestBlock = block; // Trouve le bloc le plus proche
                    break; // On s'arrête dès qu'un bloc valide est trouvé
                }
            }
        }

        //si aucun bloc trouvé, retourne un bloc par défaut
        if (nearestBlock != null) {
            return nearestBlock;
        } else {
            return new Block(0, 0, 0);
        }
    }
    
    //verifie les collisions de axel avec un bloc donné
    public void checkCollision(Block b) { // si x est verticalement aligne avec un bloc ou horixontalement it means il est sur le bloc
        if (b != null) {
            double verticalDistance = Math.abs(y - b.getY()); //distance verticale entre axel et le bloc
            
            if (y > altitudeMax) { //met a jour l'altitude maximale atteinte
                altitudeMax = y;
            }
            
            if (x >= b.getX() && x <= b.getRight() && verticalDistance <= 10 && dy <= 0) { //verifie si axel est aligné horizontalement et proche verticalement du bloc
                if (b.getPiques()) { // regarde si il y a des piques
                    surviving = false; // le jeu se finit si on touche
                    return; //comme om a une fonction void om met juste return pour sortir de la fonction
                }

                y = b.getY(); // y devient le meme que le bloc
                dy = 0.0; // falling speed est 0 car il ne tombe plus (sur le bloc)
                falling = false; 
                return;
            }
        }
        falling = true;       
    }
    //donc si le x de Axel est dans le range du x du bloc (width) et l'y du Axel est proche du y du bloc Axel est sur le block

    public int score() { //le score est base sur the highest point reached 
        return this.altitudeMax - 40;
    }

    //NOUVEAU
    public void update() {
        computeMove();
        checkCollision(plusProcheBlock());
        score();
    }

}