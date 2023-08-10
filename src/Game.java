import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Game {
    enum State {
        SPAWN, PLAY, LOSE, WIN
    }
    State state = State.SPAWN;
    static final int SQR_SIZE = Board.SQR_SIZE;
    static final int BOARD_WIDTH = Board.BOARD_WIDTH;
    static final int BOARD_HEIGHT = Board.BOARD_HEIGHT;
    static final int MENU_HEIGHT = Board.MENU_HEIGHT;
    static final int BOMB_AMOUNT = Board.BOMB_AMOUNT;
    Board board;
    Square hoveredSquare, clickedSquare;
    private int flagsPlaced = 0;
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
    public void addBombs(Square clickedSquare) {
        state = State.PLAY;

        Random random = new Random();
        int bombsToSpawn = BOMB_AMOUNT;

        while (bombsToSpawn > 0) {
            int randomX = random.nextInt(BOARD_WIDTH / SQR_SIZE);
            int randomY = random.nextInt((BOARD_HEIGHT + MENU_HEIGHT) / SQR_SIZE - MENU_HEIGHT / SQR_SIZE) + MENU_HEIGHT / SQR_SIZE;

            Square s = Board.getSquare(randomX * SQR_SIZE, randomY * SQR_SIZE);

            if (s == clickedSquare || isAdjacent(s, clickedSquare)) {
                continue;
            }

            if (s != null && s.number != Square.Number.BOMB) {
                s.setNumber(Square.Number.BOMB);
                bombsToSpawn--;
            }
        }
    }
    private boolean isAdjacent(Square s1, Square s2) {
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

        for (int i = 0; i < dx.length; i++) {
            int adjX = s1.sX + dx[i];
            int adjY = s1.sY + dy[i];

            if (adjX == s2.sX && adjY == s2.sY) {
                return true;
            }
        }

        return false;
    }
    public void addNumbers() {
        for (Square s : Board.sq) {
            s.flag = false;
            if (s.number != Square.Number.BOMB) {
                int bombCount = countAdjacentBombs(s);
                if (bombCount > 0) {
                    s.setNumber(Square.Number.values()[bombCount]);
                }
            }
        }
    }
    private static int countAdjacentBombs(Square square) {
        int bombCount = 0;
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

        for (int i = 0; i < dx.length; i++) {
            int adjX = square.sX + dx[i];
            int adjY = square.sY + dy[i];

            Square adjacentSquare = Board.getSquare(adjX * SQR_SIZE, adjY * SQR_SIZE);
            if (adjacentSquare != null && adjacentSquare.number == Square.Number.BOMB) {
                bombCount++;
            }
        }
        return bombCount;
    }
    public void click(Square s) {
        if (s.click == Square.Click.NOT_CLICK) {
            s.click = Square.Click.CLICK;
            if (s.number == Square.Number.ZERO) {
                revealEmpty(s);
            }
        }
        if (s.number == Square.Number.BOMB) {
            removeFlags();
            s.click = Square.Click.NOT_CLICK;
            state = State.LOSE;
        }
        if (winCondition()) state = State.WIN;
    }
    public void revealEmpty(Square square) {
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

        for (int i = 0; i < dx.length; i++) {
            int adjX = square.sX + dx[i];
            int adjY = square.sY + dy[i];

            Square adjacentSquare = Board.getSquare(adjX * SQR_SIZE, adjY * SQR_SIZE);
            if (adjacentSquare != null && adjacentSquare.click == Square.Click.NOT_CLICK) {
                adjacentSquare.click = Square.Click.CLICK;
                if (adjacentSquare.number == Square.Number.ZERO) {
                    revealEmpty(adjacentSquare);
                }
            }
        }
    }
    public void removeFlags() {
        for (Square s : Board.sq) {
            if (s.number == Square.Number.BOMB && s.flag) {
                s.flag = false;
                flagsPlaced--;
            }
        }
    }
    public boolean winCondition() {
        for (Square s : Board.sq) {
            return s.number != Square.Number.BOMB && s.click == Square.Click.CLICK && flagsPlaced == BOMB_AMOUNT;
        }
        return false;
    }
    public void mousePressed(MouseEvent e) {
        clickedSquare = Board.getSquare(e.getX(), e.getY());
        if (clickedSquare != null && clickedSquare.click == Square.Click.NOT_CLICK) {
            if (SwingUtilities.isRightMouseButton(e)) {
                if (!clickedSquare.flag && flagsPlaced < BOMB_AMOUNT) {
                    clickedSquare.flag = true;
                    flagsPlaced++;
                } else if (clickedSquare.flag) {
                    clickedSquare.flag = false;
                    flagsPlaced--;
                }
            } else if (!clickedSquare.flag) {
                if (state == State.SPAWN) {
                    addBombs(clickedSquare);
                    addNumbers();
                }
                click(clickedSquare);
            }
        }
        board.repaint();
    }
    public int getFlagsPlaced() {
        return flagsPlaced;
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