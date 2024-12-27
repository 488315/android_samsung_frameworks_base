package android.graphics;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

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
    public int x;
    public int y;

    public Point() {}

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point src) {
        set(src);
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(Point src) {
        this.x = src.x;
        this.y = src.y;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public final void offset(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public final boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        if (this.x == point.x && this.y == point.y) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = this.x;
        return (result * 31) + this.y;
    }

    public String toString() {
        return "Point(" + this.x + ", " + this.y + NavigationBarInflaterView.KEY_CODE_END;
    }

    public String flattenToString() {
        return this.x + "x" + this.y;
    }

    public static Point unflattenFromString(String s) throws NumberFormatException {
        int sep_ix = s.indexOf("x");
        return new Point(
                Integer.parseInt(s.substring(0, sep_ix)),
                Integer.parseInt(s.substring(sep_ix + 1)));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.x);
        out.writeInt(this.y);
    }

    public void readFromParcel(Parcel in) {
        this.x = in.readInt();
        this.y = in.readInt();
    }
}
