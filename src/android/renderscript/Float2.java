package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Float2 {
    public float x;
    public float y;

    public int length() {
        return 2;
    }

    public Float2() {
    }

    public Float2(Float2 float2) {
        this.x = float2.x;
        this.y = float2.y;
    }

    public Float2(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public static Float2 add(Float2 float2, Float2 float22) {
        Float2 float23 = new Float2();
        float23.x = float2.x + float22.x;
        float23.y = float2.y + float22.y;
        return float23;
    }

    public void add(Float2 float2) {
        this.x += float2.x;
        this.y += float2.y;
    }

    public void add(float f) {
        this.x += f;
        this.y += f;
    }

    public static Float2 add(Float2 float2, float f) {
        Float2 float22 = new Float2();
        float22.x = float2.x + f;
        float22.y = float2.y + f;
        return float22;
    }

    public void sub(Float2 float2) {
        this.x -= float2.x;
        this.y -= float2.y;
    }

    public static Float2 sub(Float2 float2, Float2 float22) {
        Float2 float23 = new Float2();
        float23.x = float2.x - float22.x;
        float23.y = float2.y - float22.y;
        return float23;
    }

    public void sub(float f) {
        this.x -= f;
        this.y -= f;
    }

    public static Float2 sub(Float2 float2, float f) {
        Float2 float22 = new Float2();
        float22.x = float2.x - f;
        float22.y = float2.y - f;
        return float22;
    }

    public void mul(Float2 float2) {
        this.x *= float2.x;
        this.y *= float2.y;
    }

    public static Float2 mul(Float2 float2, Float2 float22) {
        Float2 float23 = new Float2();
        float23.x = float2.x * float22.x;
        float23.y = float2.y * float22.y;
        return float23;
    }

    public void mul(float f) {
        this.x *= f;
        this.y *= f;
    }

    public static Float2 mul(Float2 float2, float f) {
        Float2 float22 = new Float2();
        float22.x = float2.x * f;
        float22.y = float2.y * f;
        return float22;
    }

    public void div(Float2 float2) {
        this.x /= float2.x;
        this.y /= float2.y;
    }

    public static Float2 div(Float2 float2, Float2 float22) {
        Float2 float23 = new Float2();
        float23.x = float2.x / float22.x;
        float23.y = float2.y / float22.y;
        return float23;
    }

    public void div(float f) {
        this.x /= f;
        this.y /= f;
    }

    public static Float2 div(Float2 float2, float f) {
        Float2 float22 = new Float2();
        float22.x = float2.x / f;
        float22.y = float2.y / f;
        return float22;
    }

    public float dotProduct(Float2 float2) {
        return (this.x * float2.x) + (this.y * float2.y);
    }

    public static float dotProduct(Float2 float2, Float2 float22) {
        return (float22.x * float2.x) + (float22.y * float2.y);
    }

    public void addMultiple(Float2 float2, float f) {
        this.x += float2.x * f;
        this.y += float2.y * f;
    }

    public void set(Float2 float2) {
        this.x = float2.x;
        this.y = float2.y;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public float elementSum() {
        return this.x + this.y;
    }

    public float get(int i) {
        if (i == 0) {
            return this.x;
        }
        if (i == 1) {
            return this.y;
        }
        throw new IndexOutOfBoundsException("Index: i");
    }

    public void setAt(int i, float f) {
        if (i == 0) {
            this.x = f;
        } else {
            if (i == 1) {
                this.y = f;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, float f) {
        if (i == 0) {
            this.x += f;
        } else {
            if (i == 1) {
                this.y += f;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public void copyTo(float[] fArr, int i) {
        fArr[i] = this.x;
        fArr[i + 1] = this.y;
    }
}
