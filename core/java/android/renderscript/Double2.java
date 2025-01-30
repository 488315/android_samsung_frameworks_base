package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Double2 {

    /* renamed from: x */
    public double f373x;

    /* renamed from: y */
    public double f374y;

    public Double2() {
    }

    public Double2(Double2 data) {
        this.f373x = data.f373x;
        this.f374y = data.f374y;
    }

    public Double2(double x, double y) {
        this.f373x = x;
        this.f374y = y;
    }

    public static Double2 add(Double2 a, Double2 b) {
        Double2 res = new Double2();
        res.f373x = a.f373x + b.f373x;
        res.f374y = a.f374y + b.f374y;
        return res;
    }

    public void add(Double2 value) {
        this.f373x += value.f373x;
        this.f374y += value.f374y;
    }

    public void add(double value) {
        this.f373x += value;
        this.f374y += value;
    }

    public static Double2 add(Double2 a, double b) {
        Double2 res = new Double2();
        res.f373x = a.f373x + b;
        res.f374y = a.f374y + b;
        return res;
    }

    public void sub(Double2 value) {
        this.f373x -= value.f373x;
        this.f374y -= value.f374y;
    }

    public static Double2 sub(Double2 a, Double2 b) {
        Double2 res = new Double2();
        res.f373x = a.f373x - b.f373x;
        res.f374y = a.f374y - b.f374y;
        return res;
    }

    public void sub(double value) {
        this.f373x -= value;
        this.f374y -= value;
    }

    public static Double2 sub(Double2 a, double b) {
        Double2 res = new Double2();
        res.f373x = a.f373x - b;
        res.f374y = a.f374y - b;
        return res;
    }

    public void mul(Double2 value) {
        this.f373x *= value.f373x;
        this.f374y *= value.f374y;
    }

    public static Double2 mul(Double2 a, Double2 b) {
        Double2 res = new Double2();
        res.f373x = a.f373x * b.f373x;
        res.f374y = a.f374y * b.f374y;
        return res;
    }

    public void mul(double value) {
        this.f373x *= value;
        this.f374y *= value;
    }

    public static Double2 mul(Double2 a, double b) {
        Double2 res = new Double2();
        res.f373x = a.f373x * b;
        res.f374y = a.f374y * b;
        return res;
    }

    public void div(Double2 value) {
        this.f373x /= value.f373x;
        this.f374y /= value.f374y;
    }

    public static Double2 div(Double2 a, Double2 b) {
        Double2 res = new Double2();
        res.f373x = a.f373x / b.f373x;
        res.f374y = a.f374y / b.f374y;
        return res;
    }

    public void div(double value) {
        this.f373x /= value;
        this.f374y /= value;
    }

    public static Double2 div(Double2 a, double b) {
        Double2 res = new Double2();
        res.f373x = a.f373x / b;
        res.f374y = a.f374y / b;
        return res;
    }

    public double dotProduct(Double2 a) {
        return (this.f373x * a.f373x) + (this.f374y * a.f374y);
    }

    public static Double dotProduct(Double2 a, Double2 b) {
        return Double.valueOf((b.f373x * a.f373x) + (b.f374y * a.f374y));
    }

    public void addMultiple(Double2 a, double factor) {
        this.f373x += a.f373x * factor;
        this.f374y += a.f374y * factor;
    }

    public void set(Double2 a) {
        this.f373x = a.f373x;
        this.f374y = a.f374y;
    }

    public void negate() {
        this.f373x = -this.f373x;
        this.f374y = -this.f374y;
    }

    public int length() {
        return 2;
    }

    public double elementSum() {
        return this.f373x + this.f374y;
    }

    public double get(int i) {
        switch (i) {
            case 0:
                return this.f373x;
            case 1:
                return this.f374y;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, double value) {
        switch (i) {
            case 0:
                this.f373x = value;
                return;
            case 1:
                this.f374y = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, double value) {
        switch (i) {
            case 0:
                this.f373x += value;
                return;
            case 1:
                this.f374y += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(double x, double y) {
        this.f373x = x;
        this.f374y = y;
    }

    public void copyTo(double[] data, int offset) {
        data[offset] = this.f373x;
        data[offset + 1] = this.f374y;
    }
}
