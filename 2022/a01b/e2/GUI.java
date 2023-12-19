package a01b.e2;

import javax.swing.*;

import a01b.e2.Logic.CellType;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    private final Map<Pair<Integer, Integer>, JButton> buttonMap;
    private final Logic logic;

    private final String SimboloCellaPiena = "*";
    private final String SimboloCellaVuota = "";

    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.buttonMap = new HashMap<>();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var button = (JButton) e.getSource();
                repaintGraphics(logic.makeAMove(getCoordinateFromButton(button)));

                if(logic.checkEnd()){
                    System.exit(0);
                }
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(this.SimboloCellaVuota);
                this.buttonMap.put(new Pair<Integer, Integer>(i, j), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void repaintGraphics(CellType[][] logicMap) {
        for (int i = 0; i < logicMap.length; i++) {
            for (int j = 0; j < logicMap[i].length; j++) {
                if (logicMap[i][j] == CellType.Vuota) {
                    this.buttonMap.get(new Pair<Integer, Integer>(i, j)).setText(SimboloCellaVuota);
                } else {
                    this.buttonMap.get(new Pair<Integer, Integer>(i, j)).setText(SimboloCellaPiena);
                }
            }
        }
    }

    private Pair<Integer, Integer> getCoordinateFromButton(JButton button) {
        for (Map.Entry<Pair<Integer, Integer>, JButton> entry : this.buttonMap.entrySet()) {
            if (entry.getValue() == button) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("unknown button passed");
    }
}
