package android.view;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class RoundedCorner implements Parcelable {
    public static final Parcelable.Creator<RoundedCorner> CREATOR = new Parcelable.Creator<RoundedCorner>() { // from class: android.view.RoundedCorner.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RoundedCorner createFromParcel(Parcel parcel) {
            return new RoundedCorner(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RoundedCorner[] newArray(int i) {
            return new RoundedCorner[i];
        }
    };
    public static final int POSITION_BOTTOM_LEFT = 3;
    public static final int POSITION_BOTTOM_RIGHT = 2;
    public static final int POSITION_TOP_LEFT = 0;
    public static final int POSITION_TOP_RIGHT = 1;
    private final Point mCenter;
    private final int mPosition;
    private final int mRadius;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Position {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RoundedCorner(int i) {
        this.mPosition = i;
        this.mRadius = 0;
        this.mCenter = new Point(0, 0);
    }

    public RoundedCorner(int i, int i2, int i3, int i4) {
        this.mPosition = i;
        this.mRadius = i2;
        this.mCenter = new Point(i3, i4);
    }

    RoundedCorner(RoundedCorner roundedCorner) {
        this.mPosition = roundedCorner.getPosition();
        this.mRadius = roundedCorner.getRadius();
        this.mCenter = new Point(roundedCorner.getCenter());
    }

    public int getPosition() {
        return this.mPosition;
    }

    public int getRadius() {
        return this.mRadius;
    }

    public Point getCenter() {
        return new Point(this.mCenter);
    }

    public boolean isEmpty() {
        return this.mRadius == 0 || this.mCenter.x <= 0 || this.mCenter.y <= 0;
    }

    private String getPositionString(int i) {
        if (i == 0) {
            return "TopLeft";
        }
        if (i == 1) {
            return "TopRight";
        }
        if (i == 2) {
            return "BottomRight";
        }
        if (i == 3) {
            return "BottomLeft";
        }
        return "Invalid";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RoundedCorner) {
            RoundedCorner roundedCorner = (RoundedCorner) obj;
            if (this.mPosition == roundedCorner.mPosition && this.mRadius == roundedCorner.mRadius && this.mCenter.equals(roundedCorner.mCenter)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((this.mPosition * 31) + this.mRadius) * 31) + this.mCenter.hashCode();
    }

    public String toString() {
        return "RoundedCorner{position=" + getPositionString(this.mPosition) + ", radius=" + this.mRadius + ", center=" + this.mCenter + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPosition);
        parcel.writeInt(this.mRadius);
        parcel.writeInt(this.mCenter.x);
        parcel.writeInt(this.mCenter.y);
    }
}
