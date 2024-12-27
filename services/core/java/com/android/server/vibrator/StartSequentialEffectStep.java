package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.VibrationEffect;
import android.os.vibrator.PrebakedSegment;
import android.os.vibrator.PrimitiveSegment;
import android.os.vibrator.StepSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.FrameworkStatsLog;

import com.samsung.android.vibrator.VibRune;

import java.util.ArrayList;
import java.util.List;

public final class StartSequentialEffectStep extends Step {
    public final int currentIndex;
    public long mVibratorsOnMaxDuration;
    public final CombinedVibration.Sequential sequentialEffect;

    public final class DeviceEffectMap {
        public final long mRequiredSyncCapabilities;
        public final SparseArray mVibratorEffects;
        public final int[] mVibratorIds;

        public DeviceEffectMap(
                StartSequentialEffectStep startSequentialEffectStep, CombinedVibration.Mono mono) {
            SparseArray sparseArray =
                    startSequentialEffectStep.conductor.mDeviceAdapter.mAvailableVibrators;
            VibrationEffect.Composed effect = mono.getEffect();
            if (effect instanceof VibrationEffect.Composed) {
                this.mVibratorEffects = new SparseArray(sparseArray.size());
                this.mVibratorIds = new int[sparseArray.size()];
                VibrationEffect.Composed composed = effect;
                for (int i = 0; i < sparseArray.size(); i++) {
                    int keyAt = sparseArray.keyAt(i);
                    this.mVibratorEffects.put(keyAt, composed);
                    this.mVibratorIds[i] = keyAt;
                }
            } else {
                Slog.wtf(
                        "VibrationThread",
                        "Unable to map device vibrators to unexpected effect: " + effect);
                this.mVibratorEffects = new SparseArray();
                this.mVibratorIds = new int[0];
            }
            this.mRequiredSyncCapabilities =
                    calculateRequiredSyncCapabilities(this.mVibratorEffects);
        }

        public DeviceEffectMap(
                StartSequentialEffectStep startSequentialEffectStep,
                CombinedVibration.Stereo stereo) {
            SparseArray sparseArray =
                    startSequentialEffectStep.conductor.mDeviceAdapter.mAvailableVibrators;
            SparseArray effects = stereo.getEffects();
            this.mVibratorEffects = new SparseArray();
            for (int i = 0; i < effects.size(); i++) {
                int keyAt = effects.keyAt(i);
                if (sparseArray.contains(keyAt)) {
                    VibrationEffect.Composed composed = (VibrationEffect) effects.valueAt(i);
                    if (composed instanceof VibrationEffect.Composed) {
                        this.mVibratorEffects.put(keyAt, composed);
                    } else {
                        Slog.wtf(
                                "VibrationThread",
                                "Unable to map device vibrators to unexpected effect: " + composed);
                    }
                }
            }
            this.mVibratorIds = new int[this.mVibratorEffects.size()];
            for (int i2 = 0; i2 < this.mVibratorEffects.size(); i2++) {
                this.mVibratorIds[i2] = this.mVibratorEffects.keyAt(i2);
            }
            this.mRequiredSyncCapabilities =
                    calculateRequiredSyncCapabilities(this.mVibratorEffects);
        }

        public static long calculateRequiredSyncCapabilities(SparseArray sparseArray) {
            long j = 0;
            for (int i = 0; i < sparseArray.size(); i++) {
                VibrationEffectSegment vibrationEffectSegment =
                        (VibrationEffectSegment)
                                ((VibrationEffect.Composed) sparseArray.valueAt(i))
                                        .getSegments()
                                        .get(0);
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
            return j | 1 | i2;
        }

        public static boolean requireMixedTriggerCapability(long j, long j2) {
            return ((j & j2) == 0 || (j & (~j2)) == 0) ? false : true;
        }
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
    public final List cancel() {
        return VibrationStepConductor.EMPTY_STEP_LIST;
    }

    @Override // com.android.server.vibrator.Step
    public final void cancelImmediately() {}

    @Override // com.android.server.vibrator.Step
    public final long getVibratorOnDuration() {
        return this.mVibratorsOnMaxDuration;
    }

    public final StartSequentialEffectStep nextStep() {
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

    @Override // com.android.server.vibrator.Step
    public final List play() {
        Trace.traceBegin(8388608L, "StartSequentialEffectStep");
        ArrayList arrayList = new ArrayList();
        this.mVibratorsOnMaxDuration = -1L;
        try {
            Slog.d("VibrationThread", "StartSequentialEffectStep for effect #" + this.currentIndex);
            CombinedVibration.Mono mono =
                    (CombinedVibration) this.sequentialEffect.getEffects().get(this.currentIndex);
            DeviceEffectMap deviceEffectMap =
                    mono instanceof CombinedVibration.Mono
                            ? new DeviceEffectMap(this, mono)
                            : mono instanceof CombinedVibration.Stereo
                                    ? new DeviceEffectMap(this, (CombinedVibration.Stereo) mono)
                                    : null;
            if (deviceEffectMap == null) {
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
            if (this.conductor.mDeviceAdapter.mAvailableVibrators.size() != 0) {
                VibrationEffect.Composed composed = this.conductor.mComposed;
                StepSegment stepSegment = (VibrationEffectSegment) composed.getSegments().get(0);
                VibratorController vibratorController =
                        (VibratorController)
                                this.conductor.mDeviceAdapter.mAvailableVibrators.get(0);
                if (vibratorController == null) {
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
                }
                VibratorController.NativeWrapper nativeWrapper = vibratorController.mNativeWrapper;
                if (stepSegment instanceof StepSegment) {
                    StepSegment stepSegment2 = stepSegment;
                    int semGetMagnitude =
                            composed.semGetMagnitude() > -1
                                    ? composed.semGetMagnitude()
                                    : this.conductor.mVibration.mMagnitude;
                    if (VibRune.SUPPORT_CIRRUS_HAPTIC()) {
                        vibratorController.mNativeWrapper.performPrebakedHapticPattern(
                                0L, semGetMagnitude, false);
                        vibratorController.notifyListenerOnVibrating(true);
                    } else if (vibratorController.mSupportIntensityControl) {
                        nativeWrapper.setIntensity(semGetMagnitude);
                    }
                    if (vibratorController.mSupportFrequencyControl) {
                        nativeWrapper.setFrequencyType((long) stepSegment2.getFrequencyHz());
                    }
                } else if (stepSegment instanceof PrimitiveSegment) {
                    if (vibratorController.mSupportIntensityControl) {
                        nativeWrapper.setIntensity(
                                composed.semGetMagnitude() > -1
                                        ? composed.semGetMagnitude()
                                        : this.conductor.mVibration.mMagnitude);
                    } else {
                        vibratorController.setAmplitude(1.0f);
                    }
                }
            }
            final long startVibrating = startVibrating(deviceEffectMap, arrayList);
            this.mVibratorsOnMaxDuration = startVibrating;
            VibrationStepConductor vibrationStepConductor = this.conductor;
            VibratorManagerService.VibrationThreadCallbacks vibrationThreadCallbacks =
                    vibrationStepConductor.vibratorManagerHooks;
            final int i = vibrationStepConductor.mVibration.callerInfo.uid;
            VibratorManagerService vibratorManagerService =
                    (VibratorManagerService) vibrationThreadCallbacks.this$0;
            if (startVibrating > 0) {
                if (startVibrating == Long.MAX_VALUE) {
                    startVibrating = 5000;
                }
                try {
                    vibratorManagerService.mBatteryStatsService.noteVibratorOn(i, startVibrating);
                    VibratorFrameworkStatsLogger vibratorFrameworkStatsLogger =
                            vibratorManagerService.mFrameworkStatsLogger;
                    vibratorFrameworkStatsLogger.getClass();
                    vibratorFrameworkStatsLogger.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.vibrator.VibratorFrameworkStatsLogger$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    FrameworkStatsLog.write_non_chained(
                                            84, i, (String) null, 1, startVibrating);
                                }
                            });
                } catch (RemoteException e) {
                    Slog.e("VibratorManagerService", "Error logging VibratorStateChanged to ON", e);
                }
            }
            long j3 = this.mVibratorsOnMaxDuration;
            if (j3 >= 0) {
                Step finishSequentialEffectStep3 =
                        j3 > 0 ? new FinishSequentialEffectStep(this) : nextStep();
                if (finishSequentialEffectStep3 != null) {
                    arrayList.add(finishSequentialEffectStep3);
                }
            }
            Trace.traceEnd(8388608L);
            return arrayList;
        } catch (Throwable th) {
            long j4 = this.mVibratorsOnMaxDuration;
            if (j4 >= 0) {
                Step finishSequentialEffectStep4 =
                        j4 > 0 ? new FinishSequentialEffectStep(this) : nextStep();
                if (finishSequentialEffectStep4 != null) {
                    arrayList.add(finishSequentialEffectStep4);
                }
            }
            Trace.traceEnd(8388608L);
            throw th;
        }
    }

    public final long startVibrating(DeviceEffectMap deviceEffectMap, List list) {
        boolean z;
        int[] iArr = deviceEffectMap.mVibratorIds;
        int length = iArr.length;
        if (length == 0) {
            return 0L;
        }
        AbstractVibratorStep[] abstractVibratorStepArr = new AbstractVibratorStep[length];
        long uptimeMillis = SystemClock.uptimeMillis();
        boolean z2 = false;
        int i = 0;
        while (i < length) {
            VibrationStepConductor vibrationStepConductor = this.conductor;
            int i2 = i;
            abstractVibratorStepArr[i2] =
                    vibrationStepConductor.nextVibrateStep(
                            uptimeMillis,
                            (VibratorController)
                                    vibrationStepConductor.mDeviceAdapter.mAvailableVibrators.get(
                                            deviceEffectMap.mVibratorEffects.keyAt(i)),
                            (VibrationEffect.Composed) deviceEffectMap.mVibratorEffects.valueAt(i),
                            0,
                            0L);
            i = i2 + 1;
        }
        if (length == 1) {
            AbstractVibratorStep abstractVibratorStep = abstractVibratorStepArr[0];
            ((ArrayList) list).addAll(abstractVibratorStep.play());
            long j = abstractVibratorStep.mVibratorOnResult;
            return j < 0 ? j : Math.max(j, abstractVibratorStep.effect.getDuration());
        }
        VibratorManagerService vibratorManagerService =
                (VibratorManagerService) this.conductor.vibratorManagerHooks.this$0;
        long j2 = vibratorManagerService.mCapabilities;
        long j3 = deviceEffectMap.mRequiredSyncCapabilities;
        boolean nativePrepareSynced =
                (j2 & j3) != j3
                        ? false
                        : VibratorManagerService.nativePrepareSynced(
                                vibratorManagerService.mNativeWrapper.mNativeServicePtr, iArr);
        long j4 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                z = false;
                break;
            }
            AbstractVibratorStep abstractVibratorStep2 = abstractVibratorStepArr[i3];
            ((ArrayList) list).addAll(abstractVibratorStep2.play());
            long j5 = abstractVibratorStep2.mVibratorOnResult;
            if (j5 >= 0) {
                j5 = Math.max(j5, abstractVibratorStep2.effect.getDuration());
            }
            if (j5 < 0) {
                z = true;
                break;
            }
            j4 = Math.max(j4, j5);
            i3++;
        }
        if (nativePrepareSynced && !z && j4 > 0) {
            VibrationStepConductor vibrationStepConductor2 = this.conductor;
            z2 =
                    VibratorManagerService.nativeTriggerSynced(
                            ((VibratorManagerService)
                                            vibrationStepConductor2.vibratorManagerHooks.this$0)
                                    .mNativeWrapper
                                    .mNativeServicePtr,
                            vibrationStepConductor2.mVibration.id);
            z &= z2;
        }
        if (z) {
            ArrayList arrayList = (ArrayList) list;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((Step) arrayList.remove(size)).cancelImmediately();
            }
        }
        if (nativePrepareSynced && !z2) {
            ((VibratorManagerService) this.conductor.vibratorManagerHooks.this$0)
                    .mNativeWrapper.cancelSynced();
        }
        if (z) {
            return -1L;
        }
        return j4;
    }
}
