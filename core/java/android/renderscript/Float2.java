package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Float2 {

    /* renamed from: x */
    public float f382x;

    /* renamed from: y */
    public float f383y;

    public Float2() {
    }

    public Float2(Float2 data) {
        this.f382x = data.f382x;
        this.f383y = data.f383y;
    }

    public Float2(float x, float y) {
        this.f382x = x;
        this.f383y = y;
    }

    public static Float2 add(Float2 a, Float2 b) {
        Float2 res = new Float2();
        res.f382x = a.f382x + b.f382x;
        res.f383y = a.f383y + b.f383y;
        return res;
    }

    public void add(Float2 value) {
        this.f382x += value.f382x;
        this.f383y += value.f383y;
    }

    public void add(float value) {
        this.f382x += value;
        this.f383y += value;
    }

    public static Float2 add(Float2 a, float b) {
        Float2 res = new Float2();
        res.f382x = a.f382x + b;
        res.f383y = a.f383y + b;
        return res;
    }

    public void sub(Float2 value) {
        this.f382x -= value.f382x;
        this.f383y -= value.f383y;
    }

    public static Float2 sub(Float2 a, Float2 b) {
        Float2 res = new Float2();
        res.f382x = a.f382x - b.f382x;
        res.f383y = a.f383y - b.f383y;
        return res;
    }

    public void sub(float value) {
        this.f382x -= value;
        this.f383y -= value;
    }

    public static Float2 sub(Float2 a, float b) {
        Float2 res = new Float2();
        res.f382x = a.f382x - b;
        res.f383y = a.f383y - b;
        return res;
    }

    public void mul(Float2 value) {
        this.f382x *= value.f382x;
        this.f383y *= value.f383y;
    }

    public static Float2 mul(Float2 a, Float2 b) {
        Float2 res = new Float2();
        res.f382x = a.f382x * b.f382x;
        res.f383y = a.f383y * b.f383y;
        return res;
    }

    public void mul(float value) {
        this.f382x *= value;
        this.f383y *= value;
    }

    public static Float2 mul(Float2 a, float b) {
        Float2 res = new Float2();
        res.f382x = a.f382x * b;
        res.f383y = a.f383y * b;
        return res;
    }

    public void div(Float2 value) {
        this.f382x /= value.f382x;
        this.f383y /= value.f383y;
    }

    public static Float2 div(Float2 a, Float2 b) {
        Float2 res = new Float2();
        res.f382x = a.f382x / b.f382x;
        res.f383y = a.f383y / b.f383y;
        return res;
    }

    public void div(float value) {
        this.f382x /= value;
        this.f383y /= value;
    }

    public static Float2 div(Float2 a, float b) {
        Float2 res = new Float2();
        res.f382x = a.f382x / b;
        res.f383y = a.f383y / b;
        return res;
    }

    public float dotProduct(Float2 a) {
        return (this.f382x * a.f382x) + (this.f383y * a.f383y);
    }

    public static float dotProduct(Float2 a, Float2 b) {
        return (b.f382x * a.f382x) + (b.f383y * a.f383y);
    }

    public void addMultiple(Float2 a, float factor) {
        this.f382x += a.f382x * factor;
        this.f383y += a.f383y * factor;
    }

    public void set(Float2 a) {
        this.f382x = a.f382x;
        this.f383y = a.f383y;
    }

    public void negate() {
        this.f382x = -this.f382x;
        this.f383y = -this.f383y;
    }

    public int length() {
        return 2;
    }

    public float elementSum() {
        return this.f382x + this.f383y;
    }

    public float get(int i) {
        switch (i) {
            case 0:
                return this.f382x;
            case 1:
                return this.f383y;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, float value) {
        switch (i) {
            case 0:
                this.f382x = value;
                return;
            case 1:
                this.f383y = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, float value) {
        switch (i) {
            case 0:
                this.f382x += value;
                return;
            case 1:
                this.f383y += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(float x, float y) {
        this.f382x = x;
        this.f383y = y;
    }

    public void copyTo(float[] data, int offset) {
        data[offset] = this.f382x;
        data[offset + 1] = this.f383y;
    }
}
