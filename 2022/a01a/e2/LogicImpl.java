package a01a.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicImpl implements Logic {

    private final CellType[][] field;
    private final int size;
    private List<Pair<Pair<Integer, Integer>, CellType>> lastThreeMoves;

    public LogicImpl(final int size) {
        this.size = size;
        field = new CellType[this.size][this.size];
        this.lastThreeMoves = new ArrayList<>();
    }

    @Override
    public CellType[][] makeAMove(Pair<Integer, Integer> coordinates) {
        if (this.field[coordinates.getX()][coordinates.getY()] == CellType.Visited) {
            this.field[coordinates.getX()][coordinates.getY()] = CellType.NotVisited;
        } else {
            this.field[coordinates.getX()][coordinates.getY()] = CellType.Visited;
        }
        /* remove the oldes moves and add a new one */
        if (!this.lastThreeMoves.isEmpty() && this.lastThreeMoves.size() == 3) {
            this.lastThreeMoves.remove(0);
        }
        this.lastThreeMoves.add(new Pair<Pair<Integer, Integer>, Logic.CellType>(coordinates,
                this.field[coordinates.getX()][coordinates.getY()]));

        return this.field;
    }

    private boolean checkIfMovesAreInADiagonal() {
        return (Math.abs(this.lastThreeMoves.get(0).getX().getX() - this.lastThreeMoves.get(0).getX().getY()) 
        == Math.abs(this.lastThreeMoves.get(1).getX().getX() - this.lastThreeMoves.get(1).getX().getY())
                && Math.abs(this.lastThreeMoves.get(0).getX().getX() - this.lastThreeMoves.get(0).getX().getY()) 
                == Math.abs(this.lastThreeMoves.get(2).getX().getX() - this.lastThreeMoves.get(2).getX().getY()));
    }

    @Override
    public boolean checkEnd() {
        return this.lastThreeMoves.size() == 3 && this.lastThreeMoves.get(0).getY() == CellType.Visited
                && this.lastThreeMoves.get(2).getY() == CellType.Visited
                && this.lastThreeMoves.get(1).getY() == CellType.Visited && checkIfMovesAreInADiagonal();

    }
}
