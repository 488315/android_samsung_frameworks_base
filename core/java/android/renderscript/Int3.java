package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Int3 {

  /* renamed from: x */
  public int f393x;

  /* renamed from: y */
  public int f394y;

  /* renamed from: z */
  public int f395z;

  public Int3() {}

  public Int3(int i) {
    this.f395z = i;
    this.f394y = i;
    this.f393x = i;
  }

  public Int3(int x, int y, int z) {
    this.f393x = x;
    this.f394y = y;
    this.f395z = z;
  }

  public Int3(Int3 source) {
    this.f393x = source.f393x;
    this.f394y = source.f394y;
    this.f395z = source.f395z;
  }

  public void add(Int3 a) {
    this.f393x += a.f393x;
    this.f394y += a.f394y;
    this.f395z += a.f395z;
  }

  public static Int3 add(Int3 a, Int3 b) {
    Int3 result = new Int3();
    result.f393x = a.f393x + b.f393x;
    result.f394y = a.f394y + b.f394y;
    result.f395z = a.f395z + b.f395z;
    return result;
  }

  public void add(int value) {
    this.f393x += value;
    this.f394y += value;
    this.f395z += value;
  }

  public static Int3 add(Int3 a, int b) {
    Int3 result = new Int3();
    result.f393x = a.f393x + b;
    result.f394y = a.f394y + b;
    result.f395z = a.f395z + b;
    return result;
  }

  public void sub(Int3 a) {
    this.f393x -= a.f393x;
    this.f394y -= a.f394y;
    this.f395z -= a.f395z;
  }

  public static Int3 sub(Int3 a, Int3 b) {
    Int3 result = new Int3();
    result.f393x = a.f393x - b.f393x;
    result.f394y = a.f394y - b.f394y;
    result.f395z = a.f395z - b.f395z;
    return result;
  }

  public void sub(int value) {
    this.f393x -= value;
    this.f394y -= value;
    this.f395z -= value;
  }

  public static Int3 sub(Int3 a, int b) {
    Int3 result = new Int3();
    result.f393x = a.f393x - b;
    result.f394y = a.f394y - b;
    result.f395z = a.f395z - b;
    return result;
  }

  public void mul(Int3 a) {
    this.f393x *= a.f393x;
    this.f394y *= a.f394y;
    this.f395z *= a.f395z;
  }

  public static Int3 mul(Int3 a, Int3 b) {
    Int3 result = new Int3();
    result.f393x = a.f393x * b.f393x;
    result.f394y = a.f394y * b.f394y;
    result.f395z = a.f395z * b.f395z;
    return result;
  }

  public void mul(int value) {
    this.f393x *= value;
    this.f394y *= value;
    this.f395z *= value;
  }

  public static Int3 mul(Int3 a, int b) {
    Int3 result = new Int3();
    result.f393x = a.f393x * b;
    result.f394y = a.f394y * b;
    result.f395z = a.f395z * b;
    return result;
  }

  public void div(Int3 a) {
    this.f393x /= a.f393x;
    this.f394y /= a.f394y;
    this.f395z /= a.f395z;
  }

  public static Int3 div(Int3 a, Int3 b) {
    Int3 result = new Int3();
    result.f393x = a.f393x / b.f393x;
    result.f394y = a.f394y / b.f394y;
    result.f395z = a.f395z / b.f395z;
    return result;
  }

  public void div(int value) {
    this.f393x /= value;
    this.f394y /= value;
    this.f395z /= value;
  }

  public static Int3 div(Int3 a, int b) {
    Int3 result = new Int3();
    result.f393x = a.f393x / b;
    result.f394y = a.f394y / b;
    result.f395z = a.f395z / b;
    return result;
  }

  public void mod(Int3 a) {
    this.f393x %= a.f393x;
    this.f394y %= a.f394y;
    this.f395z %= a.f395z;
  }

  public static Int3 mod(Int3 a, Int3 b) {
    Int3 result = new Int3();
    result.f393x = a.f393x % b.f393x;
    result.f394y = a.f394y % b.f394y;
    result.f395z = a.f395z % b.f395z;
    return result;
  }

  public void mod(int value) {
    this.f393x %= value;
    this.f394y %= value;
    this.f395z %= value;
  }

  public static Int3 mod(Int3 a, int b) {
    Int3 result = new Int3();
    result.f393x = a.f393x % b;
    result.f394y = a.f394y % b;
    result.f395z = a.f395z % b;
    return result;
  }

  public int length() {
    return 3;
  }

  public void negate() {
    this.f393x = -this.f393x;
    this.f394y = -this.f394y;
    this.f395z = -this.f395z;
  }

  public int dotProduct(Int3 a) {
    return (this.f393x * a.f393x) + (this.f394y * a.f394y) + (this.f395z * a.f395z);
  }

  public static int dotProduct(Int3 a, Int3 b) {
    return (b.f393x * a.f393x) + (b.f394y * a.f394y) + (b.f395z * a.f395z);
  }

  public void addMultiple(Int3 a, int factor) {
    this.f393x += a.f393x * factor;
    this.f394y += a.f394y * factor;
    this.f395z += a.f395z * factor;
  }

  public void set(Int3 a) {
    this.f393x = a.f393x;
    this.f394y = a.f394y;
    this.f395z = a.f395z;
  }

  public void setValues(int a, int b, int c) {
    this.f393x = a;
    this.f394y = b;
    this.f395z = c;
  }

  public int elementSum() {
    return this.f393x + this.f394y + this.f395z;
  }

  public int get(int i) {
    switch (i) {
      case 0:
        return this.f393x;
      case 1:
        return this.f394y;
      case 2:
        return this.f395z;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void setAt(int i, int value) {
    switch (i) {
      case 0:
        this.f393x = value;
        return;
      case 1:
        this.f394y = value;
        return;
      case 2:
        this.f395z = value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void addAt(int i, int value) {
    switch (i) {
      case 0:
        this.f393x += value;
        return;
      case 1:
        this.f394y += value;
        return;
      case 2:
        this.f395z += value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void copyTo(int[] data, int offset) {
    data[offset] = this.f393x;
    data[offset + 1] = this.f394y;
    data[offset + 2] = this.f395z;
  }
}
