package es2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ControllerImpl implements Controller {

    private final HashMap<JButton, Pair<Integer, Integer>> playground;
    private final int nMaxRowsAndCols;

    public ControllerImpl() {
        this(new HashMap<>(), 5);
    }

    public ControllerImpl(HashMap<JButton, Pair<Integer, Integer>> map, int size) {
        this.playground = map;
        this.nMaxRowsAndCols = size;
    }

    private JButton getBtnFromCoordinate(Pair<Integer, Integer> coordinates) {
        for (Map.Entry<JButton, Pair<Integer, Integer>> entry : this.playground.entrySet()) {
            if (entry.getValue().getX() == coordinates.getX() && entry.getValue().getY() == coordinates.getY()) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("bad coordinates given");
    }

    @Override
    public List<JButton> togleButton(JButton btnTogled) {
        final List<JButton> btnToChange = new ArrayList<>();
        if (this.playground.get(btnTogled).getX() - 1 >= 0 && this.playground.get(btnTogled).getY() - 1 >= 0) {
            btnToChange.add(getBtnFromCoordinate(new Pair<Integer, Integer>(this.playground.get(btnTogled).getX() - 1,
                    this.playground.get(btnTogled).getY() - 1)));
        }
        if (this.playground.get(btnTogled).getX() - 1 >= 0
                && this.playground.get(btnTogled).getY() + 1 < this.nMaxRowsAndCols) {
            btnToChange.add(getBtnFromCoordinate(new Pair<Integer, Integer>(this.playground.get(btnTogled).getX() - 1,
                    this.playground.get(btnTogled).getY() + 1)));
        }
        if (this.playground.get(btnTogled).getX() + 1 < this.nMaxRowsAndCols
                && this.playground.get(btnTogled).getY() + 1 < this.nMaxRowsAndCols) {
            btnToChange.add(getBtnFromCoordinate(new Pair<Integer, Integer>(this.playground.get(btnTogled).getX() + 1,
                    this.playground.get(btnTogled).getY() + 1)));
        }
        if (this.playground.get(btnTogled).getX() + 1 < this.nMaxRowsAndCols
                && this.playground.get(btnTogled).getY() - 1 >= 0) {
            btnToChange.add(getBtnFromCoordinate(new Pair<Integer, Integer>(this.playground.get(btnTogled).getX() + 1,
                    this.playground.get(btnTogled).getY() - 1)));
        }

        checkWin(btnToChange);
        return btnToChange;
    }

    private void checkWin(List<JButton> btnChanged) {
        if (btnChanged.size() == 4) {
            int counterBlanks = 0;
            int counterTogled = 0;

            for (JButton jButton : btnChanged) {
                if (jButton.getText() == "") {
                    counterTogled++;
                } else {
                    counterBlanks++;
                }
            }

            if (counterBlanks == 1 && counterTogled == 3) {
                endGame();
            }
        }
    }

    @Override
    public void endGame() {
        JFrame f = new JFrame();
        JLabel lbl = new JLabel();
        lbl.setText("YOU WIN");
        f.add(lbl);
        f.pack();
        f.setVisible(true);


        System.exit(0);
    }

}
