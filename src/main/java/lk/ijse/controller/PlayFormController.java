package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayFormController {

    @FXML
    AnchorPane playFormPane;

    @FXML
    private AnchorPane graphicPane;

    private final int width = 747;
    private final int height = 426;
    private final int rows = 20;
    private final int columns = 20;
    private final int square_size = width/rows;
    private final int RIGHT = 0;
    private final int LEFT = 1;
    private final int UP = 2;
    private final int DOWN = 3;
    private final String [] food_paths = new String[]{
            "/assets/Foods/ic_apple.png",
            "/assets/Foods/ic_berry.png",
            "/assets/Foods/ic_cherry.png",
            "/assets/Foods/ic_coconut_.png",
            "/assets/Foods/ic_orange.png",
            "/assets/Foods/ic_peach.png",
            "/assets/Foods/ic_pomegranate.png",
            "/assets/Foods/ic_tomato.png",
            "/assets/Foods/ic_watermelon.png"
    };

    private GraphicsContext graphicsContext;
    private List<Point> snakeBody = new ArrayList<>();
    private Point snakeHead;
    private javafx.scene.image.Image foodImage;

    private int foodX;
    private int foodY;
    private boolean gameOver;
    private int currentDirection;
    Canvas canvas = new Canvas();
    public void initialize() {

        canvas = new Canvas(width, height);
        graphicPane.getChildren().add(canvas);

        canvas.setWidth(width);
        canvas.setHeight(height);

        snakeHead = new Point(width / 2 / square_size, height / 2 / square_size);
        snakeBody = new ArrayList<>();
        snakeBody.add(snakeHead); // Add head to body initially

        graphicsContext = canvas.getGraphicsContext2D();
        drawBackground();
        generateFood();
        drawFood(graphicsContext);
        drawSnake(graphicsContext);


    }

    private void drawBackground() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i + j) % 2 == 0) {
                    graphicsContext.setFill(Color.web("AAD751"));
                } else {
                    graphicsContext.setFill(Color.web("A2D149"));
                }
                graphicsContext.fillRect(i * square_size, j * square_size, square_size, square_size);
            }
        }
    }

    private void generateFood() {

        List<Point> availablePositions = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Point position = new Point(i, j);
                if (!snakeBody.contains(position)) {
                    availablePositions.add(position);
                }
            }
        }

        int randomIndex = (int) (Math.random() * availablePositions.size());
        Point foodPosition = availablePositions.get(randomIndex);
        foodX = (int) foodPosition.getX();
        foodY = (int) foodPosition.getY();

        foodImage = new Image(food_paths[(int) (Math.random() * food_paths.length)]);
    }

    private void drawFood(GraphicsContext graphicsContext){

        graphicsContext.drawImage(foodImage,foodX * square_size, foodY * square_size, square_size , square_size);

    }

    private void drawSnake(GraphicsContext graphicsContext){
        graphicsContext.setFill(Color.web("4674E9"));
        graphicsContext.fillRoundRect(snakeHead.getX() * square_size, snakeHead.getY() * square_size, square_size - 5, square_size - 5, 30, 30);

        for (int i = 1; i < snakeBody.size(); i++) {
            graphicsContext.fillRoundRect(snakeBody.get(i).getX() * square_size, snakeBody.get(i).getY() * square_size, square_size - 5,
                    square_size - 5, 20, 20);
        }
    }

}
