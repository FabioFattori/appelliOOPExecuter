package a02a.e2;

public interface Logic {
    enum CellType { Vuota , Disabilitata , Occupata}
    
    CellType[][] makeAMove(Pair<Integer,Integer> coor);

    boolean checkIfRestartIsNeeded();

    CellType[][] restart();
} 
