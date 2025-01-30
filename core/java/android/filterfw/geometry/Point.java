package android.filterfw.geometry;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

/* loaded from: classes.dex */
public class Point {

    /* renamed from: x */
    public float f59x;

    /* renamed from: y */
    public float f60y;

    public Point() {
    }

    public Point(float x, float y) {
        this.f59x = x;
        this.f60y = y;
    }

    public void set(float x, float y) {
        this.f59x = x;
        this.f60y = y;
    }

    public boolean IsInUnitRange() {
        float f = this.f59x;
        if (f >= 0.0f && f <= 1.0f) {
            float f2 = this.f60y;
            if (f2 >= 0.0f && f2 <= 1.0f) {
                return true;
            }
        }
        return false;
    }

    public Point plus(float x, float y) {
        return new Point(this.f59x + x, this.f60y + y);
    }

    public Point plus(Point point) {
        return plus(point.f59x, point.f60y);
    }

    public Point minus(float x, float y) {
        return new Point(this.f59x - x, this.f60y - y);
    }

    public Point minus(Point point) {
        return minus(point.f59x, point.f60y);
    }

    public Point times(float s) {
        return new Point(this.f59x * s, this.f60y * s);
    }

    public Point mult(float x, float y) {
        return new Point(this.f59x * x, this.f60y * y);
    }

    public float length() {
        return (float) Math.hypot(this.f59x, this.f60y);
    }

    public float distanceTo(Point p) {
        return p.minus(this).length();
    }

    public Point scaledTo(float length) {
        return times(length / length());
    }

    public Point normalize() {
        return scaledTo(1.0f);
    }

    public Point rotated90(int count) {
        float nx = this.f59x;
        float ny = this.f60y;
        for (int i = 0; i < count; i++) {
            float ox = nx;
            nx = ny;
            ny = -ox;
        }
        return new Point(nx, ny);
    }

    public Point rotated(float radians) {
        return new Point((float) ((Math.cos(radians) * this.f59x) - (Math.sin(radians) * this.f60y)), (float) ((Math.sin(radians) * this.f59x) + (Math.cos(radians) * this.f60y)));
    }

    public Point rotatedAround(Point center, float radians) {
        return minus(center).rotated(radians).plus(center);
    }

    public String toString() {
        return NavigationBarInflaterView.KEY_CODE_START + this.f59x + ", " + this.f60y + NavigationBarInflaterView.KEY_CODE_END;
    }
}
