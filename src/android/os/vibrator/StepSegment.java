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
        public StepSegment createFromParcel(Parcel parcel) {
            parcel.readInt();
            return new StepSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StepSegment[] newArray(int i) {
            return new StepSegment[i];
        }
    };
    private final float mAmplitude;
    private final int mDuration;
    private final float mFrequencyHz;

    @Override // android.os.vibrator.VibrationEffectSegment
    public StepSegment applyEffectStrength(int i) {
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

    StepSegment(Parcel parcel) {
        this(parcel.readFloat(), parcel.readFloat(), parcel.readInt());
    }

    public StepSegment(float f, float f2, int i) {
        this.mAmplitude = f;
        this.mFrequencyHz = f2;
        this.mDuration = i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof StepSegment)) {
            return false;
        }
        StepSegment stepSegment = (StepSegment) obj;
        return Float.compare(this.mAmplitude, stepSegment.mAmplitude) == 0 && Float.compare(this.mFrequencyHz, stepSegment.mFrequencyHz) == 0 && this.mDuration == stepSegment.mDuration;
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
        boolean zHasFrequencyControl = frequencyRequiresFrequencyControl(this.mFrequencyHz) ? vibratorInfo.hasFrequencyControl() : true;
        return amplitudeRequiresAmplitudeControl(this.mAmplitude) ? vibratorInfo.hasAmplitudeControl() & zHasFrequencyControl : zHasFrequencyControl;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public void validate() {
        VibrationEffectSegment.checkFrequencyArgument(this.mFrequencyHz, XmlConstants.ATTRIBUTE_FREQUENCY_HZ);
        VibrationEffectSegment.checkDurationArgument(this.mDuration, "duration");
        if (Float.compare(this.mAmplitude, -1.0f) != 0) {
            Preconditions.checkArgumentInRange(this.mAmplitude, 0.0f, 1.0f, XmlConstants.ATTRIBUTE_AMPLITUDE);
            VibrationEffectSegment.checkFrequencyArgument(this.mFrequencyHz, XmlConstants.ATTRIBUTE_FREQUENCY_HZ);
        } else if (Float.compare(this.mFrequencyHz, 0.0f) != 0) {
            throw new IllegalArgumentException("frequency must be default when amplitude is set to default");
        }
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public StepSegment resolve(int i) {
        if (i <= 255 && i > 0) {
            return Float.compare(this.mAmplitude, -1.0f) != 0 ? this : new StepSegment(i / 255.0f, this.mFrequencyHz, this.mDuration);
        }
        throw new IllegalArgumentException("amplitude must be between 1 and 255 inclusive (amplitude=" + i + NavigationBarInflaterView.KEY_CODE_END);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public StepSegment scale(float f) {
        if (Float.compare(this.mAmplitude, -1.0f) != 0) {
            float fScale = VibrationEffect.scale(this.mAmplitude, f);
            if (Float.compare(fScale, this.mAmplitude) != 0) {
                return new StepSegment(fScale, this.mFrequencyHz, this.mDuration);
            }
        }
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public StepSegment scaleLinearly(float f) {
        if (Float.compare(this.mAmplitude, -1.0f) != 0) {
            float fScaleLinearly = VibrationEffect.scaleLinearly(this.mAmplitude, f);
            if (Float.compare(fScaleLinearly, this.mAmplitude) != 0) {
                return new StepSegment(fScaleLinearly, this.mFrequencyHz, this.mDuration);
            }
        }
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
        String str;
        Integer numValueOf = Integer.valueOf(this.mDuration);
        Float fValueOf = Float.valueOf(this.mAmplitude);
        if (Float.compare(this.mFrequencyHz, 0.0f) == 0) {
            str = "";
        } else {
            str = " @ " + this.mFrequencyHz + "Hz";
        }
        return String.format("Step=%dms(amplitude=%.2f%s)", numValueOf, fValueOf, str);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(3);
        parcel.writeFloat(this.mAmplitude);
        parcel.writeFloat(this.mFrequencyHz);
        parcel.writeInt(this.mDuration);
    }
}
