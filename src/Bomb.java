import java.util.LinkedList;
public class Bomb {
    static final int SQR_SIZE = Board.SQR_SIZE;
    int x, y, pX, pY;
    LinkedList<Bomb> bo;
    public Bomb(int pX, int pY, LinkedList<Bomb> bo) {
        this.x = pX * SQR_SIZE;
        this.y = pY * SQR_SIZE;
        this.pX = pX;
        this.pY = pY;
        this.bo = bo;
        bo.add(this);
    }
}