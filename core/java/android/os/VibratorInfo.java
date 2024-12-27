package android.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.security.keystore.KeyProperties;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.util.Range;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import com.android.internal.util.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class VibratorInfo implements Parcelable {
    private static final String TAG = "VibratorInfo";
    private final long mCapabilities;
    private final int mCompositionSizeMax;
    private final FrequencyProfile mFrequencyProfile;
    private final int mId;
    private final int mPrimitiveDelayMax;
    private final int mPwlePrimitiveDurationMax;
    private final int mPwleSizeMax;
    private final float mQFactor;
    private final SparseBooleanArray mSupportedBraking;
    private final SparseBooleanArray mSupportedEffects;
    private final SparseIntArray mSupportedPrimitives;
    public static final VibratorInfo EMPTY_VIBRATOR_INFO = new Builder(-1).build();
    public static final Parcelable.Creator<VibratorInfo> CREATOR =
            new Parcelable.Creator<VibratorInfo>() { // from class: android.os.VibratorInfo.1
                @Override // android.os.Parcelable.Creator
                public VibratorInfo createFromParcel(Parcel in) {
                    return new VibratorInfo(in);
                }

                @Override // android.os.Parcelable.Creator
                public VibratorInfo[] newArray(int size) {
                    return new VibratorInfo[size];
                }
            };

    VibratorInfo(Parcel in) {
        this.mId = in.readInt();
        this.mCapabilities = in.readLong();
        this.mSupportedEffects = in.readSparseBooleanArray();
        this.mSupportedBraking = in.readSparseBooleanArray();
        this.mSupportedPrimitives = in.readSparseIntArray();
        this.mPrimitiveDelayMax = in.readInt();
        this.mCompositionSizeMax = in.readInt();
        this.mPwlePrimitiveDurationMax = in.readInt();
        this.mPwleSizeMax = in.readInt();
        this.mQFactor = in.readFloat();
        this.mFrequencyProfile = FrequencyProfile.CREATOR.createFromParcel(in);
    }

    public VibratorInfo(int id, VibratorInfo baseVibratorInfo) {
        this(
                id,
                baseVibratorInfo.mCapabilities,
                baseVibratorInfo.mSupportedEffects,
                baseVibratorInfo.mSupportedBraking,
                baseVibratorInfo.mSupportedPrimitives,
                baseVibratorInfo.mPrimitiveDelayMax,
                baseVibratorInfo.mCompositionSizeMax,
                baseVibratorInfo.mPwlePrimitiveDurationMax,
                baseVibratorInfo.mPwleSizeMax,
                baseVibratorInfo.mQFactor,
                baseVibratorInfo.mFrequencyProfile);
    }

    public VibratorInfo(
            int id,
            long capabilities,
            SparseBooleanArray supportedEffects,
            SparseBooleanArray supportedBraking,
            SparseIntArray supportedPrimitives,
            int primitiveDelayMax,
            int compositionSizeMax,
            int pwlePrimitiveDurationMax,
            int pwleSizeMax,
            float qFactor,
            FrequencyProfile frequencyProfile) {
        Preconditions.checkNotNull(supportedPrimitives);
        Preconditions.checkNotNull(frequencyProfile);
        this.mId = id;
        this.mCapabilities = capabilities;
        this.mSupportedEffects = supportedEffects == null ? null : supportedEffects.m5235clone();
        this.mSupportedBraking = supportedBraking != null ? supportedBraking.m5235clone() : null;
        this.mSupportedPrimitives = supportedPrimitives.m5237clone();
        this.mPrimitiveDelayMax = primitiveDelayMax;
        this.mCompositionSizeMax = compositionSizeMax;
        this.mPwlePrimitiveDurationMax = pwlePrimitiveDurationMax;
        this.mPwleSizeMax = pwleSizeMax;
        this.mQFactor = qFactor;
        this.mFrequencyProfile = frequencyProfile;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeLong(this.mCapabilities);
        dest.writeSparseBooleanArray(this.mSupportedEffects);
        dest.writeSparseBooleanArray(this.mSupportedBraking);
        dest.writeSparseIntArray(this.mSupportedPrimitives);
        dest.writeInt(this.mPrimitiveDelayMax);
        dest.writeInt(this.mCompositionSizeMax);
        dest.writeInt(this.mPwlePrimitiveDurationMax);
        dest.writeInt(this.mPwleSizeMax);
        dest.writeFloat(this.mQFactor);
        this.mFrequencyProfile.writeToParcel(dest, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VibratorInfo)) {
            return false;
        }
        VibratorInfo that = (VibratorInfo) o;
        return this.mId == that.mId && equalContent(that);
    }

    public boolean equalContent(VibratorInfo that) {
        int supportedPrimitivesCount = this.mSupportedPrimitives.size();
        if (supportedPrimitivesCount != that.mSupportedPrimitives.size()) {
            return false;
        }
        for (int i = 0; i < supportedPrimitivesCount; i++) {
            if (this.mSupportedPrimitives.keyAt(i) != that.mSupportedPrimitives.keyAt(i)
                    || this.mSupportedPrimitives.valueAt(i)
                            != that.mSupportedPrimitives.valueAt(i)) {
                return false;
            }
        }
        return this.mCapabilities == that.mCapabilities
                && this.mPrimitiveDelayMax == that.mPrimitiveDelayMax
                && this.mCompositionSizeMax == that.mCompositionSizeMax
                && this.mPwlePrimitiveDurationMax == that.mPwlePrimitiveDurationMax
                && this.mPwleSizeMax == that.mPwleSizeMax
                && Objects.equals(this.mSupportedEffects, that.mSupportedEffects)
                && Objects.equals(this.mSupportedBraking, that.mSupportedBraking)
                && Objects.equals(Float.valueOf(this.mQFactor), Float.valueOf(that.mQFactor))
                && Objects.equals(this.mFrequencyProfile, that.mFrequencyProfile);
    }

    public int hashCode() {
        int hashCode =
                Objects.hash(
                        Integer.valueOf(this.mId),
                        Long.valueOf(this.mCapabilities),
                        this.mSupportedEffects,
                        this.mSupportedBraking,
                        Float.valueOf(this.mQFactor),
                        this.mFrequencyProfile);
        for (int i = 0; i < this.mSupportedPrimitives.size(); i++) {
            hashCode =
                    (((hashCode * 31) + this.mSupportedPrimitives.keyAt(i)) * 31)
                            + this.mSupportedPrimitives.valueAt(i);
        }
        return hashCode;
    }

    public String toString() {
        return "VibratorInfo{mId="
                + this.mId
                + ", mCapabilities="
                + Arrays.toString(getCapabilitiesNames())
                + ", mCapabilities flags="
                + Long.toBinaryString(this.mCapabilities)
                + ", mSupportedEffects="
                + Arrays.toString(getSupportedEffectsNames())
                + ", mSupportedBraking="
                + Arrays.toString(getSupportedBrakingNames())
                + ", mSupportedPrimitives="
                + Arrays.toString(getSupportedPrimitivesNames())
                + ", mPrimitiveDelayMax="
                + this.mPrimitiveDelayMax
                + ", mCompositionSizeMax="
                + this.mCompositionSizeMax
                + ", mPwlePrimitiveDurationMax="
                + this.mPwlePrimitiveDurationMax
                + ", mPwleSizeMax="
                + this.mPwleSizeMax
                + ", mQFactor="
                + this.mQFactor
                + ", mFrequencyProfile="
                + this.mFrequencyProfile
                + '}';
    }

    public void dump(IndentingPrintWriter pw) {
        pw.println("VibratorInfo:");
        pw.increaseIndent();
        pw.println("id = " + this.mId);
        pw.println("capabilities = " + Arrays.toString(getCapabilitiesNames()));
        pw.println("capabilitiesFlags = " + Long.toBinaryString(this.mCapabilities));
        pw.println("supportedEffects = " + Arrays.toString(getSupportedEffectsNames()));
        pw.println("supportedPrimitives = " + Arrays.toString(getSupportedPrimitivesNames()));
        pw.println("supportedBraking = " + Arrays.toString(getSupportedBrakingNames()));
        pw.println("primitiveDelayMax = " + this.mPrimitiveDelayMax);
        pw.println("compositionSizeMax = " + this.mCompositionSizeMax);
        pw.println("pwlePrimitiveDurationMax = " + this.mPwlePrimitiveDurationMax);
        pw.println("pwleSizeMax = " + this.mPwleSizeMax);
        pw.println("q-factor = " + this.mQFactor);
        pw.println("frequencyProfile = " + this.mFrequencyProfile);
        pw.decreaseIndent();
    }

    public int getId() {
        return this.mId;
    }

    public boolean hasAmplitudeControl() {
        return hasCapability(4L);
    }

    public boolean hasFrequencyControl() {
        return hasCapability(1536L);
    }

    public int getDefaultBraking() {
        if (this.mSupportedBraking != null) {
            int size = this.mSupportedBraking.size();
            for (int i = 0; i < size; i++) {
                if (this.mSupportedBraking.keyAt(i) != 0) {
                    return this.mSupportedBraking.keyAt(i);
                }
            }
            return 0;
        }
        return 0;
    }

    public SparseBooleanArray getSupportedBraking() {
        if (this.mSupportedBraking == null) {
            return null;
        }
        return this.mSupportedBraking.m5235clone();
    }

    public boolean isBrakingSupportKnown() {
        return this.mSupportedBraking != null;
    }

    public boolean hasBrakingSupport(int braking) {
        return this.mSupportedBraking != null && this.mSupportedBraking.get(braking);
    }

    public boolean isEffectSupportKnown() {
        return this.mSupportedEffects != null;
    }

    public int isEffectSupported(int effectId) {
        if (this.mSupportedEffects == null) {
            return 0;
        }
        return this.mSupportedEffects.get(effectId) ? 1 : 2;
    }

    public SparseBooleanArray getSupportedEffects() {
        if (this.mSupportedEffects == null) {
            return null;
        }
        return this.mSupportedEffects.m5235clone();
    }

    public boolean isPrimitiveSupported(int primitiveId) {
        return hasCapability(32L) && this.mSupportedPrimitives.indexOfKey(primitiveId) >= 0;
    }

    public boolean areVibrationFeaturesSupported(VibrationEffect effect) {
        return effect.areVibrationFeaturesSupported(this);
    }

    public int getPrimitiveDuration(int primitiveId) {
        return this.mSupportedPrimitives.get(primitiveId);
    }

    public SparseIntArray getSupportedPrimitives() {
        return this.mSupportedPrimitives.m5237clone();
    }

    public int getPrimitiveDelayMax() {
        return this.mPrimitiveDelayMax;
    }

    public int getCompositionSizeMax() {
        return this.mCompositionSizeMax;
    }

    public int getPwlePrimitiveDurationMax() {
        return this.mPwlePrimitiveDurationMax;
    }

    public int getPwleSizeMax() {
        return this.mPwleSizeMax;
    }

    public boolean hasCapability(long capability) {
        return (this.mCapabilities & capability) == capability;
    }

    public float getResonantFrequencyHz() {
        return this.mFrequencyProfile.mResonantFrequencyHz;
    }

    public float getQFactor() {
        return this.mQFactor;
    }

    public FrequencyProfile getFrequencyProfile() {
        return this.mFrequencyProfile;
    }

    public long getCapabilities() {
        return this.mCapabilities;
    }

    private String[] getCapabilitiesNames() {
        List<String> names = new ArrayList<>();
        if (hasCapability(1L)) {
            names.add("ON_CALLBACK");
        }
        if (hasCapability(2L)) {
            names.add("PERFORM_CALLBACK");
        }
        if (hasCapability(32L)) {
            names.add("COMPOSE_EFFECTS");
        }
        if (hasCapability(1024L)) {
            names.add("COMPOSE_PWLE_EFFECTS");
        }
        if (hasCapability(64L)) {
            names.add("ALWAYS_ON_CONTROL");
        }
        if (hasCapability(4L)) {
            names.add("AMPLITUDE_CONTROL");
        }
        if (hasCapability(512L)) {
            names.add("FREQUENCY_CONTROL");
        }
        if (hasCapability(8L)) {
            names.add("EXTERNAL_CONTROL");
        }
        if (hasCapability(16L)) {
            names.add("EXTERNAL_AMPLITUDE_CONTROL");
        }
        return (String[]) names.toArray(new String[names.size()]);
    }

    private String[] getSupportedEffectsNames() {
        if (this.mSupportedEffects == null) {
            return new String[0];
        }
        String[] names = new String[this.mSupportedEffects.size()];
        for (int i = 0; i < this.mSupportedEffects.size(); i++) {
            names[i] = VibrationEffect.effectIdToString(this.mSupportedEffects.keyAt(i));
        }
        return names;
    }

    private String[] getSupportedBrakingNames() {
        if (this.mSupportedBraking == null) {
            return new String[0];
        }
        String[] names = new String[this.mSupportedBraking.size()];
        for (int i = 0; i < this.mSupportedBraking.size(); i++) {
            switch (this.mSupportedBraking.keyAt(i)) {
                case 0:
                    names[i] = KeyProperties.DIGEST_NONE;
                    break;
                case 1:
                    names[i] = "CLAB";
                    break;
                default:
                    names[i] = Integer.toString(this.mSupportedBraking.keyAt(i));
                    break;
            }
        }
        return names;
    }

    private String[] getSupportedPrimitivesNames() {
        int supportedPrimitivesCount = this.mSupportedPrimitives.size();
        String[] names = new String[supportedPrimitivesCount];
        for (int i = 0; i < supportedPrimitivesCount; i++) {
            names[i] =
                    VibrationEffect.Composition.primitiveToString(
                                    this.mSupportedPrimitives.keyAt(i))
                            + NavigationBarInflaterView.KEY_CODE_START
                            + this.mSupportedPrimitives.valueAt(i)
                            + "ms)";
        }
        return names;
    }

    public static final class FrequencyProfile implements Parcelable {
        public static final Parcelable.Creator<FrequencyProfile> CREATOR =
                new Parcelable.Creator<
                        FrequencyProfile>() { // from class:
                                              // android.os.VibratorInfo.FrequencyProfile.1
                    @Override // android.os.Parcelable.Creator
                    public FrequencyProfile createFromParcel(Parcel in) {
                        return new FrequencyProfile(in);
                    }

                    @Override // android.os.Parcelable.Creator
                    public FrequencyProfile[] newArray(int size) {
                        return new FrequencyProfile[size];
                    }
                };
        private final Range<Float> mFrequencyRangeHz;
        private final float mFrequencyResolutionHz;
        private final float[] mMaxAmplitudes;
        private final float mMinFrequencyHz;
        private final float mResonantFrequencyHz;

        FrequencyProfile(Parcel in) {
            this(in.readFloat(), in.readFloat(), in.readFloat(), in.createFloatArray());
        }

        public FrequencyProfile(
                float resonantFrequencyHz,
                float minFrequencyHz,
                float frequencyResolutionHz,
                float[] maxAmplitudes) {
            float maxFrequencyHz;
            this.mMinFrequencyHz = minFrequencyHz;
            this.mResonantFrequencyHz = resonantFrequencyHz;
            this.mFrequencyResolutionHz = frequencyResolutionHz;
            boolean z = false;
            this.mMaxAmplitudes = new float[maxAmplitudes == null ? 0 : maxAmplitudes.length];
            if (maxAmplitudes != null) {
                System.arraycopy(maxAmplitudes, 0, this.mMaxAmplitudes, 0, maxAmplitudes.length);
            }
            boolean isValid =
                    !Float.isNaN(resonantFrequencyHz)
                            && resonantFrequencyHz > 0.0f
                            && !Float.isNaN(minFrequencyHz)
                            && minFrequencyHz > 0.0f
                            && !Float.isNaN(frequencyResolutionHz)
                            && frequencyResolutionHz > 0.0f
                            && this.mMaxAmplitudes.length > 0;
            for (int i = 0; i < this.mMaxAmplitudes.length; i++) {
                isValid &= this.mMaxAmplitudes[i] >= 0.0f && this.mMaxAmplitudes[i] <= 1.0f;
            }
            if (isValid) {
                maxFrequencyHz =
                        ((this.mMaxAmplitudes.length - 1) * frequencyResolutionHz) + minFrequencyHz;
            } else {
                maxFrequencyHz = Float.NaN;
            }
            if (!Float.isNaN(maxFrequencyHz)
                    && resonantFrequencyHz >= minFrequencyHz
                    && resonantFrequencyHz <= maxFrequencyHz
                    && minFrequencyHz < maxFrequencyHz) {
                z = true;
            }
            this.mFrequencyRangeHz =
                    z & isValid
                            ? Range.create(
                                    Float.valueOf(minFrequencyHz), Float.valueOf(maxFrequencyHz))
                            : null;
        }

        public boolean isEmpty() {
            return this.mFrequencyRangeHz == null;
        }

        public Range<Float> getFrequencyRangeHz() {
            return this.mFrequencyRangeHz;
        }

        public float getMaxAmplitude(float frequencyHz) {
            if (isEmpty()
                    || Float.isNaN(frequencyHz)
                    || !this.mFrequencyRangeHz.contains(
                            (Range<Float>) Float.valueOf(frequencyHz))) {
                return 0.0f;
            }
            float mappingFreq = frequencyHz - this.mMinFrequencyHz;
            int startIdx =
                    MathUtils.constrain(
                            (int) Math.floor(mappingFreq / this.mFrequencyResolutionHz),
                            0,
                            this.mMaxAmplitudes.length - 1);
            int nextIdx = MathUtils.constrain(startIdx + 1, 0, this.mMaxAmplitudes.length - 1);
            return MathUtils.constrainedMap(
                    this.mMaxAmplitudes[startIdx],
                    this.mMaxAmplitudes[nextIdx],
                    startIdx * this.mFrequencyResolutionHz,
                    nextIdx * this.mFrequencyResolutionHz,
                    mappingFreq);
        }

        public float[] getMaxAmplitudes() {
            return Arrays.copyOf(this.mMaxAmplitudes, this.mMaxAmplitudes.length);
        }

        public float getFrequencyResolutionHz() {
            return this.mFrequencyResolutionHz;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeFloat(this.mResonantFrequencyHz);
            dest.writeFloat(this.mMinFrequencyHz);
            dest.writeFloat(this.mFrequencyResolutionHz);
            dest.writeFloatArray(this.mMaxAmplitudes);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FrequencyProfile)) {
                return false;
            }
            FrequencyProfile that = (FrequencyProfile) o;
            return Float.compare(this.mMinFrequencyHz, that.mMinFrequencyHz) == 0
                    && Float.compare(this.mResonantFrequencyHz, that.mResonantFrequencyHz) == 0
                    && Float.compare(this.mFrequencyResolutionHz, that.mFrequencyResolutionHz) == 0
                    && Arrays.equals(this.mMaxAmplitudes, that.mMaxAmplitudes);
        }

        public int hashCode() {
            int hashCode =
                    Objects.hash(
                            Float.valueOf(this.mMinFrequencyHz),
                            Float.valueOf(this.mFrequencyResolutionHz),
                            Float.valueOf(this.mFrequencyResolutionHz));
            return (hashCode * 31) + Arrays.hashCode(this.mMaxAmplitudes);
        }

        public String toString() {
            return "FrequencyProfile{mFrequencyRange="
                    + this.mFrequencyRangeHz
                    + ", mMinFrequency="
                    + this.mMinFrequencyHz
                    + ", mResonantFrequency="
                    + this.mResonantFrequencyHz
                    + ", mFrequencyResolution="
                    + this.mFrequencyResolutionHz
                    + ", mMaxAmplitudes count="
                    + this.mMaxAmplitudes.length
                    + '}';
        }
    }

    public static final class Builder {
        private long mCapabilities;
        private int mCompositionSizeMax;
        private final int mId;
        private int mPrimitiveDelayMax;
        private int mPwlePrimitiveDurationMax;
        private int mPwleSizeMax;
        private SparseBooleanArray mSupportedBraking;
        private SparseBooleanArray mSupportedEffects;
        private SparseIntArray mSupportedPrimitives = new SparseIntArray();
        private float mQFactor = Float.NaN;
        private FrequencyProfile mFrequencyProfile =
                new FrequencyProfile(Float.NaN, Float.NaN, Float.NaN, null);

        public Builder(int id) {
            this.mId = id;
        }

        public Builder setCapabilities(long capabilities) {
            this.mCapabilities = capabilities;
            return this;
        }

        public Builder setSupportedEffects(int... supportedEffects) {
            this.mSupportedEffects = toSparseBooleanArray(supportedEffects);
            return this;
        }

        public Builder setSupportedBraking(int... supportedBraking) {
            this.mSupportedBraking = toSparseBooleanArray(supportedBraking);
            return this;
        }

        public Builder setPwlePrimitiveDurationMax(int pwlePrimitiveDurationMax) {
            this.mPwlePrimitiveDurationMax = pwlePrimitiveDurationMax;
            return this;
        }

        public Builder setPwleSizeMax(int pwleSizeMax) {
            this.mPwleSizeMax = pwleSizeMax;
            return this;
        }

        public Builder setSupportedPrimitive(int primitiveId, int duration) {
            this.mSupportedPrimitives.put(primitiveId, duration);
            return this;
        }

        public Builder setPrimitiveDelayMax(int primitiveDelayMax) {
            this.mPrimitiveDelayMax = primitiveDelayMax;
            return this;
        }

        public Builder setCompositionSizeMax(int compositionSizeMax) {
            this.mCompositionSizeMax = compositionSizeMax;
            return this;
        }

        public Builder setQFactor(float qFactor) {
            this.mQFactor = qFactor;
            return this;
        }

        public Builder setFrequencyProfile(FrequencyProfile frequencyProfile) {
            this.mFrequencyProfile = frequencyProfile;
            return this;
        }

        public VibratorInfo build() {
            return new VibratorInfo(
                    this.mId,
                    this.mCapabilities,
                    this.mSupportedEffects,
                    this.mSupportedBraking,
                    this.mSupportedPrimitives,
                    this.mPrimitiveDelayMax,
                    this.mCompositionSizeMax,
                    this.mPwlePrimitiveDurationMax,
                    this.mPwleSizeMax,
                    this.mQFactor,
                    this.mFrequencyProfile);
        }

        private static SparseBooleanArray toSparseBooleanArray(int[] supportedKeys) {
            if (supportedKeys == null) {
                return null;
            }
            SparseBooleanArray array = new SparseBooleanArray();
            for (int key : supportedKeys) {
                array.put(key, true);
            }
            return array;
        }
    }
}
