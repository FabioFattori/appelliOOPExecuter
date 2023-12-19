package a02c.e2;

public interface Logic {
    enum CellTypes {Vuota,Palla,Ostacolo};

    void move();

    boolean ballGotStuck();

    boolean ballGotToEnd();
    
    void restartLogic();

    CellTypes[][] getMap();
}
