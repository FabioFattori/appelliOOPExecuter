package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private Controller gamController;
       
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	gamController.togleBtn((JButton)e.getSource());
        	if(gamController.endGame()){
                this.dispose();
            }
        };
        HashMap<JButton, Pair<Integer, Integer>> gameMap = new HashMap<>();    
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton("");
                gameMap.put(jb,new Pair<Integer,Integer>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.gamController = new GameController(gameMap, size);
        this.gamController.populateGamingField();
        this.setVisible(true);
    }

    
    
}
