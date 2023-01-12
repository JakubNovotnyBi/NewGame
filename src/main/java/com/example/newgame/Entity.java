package com.example.newgame;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;


public class Entity{
        Image image;
        private double positionX;
        private double positionY;

        public Entity(double positionX, double positionY) {
            this.positionX = positionX;
            this.positionY = positionY;
        }

        public double getPositionX() {
            return positionX;
        }

        public void setPositionX(double positionX) {
            this.positionX = positionX;
        }

        public double getPositionY() {
            return positionY;
        }

        public void setPositionY(double positionY) {
            this.positionY = positionY;
        }

    }
