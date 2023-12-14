package a02a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;

public class GUI extends JFrame {

    private final String CELLAVUOTA = "";
    private final String PLAYER = "p";
    private final String PEDONE = "*";

    private final Map<Pair<Integer,Integer>,JButton> map;
    private Logic logic;
    private final JButton next = new JButton(">");
    
    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.map = new HashMap<>();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        this.getContentPane().add(BorderLayout.SOUTH,next);
        
        next.addActionListener(e -> {
        	
            repaintGraphic(logic.makeAMove());

        	if(logic.checkEndGame()){
                this.next.setEnabled(false);
            }
        });
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(this.CELLAVUOTA);
                this.map.put(new Pair<Integer,Integer>(i,j), jb);
                grid.add(jb);
            }
        }

        repaintGraphic(this.logic.populateMap(5));
        this.setVisible(true);
    } 
    
    private void repaintGraphic(int[][] logicMap){
        for (int i = 0; i < logicMap.length; i++) {
            for (int j = 0; j < logicMap[i].length; j++) {
                Pair<Integer,Integer> coor= new Pair<Integer,Integer>(i, j);
                if(logicMap[i][j] == this.logic.getPedoneSymbol()){
                    this.map.get(coor).setText(PEDONE);
                }else if(logicMap[i][j] == this.logic.getPlayerSymbol()){
                    this.map.get(coor).setText(PLAYER);
                }else{
                    this.map.get(coor).setText(CELLAVUOTA);
                }
            }
        }
    }
}
