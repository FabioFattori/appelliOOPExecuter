package a01c.e2;

public interface Logic {
    enum CellTypes {Vuota,Piena};

    void fillACell(Pair<Integer,Integer> coor);

    CellTypes[][] getMap();
}
