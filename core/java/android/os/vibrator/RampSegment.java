package android.os.vibrator;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.VibratorInfo;

import com.android.internal.util.Preconditions;

import java.util.Objects;

public final class RampSegment extends VibrationEffectSegment {
    public static final Parcelable.Creator<RampSegment> CREATOR =
            new Parcelable.Creator<RampSegment>() { // from class: android.os.vibrator.RampSegment.1
                @Override // android.os.Parcelable.Creator
                public RampSegment createFromParcel(Parcel in) {
                    in.readInt();
                    return new RampSegment(in);
                }

                @Override // android.os.Parcelable.Creator
                public RampSegment[] newArray(int size) {
                    return new RampSegment[size];
                }
            };
    private final int mDuration;
    private final float mEndAmplitude;
    private final float mEndFrequencyHz;
    private final float mStartAmplitude;
    private final float mStartFrequencyHz;

    RampSegment(Parcel in) {
        this(in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat(), in.readInt());
    }

    public RampSegment(
            float startAmplitude,
            float endAmplitude,
            float startFrequencyHz,
            float endFrequencyHz,
            int duration) {
        this.mStartAmplitude = startAmplitude;
        this.mEndAmplitude = endAmplitude;
        this.mStartFrequencyHz = startFrequencyHz;
        this.mEndFrequencyHz = endFrequencyHz;
        this.mDuration = duration;
    }

    public boolean equals(Object o) {
        if (!(o instanceof RampSegment)) {
            return false;
        }
        RampSegment other = (RampSegment) o;
        return Float.compare(this.mStartAmplitude, other.mStartAmplitude) == 0
                && Float.compare(this.mEndAmplitude, other.mEndAmplitude) == 0
                && Float.compare(this.mStartFrequencyHz, other.mStartFrequencyHz) == 0
                && Float.compare(this.mEndFrequencyHz, other.mEndFrequencyHz) == 0
                && this.mDuration == other.mDuration;
    }

    public float getStartAmplitude() {
        return this.mStartAmplitude;
    }

    public float getEndAmplitude() {
        return this.mEndAmplitude;
    }

    public float getStartFrequencyHz() {
        return this.mStartFrequencyHz;
    }

    public float getEndFrequencyHz() {
        return this.mEndFrequencyHz;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public long getDuration() {
        return this.mDuration;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(VibratorInfo vibratorInfo) {
        boolean areFeaturesSupported =
                (this.mStartFrequencyHz != this.mEndFrequencyHz
                                || frequencyRequiresFrequencyControl(this.mStartFrequencyHz))
                        ? true & vibratorInfo.hasFrequencyControl()
                        : true;
        if (this.mStartAmplitude != this.mEndAmplitude
                || amplitudeRequiresAmplitudeControl(this.mStartAmplitude)) {
            return areFeaturesSupported & vibratorInfo.hasAmplitudeControl();
        }
        return areFeaturesSupported;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean isHapticFeedbackCandidate() {
        return true;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public void validate() {
        VibrationEffectSegment.checkFrequencyArgument(this.mStartFrequencyHz, "startFrequencyHz");
        VibrationEffectSegment.checkFrequencyArgument(this.mEndFrequencyHz, "endFrequencyHz");
        VibrationEffectSegment.checkDurationArgument(this.mDuration, "duration");
        Preconditions.checkArgumentInRange(this.mStartAmplitude, 0.0f, 1.0f, "startAmplitude");
        Preconditions.checkArgumentInRange(this.mEndAmplitude, 0.0f, 1.0f, "endAmplitude");
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public RampSegment resolve(int defaultAmplitude) {
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public RampSegment scale(float scaleFactor) {
        float newStartAmplitude = VibrationEffect.scale(this.mStartAmplitude, scaleFactor);
        float newEndAmplitude = VibrationEffect.scale(this.mEndAmplitude, scaleFactor);
        if (Float.compare(this.mStartAmplitude, newStartAmplitude) == 0
                && Float.compare(this.mEndAmplitude, newEndAmplitude) == 0) {
            return this;
        }
        return new RampSegment(
                newStartAmplitude,
                newEndAmplitude,
                this.mStartFrequencyHz,
                this.mEndFrequencyHz,
                this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public RampSegment scaleLinearly(float scaleFactor) {
        float newStartAmplitude = VibrationEffect.scaleLinearly(this.mStartAmplitude, scaleFactor);
        float newEndAmplitude = VibrationEffect.scaleLinearly(this.mEndAmplitude, scaleFactor);
        if (Float.compare(this.mStartAmplitude, newStartAmplitude) == 0
                && Float.compare(this.mEndAmplitude, newEndAmplitude) == 0) {
            return this;
        }
        return new RampSegment(
                newStartAmplitude,
                newEndAmplitude,
                this.mStartFrequencyHz,
                this.mEndFrequencyHz,
                this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public RampSegment applyEffectStrength(int effectStrength) {
        return this;
    }

    public int hashCode() {
        return Objects.hash(
                Float.valueOf(this.mStartAmplitude),
                Float.valueOf(this.mEndAmplitude),
                Float.valueOf(this.mStartFrequencyHz),
                Float.valueOf(this.mEndFrequencyHz),
                Integer.valueOf(this.mDuration));
    }

    public String toString() {
        return "Ramp{startAmplitude="
                + this.mStartAmplitude
                + ", endAmplitude="
                + this.mEndAmplitude
                + ", startFrequencyHz="
                + this.mStartFrequencyHz
                + ", endFrequencyHz="
                + this.mEndFrequencyHz
                + ", duration="
                + this.mDuration
                + "}";
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public String toDebugString() {
        return String.format(
                "Ramp=%dms(amplitude=%.2f%s to %.2f%s)",
                Integer.valueOf(this.mDuration),
                Float.valueOf(this.mStartAmplitude),
                Float.compare(this.mStartFrequencyHz, 0.0f) == 0
                        ? ""
                        : " @ " + this.mStartFrequencyHz + "Hz",
                Float.valueOf(this.mEndAmplitude),
                Float.compare(this.mEndFrequencyHz, 0.0f) != 0
                        ? " @ " + this.mEndFrequencyHz + "Hz"
                        : "");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(4);
        out.writeFloat(this.mStartAmplitude);
        out.writeFloat(this.mEndAmplitude);
        out.writeFloat(this.mStartFrequencyHz);
        out.writeFloat(this.mEndFrequencyHz);
        out.writeInt(this.mDuration);
    }
}
