package com.example.newgame;


import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Bullet extends Entity{
    Image image = new Image("bullet.png");
    public Bullet(double positionX, double positionY) {
        super(positionX, positionY);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    public Rectangle rec = new Rectangle(image.getWidth(),image.getHeight());
}
