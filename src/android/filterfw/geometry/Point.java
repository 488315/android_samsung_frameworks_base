package android.filterfw.geometry;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

/* loaded from: classes.dex */
public class Point {
    public float x;
    public float y;

    public Point() {
    }

    public Point(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public void set(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public boolean IsInUnitRange() {
        float f = this.x;
        if (f < 0.0f || f > 1.0f) {
            return false;
        }
        float f2 = this.y;
        return f2 >= 0.0f && f2 <= 1.0f;
    }

    public Point plus(float f, float f2) {
        return new Point(this.x + f, this.y + f2);
    }

    public Point plus(Point point) {
        return plus(point.x, point.y);
    }

    public Point minus(float f, float f2) {
        return new Point(this.x - f, this.y - f2);
    }

    public Point minus(Point point) {
        return minus(point.x, point.y);
    }

    public Point times(float f) {
        return new Point(this.x * f, this.y * f);
    }

    public Point mult(float f, float f2) {
        return new Point(this.x * f, this.y * f2);
    }

    public float length() {
        return (float) Math.hypot(this.x, this.y);
    }

    public float distanceTo(Point point) {
        return point.minus(this).length();
    }

    public Point scaledTo(float f) {
        return times(f / length());
    }

    public Point normalize() {
        return scaledTo(1.0f);
    }

    public Point rotated90(int i) {
        float f = this.x;
        float f2 = this.y;
        int i2 = 0;
        while (i2 < i) {
            float f3 = -f;
            i2++;
            f = f2;
            f2 = f3;
        }
        return new Point(f, f2);
    }

    public Point rotated(float f) {
        double d = f;
        return new Point((float) ((Math.cos(d) * this.x) - (Math.sin(d) * this.y)), (float) ((Math.sin(d) * this.x) + (Math.cos(d) * this.y)));
    }

    public Point rotatedAround(Point point, float f) {
        return minus(point).rotated(f).plus(point);
    }

    public String toString() {
        return NavigationBarInflaterView.KEY_CODE_START + this.x + ", " + this.y + NavigationBarInflaterView.KEY_CODE_END;
    }
}
