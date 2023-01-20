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

    //reload time, moving diagonal, enemy


    Image firstPositionR = new Image("gangster1.png");
    Image secondPositionR = new Image("gangster2.png");
    Image firstPositionL = new Image("gangster3.png");
    Image secondPositionL = new Image("gangster4.png");

    Image firstPositionRHurt = new Image("gangster1Hurt.png");
    Image secondPositionRHurt = new Image("gangster2Hurt.png");
    Image firstPositionLHurt = new Image("gangster3Hurt.png");
    Image secondPositionLHurt = new Image("gangster4Hurt.png");

    Image firstPositionREnemy = new Image("enemy1.png");
    Image secondPositionREnemy = new Image("enemy2.png");
    Image firstPositionLEnemy = new Image("enemy3.png");
    Image secondPositionLEnemy = new Image("enemy4.png");

    Image firstPositionREnemyHurt = new Image("enemy1Hurt.png");
    Image secondPositionREnemyHurt = new Image("enemy2Hurt.png");
    Image firstPositionLEnemyHurt = new Image("enemy3Hurt.png");
    Image secondPositionLEnemyHurt = new Image("enemy4Hurt.png");


    Image bulletImage = new Image("bullet.png");
    Image bulletImage2 = new Image("bullet2.png");
    Image ammoImage = new Image("ammo.png");

    Image gameOverImage = new Image("gameOverScreen.png");
    Image background = new Image("background.jpg");
    Player player = new Player(200,400, firstPositionR);
    Player enemy = new Player(600, 400, firstPositionLEnemy);
    Bullet bullet = new Bullet(bulletImage, player.getPositionX()+player.getImage().getWidth(), player.getPositionY()+player.getImage().getHeight()/2);
    Bullet ammo = new Bullet(ammoImage, width - ammoImage.getWidth()-60, ammoImage.getHeight()-5);

    private double xBackground = 0;
    private int speed = 10;
    private int vspeed = 0;
    private double lastGroundPosition = 0;
    public final static int MAXYPOS = 420;
    public final static int MINYPOS = 360;
    public final static int MAXAMMO = 9;
    private int currentAmmo = MAXAMMO;
    public final static int MAXHEALTH = 100;
    private int currentHealth = MAXHEALTH;
    private int prevHealth = currentHealth;

    private int enemyHealth = MAXHEALTH-30;

    Canvas canvas = new Canvas(width, height);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Timeline tl = new Timeline(new KeyFrame(Duration.millis(50), e -> run(gc)));
    Timeline bulletTime = new Timeline(new KeyFrame(Duration.millis(1), e ->run1(gc)));
    Timeline breathingTime = new Timeline(new KeyFrame(Duration.millis(100), e -> run2()));


    private boolean is2ndImage = true;
    private boolean is2ndImage2 = true;
    private boolean is2ndImageEnemy = true;
    private boolean is2ndImage2Enemy = true;

    private boolean right = true;
    private boolean enemyRight = false;
    private boolean inAirBullet = false;
    private boolean inAir = false;

    public void start(Stage stage) throws IOException {
        stage.setTitle("Shoot-game");
        stage.setResizable(false);

        canvas.requestFocus();




        tl.setCycleCount(Timeline.INDEFINITE);

        bulletTime.setCycleCount(Timeline.INDEFINITE);

        breathingTime.setCycleCount(Timeline.INDEFINITE);

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
                        player.setPositionX(player.getPositionX() - speed);
                    } else if (player.getPositionX() >= 957 - (speed * 12)) {
                        player.setPositionX(player.getPositionX() - speed);
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
                        player.setPositionX(player.getPositionX() + speed);
                    } else if (player.getPositionX() <= speed * 12) {
                        player.setPositionX(player.getPositionX() + speed);
                    }
                }
            }

            else if (keyEvent.getCode() == KeyCode.W ) {
                if (player.getPositionY()-speed >= MINYPOS && !inAir)
                player.setPositionY(player.getPositionY() - speed);
            }

            else if (keyEvent.getCode() == KeyCode.S ) {
                if (player.getPositionY() + speed <= MAXYPOS && !inAir)
                player.setPositionY(player.getPositionY() + speed);
            }


            else if (keyEvent.getCode() == KeyCode.SPACE) {
                if (!inAir) {
                    vspeed = -20;
                    inAir = true;
                    lastGroundPosition = player.getPositionY();
                }
            }

            else if (keyEvent.getCode() == KeyCode.R){
                currentAmmo = MAXAMMO;
            }
            else if (keyEvent.getCode() == KeyCode.P){
                currentHealth = 0;
            }

            else if (keyEvent.getCode() == KeyCode.L){
                currentHealth -= 10;
            }


            else if (keyEvent.getCode() == KeyCode.CONTROL){
                        if (!inAirBullet && currentAmmo > 0) {
                            inAirBullet = true;
                            bulletTime.play();
                            currentAmmo--;
                            bullet.setPositionX(player.getPositionX() + (right ? player.getImage().getWidth() : -10));
                            bullet.setPositionY(player.getPositionY()+player.getImage().getHeight()/2);
                        }
            }
        });
            tl.play();
            breathingTime.play();
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


        if (inAir){
            vspeed += 2;
            player.setPositionY(player.getPositionY()+vspeed);
            if (player.getPositionY()>=lastGroundPosition) {
                inAir = false;
                vspeed = 0;
                player.setPositionY(lastGroundPosition);
            }
        }

        gc.drawImage(player.getImage(), player.getPositionX(), player.getPositionY());
        gc.drawImage(enemy.getImage(), enemy.getPositionX(), enemy.getPositionY());
        gc.drawImage(ammo.getImage(), ammo.getPositionX(), ammo.getPositionY());

        //gc.drawImage(, 100, 200);
        //gc.setFill(Color.WHITE);
        gc.setFont(new Font(20));
        gc.fillText("AMMO", ammo.getPositionX()-75, ammo.getPositionY()+15);
        gc.setFont(new Font(20));
        gc.fillText(currentAmmo+"/∞", ammo.getPositionX()+15, ammo.getPositionY()+15);


    }
    private void run1(GraphicsContext gc){
            if (bullet.rect().getBoundsInParent().intersects(enemy.rect().getBoundsInParent())){
                enemy.setPositionX(enemy.getPositionX()+5);
                enemyHealth-= 10;
                if (enemyRight) {
                    enemy.setImage(is2ndImageEnemy ? firstPositionREnemyHurt : secondPositionREnemyHurt);
                    is2ndImageEnemy = !is2ndImageEnemy;
                } else {
                    enemy.setImage(is2ndImage2Enemy ? firstPositionLEnemyHurt : secondPositionLEnemyHurt);
                    is2ndImage2Enemy = !is2ndImage2Enemy;
                }

                enemy.setPositionX(enemyHealth <= 0 ? -3000 : enemy.getPositionX());
                bullet.setPositionX(-3000);
                inAirBullet = false;
            }
           else if (inAirBullet && bullet.getPositionX() + bullet.getImage().getWidth() <= width && bullet.getPositionX() >= 0) {
                gc.drawImage(bullet.getImage(), bullet.getPositionX(), bullet.getPositionY());
                bullet.setPositionX(bullet.getPositionX() + (right ? 1:-1));
            }

            else {
                bullet.setPositionX(-3000);
                inAirBullet = false;
            }

    }

    private void run2(){
        if (currentHealth >= prevHealth) {
            if (right) {
                player.setImage(is2ndImage ? firstPositionR : secondPositionR);
                is2ndImage = !is2ndImage;
            } else {
                player.setImage(is2ndImage2 ? firstPositionL : secondPositionL);
                is2ndImage2 = !is2ndImage2;
            }
        }

        else {
            if (currentHealth <= 0) {
                gc.drawImage(gameOverImage, 0, 0, width, height);
                tl.stop();
                bulletTime.stop();
                breathingTime.stop();
            } else if (currentHealth < prevHealth) {
                prevHealth = currentHealth;
                if (right) {
                    player.setImage(is2ndImage ? firstPositionRHurt : secondPositionRHurt);
                    is2ndImage = !is2ndImage;
                } else {
                    player.setImage(is2ndImage2 ? firstPositionLHurt : secondPositionLHurt);
                    is2ndImage2 = !is2ndImage2;
                }
            }
        }
        if (enemyHealth > 0) {
            if (enemyRight) {
                enemy.setImage(is2ndImageEnemy ? firstPositionREnemy : secondPositionREnemy);
                is2ndImage = !is2ndImage;
            }   else {
                enemy.setImage(is2ndImage2Enemy ? firstPositionLEnemy : secondPositionLEnemy);
                is2ndImage2Enemy = !is2ndImage2Enemy;
            }
        }

    }

    public static void main(String[] args) {launch();}
    }