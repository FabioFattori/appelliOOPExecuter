package a02a.e2;

import javax.swing.*;

import a02a.e2.Logic.CellType;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton,Pair<Integer,Integer>> map;
    private final Logic logic;
    private final String CellFilled = "B";
    private final String CellUnFilled = "";
    
    public GUI(int size) {
        this.logic = new Logicimpl(size);
        this.map = new HashMap<>();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
                repaint(logic.makeAMove(map.get(button)));

                if(logic.checkIfRestartIsNeeded()){
                    repaint(logic.restart());
                }
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(this.CellUnFilled);
                this.map.put(jb,new Pair<Integer,Integer>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }    


    private void repaint(CellType[][] logicMap){
        for (Map.Entry<JButton,Pair<Integer,Integer>> entry : this.map.entrySet()) {
            entry.getKey().setEnabled(true);
            if(logicMap[entry.getValue().getX()][entry.getValue().getY()] == CellType.Vuota){
                entry.getKey().setText(this.CellUnFilled);
            }else if(logicMap[entry.getValue().getX()][entry.getValue().getY()] == CellType.Occupata){
                entry.getKey().setText(CellFilled);
            }else{
                entry.getKey().setEnabled(false);
            }
        }
    }
}
