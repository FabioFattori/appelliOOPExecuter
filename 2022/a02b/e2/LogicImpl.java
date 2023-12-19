package a02b.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicImpl implements Logic {
    private final CellType[][] map;
    private final int size;
    private boolean programMustEnd;
    private List<Pair<Integer, Integer>> filledCells;

    public LogicImpl(final int size) {
        this.size = size;
        this.map = new CellType[this.size][this.size];
        this.filledCells = new ArrayList<>();
        this.resetMap();
    }

    @Override
    public void makeAMove(Pair<Integer, Integer> coordinate) {
        if (this.map[coordinate.getX()][coordinate.getY()] == CellType.Vuota) {
            this.filledCells.add(coordinate);
            this.map[coordinate.getX()][coordinate.getY()] = CellType.Piena;
        }
    }  

    private boolean coordinateIsInDiagonalTo(Pair<Integer, Integer> centerOfDiagonal, Pair<Integer, Integer> coor) {
        var x = centerOfDiagonal.getX() - coor.getX();
        var y = centerOfDiagonal.getY() - coor.getY();
        return x == y;
    }

    private void changeContentOfDiagonal(Pair<Integer, Integer> coor) {
        int i = coor.getX();
        for (int j = coor.getY(); j < this.size && i<this.size; j++) {
            this.map[i][j] = CellType.DiagonalFound;
            i++;
        }

        i = coor.getX();

        for (int j = coor.getY(); j >= 0 && i>=0; j--) {
            this.map[i][j] = CellType.DiagonalFound;
            i--;
        }


    }

    private void checkDiagonals() {
        for (Pair<Integer, Integer> coor : filledCells) {
            if (filledCells.stream().filter((cell) -> coordinateIsInDiagonalTo(cell, coor) && this.map[cell.getX()][cell.getY()] == CellType.Piena).count() >= 3) {
                changeContentOfDiagonal(coor);
                this.programMustEnd = true;
            }
        }

        
    }

    @Override
    public boolean checkEndGame() {
        checkDiagonals();

        return this.programMustEnd;
    }

    @Override
    public void resetMap() {
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[i].length; j++) {
                this.map[i][j] = CellType.Vuota;
            }
        }

        this.programMustEnd = false;
    }

    @Override
    public CellType[][] getMap() {
        return this.map;
    }
}
