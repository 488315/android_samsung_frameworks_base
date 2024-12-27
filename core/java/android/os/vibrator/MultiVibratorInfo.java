package android.os.vibrator;

import android.os.VibratorInfo;
import android.util.Range;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import java.util.Arrays;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final class MultiVibratorInfo extends VibratorInfo {
    private static final float EPSILON = 1.0E-5f;
    private static final String TAG = "MultiVibratorInfo";

    public MultiVibratorInfo(int id, VibratorInfo[] vibrators) {
        this(id, vibrators, frequencyProfileIntersection(vibrators));
    }

    private MultiVibratorInfo(int id, VibratorInfo[] vibrators, VibratorInfo.FrequencyProfile mergedProfile) {
        super(id, capabilitiesIntersection(vibrators, mergedProfile.isEmpty()), supportedEffectsIntersection(vibrators), supportedBrakingIntersection(vibrators), supportedPrimitivesAndDurationsIntersection(vibrators), integerLimitIntersection(vibrators, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((VibratorInfo) obj).getPrimitiveDelayMax());
            }
        }), integerLimitIntersection(vibrators, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((VibratorInfo) obj).getCompositionSizeMax());
            }
        }), integerLimitIntersection(vibrators, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((VibratorInfo) obj).getPwlePrimitiveDurationMax());
            }
        }), integerLimitIntersection(vibrators, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((VibratorInfo) obj).getPwleSizeMax());
            }
        }), floatPropertyIntersection(vibrators, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((VibratorInfo) obj).getQFactor());
            }
        }), mergedProfile);
    }

    private static int capabilitiesIntersection(VibratorInfo[] infos, boolean frequencyProfileIsEmpty) {
        int intersection = -1;
        for (VibratorInfo info : infos) {
            intersection = (int) (intersection & info.getCapabilities());
        }
        if (frequencyProfileIsEmpty) {
            return intersection & (-513);
        }
        return intersection;
    }

    private static SparseBooleanArray supportedBrakingIntersection(VibratorInfo[] infos) {
        for (VibratorInfo info : infos) {
            if (!info.isBrakingSupportKnown()) {
                return null;
            }
        }
        SparseBooleanArray intersection = new SparseBooleanArray();
        SparseBooleanArray firstVibratorBraking = infos[0].getSupportedBraking();
        for (int i = 0; i < firstVibratorBraking.size(); i++) {
            int brakingId = firstVibratorBraking.keyAt(i);
            if (firstVibratorBraking.valueAt(i)) {
                int j = 1;
                while (true) {
                    if (j < infos.length) {
                        if (!infos[j].hasBrakingSupport(brakingId)) {
                            break;
                        }
                        j++;
                    } else {
                        intersection.put(brakingId, true);
                        break;
                    }
                }
            }
        }
        return intersection;
    }

    private static SparseBooleanArray supportedEffectsIntersection(VibratorInfo[] infos) {
        for (VibratorInfo info : infos) {
            if (!info.isEffectSupportKnown()) {
                return null;
            }
        }
        SparseBooleanArray intersection = new SparseBooleanArray();
        SparseBooleanArray firstVibratorEffects = infos[0].getSupportedEffects();
        for (int i = 0; i < firstVibratorEffects.size(); i++) {
            int effectId = firstVibratorEffects.keyAt(i);
            if (firstVibratorEffects.valueAt(i)) {
                int j = 1;
                while (true) {
                    if (j < infos.length) {
                        if (infos[j].isEffectSupported(effectId) != 1) {
                            break;
                        }
                        j++;
                    } else {
                        intersection.put(effectId, true);
                        break;
                    }
                }
            }
        }
        return intersection;
    }

    private static SparseIntArray supportedPrimitivesAndDurationsIntersection(VibratorInfo[] infos) {
        SparseIntArray intersection = new SparseIntArray();
        SparseIntArray firstVibratorPrimitives = infos[0].getSupportedPrimitives();
        for (int i = 0; i < firstVibratorPrimitives.size(); i++) {
            int primitiveId = firstVibratorPrimitives.keyAt(i);
            int primitiveDuration = firstVibratorPrimitives.valueAt(i);
            if (primitiveDuration != 0) {
                int j = 1;
                while (true) {
                    if (j < infos.length) {
                        int vibratorPrimitiveDuration = infos[j].getPrimitiveDuration(primitiveId);
                        if (vibratorPrimitiveDuration == 0) {
                            break;
                        }
                        primitiveDuration = Math.max(primitiveDuration, vibratorPrimitiveDuration);
                        j++;
                    } else {
                        intersection.put(primitiveId, primitiveDuration);
                        break;
                    }
                }
            }
        }
        return intersection;
    }

    private static int integerLimitIntersection(VibratorInfo[] infos, Function<VibratorInfo, Integer> propertyGetter) {
        int limit = 0;
        for (VibratorInfo info : infos) {
            int vibratorLimit = propertyGetter.apply(info).intValue();
            if (limit == 0 || (vibratorLimit > 0 && vibratorLimit < limit)) {
                limit = vibratorLimit;
            }
        }
        return limit;
    }

    private static float floatPropertyIntersection(VibratorInfo[] infos, Function<VibratorInfo, Float> propertyGetter) {
        float property = propertyGetter.apply(infos[0]).floatValue();
        if (Float.isNaN(property)) {
            return Float.NaN;
        }
        for (int i = 1; i < infos.length; i++) {
            if (Float.compare(property, propertyGetter.apply(infos[i]).floatValue()) != 0) {
                return Float.NaN;
            }
        }
        return property;
    }

    private static VibratorInfo.FrequencyProfile frequencyProfileIntersection(VibratorInfo[] infos) {
        float freqResolution = floatPropertyIntersection(infos, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Float valueOf;
                valueOf = Float.valueOf(((VibratorInfo) obj).getFrequencyProfile().getFrequencyResolutionHz());
                return valueOf;
            }
        });
        float resonantFreq = floatPropertyIntersection(infos, new Function() { // from class: android.os.vibrator.MultiVibratorInfo$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((VibratorInfo) obj).getResonantFrequencyHz());
            }
        });
        Range<Float> freqRange = frequencyRangeIntersection(infos, freqResolution);
        if (freqRange == null || Float.isNaN(freqResolution)) {
            return new VibratorInfo.FrequencyProfile(resonantFreq, Float.NaN, freqResolution, null);
        }
        int amplitudeCount = Math.round(((freqRange.getUpper().floatValue() - freqRange.getLower().floatValue()) / freqResolution) + 1.0f);
        float[] maxAmplitudes = new float[amplitudeCount];
        Arrays.fill(maxAmplitudes, Float.MAX_VALUE);
        for (VibratorInfo info : infos) {
            Range<Float> vibratorFreqRange = info.getFrequencyProfile().getFrequencyRangeHz();
            float[] vibratorMaxAmplitudes = info.getFrequencyProfile().getMaxAmplitudes();
            int vibratorStartIdx = Math.round((freqRange.getLower().floatValue() - vibratorFreqRange.getLower().floatValue()) / freqResolution);
            int vibratorEndIdx = (maxAmplitudes.length + vibratorStartIdx) - 1;
            if (vibratorStartIdx < 0 || vibratorEndIdx >= vibratorMaxAmplitudes.length) {
                Slog.w(TAG, "Error calculating the intersection of vibrator frequency profiles: attempted to fetch from vibrator " + info.getId() + " max amplitude with bad index " + vibratorStartIdx);
                return new VibratorInfo.FrequencyProfile(resonantFreq, Float.NaN, Float.NaN, null);
            }
            for (int i = 0; i < maxAmplitudes.length; i++) {
                maxAmplitudes[i] = Math.min(maxAmplitudes[i], vibratorMaxAmplitudes[vibratorStartIdx + i]);
            }
        }
        return new VibratorInfo.FrequencyProfile(resonantFreq, freqRange.getLower().floatValue(), freqResolution, maxAmplitudes);
    }

    private static Range<Float> frequencyRangeIntersection(VibratorInfo[] infos, float frequencyResolution) {
        Range<Float> firstRange = infos[0].getFrequencyProfile().getFrequencyRangeHz();
        if (firstRange == null) {
            return null;
        }
        float intersectionLower = firstRange.getLower().floatValue();
        float intersectionUpper = firstRange.getUpper().floatValue();
        for (int i = 1; i < infos.length; i++) {
            Range<Float> vibratorRange = infos[i].getFrequencyProfile().getFrequencyRangeHz();
            if (vibratorRange == null || vibratorRange.getLower().floatValue() >= intersectionUpper || vibratorRange.getUpper().floatValue() <= intersectionLower) {
                return null;
            }
            float frequencyDelta = Math.abs(intersectionLower - vibratorRange.getLower().floatValue());
            if (frequencyDelta % frequencyResolution > 1.0E-5f) {
                return null;
            }
            intersectionLower = Math.max(intersectionLower, vibratorRange.getLower().floatValue());
            intersectionUpper = Math.min(intersectionUpper, vibratorRange.getUpper().floatValue());
        }
        if (intersectionUpper - intersectionLower < frequencyResolution) {
            return null;
        }
        return Range.create(Float.valueOf(intersectionLower), Float.valueOf(intersectionUpper));
    }
}
