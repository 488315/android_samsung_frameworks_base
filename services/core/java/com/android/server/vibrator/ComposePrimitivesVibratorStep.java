package com.android.server.vibrator;

import android.os.Trace;
import android.os.VibrationEffect;
import android.os.vibrator.PrimitiveSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.Slog;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ComposePrimitivesVibratorStep extends AbstractVibratorStep {
    @Override // com.android.server.vibrator.Step
    public final List play() {
        long compose;
        Trace.traceBegin(8388608L, "ComposePrimitivesStep");
        try {
            int compositionSizeMax = this.controller.mVibratorInfo.getCompositionSizeMax();
            VibrationEffect.Composed composed = this.effect;
            int i = this.segmentIndex;
            if (compositionSizeMax <= 0) {
                compositionSizeMax = 100;
            }
            ArrayList arrayList = new ArrayList(compositionSizeMax);
            int size = composed.getSegments().size();
            int repeatIndex = composed.getRepeatIndex();
            while (arrayList.size() < compositionSizeMax) {
                if (i == size) {
                    if (repeatIndex < 0) {
                        break;
                    }
                    i = repeatIndex;
                }
                PrimitiveSegment primitiveSegment =
                        (VibrationEffectSegment) composed.getSegments().get(i);
                if (!(primitiveSegment instanceof PrimitiveSegment)) {
                    break;
                }
                arrayList.add(primitiveSegment);
                i++;
            }
            if (arrayList.isEmpty()) {
                Slog.w(
                        "VibrationThread",
                        "Ignoring wrong segment for a ComposePrimitivesStep: "
                                + this.effect.getSegments().get(this.segmentIndex));
                return nextSteps(1);
            }
            Slog.d(
                    "VibrationThread",
                    "Compose " + arrayList + " primitives on vibrator " + getVibratorId());
            PrimitiveSegment[] primitiveSegmentArr =
                    (PrimitiveSegment[]) arrayList.toArray(new PrimitiveSegment[arrayList.size()]);
            VibratorController vibratorController = this.controller;
            long j = this.conductor.mVibration.id;
            long j2 = 0;
            if (vibratorController.mVibratorInfo.hasCapability(32L)) {
                synchronized (vibratorController.mLock) {
                    try {
                        compose = vibratorController.mNativeWrapper.compose(primitiveSegmentArr, j);
                        if (compose > 0) {
                            vibratorController.mCurrentAmplitude = -1.0f;
                            vibratorController.notifyListenerOnVibrating(true);
                        }
                    } finally {
                    }
                }
                j2 = compose;
            }
            handleVibratorOnResult(j2);
            this.conductor.mVibration.stats.reportComposePrimitives(primitiveSegmentArr, j2);
            return nextSteps(arrayList.size());
        } finally {
            Trace.traceEnd(8388608L);
        }
    }
}
