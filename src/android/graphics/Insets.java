package android.graphics;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class Insets implements Parcelable {
    public final int bottom;
    public final int left;
    public final int right;
    public final int top;
    public static final Insets NONE = new Insets(0, 0, 0, 0);
    public static final Parcelable.Creator<Insets> CREATOR = new Parcelable.Creator<Insets>() { // from class: android.graphics.Insets.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Insets createFromParcel(Parcel parcel) {
            return new Insets(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Insets[] newArray(int i) {
            return new Insets[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private Insets(int i, int i2, int i3, int i4) {
        this.left = i;
        this.top = i2;
        this.right = i3;
        this.bottom = i4;
    }

    public static Insets of(int i, int i2, int i3, int i4) {
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return NONE;
        }
        return new Insets(i, i2, i3, i4);
    }

    public static Insets of(Rect rect) {
        return rect == null ? NONE : of(rect.left, rect.top, rect.right, rect.bottom);
    }

    public Rect toRect() {
        return new Rect(this.left, this.top, this.right, this.bottom);
    }

    public static Insets add(Insets insets, Insets insets2) {
        return of(insets.left + insets2.left, insets.top + insets2.top, insets.right + insets2.right, insets.bottom + insets2.bottom);
    }

    public static Insets subtract(Insets insets, Insets insets2) {
        return of(insets.left - insets2.left, insets.top - insets2.top, insets.right - insets2.right, insets.bottom - insets2.bottom);
    }

    public static Insets max(Insets insets, Insets insets2) {
        return of(Math.max(insets.left, insets2.left), Math.max(insets.top, insets2.top), Math.max(insets.right, insets2.right), Math.max(insets.bottom, insets2.bottom));
    }

    public static Insets min(Insets insets, Insets insets2) {
        return of(Math.min(insets.left, insets2.left), Math.min(insets.top, insets2.top), Math.min(insets.right, insets2.right), Math.min(insets.bottom, insets2.bottom));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Insets insets = (Insets) obj;
        return this.bottom == insets.bottom && this.left == insets.left && this.right == insets.right && this.top == insets.top;
    }

    public int hashCode() {
        return (((((this.left * 31) + this.top) * 31) + this.right) * 31) + this.bottom;
    }

    public String toString() {
        return "Insets{left=" + this.left + ", top=" + this.top + ", right=" + this.right + ", bottom=" + this.bottom + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.left);
        parcel.writeInt(this.top);
        parcel.writeInt(this.right);
        parcel.writeInt(this.bottom);
    }
}
