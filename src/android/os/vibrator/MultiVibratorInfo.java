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
        int i = -1;
        for (VibratorInfo vibratorInfo : vibratorInfoArr) {
            i = (int) (i & vibratorInfo.getCapabilities());
        }
        return z ? i & (-513) : i;
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
            int keyAt = supportedBraking.keyAt(i);
            if (supportedBraking.valueAt(i)) {
                int i2 = 1;
                while (true) {
                    if (i2 < vibratorInfoArr.length) {
                        if (!vibratorInfoArr[i2].hasBrakingSupport(keyAt)) {
                            break;
                        }
                        i2++;
                    } else {
                        sparseBooleanArray.put(keyAt, true);
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
            int keyAt = supportedEffects.keyAt(i);
            if (supportedEffects.valueAt(i)) {
                int i2 = 1;
                while (true) {
                    if (i2 < vibratorInfoArr.length) {
                        if (vibratorInfoArr[i2].isEffectSupported(keyAt) != 1) {
                            break;
                        }
                        i2++;
                    } else {
                        sparseBooleanArray.put(keyAt, true);
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
            int keyAt = supportedPrimitives.keyAt(i);
            int valueAt = supportedPrimitives.valueAt(i);
            if (valueAt != 0) {
                int i2 = 1;
                while (true) {
                    if (i2 < vibratorInfoArr.length) {
                        int primitiveDuration = vibratorInfoArr[i2].getPrimitiveDuration(keyAt);
                        if (primitiveDuration == 0) {
                            break;
                        }
                        valueAt = Math.max(valueAt, primitiveDuration);
                        i2++;
                    } else {
                        sparseIntArray.put(keyAt, valueAt);
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
            int intValue = function.apply(vibratorInfo).intValue();
            if (i == 0 || (intValue > 0 && intValue < i)) {
                i = intValue;
            }
        }
        return i;
    }

    private static float floatPropertyIntersection(VibratorInfo[] vibratorInfoArr, Function<VibratorInfo, Float> function) {
        float floatValue = function.apply(vibratorInfoArr[0]).floatValue();
        if (Float.isNaN(floatValue)) {
            return Float.NaN;
        }
        for (int i = 1; i < vibratorInfoArr.length; i++) {
            if (Float.compare(floatValue, function.apply(vibratorInfoArr[i]).floatValue()) != 0) {
                return Float.NaN;
            }
        }
        return floatValue;
    }

    private static VibratorInfo.FrequencyProfile frequencyProfileIntersection(VibratorInfo[] vibratorInfoArr) {
        if (vibratorInfoArr == null || vibratorInfoArr.length == 0) {
            return new VibratorInfo.FrequencyProfile(Float.NaN, null, null);
        }
        float floatPropertyIntersection = floatPropertyIntersection(vibratorInfoArr, new MultiVibratorInfo$$ExternalSyntheticLambda9());
        if (Float.isNaN(floatPropertyIntersection)) {
            return new VibratorInfo.FrequencyProfile(Float.NaN, null, null);
        }
        TreeSet<Float> treeSet = new TreeSet();
        float f = 0.0f;
        float f2 = Float.MAX_VALUE;
        for (VibratorInfo vibratorInfo : vibratorInfoArr) {
            float minFrequencyHz = vibratorInfo.getFrequencyProfile().getMinFrequencyHz();
            float maxFrequencyHz = vibratorInfo.getFrequencyProfile().getMaxFrequencyHz();
            if (Float.isNaN(minFrequencyHz) || Float.isNaN(maxFrequencyHz)) {
                return new VibratorInfo.FrequencyProfile(Float.NaN, null, null);
            }
            f = Math.max(f, minFrequencyHz);
            f2 = Math.min(f2, maxFrequencyHz);
            if (vibratorInfo.getFrequencyProfile().getFrequenciesHz() == null) {
                return new VibratorInfo.FrequencyProfile(Float.NaN, null, null);
            }
            for (float f3 : vibratorInfo.getFrequencyProfile().getFrequenciesHz()) {
                treeSet.add(Float.valueOf(f3));
            }
        }
        if (f > f2) {
            return new VibratorInfo.FrequencyProfile(Float.NaN, null, null);
        }
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            float floatValue = ((Float) it.next()).floatValue();
            if (floatValue < f || floatValue > f2) {
                it.remove();
            }
        }
        float[] fArr = new float[treeSet.size()];
        float[] fArr2 = new float[treeSet.size()];
        int i = 0;
        for (Float f4 : treeSet) {
            float f5 = Float.MAX_VALUE;
            for (VibratorInfo vibratorInfo2 : vibratorInfoArr) {
                f5 = Math.min(f5, vibratorInfo2.getFrequencyProfile().getOutputAccelerationGs(f4.floatValue()));
            }
            fArr[i] = f4.floatValue();
            fArr2[i] = f5;
            i++;
        }
        return new VibratorInfo.FrequencyProfile(floatPropertyIntersection, fArr, fArr2);
    }

    private static VibratorInfo.FrequencyProfileLegacy frequencyProfileLegacyIntersection(VibratorInfo[] vibratorInfoArr) {
        float floatPropertyIntersection = floatPropertyIntersection(vibratorInfoArr, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Float valueOf;
                valueOf = Float.valueOf(((VibratorInfo) obj).getFrequencyProfileLegacy().getFrequencyResolutionHz());
                return valueOf;
            }
        });
        float floatPropertyIntersection2 = floatPropertyIntersection(vibratorInfoArr, new MultiVibratorInfo$$ExternalSyntheticLambda9());
        Range<Float> frequencyRangeIntersection = frequencyRangeIntersection(vibratorInfoArr, floatPropertyIntersection);
        if (frequencyRangeIntersection == null || Float.isNaN(floatPropertyIntersection)) {
            return new VibratorInfo.FrequencyProfileLegacy(floatPropertyIntersection2, Float.NaN, floatPropertyIntersection, null);
        }
        int round = Math.round(((frequencyRangeIntersection.getUpper().floatValue() - frequencyRangeIntersection.getLower().floatValue()) / floatPropertyIntersection) + 1.0f);
        float[] fArr = new float[round];
        Arrays.fill(fArr, Float.MAX_VALUE);
        for (VibratorInfo vibratorInfo : vibratorInfoArr) {
            Range<Float> frequencyRangeHz = vibratorInfo.getFrequencyProfileLegacy().getFrequencyRangeHz();
            float[] maxAmplitudes = vibratorInfo.getFrequencyProfileLegacy().getMaxAmplitudes();
            int round2 = Math.round((frequencyRangeIntersection.getLower().floatValue() - frequencyRangeHz.getLower().floatValue()) / floatPropertyIntersection);
            int i = (round2 + round) - 1;
            if (round2 < 0 || i >= maxAmplitudes.length) {
                Slog.w(TAG, "Error calculating the intersection of vibrator frequency profiles: attempted to fetch from vibrator " + vibratorInfo.getId() + " max amplitude with bad index " + round2);
                return new VibratorInfo.FrequencyProfileLegacy(floatPropertyIntersection2, Float.NaN, Float.NaN, null);
            }
            for (int i2 = 0; i2 < round; i2++) {
                fArr[i2] = Math.min(fArr[i2], maxAmplitudes[round2 + i2]);
            }
        }
        return new VibratorInfo.FrequencyProfileLegacy(floatPropertyIntersection2, frequencyRangeIntersection.getLower().floatValue(), floatPropertyIntersection, fArr);
    }

    private static Range<Float> frequencyRangeIntersection(VibratorInfo[] vibratorInfoArr, float f) {
        Range<Float> frequencyRangeHz = vibratorInfoArr[0].getFrequencyProfileLegacy().getFrequencyRangeHz();
        if (frequencyRangeHz == null) {
            return null;
        }
        float floatValue = frequencyRangeHz.getLower().floatValue();
        float floatValue2 = frequencyRangeHz.getUpper().floatValue();
        for (int i = 1; i < vibratorInfoArr.length; i++) {
            Range<Float> frequencyRangeHz2 = vibratorInfoArr[i].getFrequencyProfileLegacy().getFrequencyRangeHz();
            if (frequencyRangeHz2 == null || frequencyRangeHz2.getLower().floatValue() >= floatValue2 || frequencyRangeHz2.getUpper().floatValue() <= floatValue || Math.abs(floatValue - frequencyRangeHz2.getLower().floatValue()) % f > 1.0E-5f) {
                return null;
            }
            floatValue = Math.max(floatValue, frequencyRangeHz2.getLower().floatValue());
            floatValue2 = Math.min(floatValue2, frequencyRangeHz2.getUpper().floatValue());
        }
        if (floatValue2 - floatValue < f) {
            return null;
        }
        return Range.create(Float.valueOf(floatValue), Float.valueOf(floatValue2));
    }
}
