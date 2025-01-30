package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Float3 {

    /* renamed from: x */
    public float f384x;

    /* renamed from: y */
    public float f385y;

    /* renamed from: z */
    public float f386z;

    public Float3() {
    }

    public Float3(Float3 data) {
        this.f384x = data.f384x;
        this.f385y = data.f385y;
        this.f386z = data.f386z;
    }

    public Float3(float x, float y, float z) {
        this.f384x = x;
        this.f385y = y;
        this.f386z = z;
    }

    public static Float3 add(Float3 a, Float3 b) {
        Float3 res = new Float3();
        res.f384x = a.f384x + b.f384x;
        res.f385y = a.f385y + b.f385y;
        res.f386z = a.f386z + b.f386z;
        return res;
    }

    public void add(Float3 value) {
        this.f384x += value.f384x;
        this.f385y += value.f385y;
        this.f386z += value.f386z;
    }

    public void add(float value) {
        this.f384x += value;
        this.f385y += value;
        this.f386z += value;
    }

    public static Float3 add(Float3 a, float b) {
        Float3 res = new Float3();
        res.f384x = a.f384x + b;
        res.f385y = a.f385y + b;
        res.f386z = a.f386z + b;
        return res;
    }

    public void sub(Float3 value) {
        this.f384x -= value.f384x;
        this.f385y -= value.f385y;
        this.f386z -= value.f386z;
    }

    public static Float3 sub(Float3 a, Float3 b) {
        Float3 res = new Float3();
        res.f384x = a.f384x - b.f384x;
        res.f385y = a.f385y - b.f385y;
        res.f386z = a.f386z - b.f386z;
        return res;
    }

    public void sub(float value) {
        this.f384x -= value;
        this.f385y -= value;
        this.f386z -= value;
    }

    public static Float3 sub(Float3 a, float b) {
        Float3 res = new Float3();
        res.f384x = a.f384x - b;
        res.f385y = a.f385y - b;
        res.f386z = a.f386z - b;
        return res;
    }

    public void mul(Float3 value) {
        this.f384x *= value.f384x;
        this.f385y *= value.f385y;
        this.f386z *= value.f386z;
    }

    public static Float3 mul(Float3 a, Float3 b) {
        Float3 res = new Float3();
        res.f384x = a.f384x * b.f384x;
        res.f385y = a.f385y * b.f385y;
        res.f386z = a.f386z * b.f386z;
        return res;
    }

    public void mul(float value) {
        this.f384x *= value;
        this.f385y *= value;
        this.f386z *= value;
    }

    public static Float3 mul(Float3 a, float b) {
        Float3 res = new Float3();
        res.f384x = a.f384x * b;
        res.f385y = a.f385y * b;
        res.f386z = a.f386z * b;
        return res;
    }

    public void div(Float3 value) {
        this.f384x /= value.f384x;
        this.f385y /= value.f385y;
        this.f386z /= value.f386z;
    }

    public static Float3 div(Float3 a, Float3 b) {
        Float3 res = new Float3();
        res.f384x = a.f384x / b.f384x;
        res.f385y = a.f385y / b.f385y;
        res.f386z = a.f386z / b.f386z;
        return res;
    }

    public void div(float value) {
        this.f384x /= value;
        this.f385y /= value;
        this.f386z /= value;
    }

    public static Float3 div(Float3 a, float b) {
        Float3 res = new Float3();
        res.f384x = a.f384x / b;
        res.f385y = a.f385y / b;
        res.f386z = a.f386z / b;
        return res;
    }

    public Float dotProduct(Float3 a) {
        return new Float((this.f384x * a.f384x) + (this.f385y * a.f385y) + (this.f386z * a.f386z));
    }

    public static Float dotProduct(Float3 a, Float3 b) {
        return new Float((b.f384x * a.f384x) + (b.f385y * a.f385y) + (b.f386z * a.f386z));
    }

    public void addMultiple(Float3 a, float factor) {
        this.f384x += a.f384x * factor;
        this.f385y += a.f385y * factor;
        this.f386z += a.f386z * factor;
    }

    public void set(Float3 a) {
        this.f384x = a.f384x;
        this.f385y = a.f385y;
        this.f386z = a.f386z;
    }

    public void negate() {
        this.f384x = -this.f384x;
        this.f385y = -this.f385y;
        this.f386z = -this.f386z;
    }

    public int length() {
        return 3;
    }

    public Float elementSum() {
        return new Float(this.f384x + this.f385y + this.f386z);
    }

    public float get(int i) {
        switch (i) {
            case 0:
                return this.f384x;
            case 1:
                return this.f385y;
            case 2:
                return this.f386z;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, float value) {
        switch (i) {
            case 0:
                this.f384x = value;
                return;
            case 1:
                this.f385y = value;
                return;
            case 2:
                this.f386z = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, float value) {
        switch (i) {
            case 0:
                this.f384x += value;
                return;
            case 1:
                this.f385y += value;
                return;
            case 2:
                this.f386z += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(float x, float y, float z) {
        this.f384x = x;
        this.f385y = y;
        this.f386z = z;
    }

    public void copyTo(float[] data, int offset) {
        data[offset] = this.f384x;
        data[offset + 1] = this.f385y;
        data[offset + 2] = this.f386z;
    }
}
