import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Test {
  public static void main(String[] args){
    int Hauteur = 600;
    int Largeur = 600;

    JFrame fenetre = new JFrame();

    //Définit un titre pour notre fenêtre
    fenetre.setTitle("Snake");
    //Définit sa taille : 400 pixels de large et 100 pixels de haut
    fenetre.setSize(Largeur,Hauteur);
    //Nous demandons maintenant à notre objet de se positionner au centre
    fenetre.setLocationRelativeTo(null);
    //Termine le processus lorsqu'on clique sur la croix rouge
    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fenetre.setResizable(false);
    //Et enfin, la rendre visible
    fenetre.setVisible(true);

    JeuSnake jeuSnake = new JeuSnake(Hauteur,Largeur);
    fenetre.add(jeuSnake);
    fenetre.pack();
    jeuSnake.requestFocus();
  }
}
