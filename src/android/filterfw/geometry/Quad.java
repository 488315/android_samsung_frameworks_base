package android.filterfw.geometry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class Quad {
    public Point p0;
    public Point p1;
    public Point p2;
    public Point p3;

    public Quad() {
    }

    public Quad(Point point, Point point2, Point point3, Point point4) {
        this.p0 = point;
        this.p1 = point2;
        this.p2 = point3;
        this.p3 = point4;
    }

    public boolean IsInUnitRange() {
        return this.p0.IsInUnitRange() && this.p1.IsInUnitRange() && this.p2.IsInUnitRange() && this.p3.IsInUnitRange();
    }

    public Quad translated(Point point) {
        return new Quad(this.p0.plus(point), this.p1.plus(point), this.p2.plus(point), this.p3.plus(point));
    }

    public Quad translated(float f, float f2) {
        return new Quad(this.p0.plus(f, f2), this.p1.plus(f, f2), this.p2.plus(f, f2), this.p3.plus(f, f2));
    }

    public Quad scaled(float f) {
        return new Quad(this.p0.times(f), this.p1.times(f), this.p2.times(f), this.p3.times(f));
    }

    public Quad scaled(float f, float f2) {
        return new Quad(this.p0.mult(f, f2), this.p1.mult(f, f2), this.p2.mult(f, f2), this.p3.mult(f, f2));
    }

    public Rectangle boundingBox() {
        List asList = Arrays.asList(Float.valueOf(this.p0.x), Float.valueOf(this.p1.x), Float.valueOf(this.p2.x), Float.valueOf(this.p3.x));
        List asList2 = Arrays.asList(Float.valueOf(this.p0.y), Float.valueOf(this.p1.y), Float.valueOf(this.p2.y), Float.valueOf(this.p3.y));
        float floatValue = ((Float) Collections.min(asList)).floatValue();
        float floatValue2 = ((Float) Collections.min(asList2)).floatValue();
        return new Rectangle(floatValue, floatValue2, ((Float) Collections.max(asList)).floatValue() - floatValue, ((Float) Collections.max(asList2)).floatValue() - floatValue2);
    }

    public float getBoundingWidth() {
        List asList = Arrays.asList(Float.valueOf(this.p0.x), Float.valueOf(this.p1.x), Float.valueOf(this.p2.x), Float.valueOf(this.p3.x));
        return ((Float) Collections.max(asList)).floatValue() - ((Float) Collections.min(asList)).floatValue();
    }

    public float getBoundingHeight() {
        List asList = Arrays.asList(Float.valueOf(this.p0.y), Float.valueOf(this.p1.y), Float.valueOf(this.p2.y), Float.valueOf(this.p3.y));
        return ((Float) Collections.max(asList)).floatValue() - ((Float) Collections.min(asList)).floatValue();
    }

    public String toString() {
        return "{" + this.p0 + ", " + this.p1 + ", " + this.p2 + ", " + this.p3 + "}";
    }
}
