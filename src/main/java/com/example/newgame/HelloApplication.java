package com.example.newgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {
    private static final int width = 1005;
    private static final int height = 605;

    Image firstPosition = new Image("gangster1.png");
    Image secondPosition = new Image("gangster2.png");
    Image background = new Image("background.jpg");
    Image bulletImage = new Image("bullet.png");
    Image ammoImage = new Image("bullet2.png");

    private boolean is2ndImage = true;

    Player player = new Player(20,500);
    int speed = 10;
    Bullet bullet = new Bullet(bulletImage,100,100);
    Bullet ammo = new Bullet(ammoImage, width - ammoImage.getWidth()-30, ammoImage.getHeight());
    public void start(Stage stage) throws IOException {
        stage.setTitle("Hello");
        stage.setResizable(false);

        Canvas canvas = new Canvas(width, height);
        canvas.requestFocus();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(250), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);


        Scene scene = new Scene(new StackPane(canvas));
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {

            if (keyEvent.getCode() == KeyCode.A) {
                player.setPositionX(player.getPositionX() - speed);
            }
            else if (keyEvent.getCode() == KeyCode.D) {
                player.setPositionX(player.getPositionX() + speed);
            }
            else if (keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.SPACE) {
                player.setPositionY(player.getPositionY() - speed);
            }
            else if (keyEvent.getCode() == KeyCode.CONTROL){

            }

        });
            tl.play();
            stage.setScene(scene);
            stage.show();

    }



    private void run(GraphicsContext gc) {

        gc.setFill(Color.BLACK);
        gc.fillRect(5, 5, width - 5, height - 5);
        gc.drawImage(background, 0, 0, width, height);
        player.setImage(is2ndImage ? firstPosition : secondPosition);
        is2ndImage = !is2ndImage;

        gc.drawImage(player.getImage(), player.getPositionX(), player.getPositionY());

        gc.drawImage(ammo.getImage(), ammo.getPositionX(), ammo.getPositionY());

        //gc.drawImage(, 100, 200);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(15));
        gc.fillText("AMMO", ammo.getPositionX()-50, 30);

    }
    public static void main(String[] args) {launch();}
    }