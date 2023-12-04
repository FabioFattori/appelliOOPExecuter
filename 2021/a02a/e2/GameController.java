package a02a.e2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameController implements Controller {
    private final HashMap<JButton, Pair<Integer, Integer>> map;
    private JFrame currentFrame;
    private JButton previusMossa;
    private int counter;
    private final int size;

    public GameController(HashMap<JButton, Pair<Integer, Integer>> map, JFrame frame, int size) {
        this.map = map;
        this.currentFrame = frame;
        this.previusMossa = null;
        this.counter = -1;
        this.size = size;
    }

    private JButton getBTNFromCoordinates(Pair<Integer, Integer> coor) {
        for (Map.Entry<JButton, Pair<Integer, Integer>> entry : this.map.entrySet()) {
            if (entry.getValue().getX() == coor.getX() && entry.getValue().getY() == coor.getY()) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("bad coordinates");
    }

    private boolean checkCoordinatesValidity(Pair<Integer, Integer> coor) {
        return (coor.getX() < size && coor.getY() < size && coor.getX() >= 0 && coor.getY() >= 0);
    }

    private Optional<JButton> checkSorrundingArea(JButton btn) {

        List<Pair<Integer, Integer>> directions = List.of(new Pair<>(1, 0), new Pair<>(0, -1), new Pair<>(-1, 0),
                new Pair<>(0, 1));

        for (Pair<Integer, Integer> direction : directions) {
            Pair<Integer, Integer> toCheck = new Pair<Integer, Integer>(this.map.get(btn).getX() + direction.getX(),
                    this.map.get(btn).getY() + direction.getY());

            if (checkCoordinatesValidity(toCheck) && getBTNFromCoordinates(toCheck).getText() == " ") {
                return Optional.of(getBTNFromCoordinates(toCheck));
            }
        }

        return Optional.empty();
 
    }

    private void EndGame() {
        this.currentFrame.dispose();
        this.currentFrame = new GUI(size);
        this.currentFrame.setVisible(true);
    }

    @Override
    public void togleBTN(JButton btn) {
        this.counter++;
        if (this.previusMossa == null) {
            this.previusMossa = btn;
            btn.setText(this.counter + "");
            return;
        }

        Optional<JButton> nextMove = checkSorrundingArea(this.previusMossa);
        if (nextMove.isEmpty()) {
            EndGame();
        } else {
            this.previusMossa = nextMove.get();
            this.previusMossa.setText(this.counter + "");
        }
    }

}
