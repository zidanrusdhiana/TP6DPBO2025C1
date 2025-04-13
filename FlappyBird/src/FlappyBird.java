import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;
    Image gameOverImage;
    Image baseImage; // Base/lantai image

    // Base variables
    int baseHeight = 92;
    int baseY; // Y position of the base
    int baseX = 0; // Starting X position
    int baseSpeed = -2; // Speed of the base moving left

    // player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    ArrayList<Pipe> pipes;

    Timer gameLoop;
    int gravity = 1;
    Timer pipesCooldown;

    // Game state
    boolean gameOver = false;
    boolean gameStarted = false;

    // Score variables
    int score = 0;
    JLabel scoreLabel;
    Font scoreFont = new Font("Monospaced", Font.BOLD, 24);

    // Score display
    JPanel scorePanel;

    // constructor
    public FlappyBird() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.BLUE);
        setLayout(new BorderLayout());

        // Load images
        try {
            backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
            birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
            lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
            upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();
            gameOverImage = new ImageIcon(getClass().getResource("assets/gameover.png")).getImage();
            baseImage = new ImageIcon(getClass().getResource("assets/base.png")).getImage(); // Load base image
        } catch (Exception e) {
            System.out.println("Error loading images: " + e.getMessage());
        }

        // Set base position
        baseY = frameHeight - baseHeight;

        // Initialize game components
        initGame();

        // Setup score display
        setupScoreDisplay();
    }

    private void setupScoreDisplay() {
        scorePanel = new JPanel();
        scorePanel.setOpaque(false);
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(scoreFont);
        scoreLabel.setForeground(Color.WHITE);
        scorePanel.add(scoreLabel);

        this.add(scorePanel, BorderLayout.NORTH);
    }

    private void initGame() {
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();
        score = 0;
        gameOver = false;
        baseX = 0; // Reset base position

        if (gameLoop != null) {
            gameLoop.stop();
        }

        if (pipesCooldown != null) {
            pipesCooldown.stop();
        }

        pipesCooldown = new Timer(4010, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });

        gameLoop = new Timer(1000/60, this);

        // Start the game and pipe generation
        gameLoop.start();
        pipesCooldown.start();

        if (scoreLabel != null) {
            scoreLabel.setText("Score: 0");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);

        // Draw pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        // Draw base (draw it twice to create infinite scrolling effect)
        g.drawImage(baseImage, baseX, baseY, frameWidth, baseHeight, null);
        g.drawImage(baseImage, baseX + frameWidth, baseY, frameWidth, baseHeight, null);

        // Draw player
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        // Draw game over message if game is over
        if (gameOver) {
            // Semi-transparent background overlay
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, frameWidth, frameHeight);

            // Draw game over image
            int gameOverWidth = gameOverImage.getWidth(null);
            int gameOverHeight = gameOverImage.getHeight(null);

            // Center the game over image
            int gameOverX = (frameWidth - gameOverWidth) / 2;
            int gameOverY = (frameHeight / 2) - gameOverHeight - 30;

            g.drawImage(gameOverImage, gameOverX, gameOverY, null);

            // Draw score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 20));
            FontMetrics fm = g.getFontMetrics();
            String scoreText = "Score: " + score;
            int textWidth = fm.stringWidth(scoreText);
            g.drawString(scoreText, (frameWidth - textWidth) / 2, frameHeight / 2 + 10);

            // Draw restart instructions
            g.setFont(new Font("Monospaced", Font.PLAIN, 16));
            fm = g.getFontMetrics();
            String restartText = "Press 'R' to restart";
            textWidth = fm.stringWidth(restartText);
            g.drawString(restartText, (frameWidth - textWidth) / 2, frameHeight / 2 + 50);
        }
    }

    public void move() {
        if (gameOver) {
            return;
        }

        // Move player
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());

        // Check if player has hit the bottom of the frame or the base
        if (player.getPosY() + player.getHeight() > baseY) {
            player.setPosY(baseY - player.getHeight());
            gameOver();
        }

        // Check if player has hit the top of the frame
        if (player.getPosY() < 0) {
            player.setPosY(0);
            player.setVelocityY(0);
        }

        // Move pipes and check for collisions
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

            // Check for collision with pipe
            if (checkCollision(player, pipe)) {
                gameOver();
            }

            // Check if player has passed the pipe
            if (!pipe.getPassed() && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                // Only count score for upper pipes to avoid double counting
                if (pipe.getPosY() <= 0) {
                    score++;
                    updateScoreDisplay();
                }
                pipe.setPassed(true);
            }

            // Remove pipes that are out of the screen
            if (pipe.getPosX() + pipe.getWidth() < 0) {
                pipes.remove(i);
                i--;
            }
        }

        // Move the base (for infinite scrolling effect)
        baseX += baseSpeed;
        if (baseX <= -frameWidth) {
            baseX = 0;
        }
    }

    private void updateScoreDisplay() {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + score);
        }
    }

    private boolean checkCollision(Player player, Pipe pipe) {
        // Simple rectangle collision detection
        Rectangle playerRect = new Rectangle(
                player.getPosX(),
                player.getPosY(),
                player.getWidth(),
                player.getHeight()
        );

        Rectangle pipeRect = new Rectangle(
                pipe.getPosX(),
                pipe.getPosY(),
                pipe.getWidth(),
                pipe.getHeight()
        );

        return playerRect.intersects(pipeRect);
    }

    private void gameOver() {
        gameOver = true;
        gameLoop.stop();
        pipesCooldown.stop();
    }

    private void restartGame() {
        pipes.clear();
        initGame();
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
        repaint();
    }

    public void placePipes() {
        if (gameOver) {
            return;
        }

        // Adjust pipe height to account for base
        int effectiveHeight = frameHeight - baseHeight;
        int randomPosY = (int) (pipeStartPosY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = effectiveHeight/4;

        // Create upper pipe
        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        // Create lower pipe - adjust for base height
        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e){
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameOver) {
                player.setVelocityY(-10);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            if (gameOver) {
                restartGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        // Not used
    }
}