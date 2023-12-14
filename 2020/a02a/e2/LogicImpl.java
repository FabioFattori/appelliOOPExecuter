package a02a.e2;


public class LogicImpl implements Logic {

    private final int CELLAVUOTA = 0;
    private final int PLAYER = 1;
    private final int PEDONE = 2;

    private final int[][] map;
    private final int size;
    private Pair<Integer, Integer> playerPosition;

    public LogicImpl(final int size) {
        this.size = size;
        this.map = new int[this.size][this.size];
    }

    private void playerMangiaPosPassate(Pair<Integer, Integer> pos) {
        this.map[pos.getX()][pos.getY()] = this.PLAYER;
        this.map[this.playerPosition.getX()][this.playerPosition.getY()] = this.CELLAVUOTA;
    }

    private boolean checkCoorValidity(Pair<Integer, Integer> pos) {
        return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < this.size && pos.getY() < this.size;
    }

    @Override
    public int[][] makeAMove() {
        Pair<Integer, Integer> altoSX = new Pair<Integer, Integer>(this.playerPosition.getX() - 1,
                this.playerPosition.getY() - 1);
        Pair<Integer, Integer> altoDX = new Pair<Integer, Integer>(this.playerPosition.getX() - 1,
                this.playerPosition.getY() + 1);
        Pair<Integer, Integer> alto = new Pair<Integer, Integer>(this.playerPosition.getX() - 1,
                this.playerPosition.getY());

        if (checkCoorValidity(altoSX) && this.map[altoSX.getX()][altoSX.getY()] == this.PEDONE) {
            playerMangiaPosPassate(altoSX);
            this.playerPosition = altoSX;
        } else if (checkCoorValidity(altoDX) && this.map[altoDX.getX()][altoDX.getY()] == this.PEDONE) {
            playerMangiaPosPassate(altoDX);
            this.playerPosition = altoDX;
        } else if (checkCoorValidity(alto)) {
            /* va su */
            playerMangiaPosPassate(alto);
            this.playerPosition = alto;
        }

        return this.map;
    }

    @Override
    public boolean checkEndGame() {
        Pair<Integer, Integer> altoSX = new Pair<Integer, Integer>(this.playerPosition.getX() - 1,
                this.playerPosition.getY() - 1);
        Pair<Integer, Integer> altoDX = new Pair<Integer, Integer>(this.playerPosition.getX() - 1,
                this.playerPosition.getY() + 1);
        Pair<Integer, Integer> alto = new Pair<Integer, Integer>(this.playerPosition.getX() - 1,
                this.playerPosition.getY());

        return !((checkCoorValidity(altoSX) && this.map[altoSX.getX()][altoSX.getY()] == this.PEDONE)
                || (checkCoorValidity(alto) && this.map[alto.getX()][alto.getY()] != this.PEDONE)
                || checkCoorValidity(altoDX) && this.map[altoDX.getX()][altoDX.getY()] == this.PEDONE);
    }

    @Override
    public int[][] populateMap(int nPezziPassiviDaCreare) {
        Pair<Integer, Integer> generatedCoor;
        for (int i = 0; i < nPezziPassiviDaCreare; i++) {

            do {
                generatedCoor = new Pair<Integer, Integer>((int) Math.floor(Math.random() * (size - 2)),
                        (int) Math.floor(Math.random() * (size - 1)));
            } while (this.map[generatedCoor.getX()][generatedCoor.getY()] != this.CELLAVUOTA);
            this.map[generatedCoor.getX()][generatedCoor.getY()] = this.PEDONE;
        }

        generatedCoor = new Pair<Integer, Integer>(size - 1, (int) Math.floor(Math.random() * (size - 1)));

        this.playerPosition = generatedCoor;
        this.map[generatedCoor.getX()][generatedCoor.getY()] = this.PLAYER;

        return this.map;
    }

    @Override
    public int getPlayerSymbol() {
        return this.PLAYER;
    }

    @Override
    public int getCellaVuotaSymbol() {
        return this.CELLAVUOTA;
    }

    @Override
    public int getPedoneSymbol() {
        return this.PEDONE;
    }

}
