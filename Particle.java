package magnet;

public class Particle
{
    public double x;
    public double y;

    public Particle(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public void update(java.util.List<FieldSource> sources)
    {
        Vec2 field = Main.totalField(x, y, sources);

        double speed = 0.1;

        double len = Math.sqrt(field.x * field.x + field.y * field.y);
        if (len > 0.00001)
        {
            x += (field.x / len) * speed;
            y += (field.y / len) * speed;
        }
    }
}