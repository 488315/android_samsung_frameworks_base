package android.view;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class FrameRateCategoryRate implements Parcelable {
    public static final Parcelable.Creator<FrameRateCategoryRate> CREATOR = new Parcelable.Creator<FrameRateCategoryRate>() { // from class: android.view.FrameRateCategoryRate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrameRateCategoryRate createFromParcel(Parcel parcel) {
            return new FrameRateCategoryRate(parcel.readFloat(), parcel.readFloat());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrameRateCategoryRate[] newArray(int i) {
            return new FrameRateCategoryRate[i];
        }
    };
    private final float mHigh;
    private final float mNormal;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FrameRateCategoryRate(float f, float f2) {
        this.mNormal = f;
        this.mHigh = f2;
    }

    public float getNormal() {
        return this.mNormal;
    }

    public float getHigh() {
        return this.mHigh;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FrameRateCategoryRate)) {
            return false;
        }
        FrameRateCategoryRate frameRateCategoryRate = (FrameRateCategoryRate) obj;
        return this.mNormal == frameRateCategoryRate.mNormal && this.mHigh == frameRateCategoryRate.mHigh;
    }

    public int hashCode() {
        return ((Float.hashCode(this.mNormal) + 31) * 31) + Float.hashCode(this.mHigh);
    }

    public String toString() {
        return "FrameRateCategoryRate {normal=" + this.mNormal + ", high=" + this.mHigh + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mNormal);
        parcel.writeFloat(this.mHigh);
    }
}
