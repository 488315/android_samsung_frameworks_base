package android.filterfw.geometry;

/* loaded from: classes.dex */
public class Rectangle extends Quad {
    public Rectangle() {
    }

    public Rectangle(float f, float f2, float f3, float f4) {
        Point point = new Point(f, f2);
        float f5 = f3 + f;
        Point point2 = new Point(f5, f2);
        float f6 = f2 + f4;
        super(point, point2, new Point(f, f6), new Point(f5, f6));
    }

    public Rectangle(Point point, Point point2) {
        super(point, point.plus(point2.x, 0.0f), point.plus(0.0f, point2.y), point.plus(point2.x, point2.y));
    }

    public static Rectangle fromRotatedRect(Point point, Point point2, float f) {
        return new Rectangle(new Point(point.x - (point2.x / 2.0f), point.y - (point2.y / 2.0f)).rotatedAround(point, f), new Point(point.x + (point2.x / 2.0f), point.y - (point2.y / 2.0f)).rotatedAround(point, f), new Point(point.x - (point2.x / 2.0f), point.y + (point2.y / 2.0f)).rotatedAround(point, f), new Point(point.x + (point2.x / 2.0f), point.y + (point2.y / 2.0f)).rotatedAround(point, f));
    }

    private Rectangle(Point point, Point point2, Point point3, Point point4) {
        super(point, point2, point3, point4);
    }

    public static Rectangle fromCenterVerticalAxis(Point point, Point point2, Point point3) {
        Point pointScaledTo = point2.scaledTo(point3.y / 2.0f);
        Point pointScaledTo2 = point2.rotated90(1).scaledTo(point3.x / 2.0f);
        return new Rectangle(point.minus(pointScaledTo2).minus(pointScaledTo), point.plus(pointScaledTo2).minus(pointScaledTo), point.minus(pointScaledTo2).plus(pointScaledTo), point.plus(pointScaledTo2).plus(pointScaledTo));
    }

    public float getWidth() {
        return this.p1.minus(this.p0).length();
    }

    public float getHeight() {
        return this.p2.minus(this.p0).length();
    }

    public Point center() {
        return this.p0.plus(this.p1).plus(this.p2).plus(this.p3).times(0.25f);
    }

    @Override // android.filterfw.geometry.Quad
    public Rectangle scaled(float f) {
        return new Rectangle(this.p0.times(f), this.p1.times(f), this.p2.times(f), this.p3.times(f));
    }

    @Override // android.filterfw.geometry.Quad
    public Rectangle scaled(float f, float f2) {
        return new Rectangle(this.p0.mult(f, f2), this.p1.mult(f, f2), this.p2.mult(f, f2), this.p3.mult(f, f2));
    }
}
