package com.example.asteroiddestroyer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite extends ImageView{

    boolean dead = false;
    String type;
    public Sprite(int x, int y, int W, int H, String type, String path){
        super(new Image(path, W, H, true, true));
        this.type=type;
        setTranslateX(x);
        setTranslateY(y);
    }
    public void moveLeft(){
        setTranslateX(getTranslateX()-10);
    }
    public void moveRight(){
        setTranslateX(getTranslateX()+10);
    }
    public void moveUp(){
        setTranslateY(getTranslateY()-6);
    }
    public void moveDown(){
        setTranslateY(getTranslateY()+4);
    }
}
