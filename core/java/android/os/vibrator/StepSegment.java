package android.os.vibrator;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.VibratorInfo;
import com.android.internal.util.Preconditions;
import com.android.internal.vibrator.persistence.XmlConstants;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class StepSegment extends VibrationEffectSegment {
    public static final Parcelable.Creator<StepSegment> CREATOR = new Parcelable.Creator<StepSegment>() { // from class: android.os.vibrator.StepSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StepSegment createFromParcel(Parcel in) {
            in.readInt();
            return new StepSegment(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StepSegment[] newArray(int size) {
            return new StepSegment[size];
        }
    };
    private final float mAmplitude;
    private final int mDuration;
    private final float mFrequencyHz;

    StepSegment(Parcel in) {
        this(in.readFloat(), in.readFloat(), in.readInt());
    }

    public StepSegment(float amplitude, float frequencyHz, int duration) {
        this.mAmplitude = amplitude;
        this.mFrequencyHz = frequencyHz;
        this.mDuration = duration;
    }

    public boolean equals(Object o) {
        if (!(o instanceof StepSegment)) {
            return false;
        }
        StepSegment other = (StepSegment) o;
        return Float.compare(this.mAmplitude, other.mAmplitude) == 0 && Float.compare(this.mFrequencyHz, other.mFrequencyHz) == 0 && this.mDuration == other.mDuration;
    }

    public float getAmplitude() {
        return this.mAmplitude;
    }

    public float getFrequencyHz() {
        return this.mFrequencyHz;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public long getDuration() {
        return this.mDuration;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(VibratorInfo vibratorInfo) {
        boolean areFeaturesSupported = frequencyRequiresFrequencyControl(this.mFrequencyHz) ? true & vibratorInfo.hasFrequencyControl() : true;
        if (amplitudeRequiresAmplitudeControl(this.mAmplitude)) {
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
        VibrationEffectSegment.checkFrequencyArgument(this.mFrequencyHz, "frequencyHz");
        VibrationEffectSegment.checkDurationArgument(this.mDuration, "duration");
        if (Float.compare(this.mAmplitude, -1.0f) != 0) {
            Preconditions.checkArgumentInRange(this.mAmplitude, 0.0f, 1.0f, XmlConstants.ATTRIBUTE_AMPLITUDE);
            VibrationEffectSegment.checkFrequencyArgument(this.mFrequencyHz, "frequencyHz");
        } else if (Float.compare(this.mFrequencyHz, 0.0f) != 0) {
            throw new IllegalArgumentException("frequency must be default when amplitude is set to default");
        }
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public StepSegment resolve(int defaultAmplitude) {
        if (defaultAmplitude > 255 || defaultAmplitude <= 0) {
            throw new IllegalArgumentException("amplitude must be between 1 and 255 inclusive (amplitude=" + defaultAmplitude + NavigationBarInflaterView.KEY_CODE_END);
        }
        if (Float.compare(this.mAmplitude, -1.0f) != 0) {
            return this;
        }
        return new StepSegment(defaultAmplitude / 255.0f, this.mFrequencyHz, this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public StepSegment scale(float scaleFactor) {
        if (Float.compare(this.mAmplitude, -1.0f) == 0) {
            return this;
        }
        float newAmplitude = VibrationEffect.scale(this.mAmplitude, scaleFactor);
        if (Float.compare(newAmplitude, this.mAmplitude) == 0) {
            return this;
        }
        return new StepSegment(newAmplitude, this.mFrequencyHz, this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public StepSegment scaleLinearly(float scaleFactor) {
        if (Float.compare(this.mAmplitude, -1.0f) == 0) {
            return this;
        }
        float newAmplitude = VibrationEffect.scaleLinearly(this.mAmplitude, scaleFactor);
        if (Float.compare(newAmplitude, this.mAmplitude) == 0) {
            return this;
        }
        return new StepSegment(newAmplitude, this.mFrequencyHz, this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public StepSegment applyEffectStrength(int effectStrength) {
        return this;
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.mAmplitude), Float.valueOf(this.mFrequencyHz), Integer.valueOf(this.mDuration));
    }

    public String toString() {
        return "Step{amplitude=" + this.mAmplitude + ", frequencyHz=" + this.mFrequencyHz + ", duration=" + this.mDuration + "}";
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public String toDebugString() {
        return String.format("Step=%dms(amplitude=%.2f%s)", Integer.valueOf(this.mDuration), Float.valueOf(this.mAmplitude), Float.compare(this.mFrequencyHz, 0.0f) == 0 ? "" : " @ " + this.mFrequencyHz + "Hz");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(3);
        out.writeFloat(this.mAmplitude);
        out.writeFloat(this.mFrequencyHz);
        out.writeInt(this.mDuration);
    }
}
