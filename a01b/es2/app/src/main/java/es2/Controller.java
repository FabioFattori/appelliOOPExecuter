package es2;

import java.util.List;

import javax.swing.JButton;

public interface Controller {
    public List<JButton> togleButton(JButton btnTogled);
    public void endGame();
}
