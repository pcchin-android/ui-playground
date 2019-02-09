package com.pcchin.uiplayground.tetris.tetrisblock;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.pcchin.uiplayground.tetris.TetrisSurfaceView;

import java.util.ArrayList;

public class TetrisJ extends TetrisBlock {
    public TetrisJ(@NonNull TetrisSurfaceView tetrisSurfaceView, int x, int y) {
        super(tetrisSurfaceView, "TetrisJ", Color.GREEN, x, y);
    }

    @Override
    void setStartingCoords() {

    }

    @Override
    ArrayList<Integer> getCtrGrid() {
        return null;
    }

    @Override
    public void rotate() {
        ArrayList<Integer> ctrGrid = this.getCtrGrid();
        ArrayList<ArrayList<Integer>> returnList = new ArrayList<>();
        if (! this.checkCollision(returnList)) {
            this.currentBlockCoords = returnList;
        }
    }
}
