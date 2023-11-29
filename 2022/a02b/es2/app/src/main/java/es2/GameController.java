package es2;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameController implements Controller {

    private final int nCol;
    private final int nRows;
    private boolean gameIsEnded;
    private final HashMap<JButton, Pair<Integer, Integer>> map;
    private JFrame currentGame;

    public GameController(int size, HashMap<JButton, Pair<Integer, Integer>> map,JFrame currentFrame) {
        this(size, size, map,currentFrame);
    }

    public GameController(int nRows, int nCol, HashMap<JButton, Pair<Integer, Integer>> map,JFrame currentFrame) {
        this.nRows = nRows;
        this.nCol = nCol;
        this.map = map;
        this.currentGame = currentFrame;
        gameIsEnded = false;
    }

    private JButton findButtonGivenCoordinates(Pair<Integer, Integer> coordinates) {
        for (Map.Entry<JButton, Pair<Integer, Integer>> entry : this.map.entrySet()) {
            if (entry.getValue().getX() == coordinates.getX()&&entry.getValue().getY() == coordinates.getY()) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("bad coordinates given => " + coordinates.toString());
    }

    @Override
    public boolean gameCanEnd() {
        return this.gameIsEnded;
    }

    @Override
    public void togleNormalBtn(JButton btn) {
        btn.setText((btn.getText() == "") ? "*" : "");
    }

    @Override
    public void togleCheckStartBtn() {
        if (gameCanEnd()) {
            restartGame();
        } else {
            for (int i = 0; i < this.nRows; i++) {
                if (checkDiagonalOf(new Pair<Integer, Integer>(i, 0)) == 3) {
                    disableColumn(new Pair<Integer, Integer>(i, 0));
                    this.gameIsEnded = true;
                    gameCanEnd();
                }
            }
            // foreach row and col i had to check the diagonal
            for (int i = 0; i < this.nCol; i++) {
                if (checkDiagonalOf(new Pair<Integer, Integer>(0, i)) == 3) {
                    disableColumn(new Pair<Integer, Integer>(0, i));
                    this.gameIsEnded = true;
                    gameCanEnd();
                }
            }
        }
    }

    private void restartGame(){
        this.currentGame.dispose();
        this.currentGame=new GUI(nRows);
    }

    private int checkDiagonalOf(Pair<Integer, Integer> startOfColumn) {
        int nTogled = 0;
        for (int i = 0; i + startOfColumn.getX() < this.nRows && i + startOfColumn.getY() < this.nCol; i++) {
            Pair<Integer, Integer> toCheck = new Pair<Integer, Integer>(i + startOfColumn.getX(),
                    i + startOfColumn.getY());
            if (findButtonGivenCoordinates(toCheck).getText() == "*") {
                nTogled++;
            }
        }
        return nTogled;
    }

    private void disableColumn(Pair<Integer, Integer> startOfColumn) {
        for (int i = 0; i + startOfColumn.getX() < this.nRows && i + startOfColumn.getY() < this.nCol; i++) {
            Pair<Integer, Integer> toCheck = new Pair<Integer, Integer>(i + startOfColumn.getX(),
                    i + startOfColumn.getY());
            findButtonGivenCoordinates(toCheck).setEnabled(false);
        }
    }

}
