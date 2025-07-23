package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Int4 {
    public int w;
    public int x;
    public int y;
    public int z;

    public int length() {
        return 4;
    }

    public Int4() {
    }

    public Int4(int i) {
        this.w = i;
        this.z = i;
        this.y = i;
        this.x = i;
    }

    public Int4(int i, int i2, int i3, int i4) {
        this.x = i;
        this.y = i2;
        this.z = i3;
        this.w = i4;
    }

    public Int4(Int4 int4) {
        this.x = int4.x;
        this.y = int4.y;
        this.z = int4.z;
        this.w = int4.w;
    }

    public void add(Int4 int4) {
        this.x += int4.x;
        this.y += int4.y;
        this.z += int4.z;
        this.w += int4.w;
    }

    public static Int4 add(Int4 int4, Int4 int42) {
        Int4 int43 = new Int4();
        int43.x = int4.x + int42.x;
        int43.y = int4.y + int42.y;
        int43.z = int4.z + int42.z;
        int43.w = int4.w + int42.w;
        return int43;
    }

    public void add(int i) {
        this.x += i;
        this.y += i;
        this.z += i;
        this.w += i;
    }

    public static Int4 add(Int4 int4, int i) {
        Int4 int42 = new Int4();
        int42.x = int4.x + i;
        int42.y = int4.y + i;
        int42.z = int4.z + i;
        int42.w = int4.w + i;
        return int42;
    }

    public void sub(Int4 int4) {
        this.x -= int4.x;
        this.y -= int4.y;
        this.z -= int4.z;
        this.w -= int4.w;
    }

    public static Int4 sub(Int4 int4, Int4 int42) {
        Int4 int43 = new Int4();
        int43.x = int4.x - int42.x;
        int43.y = int4.y - int42.y;
        int43.z = int4.z - int42.z;
        int43.w = int4.w - int42.w;
        return int43;
    }

    public void sub(int i) {
        this.x -= i;
        this.y -= i;
        this.z -= i;
        this.w -= i;
    }

    public static Int4 sub(Int4 int4, int i) {
        Int4 int42 = new Int4();
        int42.x = int4.x - i;
        int42.y = int4.y - i;
        int42.z = int4.z - i;
        int42.w = int4.w - i;
        return int42;
    }

    public void mul(Int4 int4) {
        this.x *= int4.x;
        this.y *= int4.y;
        this.z *= int4.z;
        this.w *= int4.w;
    }

    public static Int4 mul(Int4 int4, Int4 int42) {
        Int4 int43 = new Int4();
        int43.x = int4.x * int42.x;
        int43.y = int4.y * int42.y;
        int43.z = int4.z * int42.z;
        int43.w = int4.w * int42.w;
        return int43;
    }

    public void mul(int i) {
        this.x *= i;
        this.y *= i;
        this.z *= i;
        this.w *= i;
    }

    public static Int4 mul(Int4 int4, int i) {
        Int4 int42 = new Int4();
        int42.x = int4.x * i;
        int42.y = int4.y * i;
        int42.z = int4.z * i;
        int42.w = int4.w * i;
        return int42;
    }

    public void div(Int4 int4) {
        this.x /= int4.x;
        this.y /= int4.y;
        this.z /= int4.z;
        this.w /= int4.w;
    }

    public static Int4 div(Int4 int4, Int4 int42) {
        Int4 int43 = new Int4();
        int43.x = int4.x / int42.x;
        int43.y = int4.y / int42.y;
        int43.z = int4.z / int42.z;
        int43.w = int4.w / int42.w;
        return int43;
    }

    public void div(int i) {
        this.x /= i;
        this.y /= i;
        this.z /= i;
        this.w /= i;
    }

    public static Int4 div(Int4 int4, int i) {
        Int4 int42 = new Int4();
        int42.x = int4.x / i;
        int42.y = int4.y / i;
        int42.z = int4.z / i;
        int42.w = int4.w / i;
        return int42;
    }

    public void mod(Int4 int4) {
        this.x %= int4.x;
        this.y %= int4.y;
        this.z %= int4.z;
        this.w %= int4.w;
    }

    public static Int4 mod(Int4 int4, Int4 int42) {
        Int4 int43 = new Int4();
        int43.x = int4.x % int42.x;
        int43.y = int4.y % int42.y;
        int43.z = int4.z % int42.z;
        int43.w = int4.w % int42.w;
        return int43;
    }

    public void mod(int i) {
        this.x %= i;
        this.y %= i;
        this.z %= i;
        this.w %= i;
    }

    public static Int4 mod(Int4 int4, int i) {
        Int4 int42 = new Int4();
        int42.x = int4.x % i;
        int42.y = int4.y % i;
        int42.z = int4.z % i;
        int42.w = int4.w % i;
        return int42;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public int dotProduct(Int4 int4) {
        return (this.x * int4.x) + (this.y * int4.y) + (this.z * int4.z) + (this.w * int4.w);
    }

    public static int dotProduct(Int4 int4, Int4 int42) {
        return (int42.x * int4.x) + (int42.y * int4.y) + (int42.z * int4.z) + (int42.w * int4.w);
    }

    public void addMultiple(Int4 int4, int i) {
        this.x += int4.x * i;
        this.y += int4.y * i;
        this.z += int4.z * i;
        this.w += int4.w * i;
    }

    public void set(Int4 int4) {
        this.x = int4.x;
        this.y = int4.y;
        this.z = int4.z;
        this.w = int4.w;
    }

    public void setValues(int i, int i2, int i3, int i4) {
        this.x = i;
        this.y = i2;
        this.z = i3;
        this.w = i4;
    }

    public int elementSum() {
        return this.x + this.y + this.z + this.w;
    }

    public int get(int i) {
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

    public void setAt(int i, int i2) {
        if (i == 0) {
            this.x = i2;
            return;
        }
        if (i == 1) {
            this.y = i2;
        } else if (i == 2) {
            this.z = i2;
        } else {
            if (i == 3) {
                this.w = i2;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, int i2) {
        if (i == 0) {
            this.x += i2;
            return;
        }
        if (i == 1) {
            this.y += i2;
        } else if (i == 2) {
            this.z += i2;
        } else {
            if (i == 3) {
                this.w += i2;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(int[] iArr, int i) {
        iArr[i] = this.x;
        iArr[i + 1] = this.y;
        iArr[i + 2] = this.z;
        iArr[i + 3] = this.w;
    }
}
