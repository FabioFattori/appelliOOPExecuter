package a03b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private final Logic logic;
    private final Map<Pair<Integer,Integer>,JButton> map;
    
    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.map = new HashMap<>();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            this.logic.move();

            if(this.logic.checkEnd()){
                System.exit(0);
            }

            repaint(this.logic.getMap());
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(i,j);
                final JButton jb = new JButton(" ");
                this.map.put(pos, jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }

        repaint(this.logic.getMap());
        this.setVisible(true);
    }
    

    private void repaint(int[][] logicMap){
        for (int i = 0; i < logicMap.length; i++) {
            for (int j = 0; j < logicMap[i].length; j++) {
                if(logicMap[i][j] != -1){
                    this.map.get(new Pair<>(i,j)).setText(logicMap[i][j]+"");
                }
            }
        }
    }
}
