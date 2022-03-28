package com.model;

import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle {
    Random random;
    int xVelocity; //vận tốc bóng theo trục x
    int yVelocity; // vận tốc bóng theo trục y
    int initialSpeed = 5; // tốc độ ban đầu

    Ball(int x, int y, int width, int height) {
        super(x, y, width, height); // gọi hàm tạo rectangle
        random = new Random();
        int randomXDirection = random.nextInt(2);
        if(randomXDirection == 0) randomXDirection--;
        setXDirection(randomXDirection * initialSpeed);

        int randomYDirection = random.nextInt(2);
        if(randomYDirection == 0) randomYDirection--;
        setYDirection(randomYDirection * initialSpeed);
    }

    public void setXDirection(int randomXDirection){
        xVelocity = randomXDirection;
    }

    public void setYDirection(int randomYDirection){
        yVelocity = randomYDirection;
    }

    public void move(){
        x = x + xVelocity;
        y = y + yVelocity;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval(x,y,width,height);
    }
}