package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Byte3 {

    /* renamed from: x */
    public byte f366x;

    /* renamed from: y */
    public byte f367y;

    /* renamed from: z */
    public byte f368z;

    public Byte3() {
    }

    public Byte3(byte initX, byte initY, byte initZ) {
        this.f366x = initX;
        this.f367y = initY;
        this.f368z = initZ;
    }

    public Byte3(Byte3 source) {
        this.f366x = source.f366x;
        this.f367y = source.f367y;
        this.f368z = source.f368z;
    }

    public void add(Byte3 a) {
        this.f366x = (byte) (this.f366x + a.f366x);
        this.f367y = (byte) (this.f367y + a.f367y);
        this.f368z = (byte) (this.f368z + a.f368z);
    }

    public static Byte3 add(Byte3 a, Byte3 b) {
        Byte3 result = new Byte3();
        result.f366x = (byte) (a.f366x + b.f366x);
        result.f367y = (byte) (a.f367y + b.f367y);
        result.f368z = (byte) (a.f368z + b.f368z);
        return result;
    }

    public void add(byte value) {
        this.f366x = (byte) (this.f366x + value);
        this.f367y = (byte) (this.f367y + value);
        this.f368z = (byte) (this.f368z + value);
    }

    public static Byte3 add(Byte3 a, byte b) {
        Byte3 result = new Byte3();
        result.f366x = (byte) (a.f366x + b);
        result.f367y = (byte) (a.f367y + b);
        result.f368z = (byte) (a.f368z + b);
        return result;
    }

    public void sub(Byte3 a) {
        this.f366x = (byte) (this.f366x - a.f366x);
        this.f367y = (byte) (this.f367y - a.f367y);
        this.f368z = (byte) (this.f368z - a.f368z);
    }

    public static Byte3 sub(Byte3 a, Byte3 b) {
        Byte3 result = new Byte3();
        result.f366x = (byte) (a.f366x - b.f366x);
        result.f367y = (byte) (a.f367y - b.f367y);
        result.f368z = (byte) (a.f368z - b.f368z);
        return result;
    }

    public void sub(byte value) {
        this.f366x = (byte) (this.f366x - value);
        this.f367y = (byte) (this.f367y - value);
        this.f368z = (byte) (this.f368z - value);
    }

    public static Byte3 sub(Byte3 a, byte b) {
        Byte3 result = new Byte3();
        result.f366x = (byte) (a.f366x - b);
        result.f367y = (byte) (a.f367y - b);
        result.f368z = (byte) (a.f368z - b);
        return result;
    }

    public void mul(Byte3 a) {
        this.f366x = (byte) (this.f366x * a.f366x);
        this.f367y = (byte) (this.f367y * a.f367y);
        this.f368z = (byte) (this.f368z * a.f368z);
    }

    public static Byte3 mul(Byte3 a, Byte3 b) {
        Byte3 result = new Byte3();
        result.f366x = (byte) (a.f366x * b.f366x);
        result.f367y = (byte) (a.f367y * b.f367y);
        result.f368z = (byte) (a.f368z * b.f368z);
        return result;
    }

    public void mul(byte value) {
        this.f366x = (byte) (this.f366x * value);
        this.f367y = (byte) (this.f367y * value);
        this.f368z = (byte) (this.f368z * value);
    }

    public static Byte3 mul(Byte3 a, byte b) {
        Byte3 result = new Byte3();
        result.f366x = (byte) (a.f366x * b);
        result.f367y = (byte) (a.f367y * b);
        result.f368z = (byte) (a.f368z * b);
        return result;
    }

    public void div(Byte3 a) {
        this.f366x = (byte) (this.f366x / a.f366x);
        this.f367y = (byte) (this.f367y / a.f367y);
        this.f368z = (byte) (this.f368z / a.f368z);
    }

    public static Byte3 div(Byte3 a, Byte3 b) {
        Byte3 result = new Byte3();
        result.f366x = (byte) (a.f366x / b.f366x);
        result.f367y = (byte) (a.f367y / b.f367y);
        result.f368z = (byte) (a.f368z / b.f368z);
        return result;
    }

    public void div(byte value) {
        this.f366x = (byte) (this.f366x / value);
        this.f367y = (byte) (this.f367y / value);
        this.f368z = (byte) (this.f368z / value);
    }

    public static Byte3 div(Byte3 a, byte b) {
        Byte3 result = new Byte3();
        result.f366x = (byte) (a.f366x / b);
        result.f367y = (byte) (a.f367y / b);
        result.f368z = (byte) (a.f368z / b);
        return result;
    }

    public byte length() {
        return (byte) 3;
    }

    public void negate() {
        this.f366x = (byte) (-this.f366x);
        this.f367y = (byte) (-this.f367y);
        this.f368z = (byte) (-this.f368z);
    }

    public byte dotProduct(Byte3 a) {
        return (byte) (((byte) (((byte) (this.f366x * a.f366x)) + ((byte) (this.f367y * a.f367y)))) + ((byte) (this.f368z * a.f368z)));
    }

    public static byte dotProduct(Byte3 a, Byte3 b) {
        return (byte) (((byte) (((byte) (b.f366x * a.f366x)) + ((byte) (b.f367y * a.f367y)))) + ((byte) (b.f368z * a.f368z)));
    }

    public void addMultiple(Byte3 a, byte factor) {
        this.f366x = (byte) (this.f366x + (a.f366x * factor));
        this.f367y = (byte) (this.f367y + (a.f367y * factor));
        this.f368z = (byte) (this.f368z + (a.f368z * factor));
    }

    public void set(Byte3 a) {
        this.f366x = a.f366x;
        this.f367y = a.f367y;
        this.f368z = a.f368z;
    }

    public void setValues(byte a, byte b, byte c) {
        this.f366x = a;
        this.f367y = b;
        this.f368z = c;
    }

    public byte elementSum() {
        return (byte) (this.f366x + this.f367y + this.f368z);
    }

    public byte get(int i) {
        switch (i) {
            case 0:
                return this.f366x;
            case 1:
                return this.f367y;
            case 2:
                return this.f368z;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, byte value) {
        switch (i) {
            case 0:
                this.f366x = value;
                return;
            case 1:
                this.f367y = value;
                return;
            case 2:
                this.f368z = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, byte value) {
        switch (i) {
            case 0:
                this.f366x = (byte) (this.f366x + value);
                return;
            case 1:
                this.f367y = (byte) (this.f367y + value);
                return;
            case 2:
                this.f368z = (byte) (this.f368z + value);
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(byte[] data, int offset) {
        data[offset] = this.f366x;
        data[offset + 1] = this.f367y;
        data[offset + 2] = this.f368z;
    }
}
