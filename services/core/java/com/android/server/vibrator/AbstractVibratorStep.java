package com.android.server.vibrator;

import android.os.SystemClock;
import android.os.VibrationEffect;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AbstractVibratorStep extends Step {
    public final VibratorController controller;
    public final VibrationEffect.Composed effect;
    public long mPendingVibratorOffDeadline;
    public boolean mVibratorCompleteCallbackReceived;
    public long mVibratorOnResult;
    public final int segmentIndex;

    public AbstractVibratorStep(
            VibrationStepConductor vibrationStepConductor,
            long j,
            VibratorController vibratorController,
            VibrationEffect.Composed composed,
            int i,
            long j2) {
        super(vibrationStepConductor, j);
        this.controller = vibratorController;
        this.effect = composed;
        this.segmentIndex = i;
        this.mPendingVibratorOffDeadline = j2;
    }

    @Override // com.android.server.vibrator.Step
    public boolean acceptVibratorCompleteCallback(int i) {
        if (getVibratorId() != i) {
            return false;
        }
        boolean z = this.mPendingVibratorOffDeadline > SystemClock.uptimeMillis();
        Slog.d("VibrationThread", "Received completion callback from " + i + ", accepted = " + z);
        this.mPendingVibratorOffDeadline = 0L;
        this.mVibratorCompleteCallbackReceived = true;
        return z;
    }

    @Override // com.android.server.vibrator.Step
    public List cancel() {
        return Arrays.asList(
                new CompleteEffectVibratorStep(
                        this.conductor,
                        SystemClock.uptimeMillis(),
                        true,
                        this.controller,
                        this.mPendingVibratorOffDeadline));
    }

    @Override // com.android.server.vibrator.Step
    public void cancelImmediately() {
        if (this.mPendingVibratorOffDeadline > SystemClock.uptimeMillis()) {
            stopVibrating();
        }
    }

    public final void changeAmplitude(float f) {
        Slog.d("VibrationThread", "Amplitude changed on vibrator " + getVibratorId() + " to " + f);
        this.controller.setAmplitude(f);
        VibrationStats vibrationStats = this.conductor.mVibration.stats;
        vibrationStats.mVibratorSetAmplitudeCount = vibrationStats.mVibratorSetAmplitudeCount + 1;
    }

    public final int getVibratorId() {
        return this.controller.mVibratorInfo.getId();
    }

    @Override // com.android.server.vibrator.Step
    public final long getVibratorOnDuration() {
        return this.mVibratorOnResult;
    }

    public final void handleVibratorOnResult(long j) {
        this.mVibratorOnResult = j;
        StringBuilder sb = new StringBuilder("Turned on vibrator ");
        sb.append(getVibratorId());
        sb.append(", result = ");
        BatteryService$$ExternalSyntheticOutline0.m(sb, this.mVibratorOnResult, "VibrationThread");
        if (this.mVibratorOnResult > 0) {
            this.mPendingVibratorOffDeadline =
                    SystemClock.uptimeMillis() + this.mVibratorOnResult + 1000;
        } else {
            this.mPendingVibratorOffDeadline = 0L;
        }
    }

    public final List nextSteps(int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = this.mVibratorOnResult;
        if (j > 0) {
            uptimeMillis += j;
        }
        return nextSteps(i, uptimeMillis);
    }

    public final List nextSteps(int i, long j) {
        int i2 = this.segmentIndex + i;
        int size = this.effect.getSegments().size();
        int repeatIndex = this.effect.getRepeatIndex();
        if (i2 >= size && repeatIndex >= 0) {
            int i3 = size - repeatIndex;
            this.conductor.mVibration.stats.mRepeatCount += (i2 - repeatIndex) / i3;
            i2 = ((i2 - size) % i3) + repeatIndex;
        }
        return Arrays.asList(
                this.conductor.nextVibrateStep(
                        j, this.controller, this.effect, i2, this.mPendingVibratorOffDeadline));
    }

    public final void stopVibrating() {
        Slog.d("VibrationThread", "Turning off vibrator " + getVibratorId());
        this.controller.off();
        this.conductor.mVibration.stats.mVibratorOffCount++;
        this.mPendingVibratorOffDeadline = 0L;
    }
}
