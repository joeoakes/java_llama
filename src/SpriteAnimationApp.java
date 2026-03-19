import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class SpriteAnimationApp extends JPanel {

    private BufferedImage spriteSheet;
    private final int frameWidth = 48;
    private final int frameHeight = 48;
    private final int totalFrames = 6;
    private final int columns = 2;

    private int currentFrame = 0;

    public SpriteAnimationApp() {
        try {
            spriteSheet = ImageIO.read(new File("src/llama.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Change frame every 150 ms
        Timer timer = new Timer(150, e -> {
            currentFrame = (currentFrame + 1) % totalFrames;
            repaint();
        });
        timer.start();

        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int col = currentFrame % columns;
        int row = currentFrame / columns;

        int sx = col * frameWidth;
        int sy = row * frameHeight;

        BufferedImage frame = spriteSheet.getSubimage(sx, sy, frameWidth, frameHeight);

        Graphics2D g2d = (Graphics2D) g;

        // Optional: make pixel art look crisp
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        int scale = 4;
        int drawWidth = frameWidth * scale;
        int drawHeight = frameHeight * scale;

        int x = (getWidth() - drawWidth) / 2;
        int y = (getHeight() - drawHeight) / 2;

        g2d.drawImage(frame, x, y, drawWidth, drawHeight, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sprite Sheet Animation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new SpriteAnimationApp());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
