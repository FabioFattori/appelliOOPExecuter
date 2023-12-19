package a02b.e2;

public interface Logic {
    enum CellType {
        Vuota, Piena, DiagonalFound
    };

    void makeAMove(Pair<Integer, Integer> coordinate);

    boolean checkEndGame();

    void resetMap();

    CellType[][] getMap();
}
