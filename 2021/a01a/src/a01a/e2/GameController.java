package a01a.e2;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameController implements Controller {
    private final HashMap<JButton, Pair<Integer, Integer>> map;
    private final int size;
    private JFrame cuirrentFrame;
    private Pair<Integer, Integer> firstVertex = null;

    public GameController(HashMap<JButton, Pair<Integer, Integer>> map, int size, JFrame frame) {
        this.size = size;
        this.map = map;
        this.cuirrentFrame = frame;
    }

    private JButton getBtnFromCoordinates(Pair<Integer, Integer> pair) {
        for (Map.Entry<JButton, Pair<Integer, Integer>> entry : this.map.entrySet()) {
            if (entry.getValue().getX() == pair.getX() && entry.getValue().getY() == pair.getY()) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("bad coordinates");
    }

    @Override
    public void endGame() {
        boolean resetIsDeserved = true;
        for (JButton key : this.map.keySet()) {
            if(key.getText() == ""){
                resetIsDeserved = false;
                break;
            }
        }

        if(resetIsDeserved){
            this.cuirrentFrame.dispose();
            this.cuirrentFrame=new GUI(size);
            this.cuirrentFrame.setVisible(true);
        }
    }

    @Override
    public void togleBtn(JButton btn) {
        if (firstVertex == null) {
            firstVertex = this.map.get(btn);
            btn.setText("1");
            return;
        }

    
        boolean entered = false;

        for (int i = this.firstVertex.getX(); i <= this.map.get(btn).getX(); i++) {
            for (int j = this.firstVertex.getY(); j <= this.map.get(btn).getY(); j++) {
                getBtnFromCoordinates(new Pair<Integer, Integer>(i, j)).setText("*");
                entered = true;
            }
        }

        if (!entered) {
            for (int i = this.firstVertex.getX(); i >= this.map.get(btn).getX(); i--) {
                for (int j = this.firstVertex.getY(); j >= this.map.get(btn).getY(); j--) {
                    getBtnFromCoordinates(new Pair<Integer, Integer>(i, j)).setText("*");
                    entered = true;
                }
            }
            if (!entered) {
                for (int i = this.firstVertex.getX(); i <= this.map.get(btn).getX(); i++) {
                    for (int j = this.firstVertex.getY(); j >= this.map.get(btn).getY(); j--) {
                        getBtnFromCoordinates(new Pair<Integer, Integer>(i, j)).setText("*");
                        entered = true;
                    }
                }
                if (!entered) {
                    for (int i = this.firstVertex.getX(); i >= this.map.get(btn).getX(); i--) {
                        for (int j = this.firstVertex.getY(); j <= this.map.get(btn).getY(); j++) {
                            getBtnFromCoordinates(new Pair<Integer, Integer>(i, j)).setText("*");
                            entered = true;
                        }
                    }
                }
            }
        }
        firstVertex = null;
        endGame();
    }
}
