package android.hardware.display;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

public final class Curve implements Parcelable {
    public static final Parcelable.Creator<Curve> CREATOR =
            new Parcelable.Creator<Curve>() { // from class: android.hardware.display.Curve.1
                @Override // android.os.Parcelable.Creator
                public Curve createFromParcel(Parcel in) {
                    float[] x = in.createFloatArray();
                    float[] y = in.createFloatArray();
                    return new Curve(x, y);
                }

                @Override // android.os.Parcelable.Creator
                public Curve[] newArray(int size) {
                    return new Curve[size];
                }
            };
    private final float[] mX;
    private final float[] mY;

    public Curve(float[] x, float[] y) {
        this.mX = x;
        this.mY = y;
    }

    public float[] getX() {
        return this.mX;
    }

    public float[] getY() {
        return this.mY;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloatArray(this.mX);
        out.writeFloatArray(this.mY);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        int size = this.mX.length;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(NavigationBarInflaterView.KEY_CODE_START)
                    .append(this.mX[i])
                    .append(", ")
                    .append(this.mY[i])
                    .append(NavigationBarInflaterView.KEY_CODE_END);
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
