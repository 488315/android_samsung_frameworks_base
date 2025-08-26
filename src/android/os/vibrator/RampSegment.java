package android.os.vibrator;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.VibratorInfo;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class RampSegment extends VibrationEffectSegment {
    public static final Parcelable.Creator<RampSegment> CREATOR = new Parcelable.Creator<RampSegment>() { // from class: android.os.vibrator.RampSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RampSegment createFromParcel(Parcel parcel) {
            parcel.readInt();
            return new RampSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RampSegment[] newArray(int i) {
            return new RampSegment[i];
        }
    };
    private final int mDuration;
    private final float mEndAmplitude;
    private final float mEndFrequencyHz;
    private final float mStartAmplitude;
    private final float mStartFrequencyHz;

    @Override // android.os.vibrator.VibrationEffectSegment
    public RampSegment applyEffectStrength(int i) {
        return this;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean isHapticFeedbackCandidate() {
        return true;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public RampSegment resolve(int i) {
        return this;
    }

    RampSegment(Parcel parcel) {
        this(parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readInt());
    }

    public RampSegment(float f, float f2, float f3, float f4, int i) {
        this.mStartAmplitude = f;
        this.mEndAmplitude = f2;
        this.mStartFrequencyHz = f3;
        this.mEndFrequencyHz = f4;
        this.mDuration = i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RampSegment)) {
            return false;
        }
        RampSegment rampSegment = (RampSegment) obj;
        return Float.compare(this.mStartAmplitude, rampSegment.mStartAmplitude) == 0 && Float.compare(this.mEndAmplitude, rampSegment.mEndAmplitude) == 0 && Float.compare(this.mStartFrequencyHz, rampSegment.mStartFrequencyHz) == 0 && Float.compare(this.mEndFrequencyHz, rampSegment.mEndFrequencyHz) == 0 && this.mDuration == rampSegment.mDuration;
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
        float f = this.mStartFrequencyHz;
        boolean zHasFrequencyControl = (f != this.mEndFrequencyHz || frequencyRequiresFrequencyControl(f)) ? vibratorInfo.hasFrequencyControl() : true;
        float f2 = this.mStartAmplitude;
        return (f2 != this.mEndAmplitude || amplitudeRequiresAmplitudeControl(f2)) ? vibratorInfo.hasAmplitudeControl() & zHasFrequencyControl : zHasFrequencyControl;
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
    public RampSegment scale(float f) {
        float fScale = VibrationEffect.scale(this.mStartAmplitude, f);
        float fScale2 = VibrationEffect.scale(this.mEndAmplitude, f);
        return (Float.compare(this.mStartAmplitude, fScale) == 0 && Float.compare(this.mEndAmplitude, fScale2) == 0) ? this : new RampSegment(fScale, fScale2, this.mStartFrequencyHz, this.mEndFrequencyHz, this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public RampSegment scaleLinearly(float f) {
        float fScaleLinearly = VibrationEffect.scaleLinearly(this.mStartAmplitude, f);
        float fScaleLinearly2 = VibrationEffect.scaleLinearly(this.mEndAmplitude, f);
        return (Float.compare(this.mStartAmplitude, fScaleLinearly) == 0 && Float.compare(this.mEndAmplitude, fScaleLinearly2) == 0) ? this : new RampSegment(fScaleLinearly, fScaleLinearly2, this.mStartFrequencyHz, this.mEndFrequencyHz, this.mDuration);
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.mStartAmplitude), Float.valueOf(this.mEndAmplitude), Float.valueOf(this.mStartFrequencyHz), Float.valueOf(this.mEndFrequencyHz), Integer.valueOf(this.mDuration));
    }

    public String toString() {
        return "Ramp{startAmplitude=" + this.mStartAmplitude + ", endAmplitude=" + this.mEndAmplitude + ", startFrequencyHz=" + this.mStartFrequencyHz + ", endFrequencyHz=" + this.mEndFrequencyHz + ", duration=" + this.mDuration + "}";
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public String toDebugString() {
        String str;
        Integer numValueOf = Integer.valueOf(this.mDuration);
        Float fValueOf = Float.valueOf(this.mStartAmplitude);
        String str2 = "";
        if (Float.compare(this.mStartFrequencyHz, 0.0f) == 0) {
            str = "";
        } else {
            str = " @ " + this.mStartFrequencyHz + "Hz";
        }
        Float fValueOf2 = Float.valueOf(this.mEndAmplitude);
        if (Float.compare(this.mEndFrequencyHz, 0.0f) != 0) {
            str2 = " @ " + this.mEndFrequencyHz + "Hz";
        }
        return String.format("Ramp=%dms(amplitude=%.2f%s to %.2f%s)", numValueOf, fValueOf, str, fValueOf2, str2);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(4);
        parcel.writeFloat(this.mStartAmplitude);
        parcel.writeFloat(this.mEndAmplitude);
        parcel.writeFloat(this.mStartFrequencyHz);
        parcel.writeFloat(this.mEndFrequencyHz);
        parcel.writeInt(this.mDuration);
    }
}
