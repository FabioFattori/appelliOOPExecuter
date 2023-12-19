package a02b.e2;

public interface Logic {
    enum CellTypes {Vuota,Piena,Left,Right};

    void move();

    boolean checkEnd();

    CellTypes[][] getMap();
}
