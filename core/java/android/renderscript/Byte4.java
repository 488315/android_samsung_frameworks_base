package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Byte4 {

    /* renamed from: w */
    public byte f369w;

    /* renamed from: x */
    public byte f370x;

    /* renamed from: y */
    public byte f371y;

    /* renamed from: z */
    public byte f372z;

    public Byte4() {
    }

    public Byte4(byte initX, byte initY, byte initZ, byte initW) {
        this.f370x = initX;
        this.f371y = initY;
        this.f372z = initZ;
        this.f369w = initW;
    }

    public Byte4(Byte4 source) {
        this.f370x = source.f370x;
        this.f371y = source.f371y;
        this.f372z = source.f372z;
        this.f369w = source.f369w;
    }

    public void add(Byte4 a) {
        this.f370x = (byte) (this.f370x + a.f370x);
        this.f371y = (byte) (this.f371y + a.f371y);
        this.f372z = (byte) (this.f372z + a.f372z);
        this.f369w = (byte) (this.f369w + a.f369w);
    }

    public static Byte4 add(Byte4 a, Byte4 b) {
        Byte4 result = new Byte4();
        result.f370x = (byte) (a.f370x + b.f370x);
        result.f371y = (byte) (a.f371y + b.f371y);
        result.f372z = (byte) (a.f372z + b.f372z);
        result.f369w = (byte) (a.f369w + b.f369w);
        return result;
    }

    public void add(byte value) {
        this.f370x = (byte) (this.f370x + value);
        this.f371y = (byte) (this.f371y + value);
        this.f372z = (byte) (this.f372z + value);
        this.f369w = (byte) (this.f369w + value);
    }

    public static Byte4 add(Byte4 a, byte b) {
        Byte4 result = new Byte4();
        result.f370x = (byte) (a.f370x + b);
        result.f371y = (byte) (a.f371y + b);
        result.f372z = (byte) (a.f372z + b);
        result.f369w = (byte) (a.f369w + b);
        return result;
    }

    public void sub(Byte4 a) {
        this.f370x = (byte) (this.f370x - a.f370x);
        this.f371y = (byte) (this.f371y - a.f371y);
        this.f372z = (byte) (this.f372z - a.f372z);
        this.f369w = (byte) (this.f369w - a.f369w);
    }

    public static Byte4 sub(Byte4 a, Byte4 b) {
        Byte4 result = new Byte4();
        result.f370x = (byte) (a.f370x - b.f370x);
        result.f371y = (byte) (a.f371y - b.f371y);
        result.f372z = (byte) (a.f372z - b.f372z);
        result.f369w = (byte) (a.f369w - b.f369w);
        return result;
    }

    public void sub(byte value) {
        this.f370x = (byte) (this.f370x - value);
        this.f371y = (byte) (this.f371y - value);
        this.f372z = (byte) (this.f372z - value);
        this.f369w = (byte) (this.f369w - value);
    }

    public static Byte4 sub(Byte4 a, byte b) {
        Byte4 result = new Byte4();
        result.f370x = (byte) (a.f370x - b);
        result.f371y = (byte) (a.f371y - b);
        result.f372z = (byte) (a.f372z - b);
        result.f369w = (byte) (a.f369w - b);
        return result;
    }

    public void mul(Byte4 a) {
        this.f370x = (byte) (this.f370x * a.f370x);
        this.f371y = (byte) (this.f371y * a.f371y);
        this.f372z = (byte) (this.f372z * a.f372z);
        this.f369w = (byte) (this.f369w * a.f369w);
    }

    public static Byte4 mul(Byte4 a, Byte4 b) {
        Byte4 result = new Byte4();
        result.f370x = (byte) (a.f370x * b.f370x);
        result.f371y = (byte) (a.f371y * b.f371y);
        result.f372z = (byte) (a.f372z * b.f372z);
        result.f369w = (byte) (a.f369w * b.f369w);
        return result;
    }

    public void mul(byte value) {
        this.f370x = (byte) (this.f370x * value);
        this.f371y = (byte) (this.f371y * value);
        this.f372z = (byte) (this.f372z * value);
        this.f369w = (byte) (this.f369w * value);
    }

    public static Byte4 mul(Byte4 a, byte b) {
        Byte4 result = new Byte4();
        result.f370x = (byte) (a.f370x * b);
        result.f371y = (byte) (a.f371y * b);
        result.f372z = (byte) (a.f372z * b);
        result.f369w = (byte) (a.f369w * b);
        return result;
    }

    public void div(Byte4 a) {
        this.f370x = (byte) (this.f370x / a.f370x);
        this.f371y = (byte) (this.f371y / a.f371y);
        this.f372z = (byte) (this.f372z / a.f372z);
        this.f369w = (byte) (this.f369w / a.f369w);
    }

    public static Byte4 div(Byte4 a, Byte4 b) {
        Byte4 result = new Byte4();
        result.f370x = (byte) (a.f370x / b.f370x);
        result.f371y = (byte) (a.f371y / b.f371y);
        result.f372z = (byte) (a.f372z / b.f372z);
        result.f369w = (byte) (a.f369w / b.f369w);
        return result;
    }

    public void div(byte value) {
        this.f370x = (byte) (this.f370x / value);
        this.f371y = (byte) (this.f371y / value);
        this.f372z = (byte) (this.f372z / value);
        this.f369w = (byte) (this.f369w / value);
    }

    public static Byte4 div(Byte4 a, byte b) {
        Byte4 result = new Byte4();
        result.f370x = (byte) (a.f370x / b);
        result.f371y = (byte) (a.f371y / b);
        result.f372z = (byte) (a.f372z / b);
        result.f369w = (byte) (a.f369w / b);
        return result;
    }

    public byte length() {
        return (byte) 4;
    }

    public void negate() {
        this.f370x = (byte) (-this.f370x);
        this.f371y = (byte) (-this.f371y);
        this.f372z = (byte) (-this.f372z);
        this.f369w = (byte) (-this.f369w);
    }

    public byte dotProduct(Byte4 a) {
        return (byte) ((this.f370x * a.f370x) + (this.f371y * a.f371y) + (this.f372z * a.f372z) + (this.f369w * a.f369w));
    }

    public static byte dotProduct(Byte4 a, Byte4 b) {
        return (byte) ((b.f370x * a.f370x) + (b.f371y * a.f371y) + (b.f372z * a.f372z) + (b.f369w * a.f369w));
    }

    public void addMultiple(Byte4 a, byte factor) {
        this.f370x = (byte) (this.f370x + (a.f370x * factor));
        this.f371y = (byte) (this.f371y + (a.f371y * factor));
        this.f372z = (byte) (this.f372z + (a.f372z * factor));
        this.f369w = (byte) (this.f369w + (a.f369w * factor));
    }

    public void set(Byte4 a) {
        this.f370x = a.f370x;
        this.f371y = a.f371y;
        this.f372z = a.f372z;
        this.f369w = a.f369w;
    }

    public void setValues(byte a, byte b, byte c, byte d) {
        this.f370x = a;
        this.f371y = b;
        this.f372z = c;
        this.f369w = d;
    }

    public byte elementSum() {
        return (byte) (this.f370x + this.f371y + this.f372z + this.f369w);
    }

    public byte get(int i) {
        switch (i) {
            case 0:
                return this.f370x;
            case 1:
                return this.f371y;
            case 2:
                return this.f372z;
            case 3:
                return this.f369w;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, byte value) {
        switch (i) {
            case 0:
                this.f370x = value;
                return;
            case 1:
                this.f371y = value;
                return;
            case 2:
                this.f372z = value;
                return;
            case 3:
                this.f369w = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, byte value) {
        switch (i) {
            case 0:
                this.f370x = (byte) (this.f370x + value);
                return;
            case 1:
                this.f371y = (byte) (this.f371y + value);
                return;
            case 2:
                this.f372z = (byte) (this.f372z + value);
                return;
            case 3:
                this.f369w = (byte) (this.f369w + value);
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(byte[] data, int offset) {
        data[offset] = this.f370x;
        data[offset + 1] = this.f371y;
        data[offset + 2] = this.f372z;
        data[offset + 3] = this.f369w;
    }
}
