package a03b.e2;

import java.util.*;

public class LogicImpl implements Logic {
    private final int[][] map;
    private Pair<Integer, Integer> currentPos;
    private List<Pair<Integer, Integer>> directions;
    private int currentDirection;
    private boolean isEnded;

    public LogicImpl(final int size) {
        this.map = new int[size][size];

        this.currentPos = new Pair<Integer, Integer>((int) Math.round(Math.random() * (size - 3)),
                (int) Math.round(Math.random() * (size - 3)));

        if (this.currentPos.getX() <= 1) {
            this.currentPos = new Pair<Integer, Integer>(this.currentPos.getX() + 1, this.currentPos.getY());
        }

        if (this.currentPos.getY() <= 1) {
            this.currentPos = new Pair<Integer, Integer>(this.currentPos.getX(), this.currentPos.getY() + 1);
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                this.map[i][j] = -1;
            }
        }

        this.map[this.currentPos.getX()][this.currentPos.getY()] = 0;

        this.directions = List.of(new Pair<>(-1, 0), new Pair<>(0, 1), new Pair<>(1, 0), new Pair<>(0, -1));
        this.currentDirection = 0;
        this.isEnded = false;
    }

    private boolean checkCoordinateValidity(Pair<Integer, Integer> coor) {
        return coor.getX() >= 0 && coor.getY() >= 0 && coor.getX() < this.map.length && coor.getY() < this.map.length;
    }

    @Override
    public void move() {
        Pair<Integer, Integer> nextPos = new Pair<Integer, Integer>(
                this.currentPos.getX() + this.directions.get(this.currentDirection).getX(),
                this.currentPos.getY() + this.directions.get(this.currentDirection).getY());

        if (checkCoordinateValidity(nextPos) && this.map[nextPos.getX()][nextPos.getY()] == -1) {
            this.map[nextPos.getX()][nextPos.getY()] = this.map[this.currentPos.getX()][this.currentPos.getY()] + 1;
            this.currentPos = nextPos;
        } else {
            this.currentDirection--;
            if (this.currentDirection == -1) {
                this.currentDirection = this.directions.size() - 1;
            }

            nextPos = new Pair<Integer, Integer>(
                    this.currentPos.getX() + this.directions.get(this.currentDirection).getX(),
                    this.currentPos.getY() + this.directions.get(this.currentDirection).getY());

            if (checkCoordinateValidity(nextPos) && this.map[nextPos.getX()][nextPos.getY()] == -1) {
                this.map[nextPos.getX()][nextPos.getY()] = this.map[this.currentPos.getX()][this.currentPos.getY()] + 1;
                this.currentPos = nextPos;
            }else{
                this.isEnded = true;
            }
        }

        this.currentDirection++;
        if (this.currentDirection == this.directions.size()) {
            this.currentDirection = 0;
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
