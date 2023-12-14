package a01a.e2;

import javax.swing.*;

import a01a.e2.Logic.CellType;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    private final String VISITEDCELL = "*";
    private final String NOTVISITEDCELL = "";
    private final Logic logic;
    private final Map<Pair<Integer, Integer>, JButton> field;

    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var button = (JButton) e.getSource();
                repaint(logic.makeAMove(getCoordinateFromBtn(button)));

                if (logic.checkEnd()) {
                    System.exit(0);
                }
            }
        };

        field = new HashMap<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(NOTVISITEDCELL);
                this.field.put(new Pair<Integer, Integer>(i, j), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private Pair<Integer, Integer> getCoordinateFromBtn(JButton button) {
        for (Map.Entry<Pair<Integer, Integer>, JButton> entry : this.field.entrySet()) {
            if (entry.getValue() == button) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("Unknown button passed");
    }

    private void repaint(CellType[][] fieldToRappresent) {
        for (int i = 0; i < fieldToRappresent.length; i++) {
            for (int j = 0; j < fieldToRappresent[i].length; j++) {
                if (fieldToRappresent[i][j] == CellType.Visited) {
                    this.field.get(new Pair<Integer, Integer>(i, j)).setText(VISITEDCELL);
                } else {
                    this.field.get(new Pair<Integer, Integer>(i, j)).setText(NOTVISITEDCELL);
                }
            }
        }
    }
}
