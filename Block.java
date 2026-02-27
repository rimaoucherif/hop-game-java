public class Block {
    private final int x, y;
    private final int width;

    private boolean piques; // Nouveau

    public Block(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.piques = false; //pas de piques par defaut
    }

    public int getX() { //gauche
        return x;
    }

    public int getY() { //altitude
        return y;
    }

    public int getWidth() {
        return width;
    }

    public boolean getPiques() { 
        return piques;
    }

    public void setPiques(boolean piques) { 
        this.piques = piques;
    }

    public int getRight() { return x + width; }//NOUVEAU pour savoir the righmost position du bloc pour savoir le range qu'il prend (pour checkcollison dans Axel)
}