import java.util.LinkedList;
public class Bomb {
    static final int SQR_SIZE = Board.SQR_SIZE;
    int x, y, bX, bY;
    LinkedList<Bomb> bo;
    public Bomb(int bX, int bY, LinkedList<Bomb> bo) {
        this.x = bX * SQR_SIZE;
        this.y = bY * SQR_SIZE;
        this.bX = bX;
        this.bY = bY;
        this.bo = bo;
        bo.add(this);
    }
}