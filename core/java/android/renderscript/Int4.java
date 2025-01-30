package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Int4 {

  /* renamed from: w */
  public int f396w;

  /* renamed from: x */
  public int f397x;

  /* renamed from: y */
  public int f398y;

  /* renamed from: z */
  public int f399z;

  public Int4() {}

  public Int4(int i) {
    this.f396w = i;
    this.f399z = i;
    this.f398y = i;
    this.f397x = i;
  }

  public Int4(int x, int y, int z, int w) {
    this.f397x = x;
    this.f398y = y;
    this.f399z = z;
    this.f396w = w;
  }

  public Int4(Int4 source) {
    this.f397x = source.f397x;
    this.f398y = source.f398y;
    this.f399z = source.f399z;
    this.f396w = source.f396w;
  }

  public void add(Int4 a) {
    this.f397x += a.f397x;
    this.f398y += a.f398y;
    this.f399z += a.f399z;
    this.f396w += a.f396w;
  }

  public static Int4 add(Int4 a, Int4 b) {
    Int4 result = new Int4();
    result.f397x = a.f397x + b.f397x;
    result.f398y = a.f398y + b.f398y;
    result.f399z = a.f399z + b.f399z;
    result.f396w = a.f396w + b.f396w;
    return result;
  }

  public void add(int value) {
    this.f397x += value;
    this.f398y += value;
    this.f399z += value;
    this.f396w += value;
  }

  public static Int4 add(Int4 a, int b) {
    Int4 result = new Int4();
    result.f397x = a.f397x + b;
    result.f398y = a.f398y + b;
    result.f399z = a.f399z + b;
    result.f396w = a.f396w + b;
    return result;
  }

  public void sub(Int4 a) {
    this.f397x -= a.f397x;
    this.f398y -= a.f398y;
    this.f399z -= a.f399z;
    this.f396w -= a.f396w;
  }

  public static Int4 sub(Int4 a, Int4 b) {
    Int4 result = new Int4();
    result.f397x = a.f397x - b.f397x;
    result.f398y = a.f398y - b.f398y;
    result.f399z = a.f399z - b.f399z;
    result.f396w = a.f396w - b.f396w;
    return result;
  }

  public void sub(int value) {
    this.f397x -= value;
    this.f398y -= value;
    this.f399z -= value;
    this.f396w -= value;
  }

  public static Int4 sub(Int4 a, int b) {
    Int4 result = new Int4();
    result.f397x = a.f397x - b;
    result.f398y = a.f398y - b;
    result.f399z = a.f399z - b;
    result.f396w = a.f396w - b;
    return result;
  }

  public void mul(Int4 a) {
    this.f397x *= a.f397x;
    this.f398y *= a.f398y;
    this.f399z *= a.f399z;
    this.f396w *= a.f396w;
  }

  public static Int4 mul(Int4 a, Int4 b) {
    Int4 result = new Int4();
    result.f397x = a.f397x * b.f397x;
    result.f398y = a.f398y * b.f398y;
    result.f399z = a.f399z * b.f399z;
    result.f396w = a.f396w * b.f396w;
    return result;
  }

  public void mul(int value) {
    this.f397x *= value;
    this.f398y *= value;
    this.f399z *= value;
    this.f396w *= value;
  }

  public static Int4 mul(Int4 a, int b) {
    Int4 result = new Int4();
    result.f397x = a.f397x * b;
    result.f398y = a.f398y * b;
    result.f399z = a.f399z * b;
    result.f396w = a.f396w * b;
    return result;
  }

  public void div(Int4 a) {
    this.f397x /= a.f397x;
    this.f398y /= a.f398y;
    this.f399z /= a.f399z;
    this.f396w /= a.f396w;
  }

  public static Int4 div(Int4 a, Int4 b) {
    Int4 result = new Int4();
    result.f397x = a.f397x / b.f397x;
    result.f398y = a.f398y / b.f398y;
    result.f399z = a.f399z / b.f399z;
    result.f396w = a.f396w / b.f396w;
    return result;
  }

  public void div(int value) {
    this.f397x /= value;
    this.f398y /= value;
    this.f399z /= value;
    this.f396w /= value;
  }

  public static Int4 div(Int4 a, int b) {
    Int4 result = new Int4();
    result.f397x = a.f397x / b;
    result.f398y = a.f398y / b;
    result.f399z = a.f399z / b;
    result.f396w = a.f396w / b;
    return result;
  }

  public void mod(Int4 a) {
    this.f397x %= a.f397x;
    this.f398y %= a.f398y;
    this.f399z %= a.f399z;
    this.f396w %= a.f396w;
  }

  public static Int4 mod(Int4 a, Int4 b) {
    Int4 result = new Int4();
    result.f397x = a.f397x % b.f397x;
    result.f398y = a.f398y % b.f398y;
    result.f399z = a.f399z % b.f399z;
    result.f396w = a.f396w % b.f396w;
    return result;
  }

  public void mod(int value) {
    this.f397x %= value;
    this.f398y %= value;
    this.f399z %= value;
    this.f396w %= value;
  }

  public static Int4 mod(Int4 a, int b) {
    Int4 result = new Int4();
    result.f397x = a.f397x % b;
    result.f398y = a.f398y % b;
    result.f399z = a.f399z % b;
    result.f396w = a.f396w % b;
    return result;
  }

  public int length() {
    return 4;
  }

  public void negate() {
    this.f397x = -this.f397x;
    this.f398y = -this.f398y;
    this.f399z = -this.f399z;
    this.f396w = -this.f396w;
  }

  public int dotProduct(Int4 a) {
    return (this.f397x * a.f397x)
        + (this.f398y * a.f398y)
        + (this.f399z * a.f399z)
        + (this.f396w * a.f396w);
  }

  public static int dotProduct(Int4 a, Int4 b) {
    return (b.f397x * a.f397x) + (b.f398y * a.f398y) + (b.f399z * a.f399z) + (b.f396w * a.f396w);
  }

  public void addMultiple(Int4 a, int factor) {
    this.f397x += a.f397x * factor;
    this.f398y += a.f398y * factor;
    this.f399z += a.f399z * factor;
    this.f396w += a.f396w * factor;
  }

  public void set(Int4 a) {
    this.f397x = a.f397x;
    this.f398y = a.f398y;
    this.f399z = a.f399z;
    this.f396w = a.f396w;
  }

  public void setValues(int a, int b, int c, int d) {
    this.f397x = a;
    this.f398y = b;
    this.f399z = c;
    this.f396w = d;
  }

  public int elementSum() {
    return this.f397x + this.f398y + this.f399z + this.f396w;
  }

  public int get(int i) {
    switch (i) {
      case 0:
        return this.f397x;
      case 1:
        return this.f398y;
      case 2:
        return this.f399z;
      case 3:
        return this.f396w;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void setAt(int i, int value) {
    switch (i) {
      case 0:
        this.f397x = value;
        return;
      case 1:
        this.f398y = value;
        return;
      case 2:
        this.f399z = value;
        return;
      case 3:
        this.f396w = value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void addAt(int i, int value) {
    switch (i) {
      case 0:
        this.f397x += value;
        return;
      case 1:
        this.f398y += value;
        return;
      case 2:
        this.f399z += value;
        return;
      case 3:
        this.f396w += value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void copyTo(int[] data, int offset) {
    data[offset] = this.f397x;
    data[offset + 1] = this.f398y;
    data[offset + 2] = this.f399z;
    data[offset + 3] = this.f396w;
  }
}
