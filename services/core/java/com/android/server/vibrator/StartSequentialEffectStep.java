package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.SystemClock;
import android.os.Trace;
import android.os.VibrationEffect;
import android.os.vibrator.PrebakedSegment;
import android.os.vibrator.PrimitiveSegment;
import android.os.vibrator.StepSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.SparseArray;
import com.samsung.android.vibrator.VibRune;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class StartSequentialEffectStep extends Step {
  public final int currentIndex;
  public long mVibratorsOnMaxDuration;
  public final CombinedVibration.Sequential sequentialEffect;

  @Override // com.android.server.vibrator.Step
  public void cancelImmediately() {}

  public StartSequentialEffectStep(
      VibrationStepConductor vibrationStepConductor, CombinedVibration.Sequential sequential) {
    this(
        vibrationStepConductor,
        SystemClock.uptimeMillis() + ((Integer) sequential.getDelays().get(0)).intValue(),
        sequential,
        0);
  }

  public StartSequentialEffectStep(
      VibrationStepConductor vibrationStepConductor,
      long j,
      CombinedVibration.Sequential sequential,
      int i) {
    super(vibrationStepConductor, j);
    this.sequentialEffect = sequential;
    this.currentIndex = i;
  }

  @Override // com.android.server.vibrator.Step
  public long getVibratorOnDuration() {
    return this.mVibratorsOnMaxDuration;
  }

  @Override // com.android.server.vibrator.Step
  public List play() {
    Trace.traceBegin(8388608L, "StartSequentialEffectStep");
    ArrayList arrayList = new ArrayList();
    this.mVibratorsOnMaxDuration = -1L;
    try {
      DeviceEffectMap createEffectToVibratorMapping =
          createEffectToVibratorMapping(
              (CombinedVibration) this.sequentialEffect.getEffects().get(this.currentIndex));
      if (createEffectToVibratorMapping == null) {
        return arrayList;
      }
      if (this.conductor.getVibrators().size() != 0) {
        VibrationEffect.Composed composed = this.conductor.getComposed();
        StepSegment stepSegment = (VibrationEffectSegment) composed.getSegments().get(0);
        VibratorController vibratorController =
            (VibratorController) this.conductor.getVibrators().get(0);
        if (vibratorController == null) {
          long j = this.mVibratorsOnMaxDuration;
          if (j >= 0) {
            Step finishSequentialEffectStep =
                j > 0 ? new FinishSequentialEffectStep(this) : nextStep();
            if (finishSequentialEffectStep != null) {
              arrayList.add(finishSequentialEffectStep);
            }
          }
          Trace.traceEnd(8388608L);
          return arrayList;
        }
        if (stepSegment instanceof StepSegment) {
          StepSegment stepSegment2 = stepSegment;
          int semGetMagnitude =
              composed.semGetMagnitude() > -1
                  ? composed.semGetMagnitude()
                  : this.conductor.getVibration().getMagnitude();
          if (VibRune.SUPPORT_RAM_INDEX_HAPTIC()) {
            vibratorController.performPrebakedHapticPattern(0L, semGetMagnitude, false);
          } else if (vibratorController.isSupportIntensityControl()) {
            vibratorController.setIntensity(semGetMagnitude);
          }
          if (vibratorController.isSupportFrequencyControl()) {
            vibratorController.setFrequencyType((long) stepSegment2.getFrequencyHz());
          }
        } else if (stepSegment instanceof PrimitiveSegment) {
          if (vibratorController.isSupportIntensityControl()) {
            vibratorController.setIntensity(
                composed.semGetMagnitude() > -1
                    ? composed.semGetMagnitude()
                    : this.conductor.getVibration().getMagnitude());
          } else {
            vibratorController.setAmplitude(1.0f);
          }
        }
      }
      this.mVibratorsOnMaxDuration = startVibrating(createEffectToVibratorMapping, arrayList);
      VibrationStepConductor vibrationStepConductor = this.conductor;
      vibrationStepConductor.vibratorManagerHooks.noteVibratorOn(
          vibrationStepConductor.getVibration().callerInfo.uid, this.mVibratorsOnMaxDuration);
      long j2 = this.mVibratorsOnMaxDuration;
      if (j2 >= 0) {
        Step finishSequentialEffectStep2 =
            j2 > 0 ? new FinishSequentialEffectStep(this) : nextStep();
        if (finishSequentialEffectStep2 != null) {
          arrayList.add(finishSequentialEffectStep2);
        }
      }
      Trace.traceEnd(8388608L);
      return arrayList;
    } finally {
      long j3 = this.mVibratorsOnMaxDuration;
      if (j3 >= 0) {
        Step finishSequentialEffectStep3 =
            j3 > 0 ? new FinishSequentialEffectStep(this) : nextStep();
        if (finishSequentialEffectStep3 != null) {
          arrayList.add(finishSequentialEffectStep3);
        }
      }
      Trace.traceEnd(8388608L);
    }
  }

  @Override // com.android.server.vibrator.Step
  public List cancel() {
    return VibrationStepConductor.EMPTY_STEP_LIST;
  }

  public Step nextStep() {
    int i = this.currentIndex + 1;
    if (i >= this.sequentialEffect.getEffects().size()) {
      return null;
    }
    return new StartSequentialEffectStep(
        this.conductor,
        SystemClock.uptimeMillis()
            + ((Integer) this.sequentialEffect.getDelays().get(i)).intValue(),
        this.sequentialEffect,
        i);
  }

  public final DeviceEffectMap createEffectToVibratorMapping(CombinedVibration combinedVibration) {
    if (combinedVibration instanceof CombinedVibration.Mono) {
      return new DeviceEffectMap((CombinedVibration.Mono) combinedVibration);
    }
    if (combinedVibration instanceof CombinedVibration.Stereo) {
      return new DeviceEffectMap((CombinedVibration.Stereo) combinedVibration);
    }
    return null;
  }

  public final long startVibrating(DeviceEffectMap deviceEffectMap, List list) {
    boolean z;
    int size = deviceEffectMap.size();
    if (size == 0) {
      return 0L;
    }
    AbstractVibratorStep[] abstractVibratorStepArr = new AbstractVibratorStep[size];
    long uptimeMillis = SystemClock.uptimeMillis();
    boolean z2 = false;
    int i = 0;
    while (i < size) {
      VibrationStepConductor vibrationStepConductor = this.conductor;
      int i2 = i;
      abstractVibratorStepArr[i2] =
          vibrationStepConductor.nextVibrateStep(
              uptimeMillis,
              (VibratorController)
                  vibrationStepConductor.getVibrators().get(deviceEffectMap.vibratorIdAt(i)),
              deviceEffectMap.effectAt(i),
              0,
              0L);
      i = i2 + 1;
    }
    if (size == 1) {
      return startVibrating(abstractVibratorStepArr[0], list);
    }
    boolean prepareSyncedVibration =
        this.conductor.vibratorManagerHooks.prepareSyncedVibration(
            deviceEffectMap.getRequiredSyncCapabilities(), deviceEffectMap.getVibratorIds());
    long j = 0;
    int i3 = 0;
    while (true) {
      if (i3 >= size) {
        z = false;
        break;
      }
      long startVibrating = startVibrating(abstractVibratorStepArr[i3], list);
      if (startVibrating < 0) {
        z = true;
        break;
      }
      j = Math.max(j, startVibrating);
      i3++;
    }
    if (prepareSyncedVibration && !z && j > 0) {
      z2 = this.conductor.vibratorManagerHooks.triggerSyncedVibration(getVibration().f1733id);
      z &= z2;
    }
    if (z) {
      for (int size2 = list.size() - 1; size2 >= 0; size2--) {
        ((Step) list.remove(size2)).cancelImmediately();
      }
    }
    if (prepareSyncedVibration && !z2) {
      this.conductor.vibratorManagerHooks.cancelSyncedVibration();
    }
    if (z) {
      return -1L;
    }
    return j;
  }

  public final long startVibrating(AbstractVibratorStep abstractVibratorStep, List list) {
    list.addAll(abstractVibratorStep.play());
    long vibratorOnDuration = abstractVibratorStep.getVibratorOnDuration();
    return vibratorOnDuration < 0
        ? vibratorOnDuration
        : Math.max(vibratorOnDuration, abstractVibratorStep.effect.getDuration());
  }

  public final class DeviceEffectMap {
    public final long mRequiredSyncCapabilities;
    public final SparseArray mVibratorEffects;
    public final int[] mVibratorIds;

    public final boolean requireMixedTriggerCapability(long j, long j2) {
      return ((j & j2) == 0 || (j & (~j2)) == 0) ? false : true;
    }

    public DeviceEffectMap(CombinedVibration.Mono mono) {
      SparseArray vibrators = StartSequentialEffectStep.this.conductor.getVibrators();
      this.mVibratorEffects = new SparseArray(vibrators.size());
      this.mVibratorIds = new int[vibrators.size()];
      for (int i = 0; i < vibrators.size(); i++) {
        int keyAt = vibrators.keyAt(i);
        VibrationEffect.Composed apply =
            StartSequentialEffectStep.this.conductor.deviceEffectAdapter.apply(
                mono.getEffect(), ((VibratorController) vibrators.valueAt(i)).getVibratorInfo());
        if (apply instanceof VibrationEffect.Composed) {
          this.mVibratorEffects.put(keyAt, apply);
          this.mVibratorIds[i] = keyAt;
        }
      }
      this.mRequiredSyncCapabilities = calculateRequiredSyncCapabilities(this.mVibratorEffects);
    }

    public DeviceEffectMap(CombinedVibration.Stereo stereo) {
      SparseArray vibrators = StartSequentialEffectStep.this.conductor.getVibrators();
      SparseArray effects = stereo.getEffects();
      this.mVibratorEffects = new SparseArray();
      for (int i = 0; i < effects.size(); i++) {
        int keyAt = effects.keyAt(i);
        if (vibrators.contains(keyAt)) {
          VibrationEffect.Composed apply =
              StartSequentialEffectStep.this.conductor.deviceEffectAdapter.apply(
                  (VibrationEffect) effects.valueAt(i),
                  ((VibratorController) vibrators.valueAt(i)).getVibratorInfo());
          if (apply instanceof VibrationEffect.Composed) {
            this.mVibratorEffects.put(keyAt, apply);
          }
        }
      }
      this.mVibratorIds = new int[this.mVibratorEffects.size()];
      for (int i2 = 0; i2 < this.mVibratorEffects.size(); i2++) {
        this.mVibratorIds[i2] = this.mVibratorEffects.keyAt(i2);
      }
      this.mRequiredSyncCapabilities = calculateRequiredSyncCapabilities(this.mVibratorEffects);
    }

    public int size() {
      return this.mVibratorIds.length;
    }

    public long getRequiredSyncCapabilities() {
      return this.mRequiredSyncCapabilities;
    }

    public int[] getVibratorIds() {
      return this.mVibratorIds;
    }

    public int vibratorIdAt(int i) {
      return this.mVibratorEffects.keyAt(i);
    }

    public VibrationEffect.Composed effectAt(int i) {
      return (VibrationEffect.Composed) this.mVibratorEffects.valueAt(i);
    }

    public final long calculateRequiredSyncCapabilities(SparseArray sparseArray) {
      long j = 0;
      for (int i = 0; i < sparseArray.size(); i++) {
        VibrationEffectSegment vibrationEffectSegment =
            (VibrationEffectSegment)
                ((VibrationEffect.Composed) sparseArray.valueAt(i)).getSegments().get(0);
        if (vibrationEffectSegment instanceof StepSegment) {
          j |= 2;
        } else if (vibrationEffectSegment instanceof PrebakedSegment) {
          j |= 4;
        } else if (vibrationEffectSegment instanceof PrimitiveSegment) {
          j |= 8;
        }
      }
      int i2 = requireMixedTriggerCapability(j, 2L) ? 16 : 0;
      if (requireMixedTriggerCapability(j, 4L)) {
        i2 |= 32;
      }
      if (requireMixedTriggerCapability(j, 8L)) {
        i2 |= 64;
      }
      return 1 | j | i2;
    }
  }
}
