package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Short4 {
    public short w;
    public short x;
    public short y;
    public short z;

    public short length() {
        return (short) 4;
    }

    public Short4() {
    }

    public Short4(short s) {
        this.w = s;
        this.z = s;
        this.y = s;
        this.x = s;
    }

    public Short4(short s, short s2, short s3, short s4) {
        this.x = s;
        this.y = s2;
        this.z = s3;
        this.w = s4;
    }

    public Short4(Short4 short4) {
        this.x = short4.x;
        this.y = short4.y;
        this.z = short4.z;
        this.w = short4.w;
    }

    public void add(Short4 short4) {
        this.x = (short) (this.x + short4.x);
        this.y = (short) (this.y + short4.y);
        this.z = (short) (this.z + short4.z);
        this.w = (short) (this.w + short4.w);
    }

    public static Short4 add(Short4 short4, Short4 short42) {
        Short4 short43 = new Short4();
        short43.x = (short) (short4.x + short42.x);
        short43.y = (short) (short4.y + short42.y);
        short43.z = (short) (short4.z + short42.z);
        short43.w = (short) (short4.w + short42.w);
        return short43;
    }

    public void add(short s) {
        this.x = (short) (this.x + s);
        this.y = (short) (this.y + s);
        this.z = (short) (this.z + s);
        this.w = (short) (this.w + s);
    }

    public static Short4 add(Short4 short4, short s) {
        Short4 short42 = new Short4();
        short42.x = (short) (short4.x + s);
        short42.y = (short) (short4.y + s);
        short42.z = (short) (short4.z + s);
        short42.w = (short) (short4.w + s);
        return short42;
    }

    public void sub(Short4 short4) {
        this.x = (short) (this.x - short4.x);
        this.y = (short) (this.y - short4.y);
        this.z = (short) (this.z - short4.z);
        this.w = (short) (this.w - short4.w);
    }

    public static Short4 sub(Short4 short4, Short4 short42) {
        Short4 short43 = new Short4();
        short43.x = (short) (short4.x - short42.x);
        short43.y = (short) (short4.y - short42.y);
        short43.z = (short) (short4.z - short42.z);
        short43.w = (short) (short4.w - short42.w);
        return short43;
    }

    public void sub(short s) {
        this.x = (short) (this.x - s);
        this.y = (short) (this.y - s);
        this.z = (short) (this.z - s);
        this.w = (short) (this.w - s);
    }

    public static Short4 sub(Short4 short4, short s) {
        Short4 short42 = new Short4();
        short42.x = (short) (short4.x - s);
        short42.y = (short) (short4.y - s);
        short42.z = (short) (short4.z - s);
        short42.w = (short) (short4.w - s);
        return short42;
    }

    public void mul(Short4 short4) {
        this.x = (short) (this.x * short4.x);
        this.y = (short) (this.y * short4.y);
        this.z = (short) (this.z * short4.z);
        this.w = (short) (this.w * short4.w);
    }

    public static Short4 mul(Short4 short4, Short4 short42) {
        Short4 short43 = new Short4();
        short43.x = (short) (short4.x * short42.x);
        short43.y = (short) (short4.y * short42.y);
        short43.z = (short) (short4.z * short42.z);
        short43.w = (short) (short4.w * short42.w);
        return short43;
    }

    public void mul(short s) {
        this.x = (short) (this.x * s);
        this.y = (short) (this.y * s);
        this.z = (short) (this.z * s);
        this.w = (short) (this.w * s);
    }

    public static Short4 mul(Short4 short4, short s) {
        Short4 short42 = new Short4();
        short42.x = (short) (short4.x * s);
        short42.y = (short) (short4.y * s);
        short42.z = (short) (short4.z * s);
        short42.w = (short) (short4.w * s);
        return short42;
    }

    public void div(Short4 short4) {
        this.x = (short) (this.x / short4.x);
        this.y = (short) (this.y / short4.y);
        this.z = (short) (this.z / short4.z);
        this.w = (short) (this.w / short4.w);
    }

    public static Short4 div(Short4 short4, Short4 short42) {
        Short4 short43 = new Short4();
        short43.x = (short) (short4.x / short42.x);
        short43.y = (short) (short4.y / short42.y);
        short43.z = (short) (short4.z / short42.z);
        short43.w = (short) (short4.w / short42.w);
        return short43;
    }

    public void div(short s) {
        this.x = (short) (this.x / s);
        this.y = (short) (this.y / s);
        this.z = (short) (this.z / s);
        this.w = (short) (this.w / s);
    }

    public static Short4 div(Short4 short4, short s) {
        Short4 short42 = new Short4();
        short42.x = (short) (short4.x / s);
        short42.y = (short) (short4.y / s);
        short42.z = (short) (short4.z / s);
        short42.w = (short) (short4.w / s);
        return short42;
    }

    public void mod(Short4 short4) {
        this.x = (short) (this.x % short4.x);
        this.y = (short) (this.y % short4.y);
        this.z = (short) (this.z % short4.z);
        this.w = (short) (this.w % short4.w);
    }

    public static Short4 mod(Short4 short4, Short4 short42) {
        Short4 short43 = new Short4();
        short43.x = (short) (short4.x % short42.x);
        short43.y = (short) (short4.y % short42.y);
        short43.z = (short) (short4.z % short42.z);
        short43.w = (short) (short4.w % short42.w);
        return short43;
    }

    public void mod(short s) {
        this.x = (short) (this.x % s);
        this.y = (short) (this.y % s);
        this.z = (short) (this.z % s);
        this.w = (short) (this.w % s);
    }

    public static Short4 mod(Short4 short4, short s) {
        Short4 short42 = new Short4();
        short42.x = (short) (short4.x % s);
        short42.y = (short) (short4.y % s);
        short42.z = (short) (short4.z % s);
        short42.w = (short) (short4.w % s);
        return short42;
    }

    public void negate() {
        this.x = (short) (-this.x);
        this.y = (short) (-this.y);
        this.z = (short) (-this.z);
        this.w = (short) (-this.w);
    }

    public short dotProduct(Short4 short4) {
        return (short) ((this.x * short4.x) + (this.y * short4.y) + (this.z * short4.z) + (this.w * short4.w));
    }

    public static short dotProduct(Short4 short4, Short4 short42) {
        return (short) ((short42.x * short4.x) + (short42.y * short4.y) + (short42.z * short4.z) + (short42.w * short4.w));
    }

    public void addMultiple(Short4 short4, short s) {
        this.x = (short) (this.x + (short4.x * s));
        this.y = (short) (this.y + (short4.y * s));
        this.z = (short) (this.z + (short4.z * s));
        this.w = (short) (this.w + (short4.w * s));
    }

    public void set(Short4 short4) {
        this.x = short4.x;
        this.y = short4.y;
        this.z = short4.z;
        this.w = short4.w;
    }

    public void setValues(short s, short s2, short s3, short s4) {
        this.x = s;
        this.y = s2;
        this.z = s3;
        this.w = s4;
    }

    public short elementSum() {
        return (short) (this.x + this.y + this.z + this.w);
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
        if (i == 3) {
            return this.w;
        }
        throw new IndexOutOfBoundsException("Index: i");
    }

    public void setAt(int i, short s) {
        if (i == 0) {
            this.x = s;
            return;
        }
        if (i == 1) {
            this.y = s;
        } else if (i == 2) {
            this.z = s;
        } else {
            if (i == 3) {
                this.w = s;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, short s) {
        if (i == 0) {
            this.x = (short) (this.x + s);
            return;
        }
        if (i == 1) {
            this.y = (short) (this.y + s);
        } else if (i == 2) {
            this.z = (short) (this.z + s);
        } else {
            if (i == 3) {
                this.w = (short) (this.w + s);
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(short[] sArr, int i) {
        sArr[i] = this.x;
        sArr[i + 1] = this.y;
        sArr[i + 2] = this.z;
        sArr[i + 3] = this.w;
    }
}
