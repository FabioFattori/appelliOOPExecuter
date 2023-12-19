package a02a.e2;

public class Logicimpl implements Logic {
    private final CellType[][] map;
    private final int size;

    public Logicimpl(final int size) {
        this.size = size;
        this.map = new CellType[this.size][this.size];

        resetMap();
    }

    private void resetMap() {
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[i].length; j++) {
                this.map[i][j] = CellType.Vuota;
            }
        }
    }

    @Override
    public CellType[][] makeAMove(Pair<Integer, Integer> coor) {
        if (this.map[coor.getX()][coor.getY()] == CellType.Vuota) {
            this.map[coor.getX()][coor.getY()] = CellType.Occupata;
            for (int i = 0; i < this.map.length; i++) {
                for (int j = 0; j < this.map[i].length; j++) {
                    Pair<Integer, Integer> toCheck = new Pair<Integer, Integer>(i, j);

                    if (Math.abs(toCheck.getX() - coor.getX()) == Math.abs(toCheck.getY() - coor.getY())
                            && this.map[toCheck.getX()][toCheck.getY()] == CellType.Vuota) {
                        this.map[toCheck.getX()][toCheck.getY()] = CellType.Disabilitata;
                    }
                }
            }
        }

        return this.map;
    }

    @Override
    public boolean checkIfRestartIsNeeded() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(this.map[i][j] == CellType.Vuota){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public CellType[][] restart() {
        resetMap();
        return this.map;
    }
}
