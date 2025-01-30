package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Double3 {

  /* renamed from: x */
  public double f375x;

  /* renamed from: y */
  public double f376y;

  /* renamed from: z */
  public double f377z;

  public Double3() {}

  public Double3(Double3 data) {
    this.f375x = data.f375x;
    this.f376y = data.f376y;
    this.f377z = data.f377z;
  }

  public Double3(double x, double y, double z) {
    this.f375x = x;
    this.f376y = y;
    this.f377z = z;
  }

  public static Double3 add(Double3 a, Double3 b) {
    Double3 res = new Double3();
    res.f375x = a.f375x + b.f375x;
    res.f376y = a.f376y + b.f376y;
    res.f377z = a.f377z + b.f377z;
    return res;
  }

  public void add(Double3 value) {
    this.f375x += value.f375x;
    this.f376y += value.f376y;
    this.f377z += value.f377z;
  }

  public void add(double value) {
    this.f375x += value;
    this.f376y += value;
    this.f377z += value;
  }

  public static Double3 add(Double3 a, double b) {
    Double3 res = new Double3();
    res.f375x = a.f375x + b;
    res.f376y = a.f376y + b;
    res.f377z = a.f377z + b;
    return res;
  }

  public void sub(Double3 value) {
    this.f375x -= value.f375x;
    this.f376y -= value.f376y;
    this.f377z -= value.f377z;
  }

  public static Double3 sub(Double3 a, Double3 b) {
    Double3 res = new Double3();
    res.f375x = a.f375x - b.f375x;
    res.f376y = a.f376y - b.f376y;
    res.f377z = a.f377z - b.f377z;
    return res;
  }

  public void sub(double value) {
    this.f375x -= value;
    this.f376y -= value;
    this.f377z -= value;
  }

  public static Double3 sub(Double3 a, double b) {
    Double3 res = new Double3();
    res.f375x = a.f375x - b;
    res.f376y = a.f376y - b;
    res.f377z = a.f377z - b;
    return res;
  }

  public void mul(Double3 value) {
    this.f375x *= value.f375x;
    this.f376y *= value.f376y;
    this.f377z *= value.f377z;
  }

  public static Double3 mul(Double3 a, Double3 b) {
    Double3 res = new Double3();
    res.f375x = a.f375x * b.f375x;
    res.f376y = a.f376y * b.f376y;
    res.f377z = a.f377z * b.f377z;
    return res;
  }

  public void mul(double value) {
    this.f375x *= value;
    this.f376y *= value;
    this.f377z *= value;
  }

  public static Double3 mul(Double3 a, double b) {
    Double3 res = new Double3();
    res.f375x = a.f375x * b;
    res.f376y = a.f376y * b;
    res.f377z = a.f377z * b;
    return res;
  }

  public void div(Double3 value) {
    this.f375x /= value.f375x;
    this.f376y /= value.f376y;
    this.f377z /= value.f377z;
  }

  public static Double3 div(Double3 a, Double3 b) {
    Double3 res = new Double3();
    res.f375x = a.f375x / b.f375x;
    res.f376y = a.f376y / b.f376y;
    res.f377z = a.f377z / b.f377z;
    return res;
  }

  public void div(double value) {
    this.f375x /= value;
    this.f376y /= value;
    this.f377z /= value;
  }

  public static Double3 div(Double3 a, double b) {
    Double3 res = new Double3();
    res.f375x = a.f375x / b;
    res.f376y = a.f376y / b;
    res.f377z = a.f377z / b;
    return res;
  }

  public double dotProduct(Double3 a) {
    return (this.f375x * a.f375x) + (this.f376y * a.f376y) + (this.f377z * a.f377z);
  }

  public static double dotProduct(Double3 a, Double3 b) {
    return (b.f375x * a.f375x) + (b.f376y * a.f376y) + (b.f377z * a.f377z);
  }

  public void addMultiple(Double3 a, double factor) {
    this.f375x += a.f375x * factor;
    this.f376y += a.f376y * factor;
    this.f377z += a.f377z * factor;
  }

  public void set(Double3 a) {
    this.f375x = a.f375x;
    this.f376y = a.f376y;
    this.f377z = a.f377z;
  }

  public void negate() {
    this.f375x = -this.f375x;
    this.f376y = -this.f376y;
    this.f377z = -this.f377z;
  }

  public int length() {
    return 3;
  }

  public double elementSum() {
    return this.f375x + this.f376y + this.f377z;
  }

  public double get(int i) {
    switch (i) {
      case 0:
        return this.f375x;
      case 1:
        return this.f376y;
      case 2:
        return this.f377z;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void setAt(int i, double value) {
    switch (i) {
      case 0:
        this.f375x = value;
        return;
      case 1:
        this.f376y = value;
        return;
      case 2:
        this.f377z = value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void addAt(int i, double value) {
    switch (i) {
      case 0:
        this.f375x += value;
        return;
      case 1:
        this.f376y += value;
        return;
      case 2:
        this.f377z += value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void setValues(double x, double y, double z) {
    this.f375x = x;
    this.f376y = y;
    this.f377z = z;
  }

  public void copyTo(double[] data, int offset) {
    data[offset] = this.f375x;
    data[offset + 1] = this.f376y;
    data[offset + 2] = this.f377z;
  }
}
