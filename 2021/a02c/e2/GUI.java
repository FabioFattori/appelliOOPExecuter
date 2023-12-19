package a02c.e2;

import javax.swing.*;

import a02c.e2.Logic.CellTypes;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private final String voidCell = "";
    private final String ballCell = "*";
    private final String obstacleCell = "o";
    private final int nObstacles = 20;
    private final Logic logic;
    private final Map<Pair<Integer, Integer>, JButton> map;

    public GUI(int size) {
        this.logic = new LogicImpl(size, this.nObstacles);
        this.map = new HashMap<>();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50 * size, 50 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            logic.move();

            if(logic.ballGotStuck()){
                System.exit(0);
            }else if(logic.ballGotToEnd()){
                logic.restartLogic();
            }

            repaint(logic.getMap());
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var pos = new Pair<>(i, j);
                final JButton jb = new JButton(this.voidCell);
                this.map.put(pos, jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        repaint(logic.getMap());
        this.setVisible(true);
    }

    private void repaint(CellTypes[][] logicMap) {
        for (int i = 0; i < logicMap.length; i++) {
            for (int j = 0; j < logicMap[i].length; j++) {
                Pair<Integer, Integer> coor = new Pair<Integer, Integer>(i, j);
                if (logicMap[i][j] == CellTypes.Palla) {
                    this.map.get(coor).setText(this.ballCell);
                } else if (logicMap[i][j] == CellTypes.Ostacolo) {
                    this.map.get(coor).setText(this.obstacleCell);
                } else {
                    this.map.get(coor).setText(this.voidCell);
                }
            }
        }
    }

}
