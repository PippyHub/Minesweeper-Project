import javax.swing.*;
import java.awt.*;
public class Panel extends JFrame {
    static final int BOARD_WIDTH = Board.BOARD_WIDTH;
    static final int BOARD_HEIGHT = Board.BOARD_HEIGHT;
    static final int MENU_HEIGHT = Board.MENU_HEIGHT;
    public static Board panel = new Board();

    public Panel() {
        setTitle("Minesweeper");
        this.getContentPane().setPreferredSize(new Dimension(BOARD_WIDTH, MENU_HEIGHT + BOARD_HEIGHT));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.add(panel);

        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
}