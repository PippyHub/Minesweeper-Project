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
    private final Image[] imagesNum;
    private final Game game;
    public Board() {
        images = img.loadImages();
        imagesNum = img.loadNumberImages();
        game = new Game(this);
        Game.addSquares();
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    public static void squareList(int sX, int sY) {
        new Square(sX, sY, sq);
    }
    @Override
    public void paint(Graphics g) {
        menu(g);
        background(g);
        highlight(g);
        square(g);
    }
    public void menu(Graphics g) {
        g.setColor(new Color(74, 117, 44));
        g.fillRect(0, 0, BOARD_WIDTH, MENU_HEIGHT);

        int centerX = (BOARD_WIDTH - images[0].getWidth(this)) / 2;
        int centerY = (MENU_HEIGHT - images[0].getHeight(this)) / 2;
        g.drawImage(images[0], centerX, centerY, this);

        int imageSpacing = 0;
        int secondImageX = centerX + images[0].getWidth(this) + imageSpacing;
        int thirdImageX = secondImageX + imagesNum[1].getWidth(this) - 20;
        int imageY = (MENU_HEIGHT - imagesNum[0].getHeight(this)) / 2;

        int number = BOMB_AMOUNT - game.getFlagsPlaced();

        String numberStr = Integer.toString(number);

        char digit1;
        char digit2;

        if (number > 9) {
            digit1 = numberStr.charAt(0);
            digit2 = numberStr.charAt(1);
        } else {
            digit1 = '0';
            digit2 = numberStr.charAt(0);
        }

        int digit1Int = Character.getNumericValue(digit1);
        int digit2Int = Character.getNumericValue(digit2);

        g.drawImage(imagesNum[digit1Int], secondImageX, imageY, this);
        g.drawImage(imagesNum[digit2Int], thirdImageX, imageY, this);
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
            /*if (s.number == Square.Number.BOMB) {
                g.setColor(Color.magenta);
                g.fillRect(s.sX * SQR_SIZE, s.sY * SQR_SIZE, SQR_SIZE, SQR_SIZE);
            }*/
            if (s.flag) {
                g.drawImage(images[0], s.x, s.y, this);
            } else if (s.click == Square.Click.CLICK) {
                if (s.number != Square.Number.BOMB) {
                    int num;
                    switch (s.number) {
                        case ONE -> num = 1;
                        case TWO -> num = 2;
                        case THREE -> num = 3;
                        case FOUR -> num = 4;
                        case FIVE -> num = 5;
                        case SIX -> num = 6;
                        case SEVEN -> num = 7;
                        case EIGHT -> num = 8;
                        default -> num = 0;
                    }
                    if (num > 0) {
                        g.drawImage(imagesNum[num], s.x, s.y, this);
                    }
                }
            }
            if (s.number == Square.Number.BOMB && game.state == Game.State.LOSE) {
                g.setColor(Color.red);
                int centerX = s.sX * SQR_SIZE + SQR_SIZE / 2;
                int centerY = s.sY * SQR_SIZE + SQR_SIZE / 2;
                int radius = SQR_SIZE / 4;
                g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
            }
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
    public boolean gameOver() {
        return game.state != Game.State.LOSE && game.state != Game.State.WIN;
    }
    @Override
    public void actionPerformed(ActionEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
         if (gameOver()) game.mousePressed(e);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameOver()) game.mouseReleased();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameOver()) game.mouseClicked();
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
        if (gameOver()) game.mouseDragged();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        game.mouseMoved(e);
    }
}