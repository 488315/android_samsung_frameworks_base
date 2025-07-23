package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Double2 {
    public double x;
    public double y;

    public int length() {
        return 2;
    }

    public Double2() {
    }

    public Double2(Double2 double2) {
        this.x = double2.x;
        this.y = double2.y;
    }

    public Double2(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public static Double2 add(Double2 double2, Double2 double22) {
        Double2 double23 = new Double2();
        double23.x = double2.x + double22.x;
        double23.y = double2.y + double22.y;
        return double23;
    }

    public void add(Double2 double2) {
        this.x += double2.x;
        this.y += double2.y;
    }

    public void add(double d) {
        this.x += d;
        this.y += d;
    }

    public static Double2 add(Double2 double2, double d) {
        Double2 double22 = new Double2();
        double22.x = double2.x + d;
        double22.y = double2.y + d;
        return double22;
    }

    public void sub(Double2 double2) {
        this.x -= double2.x;
        this.y -= double2.y;
    }

    public static Double2 sub(Double2 double2, Double2 double22) {
        Double2 double23 = new Double2();
        double23.x = double2.x - double22.x;
        double23.y = double2.y - double22.y;
        return double23;
    }

    public void sub(double d) {
        this.x -= d;
        this.y -= d;
    }

    public static Double2 sub(Double2 double2, double d) {
        Double2 double22 = new Double2();
        double22.x = double2.x - d;
        double22.y = double2.y - d;
        return double22;
    }

    public void mul(Double2 double2) {
        this.x *= double2.x;
        this.y *= double2.y;
    }

    public static Double2 mul(Double2 double2, Double2 double22) {
        Double2 double23 = new Double2();
        double23.x = double2.x * double22.x;
        double23.y = double2.y * double22.y;
        return double23;
    }

    public void mul(double d) {
        this.x *= d;
        this.y *= d;
    }

    public static Double2 mul(Double2 double2, double d) {
        Double2 double22 = new Double2();
        double22.x = double2.x * d;
        double22.y = double2.y * d;
        return double22;
    }

    public void div(Double2 double2) {
        this.x /= double2.x;
        this.y /= double2.y;
    }

    public static Double2 div(Double2 double2, Double2 double22) {
        Double2 double23 = new Double2();
        double23.x = double2.x / double22.x;
        double23.y = double2.y / double22.y;
        return double23;
    }

    public void div(double d) {
        this.x /= d;
        this.y /= d;
    }

    public static Double2 div(Double2 double2, double d) {
        Double2 double22 = new Double2();
        double22.x = double2.x / d;
        double22.y = double2.y / d;
        return double22;
    }

    public double dotProduct(Double2 double2) {
        return (this.x * double2.x) + (this.y * double2.y);
    }

    public static Double dotProduct(Double2 double2, Double2 double22) {
        return Double.valueOf((double22.x * double2.x) + (double22.y * double2.y));
    }

    public void addMultiple(Double2 double2, double d) {
        this.x += double2.x * d;
        this.y += double2.y * d;
    }

    public void set(Double2 double2) {
        this.x = double2.x;
        this.y = double2.y;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public double elementSum() {
        return this.x + this.y;
    }

    public double get(int i) {
        if (i == 0) {
            return this.x;
        }
        if (i == 1) {
            return this.y;
        }
        throw new IndexOutOfBoundsException("Index: i");
    }

    public void setAt(int i, double d) {
        if (i == 0) {
            this.x = d;
        } else {
            if (i == 1) {
                this.y = d;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, double d) {
        if (i == 0) {
            this.x += d;
        } else {
            if (i == 1) {
                this.y += d;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public void copyTo(double[] dArr, int i) {
        dArr[i] = this.x;
        dArr[i + 1] = this.y;
    }
}
