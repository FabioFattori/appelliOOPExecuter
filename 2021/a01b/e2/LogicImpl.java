package a01b.e2;

public class LogicImpl implements Logic {
    private final CellTypes[][] map;
    private Pair<Integer, Integer> unoCoor;
    private Pair<Integer, Integer> dueCoor;
    private boolean isEnded;

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

        this.unoCoor = null;
        this.dueCoor = null;
        this.isEnded = false;
    }

    private boolean coorIsInAAxis(Pair<Integer, Integer> coor) {
        return (this.unoCoor.getX() == coor.getX() || this.unoCoor.getY() == coor.getY())
                && !(this.unoCoor.getX() == coor.getX() && this.unoCoor.getY() == coor.getY());
    }

    private void fillEmptySpaces(Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
        int minCor = (start.getX() == end.getX()) ? (start.getY() < end.getY()) ? start.getY() : end.getY()
                : (start.getX() < end.getX()) ? start.getX() : end.getX();
        int maxCor = (start.getX() == end.getX()) ? (start.getY() > end.getY()) ? start.getY() : end.getY()
                : (start.getX() > end.getX()) ? start.getX() : end.getX();

        for (int i = minCor; i < maxCor; i++) {
            if (start.getX() == end.getX() && this.map[start.getX()][i] == CellTypes.Vuota) {
                this.map[start.getX()][i] = CellTypes.Piena;
            } else if (this.map[i][start.getY()] == CellTypes.Vuota) {
                this.map[i][start.getY()] = CellTypes.Piena;
            }
        }
    }

    @Override
    public void placeANumber(Pair<Integer, Integer> coor) {
        if (this.unoCoor == null) {
            this.unoCoor = coor;
            this.map[this.unoCoor.getX()][this.unoCoor.getY()] = CellTypes.Uno;
        } else {
            if (coorIsInAAxis(coor)) {
                if (this.dueCoor == null) {
                    this.dueCoor = coor;
                    this.map[coor.getX()][coor.getY()] = CellTypes.Due;
                } else {
                    this.map[coor.getX()][coor.getY()] = CellTypes.Tre;
                    fillEmptySpaces(this.unoCoor, this.dueCoor);
                    fillEmptySpaces(this.unoCoor, coor);
                    this.isEnded = true;
                }
            }
        }
    }

    @Override
    public boolean checkEnd() {
        return this.isEnded;
    }

    @Override
    public CellTypes[][] getMap() {
        return this.map;
    }
}
