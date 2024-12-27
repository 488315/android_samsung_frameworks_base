package com.android.server.vibrator;

import android.os.SystemClock;
import android.os.Trace;
import android.os.VibrationEffect;
import android.os.vibrator.StepSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.Slog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SetAmplitudeVibratorStep extends AbstractVibratorStep {
    @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
    public final boolean acceptVibratorCompleteCallback(int i) {
        boolean z = false;
        if (!super.acceptVibratorCompleteCallback(i)) {
            return false;
        }
        if (SystemClock.uptimeMillis() < this.startTime && this.controller.mCurrentAmplitude > FullScreenMagnificationGestureHandler.MAX_SCALE) {
            z = true;
        }
        Slog.d("VibrationThread", "Amplitude step received completion callback from " + i + ", accepted = " + z);
        return z;
    }

    public final long getVibratorOnDuration(VibrationEffect.Composed composed, int i) {
        List segments = composed.getSegments();
        int size = segments.size();
        int repeatIndex = composed.getRepeatIndex();
        int i2 = i;
        long j = 0;
        while (i2 < size) {
            StepSegment stepSegment = (VibrationEffectSegment) segments.get(i2);
            if (!(stepSegment instanceof StepSegment) || (stepSegment.getDuration() > 0 && stepSegment.getAmplitude() == FullScreenMagnificationGestureHandler.MAX_SCALE)) {
                break;
            }
            j += stepSegment.getDuration();
            i2++;
            if (i2 == size && repeatIndex >= 0) {
                i2 = repeatIndex;
                repeatIndex = -1;
            }
            if (i2 == i) {
                return Math.max(j, 5000L);
            }
        }
        return (i2 != size || composed.getRepeatIndex() >= 0) ? j : j + this.conductor.vibrationSettings.mVibrationConfig.getRampDownDurationMs();
    }

    @Override // com.android.server.vibrator.Step
    public final List play() {
        Trace.traceBegin(8388608L, "SetAmplitudeVibratorStep");
        try {
            long uptimeMillis = SystemClock.uptimeMillis();
            long j = uptimeMillis - this.startTime;
            Slog.d("VibrationThread", "Running amplitude step with " + j + "ms latency.");
            if (this.mVibratorCompleteCallbackReceived && j < 0) {
                turnVibratorBackOn(-j);
                return Arrays.asList(new SetAmplitudeVibratorStep(this.conductor, this.startTime, this.controller, this.effect, this.segmentIndex, this.mPendingVibratorOffDeadline));
            }
            VibrationEffectSegment vibrationEffectSegment = (VibrationEffectSegment) this.effect.getSegments().get(this.segmentIndex);
            if (!(vibrationEffectSegment instanceof StepSegment)) {
                Slog.w("VibrationThread", "Ignoring wrong segment for a SetAmplitudeVibratorStep: " + vibrationEffectSegment);
                return nextSteps(1, this.startTime);
            }
            StepSegment stepSegment = (StepSegment) vibrationEffectSegment;
            if (stepSegment.getDuration() == 0) {
                return nextSteps(1, this.startTime);
            }
            if (stepSegment.getAmplitude() != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                long vibratorOnDuration = getVibratorOnDuration(this.effect, this.segmentIndex);
                if (vibratorOnDuration > 0) {
                    this.mVibratorOnResult = startVibrating(vibratorOnDuration, stepSegment);
                }
            } else if (this.mPendingVibratorOffDeadline > uptimeMillis) {
                stopVibrating();
            }
            return nextSteps(1, this.startTime + vibrationEffectSegment.getDuration());
        } finally {
            Trace.traceEnd(8388608L);
        }
    }

    public final long startVibrating(long j, StepSegment stepSegment) {
        StringBuilder sb = new StringBuilder("Turning on vibrator ");
        sb.append(this.controller.mVibratorInfo.getId());
        sb.append(" for ");
        sb.append(j);
        BootReceiver$$ExternalSyntheticOutline0.m(sb, "ms", "VibrationThread");
        if (this.controller.mSupportIntensityControl && stepSegment.getAmplitude() != -1.0f) {
            this.controller.setAmplitude(stepSegment.getAmplitude());
        }
        return this.controller.on(j, this.conductor.mVibration.id);
    }

    public final void turnVibratorBackOn(long j) {
        long vibratorOnDuration = getVibratorOnDuration(this.effect, this.segmentIndex);
        if (vibratorOnDuration <= 0) {
            return;
        }
        long j2 = vibratorOnDuration + j;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Turning the vibrator back ON using the remaining duration of ", j, "ms, for a total of ");
        m.append(j2);
        m.append("ms");
        Slog.d("VibrationThread", m.toString());
        float f = this.controller.mCurrentAmplitude;
        StringBuilder sb = new StringBuilder("Turning on vibrator ");
        sb.append(this.controller.mVibratorInfo.getId());
        sb.append(" for ");
        sb.append(j2);
        BootReceiver$$ExternalSyntheticOutline0.m(sb, "ms", "VibrationThread");
        long on = this.controller.on(j2, this.conductor.mVibration.id);
        handleVibratorOnResult(on);
        VibrationStats vibrationStats = this.conductor.mVibration.stats;
        vibrationStats.mVibratorOnCount++;
        if (on > 0) {
            vibrationStats.mVibratorOnTotalDurationMillis += (int) on;
        }
        if (on > 0) {
            changeAmplitude(f);
        }
    }
}
