package com.android.server.vibrator;

import android.os.VibratorInfo;
import android.os.vibrator.RampSegment;
import android.os.vibrator.StepSegment;
import android.os.vibrator.VibrationEffectSegment;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class StepToRampAdapter implements VibrationSegmentsAdapter {
    public static RampSegment convertStepToRamp(VibratorInfo vibratorInfo, StepSegment stepSegment) {
        float frequencyHz = stepSegment.getFrequencyHz();
        if (!Float.isNaN(vibratorInfo.getResonantFrequencyHz()) && frequencyHz == FullScreenMagnificationGestureHandler.MAX_SCALE) {
            frequencyHz = vibratorInfo.getResonantFrequencyHz();
        }
        float f = frequencyHz;
        return new RampSegment(stepSegment.getAmplitude(), stepSegment.getAmplitude(), f, f, (int) stepSegment.getDuration());
    }

    @Override // com.android.server.vibrator.VibrationSegmentsAdapter
    public final int adaptToVibrator(VibratorInfo vibratorInfo, List list, int i) {
        if (!vibratorInfo.hasCapability(1024L)) {
            return i;
        }
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            StepSegment stepSegment = (VibrationEffectSegment) arrayList.get(i2);
            if (stepSegment instanceof StepSegment) {
                StepSegment stepSegment2 = stepSegment;
                if (stepSegment2.getFrequencyHz() != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    arrayList.set(i2, convertStepToRamp(vibratorInfo, stepSegment2));
                }
            }
        }
        for (int i3 = 0; i3 < size; i3++) {
            if (arrayList.get(i3) instanceof RampSegment) {
                for (int i4 = i3 - 1; i4 >= 0 && (((VibrationEffectSegment) arrayList.get(i4)) instanceof StepSegment); i4--) {
                    arrayList.set(i4, convertStepToRamp(vibratorInfo, (StepSegment) arrayList.get(i4)));
                }
                for (int i5 = i3 + 1; i5 < size && (((VibrationEffectSegment) arrayList.get(i5)) instanceof StepSegment); i5++) {
                    arrayList.set(i5, convertStepToRamp(vibratorInfo, (StepSegment) arrayList.get(i5)));
                }
            }
        }
        return i;
    }
}
