package a01a.e2;

public interface Logic {
    
    enum CellTypes {Vuota,Piena};

    void fillACell(Pair<Integer,Integer> coor);

    boolean checkEnd();

    CellTypes[][] getMap();
}
