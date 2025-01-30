package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Float4 {

  /* renamed from: w */
  public float f387w;

  /* renamed from: x */
  public float f388x;

  /* renamed from: y */
  public float f389y;

  /* renamed from: z */
  public float f390z;

  public Float4() {}

  public Float4(Float4 data) {
    this.f388x = data.f388x;
    this.f389y = data.f389y;
    this.f390z = data.f390z;
    this.f387w = data.f387w;
  }

  public Float4(float x, float y, float z, float w) {
    this.f388x = x;
    this.f389y = y;
    this.f390z = z;
    this.f387w = w;
  }

  public static Float4 add(Float4 a, Float4 b) {
    Float4 res = new Float4();
    res.f388x = a.f388x + b.f388x;
    res.f389y = a.f389y + b.f389y;
    res.f390z = a.f390z + b.f390z;
    res.f387w = a.f387w + b.f387w;
    return res;
  }

  public void add(Float4 value) {
    this.f388x += value.f388x;
    this.f389y += value.f389y;
    this.f390z += value.f390z;
    this.f387w += value.f387w;
  }

  public void add(float value) {
    this.f388x += value;
    this.f389y += value;
    this.f390z += value;
    this.f387w += value;
  }

  public static Float4 add(Float4 a, float b) {
    Float4 res = new Float4();
    res.f388x = a.f388x + b;
    res.f389y = a.f389y + b;
    res.f390z = a.f390z + b;
    res.f387w = a.f387w + b;
    return res;
  }

  public void sub(Float4 value) {
    this.f388x -= value.f388x;
    this.f389y -= value.f389y;
    this.f390z -= value.f390z;
    this.f387w -= value.f387w;
  }

  public void sub(float value) {
    this.f388x -= value;
    this.f389y -= value;
    this.f390z -= value;
    this.f387w -= value;
  }

  public static Float4 sub(Float4 a, float b) {
    Float4 res = new Float4();
    res.f388x = a.f388x - b;
    res.f389y = a.f389y - b;
    res.f390z = a.f390z - b;
    res.f387w = a.f387w - b;
    return res;
  }

  public static Float4 sub(Float4 a, Float4 b) {
    Float4 res = new Float4();
    res.f388x = a.f388x - b.f388x;
    res.f389y = a.f389y - b.f389y;
    res.f390z = a.f390z - b.f390z;
    res.f387w = a.f387w - b.f387w;
    return res;
  }

  public void mul(Float4 value) {
    this.f388x *= value.f388x;
    this.f389y *= value.f389y;
    this.f390z *= value.f390z;
    this.f387w *= value.f387w;
  }

  public void mul(float value) {
    this.f388x *= value;
    this.f389y *= value;
    this.f390z *= value;
    this.f387w *= value;
  }

  public static Float4 mul(Float4 a, Float4 b) {
    Float4 res = new Float4();
    res.f388x = a.f388x * b.f388x;
    res.f389y = a.f389y * b.f389y;
    res.f390z = a.f390z * b.f390z;
    res.f387w = a.f387w * b.f387w;
    return res;
  }

  public static Float4 mul(Float4 a, float b) {
    Float4 res = new Float4();
    res.f388x = a.f388x * b;
    res.f389y = a.f389y * b;
    res.f390z = a.f390z * b;
    res.f387w = a.f387w * b;
    return res;
  }

  public void div(Float4 value) {
    this.f388x /= value.f388x;
    this.f389y /= value.f389y;
    this.f390z /= value.f390z;
    this.f387w /= value.f387w;
  }

  public void div(float value) {
    this.f388x /= value;
    this.f389y /= value;
    this.f390z /= value;
    this.f387w /= value;
  }

  public static Float4 div(Float4 a, float b) {
    Float4 res = new Float4();
    res.f388x = a.f388x / b;
    res.f389y = a.f389y / b;
    res.f390z = a.f390z / b;
    res.f387w = a.f387w / b;
    return res;
  }

  public static Float4 div(Float4 a, Float4 b) {
    Float4 res = new Float4();
    res.f388x = a.f388x / b.f388x;
    res.f389y = a.f389y / b.f389y;
    res.f390z = a.f390z / b.f390z;
    res.f387w = a.f387w / b.f387w;
    return res;
  }

  public float dotProduct(Float4 a) {
    return (this.f388x * a.f388x)
        + (this.f389y * a.f389y)
        + (this.f390z * a.f390z)
        + (this.f387w * a.f387w);
  }

  public static float dotProduct(Float4 a, Float4 b) {
    return (b.f388x * a.f388x) + (b.f389y * a.f389y) + (b.f390z * a.f390z) + (b.f387w * a.f387w);
  }

  public void addMultiple(Float4 a, float factor) {
    this.f388x += a.f388x * factor;
    this.f389y += a.f389y * factor;
    this.f390z += a.f390z * factor;
    this.f387w += a.f387w * factor;
  }

  public void set(Float4 a) {
    this.f388x = a.f388x;
    this.f389y = a.f389y;
    this.f390z = a.f390z;
    this.f387w = a.f387w;
  }

  public void negate() {
    this.f388x = -this.f388x;
    this.f389y = -this.f389y;
    this.f390z = -this.f390z;
    this.f387w = -this.f387w;
  }

  public int length() {
    return 4;
  }

  public float elementSum() {
    return this.f388x + this.f389y + this.f390z + this.f387w;
  }

  public float get(int i) {
    switch (i) {
      case 0:
        return this.f388x;
      case 1:
        return this.f389y;
      case 2:
        return this.f390z;
      case 3:
        return this.f387w;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void setAt(int i, float value) {
    switch (i) {
      case 0:
        this.f388x = value;
        return;
      case 1:
        this.f389y = value;
        return;
      case 2:
        this.f390z = value;
        return;
      case 3:
        this.f387w = value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void addAt(int i, float value) {
    switch (i) {
      case 0:
        this.f388x += value;
        return;
      case 1:
        this.f389y += value;
        return;
      case 2:
        this.f390z += value;
        return;
      case 3:
        this.f387w += value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void setValues(float x, float y, float z, float w) {
    this.f388x = x;
    this.f389y = y;
    this.f390z = z;
    this.f387w = w;
  }

  public void copyTo(float[] data, int offset) {
    data[offset] = this.f388x;
    data[offset + 1] = this.f389y;
    data[offset + 2] = this.f390z;
    data[offset + 3] = this.f387w;
  }
}
