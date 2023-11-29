package es2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    private final Controller gameController;
    private final String blankBTN = "";
    private final String togledBTN = "*";
    

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var button = (JButton) e.getSource();
                setGraphicsOfButtons(gameController.togleButton(button));
            }
        };

        HashMap<JButton, Pair<Integer, Integer>> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(this.blankBTN);
                map.put(jb, new Pair<Integer, Integer>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }

        this.gameController = new ControllerImpl(map,size);
        this.setVisible(true);
    }

    public void setGraphicsOfButtons(List<JButton> btnsToChange){
        for (JButton jButton : btnsToChange) {
            if(jButton.getText()==this.blankBTN){
                jButton.setText(this.togledBTN);
            }else{
                jButton.setText(this.blankBTN);
            }
        }
    }
}
