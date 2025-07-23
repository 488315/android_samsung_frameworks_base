package android.os.vibrator;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.VibratorInfo;
import com.android.internal.util.Preconditions;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class BasicPwleSegment extends VibrationEffectSegment {
    public static final Parcelable.Creator<BasicPwleSegment> CREATOR = new Parcelable.Creator<BasicPwleSegment>() { // from class: android.os.vibrator.BasicPwleSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BasicPwleSegment createFromParcel(Parcel parcel) {
            parcel.readInt();
            return new BasicPwleSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BasicPwleSegment[] newArray(int i) {
            return new BasicPwleSegment[i];
        }
    };
    private final long mDuration;
    private final float mEndIntensity;
    private final float mEndSharpness;
    private final float mStartIntensity;
    private final float mStartSharpness;

    @Override // android.os.vibrator.VibrationEffectSegment
    public BasicPwleSegment applyEffectStrength(int i) {
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
    public BasicPwleSegment resolve(int i) {
        return this;
    }

    BasicPwleSegment(Parcel parcel) {
        this(parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readLong());
    }

    public BasicPwleSegment(float f, float f2, float f3, float f4, long j) {
        this.mStartIntensity = f;
        this.mEndIntensity = f2;
        this.mStartSharpness = f3;
        this.mEndSharpness = f4;
        this.mDuration = j;
    }

    public float getStartIntensity() {
        return this.mStartIntensity;
    }

    public float getEndIntensity() {
        return this.mEndIntensity;
    }

    public float getStartSharpness() {
        return this.mStartSharpness;
    }

    public float getEndSharpness() {
        return this.mEndSharpness;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public long getDuration() {
        return this.mDuration;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BasicPwleSegment)) {
            return false;
        }
        BasicPwleSegment basicPwleSegment = (BasicPwleSegment) obj;
        return Float.compare(this.mStartIntensity, basicPwleSegment.mStartIntensity) == 0 && Float.compare(this.mEndIntensity, basicPwleSegment.mEndIntensity) == 0 && Float.compare(this.mStartSharpness, basicPwleSegment.mStartSharpness) == 0 && Float.compare(this.mEndSharpness, basicPwleSegment.mEndSharpness) == 0 && this.mDuration == basicPwleSegment.mDuration;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(VibratorInfo vibratorInfo) {
        return vibratorInfo.areEnvelopeEffectsSupported();
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public void validate() {
        Preconditions.checkArgumentInRange(this.mStartSharpness, 0.0f, 1.0f, "startSharpness");
        Preconditions.checkArgumentInRange(this.mEndSharpness, 0.0f, 1.0f, "endSharpness");
        Preconditions.checkArgumentInRange(this.mStartIntensity, 0.0f, 1.0f, "startIntensity");
        Preconditions.checkArgumentInRange(this.mEndIntensity, 0.0f, 1.0f, "endIntensity");
        Preconditions.checkArgumentPositive(this.mDuration, "Time must be greater than zero.");
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public BasicPwleSegment scale(float f) {
        float scale = VibrationEffect.scale(this.mStartIntensity, f);
        float scale2 = VibrationEffect.scale(this.mEndIntensity, f);
        return (Float.compare(this.mStartIntensity, scale) == 0 && Float.compare(this.mEndIntensity, scale2) == 0) ? this : new BasicPwleSegment(scale, scale2, this.mStartSharpness, this.mEndSharpness, this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public BasicPwleSegment scaleLinearly(float f) {
        float scaleLinearly = VibrationEffect.scaleLinearly(this.mStartIntensity, f);
        float scaleLinearly2 = VibrationEffect.scaleLinearly(this.mEndIntensity, f);
        return (Float.compare(this.mStartIntensity, scaleLinearly) == 0 && Float.compare(this.mEndIntensity, scaleLinearly2) == 0) ? this : new BasicPwleSegment(scaleLinearly, scaleLinearly2, this.mStartSharpness, this.mEndSharpness, this.mDuration);
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.mStartIntensity), Float.valueOf(this.mEndIntensity), Float.valueOf(this.mStartSharpness), Float.valueOf(this.mEndSharpness), Long.valueOf(this.mDuration));
    }

    public String toString() {
        return "BasicPwle{startIntensity=" + this.mStartIntensity + ", endIntensity=" + this.mEndIntensity + ", startSharpness=" + this.mStartSharpness + ", endSharpness=" + this.mEndSharpness + ", duration=" + this.mDuration + "}";
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public String toDebugString() {
        return String.format(Locale.US, "Pwle=%dms(intensity=%.2f @ %.2f to %.2f @ %.2f)", Long.valueOf(this.mDuration), Float.valueOf(this.mStartIntensity), Float.valueOf(this.mStartSharpness), Float.valueOf(this.mEndIntensity), Float.valueOf(this.mEndSharpness));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(5);
        parcel.writeFloat(this.mStartIntensity);
        parcel.writeFloat(this.mEndIntensity);
        parcel.writeFloat(this.mStartSharpness);
        parcel.writeFloat(this.mEndSharpness);
        parcel.writeLong(this.mDuration);
    }
}
