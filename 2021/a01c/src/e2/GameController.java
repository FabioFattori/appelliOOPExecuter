package e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.JButton;

public class GameController implements Controller {
    private final HashMap<JButton, Pair<Integer, Integer>> map;
    private JButton oldMossa;
    private Optional<Integer> oldMossaDirection = Optional.of(0);

    public GameController(HashMap<JButton, Pair<Integer, Integer>> map) {
        this.map = map;
        oldMossa = null;
    }

    private JButton getBtnFromCoordinates(Pair<Integer, Integer> coor) {
        for (Map.Entry<JButton, Pair<Integer, Integer>> entry : this.map.entrySet()) {
            if (coor.getX() == entry.getValue().getX() && coor.getY() == entry.getValue().getY()) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("bad coordinates");
    }

    private void fillPathToNewBTN(Pair<Integer, Integer> coor, JButton btn) {
        Pair<Integer, Integer> coordinateOldMossa = this.map.get(this.oldMossa);
        if (coor.getY() == coordinateOldMossa.getY() && this.oldMossaDirection.get() != 1) {
            if (coor.getX() < coordinateOldMossa.getX()) {
                for (int i = coordinateOldMossa.getX(); i > coor.getX(); i--) {
                    getBtnFromCoordinates(new Pair<Integer, Integer>(i, coor.getY())).setText("*");
                }

            } else {
                for (int i = coordinateOldMossa.getX(); i < coor.getX(); i++) {
                    getBtnFromCoordinates(new Pair<Integer, Integer>(i, coor.getY())).setText("*");
                }
            }
            oldMossaDirection = Optional.of(1);
            oldMossa = btn;
        } else if (this.oldMossaDirection.get() != 2) {
            if (coor.getY() < coordinateOldMossa.getY()) {
                for (int i = coordinateOldMossa.getY(); i > coor.getY(); i--) {
                    getBtnFromCoordinates(new Pair<Integer, Integer>(coor.getX(), i)).setText("*");
                }
            } else {
                for (int i = coordinateOldMossa.getY(); i < coor.getY(); i++) {
                    getBtnFromCoordinates(new Pair<Integer, Integer>(coor.getX(), i)).setText("*");
                }
            }
            oldMossaDirection = Optional.of(2);
            oldMossa = btn;
        }
    }

    @Override
    public void togleBtn(JButton btn) {
        if (oldMossa == null) {
            btn.setText("*");
            oldMossa = btn;
            return;
        }

        fillPathToNewBTN(this.map.get(btn), btn);
    }

}
