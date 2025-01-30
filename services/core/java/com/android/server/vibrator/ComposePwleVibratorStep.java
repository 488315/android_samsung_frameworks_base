package com.android.server.vibrator;

import android.os.Trace;
import android.os.VibrationEffect;
import android.os.vibrator.RampSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.Slog;
import com.android.server.display.DisplayPowerController2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class ComposePwleVibratorStep extends AbstractVibratorStep {
    public ComposePwleVibratorStep(VibrationStepConductor vibrationStepConductor, long j, VibratorController vibratorController, VibrationEffect.Composed composed, int i, long j2) {
        super(vibrationStepConductor, Math.max(j, j2), vibratorController, composed, i, j2);
    }

    @Override // com.android.server.vibrator.Step
    public List play() {
        Trace.traceBegin(8388608L, "ComposePwleStep");
        try {
            int pwleSizeMax = this.controller.getVibratorInfo().getPwleSizeMax();
            VibrationEffect.Composed composed = this.effect;
            int i = this.segmentIndex;
            if (pwleSizeMax <= 0) {
                pwleSizeMax = 100;
            }
            List unrollRampSegments = unrollRampSegments(composed, i, pwleSizeMax);
            if (unrollRampSegments.isEmpty()) {
                Slog.w("VibrationThread", "Ignoring wrong segment for a ComposePwleStep: " + this.effect.getSegments().get(this.segmentIndex));
                return nextSteps(1);
            }
            RampSegment[] rampSegmentArr = (RampSegment[]) unrollRampSegments.toArray(new RampSegment[unrollRampSegments.size()]);
            long m110on = this.controller.m110on(rampSegmentArr, getVibration().f1733id);
            handleVibratorOnResult(m110on);
            getVibration().stats.reportComposePwle(m110on, rampSegmentArr);
            return nextSteps(unrollRampSegments.size());
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public final List unrollRampSegments(VibrationEffect.Composed composed, int i, int i2) {
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
            if (isBetterBreakPosition(arrayList, f, i2)) {
                f = rampSegment2.getEndAmplitude();
                i3 = arrayList.size();
            }
            i++;
        }
        return arrayList.size() > i2 ? arrayList.subList(0, i3) : arrayList;
    }

    public final boolean isBetterBreakPosition(List list, float f, int i) {
        float endAmplitude = ((RampSegment) list.get(list.size() - 1)).getEndAmplitude();
        int size = list.size();
        if (size > i) {
            return false;
        }
        if (endAmplitude == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            return true;
        }
        return size >= i / 2 && endAmplitude <= f;
    }
}
