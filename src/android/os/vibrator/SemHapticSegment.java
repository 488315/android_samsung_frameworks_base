package android.os.vibrator;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.VibratorInfo;
import com.samsung.android.vibrator.SemHapticFeedbackConstants;

/* loaded from: classes3.dex */
public final class SemHapticSegment extends VibrationEffectSegment {
    public static final Parcelable.Creator<SemHapticSegment> CREATOR = new Parcelable.Creator<SemHapticSegment>() { // from class: android.os.vibrator.SemHapticSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemHapticSegment createFromParcel(Parcel parcel) {
            parcel.readInt();
            return new SemHapticSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemHapticSegment[] newArray(int i) {
            return new SemHapticSegment[i];
        }
    };
    private int mMagnitude;
    private int mType;

    private boolean isValidEffectStrength(int i) {
        return i >= 0 && i <= 10000;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(VibratorInfo vibratorInfo) {
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public long getDuration() {
        return 5000L;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean isHapticFeedbackCandidate() {
        return true;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public SemHapticSegment resolve(int i) {
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public SemHapticSegment scale(float f) {
        return this;
    }

    SemHapticSegment(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt());
    }

    public SemHapticSegment(int i) {
        this.mMagnitude = -1;
        this.mType = i;
    }

    public SemHapticSegment(int i, int i2) {
        this.mType = i;
        this.mMagnitude = i2;
    }

    public int getType() {
        return this.mType;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SemHapticSegment)) {
            return false;
        }
        SemHapticSegment semHapticSegment = (SemHapticSegment) obj;
        return this.mType == semHapticSegment.mType && this.mMagnitude == semHapticSegment.mMagnitude;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public void validate() {
        if (SemHapticFeedbackConstants.isValidatedVibeIndex(this.mType)) {
            return;
        }
        throw new IllegalArgumentException("invalid haptic type=" + this.mType);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public SemHapticSegment scaleLinearly(float f) {
        return new SemHapticSegment(this.mType);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public String toDebugString() {
        return String.format("SemHaptic: mType=%d, mMagnitude=%d", Integer.valueOf(this.mType), Integer.valueOf(this.mMagnitude));
    }

    public int hashCode() {
        return super.hashCode();
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public SemHapticSegment applyEffectStrength(int i) {
        return isValidEffectStrength(i) ? new SemHapticSegment(this.mType, i) : this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(7);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mMagnitude);
    }

    public String toString() {
        return "SemHaptic{mType=" + this.mType + ", mMagnitude=" + this.mMagnitude + "}";
    }

    public int getSepIndex() {
        return this.mType - SemHapticFeedbackConstants.INTERNAL_INDEX_OFFSET;
    }

    public int getMagnitude() {
        return this.mMagnitude;
    }

    public String getCategoryPath() {
        return SemHapticFeedbackConstants.getCategoryPath(this.mType);
    }

    public int getDefaultSepIndex() {
        return SemHapticFeedbackConstants.getDefaultSepIndex(this.mType);
    }

    public boolean isCustomIndexValid() {
        return SemHapticFeedbackConstants.isCustomIndexValid(this.mType);
    }

    public boolean isRamIndexValid() {
        return SemHapticFeedbackConstants.isRamIndexValid(this.mType);
    }

    public boolean isResourceIndexValid() {
        return SemHapticFeedbackConstants.isResourceIndexValid(this.mType);
    }

    public boolean isHybridIndexValid() {
        return SemHapticFeedbackConstants.isHybridIndexValid(this.mType);
    }

    public boolean isEffectClickReservedDC() {
        return this.mType == 50124;
    }

    public boolean isEffectSilent() {
        return this.mType == 50084;
    }
}
