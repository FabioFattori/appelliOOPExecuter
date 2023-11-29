package es2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameController implements Controller {
    final HashMap<JButton, Pair<Integer, Integer>> playground;
    final int nColAndnRows;
    private JFrame currentFrame;

    public GameController() {
        this(new HashMap<>(), 5, new GUI(5));
    }

    public GameController(HashMap<JButton, Pair<Integer, Integer>> map, int size, JFrame currentFrame) {
        this.playground = map;
        this.nColAndnRows = size;
        this.currentFrame = currentFrame;
    }

    @Override
    public void checkGameEnd() {
        boolean gameIsEnded = true;
        for (Map.Entry<JButton, Pair<Integer, Integer>> entry : this.playground.entrySet()) {
            if (entry.getKey().isEnabled() && !entry.getKey().getText().equals("B")) {
                gameIsEnded = false;
            }
        }

        if (gameIsEnded) {
            this.currentFrame.dispose();
            this.currentFrame = new GUI(nColAndnRows);

        }
    }

    private JButton getBtnFromCoordinate(Pair<Integer, Integer> coordinates) {
        for (Map.Entry<JButton, Pair<Integer, Integer>> entry : this.playground.entrySet()) {
            if (coordinates.getX() == entry.getValue().getX() && coordinates.getY() == entry.getValue().getY()) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("bad coordinates");
    }

    @Override
    public void togleButton(JButton btn) {
        if (btn.getText() == "B") {
            return;
        }

        btn.setText("B");
        final Pair<Integer, Integer> btnCoordinates = this.playground.get(btn);
        
        for (int i = 0; i < this.nColAndnRows; i++) {
            /* versione più brutta ma efficace */
            // Pair<Integer, Integer> toCheck = new Pair<Integer, Integer>(i +
            // this.playground.get(btn).getX(),
            // i + this.playground.get(btn).getY());
            // if (toCheck.getX() < this.nColAndnRows && toCheck.getY() < this.nColAndnRows
            // && toCheck.getX() != this.playground.get(btn).getX()
            // && toCheck.getY() != this.playground.get(btn).getY()) {
            // getBtnFromCoordinate(new Pair<Integer, Integer>(toCheck.getX(),
            // toCheck.getY())).setEnabled(false);
            // }

            // toCheck = new Pair<Integer, Integer>(this.playground.get(btn).getX() - i,
            // this.playground.get(btn).getY() - i);
            // if (toCheck.getX() >= 0 && toCheck.getY() >= 0 && toCheck.getX() !=
            // this.playground.get(btn).getX()
            // && toCheck.getY() != this.playground.get(btn).getY()) {
            // getBtnFromCoordinate(new Pair<Integer, Integer>(toCheck.getX(),
            // toCheck.getY())).setEnabled(false);
            // }

            // toCheck = new Pair<Integer, Integer>(this.playground.get(btn).getX() - i,
            // this.playground.get(btn).getY() + i);
            // if (toCheck.getX() >= 0 && toCheck.getY() < this.nColAndnRows
            // && toCheck.getX() != this.playground.get(btn).getX()
            // && toCheck.getY() != this.playground.get(btn).getY()) {
            // getBtnFromCoordinate(new Pair<Integer, Integer>(toCheck.getX(),
            // toCheck.getY())).setEnabled(false);
            // }

            // toCheck = new Pair<Integer, Integer>(this.playground.get(btn).getX() + i,
            // this.playground.get(btn).getY() - i);
            // if (toCheck.getX() < this.nColAndnRows && toCheck.getY() >= 0
            // && toCheck.getX() != this.playground.get(btn).getX()
            // && toCheck.getY() != this.playground.get(btn).getY()) {
            // getBtnFromCoordinate(new Pair<Integer, Integer>(toCheck.getX(),
            // toCheck.getY())).setEnabled(false);
            // }
            /* versione migliore */
            for (Pair<Integer, Integer> directions : List.of(new Pair<Integer, Integer>(-1, -1),
                    new Pair<Integer, Integer>(1, -1), new Pair<Integer, Integer>(-1, 1),
                    new Pair<Integer, Integer>(1, 1))) {

                Pair<Integer, Integer> toCheck = new Pair<Integer, Integer>(
                        (directions.getX() < 1) ? btnCoordinates.getX() - i : btnCoordinates.getX() + i,
                        (directions.getY() < 1) ? btnCoordinates.getY() - i : btnCoordinates.getY() + i);
                if (checkIfCoordiantesAreValid(toCheck, btn)) {
                    getBtnFromCoordinate(toCheck).setEnabled(false);
                }
            }

        }

        checkGameEnd();

    }

    private boolean checkIfCoordiantesAreValid(Pair<Integer, Integer> toCheck, JButton btn) {
        if (toCheck.getX() < this.nColAndnRows && toCheck.getY() >= 0 && toCheck.getY() < this.nColAndnRows
                && toCheck.getX() >= 0 && toCheck.getX() != this.playground.get(btn).getX()
                && toCheck.getY() != this.playground.get(btn).getY()) {
            return true;
        }
        return false;

    }

}
