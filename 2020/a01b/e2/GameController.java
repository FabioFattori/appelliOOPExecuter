package a01b.e2;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

public class GameController implements Controller {

    private final HashMap<JButton, Pair<Integer, Integer>> gameMap;
    private final int size;
    private JButton king;

    public GameController(HashMap<JButton, Pair<Integer, Integer>> gameMap, int size) {
        this.size = size;
        this.gameMap = gameMap;
    }

    private JButton getBtnFromCoor(Pair<Integer, Integer> coor) {
        for (Map.Entry<JButton, Pair<Integer, Integer>> entry : this.gameMap.entrySet()) {
            if (coor.getX() == entry.getValue().getX() && coor.getY() == entry.getValue().getY()) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("bad coordinates");
    }

    private boolean checkCoorValidity(Pair<Integer, Integer> coor) {
        return coor.getX() >= 0 && coor.getY() >= 0 && coor.getX() < size && coor.getY() < size;
    }

    private boolean checkIfKingDoesntGetEaten(JButton btn) {
        Pair<Integer, Integer> altoSx = new Pair<Integer, Integer>(this.gameMap.get(btn).getX() - 1,
                this.gameMap.get(btn).getY() - 1);
        Pair<Integer, Integer> altoDx = new Pair<Integer, Integer>(this.gameMap.get(btn).getX() - 1,
                this.gameMap.get(btn).getY() + 1);

        if(checkCoorValidity(altoDx) && checkCoorValidity(altoSx)){
            return getBtnFromCoor(altoDx).getText() != "P" && getBtnFromCoor(altoSx).getText() != "P" ;
        }else if (checkCoorValidity(altoDx)) {
            return getBtnFromCoor(altoDx).getText() != "P" ;
        }else if(checkCoorValidity(altoSx)){
            return getBtnFromCoor(altoSx).getText() != "P";
        }
        // coordinate are not correct so the moves can be done ;
        return true;
    }

    private boolean checkIfMoveCanbeDoneByKing(JButton btn) {
        return (this.gameMap.get(btn).getX() == this.gameMap.get(this.king).getX() - 1
                && this.gameMap.get(btn).getY() == this.gameMap.get(this.king).getY() - 1
                && checkIfKingDoesntGetEaten(btn) &&
                checkIfKingDoesntGetEaten(btn)) ||
                (this.gameMap.get(btn).getX() == this.gameMap.get(this.king).getX() - 1
                        && this.gameMap.get(btn).getY() == this.gameMap.get(this.king).getY()
                        && checkIfKingDoesntGetEaten(btn) &&
                        checkIfKingDoesntGetEaten(btn))
                ||
                (this.gameMap.get(btn).getX() == this.gameMap.get(this.king).getX() - 1
                        && this.gameMap.get(btn).getY() == this.gameMap.get(this.king).getY() + 1
                        && checkIfKingDoesntGetEaten(btn) &&
                        checkIfKingDoesntGetEaten(btn))
                ||
                (this.gameMap.get(btn).getX() == this.gameMap.get(this.king).getX()
                        && this.gameMap.get(btn).getY() == this.gameMap.get(this.king).getY() - 1
                        && checkIfKingDoesntGetEaten(btn) &&
                        checkIfKingDoesntGetEaten(btn))
                ||
                (this.gameMap.get(btn).getX() == this.gameMap.get(this.king).getX()
                        && this.gameMap.get(btn).getY() == this.gameMap.get(this.king).getY() + 1
                        && checkIfKingDoesntGetEaten(btn) &&
                        checkIfKingDoesntGetEaten(btn))
                ||
                (this.gameMap.get(btn).getX() == this.gameMap.get(this.king).getX() + 1
                        && this.gameMap.get(btn).getY() == this.gameMap.get(this.king).getY() - 1
                        && checkIfKingDoesntGetEaten(btn) &&
                        checkIfKingDoesntGetEaten(btn))
                ||
                (this.gameMap.get(btn).getX() == this.gameMap.get(this.king).getX() + 1
                        && this.gameMap.get(btn).getY() == this.gameMap.get(this.king).getY()
                        && checkIfKingDoesntGetEaten(btn) &&
                        checkIfKingDoesntGetEaten(btn))
                ||
                (this.gameMap.get(btn).getX() == this.gameMap.get(this.king).getX() + 1
                        && this.gameMap.get(btn).getY() == this.gameMap.get(this.king).getY() + 1
                        && checkIfKingDoesntGetEaten(btn) &&
                        checkIfKingDoesntGetEaten(btn));
    }

    @Override
    public void togleBtn(JButton btn) {
        if (checkIfMoveCanbeDoneByKing(btn)) {
            btn.setText("K");
            this.king.setText("");
            this.king = btn;
        }

    }

    @Override
    public boolean endGame() {
        for (JButton btn : this.gameMap.keySet()) {
            if(btn.getText() == "P"){
                return false;
            }
        }

        return true;
    }

    @Override
    public void populateGamingField() {
        Pair<Integer,Integer> kingPos = new Pair<Integer,Integer>(this.size-1,this.size-1);
        this.king = getBtnFromCoor(kingPos);
        this.king.setText("K");

        for (int i = 0; i < 3; i++) {
            Pair<Integer,Integer> pedonePos ;
            do {
                pedonePos = new Pair<Integer,Integer>((int)Math.round(Math.random()*(size-2)),(int)Math.round(Math.random()*(size-2)));
            } while (getBtnFromCoor(pedonePos).getText()!="");
            getBtnFromCoor(pedonePos).setText("P");
        }
    }

}
