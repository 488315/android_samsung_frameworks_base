package com.android.server.vibrator;

import android.os.VibratorInfo;
import android.os.vibrator.RampSegment;
import android.os.vibrator.StepSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.MathUtils;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RampToStepAdapter implements VibrationSegmentsAdapter {
    public final int mStepDuration;

    public RampToStepAdapter(int i) {
        this.mStepDuration = i;
    }

    public static float fillEmptyFrequency(VibratorInfo vibratorInfo, float f) {
        return Float.isNaN(vibratorInfo.getResonantFrequencyHz()) ? FullScreenMagnificationGestureHandler.MAX_SCALE : f == FullScreenMagnificationGestureHandler.MAX_SCALE ? vibratorInfo.getResonantFrequencyHz() : f;
    }

    @Override // com.android.server.vibrator.VibrationSegmentsAdapter
    public final int adaptToVibrator(VibratorInfo vibratorInfo, List list, int i) {
        int i2;
        List list2;
        int i3;
        if (vibratorInfo.hasCapability(1024L)) {
            return i;
        }
        ArrayList arrayList = (ArrayList) list;
        int i4 = 0;
        int size = arrayList.size();
        int i5 = 0;
        int i6 = i;
        while (i5 < size) {
            RampSegment rampSegment = (VibrationEffectSegment) arrayList.get(i5);
            if (rampSegment instanceof RampSegment) {
                RampSegment rampSegment2 = rampSegment;
                if (Float.compare(rampSegment2.getStartAmplitude(), rampSegment2.getEndAmplitude()) == 0) {
                    StepSegment[] stepSegmentArr = new StepSegment[1];
                    stepSegmentArr[i4] = new StepSegment(rampSegment2.getStartAmplitude(), fillEmptyFrequency(vibratorInfo, rampSegment2.getStartFrequencyHz()), (int) rampSegment2.getDuration());
                    list2 = Arrays.asList(stepSegmentArr);
                } else {
                    ArrayList arrayList2 = new ArrayList();
                    long duration = rampSegment2.getDuration();
                    int i7 = this.mStepDuration;
                    int i8 = ((int) ((duration + i7) - 1)) / i7;
                    int i9 = i4;
                    while (true) {
                        i2 = i8 - 1;
                        if (i9 >= i2) {
                            break;
                        }
                        float f = i9 / i8;
                        arrayList2.add(new StepSegment(MathUtils.lerp(rampSegment2.getStartAmplitude(), rampSegment2.getEndAmplitude(), f), MathUtils.lerp(fillEmptyFrequency(vibratorInfo, rampSegment2.getStartFrequencyHz()), fillEmptyFrequency(vibratorInfo, rampSegment2.getEndFrequencyHz()), f), i7));
                        i9++;
                        i8 = i8;
                    }
                    arrayList2.add(new StepSegment(rampSegment2.getEndAmplitude(), fillEmptyFrequency(vibratorInfo, rampSegment2.getEndFrequencyHz()), ((int) rampSegment2.getDuration()) - (i7 * i2)));
                    list2 = arrayList2;
                }
                arrayList.remove(i5);
                arrayList.addAll(i5, list2);
                int size2 = list2.size();
                i3 = 1;
                int i10 = size2 - 1;
                if (i6 > i5) {
                    i6 += i10;
                }
                i5 += i10;
                size += i10;
            } else {
                i3 = 1;
            }
            i5 += i3;
            i4 = 0;
        }
        return i6;
    }
}
