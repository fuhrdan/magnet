package magnet;

import javax.swing.*;
import java.util.*;
import java.awt.BorderLayout;

public class Main
{
    public static void main(String[] args)
    {
        List<FieldSource> sources = new ArrayList<>();

        // initial magnets
        sources.add(new Dipole(-2, 0, 1, 0, "Magnet 1"));
        sources.add(new Dipole(2, 0, -1, 0, "Magnet 2"));

        JFrame frame = new JFrame("Magnetic Field - Particle Flow");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);

        // create panel first
        FieldPanel panel = new FieldPanel(sources);

        // create control panel (passes panel in)
        ControlPanel controlPanel = new ControlPanel(panel);

        // link them
        panel.setControlPanel(controlPanel);

        // layout
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH); // <-- bottom

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