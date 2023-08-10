import javax.swing.*;
import java.awt.*;
public class Panel extends JFrame {
    static final int BOARD_SIZE = Board.SQR_SIZE * Board.SQR_AMOUNT;;
    public static Board panel = new Board();

    public Panel() {
        setTitle("Minesweeper");
        this.getContentPane().setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.add(panel);

        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
}