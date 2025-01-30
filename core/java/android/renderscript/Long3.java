package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Long3 {

    /* renamed from: x */
    public long f402x;

    /* renamed from: y */
    public long f403y;

    /* renamed from: z */
    public long f404z;

    public Long3() {
    }

    public Long3(long i) {
        this.f404z = i;
        this.f403y = i;
        this.f402x = i;
    }

    public Long3(long x, long y, long z) {
        this.f402x = x;
        this.f403y = y;
        this.f404z = z;
    }

    public Long3(Long3 source) {
        this.f402x = source.f402x;
        this.f403y = source.f403y;
        this.f404z = source.f404z;
    }

    public void add(Long3 a) {
        this.f402x += a.f402x;
        this.f403y += a.f403y;
        this.f404z += a.f404z;
    }

    public static Long3 add(Long3 a, Long3 b) {
        Long3 result = new Long3();
        result.f402x = a.f402x + b.f402x;
        result.f403y = a.f403y + b.f403y;
        result.f404z = a.f404z + b.f404z;
        return result;
    }

    public void add(long value) {
        this.f402x += value;
        this.f403y += value;
        this.f404z += value;
    }

    public static Long3 add(Long3 a, long b) {
        Long3 result = new Long3();
        result.f402x = a.f402x + b;
        result.f403y = a.f403y + b;
        result.f404z = a.f404z + b;
        return result;
    }

    public void sub(Long3 a) {
        this.f402x -= a.f402x;
        this.f403y -= a.f403y;
        this.f404z -= a.f404z;
    }

    public static Long3 sub(Long3 a, Long3 b) {
        Long3 result = new Long3();
        result.f402x = a.f402x - b.f402x;
        result.f403y = a.f403y - b.f403y;
        result.f404z = a.f404z - b.f404z;
        return result;
    }

    public void sub(long value) {
        this.f402x -= value;
        this.f403y -= value;
        this.f404z -= value;
    }

    public static Long3 sub(Long3 a, long b) {
        Long3 result = new Long3();
        result.f402x = a.f402x - b;
        result.f403y = a.f403y - b;
        result.f404z = a.f404z - b;
        return result;
    }

    public void mul(Long3 a) {
        this.f402x *= a.f402x;
        this.f403y *= a.f403y;
        this.f404z *= a.f404z;
    }

    public static Long3 mul(Long3 a, Long3 b) {
        Long3 result = new Long3();
        result.f402x = a.f402x * b.f402x;
        result.f403y = a.f403y * b.f403y;
        result.f404z = a.f404z * b.f404z;
        return result;
    }

    public void mul(long value) {
        this.f402x *= value;
        this.f403y *= value;
        this.f404z *= value;
    }

    public static Long3 mul(Long3 a, long b) {
        Long3 result = new Long3();
        result.f402x = a.f402x * b;
        result.f403y = a.f403y * b;
        result.f404z = a.f404z * b;
        return result;
    }

    public void div(Long3 a) {
        this.f402x /= a.f402x;
        this.f403y /= a.f403y;
        this.f404z /= a.f404z;
    }

    public static Long3 div(Long3 a, Long3 b) {
        Long3 result = new Long3();
        result.f402x = a.f402x / b.f402x;
        result.f403y = a.f403y / b.f403y;
        result.f404z = a.f404z / b.f404z;
        return result;
    }

    public void div(long value) {
        this.f402x /= value;
        this.f403y /= value;
        this.f404z /= value;
    }

    public static Long3 div(Long3 a, long b) {
        Long3 result = new Long3();
        result.f402x = a.f402x / b;
        result.f403y = a.f403y / b;
        result.f404z = a.f404z / b;
        return result;
    }

    public void mod(Long3 a) {
        this.f402x %= a.f402x;
        this.f403y %= a.f403y;
        this.f404z %= a.f404z;
    }

    public static Long3 mod(Long3 a, Long3 b) {
        Long3 result = new Long3();
        result.f402x = a.f402x % b.f402x;
        result.f403y = a.f403y % b.f403y;
        result.f404z = a.f404z % b.f404z;
        return result;
    }

    public void mod(long value) {
        this.f402x %= value;
        this.f403y %= value;
        this.f404z %= value;
    }

    public static Long3 mod(Long3 a, long b) {
        Long3 result = new Long3();
        result.f402x = a.f402x % b;
        result.f403y = a.f403y % b;
        result.f404z = a.f404z % b;
        return result;
    }

    public long length() {
        return 3L;
    }

    public void negate() {
        this.f402x = -this.f402x;
        this.f403y = -this.f403y;
        this.f404z = -this.f404z;
    }

    public long dotProduct(Long3 a) {
        return (this.f402x * a.f402x) + (this.f403y * a.f403y) + (this.f404z * a.f404z);
    }

    public static long dotProduct(Long3 a, Long3 b) {
        return (b.f402x * a.f402x) + (b.f403y * a.f403y) + (b.f404z * a.f404z);
    }

    public void addMultiple(Long3 a, long factor) {
        this.f402x += a.f402x * factor;
        this.f403y += a.f403y * factor;
        this.f404z += a.f404z * factor;
    }

    public void set(Long3 a) {
        this.f402x = a.f402x;
        this.f403y = a.f403y;
        this.f404z = a.f404z;
    }

    public void setValues(long a, long b, long c) {
        this.f402x = a;
        this.f403y = b;
        this.f404z = c;
    }

    public long elementSum() {
        return this.f402x + this.f403y + this.f404z;
    }

    public long get(int i) {
        switch (i) {
            case 0:
                return this.f402x;
            case 1:
                return this.f403y;
            case 2:
                return this.f404z;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, long value) {
        switch (i) {
            case 0:
                this.f402x = value;
                return;
            case 1:
                this.f403y = value;
                return;
            case 2:
                this.f404z = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, long value) {
        switch (i) {
            case 0:
                this.f402x += value;
                return;
            case 1:
                this.f403y += value;
                return;
            case 2:
                this.f404z += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(long[] data, int offset) {
        data[offset] = this.f402x;
        data[offset + 1] = this.f403y;
        data[offset + 2] = this.f404z;
    }
}
