import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Images {
    final int IMAGE_WIDTH = 500;
    final int IMAGE_NUMBER = 1;
    final int SUB_IMAGE_SIZE = IMAGE_WIDTH * IMAGE_NUMBER;
    private final Image[] images;
    public Images() {
        images = new Image[1];
    }
    public Image[] loadImages() {
        try {
            BufferedImage all = ImageIO.read(new File("src/Images/flag.png"));
            int index = 0;
            for (int x = 0; x < IMAGE_WIDTH; x += SUB_IMAGE_SIZE) {
                images[index] = all.getSubimage(x, 0, SUB_IMAGE_SIZE, SUB_IMAGE_SIZE)
                        .getScaledInstance(Board.SQR_SIZE, Board.SQR_SIZE, BufferedImage.SCALE_SMOOTH);
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return images;
    }
    public Image[] loadNumberImages(Color color) {
        Image[] numberImages = new Image[10];
        for (int i = 0; i < 10; i++) {
            BufferedImage numberImage = new BufferedImage
                    (Board.SQR_SIZE, Board.SQR_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = numberImage.createGraphics();
            g.setColor(color);
            int fontSize = 20;
            g.setFont(new Font("Arial", Font.PLAIN, fontSize));

            String numberText = String.valueOf(i);
            FontMetrics fontMetrics = g.getFontMetrics();
            int xCenter = (Board.SQR_SIZE - fontMetrics.stringWidth(numberText)) / 2;
            int yCenter = (Board.SQR_SIZE - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

            g.drawString(numberText, xCenter, yCenter);
            g.dispose();
            numberImages[i] = numberImage;
        }
        return numberImages;
    }
}