package com.android.server.vibrator;

import android.os.Trace;
import android.os.VibrationEffect;
import android.os.vibrator.PrebakedSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.Slog;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PerformPrebakedVibratorStep extends AbstractVibratorStep {
    @Override // com.android.server.vibrator.Step
    public final List play() {
        long perform;
        Trace.traceBegin(8388608L, "PerformPrebakedVibratorStep");
        try {
            PrebakedSegment prebakedSegment =
                    (VibrationEffectSegment) this.effect.getSegments().get(this.segmentIndex);
            if (!(prebakedSegment instanceof PrebakedSegment)) {
                Slog.w(
                        "VibrationThread",
                        "Ignoring wrong segment for a PerformPrebakedVibratorStep: "
                                + prebakedSegment);
                return nextSteps(1);
            }
            PrebakedSegment prebakedSegment2 = prebakedSegment;
            Slog.d(
                    "VibrationThread",
                    "Perform "
                            + VibrationEffect.effectIdToString(prebakedSegment2.getEffectId())
                            + " on vibrator "
                            + this.controller.mVibratorInfo.getId());
            VibrationEffect vibrationEffect =
                    (VibrationEffect)
                            this.conductor.mVibration.mFallbacks.get(
                                    prebakedSegment2.getEffectId());
            VibratorController vibratorController = this.controller;
            long j = this.conductor.mVibration.id;
            synchronized (vibratorController.mLock) {
                try {
                    perform =
                            vibratorController.mNativeWrapper.perform(
                                    prebakedSegment2.getEffectId(),
                                    prebakedSegment2.getEffectStrength(),
                                    j);
                    if (perform > 0) {
                        vibratorController.mCurrentAmplitude = -1.0f;
                        vibratorController.notifyListenerOnVibrating(true);
                    }
                } finally {
                }
            }
            handleVibratorOnResult(perform);
            VibrationStats vibrationStats = this.conductor.mVibration.stats;
            vibrationStats.mVibratorPerformCount++;
            if (perform > 0) {
                vibrationStats.mVibratorEffectsUsed.put(prebakedSegment2.getEffectId(), true);
                vibrationStats.mVibratorOnTotalDurationMillis += (int) perform;
            } else {
                vibrationStats.mVibratorEffectsUsed.put(prebakedSegment2.getEffectId(), false);
            }
            if (perform != 0
                    || !prebakedSegment2.shouldFallback()
                    || !(vibrationEffect instanceof VibrationEffect.Composed)) {
                return nextSteps(1);
            }
            Slog.d(
                    "VibrationThread",
                    "Playing fallback for effect "
                            + VibrationEffect.effectIdToString(prebakedSegment2.getEffectId()));
            AbstractVibratorStep nextVibrateStep =
                    this.conductor.nextVibrateStep(
                            this.startTime,
                            this.controller,
                            replaceCurrentSegment((VibrationEffect.Composed) vibrationEffect),
                            this.segmentIndex,
                            this.mPendingVibratorOffDeadline);
            List play = nextVibrateStep.play();
            handleVibratorOnResult(nextVibrateStep.mVibratorOnResult);
            return play;
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public final VibrationEffect.Composed replaceCurrentSegment(VibrationEffect.Composed composed) {
        ArrayList arrayList = new ArrayList(this.effect.getSegments());
        int repeatIndex = this.effect.getRepeatIndex();
        arrayList.remove(this.segmentIndex);
        arrayList.addAll(this.segmentIndex, composed.getSegments());
        if (this.segmentIndex < this.effect.getRepeatIndex()) {
            repeatIndex += composed.getSegments().size() - 1;
        }
        return new VibrationEffect.Composed(arrayList, repeatIndex);
    }
}
