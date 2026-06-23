import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class JeuSnake extends JPanel implements ActionListener, KeyListener {
    public class Case {
        int x;
        int y;

        Case(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int Hauteur;
    int Largeur;
    int TailleDesCases = 25;
    int nb_tour = 0;

    Case snakeHead;
    Case snakeHead2;

    Case obstacle;
    Case obstacle2;
    Case obstacle3;

    Case fraise;
    Random random;
    Random random1;
    Random random2;
    Random random3;

    ArrayList<Case> snakeBody;
    ArrayList<Case> snakeBody2;

    // game logic
    Timer gameLoop;
    Timer delayTimer;
    Timer delayTimer2;
    int mouvementX; // mouvement x du joueur 1
    int mouvementY; // mouvement y du joueur 1
    int mouvementX2; // mouvement x du joueur 2
    int mouvementY2; // // mouvement y du joueur 2
    int score1; // score du joueur 1
    int score2; // score du joueur 2
    boolean Egalite = false; // égalité
    boolean VictoireJoueur1 = false; // joueur 1 qui gagne
    boolean VictoireJoueur2 = false; // joueur 2 qui gagne
    boolean gameRestart = false; // redémarrer la partie

    String N_s = JOptionPane.showInputDialog("“How many tiles before the snake grows?");// la variable qui contient
                                                                                        // JOptionPane ne peut pas être
                                                                                        // de type int
    int N = Integer.parseInt(N_s);// transforme la variable i_age de type String en type int

    JeuSnake(int Hauteur, int Largeur) {
        this.Hauteur = Hauteur;
        this.Largeur = Largeur;
        setPreferredSize(new Dimension(this.Hauteur, this.Largeur));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Case(3, 10);
        snakeHead2 = new Case(21, 10);
        snakeBody = new ArrayList<Case>();
        snakeBody2 = new ArrayList<Case>();

        fraise = new Case(10, 10);
        obstacle = new Case(10, 10);
        obstacle2 = new Case(13, 15);
        obstacle3 = new Case(20, 4);
        random = new Random();
        random1 = new Random();
        random2 = new Random();
        random3 = new Random();
        placeFraise();
        placeObstacle();

        mouvementX = 1;
        mouvementY = 0;
        mouvementX2 = -1;
        mouvementY2 = 0;

        score1 = 0;
        score2 = 0;

        gameLoop = new Timer(200, this);// indique au timer à quelle fréquence elle doit s'éxecuter, ici 100
                                        // millisecondes
        // et toutes les 100 mls nous allons appellé l'actionPerformed
        gameLoop.start();

        delayTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delayTimer.stop();
                resetGame();
                gameLoop.start();
            }
        });
        delayTimer2 = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delayTimer2.stop();
                resetGame2();
                gameLoop.start();
            }
        });

    }

    public void placeObstacle() {
        obstacle.x = random1.nextInt(Largeur / TailleDesCases);
        obstacle.y = random1.nextInt(Hauteur / TailleDesCases);
        while (obstacle.y == 9 || obstacle.y == 10 || obstacle.y == 11) {
            obstacle.x = random1.nextInt(Largeur / TailleDesCases);
            obstacle.y = random1.nextInt(Hauteur / TailleDesCases);
        }
        obstacle2.x = random2.nextInt(Largeur / TailleDesCases);
        obstacle2.y = random2.nextInt(Hauteur / TailleDesCases);
        while (obstacle2.y == 9 || obstacle2.y == 10 || obstacle2.y == 11) {
            obstacle2.x = random2.nextInt(Largeur / TailleDesCases);
            obstacle2.y = random2.nextInt(Hauteur / TailleDesCases);
        }
        obstacle3.x = random3.nextInt(Largeur / TailleDesCases);
        obstacle3.y = random3.nextInt(Hauteur / TailleDesCases);
        while (obstacle3.y == 9 || obstacle3.y == 10 || obstacle3.y == 11) {
            obstacle3.x = random3.nextInt(Largeur / TailleDesCases);
            obstacle3.y = random3.nextInt(Hauteur / TailleDesCases);
        }

    }

    public void placeFraise() {
        fraise.x = random.nextInt(Largeur / TailleDesCases);
        fraise.y = random.nextInt(Hauteur / TailleDesCases);
        while (fraise.y == 9 || fraise.y == 10 || fraise.y == 11) {
            fraise.x = random.nextInt(Largeur / TailleDesCases);
            fraise.y = random.nextInt(Hauteur / TailleDesCases);
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        g.setColor(Color.white);
        g.drawString("Score : ", 10, 20);
        g.setColor(Color.green);
        g.drawString(score1 + " ", 70, 20);
        g.setColor(Color.white);
        g.drawString("-" + " ", 90, 20);
        g.setColor(Color.blue);
        g.drawString(score2 + "", 110, 20);
        // + "-" + " " + score2,, velocityX2, velocityX);
    }

    public void draw(Graphics g) {
        // Dessiner des lignes
        for (int i = 0; i < Largeur / TailleDesCases; i++) {
            // x1,y1,x2,y2
            g.drawLine(i * TailleDesCases, 0, i * TailleDesCases, Hauteur);
            g.drawLine(0, i * TailleDesCases, Largeur, i * TailleDesCases);
        }

        // Food
        g.setColor(Color.red);
        g.fillOval(fraise.x * TailleDesCases, fraise.y * TailleDesCases, TailleDesCases, TailleDesCases);

        // obstacle
        g.setColor(Color.gray);
        g.fillRect(obstacle.x * TailleDesCases, obstacle.y * TailleDesCases, TailleDesCases, TailleDesCases);
        g.setColor(Color.gray);
        g.fillRect(obstacle2.x * TailleDesCases, obstacle2.y * TailleDesCases, TailleDesCases, TailleDesCases);
        g.setColor(Color.gray);
        g.fillRect(obstacle3.x * TailleDesCases, obstacle3.y * TailleDesCases, TailleDesCases, TailleDesCases);

        // Snake Head
        g.setColor(Color.green);
        Color DarkGreen = new Color(0, 153, 0);
        g.setColor(DarkGreen);
        g.fillRect(snakeHead.x * TailleDesCases, snakeHead.y * TailleDesCases, TailleDesCases, TailleDesCases);
        g.setColor(Color.blue);
        g.fillRect(snakeHead2.x * TailleDesCases, snakeHead2.y * TailleDesCases, TailleDesCases, TailleDesCases);

        // Snake Body
        for (int i = 0; i < snakeBody.size(); i++) {
            Case snakePart = snakeBody.get(i);
            g.setColor(Color.green);
            g.fillRect(snakePart.x * TailleDesCases, snakePart.y * TailleDesCases, TailleDesCases, TailleDesCases);
        }
        for (int i = 0; i < snakeBody2.size(); i++) {
            Case snakePart2 = snakeBody2.get(i);
            g.setColor(Color.blue);
            Color LightBlue = new Color(51, 153, 255);
            g.setColor(LightBlue);
            g.fillRect(snakePart2.x * TailleDesCases, snakePart2.y * TailleDesCases, TailleDesCases, TailleDesCases);
        }
        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (Egalite) {
            g.setColor(Color.red);
            g.drawString("Egalité", TailleDesCases - 16, TailleDesCases + 16);
        }
        if (VictoireJoueur1) {
            g.setColor(Color.green);
            g.drawString("Manche gagné par le joueur 1", TailleDesCases - 16, TailleDesCases + 16);
        }
        if (VictoireJoueur2) {
            g.setColor(Color.blue);
            g.drawString("Manche gagné par le joueur 2", TailleDesCases - 16, TailleDesCases + 16);
        }
        if (score1 == 3) {
            gameLoop.stop();
            g.setColor(Color.green);
            g.drawString("Victoire du joueur 1", TailleDesCases - 16, TailleDesCases + 16);
        }
        if (score2 == 3) {
            gameLoop.stop();
            g.setColor(Color.blue);
            g.drawString("Victoire du joueur 2", TailleDesCases - 16, TailleDesCases + 16);
        }
    }

    public boolean collision(Case Pixel1, Case Pixel2) {
        return Pixel1.x == Pixel2.x && Pixel1.y == Pixel2.y;
    }

    public void move() {
        // obstacle
        if (collision(snakeHead, obstacle) || collision(snakeHead, obstacle2) || collision(snakeHead, obstacle3)) {
            if (collision(snakeHead2, obstacle) || collision(snakeHead2, obstacle2)
                    || collision(snakeHead2, obstacle3)) {
                Egalite = true;
            } else {
                VictoireJoueur2 = true;
            }
        }
        if (collision(snakeHead2, obstacle) || collision(snakeHead2, obstacle2)
                || collision(snakeHead2, obstacle3)) {
            if (collision(snakeHead, obstacle) || collision(snakeHead, obstacle2)
                    || collision(snakeHead, obstacle3)) {
                Egalite = true;
            } else {
                VictoireJoueur1 = true;
            }
        }
        // mange une fraise
        if (collision(snakeHead, fraise)) {
            if (!snakeBody.isEmpty()) {
                snakeBody.remove(snakeBody.size() - 1);
            }

            placeFraise();
        }
        if (collision(snakeHead2, fraise)) {
            if (!snakeBody2.isEmpty()) {
                snakeBody2.remove(snakeBody2.size() - 1);
            }

            placeFraise();
        }

        if (nb_tour == N) {
            snakeBody.add(new Case(snakeHead.x, snakeHead.y));
            snakeBody2.add(new Case(snakeHead2.x, snakeHead2.y));
            nb_tour = 0;
        }

        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Case snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Case prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        for (int i = snakeBody2.size() - 1; i >= 0; i--) {
            Case snakePart2 = snakeBody2.get(i);
            if (i == 0) {
                snakePart2.x = snakeHead2.x;
                snakePart2.y = snakeHead2.y;
            } else {
                Case prevSnakePart2 = snakeBody2.get(i - 1);
                snakePart2.x = prevSnakePart2.x;
                snakePart2.y = prevSnakePart2.y;
            }
        }

        // snake head
        snakeHead.x += mouvementX;
        snakeHead2.x += mouvementX2;
        snakeHead.y += mouvementY;
        snakeHead2.y += mouvementY2;
        if (gameLoop.isRunning()) {
            nb_tour++;
            System.out.println("tour= " + nb_tour);
        }

        // Condition Game Over
        for (int i = 0; i < snakeBody.size(); i++) {
            Case snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                VictoireJoueur2 = true;
            }
        }
        for (int i = 0; i < snakeBody2.size(); i++) {
            Case snakePart2 = snakeBody2.get(i);
            if (collision(snakeHead2, snakePart2)) {
                VictoireJoueur1 = true;
            }
        }
        for (int i = 0; i < snakeBody.size(); i++) {
            Case snakePart = snakeBody.get(i);
            if (collision(snakeHead2, snakePart)) {
                VictoireJoueur1 = true;
            }
        }
        for (int i = 0; i < snakeBody2.size(); i++) {
            Case snakePart2 = snakeBody2.get(i);
            if (collision(snakeHead, snakePart2)) {
                VictoireJoueur2 = true;
            }
        }
    }

    public void resetGame() {
        snakeHead.x = 3;
        snakeHead.y = 10;
        snakeHead2.x = 21;
        snakeHead2.y = 10;
        snakeBody.clear();
        placeFraise();
        placeObstacle();
        snakeBody2.clear();
        Egalite = false;
        VictoireJoueur1 = false;
        VictoireJoueur2 = false;
        mouvementX = 1;
        mouvementY = 0;
        mouvementX2 = -1;
        mouvementY2 = 0;
        snakeHead.x += mouvementX;
        snakeHead.y += mouvementY;
        snakeHead2.x += mouvementX2;
        snakeHead2.y += mouvementY2;
        nb_tour = 0;
    }

    public void resetGame2() {
        snakeHead.x = 3;
        snakeHead.y = 10;
        snakeHead2.x = 21;
        snakeHead2.y = 10;
        snakeBody.clear();
        placeFraise();
        placeObstacle();
        snakeBody2.clear();
        Egalite = false;
        VictoireJoueur1 = false;
        VictoireJoueur2 = false;
        gameRestart = false;
        mouvementX = 1;
        mouvementY = 0;
        mouvementX2 = -1;
        mouvementY2 = 0;
        snakeHead.x += mouvementX;
        snakeHead.y += mouvementY;
        snakeHead2.x += mouvementX2;
        snakeHead2.y += mouvementY2;
        score1 = 0;
        score2 = 0;
        nb_tour = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        ConditionsDeVictoire();
        SortieDeTerrain();
    }

    public void ConditionsDeVictoire() {
        if (Egalite) {
            handleDraw();
        }
        if (VictoireJoueur1) {
            handlePlayer1Win();
        }
        if (VictoireJoueur2) {
            handlePlayer2Win();
        }
        if (gameRestart) {
            handleGameRestart();
        }
    }

    public void handleDraw() {
        VictoireJoueur1 = false;
        VictoireJoueur2 = false;
        gameLoop.stop();
        delayTimer.start();
    }

    public void handlePlayer1Win() {
        VictoireJoueur2 = false;
        gameLoop.stop();
        delayTimer.start();
        score1 += 1;
    }

    public void handlePlayer2Win() {
        VictoireJoueur1 = false;
        gameLoop.stop();
        delayTimer.start();
        score2 += 1;
    }

    public void handleGameRestart() {
        gameLoop.stop();
        delayTimer2.start();
    }

    public void SortieDeTerrain() {
        if (snakeHead.x * TailleDesCases < 0 || snakeHead.x * TailleDesCases > Largeur
                || snakeHead.y * TailleDesCases < 0 || snakeHead.y * TailleDesCases > Hauteur) {
            if (snakeHead2.x * TailleDesCases < 0 || snakeHead2.x * TailleDesCases > Largeur
                    || snakeHead2.y * TailleDesCases < 0 || snakeHead2.y * TailleDesCases > Hauteur) {
                Egalite = true; // Les deux serpents sont sortis, donc c'est une égalité
            } else {
                VictoireJoueur2 = true;
            }
        }
        if (snakeHead2.x * TailleDesCases < 0 || snakeHead2.x * TailleDesCases > Largeur
                || snakeHead2.y * TailleDesCases < 0 || snakeHead2.y * TailleDesCases > Hauteur) {
            if (snakeHead.x * TailleDesCases < 0 || snakeHead.x * TailleDesCases > Largeur
                    || snakeHead.y * TailleDesCases < 0 || snakeHead.y * TailleDesCases > Hauteur) {
                Egalite = true;
            } else {
                VictoireJoueur1 = true;
            }
        }
        if (snakeHead.x * TailleDesCases == snakeHead2.x * TailleDesCases
                && snakeHead.y * TailleDesCases == snakeHead2.y * TailleDesCases) {
            Egalite = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameLoop.isRunning()) {
            MouvementduJoueur1(e);
            MouvementduJoueur2(e);
        }

        PauseEtRestart(e);
    }

    public void MouvementduJoueur1(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Z && mouvementY != 1) {
            mouvementX = 0;
            mouvementY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_S && mouvementY != -1) {
            mouvementX = 0;
            mouvementY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_Q && mouvementX != 1) {
            mouvementX = -1;
            mouvementY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_D && mouvementX != -1) {
            mouvementX = 1;
            mouvementY = 0;
        }
    }

    public void MouvementduJoueur2(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && mouvementY2 != 1) {
            mouvementX2 = 0;
            mouvementY2 = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && mouvementY2 != -1) {
            mouvementX2 = 0;
            mouvementY2 = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && mouvementX2 != 1) {
            mouvementX2 = -1;
            mouvementY2 = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && mouvementX2 != -1) {
            mouvementX2 = 1;
            mouvementY2 = 0;
        }
    }

    public void PauseEtRestart(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameLoop.isRunning()) {
                gameLoop.stop();
            } else if (!gameLoop.isRunning() && !Egalite && !VictoireJoueur1 && !VictoireJoueur2 && score1 != 3
                    && score2 != 3) {
                gameLoop.start();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            gameRestart = true;
        }
    }

    // pas besoin, juste les définir
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    // quadrillage 600*600 pixels ou 25 carré vers le bas et 24 vers la droite
    // x = horizontale et y = verticale
}
