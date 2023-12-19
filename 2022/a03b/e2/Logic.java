package a03b.e2;

public interface Logic {
    final int NPedoniNemici = 4;
    final int NPedoniAlleati = 4;

    enum CellType { Vuota , PedinaAlleata , PedinaAvversaria };

    CellType[][] getMap();

    void makeAMove(Pair<Integer,Integer> coor);

    void populateMap();

}
