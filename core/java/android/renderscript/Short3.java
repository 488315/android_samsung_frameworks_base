package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Short3 {

    /* renamed from: x */
    public short f420x;

    /* renamed from: y */
    public short f421y;

    /* renamed from: z */
    public short f422z;

    public Short3() {
    }

    public Short3(short i) {
        this.f422z = i;
        this.f421y = i;
        this.f420x = i;
    }

    public Short3(short x, short y, short z) {
        this.f420x = x;
        this.f421y = y;
        this.f422z = z;
    }

    public Short3(Short3 source) {
        this.f420x = source.f420x;
        this.f421y = source.f421y;
        this.f422z = source.f422z;
    }

    public void add(Short3 a) {
        this.f420x = (short) (this.f420x + a.f420x);
        this.f421y = (short) (this.f421y + a.f421y);
        this.f422z = (short) (this.f422z + a.f422z);
    }

    public static Short3 add(Short3 a, Short3 b) {
        Short3 result = new Short3();
        result.f420x = (short) (a.f420x + b.f420x);
        result.f421y = (short) (a.f421y + b.f421y);
        result.f422z = (short) (a.f422z + b.f422z);
        return result;
    }

    public void add(short value) {
        this.f420x = (short) (this.f420x + value);
        this.f421y = (short) (this.f421y + value);
        this.f422z = (short) (this.f422z + value);
    }

    public static Short3 add(Short3 a, short b) {
        Short3 result = new Short3();
        result.f420x = (short) (a.f420x + b);
        result.f421y = (short) (a.f421y + b);
        result.f422z = (short) (a.f422z + b);
        return result;
    }

    public void sub(Short3 a) {
        this.f420x = (short) (this.f420x - a.f420x);
        this.f421y = (short) (this.f421y - a.f421y);
        this.f422z = (short) (this.f422z - a.f422z);
    }

    public static Short3 sub(Short3 a, Short3 b) {
        Short3 result = new Short3();
        result.f420x = (short) (a.f420x - b.f420x);
        result.f421y = (short) (a.f421y - b.f421y);
        result.f422z = (short) (a.f422z - b.f422z);
        return result;
    }

    public void sub(short value) {
        this.f420x = (short) (this.f420x - value);
        this.f421y = (short) (this.f421y - value);
        this.f422z = (short) (this.f422z - value);
    }

    public static Short3 sub(Short3 a, short b) {
        Short3 result = new Short3();
        result.f420x = (short) (a.f420x - b);
        result.f421y = (short) (a.f421y - b);
        result.f422z = (short) (a.f422z - b);
        return result;
    }

    public void mul(Short3 a) {
        this.f420x = (short) (this.f420x * a.f420x);
        this.f421y = (short) (this.f421y * a.f421y);
        this.f422z = (short) (this.f422z * a.f422z);
    }

    public static Short3 mul(Short3 a, Short3 b) {
        Short3 result = new Short3();
        result.f420x = (short) (a.f420x * b.f420x);
        result.f421y = (short) (a.f421y * b.f421y);
        result.f422z = (short) (a.f422z * b.f422z);
        return result;
    }

    public void mul(short value) {
        this.f420x = (short) (this.f420x * value);
        this.f421y = (short) (this.f421y * value);
        this.f422z = (short) (this.f422z * value);
    }

    public static Short3 mul(Short3 a, short b) {
        Short3 result = new Short3();
        result.f420x = (short) (a.f420x * b);
        result.f421y = (short) (a.f421y * b);
        result.f422z = (short) (a.f422z * b);
        return result;
    }

    public void div(Short3 a) {
        this.f420x = (short) (this.f420x / a.f420x);
        this.f421y = (short) (this.f421y / a.f421y);
        this.f422z = (short) (this.f422z / a.f422z);
    }

    public static Short3 div(Short3 a, Short3 b) {
        Short3 result = new Short3();
        result.f420x = (short) (a.f420x / b.f420x);
        result.f421y = (short) (a.f421y / b.f421y);
        result.f422z = (short) (a.f422z / b.f422z);
        return result;
    }

    public void div(short value) {
        this.f420x = (short) (this.f420x / value);
        this.f421y = (short) (this.f421y / value);
        this.f422z = (short) (this.f422z / value);
    }

    public static Short3 div(Short3 a, short b) {
        Short3 result = new Short3();
        result.f420x = (short) (a.f420x / b);
        result.f421y = (short) (a.f421y / b);
        result.f422z = (short) (a.f422z / b);
        return result;
    }

    public void mod(Short3 a) {
        this.f420x = (short) (this.f420x % a.f420x);
        this.f421y = (short) (this.f421y % a.f421y);
        this.f422z = (short) (this.f422z % a.f422z);
    }

    public static Short3 mod(Short3 a, Short3 b) {
        Short3 result = new Short3();
        result.f420x = (short) (a.f420x % b.f420x);
        result.f421y = (short) (a.f421y % b.f421y);
        result.f422z = (short) (a.f422z % b.f422z);
        return result;
    }

    public void mod(short value) {
        this.f420x = (short) (this.f420x % value);
        this.f421y = (short) (this.f421y % value);
        this.f422z = (short) (this.f422z % value);
    }

    public static Short3 mod(Short3 a, short b) {
        Short3 result = new Short3();
        result.f420x = (short) (a.f420x % b);
        result.f421y = (short) (a.f421y % b);
        result.f422z = (short) (a.f422z % b);
        return result;
    }

    public short length() {
        return (short) 3;
    }

    public void negate() {
        this.f420x = (short) (-this.f420x);
        this.f421y = (short) (-this.f421y);
        this.f422z = (short) (-this.f422z);
    }

    public short dotProduct(Short3 a) {
        return (short) ((this.f420x * a.f420x) + (this.f421y * a.f421y) + (this.f422z * a.f422z));
    }

    public static short dotProduct(Short3 a, Short3 b) {
        return (short) ((b.f420x * a.f420x) + (b.f421y * a.f421y) + (b.f422z * a.f422z));
    }

    public void addMultiple(Short3 a, short factor) {
        this.f420x = (short) (this.f420x + (a.f420x * factor));
        this.f421y = (short) (this.f421y + (a.f421y * factor));
        this.f422z = (short) (this.f422z + (a.f422z * factor));
    }

    public void set(Short3 a) {
        this.f420x = a.f420x;
        this.f421y = a.f421y;
        this.f422z = a.f422z;
    }

    public void setValues(short a, short b, short c) {
        this.f420x = a;
        this.f421y = b;
        this.f422z = c;
    }

    public short elementSum() {
        return (short) (this.f420x + this.f421y + this.f422z);
    }

    public short get(int i) {
        switch (i) {
            case 0:
                return this.f420x;
            case 1:
                return this.f421y;
            case 2:
                return this.f422z;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, short value) {
        switch (i) {
            case 0:
                this.f420x = value;
                return;
            case 1:
                this.f421y = value;
                return;
            case 2:
                this.f422z = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, short value) {
        switch (i) {
            case 0:
                this.f420x = (short) (this.f420x + value);
                return;
            case 1:
                this.f421y = (short) (this.f421y + value);
                return;
            case 2:
                this.f422z = (short) (this.f422z + value);
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(short[] data, int offset) {
        data[offset] = this.f420x;
        data[offset + 1] = this.f421y;
        data[offset + 2] = this.f422z;
    }
}
