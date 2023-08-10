import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;
public class Board extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    static final int SQR_SIZE = 30;
    static final int BOARD_WIDTH = SQR_SIZE * 24;
    static final int BOARD_HEIGHT = SQR_SIZE * 20;
    static final int MENU_HEIGHT = SQR_SIZE * 3;
    static final int BOMB_AMOUNT = 99;
    static LinkedList<Square> sq = new LinkedList<>();
    Images img = new Images();
    private final Image[] images;
    private final Game game;
    public Board() {
        images = img.loadImages();
        game = new Game(this);
        Game.addSquares();
        Game.addBombs();
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    public static void squareList(int sX, int sY) {
        new Square(sX, sY, sq);
    }
    public void paint(Graphics g) {
        menu(g);
        background(g);
        highlight(g);
        square(g);
    }
    public void menu(Graphics g) {
        g.setColor(new Color(74,117,44));
        g.fillRect(0,0,BOARD_WIDTH, MENU_HEIGHT);
        g.drawImage(images[0],BOARD_WIDTH / 2 , MENU_HEIGHT / 2, this);
    }
    public void background(Graphics g) {
        for (Square s : sq) {
            Color color;
            if (s.click == Square.Click.NOT_CLICK) {
                color = (s.sX + s.sY) % 2 == 0 ? new Color(170, 215, 80) : new Color(162, 209, 72);
            } else {
                color = (s.sX + s.sY) % 2 == 0 ? new Color(229, 194, 159) : new Color(215, 184, 153);
            }
            g.setColor(color);
            g.fillRect(s.sX * SQR_SIZE, s.sY * SQR_SIZE, SQR_SIZE, SQR_SIZE);
        }
    }
    public void highlight(Graphics g) {
        g.setColor(new Color(191,225,125));
        for (Square s : sq) {
            if (s.hover == Square.Hover.HOVERED && s.click == Square.Click.NOT_CLICK) {
                g.fillRect(s.sX * SQR_SIZE, s.sY * SQR_SIZE, SQR_SIZE, SQR_SIZE);
            }
        }
    }
    public void square(Graphics g) {
        for (Square s : sq) {
            if (s.flag) g.drawImage(images[0], s.x, s.y, this);
        }

    }
    public static Square getSquare(int x, int y) {
        int sX = x / SQR_SIZE;
        int sY = y / SQR_SIZE;
        for (Square s : sq) {
            if (s.sX == sX && s.sY == sY) return s;
        }
        return null;
    }
    @Override
    public void actionPerformed(ActionEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        game.mousePressed(e);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        game.mouseReleased();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        game.mouseClicked();
    }
    @Override
    public void mouseExited(MouseEvent e) {
        game.mouseExited();
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        game.mouseEntered();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        game.mouseDragged();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        game.mouseMoved(e);
    }
}