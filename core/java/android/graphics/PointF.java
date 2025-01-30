package android.graphics;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes.dex */
public class PointF implements Parcelable {
    public static final Parcelable.Creator<PointF> CREATOR = new Parcelable.Creator<PointF>() { // from class: android.graphics.PointF.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PointF createFromParcel(Parcel in) {
            PointF r = new PointF();
            r.readFromParcel(in);
            return r;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PointF[] newArray(int size) {
            return new PointF[size];
        }
    };

    /* renamed from: x */
    public float f87x;

    /* renamed from: y */
    public float f88y;

    public PointF() {
    }

    public PointF(float x, float y) {
        this.f87x = x;
        this.f88y = y;
    }

    public PointF(Point p) {
        this.f87x = p.f85x;
        this.f88y = p.f86y;
    }

    public PointF(PointF p) {
        this.f87x = p.f87x;
        this.f88y = p.f88y;
    }

    public final void set(float x, float y) {
        this.f87x = x;
        this.f88y = y;
    }

    public final void set(PointF p) {
        this.f87x = p.f87x;
        this.f88y = p.f88y;
    }

    public final void negate() {
        this.f87x = -this.f87x;
        this.f88y = -this.f88y;
    }

    public final void offset(float dx, float dy) {
        this.f87x += dx;
        this.f88y += dy;
    }

    public final boolean equals(float x, float y) {
        return this.f87x == x && this.f88y == y;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PointF pointF = (PointF) o;
        if (Float.compare(pointF.f87x, this.f87x) == 0 && Float.compare(pointF.f88y, this.f88y) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        float f = this.f87x;
        int result = f != 0.0f ? Float.floatToIntBits(f) : 0;
        int i = result * 31;
        float f2 = this.f88y;
        int result2 = i + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0);
        return result2;
    }

    public String toString() {
        return "PointF(" + this.f87x + ", " + this.f88y + NavigationBarInflaterView.KEY_CODE_END;
    }

    public final float length() {
        return length(this.f87x, this.f88y);
    }

    public static float length(float x, float y) {
        return (float) Math.hypot(x, y);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(this.f87x);
        out.writeFloat(this.f88y);
    }

    public void readFromParcel(Parcel in) {
        this.f87x = in.readFloat();
        this.f88y = in.readFloat();
    }
}
