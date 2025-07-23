package android.hardware.input;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class TouchCalibration implements Parcelable {
    private final float mXOffset;
    private final float mXScale;
    private final float mXYMix;
    private final float mYOffset;
    private final float mYScale;
    private final float mYXMix;
    public static final TouchCalibration IDENTITY = new TouchCalibration();
    public static final Parcelable.Creator<TouchCalibration> CREATOR = new Parcelable.Creator<TouchCalibration>() { // from class: android.hardware.input.TouchCalibration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TouchCalibration createFromParcel(Parcel parcel) {
            return new TouchCalibration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TouchCalibration[] newArray(int i) {
            return new TouchCalibration[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TouchCalibration() {
        this(1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }

    public TouchCalibration(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mXScale = f;
        this.mXYMix = f2;
        this.mXOffset = f3;
        this.mYXMix = f4;
        this.mYScale = f5;
        this.mYOffset = f6;
    }

    public TouchCalibration(Parcel parcel) {
        this.mXScale = parcel.readFloat();
        this.mXYMix = parcel.readFloat();
        this.mXOffset = parcel.readFloat();
        this.mYXMix = parcel.readFloat();
        this.mYScale = parcel.readFloat();
        this.mYOffset = parcel.readFloat();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mXScale);
        parcel.writeFloat(this.mXYMix);
        parcel.writeFloat(this.mXOffset);
        parcel.writeFloat(this.mYXMix);
        parcel.writeFloat(this.mYScale);
        parcel.writeFloat(this.mYOffset);
    }

    public float[] getAffineTransform() {
        return new float[]{this.mXScale, this.mXYMix, this.mXOffset, this.mYXMix, this.mYScale, this.mYOffset};
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof TouchCalibration) {
            TouchCalibration touchCalibration = (TouchCalibration) obj;
            if (touchCalibration.mXScale == this.mXScale && touchCalibration.mXYMix == this.mXYMix && touchCalibration.mXOffset == this.mXOffset && touchCalibration.mYXMix == this.mYXMix && touchCalibration.mYScale == this.mYScale && touchCalibration.mYOffset == this.mYOffset) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.mYOffset) ^ ((((Float.floatToIntBits(this.mXScale) ^ Float.floatToIntBits(this.mXYMix)) ^ Float.floatToIntBits(this.mXOffset)) ^ Float.floatToIntBits(this.mYXMix)) ^ Float.floatToIntBits(this.mYScale));
    }
}
