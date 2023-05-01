import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import java.util.Random;

public class GamePanel extends JPanel implements AncestorListener, ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNIT = (SCREEN_HEIGHT * SCREEN_WIDTH) / UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNIT];
    final int y[] = new int[GAME_UNIT];
    int stem_length = 5;
    int stem_width = 2;
    int bodyParts = 6;
    int applesEaten;
    int applesX;
    int applesY;
    char direction = 'R';
    boolean running = false;

    Timer timer;
    Random random;

    /**
     * 
     */
    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_HEIGHT, SCREEN_WIDTH));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            // Apple added as 2 oval
            g.setColor(Color.red);
            g.fillOval(applesX, applesY, (int) (UNIT_SIZE * 0.60), UNIT_SIZE);
            g.fillOval(applesX + (int) (UNIT_SIZE * 0.40), applesY, (int) (UNIT_SIZE * 0.60), UNIT_SIZE);
            // Apple's stem added as rectangle
            g.setColor(Color.green);
            g.fillRect(applesX + UNIT_SIZE / 2 - stem_width / 2, applesY, stem_width, stem_length);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));

                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("INK FREE ", Font.BOLD, 50));
            FontMetrics metrics = getFontMetrics(getFont());
            g.drawString("SCORE " + applesEaten, ((SCREEN_WIDTH / 2) - metrics.stringWidth("SCORE " + applesEaten)) / 2,
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }

    }

    public void newApple() {
        applesX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        applesY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    /**
     * 
     */
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if ((x[0] == applesX) && (y[0] == applesY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // check if head touches left border
        if (x[0] < 0) {
            running = false;
        }
        // check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // check if head touches top border
        if (y[0] < 0) {
            running = false;
        }
        // chech if head touches down border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        // SCORE

        g.setColor(Color.red);
        g.setFont(new Font("INK FREE ", Font.BOLD, 50));
        FontMetrics metrics2 = getFontMetrics(getFont());
        g.drawString("SCORE " + applesEaten, ((SCREEN_WIDTH / 2) - metrics2.stringWidth("SCORE " + applesEaten)) / 2,
                g.getFont().getSize());
        // GAME OVER
        g.setColor(Color.red);
        g.setFont(new Font("INK FREE ", Font.BOLD, 70));
        FontMetrics metrics1 = getFontMetrics(getFont());
        g.drawString("GAME OVER ", ((SCREEN_WIDTH / 2) - metrics1.stringWidth("GAME OVER ")) / 2, SCREEN_HEIGHT / 2);
    }

    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }

    }

    @Override
    public void ancestorAdded(AncestorEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ancestorAdded'");
    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ancestorRemoved'");
    }

    @Override
    public void ancestorMoved(AncestorEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ancestorMoved'");
    }

}