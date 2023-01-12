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
    private static final int height = 498;

    //890px background

    Image firstPositionR = new Image("gangster1.png");
    Image secondPositionR = new Image("gangster2.png");
    Image firstPositionL = new Image("gangster3.png");
    Image secondPositionL = new Image("gangster4.png");
    Image background = new Image("background.jpg");
    Image bulletImage = new Image("bullet.png");
    Image bulletImage2 = new Image("bullet2.png");
    Image ammoImage = new Image("ammo.png");

    Player player = new Player(200,400);
    Bullet bullet = new Bullet(bulletImage, player.getPositionX()+player.getImage().getWidth(), player.getPositionY()+player.getImage().getHeight()/2);
    Bullet ammo = new Bullet(ammoImage, width - ammoImage.getWidth()-60, ammoImage.getHeight()-5);

    double xBackground = 0;
    int speed = 10;


    private boolean is2ndImage = true;
    private boolean is2ndImage2 = true;
    private boolean right = true;

    private boolean inAirBullet = false;

    public void start(Stage stage) throws IOException {
        stage.setTitle("Shoot-game");
        stage.setResizable(false);

        Canvas canvas = new Canvas(width, height);
        canvas.requestFocus();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(250), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        Timeline bulletTime = new Timeline(new KeyFrame(Duration.millis(10), e ->run1(gc)));
        bulletTime.setCycleCount(Timeline.INDEFINITE);

        Scene scene = new Scene(new StackPane(canvas));
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {

            if (keyEvent.getCode() == KeyCode.A) {
                //player.setPositionX(player.getPositionX() - speed);

                if (right) {
                    right = false;
                    bullet.setImage(bulletImage2);
                }
                else {
                    xBackground+= speed;
                    if (xBackground == 0 || xBackground > 0 && player.getPositionX() > 0) {
                        player.setPositionX(player.getPositionX() - speed * 4);
                    } else if (player.getPositionX() >= 957 - (speed * 12)) {
                        player.setPositionX(player.getPositionX() - speed * 4);
                    }
                }
            }
            else if (keyEvent.getCode() == KeyCode.D) {
                if (!right) {
                    right = true;
                    bullet.setImage(bulletImage);
                }
                else {
                    xBackground -= speed;
                    if (xBackground == -1270 || xBackground == -1280 && player.getPositionX() < 957) {
                        player.setPositionX(player.getPositionX() + speed * 4);
                    } else if (player.getPositionX() <= speed * 12) {
                        player.setPositionX(player.getPositionX() + speed * 4);
                    }
                }
            }
            else if (keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.SPACE) {
                player.setPositionY(player.getPositionY() - speed);
            }
            else if (keyEvent.getCode() == KeyCode.CONTROL){
                    if (right) {
                        if (!inAirBullet) {
                            inAirBullet = true;
                            bulletTime.play();
                            bullet.setPositionX(player.getPositionX() + player.getImage().getWidth());
                        }
                    }
                    else {
                        if (!inAirBullet) {
                            inAirBullet = true;
                            bulletTime.play();
                            bullet.setPositionX(player.getPositionX()-15);
                        }
                    }

            }

        });
            tl.play();
            stage.setScene(scene);
            stage.show();
            stage.getIcons().add(new Image("icon.png"));

    }



    private void run(GraphicsContext gc) {

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        while (xBackground > 0)
        {
            //xBackground -= 890;
            xBackground = 0;
        }
        while (xBackground < -1270){
            //xBackground += 890;
            xBackground = -1270;
        }
        gc.drawImage(background, xBackground, 0);
        if (right) {
            player.setImage(is2ndImage ? firstPositionR : secondPositionR);
            is2ndImage = !is2ndImage;
        }
        else {
            player.setImage(is2ndImage2 ? firstPositionL : secondPositionL);
            is2ndImage2 = !is2ndImage2;
        }
        gc.drawImage(player.getImage(), player.getPositionX(), player.getPositionY());
        gc.drawImage(ammo.getImage(), ammo.getPositionX(), ammo.getPositionY());

        //gc.drawImage(, 100, 200);
        //gc.setFill(Color.WHITE);
        gc.setFont(new Font(20));
        gc.fillText("AMMO", ammo.getPositionX()-75, ammo.getPositionY()+15);
        gc.setFont(new Font(20));
        gc.fillText("∞/∞", ammo.getPositionX()+15, ammo.getPositionY()+15);

    }
    private void run1(GraphicsContext gc){

        if (right) {
            if (inAirBullet && bullet.getPositionX() + bullet.getImage().getWidth() <= width) {
                gc.drawImage(bullet.getImage(), bullet.getPositionX(), bullet.getPositionY());
                bullet.setPositionX(bullet.getPositionX() + 100);
            } else {
                bullet.setPositionX(-3000);
                inAirBullet = false;
            }
        }
        else {
            if (inAirBullet && bullet.getPositionX() >= 0) {
                gc.drawImage(bullet.getImage(), bullet.getPositionX(), bullet.getPositionY());
                bullet.setPositionX(bullet.getPositionX() - 100);
            } else {
                bullet.setPositionX(-3000);
                inAirBullet = false;
            }
        }
    }

    public static void main(String[] args) {launch();}
    }