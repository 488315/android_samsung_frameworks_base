package com.android.server.vibrator;

import android.os.SystemClock;
import android.os.Trace;
import android.util.Slog;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RampOffVibratorStep extends AbstractVibratorStep {
    public final float mAmplitudeDelta;
    public final float mAmplitudeTarget;

    public RampOffVibratorStep(VibrationStepConductor vibrationStepConductor, long j, float f, float f2, VibratorController vibratorController, long j2) {
        super(vibrationStepConductor, j, vibratorController, null, -1, j2);
        this.mAmplitudeTarget = f;
        this.mAmplitudeDelta = f2;
    }

    @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
    public final List cancel() {
        return Arrays.asList(new TurnOffVibratorStep(this.conductor, SystemClock.uptimeMillis(), this.controller, true));
    }

    @Override // com.android.server.vibrator.Step
    public final boolean isCleanUp() {
        return true;
    }

    @Override // com.android.server.vibrator.Step
    public final List play() {
        Trace.traceBegin(8388608L, "RampOffVibratorStep");
        try {
            Slog.d("VibrationThread", "Ramp down the vibrator amplitude, step with " + (SystemClock.uptimeMillis() - this.startTime) + "ms latency.");
            if (this.mVibratorCompleteCallbackReceived) {
                stopVibrating();
                return VibrationStepConductor.EMPTY_STEP_LIST;
            }
            changeAmplitude(this.mAmplitudeTarget);
            float f = this.mAmplitudeTarget - this.mAmplitudeDelta;
            if (f < 0.001f) {
                return Arrays.asList(new TurnOffVibratorStep(this.conductor, this.mPendingVibratorOffDeadline, this.controller, true));
            }
            return Arrays.asList(new RampOffVibratorStep(this.conductor, this.startTime + r5.vibrationSettings.mVibrationConfig.getRampStepDurationMs(), f, this.mAmplitudeDelta, this.controller, this.mPendingVibratorOffDeadline));
        } finally {
            Trace.traceEnd(8388608L);
        }
    }
}
