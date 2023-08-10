import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;
public class Board extends JPanel implements ActionListener, MouseListener {
    static final int SQR_SIZE = 64;
    static final int SQR_AMOUNT = 10;
    static final int BOARD_SIZE = SQR_SIZE * SQR_AMOUNT;
    static LinkedList<Bomb> bo = new LinkedList<>();
    //Images img = new Images();
    //private final Image[] images;
    private final Game game;
    int buttonWidth, buttonHeight, buttonX, buttonY, buttonArc;
    public Board() {
        //images = img.loadImages();
        game = new Game(this);
        addMouseListener(this);
    }
    public void bombList(int bX, int bY) {
        new Bomb(bX, bY, bo);
    }
    public void paint(Graphics g) {
        g.setColor(new Color(86, 86, 86));
        g.fillRect(0, 0, BOARD_SIZE, BOARD_SIZE);
        drawGrid(g, SQR_AMOUNT, BOARD_SIZE, 0, 0);
    }
    public void drawGrid(Graphics g, int gridSize, int size, int startX, int startY) {
        g.setColor(Color.BLACK);
        int cellSize = size / gridSize;

        startX = (startX / cellSize) * cellSize;
        startY = (startY / cellSize) * cellSize;

        for (int x = startX; x <= size + startX; x += cellSize) {
            g.drawLine(x, startY, x, size);
        }
        for (int y = startY; y <= size + startY; y += cellSize) {
            g.drawLine(startX, y, startX + size, y);
        }
    }
    public void actionPerformed(ActionEvent e) {}
    public void mousePressed(MouseEvent e) {
        game.mousePressed();
    }
    public void mouseReleased(MouseEvent e) {
        game.mouseReleased();
    }
    public void mouseClicked(MouseEvent e) {
        game.mouseClicked();
    }
    public void mouseExited(MouseEvent e) {
        game.mouseExited();
    }

    public void mouseEntered(MouseEvent e) {
        game.mouseEntered();
    }
}