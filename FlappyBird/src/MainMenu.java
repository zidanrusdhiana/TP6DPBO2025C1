import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    // Constants for the frame size
    private static final int FRAME_WIDTH = 360;
    private static final int FRAME_HEIGHT = 640;

    // Components
    private JPanel mainPanel;
    private JButton playButton;
    private JLabel titleLabel;

    public MainMenu() {
        // Setup frame properties
        setTitle("Flappy Bird");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setResizable(false);

        // Initialize components
        initComponents();
    }

    private void initComponents() {
        // Create main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());

        // Create custom panel for the background with null layout
        JPanel backgroundPanel = new CustomBackgroundPanel();
        backgroundPanel.setLayout(null);

        // Create title label
        titleLabel = new JLabel("FLAPPY BIRD");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 120, FRAME_WIDTH, 60);
        backgroundPanel.add(titleLabel);

        // Create play button with improved styling
        playButton = new JButton("PLAY");
        playButton.setFont(new Font("Arial", Font.BOLD, 28));
        playButton.setForeground(Color.WHITE);
        playButton.setBackground(new Color(76, 175, 80)); // Nicer green
        playButton.setFocusPainted(false);
        playButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        playButton.setOpaque(true);

        // Add hover effect to the button
        playButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playButton.setBackground(new Color(105, 190, 105)); // Lighter green
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                playButton.setBackground(new Color(76, 175, 80)); // Original green
            }
        });

        // Position the play button in the center of the frame
        playButton.setBounds(FRAME_WIDTH/2 - 100, FRAME_HEIGHT/2, 200, 60);
        backgroundPanel.add(playButton);

        // Add action listener to the play button
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // Add a subtle footer text
        JLabel footerLabel = new JLabel("Press SPACE to fly · Press R to restart");
        footerLabel.setForeground(new Color(255, 255, 255, 180));
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setBounds(0, FRAME_HEIGHT - 100, FRAME_WIDTH, 20);
        backgroundPanel.add(footerLabel);

        // Add copyright text
        JLabel copyrightLabel = new JLabel("© 2025 Flappy Bird by Mochamad Zidan Rusdhiana");
        copyrightLabel.setForeground(new Color(255, 255, 255, 150));
        copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        copyrightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        copyrightLabel.setBounds(0, FRAME_HEIGHT - 70, FRAME_WIDTH, 20);
        backgroundPanel.add(copyrightLabel);

        // Add the background panel to the main panel
        mainPanel.add(backgroundPanel, BorderLayout.CENTER);

        // Set the panel as content pane
        setContentPane(mainPanel);
    }

    private void startGame() {
        // Close the main menu
        dispose();

        // Start the game
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame gameFrame = new JFrame("Flappy Bird");
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.add(new FlappyBird());
                gameFrame.pack();
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setResizable(false);
                gameFrame.setVisible(true);
            }
        });
    }

    // Custom panel class to draw the background that matches the game
    private class CustomBackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            // Draw sky background
            g2d.setColor(new Color(4, 156, 176)); // Turquoise blue
            g2d.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

            // Add some stars in the sky
            g2d.setColor(Color.WHITE);
            drawStars(g2d);

            // Draw city silhouette
            drawCity(g2d);

            // Draw ground
            g2d.setColor(new Color(83, 168, 52)); // Green grass
            g2d.fillRect(0, FRAME_HEIGHT - 100, FRAME_WIDTH, 100);

            // Draw side pipes like in the game
            drawSidePipes(g2d);
        }

        private void drawStars(Graphics2D g2d) {
            // Draw several small stars randomly
            g2d.setColor(new Color(255, 255, 255, 200));

            int[] starX = {20, 60, 120, 200, 250, 300, 320, 100, 170, 220};
            int[] starY = {30, 70, 50, 40, 90, 60, 120, 200, 150, 220};

            for (int i = 0; i < starX.length; i++) {
                g2d.fillRect(starX[i], starY[i], 2, 2);
            }

            // Add a few cross-shaped stars
            g2d.drawLine(150, 100, 154, 104);
            g2d.drawLine(150, 104, 154, 100);

            g2d.drawLine(270, 180, 274, 184);
            g2d.drawLine(270, 184, 274, 180);
        }

        private void drawCity(Graphics2D g2d) {
            // Draw city silhouette at the bottom
            g2d.setColor(new Color(0, 89, 107)); // Darker blue

            // Draw several buildings of varying heights
            int baseY = FRAME_HEIGHT - 100;

            // First set of buildings
            for (int i = 0; i < FRAME_WIDTH; i += 30) {
                int height = 20 + (int)(Math.random() * 30);
                g2d.fillRect(i, baseY - height, 25, height);

                // Add windows
                g2d.setColor(new Color(255, 255, 150, 100));
                for (int y = baseY - height + 5; y < baseY - 2; y += 10) {
                    for (int x = i + 5; x < i + 20; x += 10) {
                        g2d.fillRect(x, y, 3, 3);
                    }
                }
                g2d.setColor(new Color(0, 89, 107));
            }
        }

        private void drawSidePipes(Graphics2D g2d) {
            // Draw side pipes like those in the game

            // Right top pipe
            g2d.setColor(new Color(76, 175, 80)); // Green
            g2d.fillRect(FRAME_WIDTH - 30, 0, 30, 100);

            // Lighter green stripe on pipe
            g2d.setColor(new Color(129, 199, 132)); // Lighter green
            g2d.fillRect(FRAME_WIDTH - 10, 0, 10, 100);

            // Left bottom pipe
            g2d.setColor(new Color(76, 175, 80)); // Green
            g2d.fillRect(0, FRAME_HEIGHT - 150, 30, 50);

            // Lighter green stripe on pipe
            g2d.setColor(new Color(129, 199, 132)); // Lighter green
            g2d.fillRect(20, FRAME_HEIGHT - 150, 10, 50);
        }
    }
}