package com.android.server.vibrator;

import android.os.VibratorInfo;
import android.os.vibrator.RampSegment;
import android.os.vibrator.StepSegment;
import android.os.vibrator.VibrationEffectSegment;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RampDownAdapter implements VibrationSegmentsAdapter {
    public final int mRampDownDuration;
    public final int mStepDuration;

    public RampDownAdapter(int i, int i2) {
        this.mRampDownDuration = i;
        this.mStepDuration = i2;
    }

    public static boolean endsWithNonZeroAmplitude(VibrationEffectSegment vibrationEffectSegment) {
        return vibrationEffectSegment instanceof StepSegment
                ? ((StepSegment) vibrationEffectSegment).getAmplitude()
                        != FullScreenMagnificationGestureHandler.MAX_SCALE
                : (vibrationEffectSegment instanceof RampSegment)
                        && ((RampSegment) vibrationEffectSegment).getEndAmplitude()
                                != FullScreenMagnificationGestureHandler.MAX_SCALE;
    }

    public static boolean isOffSegment(VibrationEffectSegment vibrationEffectSegment) {
        if (vibrationEffectSegment instanceof StepSegment) {
            return ((StepSegment) vibrationEffectSegment).getAmplitude()
                    == FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
        if (!(vibrationEffectSegment instanceof RampSegment)) {
            return false;
        }
        RampSegment rampSegment = (RampSegment) vibrationEffectSegment;
        return rampSegment.getStartAmplitude() == FullScreenMagnificationGestureHandler.MAX_SCALE
                && rampSegment.getEndAmplitude() == FullScreenMagnificationGestureHandler.MAX_SCALE;
    }

    public static VibrationEffectSegment updateDuration(
            VibrationEffectSegment vibrationEffectSegment, long j) {
        if (vibrationEffectSegment instanceof RampSegment) {
            RampSegment rampSegment = (RampSegment) vibrationEffectSegment;
            return new RampSegment(
                    rampSegment.getStartAmplitude(),
                    rampSegment.getEndAmplitude(),
                    rampSegment.getStartFrequencyHz(),
                    rampSegment.getEndFrequencyHz(),
                    (int) j);
        }
        if (!(vibrationEffectSegment instanceof StepSegment)) {
            return vibrationEffectSegment;
        }
        StepSegment stepSegment = (StepSegment) vibrationEffectSegment;
        return new StepSegment(stepSegment.getAmplitude(), stepSegment.getFrequencyHz(), (int) j);
    }

    @Override // com.android.server.vibrator.VibrationSegmentsAdapter
    public final int adaptToVibrator(VibratorInfo vibratorInfo, List list, int i) {
        List list2;
        int i2 = this.mRampDownDuration;
        if (i2 <= 0) {
            return i;
        }
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        int i3 = 1;
        int i4 = i;
        while (i3 < size) {
            StepSegment stepSegment = (VibrationEffectSegment) arrayList.get(i3 - 1);
            if (isOffSegment((VibrationEffectSegment) arrayList.get(i3))
                    && endsWithNonZeroAmplitude(stepSegment)) {
                long duration = ((VibrationEffectSegment) arrayList.get(i3)).getDuration();
                if (stepSegment instanceof StepSegment) {
                    StepSegment stepSegment2 = stepSegment;
                    list2 =
                            createStepsDown(
                                    stepSegment2.getAmplitude(),
                                    stepSegment2.getFrequencyHz(),
                                    duration);
                } else if (stepSegment instanceof RampSegment) {
                    RampSegment rampSegment = (RampSegment) stepSegment;
                    float endAmplitude = rampSegment.getEndAmplitude();
                    float endFrequencyHz = rampSegment.getEndFrequencyHz();
                    long j = i2;
                    list2 =
                            duration <= j
                                    ? Arrays.asList(
                                            new RampSegment(
                                                    endAmplitude,
                                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                                    endFrequencyHz,
                                                    endFrequencyHz,
                                                    (int) duration))
                                    : Arrays.asList(
                                            new RampSegment(
                                                    endAmplitude,
                                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                                    endFrequencyHz,
                                                    endFrequencyHz,
                                                    (int) j),
                                            new RampSegment(
                                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                                    endFrequencyHz,
                                                    endFrequencyHz,
                                                    (int) (duration - j)));
                } else {
                    list2 = null;
                }
                if (list2 != null) {
                    int size2 = list2.size() - 1;
                    VibrationEffectSegment vibrationEffectSegment =
                            (VibrationEffectSegment) arrayList.remove(i3);
                    arrayList.addAll(i3, list2);
                    if (i4 >= i3) {
                        if (i4 == i3) {
                            arrayList.add(vibrationEffectSegment);
                            i4++;
                            size++;
                        }
                        i4 += size2;
                    }
                    i3 += size2;
                    size += size2;
                }
            }
            i3++;
        }
        if (i4 >= 0) {
            int size3 = arrayList.size() - 1;
            if (endsWithNonZeroAmplitude((VibrationEffectSegment) arrayList.get(size3))
                    && isOffSegment((VibrationEffectSegment) arrayList.get(i4))) {
                StepSegment stepSegment3 = (VibrationEffectSegment) arrayList.get(size3);
                VibrationEffectSegment vibrationEffectSegment2 =
                        (VibrationEffectSegment) arrayList.get(i4);
                long duration2 = vibrationEffectSegment2.getDuration();
                long j2 = i2;
                if (duration2 > j2) {
                    arrayList.set(i4, updateDuration(vibrationEffectSegment2, duration2 - j2));
                    arrayList.add(i4, updateDuration(vibrationEffectSegment2, j2));
                }
                i4++;
                if (stepSegment3 instanceof StepSegment) {
                    StepSegment stepSegment4 = stepSegment3;
                    arrayList.addAll(
                            createStepsDown(
                                    stepSegment4.getAmplitude(),
                                    stepSegment4.getFrequencyHz(),
                                    Math.min(duration2, j2)));
                } else if (stepSegment3 instanceof RampSegment) {
                    RampSegment rampSegment2 = (RampSegment) stepSegment3;
                    float endAmplitude2 = rampSegment2.getEndAmplitude();
                    float endFrequencyHz2 = rampSegment2.getEndFrequencyHz();
                    arrayList.add(
                            new RampSegment(
                                    endAmplitude2,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    endFrequencyHz2,
                                    endFrequencyHz2,
                                    (int) Math.min(duration2, j2)));
                }
            }
        }
        return i4;
    }

    public final List createStepsDown(float f, float f2, long j) {
        int min = (int) Math.min(j, this.mRampDownDuration);
        int i = this.mStepDuration;
        int i2 = min / i;
        float f3 = f / i2;
        ArrayList arrayList = new ArrayList();
        for (int i3 = 1; i3 < i2; i3++) {
            arrayList.add(new StepSegment(f - (i3 * f3), f2, i));
        }
        arrayList.add(
                new StepSegment(
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        f2,
                        ((int) j) - ((i2 - 1) * i)));
        return arrayList;
    }
}
