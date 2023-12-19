package a01c.e2;

import java.util.Optional;

public class LogicImpl implements Logic {
    private final CellTypes[][] map;
    private Optional<Boolean> lastMovesWasVertical; 
    private Pair<Integer, Integer> firstMove;

    public LogicImpl(final int size) {
        this.map = new CellTypes[size][size];
        resetLogic();
    }

    private void resetLogic() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                this.map[i][j] = CellTypes.Vuota;
            }
        }

        this.lastMovesWasVertical = Optional.empty();
        this.firstMove = null;
    }

    private boolean checkIfCoorIsInAxis(Pair<Integer, Integer> coor) {
        return (coor.getX() == this.firstMove.getX() || coor.getY() == this.firstMove.getY())
                && !(coor.getX() == this.firstMove.getX() && coor.getY() == this.firstMove.getY());
    }

    private void fillToPointAToPointB(Pair<Integer, Integer> A, Pair<Integer, Integer> B) {
        int minCoor;
        int maxCoor;
        if (this.lastMovesWasVertical.get() == false) {
            minCoor = (A.getY() < B.getY()) ? A.getY() : B.getY();
            maxCoor = (A.getY() > B.getY()) ? A.getY() : B.getY();

            for (int i = minCoor; i < maxCoor; i++) {
                this.map[A.getX()][i] = CellTypes.Piena;
            }

        } else {
            minCoor = (A.getX() < B.getX()) ? A.getX() : B.getX();
            maxCoor = (A.getX() > B.getX()) ? A.getX() : B.getX();
            for (int i = minCoor; i < maxCoor; i++) {
                this.map[i][A.getY()] = CellTypes.Piena;
            }
        }

    }

    private void fillSecondCell(Pair<Integer, Integer> coor) {
        this.map[coor.getX()][coor.getY()] = CellTypes.Piena;
        fillToPointAToPointB(this.firstMove, coor);
        this.firstMove = coor;
    }

    @Override
    public void fillACell(Pair<Integer, Integer> coor) {
        if (firstMove == null) {
            this.firstMove = coor;
            this.map[coor.getX()][coor.getY()] = CellTypes.Piena;
        } else if (checkIfCoorIsInAxis(coor)) {
            if (coor.getX() == this.firstMove.getX() && ((this.lastMovesWasVertical.isEmpty())
                    || (this.lastMovesWasVertical.isPresent() && this.lastMovesWasVertical.get() == true))) {
                this.lastMovesWasVertical = Optional.of(false);
                fillSecondCell(coor);
            } else if (coor.getY() == this.firstMove.getY()
                    && ((this.lastMovesWasVertical.isEmpty())
                            || (this.lastMovesWasVertical.isPresent() && this.lastMovesWasVertical.get() == false))) {
                this.lastMovesWasVertical = Optional.of(true);
                fillSecondCell(coor);
            }
        }
    }

    @Override
    public CellTypes[][] getMap() {
        return this.map;
    }
}
