package com.android.server.vibrator;

import android.os.SystemClock;
import android.os.Trace;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public final class RampOffVibratorStep extends AbstractVibratorStep {
  public final float mAmplitudeDelta;
  public final float mAmplitudeTarget;

  @Override // com.android.server.vibrator.Step
  public boolean isCleanUp() {
    return true;
  }

  public RampOffVibratorStep(
      VibrationStepConductor vibrationStepConductor,
      long j,
      float f,
      float f2,
      VibratorController vibratorController,
      long j2) {
    super(vibrationStepConductor, j, vibratorController, null, -1, j2);
    this.mAmplitudeTarget = f;
    this.mAmplitudeDelta = f2;
  }

  @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
  public List cancel() {
    return Arrays.asList(
        new TurnOffVibratorStep(this.conductor, SystemClock.uptimeMillis(), this.controller));
  }

  @Override // com.android.server.vibrator.Step
  public List play() {
    Trace.traceBegin(8388608L, "RampOffVibratorStep");
    try {
      if (this.mVibratorCompleteCallbackReceived) {
        stopVibrating();
        return VibrationStepConductor.EMPTY_STEP_LIST;
      }
      changeAmplitude(this.mAmplitudeTarget);
      float f = this.mAmplitudeTarget - this.mAmplitudeDelta;
      if (f < 0.001f) {
        return Arrays.asList(
            new TurnOffVibratorStep(
                this.conductor, this.mPendingVibratorOffDeadline, this.controller));
      }
      return Arrays.asList(
          new RampOffVibratorStep(
              this.conductor,
              this.startTime + r5.vibrationSettings.getRampStepDuration(),
              f,
              this.mAmplitudeDelta,
              this.controller,
              this.mPendingVibratorOffDeadline));
    } finally {
      Trace.traceEnd(8388608L);
    }
  }
}
