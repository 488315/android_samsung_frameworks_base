package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Short4 {

  /* renamed from: w */
  public short f423w;

  /* renamed from: x */
  public short f424x;

  /* renamed from: y */
  public short f425y;

  /* renamed from: z */
  public short f426z;

  public Short4() {}

  public Short4(short i) {
    this.f423w = i;
    this.f426z = i;
    this.f425y = i;
    this.f424x = i;
  }

  public Short4(short x, short y, short z, short w) {
    this.f424x = x;
    this.f425y = y;
    this.f426z = z;
    this.f423w = w;
  }

  public Short4(Short4 source) {
    this.f424x = source.f424x;
    this.f425y = source.f425y;
    this.f426z = source.f426z;
    this.f423w = source.f423w;
  }

  public void add(Short4 a) {
    this.f424x = (short) (this.f424x + a.f424x);
    this.f425y = (short) (this.f425y + a.f425y);
    this.f426z = (short) (this.f426z + a.f426z);
    this.f423w = (short) (this.f423w + a.f423w);
  }

  public static Short4 add(Short4 a, Short4 b) {
    Short4 result = new Short4();
    result.f424x = (short) (a.f424x + b.f424x);
    result.f425y = (short) (a.f425y + b.f425y);
    result.f426z = (short) (a.f426z + b.f426z);
    result.f423w = (short) (a.f423w + b.f423w);
    return result;
  }

  public void add(short value) {
    this.f424x = (short) (this.f424x + value);
    this.f425y = (short) (this.f425y + value);
    this.f426z = (short) (this.f426z + value);
    this.f423w = (short) (this.f423w + value);
  }

  public static Short4 add(Short4 a, short b) {
    Short4 result = new Short4();
    result.f424x = (short) (a.f424x + b);
    result.f425y = (short) (a.f425y + b);
    result.f426z = (short) (a.f426z + b);
    result.f423w = (short) (a.f423w + b);
    return result;
  }

  public void sub(Short4 a) {
    this.f424x = (short) (this.f424x - a.f424x);
    this.f425y = (short) (this.f425y - a.f425y);
    this.f426z = (short) (this.f426z - a.f426z);
    this.f423w = (short) (this.f423w - a.f423w);
  }

  public static Short4 sub(Short4 a, Short4 b) {
    Short4 result = new Short4();
    result.f424x = (short) (a.f424x - b.f424x);
    result.f425y = (short) (a.f425y - b.f425y);
    result.f426z = (short) (a.f426z - b.f426z);
    result.f423w = (short) (a.f423w - b.f423w);
    return result;
  }

  public void sub(short value) {
    this.f424x = (short) (this.f424x - value);
    this.f425y = (short) (this.f425y - value);
    this.f426z = (short) (this.f426z - value);
    this.f423w = (short) (this.f423w - value);
  }

  public static Short4 sub(Short4 a, short b) {
    Short4 result = new Short4();
    result.f424x = (short) (a.f424x - b);
    result.f425y = (short) (a.f425y - b);
    result.f426z = (short) (a.f426z - b);
    result.f423w = (short) (a.f423w - b);
    return result;
  }

  public void mul(Short4 a) {
    this.f424x = (short) (this.f424x * a.f424x);
    this.f425y = (short) (this.f425y * a.f425y);
    this.f426z = (short) (this.f426z * a.f426z);
    this.f423w = (short) (this.f423w * a.f423w);
  }

  public static Short4 mul(Short4 a, Short4 b) {
    Short4 result = new Short4();
    result.f424x = (short) (a.f424x * b.f424x);
    result.f425y = (short) (a.f425y * b.f425y);
    result.f426z = (short) (a.f426z * b.f426z);
    result.f423w = (short) (a.f423w * b.f423w);
    return result;
  }

  public void mul(short value) {
    this.f424x = (short) (this.f424x * value);
    this.f425y = (short) (this.f425y * value);
    this.f426z = (short) (this.f426z * value);
    this.f423w = (short) (this.f423w * value);
  }

  public static Short4 mul(Short4 a, short b) {
    Short4 result = new Short4();
    result.f424x = (short) (a.f424x * b);
    result.f425y = (short) (a.f425y * b);
    result.f426z = (short) (a.f426z * b);
    result.f423w = (short) (a.f423w * b);
    return result;
  }

  public void div(Short4 a) {
    this.f424x = (short) (this.f424x / a.f424x);
    this.f425y = (short) (this.f425y / a.f425y);
    this.f426z = (short) (this.f426z / a.f426z);
    this.f423w = (short) (this.f423w / a.f423w);
  }

  public static Short4 div(Short4 a, Short4 b) {
    Short4 result = new Short4();
    result.f424x = (short) (a.f424x / b.f424x);
    result.f425y = (short) (a.f425y / b.f425y);
    result.f426z = (short) (a.f426z / b.f426z);
    result.f423w = (short) (a.f423w / b.f423w);
    return result;
  }

  public void div(short value) {
    this.f424x = (short) (this.f424x / value);
    this.f425y = (short) (this.f425y / value);
    this.f426z = (short) (this.f426z / value);
    this.f423w = (short) (this.f423w / value);
  }

  public static Short4 div(Short4 a, short b) {
    Short4 result = new Short4();
    result.f424x = (short) (a.f424x / b);
    result.f425y = (short) (a.f425y / b);
    result.f426z = (short) (a.f426z / b);
    result.f423w = (short) (a.f423w / b);
    return result;
  }

  public void mod(Short4 a) {
    this.f424x = (short) (this.f424x % a.f424x);
    this.f425y = (short) (this.f425y % a.f425y);
    this.f426z = (short) (this.f426z % a.f426z);
    this.f423w = (short) (this.f423w % a.f423w);
  }

  public static Short4 mod(Short4 a, Short4 b) {
    Short4 result = new Short4();
    result.f424x = (short) (a.f424x % b.f424x);
    result.f425y = (short) (a.f425y % b.f425y);
    result.f426z = (short) (a.f426z % b.f426z);
    result.f423w = (short) (a.f423w % b.f423w);
    return result;
  }

  public void mod(short value) {
    this.f424x = (short) (this.f424x % value);
    this.f425y = (short) (this.f425y % value);
    this.f426z = (short) (this.f426z % value);
    this.f423w = (short) (this.f423w % value);
  }

  public static Short4 mod(Short4 a, short b) {
    Short4 result = new Short4();
    result.f424x = (short) (a.f424x % b);
    result.f425y = (short) (a.f425y % b);
    result.f426z = (short) (a.f426z % b);
    result.f423w = (short) (a.f423w % b);
    return result;
  }

  public short length() {
    return (short) 4;
  }

  public void negate() {
    this.f424x = (short) (-this.f424x);
    this.f425y = (short) (-this.f425y);
    this.f426z = (short) (-this.f426z);
    this.f423w = (short) (-this.f423w);
  }

  public short dotProduct(Short4 a) {
    return (short)
        ((this.f424x * a.f424x)
            + (this.f425y * a.f425y)
            + (this.f426z * a.f426z)
            + (this.f423w * a.f423w));
  }

  public static short dotProduct(Short4 a, Short4 b) {
    return (short)
        ((b.f424x * a.f424x) + (b.f425y * a.f425y) + (b.f426z * a.f426z) + (b.f423w * a.f423w));
  }

  public void addMultiple(Short4 a, short factor) {
    this.f424x = (short) (this.f424x + (a.f424x * factor));
    this.f425y = (short) (this.f425y + (a.f425y * factor));
    this.f426z = (short) (this.f426z + (a.f426z * factor));
    this.f423w = (short) (this.f423w + (a.f423w * factor));
  }

  public void set(Short4 a) {
    this.f424x = a.f424x;
    this.f425y = a.f425y;
    this.f426z = a.f426z;
    this.f423w = a.f423w;
  }

  public void setValues(short a, short b, short c, short d) {
    this.f424x = a;
    this.f425y = b;
    this.f426z = c;
    this.f423w = d;
  }

  public short elementSum() {
    return (short) (this.f424x + this.f425y + this.f426z + this.f423w);
  }

  public short get(int i) {
    switch (i) {
      case 0:
        return this.f424x;
      case 1:
        return this.f425y;
      case 2:
        return this.f426z;
      case 3:
        return this.f423w;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void setAt(int i, short value) {
    switch (i) {
      case 0:
        this.f424x = value;
        return;
      case 1:
        this.f425y = value;
        return;
      case 2:
        this.f426z = value;
        return;
      case 3:
        this.f423w = value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void addAt(int i, short value) {
    switch (i) {
      case 0:
        this.f424x = (short) (this.f424x + value);
        return;
      case 1:
        this.f425y = (short) (this.f425y + value);
        return;
      case 2:
        this.f426z = (short) (this.f426z + value);
        return;
      case 3:
        this.f423w = (short) (this.f423w + value);
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void copyTo(short[] data, int offset) {
    data[offset] = this.f424x;
    data[offset + 1] = this.f425y;
    data[offset + 2] = this.f426z;
    data[offset + 3] = this.f423w;
  }
}
