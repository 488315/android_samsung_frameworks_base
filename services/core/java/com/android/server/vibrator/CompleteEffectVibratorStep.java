package com.android.server.vibrator;

import android.os.SystemClock;
import android.os.Trace;
import android.util.Slog;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.util.Arrays;
import java.util.List;

public final class CompleteEffectVibratorStep extends AbstractVibratorStep {
    public final boolean mCancelled;

    public CompleteEffectVibratorStep(
            VibrationStepConductor vibrationStepConductor,
            long j,
            boolean z,
            VibratorController vibratorController,
            long j2) {
        super(vibrationStepConductor, j, vibratorController, null, -1, j2);
        this.mCancelled = z;
    }

    @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
    public final List cancel() {
        return this.mCancelled
                ? Arrays.asList(
                        new TurnOffVibratorStep(
                                this.conductor, SystemClock.uptimeMillis(), this.controller, true))
                : super.cancel();
    }

    @Override // com.android.server.vibrator.Step
    public final boolean isCleanUp() {
        return this.mCancelled;
    }

    @Override // com.android.server.vibrator.Step
    public final List play() {
        Trace.traceBegin(8388608L, "CompleteEffectVibratorStep");
        try {
            StringBuilder sb = new StringBuilder("Running ");
            sb.append(this.mCancelled ? "cancel" : Constants.MESSAGE_TYPE_COMPLETE);
            sb.append(" vibration step on vibrator ");
            sb.append(this.controller.mVibratorInfo.getId());
            Slog.d("VibrationThread", sb.toString());
            if (this.mVibratorCompleteCallbackReceived) {
                stopVibrating();
                List list = VibrationStepConductor.EMPTY_STEP_LIST;
                Trace.traceEnd(8388608L);
                return list;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            float f = this.controller.mCurrentAmplitude;
            long min =
                    Math.min(
                            (this.mPendingVibratorOffDeadline - uptimeMillis) - 1000,
                            this.conductor.vibrationSettings.mVibrationConfig
                                    .getRampDownDurationMs());
            long rampStepDurationMs =
                    this.conductor.vibrationSettings.mVibrationConfig.getRampStepDurationMs();
            if (f >= 0.001f && min > rampStepDurationMs) {
                Slog.d(
                        "VibrationThread",
                        "Ramping down vibrator "
                                + this.controller.mVibratorInfo.getId()
                                + " from amplitude "
                                + f
                                + " for "
                                + min
                                + "ms");
                float f2 = f / (min / rampStepDurationMs);
                List asList =
                        Arrays.asList(
                                new RampOffVibratorStep(
                                        this.conductor,
                                        this.startTime,
                                        f - f2,
                                        f2,
                                        this.controller,
                                        this.mCancelled
                                                ? uptimeMillis + min
                                                : this.mPendingVibratorOffDeadline));
                Trace.traceEnd(8388608L);
                return asList;
            }
            if (!this.mCancelled) {
                List asList2 =
                        Arrays.asList(
                                new TurnOffVibratorStep(
                                        this.conductor,
                                        this.mPendingVibratorOffDeadline,
                                        this.controller,
                                        false));
                Trace.traceEnd(8388608L);
                return asList2;
            }
            stopVibrating();
            List list2 = VibrationStepConductor.EMPTY_STEP_LIST;
            Trace.traceEnd(8388608L);
            return list2;
        } catch (Throwable th) {
            Trace.traceEnd(8388608L);
            throw th;
        }
    }
}
