package android.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.VibratorInfo;
import android.os.vibrator.Flags;
import android.security.keystore.KeyProperties;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.util.Pair;
import android.util.Range;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class VibratorInfo implements Parcelable {
    private static final String TAG = "VibratorInfo";
    private final long mCapabilities;
    private final int mCompositionSizeMax;
    private final FrequencyProfile mFrequencyProfile;
    private final FrequencyProfileLegacy mFrequencyProfileLegacy;
    private final int mId;
    private final int mMaxEnvelopeEffectControlPointDurationMillis;
    private final int mMaxEnvelopeEffectSize;
    private final int mMinEnvelopeEffectControlPointDurationMillis;
    private final int mPrimitiveDelayMax;
    private final int mPwlePrimitiveDurationMax;
    private final int mPwleSizeMax;
    private final float mQFactor;
    private final SparseBooleanArray mSupportedBraking;
    private final SparseBooleanArray mSupportedEffects;
    private final SparseIntArray mSupportedPrimitives;
    public static final VibratorInfo EMPTY_VIBRATOR_INFO = new Builder(-1).build();
    public static final Parcelable.Creator<VibratorInfo> CREATOR = new Parcelable.Creator<VibratorInfo>() { // from class: android.os.VibratorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VibratorInfo createFromParcel(Parcel parcel) {
            return new VibratorInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VibratorInfo[] newArray(int i) {
            return new VibratorInfo[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VibratorInfo(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mCapabilities = parcel.readLong();
        this.mSupportedEffects = parcel.readSparseBooleanArray();
        this.mSupportedBraking = parcel.readSparseBooleanArray();
        this.mSupportedPrimitives = parcel.readSparseIntArray();
        this.mPrimitiveDelayMax = parcel.readInt();
        this.mCompositionSizeMax = parcel.readInt();
        this.mPwlePrimitiveDurationMax = parcel.readInt();
        this.mPwleSizeMax = parcel.readInt();
        this.mQFactor = parcel.readFloat();
        this.mFrequencyProfileLegacy = FrequencyProfileLegacy.CREATOR.createFromParcel(parcel);
        this.mFrequencyProfile = FrequencyProfile.CREATOR.createFromParcel(parcel);
        this.mMaxEnvelopeEffectSize = parcel.readInt();
        this.mMinEnvelopeEffectControlPointDurationMillis = parcel.readInt();
        this.mMaxEnvelopeEffectControlPointDurationMillis = parcel.readInt();
    }

    public VibratorInfo(int i, VibratorInfo vibratorInfo) {
        this(i, vibratorInfo.mCapabilities, vibratorInfo.mSupportedEffects, vibratorInfo.mSupportedBraking, vibratorInfo.mSupportedPrimitives, vibratorInfo.mPrimitiveDelayMax, vibratorInfo.mCompositionSizeMax, vibratorInfo.mPwlePrimitiveDurationMax, vibratorInfo.mPwleSizeMax, vibratorInfo.mQFactor, vibratorInfo.mFrequencyProfileLegacy, vibratorInfo.mFrequencyProfile, vibratorInfo.mMaxEnvelopeEffectSize, vibratorInfo.mMinEnvelopeEffectControlPointDurationMillis, vibratorInfo.mMaxEnvelopeEffectControlPointDurationMillis);
    }

    public VibratorInfo(int i, long j, SparseBooleanArray sparseBooleanArray, SparseBooleanArray sparseBooleanArray2, SparseIntArray sparseIntArray, int i2, int i3, int i4, int i5, float f, FrequencyProfileLegacy frequencyProfileLegacy, FrequencyProfile frequencyProfile, int i6, int i7, int i8) {
        Preconditions.checkNotNull(sparseIntArray);
        Preconditions.checkNotNull(frequencyProfileLegacy);
        Preconditions.checkNotNull(frequencyProfile);
        this.mId = i;
        this.mCapabilities = j;
        this.mSupportedEffects = sparseBooleanArray == null ? null : sparseBooleanArray.m5530clone();
        this.mSupportedBraking = sparseBooleanArray2 != null ? sparseBooleanArray2.m5530clone() : null;
        this.mSupportedPrimitives = sparseIntArray.m5532clone();
        this.mPrimitiveDelayMax = i2;
        this.mCompositionSizeMax = i3;
        this.mPwlePrimitiveDurationMax = i4;
        this.mPwleSizeMax = i5;
        this.mQFactor = f;
        this.mFrequencyProfileLegacy = frequencyProfileLegacy;
        this.mFrequencyProfile = frequencyProfile;
        this.mMaxEnvelopeEffectSize = i6;
        this.mMinEnvelopeEffectControlPointDurationMillis = i7;
        this.mMaxEnvelopeEffectControlPointDurationMillis = i8;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeLong(this.mCapabilities);
        parcel.writeSparseBooleanArray(this.mSupportedEffects);
        parcel.writeSparseBooleanArray(this.mSupportedBraking);
        parcel.writeSparseIntArray(this.mSupportedPrimitives);
        parcel.writeInt(this.mPrimitiveDelayMax);
        parcel.writeInt(this.mCompositionSizeMax);
        parcel.writeInt(this.mPwlePrimitiveDurationMax);
        parcel.writeInt(this.mPwleSizeMax);
        parcel.writeFloat(this.mQFactor);
        this.mFrequencyProfileLegacy.writeToParcel(parcel, i);
        this.mFrequencyProfile.writeToParcel(parcel, i);
        parcel.writeInt(this.mMaxEnvelopeEffectSize);
        parcel.writeInt(this.mMinEnvelopeEffectControlPointDurationMillis);
        parcel.writeInt(this.mMaxEnvelopeEffectControlPointDurationMillis);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VibratorInfo)) {
            return false;
        }
        VibratorInfo vibratorInfo = (VibratorInfo) obj;
        return this.mId == vibratorInfo.mId && equalContent(vibratorInfo);
    }

    public boolean equalContent(VibratorInfo vibratorInfo) {
        int size = this.mSupportedPrimitives.size();
        if (size != vibratorInfo.mSupportedPrimitives.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.mSupportedPrimitives.keyAt(i) != vibratorInfo.mSupportedPrimitives.keyAt(i) || this.mSupportedPrimitives.valueAt(i) != vibratorInfo.mSupportedPrimitives.valueAt(i)) {
                return false;
            }
        }
        return this.mCapabilities == vibratorInfo.mCapabilities && this.mPrimitiveDelayMax == vibratorInfo.mPrimitiveDelayMax && this.mCompositionSizeMax == vibratorInfo.mCompositionSizeMax && this.mPwlePrimitiveDurationMax == vibratorInfo.mPwlePrimitiveDurationMax && this.mPwleSizeMax == vibratorInfo.mPwleSizeMax && Objects.equals(this.mSupportedEffects, vibratorInfo.mSupportedEffects) && Objects.equals(this.mSupportedBraking, vibratorInfo.mSupportedBraking) && Objects.equals(Float.valueOf(this.mQFactor), Float.valueOf(vibratorInfo.mQFactor)) && Objects.equals(this.mFrequencyProfileLegacy, vibratorInfo.mFrequencyProfileLegacy) && Objects.equals(this.mFrequencyProfile, vibratorInfo.mFrequencyProfile) && this.mMaxEnvelopeEffectSize == vibratorInfo.mMaxEnvelopeEffectSize && this.mMinEnvelopeEffectControlPointDurationMillis == vibratorInfo.mMinEnvelopeEffectControlPointDurationMillis && this.mMaxEnvelopeEffectControlPointDurationMillis == vibratorInfo.mMaxEnvelopeEffectControlPointDurationMillis;
    }

    public int hashCode() {
        int hash = Objects.hash(Integer.valueOf(this.mId), Long.valueOf(this.mCapabilities), this.mSupportedEffects, this.mSupportedBraking, Float.valueOf(this.mQFactor), this.mFrequencyProfileLegacy, this.mFrequencyProfile);
        for (int i = 0; i < this.mSupportedPrimitives.size(); i++) {
            hash = (((hash * 31) + this.mSupportedPrimitives.keyAt(i)) * 31) + this.mSupportedPrimitives.valueAt(i);
        }
        return hash;
    }

    public String toString() {
        return "VibratorInfo{mId=" + this.mId + ", mCapabilities=" + Arrays.toString(getCapabilitiesNames()) + ", mCapabilities flags=" + Long.toBinaryString(this.mCapabilities) + ", mSupportedEffects=" + Arrays.toString(getSupportedEffectsNames()) + ", mSupportedBraking=" + Arrays.toString(getSupportedBrakingNames()) + ", mSupportedPrimitives=" + Arrays.toString(getSupportedPrimitivesNames()) + ", mPrimitiveDelayMax=" + this.mPrimitiveDelayMax + ", mCompositionSizeMax=" + this.mCompositionSizeMax + ", mPwlePrimitiveDurationMax=" + this.mPwlePrimitiveDurationMax + ", mPwleSizeMax=" + this.mPwleSizeMax + ", mQFactor=" + this.mQFactor + ", mFrequencyProfileLegacy=" + this.mFrequencyProfileLegacy + ", mFrequencyProfile=" + this.mFrequencyProfile + ", mMaxEnvelopeEffectSize=" + this.mMaxEnvelopeEffectSize + ", mMinEnvelopeEffectControlPointDurationMillis=" + this.mMinEnvelopeEffectControlPointDurationMillis + ", mMaxEnvelopeEffectControlPointDurationMillis=" + this.mMaxEnvelopeEffectControlPointDurationMillis + '}';
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("VibratorInfo:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("id = " + this.mId);
        indentingPrintWriter.println("capabilities = " + Arrays.toString(getCapabilitiesNames()));
        indentingPrintWriter.println("capabilitiesFlags = " + Long.toBinaryString(this.mCapabilities));
        indentingPrintWriter.println("supportedEffects = " + Arrays.toString(getSupportedEffectsNames()));
        indentingPrintWriter.println("supportedPrimitives = " + Arrays.toString(getSupportedPrimitivesNames()));
        indentingPrintWriter.println("supportedBraking = " + Arrays.toString(getSupportedBrakingNames()));
        indentingPrintWriter.println("primitiveDelayMax = " + this.mPrimitiveDelayMax);
        indentingPrintWriter.println("compositionSizeMax = " + this.mCompositionSizeMax);
        indentingPrintWriter.println("pwlePrimitiveDurationMax = " + this.mPwlePrimitiveDurationMax);
        indentingPrintWriter.println("pwleSizeMax = " + this.mPwleSizeMax);
        indentingPrintWriter.println("q-factor = " + this.mQFactor);
        indentingPrintWriter.println("frequencyProfileLegacy = " + this.mFrequencyProfileLegacy);
        indentingPrintWriter.println("frequencyProfile = " + this.mFrequencyProfile);
        indentingPrintWriter.println("mMaxEnvelopeEffectSize = " + this.mMaxEnvelopeEffectSize);
        indentingPrintWriter.println("mMinEnvelopeEffectControlPointDurationMillis = " + this.mMinEnvelopeEffectControlPointDurationMillis);
        indentingPrintWriter.println("mMaxEnvelopeEffectControlPointDurationMillis = " + this.mMaxEnvelopeEffectControlPointDurationMillis);
        indentingPrintWriter.decreaseIndent();
    }

    public int getId() {
        return this.mId;
    }

    public boolean hasAmplitudeControl() {
        return hasCapability(4L);
    }

    public boolean hasFrequencyControl() {
        return hasCapability(512L);
    }

    public int getDefaultBraking() {
        SparseBooleanArray sparseBooleanArray = this.mSupportedBraking;
        if (sparseBooleanArray != null) {
            int size = sparseBooleanArray.size();
            for (int i = 0; i < size; i++) {
                if (this.mSupportedBraking.keyAt(i) != 0) {
                    return this.mSupportedBraking.keyAt(i);
                }
            }
        }
        return 0;
    }

    public SparseBooleanArray getSupportedBraking() {
        SparseBooleanArray sparseBooleanArray = this.mSupportedBraking;
        if (sparseBooleanArray == null) {
            return null;
        }
        return sparseBooleanArray.m5530clone();
    }

    public boolean isBrakingSupportKnown() {
        return this.mSupportedBraking != null;
    }

    public boolean hasBrakingSupport(int i) {
        SparseBooleanArray sparseBooleanArray = this.mSupportedBraking;
        return sparseBooleanArray != null && sparseBooleanArray.get(i);
    }

    public boolean isEffectSupportKnown() {
        return this.mSupportedEffects != null;
    }

    public int isEffectSupported(int i) {
        SparseBooleanArray sparseBooleanArray = this.mSupportedEffects;
        if (sparseBooleanArray == null) {
            return 0;
        }
        return sparseBooleanArray.get(i) ? 1 : 2;
    }

    public SparseBooleanArray getSupportedEffects() {
        SparseBooleanArray sparseBooleanArray = this.mSupportedEffects;
        if (sparseBooleanArray == null) {
            return null;
        }
        return sparseBooleanArray.m5530clone();
    }

    public boolean isPrimitiveSupported(int i) {
        return hasCapability(32L) && this.mSupportedPrimitives.indexOfKey(i) >= 0;
    }

    public boolean areVibrationFeaturesSupported(VibrationEffect vibrationEffect) {
        return vibrationEffect.areVibrationFeaturesSupported(this);
    }

    public int getPrimitiveDuration(int i) {
        return this.mSupportedPrimitives.get(i);
    }

    public SparseIntArray getSupportedPrimitives() {
        return this.mSupportedPrimitives.m5532clone();
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

    public boolean areEnvelopeEffectsSupported() {
        return hasCapability(4608L);
    }

    public int getMaxEnvelopeEffectDurationMillis() {
        return this.mMaxEnvelopeEffectSize * this.mMaxEnvelopeEffectControlPointDurationMillis;
    }

    public int getMaxEnvelopeEffectSize() {
        return this.mMaxEnvelopeEffectSize;
    }

    public int getMinEnvelopeEffectControlPointDurationMillis() {
        return this.mMinEnvelopeEffectControlPointDurationMillis;
    }

    public int getMaxEnvelopeEffectControlPointDurationMillis() {
        return this.mMaxEnvelopeEffectControlPointDurationMillis;
    }

    public boolean hasCapability(long j) {
        return (this.mCapabilities & j) == j;
    }

    public float getResonantFrequencyHz() {
        if (Flags.normalizedPwleEffects()) {
            return this.mFrequencyProfile.mResonantFrequencyHz;
        }
        return this.mFrequencyProfileLegacy.mResonantFrequencyHz;
    }

    public float getQFactor() {
        return this.mQFactor;
    }

    public FrequencyProfileLegacy getFrequencyProfileLegacy() {
        return this.mFrequencyProfileLegacy;
    }

    public FrequencyProfile getFrequencyProfile() {
        return this.mFrequencyProfile;
    }

    public long getCapabilities() {
        return this.mCapabilities;
    }

    private String[] getCapabilitiesNames() {
        ArrayList arrayList = new ArrayList();
        if (hasCapability(1L)) {
            arrayList.add("ON_CALLBACK");
        }
        if (hasCapability(2L)) {
            arrayList.add("PERFORM_CALLBACK");
        }
        if (hasCapability(32L)) {
            arrayList.add("COMPOSE_EFFECTS");
        }
        if (hasCapability(1024L)) {
            arrayList.add("COMPOSE_PWLE_EFFECTS");
        }
        if (hasCapability(64L)) {
            arrayList.add("ALWAYS_ON_CONTROL");
        }
        if (hasCapability(4L)) {
            arrayList.add("AMPLITUDE_CONTROL");
        }
        if (hasCapability(512L)) {
            arrayList.add("FREQUENCY_CONTROL");
        }
        if (hasCapability(8L)) {
            arrayList.add("EXTERNAL_CONTROL");
        }
        if (hasCapability(16L)) {
            arrayList.add("EXTERNAL_AMPLITUDE_CONTROL");
        }
        if (hasCapability(4096L)) {
            arrayList.add("CAP_COMPOSE_PWLE_EFFECTS_V2");
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private String[] getSupportedEffectsNames() {
        SparseBooleanArray sparseBooleanArray = this.mSupportedEffects;
        if (sparseBooleanArray == null) {
            return new String[0];
        }
        String[] strArr = new String[sparseBooleanArray.size()];
        for (int i = 0; i < this.mSupportedEffects.size(); i++) {
            strArr[i] = VibrationEffect.effectIdToString(this.mSupportedEffects.keyAt(i));
        }
        return strArr;
    }

    private String[] getSupportedBrakingNames() {
        SparseBooleanArray sparseBooleanArray = this.mSupportedBraking;
        if (sparseBooleanArray == null) {
            return new String[0];
        }
        String[] strArr = new String[sparseBooleanArray.size()];
        for (int i = 0; i < this.mSupportedBraking.size(); i++) {
            int keyAt = this.mSupportedBraking.keyAt(i);
            if (keyAt == 0) {
                strArr[i] = KeyProperties.DIGEST_NONE;
            } else if (keyAt == 1) {
                strArr[i] = "CLAB";
            } else {
                strArr[i] = Integer.toString(this.mSupportedBraking.keyAt(i));
            }
        }
        return strArr;
    }

    private String[] getSupportedPrimitivesNames() {
        int size = this.mSupportedPrimitives.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = VibrationEffect.Composition.primitiveToString(this.mSupportedPrimitives.keyAt(i)) + NavigationBarInflaterView.KEY_CODE_START + this.mSupportedPrimitives.valueAt(i) + "ms)";
        }
        return strArr;
    }

    public static final class FrequencyProfile implements Parcelable {
        public static final Parcelable.Creator<FrequencyProfile> CREATOR = new Parcelable.Creator<FrequencyProfile>() { // from class: android.os.VibratorInfo.FrequencyProfile.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FrequencyProfile createFromParcel(Parcel parcel) {
                return new FrequencyProfile(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FrequencyProfile[] newArray(int i) {
                return new FrequencyProfile[i];
            }
        };
        private final float[] mFrequenciesHz;
        private final float mMaxFrequencyHz;
        private final float mMaxOutputAccelerationGs;
        private final float mMinFrequencyHz;
        private final float[] mOutputAccelerationsGs;
        private final float mResonantFrequencyHz;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public FrequencyProfile(Parcel parcel) {
            this(parcel.readFloat(), parcel.createFloatArray(), parcel.createFloatArray());
        }

        public FrequencyProfile(float f, float[] fArr, float[] fArr2) {
            this.mResonantFrequencyHz = f;
            if (!Float.isNaN(f)) {
                float f2 = 0.0f;
                if (f > 0.0f && fArr != null && fArr2 != null && fArr.length == fArr2.length && fArr.length > 0) {
                    TreeMap treeMap = new TreeMap();
                    for (int i = 0; i < fArr.length; i++) {
                        treeMap.putIfAbsent(Float.valueOf(fArr[i]), Float.valueOf(fArr2[i]));
                    }
                    float[] fArr3 = new float[treeMap.size()];
                    float[] fArr4 = new float[treeMap.size()];
                    int i2 = 0;
                    for (Map.Entry entry : treeMap.entrySet()) {
                        fArr3[i2] = ((Float) entry.getKey()).floatValue();
                        fArr4[i2] = ((Float) entry.getValue()).floatValue();
                        f2 = Math.max(f2, ((Float) entry.getValue()).floatValue());
                        i2++;
                    }
                    this.mFrequenciesHz = fArr3;
                    this.mOutputAccelerationsGs = fArr4;
                    this.mMinFrequencyHz = fArr3[0];
                    this.mMaxFrequencyHz = fArr3[fArr3.length - 1];
                    this.mMaxOutputAccelerationGs = f2;
                    return;
                }
            }
            this.mFrequenciesHz = null;
            this.mOutputAccelerationsGs = null;
            this.mMinFrequencyHz = Float.NaN;
            this.mMaxFrequencyHz = Float.NaN;
            this.mMaxOutputAccelerationGs = Float.NaN;
        }

        public boolean isEmpty() {
            return this.mFrequenciesHz == null;
        }

        public float[] getFrequenciesHz() {
            return this.mFrequenciesHz;
        }

        public float[] getOutputAccelerationsGs() {
            return this.mOutputAccelerationsGs;
        }

        public float getMaxOutputAccelerationGs() {
            return this.mMaxOutputAccelerationGs;
        }

        public float getOutputAccelerationGs(float f) {
            float[] fArr = this.mFrequenciesHz;
            if (fArr == null) {
                return Float.NaN;
            }
            if (f < this.mMinFrequencyHz || f > this.mMaxFrequencyHz) {
                return 0.0f;
            }
            int binarySearch = Arrays.binarySearch(fArr, f);
            if (binarySearch >= 0) {
                return this.mOutputAccelerationsGs[binarySearch];
            }
            int i = -binarySearch;
            int i2 = i - 2;
            float[] fArr2 = this.mOutputAccelerationsGs;
            float f2 = fArr2[i2];
            int i3 = i - 1;
            float f3 = fArr2[i3];
            float[] fArr3 = this.mFrequenciesHz;
            return MathUtils.constrainedMap(f2, f3, fArr3[i2], fArr3[i3], f);
        }

        public float getMinFrequencyHz() {
            return this.mMinFrequencyHz;
        }

        public float getMaxFrequencyHz() {
            return this.mMaxFrequencyHz;
        }

        public Range<Float> getFrequencyRangeHz(float f) {
            float f2;
            float f3;
            if (this.mFrequenciesHz == null || this.mOutputAccelerationsGs == null || f > this.mMaxOutputAccelerationGs) {
                return null;
            }
            if (f <= 0.0f) {
                return new Range<>(Float.valueOf(this.mMinFrequencyHz), Float.valueOf(this.mMaxFrequencyHz));
            }
            int i = 0;
            while (true) {
                float[] fArr = this.mOutputAccelerationsGs;
                f2 = Float.NaN;
                if (i >= fArr.length) {
                    i = 0;
                    f3 = Float.NaN;
                    break;
                }
                float f4 = fArr[i];
                if (f4 < f) {
                    i++;
                } else if (i == 0) {
                    f3 = this.mMinFrequencyHz;
                } else {
                    float[] fArr2 = this.mFrequenciesHz;
                    int i2 = i - 1;
                    f3 = MathUtils.constrainedMap(fArr2[i2], fArr2[i], fArr[i2], f4, f);
                }
            }
            if (Float.isNaN(f3)) {
                return null;
            }
            while (true) {
                float[] fArr3 = this.mOutputAccelerationsGs;
                if (i >= fArr3.length) {
                    break;
                }
                float f5 = fArr3[i];
                if (f5 <= f) {
                    float[] fArr4 = this.mFrequenciesHz;
                    int i3 = i - 1;
                    f2 = MathUtils.constrainedMap(fArr4[i3], fArr4[i], fArr3[i3], f5, f);
                    break;
                }
                i++;
            }
            if (Float.isNaN(f2)) {
                f2 = this.mMaxFrequencyHz;
            }
            return new Range<>(Float.valueOf(f3), Float.valueOf(f2));
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeFloat(this.mResonantFrequencyHz);
            parcel.writeFloatArray(this.mFrequenciesHz);
            parcel.writeFloatArray(this.mOutputAccelerationsGs);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof FrequencyProfile) {
                FrequencyProfile frequencyProfile = (FrequencyProfile) obj;
                if (Float.compare(this.mResonantFrequencyHz, frequencyProfile.mResonantFrequencyHz) == 0 && Arrays.equals(this.mFrequenciesHz, frequencyProfile.mFrequenciesHz) && Arrays.equals(this.mOutputAccelerationsGs, frequencyProfile.mOutputAccelerationsGs)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.mResonantFrequencyHz), Integer.valueOf(Arrays.hashCode(this.mFrequenciesHz)), Integer.valueOf(Arrays.hashCode(this.mOutputAccelerationsGs)));
        }

        public String toString() {
            return "FrequencyProfile{mResonantFrequency=" + this.mResonantFrequencyHz + ", mFrequenciesHz=" + Arrays.toString(this.mFrequenciesHz) + ", mOutputAccelerationsGs=" + Arrays.toString(this.mOutputAccelerationsGs) + ", mMinFrequencyHz=" + this.mMinFrequencyHz + ", mMaxFrequencyHz=" + this.mMaxFrequencyHz + ", mMaxOutputAccelerationGs=" + this.mMaxOutputAccelerationGs + '}';
        }

        private static void deduplicateAndSortList(List<Pair<Float, Float>> list) {
            if (list == null || list.size() < 2) {
                return;
            }
            list.sort(Comparator.comparing(new Function() { // from class: android.os.VibratorInfo$FrequencyProfile$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return VibratorInfo.FrequencyProfile.lambda$deduplicateAndSortList$0((Pair) obj);
                }
            }));
            int i = 1;
            for (int i2 = 1; i2 < list.size(); i2++) {
                Pair<Float, Float> pair = list.get(i2);
                if (pair.first.compareTo(list.get(i - 1).first) != 0) {
                    list.set(i, pair);
                    i++;
                }
            }
            list.subList(i, list.size()).clear();
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ Float lambda$deduplicateAndSortList$0(Pair pair) {
            return (Float) pair.first;
        }

        private static ArrayList<Pair<Float, Float>> extractFrequencyToOutputAccelerationData(float[] fArr, float[] fArr2) {
            if (fArr == null || fArr2 == null || fArr.length == 0 || fArr.length != fArr2.length) {
                return new ArrayList<>();
            }
            ArrayList<Pair<Float, Float>> arrayList = new ArrayList<>(fArr.length);
            for (int i = 0; i < fArr.length; i++) {
                arrayList.add(new Pair<>(Float.valueOf(fArr[i]), Float.valueOf(fArr2[i])));
            }
            return arrayList;
        }
    }

    public static final class FrequencyProfileLegacy implements Parcelable {
        public static final Parcelable.Creator<FrequencyProfileLegacy> CREATOR = new Parcelable.Creator<FrequencyProfileLegacy>() { // from class: android.os.VibratorInfo.FrequencyProfileLegacy.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FrequencyProfileLegacy createFromParcel(Parcel parcel) {
                return new FrequencyProfileLegacy(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FrequencyProfileLegacy[] newArray(int i) {
                return new FrequencyProfileLegacy[i];
            }
        };
        private final Range<Float> mFrequencyRangeHz;
        private final float mFrequencyResolutionHz;
        private final float[] mMaxAmplitudes;
        private final float mMinFrequencyHz;
        private final float mResonantFrequencyHz;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        FrequencyProfileLegacy(Parcel parcel) {
            this(parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.createFloatArray());
        }

        public FrequencyProfileLegacy(float f, float f2, float f3, float[] fArr) {
            float[] fArr2;
            this.mMinFrequencyHz = f2;
            this.mResonantFrequencyHz = f;
            this.mFrequencyResolutionHz = f3;
            boolean z = false;
            float[] fArr3 = new float[fArr == null ? 0 : fArr.length];
            this.mMaxAmplitudes = fArr3;
            if (fArr != null) {
                System.arraycopy(fArr, 0, fArr3, 0, fArr.length);
            }
            boolean z2 = !Float.isNaN(f) && f > 0.0f && !Float.isNaN(f2) && f2 > 0.0f && !Float.isNaN(f3) && f3 > 0.0f && fArr3.length > 0;
            int i = 0;
            while (true) {
                fArr2 = this.mMaxAmplitudes;
                if (i >= fArr2.length) {
                    break;
                }
                float f4 = fArr2[i];
                z2 &= f4 >= 0.0f && f4 <= 1.0f;
                i++;
            }
            float length = z2 ? (f3 * (fArr2.length - 1)) + f2 : Float.NaN;
            if (!Float.isNaN(length) && f >= f2 && f <= length && f2 < length) {
                z = true;
            }
            this.mFrequencyRangeHz = z2 & z ? Range.create(Float.valueOf(f2), Float.valueOf(length)) : null;
        }

        public boolean isEmpty() {
            return this.mFrequencyRangeHz == null;
        }

        public Range<Float> getFrequencyRangeHz() {
            return this.mFrequencyRangeHz;
        }

        public float getMaxAmplitude(float f) {
            if (isEmpty() || Float.isNaN(f) || !this.mFrequencyRangeHz.contains((Range<Float>) Float.valueOf(f))) {
                return 0.0f;
            }
            float f2 = f - this.mMinFrequencyHz;
            int constrain = MathUtils.constrain((int) Math.floor(f2 / this.mFrequencyResolutionHz), 0, this.mMaxAmplitudes.length - 1);
            int constrain2 = MathUtils.constrain(constrain + 1, 0, this.mMaxAmplitudes.length - 1);
            float[] fArr = this.mMaxAmplitudes;
            float f3 = fArr[constrain];
            float f4 = fArr[constrain2];
            float f5 = this.mFrequencyResolutionHz;
            return MathUtils.constrainedMap(f3, f4, constrain * f5, constrain2 * f5, f2);
        }

        public float[] getMaxAmplitudes() {
            float[] fArr = this.mMaxAmplitudes;
            return Arrays.copyOf(fArr, fArr.length);
        }

        public float getFrequencyResolutionHz() {
            return this.mFrequencyResolutionHz;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeFloat(this.mResonantFrequencyHz);
            parcel.writeFloat(this.mMinFrequencyHz);
            parcel.writeFloat(this.mFrequencyResolutionHz);
            parcel.writeFloatArray(this.mMaxAmplitudes);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FrequencyProfileLegacy)) {
                return false;
            }
            FrequencyProfileLegacy frequencyProfileLegacy = (FrequencyProfileLegacy) obj;
            return Float.compare(this.mMinFrequencyHz, frequencyProfileLegacy.mMinFrequencyHz) == 0 && Float.compare(this.mResonantFrequencyHz, frequencyProfileLegacy.mResonantFrequencyHz) == 0 && Float.compare(this.mFrequencyResolutionHz, frequencyProfileLegacy.mFrequencyResolutionHz) == 0 && Arrays.equals(this.mMaxAmplitudes, frequencyProfileLegacy.mMaxAmplitudes);
        }

        public int hashCode() {
            return (Objects.hash(Float.valueOf(this.mMinFrequencyHz), Float.valueOf(this.mFrequencyResolutionHz), Float.valueOf(this.mFrequencyResolutionHz)) * 31) + Arrays.hashCode(this.mMaxAmplitudes);
        }

        public String toString() {
            return "FrequencyProfileLegacy{mFrequencyRange=" + this.mFrequencyRangeHz + ", mMinFrequency=" + this.mMinFrequencyHz + ", mResonantFrequency=" + this.mResonantFrequencyHz + ", mFrequencyResolution=" + this.mFrequencyResolutionHz + ", mMaxAmplitudes count=" + this.mMaxAmplitudes.length + '}';
        }
    }

    public static final class Builder {
        private long mCapabilities;
        private int mCompositionSizeMax;
        private final int mId;
        private int mMaxEnvelopeEffectControlPointDurationMillis;
        private int mMaxEnvelopeEffectSize;
        private int mMinEnvelopeEffectControlPointDurationMillis;
        private int mPrimitiveDelayMax;
        private int mPwlePrimitiveDurationMax;
        private int mPwleSizeMax;
        private SparseBooleanArray mSupportedBraking;
        private SparseBooleanArray mSupportedEffects;
        private SparseIntArray mSupportedPrimitives = new SparseIntArray();
        private float mQFactor = Float.NaN;
        private FrequencyProfileLegacy mFrequencyProfileLegacy = new FrequencyProfileLegacy(Float.NaN, Float.NaN, Float.NaN, null);
        private FrequencyProfile mFrequencyProfile = new FrequencyProfile(Float.NaN, null, null);

        public Builder(int i) {
            this.mId = i;
        }

        public Builder setCapabilities(long j) {
            this.mCapabilities = j;
            return this;
        }

        public Builder setSupportedEffects(int... iArr) {
            this.mSupportedEffects = toSparseBooleanArray(iArr);
            return this;
        }

        public Builder setSupportedBraking(int... iArr) {
            this.mSupportedBraking = toSparseBooleanArray(iArr);
            return this;
        }

        public Builder setPwlePrimitiveDurationMax(int i) {
            this.mPwlePrimitiveDurationMax = i;
            return this;
        }

        public Builder setPwleSizeMax(int i) {
            this.mPwleSizeMax = i;
            return this;
        }

        public Builder setSupportedPrimitive(int i, int i2) {
            this.mSupportedPrimitives.put(i, i2);
            return this;
        }

        public Builder setPrimitiveDelayMax(int i) {
            this.mPrimitiveDelayMax = i;
            return this;
        }

        public Builder setCompositionSizeMax(int i) {
            this.mCompositionSizeMax = i;
            return this;
        }

        public Builder setQFactor(float f) {
            this.mQFactor = f;
            return this;
        }

        public Builder setFrequencyProfileLegacy(FrequencyProfileLegacy frequencyProfileLegacy) {
            this.mFrequencyProfileLegacy = frequencyProfileLegacy;
            return this;
        }

        public Builder setFrequencyProfile(FrequencyProfile frequencyProfile) {
            this.mFrequencyProfile = frequencyProfile;
            return this;
        }

        public Builder setMaxEnvelopeEffectSize(int i) {
            this.mMaxEnvelopeEffectSize = i;
            return this;
        }

        public Builder setMinEnvelopeEffectControlPointDurationMillis(int i) {
            this.mMinEnvelopeEffectControlPointDurationMillis = i;
            return this;
        }

        public Builder setMaxEnvelopeEffectControlPointDurationMillis(int i) {
            this.mMaxEnvelopeEffectControlPointDurationMillis = i;
            return this;
        }

        public VibratorInfo build() {
            return new VibratorInfo(this.mId, this.mCapabilities, this.mSupportedEffects, this.mSupportedBraking, this.mSupportedPrimitives, this.mPrimitiveDelayMax, this.mCompositionSizeMax, this.mPwlePrimitiveDurationMax, this.mPwleSizeMax, this.mQFactor, this.mFrequencyProfileLegacy, this.mFrequencyProfile, this.mMaxEnvelopeEffectSize, this.mMinEnvelopeEffectControlPointDurationMillis, this.mMaxEnvelopeEffectControlPointDurationMillis);
        }

        private static SparseBooleanArray toSparseBooleanArray(int[] iArr) {
            if (iArr == null) {
                return null;
            }
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
            for (int i : iArr) {
                sparseBooleanArray.put(i, true);
            }
            return sparseBooleanArray;
        }
    }
}
