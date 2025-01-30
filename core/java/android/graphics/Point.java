package android.graphics;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes.dex */
public class Point implements Parcelable {
  public static final Parcelable.Creator<Point> CREATOR =
      new Parcelable.Creator<Point>() { // from class: android.graphics.Point.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Point createFromParcel(Parcel in) {
          Point r = new Point();
          r.readFromParcel(in);
          return r;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Point[] newArray(int size) {
          return new Point[size];
        }
      };

  /* renamed from: x */
  public int f85x;

  /* renamed from: y */
  public int f86y;

  public Point() {}

  public Point(int x, int y) {
    this.f85x = x;
    this.f86y = y;
  }

  public Point(Point src) {
    set(src);
  }

  public void set(int x, int y) {
    this.f85x = x;
    this.f86y = y;
  }

  public void set(Point src) {
    this.f85x = src.f85x;
    this.f86y = src.f86y;
  }

  public final void negate() {
    this.f85x = -this.f85x;
    this.f86y = -this.f86y;
  }

  public final void offset(int dx, int dy) {
    this.f85x += dx;
    this.f86y += dy;
  }

  public final boolean equals(int x, int y) {
    return this.f85x == x && this.f86y == y;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Point point = (Point) o;
    if (this.f85x == point.f85x && this.f86y == point.f86y) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    int result = this.f85x;
    return (result * 31) + this.f86y;
  }

  public String toString() {
    return "Point(" + this.f85x + ", " + this.f86y + NavigationBarInflaterView.KEY_CODE_END;
  }

  public String flattenToString() {
    return this.f85x + "x" + this.f86y;
  }

  public static Point unflattenFromString(String s) throws NumberFormatException {
    int sep_ix = s.indexOf("x");
    return new Point(
        Integer.parseInt(s.substring(0, sep_ix)), Integer.parseInt(s.substring(sep_ix + 1)));
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeInt(this.f85x);
    out.writeInt(this.f86y);
  }

  public void readFromParcel(Parcel in) {
    this.f85x = in.readInt();
    this.f86y = in.readInt();
  }
}
