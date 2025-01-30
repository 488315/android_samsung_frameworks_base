package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Double4 {

    /* renamed from: w */
    public double f378w;

    /* renamed from: x */
    public double f379x;

    /* renamed from: y */
    public double f380y;

    /* renamed from: z */
    public double f381z;

    public Double4() {
    }

    public Double4(Double4 data) {
        this.f379x = data.f379x;
        this.f380y = data.f380y;
        this.f381z = data.f381z;
        this.f378w = data.f378w;
    }

    public Double4(double x, double y, double z, double w) {
        this.f379x = x;
        this.f380y = y;
        this.f381z = z;
        this.f378w = w;
    }

    public static Double4 add(Double4 a, Double4 b) {
        Double4 res = new Double4();
        res.f379x = a.f379x + b.f379x;
        res.f380y = a.f380y + b.f380y;
        res.f381z = a.f381z + b.f381z;
        res.f378w = a.f378w + b.f378w;
        return res;
    }

    public void add(Double4 value) {
        this.f379x += value.f379x;
        this.f380y += value.f380y;
        this.f381z += value.f381z;
        this.f378w += value.f378w;
    }

    public void add(double value) {
        this.f379x += value;
        this.f380y += value;
        this.f381z += value;
        this.f378w += value;
    }

    public static Double4 add(Double4 a, double b) {
        Double4 res = new Double4();
        res.f379x = a.f379x + b;
        res.f380y = a.f380y + b;
        res.f381z = a.f381z + b;
        res.f378w = a.f378w + b;
        return res;
    }

    public void sub(Double4 value) {
        this.f379x -= value.f379x;
        this.f380y -= value.f380y;
        this.f381z -= value.f381z;
        this.f378w -= value.f378w;
    }

    public void sub(double value) {
        this.f379x -= value;
        this.f380y -= value;
        this.f381z -= value;
        this.f378w -= value;
    }

    public static Double4 sub(Double4 a, double b) {
        Double4 res = new Double4();
        res.f379x = a.f379x - b;
        res.f380y = a.f380y - b;
        res.f381z = a.f381z - b;
        res.f378w = a.f378w - b;
        return res;
    }

    public static Double4 sub(Double4 a, Double4 b) {
        Double4 res = new Double4();
        res.f379x = a.f379x - b.f379x;
        res.f380y = a.f380y - b.f380y;
        res.f381z = a.f381z - b.f381z;
        res.f378w = a.f378w - b.f378w;
        return res;
    }

    public void mul(Double4 value) {
        this.f379x *= value.f379x;
        this.f380y *= value.f380y;
        this.f381z *= value.f381z;
        this.f378w *= value.f378w;
    }

    public void mul(double value) {
        this.f379x *= value;
        this.f380y *= value;
        this.f381z *= value;
        this.f378w *= value;
    }

    public static Double4 mul(Double4 a, Double4 b) {
        Double4 res = new Double4();
        res.f379x = a.f379x * b.f379x;
        res.f380y = a.f380y * b.f380y;
        res.f381z = a.f381z * b.f381z;
        res.f378w = a.f378w * b.f378w;
        return res;
    }

    public static Double4 mul(Double4 a, double b) {
        Double4 res = new Double4();
        res.f379x = a.f379x * b;
        res.f380y = a.f380y * b;
        res.f381z = a.f381z * b;
        res.f378w = a.f378w * b;
        return res;
    }

    public void div(Double4 value) {
        this.f379x /= value.f379x;
        this.f380y /= value.f380y;
        this.f381z /= value.f381z;
        this.f378w /= value.f378w;
    }

    public void div(double value) {
        this.f379x /= value;
        this.f380y /= value;
        this.f381z /= value;
        this.f378w /= value;
    }

    public static Double4 div(Double4 a, double b) {
        Double4 res = new Double4();
        res.f379x = a.f379x / b;
        res.f380y = a.f380y / b;
        res.f381z = a.f381z / b;
        res.f378w = a.f378w / b;
        return res;
    }

    public static Double4 div(Double4 a, Double4 b) {
        Double4 res = new Double4();
        res.f379x = a.f379x / b.f379x;
        res.f380y = a.f380y / b.f380y;
        res.f381z = a.f381z / b.f381z;
        res.f378w = a.f378w / b.f378w;
        return res;
    }

    public double dotProduct(Double4 a) {
        return (this.f379x * a.f379x) + (this.f380y * a.f380y) + (this.f381z * a.f381z) + (this.f378w * a.f378w);
    }

    public static double dotProduct(Double4 a, Double4 b) {
        return (b.f379x * a.f379x) + (b.f380y * a.f380y) + (b.f381z * a.f381z) + (b.f378w * a.f378w);
    }

    public void addMultiple(Double4 a, double factor) {
        this.f379x += a.f379x * factor;
        this.f380y += a.f380y * factor;
        this.f381z += a.f381z * factor;
        this.f378w += a.f378w * factor;
    }

    public void set(Double4 a) {
        this.f379x = a.f379x;
        this.f380y = a.f380y;
        this.f381z = a.f381z;
        this.f378w = a.f378w;
    }

    public void negate() {
        this.f379x = -this.f379x;
        this.f380y = -this.f380y;
        this.f381z = -this.f381z;
        this.f378w = -this.f378w;
    }

    public int length() {
        return 4;
    }

    public double elementSum() {
        return this.f379x + this.f380y + this.f381z + this.f378w;
    }

    public double get(int i) {
        switch (i) {
            case 0:
                return this.f379x;
            case 1:
                return this.f380y;
            case 2:
                return this.f381z;
            case 3:
                return this.f378w;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, double value) {
        switch (i) {
            case 0:
                this.f379x = value;
                return;
            case 1:
                this.f380y = value;
                return;
            case 2:
                this.f381z = value;
                return;
            case 3:
                this.f378w = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, double value) {
        switch (i) {
            case 0:
                this.f379x += value;
                return;
            case 1:
                this.f380y += value;
                return;
            case 2:
                this.f381z += value;
                return;
            case 3:
                this.f378w += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(double x, double y, double z, double w) {
        this.f379x = x;
        this.f380y = y;
        this.f381z = z;
        this.f378w = w;
    }

    public void copyTo(double[] data, int offset) {
        data[offset] = this.f379x;
        data[offset + 1] = this.f380y;
        data[offset + 2] = this.f381z;
        data[offset + 3] = this.f378w;
    }
}
