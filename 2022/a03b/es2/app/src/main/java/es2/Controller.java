package es2;

import javax.swing.JButton;

public interface Controller {
    public void togleBtn(JButton btn);

    public void resetGame();

    public boolean checkIfGameShouldEnd();
    
}