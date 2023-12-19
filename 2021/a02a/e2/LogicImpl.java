package a02a.e2;

import java.util.*;

public class LogicImpl implements Logic {
    private final int[][] map;
    private Pair<Integer, Integer> currentCell;
    private boolean isEnded;

    public LogicImpl(final int size) {
        this.map = new int[size][size];
        resetLogic();
    }

    private void resetLogic() {
        this.currentCell = null;
        this.isEnded = false;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                this.map[i][j] = -1;
            }
        }
    }

    private boolean checkCoordinateValidity(Pair<Integer, Integer> coor) {
        return coor.getX() >= 0 && coor.getY() >= 0 && coor.getX() < this.map.length && coor.getY() < this.map.length;
    }

    private boolean tryToMove(Pair<Integer, Integer> possibleNewPos) {
        if (checkCoordinateValidity(possibleNewPos) && this.map[possibleNewPos.getX()][possibleNewPos.getY()] != -1) {
            return false;
        } else if (checkCoordinateValidity(possibleNewPos)) {
            this.map[possibleNewPos.getX()][possibleNewPos
                    .getY()] = this.map[this.currentCell.getX()][this.currentCell.getY()] + 1;
            this.currentCell = possibleNewPos;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void move() {
        if (this.currentCell == null) {
            this.currentCell = new Pair<Integer, Integer>((int) Math.round(Math.random() * (this.map.length - 1)),
                    (int) Math.round(Math.random() * (this.map.length - 1)));
            this.map[this.currentCell.getX()][this.currentCell.getY()] = 0;
        } else {
            Pair<Integer, Integer> possibleNewPos;
            possibleNewPos = new Pair<Integer, Integer>(this.currentCell.getX(), this.currentCell.getY() - 1);
            if (!tryToMove(possibleNewPos)) {
                possibleNewPos = new Pair<Integer, Integer>(this.currentCell.getX(), this.currentCell.getY() + 1);
                if (!tryToMove(possibleNewPos)) {
                    possibleNewPos = new Pair<Integer, Integer>(this.currentCell.getX() - 1, this.currentCell.getY());
                    if (!tryToMove(possibleNewPos)) {
                        possibleNewPos = new Pair<Integer, Integer>(this.currentCell.getX() + 1,
                                this.currentCell.getY());
                        if (!tryToMove(possibleNewPos)) {
                            this.isEnded = true;
                        }
                    }
                }
            }

        }
    }

    

    @Override
    public int[][] getMap() {
        return this.map;
    }

    @Override
    public boolean checkEnd() {
        return this.isEnded;
    }
}
