package a03a.e2;

import java.util.*;

public class LogicImpl implements Logic {
    private final boolean[][] map;
    private Pair<Integer, Integer> firstFill;
    private final List<Pair<Integer, Integer>> filledCells;
    private boolean isEnded;

    public LogicImpl(final int size) {
        this.map = new boolean[size][size];
        this.filledCells = new ArrayList<>();
        this.firstFill = null;
        this.isEnded = false;
    }

    private void fillCellsFromPointAToPointB(Pair<Integer, Integer> A, Pair<Integer, Integer> B) {
        if (A.getY() == B.getY()) {
            int minX = (A.getX() < B.getX()) ? A.getX() : B.getX();
            int maxX = (A.getX() > B.getX()) ? A.getX() : B.getX();

            for (int i = minX; i < maxX; i++) {
                this.map[i][A.getY()] = true;
            }
        } else if (A.getX() == B.getX()) {
            int minY = (A.getY() < B.getY()) ? A.getY() : B.getY();
            int maxY = (A.getY() > B.getY()) ? A.getY() : B.getY();

            for (int i = minY; i < maxY; i++) {
                this.map[A.getX()][i] = true;
            }
        }
    }

    private boolean checkCoordinateValidity(Pair<Integer, Integer> coor) {
        int minX = (this.filledCells.get(0).getX() < this.filledCells.get(1).getX()) ? this.filledCells.get(0).getX()
                : this.filledCells.get(1).getX();
        int maxX = (this.filledCells.get(0).getX() > this.filledCells.get(1).getX()) ? this.filledCells.get(0).getX()
                : this.filledCells.get(1).getX();
        int minY = (this.filledCells.get(0).getY() < this.filledCells.get(1).getY()) ? this.filledCells.get(0).getY()
                : this.filledCells.get(1).getY();
        int maxY = (this.filledCells.get(0).getY() > this.filledCells.get(1).getY()) ? this.filledCells.get(0).getY()
                : this.filledCells.get(1).getY();
        return coor.getX() > minX && coor.getY() > minY && coor.getX() < maxX && coor.getY() < maxY;
    }

    @Override
    public void fillACell(Pair<Integer, Integer> coor) {
        if (this.filledCells.isEmpty() || checkCoordinateValidity(coor)) {
            if (this.firstFill == null) {
                this.firstFill = coor;
            } else {
                this.filledCells.clear();
                Pair<Integer, Integer> calcAngle = new Pair<Integer, Integer>(this.firstFill.getX(), coor.getY());
                fillCellsFromPointAToPointB(calcAngle, this.firstFill);
                fillCellsFromPointAToPointB(calcAngle, coor);
                calcAngle = new Pair<Integer, Integer>(coor.getX(), this.firstFill.getY());
                fillCellsFromPointAToPointB(calcAngle, this.firstFill);
                fillCellsFromPointAToPointB(calcAngle, coor);

                this.filledCells.addAll(List.of(this.firstFill, coor));

                this.isEnded = (this.firstFill.getX() == coor.getX() || this.firstFill.getY() == coor.getY()
                        || Math.abs(this.firstFill.getX() - coor.getX()) - 1 == 0
                        || Math.abs(this.firstFill.getY() - coor.getY()) - 1 == 0
                        || (Math.abs(this.firstFill.getX() - coor.getX()) - 2 == 0
                        && Math.abs(this.firstFill.getY() - coor.getY()) - 2 == 0));

                this.firstFill = null;
            }
            this.map[coor.getX()][coor.getY()] = true;
        }
    }

    @Override
    public boolean checkEnd() {
        return this.isEnded;
    }

    @Override
    public boolean[][] getMap() {
        return this.map;
    }
}
