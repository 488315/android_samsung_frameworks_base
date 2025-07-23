package android.os.vibrator;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.VibratorInfo;

/* loaded from: classes3.dex */
public abstract class VibrationEffectSegment implements Parcelable {
    public static final Parcelable.Creator<VibrationEffectSegment> CREATOR = new Parcelable.Creator<VibrationEffectSegment>() { // from class: android.os.vibrator.VibrationEffectSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VibrationEffectSegment createFromParcel(Parcel parcel) {
            switch (parcel.readInt()) {
                case 1:
                    return new PrebakedSegment(parcel);
                case 2:
                    return new PrimitiveSegment(parcel);
                case 3:
                    return new StepSegment(parcel);
                case 4:
                    return new RampSegment(parcel);
                case 5:
                    if (Flags.normalizedPwleEffects()) {
                        return new PwleSegment(parcel);
                    }
                case 6:
                    if (Flags.normalizedPwleEffects()) {
                        return new BasicPwleSegment(parcel);
                    }
                case 7:
                    return new SemHapticSegment(parcel);
                default:
                    throw new IllegalStateException("Unexpected vibration event type token in parcel.");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VibrationEffectSegment[] newArray(int i) {
            return new VibrationEffectSegment[i];
        }
    };
    static final int PARCEL_TOKEN_BASIC_PWLE = 6;
    static final int PARCEL_TOKEN_PREBAKED = 1;
    static final int PARCEL_TOKEN_PRIMITIVE = 2;
    static final int PARCEL_TOKEN_PWLE = 5;
    static final int PARCEL_TOKEN_RAMP = 4;
    static final int PARCEL_TOKEN_SEMHAPTIC = 7;
    static final int PARCEL_TOKEN_STEP = 3;

    protected static boolean amplitudeRequiresAmplitudeControl(float f) {
        return (f == 0.0f || f == 1.0f || f == -1.0f) ? false : true;
    }

    protected static boolean frequencyRequiresFrequencyControl(float f) {
        return f != 0.0f;
    }

    public abstract <T extends VibrationEffectSegment> T applyEffectStrength(int i);

    public abstract boolean areVibrationFeaturesSupported(VibratorInfo vibratorInfo);

    public abstract long getDuration();

    public abstract boolean isHapticFeedbackCandidate();

    public abstract <T extends VibrationEffectSegment> T resolve(int i);

    public abstract <T extends VibrationEffectSegment> T scale(float f);

    public abstract <T extends VibrationEffectSegment> T scaleLinearly(float f);

    public abstract String toDebugString();

    public abstract void validate();

    VibrationEffectSegment() {
    }

    public long getDuration(VibratorInfo vibratorInfo) {
        return getDuration();
    }

    public static void checkFrequencyArgument(float f, String str) {
        if (Float.isNaN(f)) {
            throw new IllegalArgumentException(str + " must not be NaN");
        }
        if (Float.isInfinite(f)) {
            throw new IllegalArgumentException(str + " must not be infinite");
        }
        if (f >= 0.0f) {
            return;
        }
        throw new IllegalArgumentException(str + " must be >= 0, got " + f);
    }

    public static void checkDurationArgument(long j, String str) {
        if (j >= 0) {
            return;
        }
        throw new IllegalArgumentException(str + " must be >= 0, got " + j);
    }
}
