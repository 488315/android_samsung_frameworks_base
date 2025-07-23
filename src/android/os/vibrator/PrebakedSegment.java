package android.os.vibrator;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.VibratorInfo;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class PrebakedSegment extends VibrationEffectSegment {
    public static final Parcelable.Creator<PrebakedSegment> CREATOR = new Parcelable.Creator<PrebakedSegment>() { // from class: android.os.vibrator.PrebakedSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrebakedSegment createFromParcel(Parcel parcel) {
            parcel.readInt();
            return new PrebakedSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrebakedSegment[] newArray(int i) {
            return new PrebakedSegment[i];
        }
    };
    public static final boolean DEFAULT_SHOULD_FALLBACK = true;
    public static final int DEFAULT_STRENGTH = 1;
    private final int mEffectId;
    private final int mEffectStrength;
    private final boolean mFallback;

    private static boolean isValidEffectStrength(int i) {
        return i == 0 || i == 1 || i == 2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public long getDuration() {
        return -1L;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public PrebakedSegment resolve(int i) {
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public PrebakedSegment scale(float f) {
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public PrebakedSegment scaleLinearly(float f) {
        return this;
    }

    PrebakedSegment(Parcel parcel) {
        this.mEffectId = parcel.readInt();
        this.mFallback = parcel.readByte() != 0;
        this.mEffectStrength = parcel.readInt();
    }

    public PrebakedSegment(int i, boolean z, int i2) {
        this.mEffectId = i;
        this.mFallback = z;
        this.mEffectStrength = i2;
    }

    public int getEffectId() {
        return this.mEffectId;
    }

    public int getEffectStrength() {
        return this.mEffectStrength;
    }

    public boolean shouldFallback() {
        return this.mFallback;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public long getDuration(VibratorInfo vibratorInfo) {
        if (vibratorInfo == null) {
            return getDuration();
        }
        int i = this.mEffectId;
        if (i != 0) {
            if (i == 1) {
                long primitiveDuration = vibratorInfo.getPrimitiveDuration(1);
                return primitiveDuration > 0 ? primitiveDuration * 2 : getDuration();
            }
            if (i != 2) {
                if (i == 3) {
                    return estimateFromPrimitiveDuration(vibratorInfo, 2);
                }
                if (i != 5) {
                    if (i == 21) {
                        return estimateFromPrimitiveDuration(vibratorInfo, 7);
                    }
                    return getDuration();
                }
            }
        }
        return estimateFromPrimitiveDuration(vibratorInfo, 1);
    }

    private long estimateFromPrimitiveDuration(VibratorInfo vibratorInfo, int i) {
        int primitiveDuration = vibratorInfo.getPrimitiveDuration(i);
        return primitiveDuration > 0 ? primitiveDuration : getDuration();
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(VibratorInfo vibratorInfo) {
        if (vibratorInfo.isEffectSupported(this.mEffectId) == 1) {
            return true;
        }
        if (!this.mFallback) {
            return false;
        }
        int i = this.mEffectId;
        return i == 0 || i == 1 || i == 2 || i == 5;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean isHapticFeedbackCandidate() {
        int i = this.mEffectId;
        return i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 21;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public PrebakedSegment applyEffectStrength(int i) {
        return (i == this.mEffectStrength || !isValidEffectStrength(i)) ? this : new PrebakedSegment(this.mEffectId, this.mFallback, i);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public void validate() {
        int i = this.mEffectId;
        if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 21) {
            int[] iArr = VibrationEffect.RINGTONES;
            int i2 = this.mEffectId;
            if (i2 < iArr[0] || i2 > iArr[iArr.length - 1]) {
                throw new IllegalArgumentException("Unknown prebaked effect type (value=" + this.mEffectId + NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        if (isValidEffectStrength(this.mEffectStrength)) {
            return;
        }
        throw new IllegalArgumentException("Unknown prebaked effect strength (value=" + this.mEffectStrength + NavigationBarInflaterView.KEY_CODE_END);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PrebakedSegment)) {
            return false;
        }
        PrebakedSegment prebakedSegment = (PrebakedSegment) obj;
        return this.mEffectId == prebakedSegment.mEffectId && this.mFallback == prebakedSegment.mFallback && this.mEffectStrength == prebakedSegment.mEffectStrength;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mEffectId), Boolean.valueOf(this.mFallback), Integer.valueOf(this.mEffectStrength));
    }

    public String toString() {
        return "Prebaked{effect=" + VibrationEffect.effectIdToString(this.mEffectId) + ", strength=" + VibrationEffect.effectStrengthToString(this.mEffectStrength) + ", fallback=" + this.mFallback + "}";
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public String toDebugString() {
        return String.format("Prebaked=%s(%s, %s fallback)", VibrationEffect.effectIdToString(this.mEffectId), VibrationEffect.effectStrengthToString(this.mEffectStrength), this.mFallback ? "with" : "no");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(1);
        parcel.writeInt(this.mEffectId);
        parcel.writeByte(this.mFallback ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mEffectStrength);
    }
}
