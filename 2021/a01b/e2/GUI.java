package a01b.e2;

import javax.swing.*;

import a01b.e2.Logic.CellTypes;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private final String Uno = "1";
    private final String Due = "2";
    private final String Tre = "3";
    private final String Piena = "*";
    private final String Vuota = "";
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
            logic.placeANumber(getCoordinateFromButton(button));

            if (logic.checkEnd()) {
                this.disableAll();
            }

            repaint(logic.getMap());
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(this.Vuota);
                this.map.put(new Pair<Integer, Integer>(i, j), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private Pair<Integer, Integer> getCoordinateFromButton(JButton button) {
        return this.map.entrySet().stream().filter((e) -> e.getValue() == button).findFirst().get().getKey();
    }

    private void disableAll() {
        this.map.values().forEach((e) -> e.setEnabled(false));
    }

    private void repaint(CellTypes[][] logicMap) {
        for (int i = 0; i < logicMap.length; i++) {
            for (int j = 0; j < logicMap[i].length; j++) {
                Pair<Integer, Integer> coor = new Pair<Integer, Integer>(i, j);
                if (logicMap[i][j] == CellTypes.Piena) {
                    this.map.get(coor).setText(this.Piena);
                } else if (logicMap[i][j] == CellTypes.Uno) {
                    this.map.get(coor).setText(this.Uno);
                } else if (logicMap[i][j] == CellTypes.Due) {
                    this.map.get(coor).setText(this.Due);
                } else if (logicMap[i][j] == CellTypes.Tre) {
                    this.map.get(coor).setText(this.Tre);
                } else {
                    this.map.get(coor).setText(this.Vuota);
                }
            }
        }
    }

}
