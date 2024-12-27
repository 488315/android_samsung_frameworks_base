package com.android.server.vibrator;

import android.os.VibratorInfo;
import android.os.vibrator.RampSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.MathUtils;
import android.util.Range;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ClippingAmplitudeAndFrequencyAdapter implements VibrationSegmentsAdapter {
    public static float clampFrequency(VibratorInfo vibratorInfo, float f) {
        Range frequencyRangeHz = vibratorInfo.getFrequencyProfile().getFrequencyRangeHz();
        return (f == FullScreenMagnificationGestureHandler.MAX_SCALE || frequencyRangeHz == null) ? Float.isNaN(vibratorInfo.getResonantFrequencyHz()) ? FullScreenMagnificationGestureHandler.MAX_SCALE : vibratorInfo.getResonantFrequencyHz() : ((Float) frequencyRangeHz.clamp(Float.valueOf(f))).floatValue();
    }

    @Override // com.android.server.vibrator.VibrationSegmentsAdapter
    public final int adaptToVibrator(VibratorInfo vibratorInfo, List list, int i) {
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            RampSegment rampSegment = (VibrationEffectSegment) arrayList.get(i2);
            if (rampSegment instanceof RampSegment) {
                RampSegment rampSegment2 = rampSegment;
                float clampFrequency = clampFrequency(vibratorInfo, rampSegment2.getStartFrequencyHz());
                float clampFrequency2 = clampFrequency(vibratorInfo, rampSegment2.getEndFrequencyHz());
                float startAmplitude = rampSegment2.getStartAmplitude();
                VibratorInfo.FrequencyProfile frequencyProfile = vibratorInfo.getFrequencyProfile();
                if (!frequencyProfile.isEmpty()) {
                    startAmplitude = MathUtils.min(startAmplitude, frequencyProfile.getMaxAmplitude(clampFrequency));
                }
                float f = startAmplitude;
                float endAmplitude = rampSegment2.getEndAmplitude();
                VibratorInfo.FrequencyProfile frequencyProfile2 = vibratorInfo.getFrequencyProfile();
                if (!frequencyProfile2.isEmpty()) {
                    endAmplitude = MathUtils.min(endAmplitude, frequencyProfile2.getMaxAmplitude(clampFrequency2));
                }
                arrayList.set(i2, new RampSegment(f, endAmplitude, clampFrequency, clampFrequency2, (int) rampSegment2.getDuration()));
            }
        }
        return i;
    }
}
