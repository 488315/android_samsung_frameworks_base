package com.android.server.vibrator;

import android.os.SystemClock;
import android.os.Trace;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TurnOffVibratorStep extends AbstractVibratorStep {
    public final boolean mIsCleanUp;

    public TurnOffVibratorStep(VibrationStepConductor vibrationStepConductor, long j, VibratorController vibratorController, boolean z) {
        super(vibrationStepConductor, j, vibratorController, null, -1, j);
        this.mIsCleanUp = z;
    }

    @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
    public final List cancel() {
        return Arrays.asList(new TurnOffVibratorStep(this.conductor, SystemClock.uptimeMillis(), this.controller, true));
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
