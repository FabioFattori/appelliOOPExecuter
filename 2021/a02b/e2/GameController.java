package a02b.e2;

import javax.swing.JButton;
import java.util.*;

public class GameController implements Controller{

    private final HashMap<JButton,Pair<Integer,Integer>> map;
    private final int size;
    private JButton oldPlace;
    private Pair<Integer,Integer> direction;


    public GameController(HashMap<JButton,Pair<Integer,Integer>> map , int size){
        this.map = map;
        this.size = size;
        this.oldPlace = null;
        this.direction = new Pair<Integer,Integer>(-1, 0);
    }

    private boolean checkCoordinatesValidity(Pair<Integer,Integer> coor){
        return coor.getX()>=0&&coor.getY()>=0&&coor.getX()<size&&coor.getY()<size;
    }

    private JButton getBTNFromCoordinates(Pair<Integer,Integer> coor){
        for (Map.Entry<JButton,Pair<Integer,Integer>> entry : this.map.entrySet()) {
            if(coor.getX()==entry.getValue().getX() && coor.getY()==entry.getValue().getY()){
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("bad coordinates");
    }
     


    @Override
    public boolean togleBtn() {
        Pair<Integer,Integer> toCheck = new Pair<Integer,Integer>(this.map.get(this.oldPlace).getX()+this.direction.getX(), this.map.get(this.oldPlace).getY()+this.direction.getY());
        if(checkCoordinatesValidity(toCheck)){
            if(getBTNFromCoordinates(toCheck).getText()=="R"){
                this.direction = new Pair<Integer,Integer>(0, 1);
            }else if(getBTNFromCoordinates(toCheck).getText()=="L"){
                this.direction = new Pair<Integer,Integer>(0, -1);
            }
            // i need to recalculate the coordinates
            toCheck = new Pair<Integer,Integer>(this.map.get(this.oldPlace).getX()+this.direction.getX(), this.map.get(this.oldPlace).getY()+this.direction.getY());
            this.oldPlace.setText(" ");
            this.oldPlace = getBTNFromCoordinates(toCheck);
            this.oldPlace.setText("*");
            return true;
        }

        return false;

    }

    

    @Override
    public void populateGamingField() {
        //generate L & R
        for (int i = 0; i < 20; i++) {
            Pair<Integer,Integer> extracted = new Pair<Integer,Integer>((int)(Math.random()*size), (int)(Math.random()*size));
            if(getBTNFromCoordinates(extracted).getText() != " "){
                i--;
            }else{
                getBTNFromCoordinates(extracted).setText((i%2==0)?"R":"L");
            }
        }

        //generate Player
        this.oldPlace=getBTNFromCoordinates(new Pair<Integer,Integer>(size-1, (int)(Math.random()*size)));
        this.oldPlace.setText("*");
    }
    
}
