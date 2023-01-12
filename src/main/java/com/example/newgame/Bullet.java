package com.example.newgame;


import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Bullet extends Entity{

    private Image image = new Image("bullet.png");

    public Bullet(Image image, double positionX, double positionY) {
        super(positionX, positionY);
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Rectangle rec = new Rectangle(image.getWidth(),image.getHeight());
}