package a01b.e2;

public interface Logic {
    enum CellTypes {Vuota , Piena , Uno , Due , Tre };

    void placeANumber(Pair<Integer,Integer> coor);

    boolean checkEnd();

    CellTypes[][] getMap();
}
