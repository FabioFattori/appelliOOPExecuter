package es2;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameController implements Controller{

    private final HashMap<JButton,Pair<Integer,Integer>> map;
    private final int size;
    private JFrame currentFrame;

    public GameController(int size,HashMap<JButton,Pair<Integer,Integer>> map,JFrame frame){
        this.map = map;
        this.size = size;
        this.currentFrame = frame;
    }

    @Override
    public void togleBtn(JButton btn) {
        if(btn.getText() == "*"){
            /* check if btn can eat and if true it will eat */
            if(checkIfPedoneCanEat(this.map.get(btn))){
                   
            }else{
                movePedone(btn);
            }

            if(checkIfGameShouldEnd()){
                resetGame();
            }
        }
    }

    private void movePedone(JButton btn){
        Pair<Integer,Integer> movedCoordinate = new Pair<Integer,Integer>(this.map.get(btn).getX()-1, this.map.get(btn).getY());
        if(checkIfCoordinatesAreValid(movedCoordinate) && getBtnGivenCoordinates(movedCoordinate).getText() != "*"){
            btn.setText("");
            getBtnGivenCoordinates(movedCoordinate).setText("*");
        }
    }

    private boolean checkIfPedoneCanEat(Pair<Integer,Integer> btnCoordinates){
        Pair<Integer,Integer> toCheck = new Pair<Integer,Integer>(btnCoordinates.getX()-1, btnCoordinates.getY()-1);
        if(checkIfCoordinatesAreValid(toCheck)&&getBtnGivenCoordinates(toCheck).getText()=="o"){
            eatPedone(btnCoordinates,toCheck);
            return true;
        }

        toCheck = new Pair<Integer,Integer>(btnCoordinates.getX()-1, btnCoordinates.getY()+1);
        if(checkIfCoordinatesAreValid(toCheck)&&getBtnGivenCoordinates(toCheck).getText()=="o"){
            eatPedone(btnCoordinates,toCheck);
            return true;
        }

        return false;
    }

    private boolean checkIfCoordinatesAreValid(Pair<Integer,Integer> cor){
        if(cor.getX()>=0 && cor.getY()>=0 && cor.getX()<size&&cor.getY()<size){
            return true;
        }
        return false;
    }

    private void eatPedone(Pair<Integer,Integer> btnMovingCoordinates,Pair<Integer,Integer> eatenCoordinates){
        getBtnGivenCoordinates(btnMovingCoordinates).setText("");
        getBtnGivenCoordinates(eatenCoordinates).setText("*");

    }

    private JButton getBtnGivenCoordinates(Pair<Integer,Integer> btnCoordinates){
        for (Map.Entry<JButton,Pair<Integer,Integer>> entry : this.map.entrySet()) {
            if(btnCoordinates.getX() == entry.getValue().getX() && btnCoordinates.getY() == entry.getValue().getY()){
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("bad coordinates given");
    }

    @Override
    public void resetGame() {
        this.currentFrame.dispose();
        this.currentFrame = new GUI(size);
        
    }

    @Override
    public boolean checkIfGameShouldEnd() {
        boolean win = true;
       for (Map.Entry<JButton,Pair<Integer,Integer>> entry : this.map.entrySet()) {
            if(entry.getKey().getText()=="o"){
                win= false;
            }
        }
        return win;
    }
    
}
