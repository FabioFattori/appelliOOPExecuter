package a01a.e2;

public interface Logic {
    enum CellType { Visited , NotVisited };
    CellType[][] makeAMove(Pair<Integer,Integer> coordinates);
    boolean checkEnd();
}
