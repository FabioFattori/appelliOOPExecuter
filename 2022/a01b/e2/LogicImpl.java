package a01b.e2;

public class LogicImpl implements Logic {

    private final CellType[][] map;
    private final int size;
    private int celleSvuotate;
    private int celleRiempite;

    public LogicImpl(final int size) {
        this.size = size;
        this.map = new CellType[this.size][this.size];

        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[i].length; j++) {
                this.map[i][j] = CellType.Vuota;
            }
        }

    }

    private boolean checkCoordinateValidity(Pair<Integer, Integer> coor) {
        return coor.getX() >= 0 && coor.getY() >= 0 && coor.getX() < this.size && coor.getY() < this.size;
    }

    private void changeMapValue(Pair<Integer, Integer> coordinate) {
        if (this.map[coordinate.getX()][coordinate.getY()] == CellType.Vuota) {
            this.map[coordinate.getX()][coordinate.getY()] = CellType.Piena;
            this.celleRiempite++;
        } else {
            this.map[coordinate.getX()][coordinate.getY()] = CellType.Vuota;
            this.celleSvuotate++;
        }
    }

    @Override
    public CellType[][] makeAMove(Pair<Integer, Integer> coordinate) {
        this.celleRiempite = 0;
        this.celleSvuotate = 0;
        Pair<Integer, Integer> altoSX = new Pair<Integer, Integer>(coordinate.getX() - 1, coordinate.getY() - 1);
        Pair<Integer, Integer> altoDX = new Pair<Integer, Integer>(coordinate.getX() - 1, coordinate.getY() + 1);
        Pair<Integer, Integer> bassoSX = new Pair<Integer, Integer>(coordinate.getX() + 1, coordinate.getY() - 1);
        Pair<Integer, Integer> bassoDX = new Pair<Integer, Integer>(coordinate.getX() + 1, coordinate.getY() + 1);
        if (checkCoordinateValidity(altoSX)) {
            changeMapValue(altoSX);
        }

        if (checkCoordinateValidity(altoDX)) {
            changeMapValue(altoDX);
        }

        if (checkCoordinateValidity(bassoSX)) {
            changeMapValue(bassoSX);
        }

        if (checkCoordinateValidity(bassoDX)) {
            changeMapValue(bassoDX);
        }

        return this.map;
    }

    @Override
    public boolean checkEnd() {
        return (this.celleSvuotate == 3 && this.celleRiempite == 1);
    }

}
