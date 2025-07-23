package android.filterfw.geometry;

/* loaded from: classes.dex */
public class Rectangle extends Quad {
    public Rectangle() {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Rectangle(float r4, float r5, float r6, float r7) {
        /*
            r3 = this;
            android.filterfw.geometry.Point r0 = new android.filterfw.geometry.Point
            r0.<init>(r4, r5)
            android.filterfw.geometry.Point r1 = new android.filterfw.geometry.Point
            float r6 = r6 + r4
            r1.<init>(r6, r5)
            android.filterfw.geometry.Point r2 = new android.filterfw.geometry.Point
            float r5 = r5 + r7
            r2.<init>(r4, r5)
            android.filterfw.geometry.Point r4 = new android.filterfw.geometry.Point
            r4.<init>(r6, r5)
            r3.<init>(r0, r1, r2, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.filterfw.geometry.Rectangle.<init>(float, float, float, float):void");
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
        Point scaledTo = point2.scaledTo(point3.y / 2.0f);
        Point scaledTo2 = point2.rotated90(1).scaledTo(point3.x / 2.0f);
        return new Rectangle(point.minus(scaledTo2).minus(scaledTo), point.plus(scaledTo2).minus(scaledTo), point.minus(scaledTo2).plus(scaledTo), point.plus(scaledTo2).plus(scaledTo));
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
