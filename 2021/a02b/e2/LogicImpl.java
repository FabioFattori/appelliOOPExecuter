package a02b.e2;

public class LogicImpl implements Logic {
    private final CellTypes[][] map;
    private Pair<Integer, Integer> currentDirection;
    private Pair<Integer, Integer> playerPosition;
    private boolean isEnded;

    public LogicImpl(final int size, final int nLeftCell, final int nRightCell) {
        this.map = new CellTypes[size][size];
        resetLogic();
        populateMap(nLeftCell, nRightCell);
    }

    private void resetLogic() {
        this.currentDirection = new Pair<Integer, Integer>(-1, 0);
        this.isEnded = false;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                this.map[i][j] = CellTypes.Vuota;
            }
        }
    }

    private Pair<Integer, Integer> generateValidCoordinate(int rangeX, int rangeY) {
        Pair<Integer, Integer> generated;
        do {
            generated = new Pair<Integer, Integer>(rangeX, rangeY);
        } while (this.map[generated.getX()][generated.getY()] != CellTypes.Vuota);

        return generated;
    }

    private void populateMap(final int nLeftCell, final int nRightCell) {
        Pair<Integer, Integer> generated;
        for (int i = 0; i < nLeftCell; i++) {
            generated = generateValidCoordinate((int) Math.round(Math.random() * (this.map.length - 1)),
                    (int) Math.round(Math.random() * (this.map.length - 1)));
            this.map[generated.getX()][generated.getY()] = CellTypes.Left;
        }
        for (int i = 0; i < nRightCell; i++) {
            generated = generateValidCoordinate((int) Math.round(Math.random() * (this.map.length - 1)),
                    (int) Math.round(Math.random() * (this.map.length - 1)));
            this.map[generated.getX()][generated.getY()] = CellTypes.Right;
        }

        generated = generateValidCoordinate(this.map.length - 1,
                (int) Math.round(Math.random() * (this.map.length - 1)));
        this.map[generated.getX()][generated.getY()] = CellTypes.Piena;
        this.playerPosition = generated;
    }

    private boolean checkCoordinateValidy(Pair<Integer, Integer> coor) {
        return coor.getX() >= 0 && coor.getY() >= 0 && coor.getX() < this.map.length && coor.getY() < this.map.length;
    }

    @Override
    public void move() {
        Pair<Integer, Integer> nextPosition = new Pair<Integer, Integer>(
                this.playerPosition.getX() + this.currentDirection.getX(),
                this.playerPosition.getY() + this.currentDirection.getY());
        if (checkCoordinateValidy(nextPosition)) {
            if (this.map[nextPosition.getX()][nextPosition.getY()] == CellTypes.Left) {
                this.currentDirection = new Pair<Integer, Integer>(0, -1);
            } else if (this.map[nextPosition.getX()][nextPosition.getY()] == CellTypes.Right) {
                this.currentDirection = new Pair<Integer, Integer>(0, 1);
            }
            this.map[nextPosition.getX()][nextPosition.getY()] = CellTypes.Piena;
            this.map[this.playerPosition.getX()][this.playerPosition.getY()] = CellTypes.Vuota;
            this.playerPosition = nextPosition;
        } else {
            this.isEnded = true;
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
