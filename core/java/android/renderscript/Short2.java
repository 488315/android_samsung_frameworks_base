package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Short2 {

  /* renamed from: x */
  public short f418x;

  /* renamed from: y */
  public short f419y;

  public Short2() {}

  public Short2(short i) {
    this.f419y = i;
    this.f418x = i;
  }

  public Short2(short x, short y) {
    this.f418x = x;
    this.f419y = y;
  }

  public Short2(Short2 source) {
    this.f418x = source.f418x;
    this.f419y = source.f419y;
  }

  public void add(Short2 a) {
    this.f418x = (short) (this.f418x + a.f418x);
    this.f419y = (short) (this.f419y + a.f419y);
  }

  public static Short2 add(Short2 a, Short2 b) {
    Short2 result = new Short2();
    result.f418x = (short) (a.f418x + b.f418x);
    result.f419y = (short) (a.f419y + b.f419y);
    return result;
  }

  public void add(short value) {
    this.f418x = (short) (this.f418x + value);
    this.f419y = (short) (this.f419y + value);
  }

  public static Short2 add(Short2 a, short b) {
    Short2 result = new Short2();
    result.f418x = (short) (a.f418x + b);
    result.f419y = (short) (a.f419y + b);
    return result;
  }

  public void sub(Short2 a) {
    this.f418x = (short) (this.f418x - a.f418x);
    this.f419y = (short) (this.f419y - a.f419y);
  }

  public static Short2 sub(Short2 a, Short2 b) {
    Short2 result = new Short2();
    result.f418x = (short) (a.f418x - b.f418x);
    result.f419y = (short) (a.f419y - b.f419y);
    return result;
  }

  public void sub(short value) {
    this.f418x = (short) (this.f418x - value);
    this.f419y = (short) (this.f419y - value);
  }

  public static Short2 sub(Short2 a, short b) {
    Short2 result = new Short2();
    result.f418x = (short) (a.f418x - b);
    result.f419y = (short) (a.f419y - b);
    return result;
  }

  public void mul(Short2 a) {
    this.f418x = (short) (this.f418x * a.f418x);
    this.f419y = (short) (this.f419y * a.f419y);
  }

  public static Short2 mul(Short2 a, Short2 b) {
    Short2 result = new Short2();
    result.f418x = (short) (a.f418x * b.f418x);
    result.f419y = (short) (a.f419y * b.f419y);
    return result;
  }

  public void mul(short value) {
    this.f418x = (short) (this.f418x * value);
    this.f419y = (short) (this.f419y * value);
  }

  public static Short2 mul(Short2 a, short b) {
    Short2 result = new Short2();
    result.f418x = (short) (a.f418x * b);
    result.f419y = (short) (a.f419y * b);
    return result;
  }

  public void div(Short2 a) {
    this.f418x = (short) (this.f418x / a.f418x);
    this.f419y = (short) (this.f419y / a.f419y);
  }

  public static Short2 div(Short2 a, Short2 b) {
    Short2 result = new Short2();
    result.f418x = (short) (a.f418x / b.f418x);
    result.f419y = (short) (a.f419y / b.f419y);
    return result;
  }

  public void div(short value) {
    this.f418x = (short) (this.f418x / value);
    this.f419y = (short) (this.f419y / value);
  }

  public static Short2 div(Short2 a, short b) {
    Short2 result = new Short2();
    result.f418x = (short) (a.f418x / b);
    result.f419y = (short) (a.f419y / b);
    return result;
  }

  public void mod(Short2 a) {
    this.f418x = (short) (this.f418x % a.f418x);
    this.f419y = (short) (this.f419y % a.f419y);
  }

  public static Short2 mod(Short2 a, Short2 b) {
    Short2 result = new Short2();
    result.f418x = (short) (a.f418x % b.f418x);
    result.f419y = (short) (a.f419y % b.f419y);
    return result;
  }

  public void mod(short value) {
    this.f418x = (short) (this.f418x % value);
    this.f419y = (short) (this.f419y % value);
  }

  public static Short2 mod(Short2 a, short b) {
    Short2 result = new Short2();
    result.f418x = (short) (a.f418x % b);
    result.f419y = (short) (a.f419y % b);
    return result;
  }

  public short length() {
    return (short) 2;
  }

  public void negate() {
    this.f418x = (short) (-this.f418x);
    this.f419y = (short) (-this.f419y);
  }

  public short dotProduct(Short2 a) {
    return (short) ((this.f418x * a.f418x) + (this.f419y * a.f419y));
  }

  public static short dotProduct(Short2 a, Short2 b) {
    return (short) ((b.f418x * a.f418x) + (b.f419y * a.f419y));
  }

  public void addMultiple(Short2 a, short factor) {
    this.f418x = (short) (this.f418x + (a.f418x * factor));
    this.f419y = (short) (this.f419y + (a.f419y * factor));
  }

  public void set(Short2 a) {
    this.f418x = a.f418x;
    this.f419y = a.f419y;
  }

  public void setValues(short a, short b) {
    this.f418x = a;
    this.f419y = b;
  }

  public short elementSum() {
    return (short) (this.f418x + this.f419y);
  }

  public short get(int i) {
    switch (i) {
      case 0:
        return this.f418x;
      case 1:
        return this.f419y;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void setAt(int i, short value) {
    switch (i) {
      case 0:
        this.f418x = value;
        return;
      case 1:
        this.f419y = value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void addAt(int i, short value) {
    switch (i) {
      case 0:
        this.f418x = (short) (this.f418x + value);
        return;
      case 1:
        this.f419y = (short) (this.f419y + value);
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void copyTo(short[] data, int offset) {
    data[offset] = this.f418x;
    data[offset + 1] = this.f419y;
  }
}
