package magnet;

import javax.swing.*;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class Main
{
    public static void main(String[] args)
    {
        List<FieldSource> sources = new ArrayList<>();

        // classic dipole pair
        sources.add(new Dipole(-2, 0, 1, 0));
        sources.add(new Dipole(2, 0, -1, 0));

        JFrame frame = new JFrame("Magnetic Field - Particle Flow");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);

        FieldPanel panel = new FieldPanel(sources);
        // buttons
        JButton addBtn = new JButton("Add Magnet");
        JButton delBtn = new JButton("Delete Magnet");

        addBtn.addActionListener(e -> panel.addMagnet());
        delBtn.addActionListener(e -> panel.removeSelected());

        // layout
        JPanel controls = new JPanel();
        controls.add(addBtn);
        controls.add(delBtn);

        frame.setLayout(new java.awt.BorderLayout());
        frame.add(panel, java.awt.BorderLayout.CENTER);
        frame.add(controls, java.awt.BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.add(panel);

        frame.setVisible(true);
    }

    public static Vec2 totalField(double x, double y, List<FieldSource> sources)
    {
        Vec2 sum = new Vec2(0, 0);

        for (FieldSource s : sources)
        {
            Vec2 f = s.getField(x, y);
            sum = sum.add(f);
        }

        return sum;
    }
}