package magnet;

public class Wire implements FieldSource
{
    double x, y;
    double current;

    public Wire(double x, double y, double current)
    {
        this.x = x;
        this.y = y;
        this.current = current;
    }

    public Vec2 getField(double px, double py)
    {
        double dx = px - x;
        double dy = py - y;
        double r2 = dx*dx + dy*dy + 0.0001;

        double strength = current / r2;

        return new Vec2(-dy * strength, dx * strength);
    }
}