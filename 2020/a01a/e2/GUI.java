package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    private Controller gameController;

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            this.gameController.togleBtn((JButton) e.getSource());
            if (this.gameController.checkGameEnd()) {
                this.dispose();
            }
        };
        HashMap<JButton, Pair<Integer, Integer>> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton("");
                map.put(jb, new Pair<Integer, Integer>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.gameController = new GameController(map, size);
        this.setVisible(true);
    }

}
