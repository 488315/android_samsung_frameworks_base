package android.window;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public final class TrustedPresentationThresholds implements Parcelable {
    public static final Parcelable.Creator<TrustedPresentationThresholds> CREATOR =
            new Parcelable.Creator<
                    TrustedPresentationThresholds>() { // from class:
                                                       // android.window.TrustedPresentationThresholds.1
                @Override // android.os.Parcelable.Creator
                public TrustedPresentationThresholds[] newArray(int size) {
                    return new TrustedPresentationThresholds[size];
                }

                @Override // android.os.Parcelable.Creator
                public TrustedPresentationThresholds createFromParcel(Parcel in) {
                    return new TrustedPresentationThresholds(in);
                }
            };
    private final float mMinAlpha;
    private final float mMinFractionRendered;
    private final int mStabilityRequirementMs;

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
        if (this.mMinAlpha <= 0.0f
                || this.mMinFractionRendered <= 0.0f
                || this.mStabilityRequirementMs < 1) {
            throw new IllegalArgumentException("TrustedPresentationThresholds values are invalid");
        }
    }

    public TrustedPresentationThresholds(
            float minAlpha, float minFractionRendered, int stabilityRequirementMs) {
        this.mMinAlpha = minAlpha;
        this.mMinFractionRendered = minFractionRendered;
        this.mStabilityRequirementMs = stabilityRequirementMs;
        checkValid();
    }

    public String toString() {
        return "TrustedPresentationThresholds { minAlpha = "
                + this.mMinAlpha
                + ", minFractionRendered = "
                + this.mMinFractionRendered
                + ", stabilityRequirementMs = "
                + this.mStabilityRequirementMs
                + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.mMinAlpha);
        dest.writeFloat(this.mMinFractionRendered);
        dest.writeInt(this.mStabilityRequirementMs);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return Objects.hash(
                Float.valueOf(this.mMinAlpha),
                Float.valueOf(this.mMinFractionRendered),
                Integer.valueOf(this.mStabilityRequirementMs));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrustedPresentationThresholds)) {
            return false;
        }
        TrustedPresentationThresholds that = (TrustedPresentationThresholds) o;
        return this.mMinAlpha == that.mMinAlpha
                && this.mMinFractionRendered == that.mMinFractionRendered
                && this.mStabilityRequirementMs == that.mStabilityRequirementMs;
    }

    TrustedPresentationThresholds(Parcel in) {
        this.mMinAlpha = in.readFloat();
        this.mMinFractionRendered = in.readFloat();
        this.mStabilityRequirementMs = in.readInt();
        checkValid();
    }
}
