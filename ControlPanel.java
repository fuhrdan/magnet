package magnet;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class ControlPanel extends JPanel
{
    private Dipole selected;

    private JTextField nameField = new JTextField(8);
    private JTextField xField = new JTextField(6);
    private JTextField yField = new JTextField(6);
    private JTextField strengthField = new JTextField(6);
    private JTextField angleField = new JTextField(6);

    private DecimalFormat fmt = new DecimalFormat("0.0000");

    public ControlPanel(FieldPanel panel)
    {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton addBtn = new JButton("Add");
        JButton delBtn = new JButton("Delete");

        add(addBtn);
        add(delBtn);

        add(new JLabel("Name:"));
        add(nameField);

        add(new JLabel("X:"));
        add(xField);

        add(new JLabel("Y:"));
        add(yField);

        add(new JLabel("Str:"));
        add(strengthField);

        add(new JLabel("Angle:"));
        add(angleField);

        JButton applyBtn = new JButton("Apply");
        add(applyBtn);

        addBtn.addActionListener(e -> panel.addMagnet());
        delBtn.addActionListener(e -> panel.removeSelected());
        applyBtn.addActionListener(e -> applyChanges());

        setVisible(false);
    }

    public void setSelected(Dipole d)
    {
        selected = d;

        if (d == null)
        {
            setVisible(false);
            return;
        }

        setVisible(true);

        double strength = Math.sqrt(d.mx*d.mx + d.my*d.my);
        double angle = Math.toDegrees(Math.atan2(d.my, d.mx));

        nameField.setText(d.name);
        xField.setText(fmt.format(d.x));
        yField.setText(fmt.format(d.y));
        strengthField.setText(fmt.format(strength));
        angleField.setText(fmt.format(angle));
    }

    private void applyChanges()
    {
        if (selected == null) return;

        try
        {
            selected.name = nameField.getText();

            double x = Double.parseDouble(xField.getText());
            double y = Double.parseDouble(yField.getText());
            double strength = Double.parseDouble(strengthField.getText());
            double angle = Math.toRadians(Double.parseDouble(angleField.getText()));

            selected.x = x;
            selected.y = y;
            selected.mx = strength * Math.cos(angle);
            selected.my = strength * Math.sin(angle);
        }
        catch (Exception ignored) {}
    }
}