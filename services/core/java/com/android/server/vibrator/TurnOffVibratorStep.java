package com.android.server.vibrator;

import android.os.SystemClock;
import android.os.Trace;

import java.util.Arrays;
import java.util.List;

public final class TurnOffVibratorStep extends AbstractVibratorStep {
    public final boolean mIsCleanUp;

    public TurnOffVibratorStep(
            VibrationStepConductor vibrationStepConductor,
            long j,
            VibratorController vibratorController,
            boolean z) {
        super(vibrationStepConductor, j, vibratorController, null, -1, j);
        this.mIsCleanUp = z;
    }

    @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
    public final List cancel() {
        return Arrays.asList(
                new TurnOffVibratorStep(
                        this.conductor, SystemClock.uptimeMillis(), this.controller, true));
    }

    @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
    public final void cancelImmediately() {
        stopVibrating();
    }

    @Override // com.android.server.vibrator.Step
    public final boolean isCleanUp() {
        return this.mIsCleanUp;
    }

    @Override // com.android.server.vibrator.Step
    public final List play() {
        Trace.traceBegin(8388608L, "TurnOffVibratorStep");
        try {
            stopVibrating();
            return VibrationStepConductor.EMPTY_STEP_LIST;
        } finally {
            Trace.traceEnd(8388608L);
        }
    }
}
