package a02c.e2;

public class LogicImpl implements Logic {
    private final CellTypes[][] map;
    private Pair<Integer, Integer> currentPosition = null;
    private boolean ballGotStuck;

    public LogicImpl(final int size, final int nObstacles) {
        this.map = new CellTypes[size][size];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                this.map[i][j] = CellTypes.Vuota;
            }
        }
        restartLogic();
        populateMapWithObstacles(nObstacles);
    }

    public void restartLogic() {
        if (this.currentPosition != null) {
            this.map[this.currentPosition.getX()][this.currentPosition.getY()] = CellTypes.Vuota;
        }

        this.ballGotStuck = false;
        do {
            this.currentPosition = new Pair<Integer, Integer>(0,
                    (int) Math.round(Math.random() * (this.map.length - 1)));
        } while (this.map[this.currentPosition.getX()][this.currentPosition.getY()] != CellTypes.Vuota);
        this.map[this.currentPosition.getX()][this.currentPosition.getY()] = CellTypes.Palla;
    }

    public void populateMapWithObstacles(final int nObstacles) {
        for (int i = 0; i < nObstacles; i++) {
            Pair<Integer, Integer> generated;
            do {
                generated = new Pair<Integer, Integer>((int) Math.round(Math.random() * (this.map.length - 1)),
                        (int) Math.round(Math.random() * (this.map.length - 1)));
            } while (this.map[generated.getX()][generated.getY()] != CellTypes.Vuota);
            this.map[generated.getX()][generated.getY()] = CellTypes.Ostacolo;
        }
    }

    private boolean checkCoordinateValidity(Pair<Integer, Integer> coor) {
        return coor.getX() >= 0 && coor.getY() >= 0 && coor.getX() < this.map.length && coor.getY() < this.map.length;
    }

    private void tryToMoveToDirection(boolean isEnd, int direction) {
        Pair<Integer, Integer> nextPos = new Pair<Integer, Integer>(this.currentPosition.getX(),
                this.currentPosition.getY() + direction);
        if (checkCoordinateValidity(nextPos) && this.map[nextPos.getX()][nextPos.getY()] != CellTypes.Ostacolo) {
            this.map[nextPos.getX()][nextPos.getY()] = CellTypes.Palla;
            this.map[this.currentPosition.getX()][this.currentPosition.getY()] = CellTypes.Vuota;
            this.currentPosition = nextPos;
        } else if (!isEnd) {
            tryToMoveToDirection(true, (direction == 1) ? -1 : 1);
        } else {
            this.ballGotStuck = true;
        }
    }

    @Override
    public void move() {
        Pair<Integer, Integer> nextPos = new Pair<Integer, Integer>(this.currentPosition.getX() + 1,
                this.currentPosition.getY());

        if (this.map[nextPos.getX()][nextPos.getY()] == CellTypes.Ostacolo) {
            int randomChoise = (int) Math.round(Math.random());
            switch (randomChoise) {
                case 0:
                    tryToMoveToDirection(false, -1);
                    break;
                default:
                    tryToMoveToDirection(false, 1);
                    break;
            }
        } else {
            this.map[nextPos.getX()][nextPos.getY()] = CellTypes.Palla;
            this.map[this.currentPosition.getX()][this.currentPosition.getY()] = CellTypes.Vuota;
            this.currentPosition = nextPos;
        }

    }

    @Override
    public boolean ballGotStuck() {
        return this.ballGotStuck;
    }

    @Override
    public CellTypes[][] getMap() {
        return this.map;
    }

    @Override
    public boolean ballGotToEnd() {
        return this.currentPosition.getX() == this.map.length - 1;
    }
}
