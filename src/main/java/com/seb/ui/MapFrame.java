package com.seb.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import com.seb.model.MapObject;

public class MapFrame {
    private final JFrame frame;
    private final MapPanel mapPanel;

    public MapFrame() {
        ImageIcon imageIcon = new ImageIcon(MapFrame.class.getResource("/map.gif"));
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mapPanel = new MapPanel(imageIcon);
        
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.add(mapPanel, BorderLayout.CENTER);

        JPanel controls = new JPanel();

        JToggleButton category1 = new JToggleButton("Show Category 1");
        JToggleButton category2 = new JToggleButton("Show Category 2");
        JToggleButton all = new JToggleButton("Show All", true);

        ButtonGroup categoryButtons = new ButtonGroup();
        categoryButtons.add(category1);
        categoryButtons.add(category2);
        categoryButtons.add(all);

        category1.addActionListener(event -> {
            mapPanel.setVisibleCategory(1);
            all.setSelected(false);
        });
        category2.addActionListener(event -> {
            mapPanel.setVisibleCategory(2);
            all.setSelected(false);
        });
        all.addActionListener(event -> mapPanel.setVisibleCategory(null));

        controls.add(category1);
        controls.add(category2);
        controls.add(all);

        JPanel legend = new JPanel();
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));

        legend.add(new JLabel("Type 1: RED"));
        legend.add(new JLabel("Type 2: BLUE"));
        legend.add(new JLabel("Type 3: GREEN"));

        content.add(legend, BorderLayout.EAST);

        content.add(controls, BorderLayout.SOUTH);
        frame.setContentPane(content);

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void updateObject(MapObject object) {
        if (object == null) {
            System.out.println("Received null object, ignoring update");
            return;
        }
        Runnable updateTask = () -> mapPanel.updateObject(object);
        if (SwingUtilities.isEventDispatchThread()) {
            updateTask.run();
        } else {
            SwingUtilities.invokeLater(updateTask);
        }
    }
}