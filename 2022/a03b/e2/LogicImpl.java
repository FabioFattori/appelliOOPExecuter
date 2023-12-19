package a03b.e2;

import java.util.List;

public class LogicImpl implements Logic {

    private final CellType[][] map;
    private final int size;
    private int pedoniNemiciRestanti;

    public LogicImpl(final int size) {
        this.size = size;
        this.map = new CellType[size][size];

        resetLogic();
    }

    @Override
    public CellType[][] getMap() {
        return this.map;
    }

    private boolean checkCoordinateValidity(Pair<Integer, Integer> coor) {
        return coor.getX() >= 0 && coor.getY() >= 0 && coor.getX() < this.size && coor.getY() < this.size;
    }

    private void pedinaAMangiaPedinaB(Pair<Integer, Integer> A, Pair<Integer, Integer> B) {
        this.map[B.getX()][B.getY()] = CellType.PedinaAlleata;
        this.map[A.getX()][A.getY()] = CellType.Vuota;
    }

    private boolean checkIfPedoneCanEat(Pair<Integer, Integer> coor) {
        for (Pair<Integer, Integer> cell : List.of(new Pair<Integer, Integer>(coor.getX() - 1, coor.getY() + 1),
                new Pair<Integer, Integer>(coor.getX() - 1, coor.getY() - 1))) {
            if (checkCoordinateValidity(cell) && this.map[cell.getX()][cell.getY()] == CellType.PedinaAvversaria) {
                pedinaAMangiaPedinaB(coor, cell);
                this.pedoniNemiciRestanti--;
                checkEndGame();
                return true;
            }
        }

        return false;
    }

    @Override
    public void makeAMove(Pair<Integer, Integer> coor) {
        if (this.map[coor.getX()][coor.getY()] != CellType.Vuota) {
            if (!checkIfPedoneCanEat(coor)) {
                // pedone can't eat so it moves if possible
                Pair<Integer, Integer> upperCell = new Pair<Integer, Integer>(coor.getX() - 1, coor.getY());
                if (checkCoordinateValidity(upperCell)
                        && this.map[upperCell.getX()][upperCell.getY()] == CellType.Vuota) {
                    pedinaAMangiaPedinaB(coor, upperCell);
                }
            }
        }
    }

    private void resetLogic() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.map[i][j] = CellType.Vuota;
            }
        }

        this.pedoniNemiciRestanti = NPedoniNemici;
    }

    private void checkEndGame() {
        if (this.pedoniNemiciRestanti <= 0) {
            resetLogic();
            populateMap();
        }
    }

    @Override
    public void populateMap() {
        Pair<Integer, Integer> generated;
        for (int i = 0; i < NPedoniNemici; i++) {
            do {
                generated = new Pair<Integer, Integer>((int) Math.round(Math.random() * 1), i);
            } while (this.map[generated.getX()][generated.getY()] != CellType.Vuota);
            this.map[generated.getX()][generated.getY()] = CellType.PedinaAvversaria;
        }

        for (int i = 0; i < NPedoniAlleati; i++) {
            do {
                generated = new Pair<Integer, Integer>(
                        (int) Math.round(Math.random() + this.size - 2), i);
            } while (this.map[generated.getX()][generated.getY()] != CellType.Vuota);
            this.map[generated.getX()][generated.getY()] = CellType.PedinaAlleata;
        }
    }

}
