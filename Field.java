
import java.util.ArrayList;

public class Field {
    public static final int ALTITUDE_GAP = 80;  //chaque bloc est placé à une altitude différente, augmentée de 80 par rapport au bloc précédent
    public static final int START_ALTITUDE = 40; //altitude initiale à laquelle le premier bloc est placé. Elle est définie comme constante dans la classe 

    public final int width, height;
    private int bottom, top; // bottom and top altitude
    private ArrayList<Block> blocks; 

    private int[] AugAltitude = {0, 80, 800, 2000, 3200, 4800, 7200}; //apres chaque altitude on change de level, seuils d'altitude déclenche une progression de niveau
    
    //niveau actuel du jeu
    private int level = 1;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.blocks = new ArrayList<>();  //creeation d'une liste de blocks
        this.blocks.add(new Block(0, START_ALTITUDE, 400)); //ajouter premier block, position fixe

        for (int i = 0; i < this.height; i++) { //genere les blocks
            int altitude = START_ALTITUDE + i * ALTITUDE_GAP; //calculer l'altitude du bloc, augmenter par ALTITUDE_GAP a chaque itération
            int xPosition = (int) (Math.random() * width); // Position horizontale aléatoire dans la largeur du champ
            int blockWidth = 50 + (int) (Math.random() * 50); // Largeur du block aléatoire entre 50 et 100
            Block block = new Block(xPosition, altitude, blockWidth); //Création d'un nouveau bloc avec les paramètres générés

        //10% de chance d'avoir des piques
            if (Math.random() < 0.10) { 
                block.setPiques(true);
            }

        blocks.add(block);
        }
    }

    public ArrayList<Block> getBlocks() { //sert a return les blocks
        return blocks;
    }
    
    public int getBottom() {
        return this.bottom;
     }
  
    public int getTop() {
        return this.top;
    }

    public int getSTART_ALTITUDE() { //NOUVEAU
        return this.START_ALTITUDE;
    }

    public int getALTITUDE_GAP() { //retourne le gap entre l'altitude de chaque block
        return this.ALTITUDE_GAP;
    }

    public int getLevel() {
        return this.level;
    }

    public int[] getAugAltitude() { //retourne les altitude tresholds pour la pregression des levels
        return this.AugAltitude;
    }

    public int evolutionLevel() { // utilisée pour contrôler si le champ doit évoluer en fonction du niveau du jeu.
        if(level > 0) { 
            return 1;
        } else {
            return 0;
        }
    }

    // DIFICULTE 2: augmenter l altitudes bottom et top en fonction du niveau 
    public void grandirAltitude() { //pour agrandir l'altitude dans le processus du jeu 
        this.bottom = this.bottom + (level * evolutionLevel());
        this.top = this.top + (level *evolutionLevel());
         
    }

    public void augmenteLevel() { 
       level++; 
    }
    
    public void update() {
        grandirAltitude();
    }
}