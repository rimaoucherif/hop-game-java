import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener{
    private static final int BLOCK_HEIGHT = 10;
    private static final int AXEL_WIDTH = 10;
    private static final int AXEL_HEIGHT = 10;

    private final Axel axel;
    private final Field field;
   
    public GamePanel(Field field, Axel axel) { //initialise l'interface graphique du jeu
        this.field = field;
        this.axel = axel;

        setPreferredSize(new Dimension(field.width, field.height)); // definit la taille du panneau graphique

        setFocusable(true); //rend gamepanel capable de recevoir les touches sur le clavier car il ya des compsants qui ne peuvent pas interagir

        requestFocusInWindow(); //NOUVEAU : Demander explicitement le focus uniquement si un composant est focusable

        addKeyListener(this); //pour activer la connection entre les keypressed et l'axel des que le jeu demarre
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //clears the screen before drawing (illusion de defilement)
    
        Color rose = new Color(240, 150, 170);
        g.setColor(rose);

         //dessiner le font
         ImageIcon background = new ImageIcon("wallpaper3.jpg.jpeg");
         g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
    
        //dessiner les blocks
        for (Block block : field.getBlocks()) {
            g.fillRect(block.getX(), field.height - (block.getY() - field.getBottom() - 20), block.getWidth(), BLOCK_HEIGHT);
    
            if (block.getPiques()) { 
                g.setColor(Color.WHITE); //piques blanches car on ne voit pas les noirs(couleur qu'on voulait faire au debut)
                int piqueWidth = 5; 
                for (int i = block.getX(); i < block.getX() + block.getWidth(); i += piqueWidth) {
                    int piqueHeight = 10; 
                    int[] xPoints = {i, i + piqueWidth / 2, i + piqueWidth}; 
                    int[] yPoints = {
                        field.height - (block.getY() - field.getBottom() - 20), //son bottome
                        field.height - (block.getY() - field.getBottom() - 20) - piqueHeight, //son top
                        field.height - (block.getY() - field.getBottom() - 20) //sa base
                    };
                    g.fillPolygon(xPoints, yPoints, 3); //dessine un triangle
                }
                g.setColor(rose);
            }
        }
    
        //Axel
        Color green = new Color(119, 190, 119);
        g.setColor(green); 
        g.fillOval(axel.getX(), field.height - (axel.getY() - AXEL_HEIGHT - field.getBottom()), AXEL_WIDTH, AXEL_HEIGHT);
    
        //Level
        g.setColor(Color.WHITE);
        g.setFont(new Font("Instrument", Font.BOLD, 20));
        g.drawString("Level: " + field.getLevel(), 10, 30);
    
        // Score
        g.setColor(Color.WHITE); 
        g.drawString("Score: " + axel.score(), 10, 50); 
    
        //game over
        if (!axel.getSurviving()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Instrument", Font.BOLD, 50));
            String gameOverTxt = "GAME OVER";
    
            int textWidth = g.getFontMetrics().stringWidth(gameOverTxt);
            int textHeight = g.getFontMetrics().getHeight();
            g.drawString(gameOverTxt, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2);
        }
    }

   /* Fonction modifiant l'état de l'axel lorsqu'une des touches fléchée et actionnée. */
    public void keyPressed(KeyEvent e) {
      switch (e.getKeyCode()) {
       case KeyEvent.VK_LEFT:
          this.axel.setLeft(true);
          break;
       case KeyEvent.VK_UP:
          this.axel.setJump(true);
          break;
       case KeyEvent.VK_RIGHT:
          this.axel.setRight(true);
          break;
       case KeyEvent.VK_DOWN:
          this.axel.setDiving(true);
          break;
      }
    }

   /* Fonction annulant l'état de l'axel lorsqu'une des touches fléchée et actionnée. */ //donc detec quand une touche est released et stop axel from bouger
    public void keyReleased(KeyEvent e) {
      switch (e.getKeyCode()) {
       case KeyEvent.VK_LEFT: //commence a bouger left
          this.axel.setLeft(false);
          break;
       case KeyEvent.VK_UP: 
          this.axel.setJump(false);
          break;
       case KeyEvent.VK_RIGHT:
          this.axel.setRight(false);
          break;
       case KeyEvent.VK_DOWN:
          this.axel.setDiving(false);
          break;
      }
    }

   public void keyTyped(KeyEvent e) { }

}