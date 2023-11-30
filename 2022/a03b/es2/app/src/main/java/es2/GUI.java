package es2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Controller gameCotnroller;
    private final int maxPieces = 4;
    private  int currentPlayersPieces = 0;
    private  int currentBotPieces = 0;
    private final int size;
     
    
    public GUI(int size) {
        this.size = size;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
                gameCotnroller.togleBtn(button);
            }
        };
        HashMap<JButton,Pair<Integer,Integer>> map = new HashMap<>();        
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton((i == size-1)?choseString("*"):(i<2)?choseString("o"):"");
                map.put(jb, new Pair<Integer,Integer>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }

        createPieces(map);
        this.gameCotnroller=new GameController(size, map,this);
        this.setVisible(true);
    }  
    
    private void createPieces(HashMap<JButton,Pair<Integer,Integer>> map){
        while (this.currentPlayersPieces<this.maxPieces) {
            for (Map.Entry<JButton,Pair<Integer,Integer>> entry : map.entrySet()) {
                if(entry.getValue().getX()==size-1 && entry.getKey().getText()!="*"){
                    entry.getKey().setText("*");
                    break;
                }
            }
            
            this.currentPlayersPieces++;
        }

        while (this.currentBotPieces<this.maxPieces) {
            for (Map.Entry<JButton,Pair<Integer,Integer>> entry : map.entrySet()) {
                if(entry.getValue().getX()<2 && entry.getKey().getText()!="o"){
                    entry.getKey().setText("o");
                    break;
                }
            }
            this.currentBotPieces++;
        }
    }
    
    private String choseString(String toPrint){
        double extractedNumber = Math.floor(Math.random()*2+1);
        
        switch ((int)extractedNumber) {
            case 1:
                if(toPrint == "*"){
                    this.currentPlayersPieces++;
                    if(this.currentPlayersPieces == this.maxPieces){
                        return "";
                    }
                }else{
                    this.currentBotPieces++;
                    if(this.currentBotPieces == this.maxPieces){
                        return "";
                    }
                }
                return toPrint;
                   
            default:
                return "";
        }
    }
    
}
