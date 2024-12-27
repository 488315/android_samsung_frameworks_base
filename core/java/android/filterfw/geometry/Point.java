package android.filterfw.geometry;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

public class Point {
    public float x;
    public float y;

    public Point() {}

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean IsInUnitRange() {
        return this.x >= 0.0f && this.x <= 1.0f && this.y >= 0.0f && this.y <= 1.0f;
    }

    public Point plus(float x, float y) {
        return new Point(this.x + x, this.y + y);
    }

    public Point plus(Point point) {
        return plus(point.x, point.y);
    }

    public Point minus(float x, float y) {
        return new Point(this.x - x, this.y - y);
    }

    public Point minus(Point point) {
        return minus(point.x, point.y);
    }

    public Point times(float s) {
        return new Point(this.x * s, this.y * s);
    }

    public Point mult(float x, float y) {
        return new Point(this.x * x, this.y * y);
    }

    public float length() {
        return (float) Math.hypot(this.x, this.y);
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
        float nx = this.x;
        float ny = this.y;
        for (int i = 0; i < count; i++) {
            float ox = nx;
            nx = ny;
            ny = -ox;
        }
        return new Point(nx, ny);
    }

    public Point rotated(float radians) {
        return new Point(
                (float) ((Math.cos(radians) * this.x) - (Math.sin(radians) * this.y)),
                (float) ((Math.sin(radians) * this.x) + (Math.cos(radians) * this.y)));
    }

    public Point rotatedAround(Point center, float radians) {
        return minus(center).rotated(radians).plus(center);
    }

    public String toString() {
        return NavigationBarInflaterView.KEY_CODE_START
                + this.x
                + ", "
                + this.y
                + NavigationBarInflaterView.KEY_CODE_END;
    }
}
