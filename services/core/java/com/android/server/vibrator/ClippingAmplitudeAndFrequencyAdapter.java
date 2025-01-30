package com.android.server.vibrator;

import android.os.VibratorInfo;
import android.os.vibrator.RampSegment;
import android.os.vibrator.StepSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.MathUtils;
import android.util.Range;
import com.android.server.display.DisplayPowerController2;
import com.android.server.vibrator.VibrationEffectAdapters;
import java.util.List;

/* loaded from: classes3.dex */
public final class ClippingAmplitudeAndFrequencyAdapter implements VibrationEffectAdapters.SegmentsAdapter {
    @Override // com.android.server.vibrator.VibrationEffectAdapters.SegmentsAdapter
    public int apply(List list, int i, VibratorInfo vibratorInfo) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            VibrationEffectSegment vibrationEffectSegment = (VibrationEffectSegment) list.get(i2);
            if (vibrationEffectSegment instanceof StepSegment) {
                list.set(i2, apply((StepSegment) vibrationEffectSegment, vibratorInfo));
            } else if (vibrationEffectSegment instanceof RampSegment) {
                list.set(i2, apply((RampSegment) vibrationEffectSegment, vibratorInfo));
            }
        }
        return i;
    }

    public final StepSegment apply(StepSegment stepSegment, VibratorInfo vibratorInfo) {
        float clampFrequency = clampFrequency(vibratorInfo, stepSegment.getFrequencyHz());
        return new StepSegment(clampAmplitude(vibratorInfo, clampFrequency, stepSegment.getAmplitude()), clampFrequency, (int) stepSegment.getDuration());
    }

    public final RampSegment apply(RampSegment rampSegment, VibratorInfo vibratorInfo) {
        float clampFrequency = clampFrequency(vibratorInfo, rampSegment.getStartFrequencyHz());
        float clampFrequency2 = clampFrequency(vibratorInfo, rampSegment.getEndFrequencyHz());
        return new RampSegment(clampAmplitude(vibratorInfo, clampFrequency, rampSegment.getStartAmplitude()), clampAmplitude(vibratorInfo, clampFrequency2, rampSegment.getEndAmplitude()), clampFrequency, clampFrequency2, (int) rampSegment.getDuration());
    }

    public final float clampFrequency(VibratorInfo vibratorInfo, float f) {
        Range frequencyRangeHz = vibratorInfo.getFrequencyProfile().getFrequencyRangeHz();
        if (f == DisplayPowerController2.RATE_FROM_DOZE_TO_ON || frequencyRangeHz == null) {
            return vibratorInfo.getResonantFrequencyHz();
        }
        return ((Float) frequencyRangeHz.clamp(Float.valueOf(f))).floatValue();
    }

    public final float clampAmplitude(VibratorInfo vibratorInfo, float f, float f2) {
        VibratorInfo.FrequencyProfile frequencyProfile = vibratorInfo.getFrequencyProfile();
        return frequencyProfile.isEmpty() ? f2 : MathUtils.min(f2, frequencyProfile.getMaxAmplitude(f));
    }
}
