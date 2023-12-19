package a01c.e2;

import javax.swing.*;

import a01c.e2.Logic.CellTypes;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final String CellaPiena = "*";
    private final String CellaVuota = "";
    private final Logic logic;
    private final Map<Pair<Integer, Integer>, JButton> map;

    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.map = new HashMap<>();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50 * size, 50 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var button = (JButton) e.getSource();
            logic.fillACell(getCoordinateFromButton(button));

            repaint(logic.getMap());
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(this.CellaVuota);
                this.map.put(new Pair<Integer,Integer>(i,j), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private Pair<Integer,Integer> getCoordinateFromButton(JButton btn){
        return this.map.entrySet().stream().filter((e)->e.getValue() == btn).findFirst().get().getKey();
    }

    private void repaint(CellTypes[][] logicMap) {
        for (int i = 0; i < logicMap.length; i++) {
            for (int j = 0; j < logicMap[i].length; j++) {
                if (logicMap[i][j] == CellTypes.Piena) {
                    this.map.get(new Pair<>(i, j)).setText(this.CellaPiena);
                } else {
                    this.map.get(new Pair<>(i, j)).setText(this.CellaVuota);
                }
            }
        }
    }
}
