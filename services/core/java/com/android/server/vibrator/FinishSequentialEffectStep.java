package com.android.server.vibrator;

import android.os.Trace;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public final class FinishSequentialEffectStep extends Step {
    public final StartSequentialEffectStep startedStep;

    @Override // com.android.server.vibrator.Step
    public boolean isCleanUp() {
        return true;
    }

    public FinishSequentialEffectStep(StartSequentialEffectStep startSequentialEffectStep) {
        super(startSequentialEffectStep.conductor, Long.MAX_VALUE);
        this.startedStep = startSequentialEffectStep;
    }

    @Override // com.android.server.vibrator.Step
    public List play() {
        List asList;
        Trace.traceBegin(8388608L, "FinishSequentialEffectStep");
        try {
            VibrationStepConductor vibrationStepConductor = this.conductor;
            vibrationStepConductor.vibratorManagerHooks.noteVibratorOff(vibrationStepConductor.getVibration().callerInfo.uid);
            Step nextStep = this.startedStep.nextStep();
            if (nextStep == null) {
                asList = VibrationStepConductor.EMPTY_STEP_LIST;
            } else {
                asList = Arrays.asList(nextStep);
            }
            return asList;
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    @Override // com.android.server.vibrator.Step
    public List cancel() {
        cancelImmediately();
        return VibrationStepConductor.EMPTY_STEP_LIST;
    }

    @Override // com.android.server.vibrator.Step
    public void cancelImmediately() {
        VibrationStepConductor vibrationStepConductor = this.conductor;
        vibrationStepConductor.vibratorManagerHooks.noteVibratorOff(vibrationStepConductor.getVibration().callerInfo.uid);
    }
}
