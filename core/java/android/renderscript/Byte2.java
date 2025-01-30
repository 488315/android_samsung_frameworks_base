package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Byte2 {

  /* renamed from: x */
  public byte f364x;

  /* renamed from: y */
  public byte f365y;

  public Byte2() {}

  public Byte2(byte initX, byte initY) {
    this.f364x = initX;
    this.f365y = initY;
  }

  public Byte2(Byte2 source) {
    this.f364x = source.f364x;
    this.f365y = source.f365y;
  }

  public void add(Byte2 a) {
    this.f364x = (byte) (this.f364x + a.f364x);
    this.f365y = (byte) (this.f365y + a.f365y);
  }

  public static Byte2 add(Byte2 a, Byte2 b) {
    Byte2 result = new Byte2();
    result.f364x = (byte) (a.f364x + b.f364x);
    result.f365y = (byte) (a.f365y + b.f365y);
    return result;
  }

  public void add(byte value) {
    this.f364x = (byte) (this.f364x + value);
    this.f365y = (byte) (this.f365y + value);
  }

  public static Byte2 add(Byte2 a, byte b) {
    Byte2 result = new Byte2();
    result.f364x = (byte) (a.f364x + b);
    result.f365y = (byte) (a.f365y + b);
    return result;
  }

  public void sub(Byte2 a) {
    this.f364x = (byte) (this.f364x - a.f364x);
    this.f365y = (byte) (this.f365y - a.f365y);
  }

  public static Byte2 sub(Byte2 a, Byte2 b) {
    Byte2 result = new Byte2();
    result.f364x = (byte) (a.f364x - b.f364x);
    result.f365y = (byte) (a.f365y - b.f365y);
    return result;
  }

  public void sub(byte value) {
    this.f364x = (byte) (this.f364x - value);
    this.f365y = (byte) (this.f365y - value);
  }

  public static Byte2 sub(Byte2 a, byte b) {
    Byte2 result = new Byte2();
    result.f364x = (byte) (a.f364x - b);
    result.f365y = (byte) (a.f365y - b);
    return result;
  }

  public void mul(Byte2 a) {
    this.f364x = (byte) (this.f364x * a.f364x);
    this.f365y = (byte) (this.f365y * a.f365y);
  }

  public static Byte2 mul(Byte2 a, Byte2 b) {
    Byte2 result = new Byte2();
    result.f364x = (byte) (a.f364x * b.f364x);
    result.f365y = (byte) (a.f365y * b.f365y);
    return result;
  }

  public void mul(byte value) {
    this.f364x = (byte) (this.f364x * value);
    this.f365y = (byte) (this.f365y * value);
  }

  public static Byte2 mul(Byte2 a, byte b) {
    Byte2 result = new Byte2();
    result.f364x = (byte) (a.f364x * b);
    result.f365y = (byte) (a.f365y * b);
    return result;
  }

  public void div(Byte2 a) {
    this.f364x = (byte) (this.f364x / a.f364x);
    this.f365y = (byte) (this.f365y / a.f365y);
  }

  public static Byte2 div(Byte2 a, Byte2 b) {
    Byte2 result = new Byte2();
    result.f364x = (byte) (a.f364x / b.f364x);
    result.f365y = (byte) (a.f365y / b.f365y);
    return result;
  }

  public void div(byte value) {
    this.f364x = (byte) (this.f364x / value);
    this.f365y = (byte) (this.f365y / value);
  }

  public static Byte2 div(Byte2 a, byte b) {
    Byte2 result = new Byte2();
    result.f364x = (byte) (a.f364x / b);
    result.f365y = (byte) (a.f365y / b);
    return result;
  }

  public byte length() {
    return (byte) 2;
  }

  public void negate() {
    this.f364x = (byte) (-this.f364x);
    this.f365y = (byte) (-this.f365y);
  }

  public byte dotProduct(Byte2 a) {
    return (byte) ((this.f364x * a.f364x) + (this.f365y * a.f365y));
  }

  public static byte dotProduct(Byte2 a, Byte2 b) {
    return (byte) ((b.f364x * a.f364x) + (b.f365y * a.f365y));
  }

  public void addMultiple(Byte2 a, byte factor) {
    this.f364x = (byte) (this.f364x + (a.f364x * factor));
    this.f365y = (byte) (this.f365y + (a.f365y * factor));
  }

  public void set(Byte2 a) {
    this.f364x = a.f364x;
    this.f365y = a.f365y;
  }

  public void setValues(byte a, byte b) {
    this.f364x = a;
    this.f365y = b;
  }

  public byte elementSum() {
    return (byte) (this.f364x + this.f365y);
  }

  public byte get(int i) {
    switch (i) {
      case 0:
        return this.f364x;
      case 1:
        return this.f365y;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void setAt(int i, byte value) {
    switch (i) {
      case 0:
        this.f364x = value;
        return;
      case 1:
        this.f365y = value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void addAt(int i, byte value) {
    switch (i) {
      case 0:
        this.f364x = (byte) (this.f364x + value);
        return;
      case 1:
        this.f365y = (byte) (this.f365y + value);
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void copyTo(byte[] data, int offset) {
    data[offset] = this.f364x;
    data[offset + 1] = this.f365y;
  }
}
