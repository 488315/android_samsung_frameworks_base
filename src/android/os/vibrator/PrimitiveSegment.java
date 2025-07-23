package android.os.vibrator;

import android.media.tv.interactive.TvInteractiveAppService;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.VibratorInfo;
import com.android.internal.util.Preconditions;
import com.android.internal.vibrator.persistence.XmlConstants;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class PrimitiveSegment extends VibrationEffectSegment {
    public static final Parcelable.Creator<PrimitiveSegment> CREATOR = new Parcelable.Creator<PrimitiveSegment>() { // from class: android.os.vibrator.PrimitiveSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrimitiveSegment createFromParcel(Parcel parcel) {
            parcel.readInt();
            return new PrimitiveSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrimitiveSegment[] newArray(int i) {
            return new PrimitiveSegment[i];
        }
    };
    public static final int DEFAULT_DELAY_MILLIS = 0;
    public static final int DEFAULT_DELAY_TYPE = 0;
    public static final float DEFAULT_SCALE = 1.0f;
    private final int mDelay;
    private final int mDelayType;
    private final int mPrimitiveId;
    private final float mScale;

    private static boolean isValidDelayType(int i) {
        return i == 0 || i == 1;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public PrimitiveSegment applyEffectStrength(int i) {
        return this;
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
    public boolean isHapticFeedbackCandidate() {
        return true;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public PrimitiveSegment resolve(int i) {
        return this;
    }

    PrimitiveSegment(Parcel parcel) {
        this(parcel.readInt(), parcel.readFloat(), parcel.readInt(), parcel.readInt());
    }

    public PrimitiveSegment(int i, float f, int i2) {
        this(i, f, i2, 0);
    }

    public PrimitiveSegment(int i, float f, int i2, int i3) {
        this.mPrimitiveId = i;
        this.mScale = f;
        this.mDelay = i2;
        this.mDelayType = i3;
    }

    public int getPrimitiveId() {
        return this.mPrimitiveId;
    }

    public float getScale() {
        return this.mScale;
    }

    public int getDelay() {
        return this.mDelay;
    }

    public int getDelayType() {
        return this.mDelayType;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public long getDuration(VibratorInfo vibratorInfo) {
        if (vibratorInfo == null) {
            return getDuration();
        }
        return vibratorInfo.getPrimitiveDuration(this.mPrimitiveId) > 0 ? r2 + this.mDelay : getDuration();
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(VibratorInfo vibratorInfo) {
        return vibratorInfo.isPrimitiveSupported(this.mPrimitiveId);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public PrimitiveSegment scale(float f) {
        float scale = VibrationEffect.scale(this.mScale, f);
        return Float.compare(this.mScale, scale) == 0 ? this : new PrimitiveSegment(this.mPrimitiveId, scale, this.mDelay, this.mDelayType);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public PrimitiveSegment scaleLinearly(float f) {
        float scaleLinearly = VibrationEffect.scaleLinearly(this.mScale, f);
        return Float.compare(this.mScale, scaleLinearly) == 0 ? this : new PrimitiveSegment(this.mPrimitiveId, scaleLinearly, this.mDelay, this.mDelayType);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public void validate() {
        Preconditions.checkArgumentInRange(this.mPrimitiveId, 0, 8, "primitiveId");
        Preconditions.checkArgumentInRange(this.mScale, 0.0f, 1.0f, "scale");
        VibrationEffectSegment.checkDurationArgument(this.mDelay, "delay");
        Preconditions.checkArgument(isValidDelayType(this.mDelayType), XmlConstants.ATTRIBUTE_DELAY_TYPE);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(2);
        parcel.writeInt(this.mPrimitiveId);
        parcel.writeFloat(this.mScale);
        parcel.writeInt(this.mDelay);
        parcel.writeInt(this.mDelayType);
    }

    public String toString() {
        return "Primitive{primitive=" + VibrationEffect.Composition.primitiveToString(this.mPrimitiveId) + ", scale=" + this.mScale + ", delay=" + this.mDelay + ", delayType=" + VibrationEffect.Composition.delayTypeToString(this.mDelayType) + '}';
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public String toDebugString() {
        return String.format(Locale.ROOT, "Primitive=%s(scale=%.2f, %s=%dms)", VibrationEffect.Composition.primitiveToString(this.mPrimitiveId), Float.valueOf(this.mScale), toDelayTypeDebugString(this.mDelayType), Integer.valueOf(this.mDelay));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PrimitiveSegment primitiveSegment = (PrimitiveSegment) obj;
            if (this.mPrimitiveId == primitiveSegment.mPrimitiveId && Float.compare(primitiveSegment.mScale, this.mScale) == 0 && this.mDelay == primitiveSegment.mDelay && this.mDelayType == primitiveSegment.mDelayType) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPrimitiveId), Float.valueOf(this.mScale), Integer.valueOf(this.mDelay), Integer.valueOf(this.mDelayType));
    }

    private static String toDelayTypeDebugString(int i) {
        if (i == 1) {
            return "startOffset";
        }
        return TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PAUSE;
    }
}
