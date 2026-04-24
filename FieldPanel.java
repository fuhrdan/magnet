package magnet;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class FieldPanel extends JPanel implements 
    MouseListener, 
    MouseMotionListener, 
    KeyListener
{
    private List<FieldSource> sources;
    private List<Particle> particles;

    private ControlPanel controlPanel;
    private int selectedIndex = -1;
    private int magnetCounter = 1;

public FieldPanel(List<FieldSource> sources)
{
    this.sources = sources;
    this.particles = new ArrayList<>();

    setBackground(Color.black);

    addMouseListener(this);
    addMouseMotionListener(this);
    setFocusable(true);
    addKeyListener(this);

    Random rand = new Random();
    for (int i = 0; i < 1500; i++)
    {
        double x = (rand.nextDouble() - 0.5) * 20;
        double y = (rand.nextDouble() - 0.5) * 20;
        particles.add(new Particle(x, y));
    }

    Timer timer = new Timer(16, e -> {
        updateParticles();
        repaint();
    });
    timer.start();
}

public void setControlPanel(ControlPanel cp)
{
    this.controlPanel = cp;
}

    private void updateParticles()
    {
        for (Particle p : particles)
        {
            p.update(sources);

            // reset if too far away
            if (Math.abs(p.x) > 20 || Math.abs(p.y) > 20)
            {
                p.x = (Math.random() - 0.5) * 20;
                p.y = (Math.random() - 0.5) * 20;
            }
        }
    }

    private Dipole getSelected()
    {
        if (selectedIndex >= 0 && selectedIndex < sources.size())
        {
            return (Dipole)sources.get(selectedIndex);
        }
        return null;
    }

    public void addMagnet()
    {
        Dipole d = new Dipole(0, 0, 1, 0, "Magnet " + magnetCounter++);
        sources.add(d);
        selectedIndex = sources.size() - 1;
        controlPanel.setSelected(d);
    }
    
    public void removeSelected()
    {
        if (selectedIndex >= 0 && selectedIndex < sources.size())
        {
            sources.remove(selectedIndex);

            if (sources.size() == 0)
            {
                selectedIndex = -1;
                controlPanel.setSelected(null);
            }
            else
            {
                selectedIndex = Math.min(selectedIndex, sources.size() - 1);
                controlPanel.setSelected(getSelected());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        // TRAIL EFFECT (comment this out if you want a clean redraw)
        g.setColor(new Color(0, 0, 0, 40));
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // draw particles
        g2.setColor(Color.green);

        for (Particle p : particles)
        {
            int sx = (int)(width / 2 + p.x * 30);
            int sy = (int)(height / 2 + p.y * 30);

            g2.fillRect(sx, sy, 2, 2);
        }

        // draw magnets (with selection highlight)
        for (int i = 0; i < sources.size(); i++)
        {
            Dipole d = (Dipole)sources.get(i);
    
            if (i == selectedIndex)
            {
                g2.setColor(Color.yellow); // selected
            }
            else
            {
                g2.setColor(Color.red); // normal
            }

                int sx = (int)(width / 2 + d.x * 30);
                int sy = (int)(height / 2 + d.y * 30);

                g2.fillOval(sx - 6, sy - 6, 12, 12);
            }
        }

    // =========================
    // MOUSE INTERACTION
    // =========================

    @Override
    public void mousePressed(MouseEvent e)
    {
        requestFocusInWindow();

        double wx = (e.getX() - getWidth() / 2.0) / 30.0;
        double wy = (e.getY() - getHeight() / 2.0) / 30.0;

        selectedIndex = -1;
        double minDist = Double.MAX_VALUE;

        for (int i = 0; i < sources.size(); i++)
        {
            Dipole d = (Dipole)sources.get(i);

            double dx = d.x - wx;
            double dy = d.y - wy;
            double dist = dx*dx + dy*dy;

            if (dist < minDist && dist < 1.0)
            {
                minDist = dist;
                selectedIndex = i;
            }
        }

        controlPanel.setSelected(getSelected());
    }

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseDragged(MouseEvent e)
    {
        Dipole d = getSelected();

        if (d != null)
        {
            d.x = (e.getX() - getWidth() / 2.0) / 30.0;
            d.y = (e.getY() - getHeight() / 2.0) / 30.0;
        }
    }


    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_INSERT)
        {
            addMagnet();
        }
        else if (e.getKeyCode() == KeyEvent.VK_DELETE)
        {
            removeSelected();
        }
        else if (e.getKeyCode() == KeyEvent.VK_TAB)
        {
            if (sources.size() > 0)
            {
                selectedIndex = (selectedIndex + 1) % sources.size();
                controlPanel.setSelected(getSelected());
                repaint();
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}    // required but unused
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}