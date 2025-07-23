package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Short3 {
    public short x;
    public short y;
    public short z;

    public short length() {
        return (short) 3;
    }

    public Short3() {
    }

    public Short3(short s) {
        this.z = s;
        this.y = s;
        this.x = s;
    }

    public Short3(short s, short s2, short s3) {
        this.x = s;
        this.y = s2;
        this.z = s3;
    }

    public Short3(Short3 short3) {
        this.x = short3.x;
        this.y = short3.y;
        this.z = short3.z;
    }

    public void add(Short3 short3) {
        this.x = (short) (this.x + short3.x);
        this.y = (short) (this.y + short3.y);
        this.z = (short) (this.z + short3.z);
    }

    public static Short3 add(Short3 short3, Short3 short32) {
        Short3 short33 = new Short3();
        short33.x = (short) (short3.x + short32.x);
        short33.y = (short) (short3.y + short32.y);
        short33.z = (short) (short3.z + short32.z);
        return short33;
    }

    public void add(short s) {
        this.x = (short) (this.x + s);
        this.y = (short) (this.y + s);
        this.z = (short) (this.z + s);
    }

    public static Short3 add(Short3 short3, short s) {
        Short3 short32 = new Short3();
        short32.x = (short) (short3.x + s);
        short32.y = (short) (short3.y + s);
        short32.z = (short) (short3.z + s);
        return short32;
    }

    public void sub(Short3 short3) {
        this.x = (short) (this.x - short3.x);
        this.y = (short) (this.y - short3.y);
        this.z = (short) (this.z - short3.z);
    }

    public static Short3 sub(Short3 short3, Short3 short32) {
        Short3 short33 = new Short3();
        short33.x = (short) (short3.x - short32.x);
        short33.y = (short) (short3.y - short32.y);
        short33.z = (short) (short3.z - short32.z);
        return short33;
    }

    public void sub(short s) {
        this.x = (short) (this.x - s);
        this.y = (short) (this.y - s);
        this.z = (short) (this.z - s);
    }

    public static Short3 sub(Short3 short3, short s) {
        Short3 short32 = new Short3();
        short32.x = (short) (short3.x - s);
        short32.y = (short) (short3.y - s);
        short32.z = (short) (short3.z - s);
        return short32;
    }

    public void mul(Short3 short3) {
        this.x = (short) (this.x * short3.x);
        this.y = (short) (this.y * short3.y);
        this.z = (short) (this.z * short3.z);
    }

    public static Short3 mul(Short3 short3, Short3 short32) {
        Short3 short33 = new Short3();
        short33.x = (short) (short3.x * short32.x);
        short33.y = (short) (short3.y * short32.y);
        short33.z = (short) (short3.z * short32.z);
        return short33;
    }

    public void mul(short s) {
        this.x = (short) (this.x * s);
        this.y = (short) (this.y * s);
        this.z = (short) (this.z * s);
    }

    public static Short3 mul(Short3 short3, short s) {
        Short3 short32 = new Short3();
        short32.x = (short) (short3.x * s);
        short32.y = (short) (short3.y * s);
        short32.z = (short) (short3.z * s);
        return short32;
    }

    public void div(Short3 short3) {
        this.x = (short) (this.x / short3.x);
        this.y = (short) (this.y / short3.y);
        this.z = (short) (this.z / short3.z);
    }

    public static Short3 div(Short3 short3, Short3 short32) {
        Short3 short33 = new Short3();
        short33.x = (short) (short3.x / short32.x);
        short33.y = (short) (short3.y / short32.y);
        short33.z = (short) (short3.z / short32.z);
        return short33;
    }

    public void div(short s) {
        this.x = (short) (this.x / s);
        this.y = (short) (this.y / s);
        this.z = (short) (this.z / s);
    }

    public static Short3 div(Short3 short3, short s) {
        Short3 short32 = new Short3();
        short32.x = (short) (short3.x / s);
        short32.y = (short) (short3.y / s);
        short32.z = (short) (short3.z / s);
        return short32;
    }

    public void mod(Short3 short3) {
        this.x = (short) (this.x % short3.x);
        this.y = (short) (this.y % short3.y);
        this.z = (short) (this.z % short3.z);
    }

    public static Short3 mod(Short3 short3, Short3 short32) {
        Short3 short33 = new Short3();
        short33.x = (short) (short3.x % short32.x);
        short33.y = (short) (short3.y % short32.y);
        short33.z = (short) (short3.z % short32.z);
        return short33;
    }

    public void mod(short s) {
        this.x = (short) (this.x % s);
        this.y = (short) (this.y % s);
        this.z = (short) (this.z % s);
    }

    public static Short3 mod(Short3 short3, short s) {
        Short3 short32 = new Short3();
        short32.x = (short) (short3.x % s);
        short32.y = (short) (short3.y % s);
        short32.z = (short) (short3.z % s);
        return short32;
    }

    public void negate() {
        this.x = (short) (-this.x);
        this.y = (short) (-this.y);
        this.z = (short) (-this.z);
    }

    public short dotProduct(Short3 short3) {
        return (short) ((this.x * short3.x) + (this.y * short3.y) + (this.z * short3.z));
    }

    public static short dotProduct(Short3 short3, Short3 short32) {
        return (short) ((short32.x * short3.x) + (short32.y * short3.y) + (short32.z * short3.z));
    }

    public void addMultiple(Short3 short3, short s) {
        this.x = (short) (this.x + (short3.x * s));
        this.y = (short) (this.y + (short3.y * s));
        this.z = (short) (this.z + (short3.z * s));
    }

    public void set(Short3 short3) {
        this.x = short3.x;
        this.y = short3.y;
        this.z = short3.z;
    }

    public void setValues(short s, short s2, short s3) {
        this.x = s;
        this.y = s2;
        this.z = s3;
    }

    public short elementSum() {
        return (short) (this.x + this.y + this.z);
    }

    public short get(int i) {
        if (i == 0) {
            return this.x;
        }
        if (i == 1) {
            return this.y;
        }
        if (i == 2) {
            return this.z;
        }
        throw new IndexOutOfBoundsException("Index: i");
    }

    public void setAt(int i, short s) {
        if (i == 0) {
            this.x = s;
        } else if (i == 1) {
            this.y = s;
        } else {
            if (i == 2) {
                this.z = s;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, short s) {
        if (i == 0) {
            this.x = (short) (this.x + s);
        } else if (i == 1) {
            this.y = (short) (this.y + s);
        } else {
            if (i == 2) {
                this.z = (short) (this.z + s);
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(short[] sArr, int i) {
        sArr[i] = this.x;
        sArr[i + 1] = this.y;
        sArr[i + 2] = this.z;
    }
}
