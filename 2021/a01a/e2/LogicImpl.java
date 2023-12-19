package a01a.e2;

public class LogicImpl implements Logic {
    private final CellTypes[][] map;
    private Pair<Integer, Integer> firstFilled;

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

        this.firstFilled = null;
    }

    private void fillRect(Pair<Integer, Integer> coor){
        int maxX = (coor.getX() > this.firstFilled.getX())?coor.getX():this.firstFilled.getX();
        int maxY = (coor.getY() > this.firstFilled.getY())?coor.getY():this.firstFilled.getY();
        int minX = (coor.getX() < this.firstFilled.getX())?coor.getX():this.firstFilled.getX();
        int minY = (coor.getY() < this.firstFilled.getY())?coor.getY():this.firstFilled.getY();

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                this.map[i][j] = CellTypes.Piena;
            }
        }

        this.firstFilled = null;
    }

    @Override
    public void fillACell(Pair<Integer, Integer> coor) {
        if (this.map[coor.getX()][coor.getY()] == CellTypes.Vuota) {
            if (this.firstFilled == null) {
                this.firstFilled = coor;
                this.map[coor.getX()][coor.getY()] = CellTypes.Piena;
            } else {
                fillRect(coor);
            }
        }
    }

    @Override
    public boolean checkEnd() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(this.map[i][j] == CellTypes.Vuota){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public CellTypes[][] getMap() {
        return this.map;
    }
}
