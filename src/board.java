
/**
 * Created by David on 11/17/2014.
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


    public class board extends JPanel implements ActionListener {

        private final int WIDTH = 300;
        private final int HEIGHT = 300;

        private final int DOT_SIZE = 10;
        private final int ALL_DOTS = 900;
        private final int RAND_POS = 29;
        //Set the speed of the snake by increasing the number you make the "Slimy" slower
        private final int SPEED = 100;

        private final int x[] = new int[ALL_DOTS];
        private final int y[] = new int[ALL_DOTS];

        private int dots;
        private int raseberry_x;
        private int raseberry_y;

        public board() {

            addKeyListener(new TAdapter());
            setBackground(Color.black);
            setFocusable(true);

            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            loadImages();
            initGame();
        }

        private Image ball;
        private Image raseberry;
        private Image head;
        private Image background;

        private void loadImages() {

            ImageIcon iball = new ImageIcon("dot.png");
            ball = iball.getImage();

            ImageIcon iraseberry = new ImageIcon("raseberry.png");
            raseberry = iraseberry.getImage();

            ImageIcon ihead = new ImageIcon("head.png");
            head = ihead.getImage();

            ImageIcon ibackground = new ImageIcon("space.png");
            background = ibackground.getImage();
        }

        private Timer timer;

        private void initGame() {

            dots = 3;

            for (int z = 0; z < dots; z++) {
                x[z] = 50 - z * 10;
                y[z] = 50;
            }

            locateRaseberry();

            timer = new Timer(SPEED, this);
            timer.start();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 5, 5, null);
            doDrawing(g);

        }

        private void doDrawing(Graphics g) {

            if (inGame) {

                g.drawImage(raseberry, raseberry_x, raseberry_y, this);

                for (int z = 0; z < dots; z++) {
                    if (z == 0) {
                        g.drawImage(head, x[z], y[z], this);
                    } else {
                        g.drawImage(ball, x[z], y[z], this);
                    }
                }

                Toolkit.getDefaultToolkit().sync();

            } else {

                gameOver(g);
            }
        }

        private void gameOver(Graphics g) {

            String endGame = "Game Over";
            Font small = new Font("Times New Roman", Font.BOLD, 16);
            FontMetrics style = getFontMetrics(small);

            g.setColor(Color.ORANGE);
            g.setFont(small);
            g.drawString(endGame, (WIDTH - style.stringWidth(endGame)) / 2, HEIGHT / 2);
        }

        private void checkRaseberry() {

            if ((x[0] == raseberry_x) && (y[0] == raseberry_y)) {

                dots++;
                locateRaseberry();
            }
        }

        private boolean left = false;
        private boolean right = true;
        private boolean up = false;
        private boolean down = false;
        private boolean inGame = true;

        private void move() {

            for (int z = dots; z > 0; z--) {
                x[z] = x[(z - 1)];
                y[z] = y[(z - 1)];
            }

            if (left) {
                x[0] -= DOT_SIZE;
            }

            if (right) {
                x[0] += DOT_SIZE;
            }

            if (up) {
                y[0] -= DOT_SIZE;
            }

            if (down) {
                y[0] += DOT_SIZE;
            }
        }

        private void checkCollision() {

            for (int z = dots; z > 0; z--) {

                if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                    inGame = false;
                }
            }

            if (y[0] >= HEIGHT) {
                inGame = false;
            }

            if (y[0] < 0) {
                inGame = false;
            }

            if (x[0] >= WIDTH) {
                inGame = false;
            }

            if (x[0] < 0) {
                inGame = false;
            }

            if(!inGame) {
                timer.stop();
            }
        }

        private void locateRaseberry() {

            int l = (int) (Math.random() * RAND_POS);
            raseberry_x = ((l * DOT_SIZE));

            l = (int) (Math.random() * RAND_POS);
            raseberry_y = ((l * DOT_SIZE));
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (inGame) {

                checkRaseberry();
                checkCollision();
                move();
            }

            repaint();
        }

        private class TAdapter extends KeyAdapter {

            @Override
            public void keyPressed(KeyEvent e) {

                int keyDirection = e.getKeyCode();

                if ((keyDirection == KeyEvent.VK_LEFT) && (!right)) {
                    left = true;
                    up = false;
                    down = false;
                }

                if ((keyDirection == KeyEvent.VK_RIGHT) && (!left)) {
                    right = true;
                    up = false;
                    down = false;
                }

                if ((keyDirection == KeyEvent.VK_UP) && (!down)) {
                    up = true;
                    right = false;
                    left = false;
                }

                if ((keyDirection == KeyEvent.VK_DOWN) && (!up)) {
                    down = true;
                    right = false;
                    left = false;
                }
            }
        }
    }