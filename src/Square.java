import java.util.LinkedList;
public class Square {
    enum Hover {
        NORMAL, HOVERED
    }
    enum Click {
        NOT_CLICK, CLICK
    }
    enum Number {
        BOMB, ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
    }
    Hover hover = Hover.NORMAL;
    Click click = Click.NOT_CLICK;
    Number number = Number.ZERO;
    static final int SQR_SIZE = Board.SQR_SIZE;
    int x, y, sX, sY;
    LinkedList<Square> sq;
    boolean flag = false;
    public Square(int sX, int sY, LinkedList<Square> sq) {
        this.x = sX * SQR_SIZE;
        this.y = sY * SQR_SIZE;
        this.sX = sX;
        this.sY = sY;
        this.sq = sq;
        sq.add(this);
    }
    public void setHover(Hover hover) {
        this.hover = hover;
    }
    public void setNumber(Number number) {
        this.number = number;
    }
}