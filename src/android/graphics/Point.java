package android.graphics;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class Point implements Parcelable {
    public static final Parcelable.Creator<Point> CREATOR = new Parcelable.Creator<Point>() { // from class: android.graphics.Point.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Point createFromParcel(Parcel parcel) {
            Point point = new Point();
            point.readFromParcel(parcel);
            return point;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Point[] newArray(int i) {
            return new Point[i];
        }
    };
    public int x;
    public int y;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Point() {
    }

    public Point(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public Point(Point point) {
        set(point);
    }

    public void set(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public void set(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public final void offset(int i, int i2) {
        this.x += i;
        this.y += i2;
    }

    public final boolean equals(int i, int i2) {
        return this.x == i && this.y == i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point point = (Point) obj;
        return this.x == point.x && this.y == point.y;
    }

    public int hashCode() {
        return (this.x * 31) + this.y;
    }

    public String toString() {
        return "Point(" + this.x + ", " + this.y + NavigationBarInflaterView.KEY_CODE_END;
    }

    public String flattenToString() {
        return this.x + "x" + this.y;
    }

    public static Point unflattenFromString(String str) throws NumberFormatException {
        int indexOf = str.indexOf("x");
        return new Point(Integer.parseInt(str.substring(0, indexOf)), Integer.parseInt(str.substring(indexOf + 1)));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.x);
        parcel.writeInt(this.y);
    }

    public void readFromParcel(Parcel parcel) {
        this.x = parcel.readInt();
        this.y = parcel.readInt();
    }
}
