package com.pcchin.uiplayground.tetris;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

class TetrisThread extends Thread {
    private boolean running;
    private final SurfaceHolder surfaceHolder;
    private TetrisSurfaceView surfaceView;

    TetrisThread(TetrisSurfaceView surfaceView, SurfaceHolder surfaceHolder) {
        this.surfaceView = surfaceView;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run()  {
        long startTime;
        long timeMillis;
        long waitTime;
        int frameCount = 0;
        int targetFPS = 30;
        long targetTime = 1000 / targetFPS;

        while(running)  {
            startTime = System.nanoTime();
            Canvas canvas = null;
            try {
                // Get Canvas from Holder and lock it.
                canvas = this.surfaceHolder.lockCanvas();

                // Synchronized
                synchronized (surfaceHolder)  {
                    this.surfaceView.update();
                    this.surfaceView.draw(canvas);
                }
            }catch(Exception e)  {
                // Do nothing.
            } finally {
                if(canvas != null)  {
                    // Unlock Canvas.
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                sleep(waitTime);
            } catch (Exception ignored) {}
            frameCount++;
            if (frameCount == targetFPS)        {
                frameCount = 0;
            }
        }
    }

    void setRunning(boolean running)  {
        this.running= running;
    }
}