import javax.swing.*;
import java.awt.event.MouseEvent;
public class Game {
    static final int SQR_SIZE = Board.SQR_SIZE;
    static final int BOARD_WIDTH = Board.BOARD_WIDTH;
    static final int BOARD_HEIGHT = Board.BOARD_HEIGHT;
    static final int MENU_HEIGHT = Board.MENU_HEIGHT;
    static final int BOMB_AMOUNT = Board.BOMB_AMOUNT;
    Board board;
    Square hoveredSquare, clickedSquare;
    public Game(Board board) {
        this.board = board;
    }
    public static void addSquares() {
        for (int sY = MENU_HEIGHT / SQR_SIZE; sY < (BOARD_HEIGHT + MENU_HEIGHT) / SQR_SIZE; sY ++) {
            for (int sX = 0; sX < BOARD_WIDTH / SQR_SIZE; sX ++) {
                Board.squareList(sX, sY);
            }
        }
    }
    public static void addBombs() {
        for (int i = 0; i < BOMB_AMOUNT; i ++) {
            Board.bombList(0, 0);
        }
    }
    public void mousePressed(MouseEvent e) {
        clickedSquare = Board.getSquare(e.getX(), e.getY());
        if (clickedSquare != null) {
            if (SwingUtilities.isRightMouseButton(e)) {
                clickedSquare.flag = !clickedSquare.flag;
            } else if (!clickedSquare.flag) {
                clickedSquare.click = Square.Click.CLICK;
            }
        }
        board.repaint();
    }
    public void mouseReleased() {}
    public void mouseClicked() {}
    public void mouseExited() {
        if (hoveredSquare != null) {
            hoveredSquare.setHover(Square.Hover.NORMAL);
        }
        board.repaint();
    }
    public void mouseEntered() {
        if (hoveredSquare != null) {
            hoveredSquare.setHover(Square.Hover.HOVERED);
        }
        board.repaint();
    }
    public void mouseDragged() {}
    public void mouseMoved(MouseEvent e) {
        Square currentHoveredSquare = Board.getSquare(e.getX(), e.getY());
        if (hoveredSquare != currentHoveredSquare) {
            if (hoveredSquare != null) {
                hoveredSquare.setHover(Square.Hover.NORMAL);
            }
            if (currentHoveredSquare != null) {
                currentHoveredSquare.setHover(Square.Hover.HOVERED);
            }
            hoveredSquare = currentHoveredSquare;
            board.repaint();
        }
    }
}