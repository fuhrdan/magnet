package magnet;


public class Dipole implements FieldSource
{
    double x, y;
    double mx, my;
    public String name;

    public Dipole(double x, double y, double mx, double my, String name)
    {
        this.x = x;
        this.y = y;
        this.mx = mx;
        this.my = my;
        this.name = name;
    }

    public Vec2 getField(double px, double py)
    {
        double dx = px - x;
        double dy = py - y;
        double r2 = dx*dx + dy*dy + 0.0001;

        double r5 = r2 * r2 * Math.sqrt(r2);
        double r3 = r2 * Math.sqrt(r2);

        double dot = dx*mx + dy*my;

        double bx = (3 * dx * dot / r5) - (mx / r3);
        double by = (3 * dy * dot / r5) - (my / r3);

        return new Vec2(bx, by);
    }
}