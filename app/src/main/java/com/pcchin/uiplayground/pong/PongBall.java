package com.pcchin.uiplayground.pong;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.pcchin.uiplayground.GameObject;
import com.pcchin.uiplayground.GeneralFunctions;
import com.pcchin.uiplayground.R;

class PongBall extends GameObject {
    // Velocity of game character (pixel/millisecond)
    private static float VELOCITY = 0.4f;

    private int movingVectorX = 10;
    private int movingVectorY = 5;

    private long lastDrawNanoTime =-1;

    private Paddle paddleL;
    private Paddle paddleR;
    private Bitmap pongImg;

    private PongSurfaceView pongSurfaceView;

    PongBall(@NonNull PongSurfaceView pongSurfaceView, int x, int y, Paddle paddleL, Paddle paddleR) {
        super(GeneralFunctions.getBitmap(R.drawable.white_circle, pongSurfaceView.getContext()), x, y);
        this.pongImg = GeneralFunctions.getBitmap(R.drawable.white_circle, pongSurfaceView.getContext());
        this.pongSurfaceView = pongSurfaceView;
        this.paddleL = paddleL;
        this.paddleR = paddleR;
        VELOCITY = 0.4f;
    }

    void update(){
        // Current time in nanoseconds
        long now = System.nanoTime();

        // Never once did draw.
        if(lastDrawNanoTime==-1) {
            lastDrawNanoTime= now;
        }
        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
        int deltaTime = (int) ((now - lastDrawNanoTime)/ 1000000 );

        // Distance moves
        float distance = VELOCITY * deltaTime;

        double movingVectorLength = Math.sqrt(movingVectorX* movingVectorX + movingVectorY*movingVectorY);

        // Calculate the new position of the game character.
        this.x = x +  (int)(distance* movingVectorX / movingVectorLength);
        this.y = y +  (int)(distance* movingVectorY / movingVectorLength);

        // Object collision with paddle (Exact Dimensions)
        if (((
                Math.abs(this.x + this.width/2 - paddleL.x - paddleL.width/2) < (this.width + this.paddleL.getWidth())/2
        ) && (
                Math.abs(this.y + this.height/2 - paddleL.y - paddleL.height/2) < (this.height + this.paddleL.getHeight())/2
        ))
                || ((
                Math.abs(this.x + this.width/2 - paddleR.x - paddleR.width/2) < (this.width + this.paddleR.getWidth())/2
        ) && (
                Math.abs(this.y + this.height/2 - paddleR.y - paddleR.height/2) < (this.height + this.paddleR.getHeight())/2
        ))) {
            this.movingVectorX = -this.movingVectorX;
        }

        // When the game's character touches the top/bottom of the screen, then change direction

        if(this.y < 0 )  {
            this.y = 0;
            this.movingVectorY = - this.movingVectorY;
        } else if(this.y > this.pongSurfaceView.getHeight()- height)  {
            this.y= this.pongSurfaceView.getHeight()- height;
            this.movingVectorY = - this.movingVectorY;
        }

        float deltaV;
        if (VELOCITY < 1) {
            deltaV = (float)0.0015;
        } else {
            deltaV = (float)0.0015/VELOCITY;
        }
        VELOCITY += deltaV;
    }

    void draw(Canvas canvas)  {
        Bitmap bitmap = this.pongImg;
        canvas.drawBitmap(bitmap,x, y, null);
        // Last draw time.
        this.lastDrawNanoTime= System.nanoTime();
    }
}
