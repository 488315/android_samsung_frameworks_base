package android.p009os.vibrator;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.VibrationEffect;
import android.p009os.Vibrator;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class PrebakedSegment extends VibrationEffectSegment {
    public static final Parcelable.Creator<PrebakedSegment> CREATOR = new Parcelable.Creator<PrebakedSegment>() { // from class: android.os.vibrator.PrebakedSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrebakedSegment createFromParcel(Parcel in) {
            in.readInt();
            return new PrebakedSegment(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrebakedSegment[] newArray(int size) {
            return new PrebakedSegment[size];
        }
    };
    private final int mEffectId;
    private final int mEffectStrength;
    private final boolean mFallback;

    PrebakedSegment(Parcel in) {
        this.mEffectId = in.readInt();
        this.mFallback = in.readByte() != 0;
        this.mEffectStrength = in.readInt();
    }

    public PrebakedSegment(int effectId, boolean shouldFallback, int effectStrength) {
        this.mEffectId = effectId;
        this.mFallback = shouldFallback;
        this.mEffectStrength = effectStrength;
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

    @Override // android.p009os.vibrator.VibrationEffectSegment
    public long getDuration() {
        return -1L;
    }

    @Override // android.p009os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(Vibrator vibrator) {
        if (vibrator.areAllEffectsSupported(this.mEffectId) == 1) {
            return true;
        }
        if (!this.mFallback) {
            return false;
        }
        switch (this.mEffectId) {
            case 0:
            case 1:
            case 2:
            case 5:
                return true;
            case 3:
            case 4:
            default:
                return false;
        }
    }

    @Override // android.p009os.vibrator.VibrationEffectSegment
    public boolean isHapticFeedbackCandidate() {
        switch (this.mEffectId) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 21:
                return true;
            default:
                return false;
        }
    }

    @Override // android.p009os.vibrator.VibrationEffectSegment
    public boolean hasNonZeroAmplitude() {
        return true;
    }

    @Override // android.p009os.vibrator.VibrationEffectSegment
    public PrebakedSegment resolve(int defaultAmplitude) {
        return this;
    }

    @Override // android.p009os.vibrator.VibrationEffectSegment
    public PrebakedSegment scale(float scaleFactor) {
        return this;
    }

    @Override // android.p009os.vibrator.VibrationEffectSegment
    public PrebakedSegment applyEffectStrength(int effectStrength) {
        if (effectStrength != this.mEffectStrength && isValidEffectStrength(effectStrength)) {
            return new PrebakedSegment(this.mEffectId, this.mFallback, effectStrength);
        }
        return this;
    }

    private static boolean isValidEffectStrength(int strength) {
        switch (strength) {
            case 0:
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    @Override // android.p009os.vibrator.VibrationEffectSegment
    public void validate() {
        switch (this.mEffectId) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 21:
                break;
            default:
                int[] ringtones = VibrationEffect.RINGTONES;
                int i = this.mEffectId;
                if (i < ringtones[0] || i > ringtones[ringtones.length - 1]) {
                    throw new IllegalArgumentException("Unknown prebaked effect type (value=" + this.mEffectId + NavigationBarInflaterView.KEY_CODE_END);
                }
                break;
        }
        if (!isValidEffectStrength(this.mEffectStrength)) {
            throw new IllegalArgumentException("Unknown prebaked effect strength (value=" + this.mEffectStrength + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof PrebakedSegment)) {
            return false;
        }
        PrebakedSegment other = (PrebakedSegment) o;
        return this.mEffectId == other.mEffectId && this.mFallback == other.mFallback && this.mEffectStrength == other.mEffectStrength;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mEffectId), Boolean.valueOf(this.mFallback), Integer.valueOf(this.mEffectStrength));
    }

    public String toString() {
        return "Prebaked{effect=" + VibrationEffect.effectIdToString(this.mEffectId) + ", strength=" + VibrationEffect.effectStrengthToString(this.mEffectStrength) + ", fallback=" + this.mFallback + "}";
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(1);
        parcel.writeInt(this.mEffectId);
        parcel.writeByte(this.mFallback ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mEffectStrength);
    }
}
