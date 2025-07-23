package android.window;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class TrustedPresentationThresholds implements Parcelable {
    public static final Parcelable.Creator<TrustedPresentationThresholds> CREATOR = new Parcelable.Creator<TrustedPresentationThresholds>() { // from class: android.window.TrustedPresentationThresholds.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrustedPresentationThresholds[] newArray(int i) {
            return new TrustedPresentationThresholds[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrustedPresentationThresholds createFromParcel(Parcel parcel) {
            return new TrustedPresentationThresholds(parcel);
        }
    };
    private final float mMinAlpha;
    private final float mMinFractionRendered;
    private final int mStabilityRequirementMs;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public float getMinAlpha() {
        return this.mMinAlpha;
    }

    public float getMinFractionRendered() {
        return this.mMinFractionRendered;
    }

    public int getStabilityRequirementMillis() {
        return this.mStabilityRequirementMs;
    }

    private void checkValid() {
        if (this.mMinAlpha <= 0.0f || this.mMinFractionRendered <= 0.0f || this.mStabilityRequirementMs < 1) {
            throw new IllegalArgumentException("TrustedPresentationThresholds values are invalid");
        }
    }

    public TrustedPresentationThresholds(float f, float f2, int i) {
        this.mMinAlpha = f;
        this.mMinFractionRendered = f2;
        this.mStabilityRequirementMs = i;
        checkValid();
    }

    public String toString() {
        return "TrustedPresentationThresholds { minAlpha = " + this.mMinAlpha + ", minFractionRendered = " + this.mMinFractionRendered + ", stabilityRequirementMs = " + this.mStabilityRequirementMs + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mMinAlpha);
        parcel.writeFloat(this.mMinFractionRendered);
        parcel.writeInt(this.mStabilityRequirementMs);
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.mMinAlpha), Float.valueOf(this.mMinFractionRendered), Integer.valueOf(this.mStabilityRequirementMs));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TrustedPresentationThresholds) {
            TrustedPresentationThresholds trustedPresentationThresholds = (TrustedPresentationThresholds) obj;
            if (this.mMinAlpha == trustedPresentationThresholds.mMinAlpha && this.mMinFractionRendered == trustedPresentationThresholds.mMinFractionRendered && this.mStabilityRequirementMs == trustedPresentationThresholds.mStabilityRequirementMs) {
                return true;
            }
        }
        return false;
    }

    TrustedPresentationThresholds(Parcel parcel) {
        this.mMinAlpha = parcel.readFloat();
        this.mMinFractionRendered = parcel.readFloat();
        this.mStabilityRequirementMs = parcel.readInt();
        checkValid();
    }
}
