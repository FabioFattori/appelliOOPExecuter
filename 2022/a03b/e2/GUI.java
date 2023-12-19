package a03b.e2;

import javax.swing.*;

import a03b.e2.Logic.CellType;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    private final String EmptyCell = "";
    private final String PedoneAlleato = "*";
    private final String PedoneNemico = "o";

    private final Logic logic;
    private final Map<Pair<Integer, Integer>, JButton> map;

    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.map = new HashMap<>();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var button = (JButton) e.getSource();
                logic.makeAMove(getCoordinatesFromButton(button));
                repaint(logic.getMap());
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(this.EmptyCell);
                this.map.put(new Pair<Integer, Integer>(i, j), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        logic.populateMap();
        repaint(logic.getMap());
        this.setVisible(true);
    }

    private Pair<Integer, Integer> getCoordinatesFromButton(JButton button) {
        return this.map.entrySet().stream().filter((entry) -> entry.getValue() == button).findFirst().get().getKey();
    }

    private void repaint(CellType[][] logicMap) {
        for (int i = 0; i < logicMap.length; i++) {
            for (int j = 0; j < logicMap[i].length; j++) {
                if (logicMap[i][j] == CellType.PedinaAlleata) {
                    this.map.get(new Pair<Integer, Integer>(i, j)).setText(PedoneAlleato);
                } else if (logicMap[i][j] == CellType.PedinaAvversaria) {
                    this.map.get(new Pair<Integer, Integer>(i, j)).setText(PedoneNemico);
                } else {
                    this.map.get(new Pair<Integer, Integer>(i, j)).setText(EmptyCell);
                }
            }
        }
    }
}
