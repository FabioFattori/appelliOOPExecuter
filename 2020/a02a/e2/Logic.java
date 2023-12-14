package a02a.e2;


public interface Logic {
    int[][] makeAMove();
    boolean checkEndGame();
    int[][] populateMap(int nPezziPassiviDaCreare);

    int getPlayerSymbol();
    int getCellaVuotaSymbol();
    int getPedoneSymbol();
}
