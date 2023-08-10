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
    static LinkedList<Bomb> bo = new LinkedList<>();
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
    public static void bombList(int bX, int bY) {
        new Bomb(bX, bY, bo);
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
        boolean light = true;
        for (int boardY = MENU_HEIGHT / SQR_SIZE; boardY < (MENU_HEIGHT + BOARD_HEIGHT) / SQR_SIZE; boardY++) {
            for (int boardX = 0; boardX < BOARD_WIDTH / SQR_SIZE; boardX++) {
                Square s = getSquare(boardX * SQR_SIZE, boardY *  SQR_SIZE);
                if (s != null) {
                    Color color;
                    if (light) {
                        color = switch (s.click) {
                            case NOT_CLICK -> new Color(170, 215, 80);
                            case CLICK -> new Color(229, 194, 159);
                        };
                    } else {
                        color = switch (s.click) {
                            case NOT_CLICK -> new Color(162, 209, 72);
                            case CLICK -> new Color(215, 184, 153);
                        };
                    }
                    g.setColor(color);
                }
                g.fillRect(boardX * SQR_SIZE, boardY * SQR_SIZE, SQR_SIZE, SQR_SIZE);
                light = ! light;
            }
            light =! light;
        }
    }
    public void highlight(Graphics g) {
        g.setColor(new Color(191,225,125));
        for (Square s : sq) {
            if (s.hover == Square.Hover.HOVERED) {
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
    public static Bomb getBomb(int x, int y) {
        int sX = x / SQR_SIZE;
        int sY = y / SQR_SIZE;
        for (Bomb b : bo) {
            if (b.bX == sX && b.bY == sY ) return b;
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