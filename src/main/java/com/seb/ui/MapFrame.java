package com.seb.ui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MapFrame {
    private final JFrame frame;
    private final MapPanel mapPanel;

    public MapFrame() {
        ImageIcon imageIcon = new ImageIcon(MapFrame.class.getResource("/map.gif"));
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mapPanel = new MapPanel(imageIcon);
        frame.setContentPane(mapPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void updateObject(com.seb.model.MapObject object) {
        mapPanel.updateObject(object);
    }

}