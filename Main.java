package magnet;

import javax.swing.*;
import java.util.*;

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
        frame.setSize(800, 800);

        FieldPanel panel = new FieldPanel(sources);
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