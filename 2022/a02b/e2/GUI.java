package a02b.e2;

import javax.swing.*;

import a02b.e2.Logic.CellType;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    private final Logic logic;
    private final Map<Pair<Integer, Integer>, JButton> map;
    private final String FilledCell = "*";
    private final String UnFilledCell = "";
    private boolean endGame = false;

    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.map = new HashMap<>();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size, size));
        JButton checkEndButton = new JButton("Go");
        checkEndButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(endGame){
                    logic.resetMap();
                    endGame = false;
                }
                if(logic.checkEndGame()){
                    endGame = true;
                }
                repaint(logic.getMap());
            }
            
        });
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        main.add(BorderLayout.SOUTH, checkEndButton);

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var button = (JButton) e.getSource();
                logic.makeAMove(getCoordinatesFromButton(button));
                repaint(logic.getMap());
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(this.UnFilledCell);
                this.map.put(new Pair<Integer, Integer>(i, j), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private Pair<Integer, Integer> getCoordinatesFromButton(JButton button) {
        return this.map.entrySet().stream().filter((entry) -> entry.getValue() == button).findFirst().get().getKey();
    }

    private void repaint(CellType[][] logicMap) {
        for (int i = 0; i < logicMap.length; i++) {
            for (int j = 0; j < logicMap[i].length; j++) {
                if (logicMap[i][j] == CellType.Piena) {
                    this.map.get(new Pair<>(i, j)).setText(this.FilledCell);
                } else if (logicMap[i][j] == CellType.DiagonalFound) {
                    this.map.get(new Pair<>(i, j)).setEnabled(false);
                } else {
                    this.map.get(new Pair<>(i, j)).setText(this.UnFilledCell);
                    this.map.get(new Pair<>(i, j)).setEnabled(true);
                }
            }
        }
    }
}
