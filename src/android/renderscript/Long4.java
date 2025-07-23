package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Long4 {
    public long w;
    public long x;
    public long y;
    public long z;

    public long length() {
        return 4L;
    }

    public Long4() {
    }

    public Long4(long j) {
        this.w = j;
        this.z = j;
        this.y = j;
        this.x = j;
    }

    public Long4(long j, long j2, long j3, long j4) {
        this.x = j;
        this.y = j2;
        this.z = j3;
        this.w = j4;
    }

    public Long4(Long4 long4) {
        this.x = long4.x;
        this.y = long4.y;
        this.z = long4.z;
        this.w = long4.w;
    }

    public void add(Long4 long4) {
        this.x += long4.x;
        this.y += long4.y;
        this.z += long4.z;
        this.w += long4.w;
    }

    public static Long4 add(Long4 long4, Long4 long42) {
        Long4 long43 = new Long4();
        long43.x = long4.x + long42.x;
        long43.y = long4.y + long42.y;
        long43.z = long4.z + long42.z;
        long43.w = long4.w + long42.w;
        return long43;
    }

    public void add(long j) {
        this.x += j;
        this.y += j;
        this.z += j;
        this.w += j;
    }

    public static Long4 add(Long4 long4, long j) {
        Long4 long42 = new Long4();
        long42.x = long4.x + j;
        long42.y = long4.y + j;
        long42.z = long4.z + j;
        long42.w = long4.w + j;
        return long42;
    }

    public void sub(Long4 long4) {
        this.x -= long4.x;
        this.y -= long4.y;
        this.z -= long4.z;
        this.w -= long4.w;
    }

    public static Long4 sub(Long4 long4, Long4 long42) {
        Long4 long43 = new Long4();
        long43.x = long4.x - long42.x;
        long43.y = long4.y - long42.y;
        long43.z = long4.z - long42.z;
        long43.w = long4.w - long42.w;
        return long43;
    }

    public void sub(long j) {
        this.x -= j;
        this.y -= j;
        this.z -= j;
        this.w -= j;
    }

    public static Long4 sub(Long4 long4, long j) {
        Long4 long42 = new Long4();
        long42.x = long4.x - j;
        long42.y = long4.y - j;
        long42.z = long4.z - j;
        long42.w = long4.w - j;
        return long42;
    }

    public void mul(Long4 long4) {
        this.x *= long4.x;
        this.y *= long4.y;
        this.z *= long4.z;
        this.w *= long4.w;
    }

    public static Long4 mul(Long4 long4, Long4 long42) {
        Long4 long43 = new Long4();
        long43.x = long4.x * long42.x;
        long43.y = long4.y * long42.y;
        long43.z = long4.z * long42.z;
        long43.w = long4.w * long42.w;
        return long43;
    }

    public void mul(long j) {
        this.x *= j;
        this.y *= j;
        this.z *= j;
        this.w *= j;
    }

    public static Long4 mul(Long4 long4, long j) {
        Long4 long42 = new Long4();
        long42.x = long4.x * j;
        long42.y = long4.y * j;
        long42.z = long4.z * j;
        long42.w = long4.w * j;
        return long42;
    }

    public void div(Long4 long4) {
        this.x /= long4.x;
        this.y /= long4.y;
        this.z /= long4.z;
        this.w /= long4.w;
    }

    public static Long4 div(Long4 long4, Long4 long42) {
        Long4 long43 = new Long4();
        long43.x = long4.x / long42.x;
        long43.y = long4.y / long42.y;
        long43.z = long4.z / long42.z;
        long43.w = long4.w / long42.w;
        return long43;
    }

    public void div(long j) {
        this.x /= j;
        this.y /= j;
        this.z /= j;
        this.w /= j;
    }

    public static Long4 div(Long4 long4, long j) {
        Long4 long42 = new Long4();
        long42.x = long4.x / j;
        long42.y = long4.y / j;
        long42.z = long4.z / j;
        long42.w = long4.w / j;
        return long42;
    }

    public void mod(Long4 long4) {
        this.x %= long4.x;
        this.y %= long4.y;
        this.z %= long4.z;
        this.w %= long4.w;
    }

    public static Long4 mod(Long4 long4, Long4 long42) {
        Long4 long43 = new Long4();
        long43.x = long4.x % long42.x;
        long43.y = long4.y % long42.y;
        long43.z = long4.z % long42.z;
        long43.w = long4.w % long42.w;
        return long43;
    }

    public void mod(long j) {
        this.x %= j;
        this.y %= j;
        this.z %= j;
        this.w %= j;
    }

    public static Long4 mod(Long4 long4, long j) {
        Long4 long42 = new Long4();
        long42.x = long4.x % j;
        long42.y = long4.y % j;
        long42.z = long4.z % j;
        long42.w = long4.w % j;
        return long42;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public long dotProduct(Long4 long4) {
        return (this.x * long4.x) + (this.y * long4.y) + (this.z * long4.z) + (this.w * long4.w);
    }

    public static long dotProduct(Long4 long4, Long4 long42) {
        return (long42.x * long4.x) + (long42.y * long4.y) + (long42.z * long4.z) + (long42.w * long4.w);
    }

    public void addMultiple(Long4 long4, long j) {
        this.x += long4.x * j;
        this.y += long4.y * j;
        this.z += long4.z * j;
        this.w += long4.w * j;
    }

    public void set(Long4 long4) {
        this.x = long4.x;
        this.y = long4.y;
        this.z = long4.z;
        this.w = long4.w;
    }

    public void setValues(long j, long j2, long j3, long j4) {
        this.x = j;
        this.y = j2;
        this.z = j3;
        this.w = j4;
    }

    public long elementSum() {
        return this.x + this.y + this.z + this.w;
    }

    public long get(int i) {
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

    public void setAt(int i, long j) {
        if (i == 0) {
            this.x = j;
            return;
        }
        if (i == 1) {
            this.y = j;
        } else if (i == 2) {
            this.z = j;
        } else {
            if (i == 3) {
                this.w = j;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, long j) {
        if (i == 0) {
            this.x += j;
            return;
        }
        if (i == 1) {
            this.y += j;
        } else if (i == 2) {
            this.z += j;
        } else {
            if (i == 3) {
                this.w += j;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(long[] jArr, int i) {
        jArr[i] = this.x;
        jArr[i + 1] = this.y;
        jArr[i + 2] = this.z;
        jArr[i + 3] = this.w;
    }
}
