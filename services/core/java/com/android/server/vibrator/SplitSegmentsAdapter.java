package com.android.server.vibrator;

import android.os.VibratorInfo;
import android.os.vibrator.RampSegment;
import android.util.MathUtils;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SplitSegmentsAdapter implements VibrationSegmentsAdapter {
    @Override // com.android.server.vibrator.VibrationSegmentsAdapter
    public final int adaptToVibrator(VibratorInfo vibratorInfo, List list, int i) {
        int pwlePrimitiveDurationMax;
        int i2;
        if (!vibratorInfo.hasCapability(1024L)
                || (pwlePrimitiveDurationMax = vibratorInfo.getPwlePrimitiveDurationMax()) <= 0) {
            return i;
        }
        ArrayList arrayList = (ArrayList) list;
        int i3 = 0;
        int size = arrayList.size();
        int i4 = i;
        while (i3 < size) {
            if (arrayList.get(i3) instanceof RampSegment) {
                RampSegment rampSegment = (RampSegment) arrayList.get(i3);
                int duration =
                        ((((int) rampSegment.getDuration()) + pwlePrimitiveDurationMax) - 1)
                                / pwlePrimitiveDurationMax;
                if (duration > 1) {
                    arrayList.remove(i3);
                    ArrayList arrayList2 = new ArrayList(duration);
                    float startFrequencyHz = rampSegment.getStartFrequencyHz();
                    if (!Float.isNaN(vibratorInfo.getResonantFrequencyHz())
                            && startFrequencyHz
                                    == FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        startFrequencyHz = vibratorInfo.getResonantFrequencyHz();
                    }
                    float endFrequencyHz = rampSegment.getEndFrequencyHz();
                    if (!Float.isNaN(vibratorInfo.getResonantFrequencyHz())
                            && endFrequencyHz == FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        endFrequencyHz = vibratorInfo.getResonantFrequencyHz();
                    }
                    long duration2 = rampSegment.getDuration() / duration;
                    long j = 0;
                    float f = startFrequencyHz;
                    float startAmplitude = rampSegment.getStartAmplitude();
                    int i5 = 1;
                    while (i5 < duration) {
                        long j2 = j + duration2;
                        float duration3 = j2 / rampSegment.getDuration();
                        RampSegment rampSegment2 =
                                new RampSegment(
                                        startAmplitude,
                                        MathUtils.lerp(
                                                rampSegment.getStartAmplitude(),
                                                rampSegment.getEndAmplitude(),
                                                duration3),
                                        f,
                                        MathUtils.lerp(startFrequencyHz, endFrequencyHz, duration3),
                                        (int) duration2);
                        arrayList2.add(rampSegment2);
                        startAmplitude = rampSegment2.getEndAmplitude();
                        f = rampSegment2.getEndFrequencyHz();
                        i5++;
                        duration = duration;
                        j = j2;
                    }
                    arrayList2.add(
                            new RampSegment(
                                    startAmplitude,
                                    rampSegment.getEndAmplitude(),
                                    f,
                                    endFrequencyHz,
                                    (int) (rampSegment.getDuration() - j)));
                    arrayList.addAll(i3, arrayList2);
                    int i6 = duration - 1;
                    if (i4 > i3) {
                        i4 += i6;
                    }
                    i3 += i6;
                    size += i6;
                    i2 = 1;
                    i3 += i2;
                }
            }
            i2 = 1;
            i3 += i2;
        }
        return i4;
    }
}
