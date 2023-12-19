package a01a.e2;

import javax.swing.*;

import a01a.e2.Logic.CellTypes;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private final String Filled ="*";
    private final String UnFilled ="";
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
        	var button = (JButton)e.getSource();
            logic.fillACell(getCoordinateFromButton(button));
            repaint(logic.getMap());
            if(logic.checkEnd()){
                this.diableAll();
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(this.UnFilled);
                this.map.put(new Pair<Integer,Integer>(i,j), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private Pair<Integer,Integer> getCoordinateFromButton(JButton button){
        return this.map.entrySet().stream().filter((entry)->entry.getValue() == button).findFirst().get().getKey();
    }

    private void diableAll(){
        this.map.values().forEach(btn->btn.setEnabled(false));
    }

    private void repaint(CellTypes[][] logicMap){
        for (int i = 0; i < logicMap.length; i++) {
            for (int j = 0; j < logicMap[i].length; j++) {
                if(logicMap[i][j] == CellTypes.Piena){
                    this.map.get(new Pair<Integer,Integer>(i,j)).setText(this.Filled);
                }else{
                    this.map.get(new Pair<Integer,Integer>(i,j)).setText(this.UnFilled);
                }
            }
        }
    }
    
}
