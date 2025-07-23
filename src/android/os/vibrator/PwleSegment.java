package android.os.vibrator;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.VibratorInfo;
import com.android.internal.util.Preconditions;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class PwleSegment extends VibrationEffectSegment {
    public static final Parcelable.Creator<PwleSegment> CREATOR = new Parcelable.Creator<PwleSegment>() { // from class: android.os.vibrator.PwleSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PwleSegment createFromParcel(Parcel parcel) {
            parcel.readInt();
            return new PwleSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PwleSegment[] newArray(int i) {
            return new PwleSegment[i];
        }
    };
    private final long mDuration;
    private final float mEndAmplitude;
    private final float mEndFrequencyHz;
    private final float mStartAmplitude;
    private final float mStartFrequencyHz;

    @Override // android.os.vibrator.VibrationEffectSegment
    public PwleSegment applyEffectStrength(int i) {
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
    public PwleSegment resolve(int i) {
        return this;
    }

    PwleSegment(Parcel parcel) {
        this(parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readLong());
    }

    public PwleSegment(float f, float f2, float f3, float f4, long j) {
        this.mStartAmplitude = f;
        this.mEndAmplitude = f2;
        this.mStartFrequencyHz = f3;
        this.mEndFrequencyHz = f4;
        this.mDuration = j;
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

    public boolean equals(Object obj) {
        if (!(obj instanceof PwleSegment)) {
            return false;
        }
        PwleSegment pwleSegment = (PwleSegment) obj;
        return Float.compare(this.mStartAmplitude, pwleSegment.mStartAmplitude) == 0 && Float.compare(this.mEndAmplitude, pwleSegment.mEndAmplitude) == 0 && Float.compare(this.mStartFrequencyHz, pwleSegment.mStartFrequencyHz) == 0 && Float.compare(this.mEndFrequencyHz, pwleSegment.mEndFrequencyHz) == 0 && this.mDuration == pwleSegment.mDuration;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(VibratorInfo vibratorInfo) {
        boolean z;
        boolean areEnvelopeEffectsSupported = vibratorInfo.areEnvelopeEffectsSupported();
        float minFrequencyHz = vibratorInfo.getFrequencyProfile().getMinFrequencyHz();
        float maxFrequencyHz = vibratorInfo.getFrequencyProfile().getMaxFrequencyHz();
        float f = this.mStartFrequencyHz;
        if (f >= minFrequencyHz && f <= maxFrequencyHz) {
            float f2 = this.mEndFrequencyHz;
            if (f2 >= minFrequencyHz && f2 <= maxFrequencyHz) {
                z = true;
                return z & areEnvelopeEffectsSupported;
            }
        }
        z = false;
        return z & areEnvelopeEffectsSupported;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public void validate() {
        Preconditions.checkArgumentPositive(this.mStartFrequencyHz, "Start frequency must be greater than zero.");
        Preconditions.checkArgumentPositive(this.mEndFrequencyHz, "End frequency must be greater than zero.");
        Preconditions.checkArgumentPositive(this.mDuration, "Time must be greater than zero.");
        Preconditions.checkArgumentInRange(this.mStartAmplitude, 0.0f, 1.0f, "startAmplitude");
        Preconditions.checkArgumentInRange(this.mEndAmplitude, 0.0f, 1.0f, "endAmplitude");
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public PwleSegment scale(float f) {
        float scale = VibrationEffect.scale(this.mStartAmplitude, f);
        float scale2 = VibrationEffect.scale(this.mEndAmplitude, f);
        return (Float.compare(this.mStartAmplitude, scale) == 0 && Float.compare(this.mEndAmplitude, scale2) == 0) ? this : new PwleSegment(scale, scale2, this.mStartFrequencyHz, this.mEndFrequencyHz, this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public PwleSegment scaleLinearly(float f) {
        float scaleLinearly = VibrationEffect.scaleLinearly(this.mStartAmplitude, f);
        float scaleLinearly2 = VibrationEffect.scaleLinearly(this.mEndAmplitude, f);
        return (Float.compare(this.mStartAmplitude, scaleLinearly) == 0 && Float.compare(this.mEndAmplitude, scaleLinearly2) == 0) ? this : new PwleSegment(scaleLinearly, scaleLinearly2, this.mStartFrequencyHz, this.mEndFrequencyHz, this.mDuration);
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.mStartAmplitude), Float.valueOf(this.mEndAmplitude), Float.valueOf(this.mStartFrequencyHz), Float.valueOf(this.mEndFrequencyHz), Long.valueOf(this.mDuration));
    }

    public String toString() {
        return "Pwle{startAmplitude=" + this.mStartAmplitude + ", endAmplitude=" + this.mEndAmplitude + ", startFrequencyHz=" + this.mStartFrequencyHz + ", endFrequencyHz=" + this.mEndFrequencyHz + ", duration=" + this.mDuration + "}";
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public String toDebugString() {
        return String.format(Locale.US, "Pwle=%dms(amplitude=%.2f @ %.2fHz to %.2f @ %.2fHz)", Long.valueOf(this.mDuration), Float.valueOf(this.mStartAmplitude), Float.valueOf(this.mStartFrequencyHz), Float.valueOf(this.mEndAmplitude), Float.valueOf(this.mEndFrequencyHz));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(5);
        parcel.writeFloat(this.mStartAmplitude);
        parcel.writeFloat(this.mEndAmplitude);
        parcel.writeFloat(this.mStartFrequencyHz);
        parcel.writeFloat(this.mEndFrequencyHz);
        parcel.writeLong(this.mDuration);
    }
}
