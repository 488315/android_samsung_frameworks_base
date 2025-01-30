package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Long4 {

    /* renamed from: w */
    public long f405w;

    /* renamed from: x */
    public long f406x;

    /* renamed from: y */
    public long f407y;

    /* renamed from: z */
    public long f408z;

    public Long4() {
    }

    public Long4(long i) {
        this.f405w = i;
        this.f408z = i;
        this.f407y = i;
        this.f406x = i;
    }

    public Long4(long x, long y, long z, long w) {
        this.f406x = x;
        this.f407y = y;
        this.f408z = z;
        this.f405w = w;
    }

    public Long4(Long4 source) {
        this.f406x = source.f406x;
        this.f407y = source.f407y;
        this.f408z = source.f408z;
        this.f405w = source.f405w;
    }

    public void add(Long4 a) {
        this.f406x += a.f406x;
        this.f407y += a.f407y;
        this.f408z += a.f408z;
        this.f405w += a.f405w;
    }

    public static Long4 add(Long4 a, Long4 b) {
        Long4 result = new Long4();
        result.f406x = a.f406x + b.f406x;
        result.f407y = a.f407y + b.f407y;
        result.f408z = a.f408z + b.f408z;
        result.f405w = a.f405w + b.f405w;
        return result;
    }

    public void add(long value) {
        this.f406x += value;
        this.f407y += value;
        this.f408z += value;
        this.f405w += value;
    }

    public static Long4 add(Long4 a, long b) {
        Long4 result = new Long4();
        result.f406x = a.f406x + b;
        result.f407y = a.f407y + b;
        result.f408z = a.f408z + b;
        result.f405w = a.f405w + b;
        return result;
    }

    public void sub(Long4 a) {
        this.f406x -= a.f406x;
        this.f407y -= a.f407y;
        this.f408z -= a.f408z;
        this.f405w -= a.f405w;
    }

    public static Long4 sub(Long4 a, Long4 b) {
        Long4 result = new Long4();
        result.f406x = a.f406x - b.f406x;
        result.f407y = a.f407y - b.f407y;
        result.f408z = a.f408z - b.f408z;
        result.f405w = a.f405w - b.f405w;
        return result;
    }

    public void sub(long value) {
        this.f406x -= value;
        this.f407y -= value;
        this.f408z -= value;
        this.f405w -= value;
    }

    public static Long4 sub(Long4 a, long b) {
        Long4 result = new Long4();
        result.f406x = a.f406x - b;
        result.f407y = a.f407y - b;
        result.f408z = a.f408z - b;
        result.f405w = a.f405w - b;
        return result;
    }

    public void mul(Long4 a) {
        this.f406x *= a.f406x;
        this.f407y *= a.f407y;
        this.f408z *= a.f408z;
        this.f405w *= a.f405w;
    }

    public static Long4 mul(Long4 a, Long4 b) {
        Long4 result = new Long4();
        result.f406x = a.f406x * b.f406x;
        result.f407y = a.f407y * b.f407y;
        result.f408z = a.f408z * b.f408z;
        result.f405w = a.f405w * b.f405w;
        return result;
    }

    public void mul(long value) {
        this.f406x *= value;
        this.f407y *= value;
        this.f408z *= value;
        this.f405w *= value;
    }

    public static Long4 mul(Long4 a, long b) {
        Long4 result = new Long4();
        result.f406x = a.f406x * b;
        result.f407y = a.f407y * b;
        result.f408z = a.f408z * b;
        result.f405w = a.f405w * b;
        return result;
    }

    public void div(Long4 a) {
        this.f406x /= a.f406x;
        this.f407y /= a.f407y;
        this.f408z /= a.f408z;
        this.f405w /= a.f405w;
    }

    public static Long4 div(Long4 a, Long4 b) {
        Long4 result = new Long4();
        result.f406x = a.f406x / b.f406x;
        result.f407y = a.f407y / b.f407y;
        result.f408z = a.f408z / b.f408z;
        result.f405w = a.f405w / b.f405w;
        return result;
    }

    public void div(long value) {
        this.f406x /= value;
        this.f407y /= value;
        this.f408z /= value;
        this.f405w /= value;
    }

    public static Long4 div(Long4 a, long b) {
        Long4 result = new Long4();
        result.f406x = a.f406x / b;
        result.f407y = a.f407y / b;
        result.f408z = a.f408z / b;
        result.f405w = a.f405w / b;
        return result;
    }

    public void mod(Long4 a) {
        this.f406x %= a.f406x;
        this.f407y %= a.f407y;
        this.f408z %= a.f408z;
        this.f405w %= a.f405w;
    }

    public static Long4 mod(Long4 a, Long4 b) {
        Long4 result = new Long4();
        result.f406x = a.f406x % b.f406x;
        result.f407y = a.f407y % b.f407y;
        result.f408z = a.f408z % b.f408z;
        result.f405w = a.f405w % b.f405w;
        return result;
    }

    public void mod(long value) {
        this.f406x %= value;
        this.f407y %= value;
        this.f408z %= value;
        this.f405w %= value;
    }

    public static Long4 mod(Long4 a, long b) {
        Long4 result = new Long4();
        result.f406x = a.f406x % b;
        result.f407y = a.f407y % b;
        result.f408z = a.f408z % b;
        result.f405w = a.f405w % b;
        return result;
    }

    public long length() {
        return 4L;
    }

    public void negate() {
        this.f406x = -this.f406x;
        this.f407y = -this.f407y;
        this.f408z = -this.f408z;
        this.f405w = -this.f405w;
    }

    public long dotProduct(Long4 a) {
        return (this.f406x * a.f406x) + (this.f407y * a.f407y) + (this.f408z * a.f408z) + (this.f405w * a.f405w);
    }

    public static long dotProduct(Long4 a, Long4 b) {
        return (b.f406x * a.f406x) + (b.f407y * a.f407y) + (b.f408z * a.f408z) + (b.f405w * a.f405w);
    }

    public void addMultiple(Long4 a, long factor) {
        this.f406x += a.f406x * factor;
        this.f407y += a.f407y * factor;
        this.f408z += a.f408z * factor;
        this.f405w += a.f405w * factor;
    }

    public void set(Long4 a) {
        this.f406x = a.f406x;
        this.f407y = a.f407y;
        this.f408z = a.f408z;
        this.f405w = a.f405w;
    }

    public void setValues(long a, long b, long c, long d) {
        this.f406x = a;
        this.f407y = b;
        this.f408z = c;
        this.f405w = d;
    }

    public long elementSum() {
        return this.f406x + this.f407y + this.f408z + this.f405w;
    }

    public long get(int i) {
        switch (i) {
            case 0:
                return this.f406x;
            case 1:
                return this.f407y;
            case 2:
                return this.f408z;
            case 3:
                return this.f405w;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, long value) {
        switch (i) {
            case 0:
                this.f406x = value;
                return;
            case 1:
                this.f407y = value;
                return;
            case 2:
                this.f408z = value;
                return;
            case 3:
                this.f405w = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, long value) {
        switch (i) {
            case 0:
                this.f406x += value;
                return;
            case 1:
                this.f407y += value;
                return;
            case 2:
                this.f408z += value;
                return;
            case 3:
                this.f405w += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(long[] data, int offset) {
        data[offset] = this.f406x;
        data[offset + 1] = this.f407y;
        data[offset + 2] = this.f408z;
        data[offset + 3] = this.f405w;
    }
}
