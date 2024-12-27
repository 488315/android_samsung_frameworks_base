package com.android.server.vibrator;

import android.os.Trace;
import android.os.VibrationEffect;
import android.os.vibrator.RampSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.Slog;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.ArrayList;
import java.util.List;

public final class ComposePwleVibratorStep extends AbstractVibratorStep {
    public static List unrollRampSegments(VibrationEffect.Composed composed, int i, int i2) {
        ArrayList arrayList = new ArrayList(i2);
        int size = composed.getSegments().size();
        int repeatIndex = composed.getRepeatIndex();
        float f = 1.0f;
        int i3 = i2;
        while (arrayList.size() <= i2) {
            if (i == size) {
                if (repeatIndex < 0) {
                    break;
                }
                i = repeatIndex;
            }
            RampSegment rampSegment = (VibrationEffectSegment) composed.getSegments().get(i);
            if (!(rampSegment instanceof RampSegment)) {
                break;
            }
            RampSegment rampSegment2 = rampSegment;
            arrayList.add(rampSegment2);
            float endAmplitude =
                    ((RampSegment) arrayList.get(arrayList.size() - 1)).getEndAmplitude();
            int size2 = arrayList.size();
            if (size2 <= i2
                    && (endAmplitude == FullScreenMagnificationGestureHandler.MAX_SCALE
                            || (size2 >= i2 / 2 && endAmplitude <= f))) {
                f = rampSegment2.getEndAmplitude();
                i3 = arrayList.size();
            }
            i++;
        }
        return arrayList.size() > i2 ? arrayList.subList(0, i3) : arrayList;
    }

    @Override // com.android.server.vibrator.Step
    public final List play() {
        long composePwle;
        Trace.traceBegin(8388608L, "ComposePwleStep");
        try {
            int pwleSizeMax = this.controller.mVibratorInfo.getPwleSizeMax();
            VibrationEffect.Composed composed = this.effect;
            int i = this.segmentIndex;
            if (pwleSizeMax <= 0) {
                pwleSizeMax = 100;
            }
            List unrollRampSegments = unrollRampSegments(composed, i, pwleSizeMax);
            if (unrollRampSegments.isEmpty()) {
                Slog.w(
                        "VibrationThread",
                        "Ignoring wrong segment for a ComposePwleStep: "
                                + this.effect.getSegments().get(this.segmentIndex));
                return nextSteps(1);
            }
            Slog.d(
                    "VibrationThread",
                    "Compose "
                            + unrollRampSegments
                            + " PWLEs on vibrator "
                            + this.controller.mVibratorInfo.getId());
            RampSegment[] rampSegmentArr =
                    (RampSegment[])
                            unrollRampSegments.toArray(new RampSegment[unrollRampSegments.size()]);
            VibratorController vibratorController = this.controller;
            long j = this.conductor.mVibration.id;
            if (vibratorController.mVibratorInfo.hasCapability(1024L)) {
                synchronized (vibratorController.mLock) {
                    try {
                        composePwle =
                                vibratorController.mNativeWrapper.composePwle(
                                        rampSegmentArr,
                                        vibratorController.mVibratorInfo.getDefaultBraking(),
                                        j);
                        if (composePwle > 0) {
                            vibratorController.mCurrentAmplitude = -1.0f;
                            vibratorController.notifyListenerOnVibrating(true);
                        }
                    } finally {
                    }
                }
            } else {
                composePwle = 0;
            }
            handleVibratorOnResult(composePwle);
            VibrationStats vibrationStats = this.conductor.mVibration.stats;
            vibrationStats.mVibratorComposePwleCount++;
            vibrationStats.mVibrationPwleTotalSize += rampSegmentArr.length;
            if (composePwle > 0) {
                for (RampSegment rampSegment : rampSegmentArr) {
                    if (rampSegment.getStartAmplitude()
                                    == FullScreenMagnificationGestureHandler.MAX_SCALE
                            && rampSegment.getEndAmplitude()
                                    == FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        composePwle -= rampSegment.getDuration();
                    }
                }
                if (composePwle > 0) {
                    vibrationStats.mVibratorOnTotalDurationMillis += (int) composePwle;
                }
            }
            return nextSteps(unrollRampSegments.size());
        } finally {
            Trace.traceEnd(8388608L);
        }
    }
}
