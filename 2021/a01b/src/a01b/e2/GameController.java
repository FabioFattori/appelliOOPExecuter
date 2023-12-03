package a01b.e2;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

public class GameController implements Controller{
    private final HashMap<JButton,Pair<Integer,Integer>> map;
    private JButton firstButtonTogled;
    private JButton SecondButtonTogled;
    private JButton thirdButtonTogled;

    public GameController(HashMap<JButton,Pair<Integer,Integer>> map){
        this.map=map;
        this.firstButtonTogled = null;
    }

    private boolean checkCoordinatesValidity(Pair<Integer,Integer> cor){

        if(cor.getX()==this.map.get(this.firstButtonTogled).getX()){
            return true;
        }
        if(cor.getY()==this.map.get(this.firstButtonTogled).getY()){
            return true;
        }

        return false;
    }

    private JButton getBtnFromCoordinates(Pair<Integer,Integer> cor){
        for (Map.Entry<JButton,Pair<Integer,Integer>> entry : this.map.entrySet()) {
            if(entry.getValue().getX() == cor.getX() && entry.getValue().getY() == cor.getY()){
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("bad Coordinates given");
    }

    private void fillToPoitAToPointB(Pair<Integer,Integer> A,Pair<Integer,Integer> B){
        if(A.getX()==B.getX()){
            if(A.getY()>B.getY()){
                for (int i = B.getY(); i<A.getY() ; i++) {
                    getBtnFromCoordinates(new Pair<Integer,Integer>(A.getX(), i)).setText("*");
                }
            }else{
                for (int i = B.getY(); i > A.getY() ; i--) {
                    getBtnFromCoordinates(new Pair<Integer,Integer>(A.getX(), i)).setText("*");
                }
            }
        }
        if(A.getY()==B.getY()){
            if(A.getX()>B.getX()){
                for (int i = B.getX(); i<A.getX() ; i++) {
                    getBtnFromCoordinates(new Pair<Integer,Integer>(i, A.getY())).setText("*");
                }
            }else{
                for (int i = B.getX(); i > A.getX() ; i--) {
                    getBtnFromCoordinates(new Pair<Integer,Integer>(i,A.getY())).setText("*");
                }
            }
        }
    }

    private boolean checkCoordinatesAreNotInTheSameAxis(Pair<Integer,Integer> A,Pair<Integer,Integer> B){
        if(A.getX()!= B.getX() && A.getY()!= B.getY()){
            return true;
        }
        return false;
    }

    @Override
    public void togleBtn(JButton btn) {
        if(this.firstButtonTogled == null){
            btn.setText("1");
            this.firstButtonTogled = btn;
        }else if(this.SecondButtonTogled == null){
            if(checkCoordinatesValidity(this.map.get(btn))){
                fillToPoitAToPointB(this.map.get(this.firstButtonTogled), this.map.get(btn));
                this.SecondButtonTogled = btn;
                btn.setText("2");
            }
        }else{
            if(!checkCoordinatesAreNotInTheSameAxis(this.map.get(btn), this.map.get(this.SecondButtonTogled))){
                return;
            }
            if(checkCoordinatesValidity(this.map.get(btn))){
                fillToPoitAToPointB(this.map.get(this.firstButtonTogled), this.map.get(btn));
                this.thirdButtonTogled = btn;
                btn.setText("3");
                endGame();
            }
        }
    }

    @Override
    public void endGame() {
        for (JButton btn : this.map.keySet()) {
            btn.setEnabled(false);
        }
    }
    
}
