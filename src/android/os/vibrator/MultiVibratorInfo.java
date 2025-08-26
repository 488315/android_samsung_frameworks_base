package android.os.vibrator;

import android.os.VibratorInfo;
import android.util.Range;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final class MultiVibratorInfo extends VibratorInfo {
    private static final float EPSILON = 1.0E-5f;
    private static final String TAG = "MultiVibratorInfo";

    public MultiVibratorInfo(int i, VibratorInfo[] vibratorInfoArr) {
        this(i, vibratorInfoArr, frequencyProfileLegacyIntersection(vibratorInfoArr), frequencyProfileIntersection(vibratorInfoArr));
    }

    private MultiVibratorInfo(int i, VibratorInfo[] vibratorInfoArr, VibratorInfo.FrequencyProfileLegacy frequencyProfileLegacy, VibratorInfo.FrequencyProfile frequencyProfile) {
        super(i, capabilitiesIntersection(vibratorInfoArr, Flags.normalizedPwleEffects() ? frequencyProfile.isEmpty() : frequencyProfileLegacy.isEmpty()), supportedEffectsIntersection(vibratorInfoArr), supportedBrakingIntersection(vibratorInfoArr), supportedPrimitivesAndDurationsIntersection(vibratorInfoArr), integerLimitIntersection(vibratorInfoArr, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((VibratorInfo) obj).getPrimitiveDelayMax());
            }
        }), integerLimitIntersection(vibratorInfoArr, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((VibratorInfo) obj).getCompositionSizeMax());
            }
        }), integerLimitIntersection(vibratorInfoArr, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((VibratorInfo) obj).getPwlePrimitiveDurationMax());
            }
        }), integerLimitIntersection(vibratorInfoArr, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((VibratorInfo) obj).getPwleSizeMax());
            }
        }), floatPropertyIntersection(vibratorInfoArr, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((VibratorInfo) obj).getQFactor());
            }
        }), frequencyProfileLegacy, frequencyProfile, integerLimitIntersection(vibratorInfoArr, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((VibratorInfo) obj).getMaxEnvelopeEffectSize());
            }
        }), integerLimitIntersection(vibratorInfoArr, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((VibratorInfo) obj).getMinEnvelopeEffectControlPointDurationMillis());
            }
        }), integerLimitIntersection(vibratorInfoArr, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((VibratorInfo) obj).getMaxEnvelopeEffectControlPointDurationMillis());
            }
        }));
    }

    private static int capabilitiesIntersection(VibratorInfo[] vibratorInfoArr, boolean z) {
        int capabilities = -1;
        for (VibratorInfo vibratorInfo : vibratorInfoArr) {
            capabilities = (int) (capabilities & vibratorInfo.getCapabilities());
        }
        return z ? capabilities & (-513) : capabilities;
    }

    private static SparseBooleanArray supportedBrakingIntersection(VibratorInfo[] vibratorInfoArr) {
        for (VibratorInfo vibratorInfo : vibratorInfoArr) {
            if (!vibratorInfo.isBrakingSupportKnown()) {
                return null;
            }
        }
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        SparseBooleanArray supportedBraking = vibratorInfoArr[0].getSupportedBraking();
        for (int i = 0; i < supportedBraking.size(); i++) {
            int iKeyAt = supportedBraking.keyAt(i);
            if (supportedBraking.valueAt(i)) {
                int i2 = 1;
                while (true) {
                    if (i2 < vibratorInfoArr.length) {
                        if (!vibratorInfoArr[i2].hasBrakingSupport(iKeyAt)) {
                            break;
                        }
                        i2++;
                    } else {
                        sparseBooleanArray.put(iKeyAt, true);
                        break;
                    }
                }
            }
        }
        return sparseBooleanArray;
    }

    private static SparseBooleanArray supportedEffectsIntersection(VibratorInfo[] vibratorInfoArr) {
        for (VibratorInfo vibratorInfo : vibratorInfoArr) {
            if (!vibratorInfo.isEffectSupportKnown()) {
                return null;
            }
        }
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        SparseBooleanArray supportedEffects = vibratorInfoArr[0].getSupportedEffects();
        for (int i = 0; i < supportedEffects.size(); i++) {
            int iKeyAt = supportedEffects.keyAt(i);
            if (supportedEffects.valueAt(i)) {
                int i2 = 1;
                while (true) {
                    if (i2 < vibratorInfoArr.length) {
                        if (vibratorInfoArr[i2].isEffectSupported(iKeyAt) != 1) {
                            break;
                        }
                        i2++;
                    } else {
                        sparseBooleanArray.put(iKeyAt, true);
                        break;
                    }
                }
            }
        }
        return sparseBooleanArray;
    }

    private static SparseIntArray supportedPrimitivesAndDurationsIntersection(VibratorInfo[] vibratorInfoArr) {
        SparseIntArray sparseIntArray = new SparseIntArray();
        SparseIntArray supportedPrimitives = vibratorInfoArr[0].getSupportedPrimitives();
        for (int i = 0; i < supportedPrimitives.size(); i++) {
            int iKeyAt = supportedPrimitives.keyAt(i);
            int iValueAt = supportedPrimitives.valueAt(i);
            if (iValueAt != 0) {
                int i2 = 1;
                while (true) {
                    if (i2 < vibratorInfoArr.length) {
                        int primitiveDuration = vibratorInfoArr[i2].getPrimitiveDuration(iKeyAt);
                        if (primitiveDuration == 0) {
                            break;
                        }
                        iValueAt = Math.max(iValueAt, primitiveDuration);
                        i2++;
                    } else {
                        sparseIntArray.put(iKeyAt, iValueAt);
                        break;
                    }
                }
            }
        }
        return sparseIntArray;
    }

    private static int integerLimitIntersection(VibratorInfo[] vibratorInfoArr, Function<VibratorInfo, Integer> function) {
        int i = 0;
        for (VibratorInfo vibratorInfo : vibratorInfoArr) {
            int iIntValue = function.apply(vibratorInfo).intValue();
            if (i == 0 || (iIntValue > 0 && iIntValue < i)) {
                i = iIntValue;
            }
        }
        return i;
    }

    private static float floatPropertyIntersection(VibratorInfo[] vibratorInfoArr, Function<VibratorInfo, Float> function) {
        float fFloatValue = function.apply(vibratorInfoArr[0]).floatValue();
        if (Float.isNaN(fFloatValue)) {
            return Float.NaN;
        }
        for (int i = 1; i < vibratorInfoArr.length; i++) {
            if (Float.compare(fFloatValue, function.apply(vibratorInfoArr[i]).floatValue()) != 0) {
                return Float.NaN;
            }
        }
        return fFloatValue;
    }

    private static VibratorInfo.FrequencyProfile frequencyProfileIntersection(VibratorInfo[] vibratorInfoArr) {
        if (vibratorInfoArr == null || vibratorInfoArr.length == 0) {
            return new VibratorInfo.FrequencyProfile(Float.NaN, null, null);
        }
        float fFloatPropertyIntersection = floatPropertyIntersection(vibratorInfoArr, new MultiVibratorInfo$$ExternalSyntheticLambda9());
        if (Float.isNaN(fFloatPropertyIntersection)) {
            return new VibratorInfo.FrequencyProfile(Float.NaN, null, null);
        }
        TreeSet<Float> treeSet = new TreeSet();
        float fMax = 0.0f;
        float fMin = Float.MAX_VALUE;
        for (VibratorInfo vibratorInfo : vibratorInfoArr) {
            float minFrequencyHz = vibratorInfo.getFrequencyProfile().getMinFrequencyHz();
            float maxFrequencyHz = vibratorInfo.getFrequencyProfile().getMaxFrequencyHz();
            if (Float.isNaN(minFrequencyHz) || Float.isNaN(maxFrequencyHz)) {
                return new VibratorInfo.FrequencyProfile(Float.NaN, null, null);
            }
            fMax = Math.max(fMax, minFrequencyHz);
            fMin = Math.min(fMin, maxFrequencyHz);
            if (vibratorInfo.getFrequencyProfile().getFrequenciesHz() == null) {
                return new VibratorInfo.FrequencyProfile(Float.NaN, null, null);
            }
            for (float f : vibratorInfo.getFrequencyProfile().getFrequenciesHz()) {
                treeSet.add(Float.valueOf(f));
            }
        }
        if (fMax > fMin) {
            return new VibratorInfo.FrequencyProfile(Float.NaN, null, null);
        }
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            float fFloatValue = ((Float) it.next()).floatValue();
            if (fFloatValue < fMax || fFloatValue > fMin) {
                it.remove();
            }
        }
        float[] fArr = new float[treeSet.size()];
        float[] fArr2 = new float[treeSet.size()];
        int i = 0;
        for (Float f2 : treeSet) {
            float fMin2 = Float.MAX_VALUE;
            for (VibratorInfo vibratorInfo2 : vibratorInfoArr) {
                fMin2 = Math.min(fMin2, vibratorInfo2.getFrequencyProfile().getOutputAccelerationGs(f2.floatValue()));
            }
            fArr[i] = f2.floatValue();
            fArr2[i] = fMin2;
            i++;
        }
        return new VibratorInfo.FrequencyProfile(fFloatPropertyIntersection, fArr, fArr2);
    }

    private static VibratorInfo.FrequencyProfileLegacy frequencyProfileLegacyIntersection(VibratorInfo[] vibratorInfoArr) {
        float fFloatPropertyIntersection = floatPropertyIntersection(vibratorInfoArr, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((VibratorInfo) obj).getFrequencyProfileLegacy().getFrequencyResolutionHz());
            }
        });
        float fFloatPropertyIntersection2 = floatPropertyIntersection(vibratorInfoArr, new MultiVibratorInfo$$ExternalSyntheticLambda9());
        Range<Float> rangeFrequencyRangeIntersection = frequencyRangeIntersection(vibratorInfoArr, fFloatPropertyIntersection);
        if (rangeFrequencyRangeIntersection == null || Float.isNaN(fFloatPropertyIntersection)) {
            return new VibratorInfo.FrequencyProfileLegacy(fFloatPropertyIntersection2, Float.NaN, fFloatPropertyIntersection, null);
        }
        int iRound = Math.round(((((Float) rangeFrequencyRangeIntersection.getUpper()).floatValue() - ((Float) rangeFrequencyRangeIntersection.getLower()).floatValue()) / fFloatPropertyIntersection) + 1.0f);
        float[] fArr = new float[iRound];
        Arrays.fill(fArr, Float.MAX_VALUE);
        for (VibratorInfo vibratorInfo : vibratorInfoArr) {
            Range<Float> frequencyRangeHz = vibratorInfo.getFrequencyProfileLegacy().getFrequencyRangeHz();
            float[] maxAmplitudes = vibratorInfo.getFrequencyProfileLegacy().getMaxAmplitudes();
            int iRound2 = Math.round((((Float) rangeFrequencyRangeIntersection.getLower()).floatValue() - ((Float) frequencyRangeHz.getLower()).floatValue()) / fFloatPropertyIntersection);
            int i = (iRound2 + iRound) - 1;
            if (iRound2 < 0 || i >= maxAmplitudes.length) {
                Slog.w(TAG, "Error calculating the intersection of vibrator frequency profiles: attempted to fetch from vibrator " + vibratorInfo.getId() + " max amplitude with bad index " + iRound2);
                return new VibratorInfo.FrequencyProfileLegacy(fFloatPropertyIntersection2, Float.NaN, Float.NaN, null);
            }
            for (int i2 = 0; i2 < iRound; i2++) {
                fArr[i2] = Math.min(fArr[i2], maxAmplitudes[iRound2 + i2]);
            }
        }
        return new VibratorInfo.FrequencyProfileLegacy(fFloatPropertyIntersection2, ((Float) rangeFrequencyRangeIntersection.getLower()).floatValue(), fFloatPropertyIntersection, fArr);
    }

    private static Range<Float> frequencyRangeIntersection(VibratorInfo[] vibratorInfoArr, float f) {
        Range<Float> frequencyRangeHz = vibratorInfoArr[0].getFrequencyProfileLegacy().getFrequencyRangeHz();
        if (frequencyRangeHz == null) {
            return null;
        }
        float fFloatValue = ((Float) frequencyRangeHz.getLower()).floatValue();
        float fFloatValue2 = ((Float) frequencyRangeHz.getUpper()).floatValue();
        for (int i = 1; i < vibratorInfoArr.length; i++) {
            Range<Float> frequencyRangeHz2 = vibratorInfoArr[i].getFrequencyProfileLegacy().getFrequencyRangeHz();
            if (frequencyRangeHz2 == null || ((Float) frequencyRangeHz2.getLower()).floatValue() >= fFloatValue2 || ((Float) frequencyRangeHz2.getUpper()).floatValue() <= fFloatValue || Math.abs(fFloatValue - ((Float) frequencyRangeHz2.getLower()).floatValue()) % f > 1.0E-5f) {
                return null;
            }
            fFloatValue = Math.max(fFloatValue, ((Float) frequencyRangeHz2.getLower()).floatValue());
            fFloatValue2 = Math.min(fFloatValue2, ((Float) frequencyRangeHz2.getUpper()).floatValue());
        }
        if (fFloatValue2 - fFloatValue < f) {
            return null;
        }
        return Range.create(Float.valueOf(fFloatValue), Float.valueOf(fFloatValue2));
    }
}
