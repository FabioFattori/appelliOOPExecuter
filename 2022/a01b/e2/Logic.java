package a01b.e2;

public interface Logic {
    enum CellType {
        Vuota, Piena
    };

    CellType[][] makeAMove(Pair<Integer, Integer> coordinate);

    boolean checkEnd();
}
