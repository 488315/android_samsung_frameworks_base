package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Long2 {

  /* renamed from: x */
  public long f400x;

  /* renamed from: y */
  public long f401y;

  public Long2() {}

  public Long2(long i) {
    this.f401y = i;
    this.f400x = i;
  }

  public Long2(long x, long y) {
    this.f400x = x;
    this.f401y = y;
  }

  public Long2(Long2 source) {
    this.f400x = source.f400x;
    this.f401y = source.f401y;
  }

  public void add(Long2 a) {
    this.f400x += a.f400x;
    this.f401y += a.f401y;
  }

  public static Long2 add(Long2 a, Long2 b) {
    Long2 result = new Long2();
    result.f400x = a.f400x + b.f400x;
    result.f401y = a.f401y + b.f401y;
    return result;
  }

  public void add(long value) {
    this.f400x += value;
    this.f401y += value;
  }

  public static Long2 add(Long2 a, long b) {
    Long2 result = new Long2();
    result.f400x = a.f400x + b;
    result.f401y = a.f401y + b;
    return result;
  }

  public void sub(Long2 a) {
    this.f400x -= a.f400x;
    this.f401y -= a.f401y;
  }

  public static Long2 sub(Long2 a, Long2 b) {
    Long2 result = new Long2();
    result.f400x = a.f400x - b.f400x;
    result.f401y = a.f401y - b.f401y;
    return result;
  }

  public void sub(long value) {
    this.f400x -= value;
    this.f401y -= value;
  }

  public static Long2 sub(Long2 a, long b) {
    Long2 result = new Long2();
    result.f400x = a.f400x - b;
    result.f401y = a.f401y - b;
    return result;
  }

  public void mul(Long2 a) {
    this.f400x *= a.f400x;
    this.f401y *= a.f401y;
  }

  public static Long2 mul(Long2 a, Long2 b) {
    Long2 result = new Long2();
    result.f400x = a.f400x * b.f400x;
    result.f401y = a.f401y * b.f401y;
    return result;
  }

  public void mul(long value) {
    this.f400x *= value;
    this.f401y *= value;
  }

  public static Long2 mul(Long2 a, long b) {
    Long2 result = new Long2();
    result.f400x = a.f400x * b;
    result.f401y = a.f401y * b;
    return result;
  }

  public void div(Long2 a) {
    this.f400x /= a.f400x;
    this.f401y /= a.f401y;
  }

  public static Long2 div(Long2 a, Long2 b) {
    Long2 result = new Long2();
    result.f400x = a.f400x / b.f400x;
    result.f401y = a.f401y / b.f401y;
    return result;
  }

  public void div(long value) {
    this.f400x /= value;
    this.f401y /= value;
  }

  public static Long2 div(Long2 a, long b) {
    Long2 result = new Long2();
    result.f400x = a.f400x / b;
    result.f401y = a.f401y / b;
    return result;
  }

  public void mod(Long2 a) {
    this.f400x %= a.f400x;
    this.f401y %= a.f401y;
  }

  public static Long2 mod(Long2 a, Long2 b) {
    Long2 result = new Long2();
    result.f400x = a.f400x % b.f400x;
    result.f401y = a.f401y % b.f401y;
    return result;
  }

  public void mod(long value) {
    this.f400x %= value;
    this.f401y %= value;
  }

  public static Long2 mod(Long2 a, long b) {
    Long2 result = new Long2();
    result.f400x = a.f400x % b;
    result.f401y = a.f401y % b;
    return result;
  }

  public long length() {
    return 2L;
  }

  public void negate() {
    this.f400x = -this.f400x;
    this.f401y = -this.f401y;
  }

  public long dotProduct(Long2 a) {
    return (this.f400x * a.f400x) + (this.f401y * a.f401y);
  }

  public static long dotProduct(Long2 a, Long2 b) {
    return (b.f400x * a.f400x) + (b.f401y * a.f401y);
  }

  public void addMultiple(Long2 a, long factor) {
    this.f400x += a.f400x * factor;
    this.f401y += a.f401y * factor;
  }

  public void set(Long2 a) {
    this.f400x = a.f400x;
    this.f401y = a.f401y;
  }

  public void setValues(long a, long b) {
    this.f400x = a;
    this.f401y = b;
  }

  public long elementSum() {
    return this.f400x + this.f401y;
  }

  public long get(int i) {
    switch (i) {
      case 0:
        return this.f400x;
      case 1:
        return this.f401y;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void setAt(int i, long value) {
    switch (i) {
      case 0:
        this.f400x = value;
        return;
      case 1:
        this.f401y = value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void addAt(int i, long value) {
    switch (i) {
      case 0:
        this.f400x += value;
        return;
      case 1:
        this.f401y += value;
        return;
      default:
        throw new IndexOutOfBoundsException("Index: i");
    }
  }

  public void copyTo(long[] data, int offset) {
    data[offset] = this.f400x;
    data[offset + 1] = this.f401y;
  }
}
