package android.filterfw.geometry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class Quad {

    /* renamed from: p0 */
    public Point f61p0;

    /* renamed from: p1 */
    public Point f62p1;

    /* renamed from: p2 */
    public Point f63p2;

    /* renamed from: p3 */
    public Point f64p3;

    public Quad() {
    }

    public Quad(Point p0, Point p1, Point p2, Point p3) {
        this.f61p0 = p0;
        this.f62p1 = p1;
        this.f63p2 = p2;
        this.f64p3 = p3;
    }

    public boolean IsInUnitRange() {
        return this.f61p0.IsInUnitRange() && this.f62p1.IsInUnitRange() && this.f63p2.IsInUnitRange() && this.f64p3.IsInUnitRange();
    }

    public Quad translated(Point t) {
        return new Quad(this.f61p0.plus(t), this.f62p1.plus(t), this.f63p2.plus(t), this.f64p3.plus(t));
    }

    public Quad translated(float x, float y) {
        return new Quad(this.f61p0.plus(x, y), this.f62p1.plus(x, y), this.f63p2.plus(x, y), this.f64p3.plus(x, y));
    }

    public Quad scaled(float s) {
        return new Quad(this.f61p0.times(s), this.f62p1.times(s), this.f63p2.times(s), this.f64p3.times(s));
    }

    public Quad scaled(float x, float y) {
        return new Quad(this.f61p0.mult(x, y), this.f62p1.mult(x, y), this.f63p2.mult(x, y), this.f64p3.mult(x, y));
    }

    public Rectangle boundingBox() {
        List<Float> xs = Arrays.asList(Float.valueOf(this.f61p0.f59x), Float.valueOf(this.f62p1.f59x), Float.valueOf(this.f63p2.f59x), Float.valueOf(this.f64p3.f59x));
        List<Float> ys = Arrays.asList(Float.valueOf(this.f61p0.f60y), Float.valueOf(this.f62p1.f60y), Float.valueOf(this.f63p2.f60y), Float.valueOf(this.f64p3.f60y));
        float x0 = ((Float) Collections.min(xs)).floatValue();
        float y0 = ((Float) Collections.min(ys)).floatValue();
        float x1 = ((Float) Collections.max(xs)).floatValue();
        float y1 = ((Float) Collections.max(ys)).floatValue();
        return new Rectangle(x0, y0, x1 - x0, y1 - y0);
    }

    public float getBoundingWidth() {
        List<Float> xs = Arrays.asList(Float.valueOf(this.f61p0.f59x), Float.valueOf(this.f62p1.f59x), Float.valueOf(this.f63p2.f59x), Float.valueOf(this.f64p3.f59x));
        return ((Float) Collections.max(xs)).floatValue() - ((Float) Collections.min(xs)).floatValue();
    }

    public float getBoundingHeight() {
        List<Float> ys = Arrays.asList(Float.valueOf(this.f61p0.f60y), Float.valueOf(this.f62p1.f60y), Float.valueOf(this.f63p2.f60y), Float.valueOf(this.f64p3.f60y));
        return ((Float) Collections.max(ys)).floatValue() - ((Float) Collections.min(ys)).floatValue();
    }

    public String toString() {
        return "{" + this.f61p0 + ", " + this.f62p1 + ", " + this.f63p2 + ", " + this.f64p3 + "}";
    }
}
