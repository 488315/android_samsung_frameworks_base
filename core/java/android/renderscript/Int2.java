package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Int2 {

  /* renamed from: x */
  public int f391x;

  /* renamed from: y */
  public int f392y;

  public Int2() {}

  public Int2(int i) {
    this.f392y = i;
    this.f391x = i;
  }

  public Int2(int x, int y) {
    this.f391x = x;
    this.f392y = y;
  }

  public Int2(Int2 source) {
    this.f391x = source.f391x;
    this.f392y = source.f392y;
  }

  public void add(Int2 a) {
    this.f391x += a.f391x;
    this.f392y += a.f392y;
  }

  public static Int2 add(Int2 a, Int2 b) {
    Int2 result = new Int2();
    result.f391x = a.f391x + b.f391x;
    result.f392y = a.f392y + b.f392y;
    return result;
  }

  public void add(int value) {
    this.f391x += value;
    this.f392y += value;
  }

  public static Int2 add(Int2 a, int b) {
    Int2 result = new Int2();
    result.f391x = a.f391x + b;
    result.f392y = a.f392y + b;
    return result;
  }

  public void sub(Int2 a) {
    this.f391x -= a.f391x;
    this.f392y -= a.f392y;
  }

  public static Int2 sub(Int2 a, Int2 b) {
    Int2 result = new Int2();
    result.f391x = a.f391x - b.f391x;
    result.f392y = a.f392y - b.f392y;
    return result;
  }

  public void sub(int value) {
    this.f391x -= value;
    this.f392y -= value;
  }

  public static Int2 sub(Int2 a, int b) {
    Int2 result = new Int2();
    result.f391x = a.f391x - b;
    result.f392y = a.f392y - b;
    return result;
  }

  public void mul(Int2 a) {
    this.f391x *= a.f391x;
    this.f392y *= a.f392y;
  }

  public static Int2 mul(Int2 a, Int2 b) {
    Int2 result = new Int2();
    result.f391x = a.f391x * b.f391x;
    result.f392y = a.f392y * b.f392y;
    return result;
  }

  public void mul(int value) {
    this.f391x *= value;
    this.f392y *= value;
  }

  public static Int2 mul(Int2 a, int b) {
    Int2 result = new Int2();
    result.f391x = a.f391x * b;
    result.f392y = a.f392y * b;
    return result;
  }

  public void div(Int2 a) {
    this.f391x /= a.f391x;
    this.f392y /= a.f392y;
  }

  public static Int2 div(Int2 a, Int2 b) {
    Int2 result = new Int2();
    result.f391x = a.f391x / b.f391x;
    result.f392y = a.f392y / b.f392y;
    return result;
  }

  public void div(int value) {
    this.f391x /= value;
    this.f392y /= value;
  }

  public static Int2 div(Int2 a, int b) {
    Int2 result = new Int2();
    result.f391x = a.f391x / b;
    result.f392y = a.f392y / b;
    return result;
  }

  public void mod(Int2 a) {
    this.f391x %= a.f391x;
    this.f392y %= a.f392y;
  }

  public static Int2 mod(Int2 a, Int2 b) {
    Int2 result = new Int2();
    result.f391x = a.f391x % b.f391x;
    result.f392y = a.f392y % b.f392y;
    return result;
  }

  public void mod(int value) {
    this.f391x %= value;
    this.f392y %= value;
  }

  public static Int2 mod(Int2 a, int b) {
    Int2 result = new Int2();
    result.f391x = a.f391x % b;
    result.f392y = a.f392y % b;
    return result;
  }

  public int length() {
    return 2;
  }

  public void negate() {
    this.f391x = -this.f391x;
    this.f392y = -this.f392y;
  }

  public int dotProduct(Int2 a) {
    return (this.f391x * a.f391x) + (this.f392y * a.f392y);
  }

  public static int dotProduct(Int2 a, Int2 b) {
    return (b.f391x * a.f391x) + (b.f392y * a.f392y);
  }

  public void addMultiple(Int2 a, int factor) {
    this.f391x += a.f391x * factor;
    this.f392y += a.f392y * factor;
  }

  public void set(Int2 a) {
    this.f391x = a.f391x;
    this.f392y = a.f392y;
  }

  public void setValues(int a, int b) {
    this.f391x = a;
    this.f392y = b;
  }

  public int elementSum() {
    return this.f391x + this.f392y;
  }

  public int get(int i) {
    switch (i) {
      case 0:
        return this.f391x;
      case 1:
        return this.f392y;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void setAt(int i, int value) {
    switch (i) {
      case 0:
        this.f391x = value;
        return;
      case 1:
        this.f392y = value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void addAt(int i, int value) {
    switch (i) {
      case 0:
        this.f391x += value;
        return;
      case 1:
        this.f392y += value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void copyTo(int[] data, int offset) {
    data[offset] = this.f391x;
    data[offset + 1] = this.f392y;
  }
}
