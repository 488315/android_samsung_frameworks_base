package android.graphics;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class PointF implements Parcelable {
    public static final Parcelable.Creator<PointF> CREATOR = new Parcelable.Creator<PointF>() { // from class: android.graphics.PointF.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PointF createFromParcel(Parcel parcel) {
            PointF pointF = new PointF();
            pointF.readFromParcel(parcel);
            return pointF;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PointF[] newArray(int i) {
            return new PointF[i];
        }
    };
    public float x;
    public float y;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PointF() {
    }

    public PointF(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public PointF(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public PointF(PointF pointF) {
        this.x = pointF.x;
        this.y = pointF.y;
    }

    public final void set(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public final void set(PointF pointF) {
        this.x = pointF.x;
        this.y = pointF.y;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public final void offset(float f, float f2) {
        this.x += f;
        this.y += f2;
    }

    public final boolean equals(float f, float f2) {
        return this.x == f && this.y == f2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PointF pointF = (PointF) obj;
        return Float.compare(pointF.x, this.x) == 0 && Float.compare(pointF.y, this.y) == 0;
    }

    public int hashCode() {
        float f = this.x;
        int floatToIntBits = (f != 0.0f ? Float.floatToIntBits(f) : 0) * 31;
        float f2 = this.y;
        return floatToIntBits + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0);
    }

    public String toString() {
        return "PointF(" + this.x + ", " + this.y + NavigationBarInflaterView.KEY_CODE_END;
    }

    public final float length() {
        return length(this.x, this.y);
    }

    public static float length(float f, float f2) {
        return (float) Math.hypot(f, f2);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.x);
        parcel.writeFloat(this.y);
    }

    public void readFromParcel(Parcel parcel) {
        this.x = parcel.readFloat();
        this.y = parcel.readFloat();
    }
}
