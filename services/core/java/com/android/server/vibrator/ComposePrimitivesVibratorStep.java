package com.android.server.vibrator;

import android.os.Trace;
import android.os.VibrationEffect;
import android.os.vibrator.PrimitiveSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.Slog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class ComposePrimitivesVibratorStep extends AbstractVibratorStep {
    public ComposePrimitivesVibratorStep(VibrationStepConductor vibrationStepConductor, long j, VibratorController vibratorController, VibrationEffect.Composed composed, int i, long j2) {
        super(vibrationStepConductor, Math.max(j, j2), vibratorController, composed, i, j2);
    }

    @Override // com.android.server.vibrator.Step
    public List play() {
        Trace.traceBegin(8388608L, "ComposePrimitivesStep");
        try {
            int compositionSizeMax = this.controller.getVibratorInfo().getCompositionSizeMax();
            VibrationEffect.Composed composed = this.effect;
            int i = this.segmentIndex;
            if (compositionSizeMax <= 0) {
                compositionSizeMax = 100;
            }
            List unrollPrimitiveSegments = unrollPrimitiveSegments(composed, i, compositionSizeMax);
            if (unrollPrimitiveSegments.isEmpty()) {
                Slog.w("VibrationThread", "Ignoring wrong segment for a ComposePrimitivesStep: " + this.effect.getSegments().get(this.segmentIndex));
                return nextSteps(1);
            }
            PrimitiveSegment[] primitiveSegmentArr = (PrimitiveSegment[]) unrollPrimitiveSegments.toArray(new PrimitiveSegment[unrollPrimitiveSegments.size()]);
            long m109on = this.controller.m109on(primitiveSegmentArr, getVibration().f1733id);
            handleVibratorOnResult(m109on);
            getVibration().stats.reportComposePrimitives(m109on, primitiveSegmentArr);
            return nextSteps(unrollPrimitiveSegments.size());
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public final List unrollPrimitiveSegments(VibrationEffect.Composed composed, int i, int i2) {
        ArrayList arrayList = new ArrayList(i2);
        int size = composed.getSegments().size();
        int repeatIndex = composed.getRepeatIndex();
        while (arrayList.size() < i2) {
            if (i == size) {
                if (repeatIndex < 0) {
                    break;
                }
                i = repeatIndex;
            }
            PrimitiveSegment primitiveSegment = (VibrationEffectSegment) composed.getSegments().get(i);
            if (!(primitiveSegment instanceof PrimitiveSegment)) {
                break;
            }
            arrayList.add(primitiveSegment);
            i++;
        }
        return arrayList;
    }
}
