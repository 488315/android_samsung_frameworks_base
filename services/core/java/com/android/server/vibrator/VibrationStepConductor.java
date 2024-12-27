package com.android.server.vibrator;

import android.os.Build;
import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.vibrator.Flags;
import android.os.vibrator.PrebakedSegment;
import android.os.vibrator.PrimitiveSegment;
import android.os.vibrator.RampSegment;
import android.os.vibrator.SemHapticSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;

import com.android.modules.expresslog.Counter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VibrationStepConductor implements IBinder.DeathRecipient {
    public static final List EMPTY_STEP_LIST = new ArrayList();
    public final VibrationEffect.Composed mComposed;
    public final DeviceAdapter mDeviceAdapter;
    public int mDynamicEffectLoop;
    public int mPendingVibrateSteps;
    public int mRemainingStartSequentialEffectSteps;
    public final CompletableFuture mRequestVibrationParamsFuture;
    public final IntArray mSignalVibratorsComplete;
    public final VibratorFrameworkStatsLogger mStatsLogger;
    public int mSuccessfulVibratorOnSteps;
    public final HalVibration mVibration;
    public final VibrationScaler mVibrationScaler;
    public final VibrationSettings vibrationSettings;
    public final VibratorManagerService.VibrationThreadCallbacks vibratorManagerHooks;
    public final PriorityQueue mNextSteps = new PriorityQueue();
    public final Queue mPendingOnVibratorCompleteSteps = new LinkedList();
    public final Object mLock = new Object();
    public Vibration.EndInfo mSignalCancel = null;
    public boolean mSignalCancelImmediate = false;
    public Vibration.EndInfo mCancelledVibrationEndInfo = null;
    public boolean mCancelledImmediately = false;

    public VibrationStepConductor(
            HalVibration halVibration,
            VibrationSettings vibrationSettings,
            DeviceAdapter deviceAdapter,
            VibrationScaler vibrationScaler,
            VibratorFrameworkStatsLogger vibratorFrameworkStatsLogger,
            CompletableFuture completableFuture,
            VibratorManagerService.VibrationThreadCallbacks vibrationThreadCallbacks) {
        this.mVibration = halVibration;
        this.vibrationSettings = vibrationSettings;
        this.mDeviceAdapter = deviceAdapter;
        this.mVibrationScaler = vibrationScaler;
        this.mStatsLogger = vibratorFrameworkStatsLogger;
        this.mRequestVibrationParamsFuture = completableFuture;
        this.vibratorManagerHooks = vibrationThreadCallbacks;
        this.mDynamicEffectLoop = -1;
        String str = halVibration.callerInfo.reason;
        if (str != null && str.contains("DynamicEffect_")) {
            try {
                this.mDynamicEffectLoop =
                        Integer.parseInt(
                                halVibration.callerInfo.reason.replace("DynamicEffect_", ""));
            } catch (NumberFormatException unused) {
                Slog.w("VibrationThread", "Failed to parse DynamicEffect reason.");
            }
        }
        this.mSignalVibratorsComplete =
                new IntArray(this.mDeviceAdapter.mAvailableVibratorIds.length);
        CombinedVibration.Mono mono = halVibration.mEffectToPlay;
        if (mono instanceof CombinedVibration.Mono) {
            this.mComposed = mono.getEffect();
        } else if (mono instanceof CombinedVibration.Stereo) {
            this.mComposed =
                    (VibrationEffect.Composed)
                            ((CombinedVibration.Stereo) mono).getEffects().get(0);
        }
    }

    public static void expectIsVibrationThread(boolean z) {
        if ((Thread.currentThread() instanceof VibrationThread) != z) {
            Slog.wtfStack(
                    "VibrationStepConductor",
                    "Thread caller assertion failed, expected isVibrationThread=" + z);
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.d("VibrationThread", "Binder died, cancelling vibration...");
        notifyCancelled(new Vibration.EndInfo(Vibration.Status.CANCELLED_BINDER_DIED, null), false);
    }

    public final boolean isFinished() {
        if (Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        if (this.mCancelledImmediately) {
            return true;
        }
        return this.mPendingOnVibratorCompleteSteps.isEmpty() && this.mNextSteps.isEmpty();
    }

    public final AbstractVibratorStep nextVibrateStep(
            long j,
            VibratorController vibratorController,
            VibrationEffect.Composed composed,
            int i,
            long j2) {
        int i2;
        int repeatIndex;
        if (Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        if (i >= composed.getSegments().size()) {
            int i3 = this.mDynamicEffectLoop;
            if (i3 > 1) {
                this.mDynamicEffectLoop = i3 - 1;
                repeatIndex = 0;
            } else {
                repeatIndex = composed.getRepeatIndex();
            }
            i2 = repeatIndex;
        } else {
            i2 = i;
        }
        if (i2 < 0) {
            return new CompleteEffectVibratorStep(this, j, false, vibratorController, j2);
        }
        VibrationEffectSegment vibrationEffectSegment =
                (VibrationEffectSegment) composed.getSegments().get(i2);
        if (vibrationEffectSegment instanceof PrebakedSegment) {
            return new PerformPrebakedVibratorStep(
                    this, Math.max(j, j2), vibratorController, composed, i2, j2);
        }
        if (vibrationEffectSegment instanceof PrimitiveSegment) {
            return new ComposePrimitivesVibratorStep(
                    this, Math.max(j, j2), vibratorController, composed, i2, j2);
        }
        if (vibrationEffectSegment instanceof RampSegment) {
            return new ComposePwleVibratorStep(
                    this, Math.max(j, j2), vibratorController, composed, i2, j2);
        }
        if (!(vibrationEffectSegment instanceof SemHapticSegment)) {
            return new SetAmplitudeVibratorStep(this, j, vibratorController, composed, i2, j2);
        }
        SemHapticStep semHapticStep =
                new SemHapticStep(this, j, vibratorController, composed, i2, j2);
        semHapticStep.mNextOffTime = j2;
        return semHapticStep;
    }

    public final void notifyCancelled(Vibration.EndInfo endInfo, boolean z) {
        if (Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(false);
        }
        Slog.d(
                "VibrationThread",
                "Vibration cancel requested with signal=" + endInfo + ", immediate=" + z);
        if (!endInfo.status.name().startsWith("CANCEL")) {
            Slog.w(
                    "VibrationThread",
                    "Vibration cancel requested with bad signal="
                            + endInfo
                            + ", using CANCELLED_UNKNOWN_REASON to ensure cancellation.");
            endInfo = new Vibration.EndInfo(Vibration.Status.CANCELLED_BY_UNKNOWN_REASON, null);
        }
        synchronized (this.mLock) {
            if (z) {
                try {
                    if (!this.mSignalCancelImmediate) {}
                    Slog.d(
                            "VibrationThread",
                            "Vibration cancel request ignored as the vibration "
                                    + this.mVibration.id
                                    + "is already being cancelled with signal="
                                    + this.mSignalCancel
                                    + ", immediate="
                                    + this.mSignalCancelImmediate);
                } catch (Throwable th) {
                    throw th;
                }
            }
            Vibration.EndInfo endInfo2 = this.mSignalCancel;
            if (endInfo2 == null) {
                this.mSignalCancelImmediate = z | this.mSignalCancelImmediate;
                if (endInfo2 == null) {
                    this.mSignalCancel = endInfo;
                } else {
                    Slog.d(
                            "VibrationThread",
                            "Vibration cancel request new signal="
                                    + endInfo
                                    + " ignored as the vibration was already cancelled with signal="
                                    + this.mSignalCancel
                                    + ", but immediate flag was updated to "
                                    + this.mSignalCancelImmediate);
                }
                CompletableFuture completableFuture = this.mRequestVibrationParamsFuture;
                if (completableFuture != null) {
                    completableFuture.cancel(true);
                }
                this.mLock.notify();
                return;
            }
            Slog.d(
                    "VibrationThread",
                    "Vibration cancel request ignored as the vibration "
                            + this.mVibration.id
                            + "is already being cancelled with signal="
                            + this.mSignalCancel
                            + ", immediate="
                            + this.mSignalCancelImmediate);
        }
    }

    public final void notifySyncedVibrationComplete() {
        Slog.d("VibrationThread", "Synced vibration complete reported by vibrator manager");
        synchronized (this.mLock) {
            try {
                for (int i : this.mDeviceAdapter.mAvailableVibratorIds) {
                    this.mSignalVibratorsComplete.add(i);
                }
                this.mLock.notify();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Step pollNext() {
        if (Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        return !this.mPendingOnVibratorCompleteSteps.isEmpty()
                ? (Step) ((LinkedList) this.mPendingOnVibratorCompleteSteps).poll()
                : (Step) this.mNextSteps.poll();
    }

    public final void prepareToStart() {
        boolean z = Build.IS_DEBUGGABLE;
        if (z) {
            expectIsVibrationThread(true);
        }
        if (this.mVibration.callerInfo.attrs.isFlagSet(16)) {
            this.mVibration.resolveEffects(this.mVibrationScaler.mDefaultVibrationAmplitude);
        } else {
            if (Flags.adaptiveHapticsEnabled()) {
                if (z) {
                    expectIsVibrationThread(true);
                }
                CompletableFuture completableFuture = this.mRequestVibrationParamsFuture;
                if (completableFuture != null) {
                    try {
                        completableFuture.get(
                                this.vibrationSettings.mVibrationConfig
                                        .getRequestVibrationParamsTimeoutMs(),
                                TimeUnit.MILLISECONDS);
                    } catch (CancellationException e) {
                        Slog.d(
                                "VibrationThread",
                                "Request for vibration params cancelled, maybe superseded or"
                                    + " vibrator controller unregistered. Skipping params...",
                                e);
                    } catch (TimeoutException e2) {
                        Slog.d("VibrationThread", "Request for vibration params timed out", e2);
                        VibratorFrameworkStatsLogger vibratorFrameworkStatsLogger =
                                this.mStatsLogger;
                        int i = this.mVibration.callerInfo.uid;
                        vibratorFrameworkStatsLogger.getClass();
                        Counter.logIncrementWithUid(
                                "vibrator.value_vibration_param_request_timeout", i);
                    } catch (Throwable th) {
                        Slog.w("VibrationThread", "Failed to retrieve vibration params.", th);
                    }
                }
            }
            HalVibration halVibration = this.mVibration;
            VibrationScaler vibrationScaler = this.mVibrationScaler;
            int usage = halVibration.callerInfo.attrs.getUsage();
            halVibration.mScaleLevel = vibrationScaler.getScaleLevel(usage);
            float adaptiveHapticsScale = vibrationScaler.getAdaptiveHapticsScale(usage);
            halVibration.mAdaptiveScale = adaptiveHapticsScale;
            VibrationStats vibrationStats = halVibration.stats;
            vibrationStats.getClass();
            if (Float.compare(adaptiveHapticsScale, 1.0f) != 0) {
                vibrationStats.mAdaptiveScale = adaptiveHapticsScale;
            }
            for (int i2 = 0; i2 < halVibration.mFallbacks.size(); i2++) {
                SparseArray sparseArray = halVibration.mFallbacks;
                VibrationEffect.Composed composed = (VibrationEffect) sparseArray.valueAt(i2);
                if (composed instanceof VibrationEffect.Composed) {
                    int effectStrength = vibrationScaler.getEffectStrength(usage);
                    VibrationScaler.ScaleLevel scaleLevel =
                            (VibrationScaler.ScaleLevel)
                                    vibrationScaler.mScaleLevels.get(
                                            vibrationScaler.getScaleLevel(usage));
                    float adaptiveHapticsScale2 = vibrationScaler.getAdaptiveHapticsScale(usage);
                    if (scaleLevel == null) {
                        StringBuilder sb =
                                new StringBuilder("No configured scaling level found! (current=");
                        VibrationSettings vibrationSettings = vibrationScaler.mSettingsController;
                        sb.append(vibrationSettings.getCurrentIntensity(usage));
                        sb.append(", default= ");
                        sb.append(
                                vibrationSettings.mVibrationConfig.getDefaultVibrationIntensity(
                                        usage));
                        sb.append(")");
                        Slog.e("VibrationScaler", sb.toString());
                        scaleLevel = VibrationScaler.SCALE_LEVEL_NONE;
                    }
                    VibrationEffect.Composed composed2 = composed;
                    ArrayList arrayList = new ArrayList(composed2.getSegments());
                    int size = arrayList.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        arrayList.set(
                                i3,
                                ((VibrationEffectSegment) arrayList.get(i3))
                                        .resolve(vibrationScaler.mDefaultVibrationAmplitude)
                                        .applyEffectStrength(effectStrength)
                                        .scale(scaleLevel.factor)
                                        .scaleLinearly(adaptiveHapticsScale2));
                    }
                    if (!arrayList.equals(composed2.getSegments())) {
                        composed =
                                new VibrationEffect.Composed(arrayList, composed2.getRepeatIndex());
                        composed.validate();
                    }
                } else {
                    Slog.wtf(
                            "VibrationScaler",
                            "Error scaling unsupported vibration effect: " + composed);
                }
                sparseArray.setValueAt(i2, composed);
            }
        }
        HalVibration halVibration2 = this.mVibration;
        CombinedVibration adapt = halVibration2.mEffectToPlay.adapt(this.mDeviceAdapter);
        if (!Objects.equals(halVibration2.mEffectToPlay, adapt)) {
            halVibration2.mEffectToPlay = adapt;
        }
        CombinedVibration.Sequential sequential = this.mVibration.mEffectToPlay;
        CombinedVibration.Sequential combine =
                sequential instanceof CombinedVibration.Sequential
                        ? sequential
                        : CombinedVibration.startSequential().addNext(sequential).combine();
        this.mPendingVibrateSteps++;
        this.mRemainingStartSequentialEffectSteps = combine.getEffects().size();
        this.mNextSteps.offer(
                new StartSequentialEffectStep(
                        this,
                        ((Integer) combine.getDelays().get(0)).intValue()
                                + SystemClock.uptimeMillis(),
                        combine,
                        0));
        VibrationStats vibrationStats2 = this.mVibration.stats;
        if (vibrationStats2.mEndUptimeMillis <= 0 && vibrationStats2.mStartUptimeMillis == 0) {
            vibrationStats2.mStartUptimeMillis = SystemClock.uptimeMillis();
            vibrationStats2.mStartTimeDebug = System.currentTimeMillis();
        }
    }

    public final void runNextStep() {
        if (Build.IS_DEBUGGABLE) {
            expectIsVibrationThread(true);
        }
        Step pollNext = pollNext();
        if (pollNext != null) {
            StringBuilder sb = new StringBuilder("Playing vibration id ");
            sb.append(this.mVibration.id);
            sb.append(
                    pollNext instanceof AbstractVibratorStep
                            ? " on vibrator " + ((AbstractVibratorStep) pollNext).getVibratorId()
                            : "");
            sb.append(" ");
            sb.append(pollNext.getClass().getSimpleName());
            sb.append(pollNext.isCleanUp() ? " (cleanup)" : "");
            Slog.d("VibrationThread", sb.toString());
            List play = pollNext.play();
            if (pollNext.getVibratorOnDuration() > 0) {
                this.mSuccessfulVibratorOnSteps++;
            }
            if (pollNext instanceof StartSequentialEffectStep) {
                this.mRemainingStartSequentialEffectSteps--;
            }
            if (!pollNext.isCleanUp()) {
                this.mPendingVibrateSteps--;
            }
            for (int i = 0; i < play.size(); i++) {
                this.mPendingVibrateSteps += !((Step) play.get(i)).isCleanUp() ? 1 : 0;
            }
            this.mNextSteps.addAll(play);
        }
    }

    public final boolean waitUntilNextStepIsDue() {
        int[] iArr;
        boolean z;
        Vibration.EndInfo endInfo;
        Step step;
        boolean z2 = Build.IS_DEBUGGABLE;
        if (z2) {
            expectIsVibrationThread(true);
        }
        if (z2) {
            expectIsVibrationThread(true);
        }
        synchronized (this.mLock) {
            try {
                iArr = null;
                if (this.mSignalCancelImmediate) {
                    if (this.mCancelledImmediately) {
                        Slog.wtf(
                                "VibrationThread", "Immediate cancellation signal processed twice");
                    }
                    endInfo = this.mSignalCancel;
                    z = true;
                } else {
                    z = false;
                    endInfo = null;
                }
                Vibration.EndInfo endInfo2 = this.mSignalCancel;
                if (endInfo2 != null && this.mCancelledVibrationEndInfo == null) {
                    endInfo = endInfo2;
                }
                if (!z && this.mSignalVibratorsComplete.size() > 0) {
                    iArr = this.mSignalVibratorsComplete.toArray();
                    this.mSignalVibratorsComplete.clear();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            if (z2) {
                expectIsVibrationThread(true);
            }
            this.mCancelledImmediately = true;
            this.mCancelledVibrationEndInfo = endInfo;
            while (true) {
                Step pollNext = pollNext();
                if (pollNext == null) {
                    break;
                }
                pollNext.cancelImmediately();
            }
            this.mPendingVibrateSteps = 0;
        } else {
            if (endInfo != null) {
                if (z2) {
                    expectIsVibrationThread(true);
                }
                this.mCancelledVibrationEndInfo = endInfo;
                ArrayList arrayList = new ArrayList();
                while (true) {
                    Step pollNext2 = pollNext();
                    if (pollNext2 == null) {
                        break;
                    }
                    arrayList.addAll(pollNext2.cancel());
                }
                this.mPendingVibrateSteps = 0;
                this.mNextSteps.addAll(arrayList);
            }
            if (iArr != null) {
                if (Build.IS_DEBUGGABLE) {
                    expectIsVibrationThread(true);
                }
                for (int i : iArr) {
                    Iterator it = this.mNextSteps.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Step step2 = (Step) it.next();
                            if (step2.acceptVibratorCompleteCallback(i)) {
                                it.remove();
                                ((LinkedList) this.mPendingOnVibratorCompleteSteps).offer(step2);
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (this.mCancelledImmediately) {
            return false;
        }
        if (!this.mPendingOnVibratorCompleteSteps.isEmpty()
                || (step = (Step) this.mNextSteps.peek()) == null) {
            return true;
        }
        long j = step.startTime;
        long max = j == Long.MAX_VALUE ? 0L : Math.max(0L, j - SystemClock.uptimeMillis());
        if (max <= 0) {
            return true;
        }
        synchronized (this.mLock) {
            if (Build.IS_DEBUGGABLE) {
                expectIsVibrationThread(true);
            }
            if ((this.mSignalCancel != null && this.mCancelledVibrationEndInfo == null)
                    || ((this.mSignalCancelImmediate && !this.mCancelledImmediately)
                            || this.mSignalVibratorsComplete.size() > 0)) {
                return false;
            }
            try {
                this.mLock.wait(max);
            } catch (InterruptedException unused) {
            }
            return false;
        }
    }
}
