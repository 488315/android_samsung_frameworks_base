package com.android.server.vibrator;

import android.os.SystemClock;
import android.os.Trace;
import android.os.VibrationEffect;
import android.os.vibrator.SemHapticSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.Slog;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.vibrator.VibRune;
import java.util.List;

/* loaded from: classes3.dex */
public final class SemHapticStep extends AbstractVibratorStep {
  public long mNextOffTime;

  public SemHapticStep(
      VibrationStepConductor vibrationStepConductor,
      long j,
      VibratorController vibratorController,
      VibrationEffect.Composed composed,
      int i,
      long j2) {
    super(vibrationStepConductor, j, vibratorController, composed, i, j2);
    this.mNextOffTime = j2;
  }

  @Override // com.android.server.vibrator.AbstractVibratorStep, com.android.server.vibrator.Step
  public boolean acceptVibratorCompleteCallback(int i) {
    if (this.controller.getVibratorInfo().getId() == i) {
      this.mVibratorCompleteCallbackReceived = true;
      this.mNextOffTime = SystemClock.uptimeMillis();
    }
    return this.mNextOffTime < this.startTime
        && this.controller.getCurrentAmplitude() > DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  }

  @Override // com.android.server.vibrator.Step
  public List play() {
    Trace.traceBegin(8388608L, "SemHapticStep");
    try {
      SemHapticSegment semHapticSegment =
          (VibrationEffectSegment) this.effect.getSegments().get(this.segmentIndex);
      int i = 1;
      if (!(semHapticSegment instanceof SemHapticSegment)) {
        Slog.w(
            "SemHapticStep", "Ignoring wrong segment for a SemHapticSegment: " + semHapticSegment);
        return nextSteps(1);
      }
      HalVibration vibration = this.conductor.getVibration();
      int magnitude = vibration.getMagnitude();
      int sepIndex = semHapticSegment.getSepIndex();
      long timeOut = vibration.getTimeOut();
      boolean z = this.effect.getRepeatIndex() > -1;
      Slog.d(
          "SemHapticStep",
          "sepIndex="
              + sepIndex
              + ", intensity="
              + magnitude
              + ", repeat="
              + z
              + ", duration="
              + timeOut);
      startVibrating(sepIndex, magnitude, z, timeOut);
      if (z && vibration.getEngineData() == null) {
        this.mNextOffTime = 9223372036854770807L;
        i = -1;
      } else {
        this.mNextOffTime = this.startTime + timeOut;
      }
      return nextSteps(this.mNextOffTime, i);
    } finally {
      Trace.traceEnd(8388608L);
    }
  }

  public final void startVibrating(int i, int i2, boolean z, long j) {
    Slog.d(
        "SemHapticStep",
        "Turning on vibrator " + this.controller.getVibratorInfo().getId() + " for " + j + "ms");
    HalVibration vibration = this.conductor.getVibration();
    if (VibRune.SUPPORT_HYBRID_HAPTIC() && vibration.getCommonData() != null) {
      int i3 = 1;
      int[] iArr = new int[(vibration.getCommonData().length * 5) + 1];
      int i4 = 0;
      iArr[0] = vibration.getCommonData().length;
      while (i4 < vibration.getCommonData().length) {
        int i5 = i3 + 1;
        iArr[i3] = vibration.getCommonData()[i4].type;
        int i6 = i5 + 1;
        iArr[i5] = vibration.getCommonData()[i4].index;
        int i7 = i6 + 1;
        iArr[i6] = vibration.getCommonData()[i4].scale;
        int i8 = i7 + 1;
        iArr[i7] = vibration.getCommonData()[i4].duration;
        iArr[i8] = vibration.getCommonData()[i4].frequency;
        i4++;
        i3 = i8 + 1;
      }
      this.controller.performCommonInputff(iArr, z, i2);
      return;
    }
    if (vibration.getEngineData() == null) {
      this.controller.performPrebakedHapticPattern(i, i2, z);
    } else {
      this.controller.performHapticEngine((int[]) vibration.getEngineData().clone(), i2);
    }
  }
}
