package com.android.server.vibrator;

import android.os.Trace;
import android.util.Slog;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class FinishSequentialEffectStep extends Step {
    public final StartSequentialEffectStep startedStep;

    public FinishSequentialEffectStep(StartSequentialEffectStep startSequentialEffectStep) {
        super(startSequentialEffectStep.conductor, Long.MAX_VALUE);
        this.startedStep = startSequentialEffectStep;
    }

    @Override // com.android.server.vibrator.Step
    public final List cancel() {
        cancelImmediately();
        return VibrationStepConductor.EMPTY_STEP_LIST;
    }

    @Override // com.android.server.vibrator.Step
    public final void cancelImmediately() {
        VibrationStepConductor vibrationStepConductor = this.conductor;
        vibrationStepConductor.vibratorManagerHooks.noteVibratorOff(
                vibrationStepConductor.mVibration.callerInfo.uid);
    }

    @Override // com.android.server.vibrator.Step
    public final List play() {
        Trace.traceBegin(8388608L, "FinishSequentialEffectStep");
        try {
            Slog.d(
                    "VibrationThread",
                    "FinishSequentialEffectStep for effect #" + this.startedStep.currentIndex);
            VibrationStepConductor vibrationStepConductor = this.conductor;
            vibrationStepConductor.vibratorManagerHooks.noteVibratorOff(
                    vibrationStepConductor.mVibration.callerInfo.uid);
            StartSequentialEffectStep nextStep = this.startedStep.nextStep();
            List asList =
                    nextStep == null
                            ? VibrationStepConductor.EMPTY_STEP_LIST
                            : Arrays.asList(nextStep);
            Trace.traceEnd(8388608L);
            return asList;
        } catch (Throwable th) {
            Trace.traceEnd(8388608L);
            throw th;
        }
    }
}
