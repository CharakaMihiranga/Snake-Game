package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class PlayForm extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 533;
    private static final int ROWS = 32;
    private static final int COLUMNS = 32;
    private static final int SQUARE_SIZE = WIDTH / ROWS;
    private static final String[] FOODS_IMAGE = new String[]{
            "/assets/Foods/ic_apple.png",
            "/assets/Foods/ic_berry.png",
            "/assets/Foods/ic_cherry.png",
            "/assets/Foods/ic_coconut_.png",
            "/assets/Foods/ic_orange.png",
            "/assets/Foods/ic_peach.png",
            "/assets/Foods/ic_pomegranate.png",
            "/assets/Foods/ic_tomato.png",
            "/assets/Foods/ic_watermelon.png"};

    private static final String food = "/assets/ScoreFruit.png";

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private GraphicsContext gc;
    private List<Point> snakeBody = new ArrayList();
    private Point snakeHead;
    private Image foodImage;
    private int foodX;
    private int foodY;
    private boolean gameOver;
    private int currentDirection;
    private int score = 0;

    private double headXC = 200; // X-coordinate of snake head
    private double headYC = 200; // Y-coordinate of snake head
    private double headSize = 20; // Size of snake head
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Snake");

        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.RIGHT || code == KeyCode.D) {
                    if (currentDirection != LEFT) {
                        currentDirection = RIGHT;
                    }
                } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                    if (currentDirection != RIGHT) {
                        currentDirection = LEFT;
                    }
                } else if (code == KeyCode.UP || code == KeyCode.W) {
                    if (currentDirection != DOWN) {
                        currentDirection = UP;
                    }
                } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                    if (currentDirection != UP) {
                        currentDirection = DOWN;
                    }
                }
            }
        });

        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);
        generateFood();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Digital-7", 70));
            gc.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
            return;
        }
        drawBackground(gc);
        drawFood(gc);
        drawSnake(gc);
        setScore();

        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        switch (currentDirection) {
            case RIGHT:
                moveRight();
                break;
            case LEFT:
                moveLeft();
                break;
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
        }

        gameOver();
        eatFood();
    }

    private void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("AAD751"));
                } else {
                    gc.setFill(Color.web("A2D149"));
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    private void generateFood() {
        start:
        while (true) {

           int range = (20 - 1) + 1;
           int foodXValue = (int)(Math.random() * range) + 1;
           int foodYValue = (int) (Math.random() * range)+1;

           if (foodXValue < 28 && foodYValue < 28){
               foodX = foodXValue;
               foodY = foodYValue;

           }else{
               generateFood();
           }

            for (Point snake : snakeBody) {
                if (snake.getX() == foodX && snake.getY() == foodY) {
                    continue start;
                }
            }
            foodImage = new Image(food);
            break;
        }
    }

    private void drawFood(GraphicsContext gc) {
        gc.drawImage(foodImage, foodX * SQUARE_SIZE, foodY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

//    private void drawSnake(GraphicsContext gc) {
//
//        gc.setFill(Color.web("4674E9"));
//        gc.fillRoundRect(snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);
//
//        for (int i = 1; i < snakeBody.size(); i++) {
//            gc.fillRoundRect(snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1,
//                    SQUARE_SIZE - 1, 20, 20);
//        }
//    }

    private void drawSnake(GraphicsContext gc) {
        // Draw head
        gc.setFill(Color.web("4674E9"));
        gc.fillRoundRect(snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);

        // Draw eyes
        double eyeSize = SQUARE_SIZE * 0.2;
        gc.setFill(Color.BLACK);
        gc.fillOval((snakeHead.getX() + 0.2) * SQUARE_SIZE, (snakeHead.getY() + 0.2) * SQUARE_SIZE, eyeSize, eyeSize);
        gc.fillOval((snakeHead.getX() + 0.6) * SQUARE_SIZE, (snakeHead.getY() + 0.2) * SQUARE_SIZE, eyeSize, eyeSize);

        // Draw mouth
        gc.setFill(Color.RED);
        gc.fillOval((snakeHead.getX() + 0.3) * SQUARE_SIZE, (snakeHead.getY() + 0.6) * SQUARE_SIZE, SQUARE_SIZE * 0.4, SQUARE_SIZE * 0.2);

        // Draw body
        gc.setFill(Color.web("4674E9"));
        for (int i = 1; i < snakeBody.size(); i++) {
            gc.fillRoundRect(snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1,
                    SQUARE_SIZE - 1, 20, 20);
        }
    }


    private void moveRight() {
        snakeHead.x++;
    }

    private void moveLeft() {
        snakeHead.x--;
    }

    private void moveUp() {
        snakeHead.y--;
    }

    private void moveDown() {
        snakeHead.y++;
    }

    public void gameOver() {
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WIDTH || snakeHead.y * SQUARE_SIZE >= HEIGHT) {
            gameOver = true;
        }

        //destroy itself
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
                gameOver = true;
                break;
            }
        }
    }

    private void eatFood() {
        if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
            snakeBody.add(new Point(-1, -1));
            generateFood();
            score += 5;
        }
    }

    private void setScore() {
        // Load the image
        Image image = new Image("/assets/ScoreFruit.png");

        // Draw the image on the canvas
        gc.drawImage(image, 30, 20, 40, 40); // Adjust the coordinates and size as needed

        // Set text properties
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 35)); // Comic Sans, bold, size 45
        gc.setTextAlign(TextAlignment.LEFT);

        // Draw the score text
        gc.fillText(" "+ score, 50, 50); // Adjust the coordinates as needed
    }


}