package a02b.e2;

import javax.swing.*;

import a02b.e2.Logic.CellTypes;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private final String filledCell = "*";
    private final String goRightCell = "R";
    private final String goLeftCell = "L";
    private final String unFilledCell = "";
    private final int NChangeDirectionCells = 20;
    private final Logic logic;
    private final Map<Pair<Integer, Integer>, JButton> map;

    public GUI(int size) {
        this.logic = new LogicImpl(size, NChangeDirectionCells, NChangeDirectionCells);
        this.map = new HashMap<>();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50 * size, 50 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            logic.move();
            repaint(logic.getMap());
            if (logic.checkEnd()) {
                System.exit(0);
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var pos = new Pair<>(i,j);
                final JButton jb = new JButton(this.unFilledCell);
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
                if (logicMap[i][j] == CellTypes.Piena) {
                    this.map.get(coor).setText(this.filledCell);
                } else if (logicMap[i][j] == CellTypes.Left) {
                    this.map.get(coor).setText(this.goLeftCell);
                } else if (logicMap[i][j] == CellTypes.Right) {
                    this.map.get(coor).setText(this.goRightCell);
                } else {
                    this.map.get(coor).setText(this.unFilledCell);
                }
            }
        }
    }

}
