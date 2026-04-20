package magnet;

public class Vec2
{
    public double x;
    public double y;

    public Vec2(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Vec2 add(Vec2 other)
    {
        return new Vec2(this.x + other.x, this.y + other.y);
    }
}