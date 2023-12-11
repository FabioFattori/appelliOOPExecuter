package a01a.e2;

import javax.swing.JButton;
import java.util.*;

public class GameController implements Controller {

    private final HashMap<JButton, Pair<Integer, Integer>> map;
    private final int size;
    private final HashMap<Integer, String> playerSigns;
    private int currentPlayer;

    public GameController(HashMap<JButton, Pair<Integer, Integer>> map, int size) {
        this.size = size;
        this.map = map;
        this.playerSigns = new HashMap<>();
        this.playerSigns.put(0, "X");
        this.playerSigns.put(1, "O");
        this.currentPlayer = this.playerSigns.keySet().stream().findFirst().get();
    }

    private JButton getBtnFromCoor(Pair<Integer, Integer> coor) {
        for (Map.Entry<JButton, Pair<Integer, Integer>> entry : this.map.entrySet()) {
            if (coor.getX() == entry.getValue().getX() && coor.getY() == entry.getValue().getY()) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("bad Coordinates");
    }

    private boolean checkCoorValidity(Pair<Integer, Integer> coo) {
        if (coo.getX() >= 0 && coo.getY() >= 0 && coo.getX() < size && coo.getY() < size) {
            return true;
        }

        return false;
    }

    @Override
    public void togleBtn(JButton btn) {
        Pair<Integer, Integer> toCheck = new Pair<Integer, Integer>(this.map.get(btn).getX() + 1,
                this.map.get(btn).getY());
        if (((checkCoorValidity(toCheck) && getBtnFromCoor(toCheck).getText() != "")
                || this.map.get(btn).getX() == this.size - 1) && btn.getText() == "") {
            btn.setText(this.playerSigns.get(currentPlayer));
            currentPlayer = (currentPlayer == 0) ? 1 : 0;
        }
    }

    private boolean checkMap(boolean checkRow) {
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < this.size - 2; i++) {
                Pair<Integer, Integer> start = (checkRow) ? new Pair<Integer, Integer>(j, i)
                        : new Pair<Integer, Integer>(i, j);
                Pair<Integer, Integer> middle = (checkRow) ? new Pair<Integer, Integer>(j, i + 1)
                        : new Pair<Integer, Integer>(i + 1, j);
                Pair<Integer, Integer> end = (checkRow) ? new Pair<Integer, Integer>(j, i + 2)
                        : new Pair<Integer, Integer>(i + 2, j);

                if (getBtnFromCoor(start).getText() == this.playerSigns.get(0)
                        && getBtnFromCoor(middle).getText() == this.playerSigns.get(0)
                        && getBtnFromCoor(end).getText() == this.playerSigns.get(0)) {
                    return true;
                } else if (getBtnFromCoor(start).getText() == this.playerSigns.get(1)
                        && getBtnFromCoor(middle).getText() == this.playerSigns.get(1)
                        && getBtnFromCoor(end).getText() == this.playerSigns.get(1)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkIfMapIsFilled() {
        for (JButton btn : this.map.keySet()) {
            if (btn.getText() == "") {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean checkGameEnd() {
        return checkMap(true) || checkMap(false) || checkIfMapIsFilled();
    }

}
