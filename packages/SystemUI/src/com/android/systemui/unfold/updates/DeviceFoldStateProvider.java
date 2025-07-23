package com.android.systemui.unfold.updates;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import androidx.core.util.Consumer;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.LifecycleScreenStatusProvider;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.system.ActivityManagerActivityTypeProvider;
import com.android.systemui.unfold.updates.FoldProvider;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.unfold.updates.RotationChangeProvider;
import com.android.systemui.unfold.updates.hinge.HingeAngleProvider;
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider;
import com.android.systemui.unfold.util.CurrentActivityTypeProvider;
import com.android.systemui.unfold.util.UnfoldKeyguardVisibilityProvider;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceFoldStateProvider implements FoldStateProvider {
    public final CurrentActivityTypeProvider activityTypeProvider;
    public final Context context;
    public final FoldProvider foldProvider;
    public final int halfOpenedTimeoutMillis;
    public final HingeAngleProvider hingeAngleProvider;
    public boolean isFolded;
    public boolean isScreenOn;
    public boolean isStarted;
    public Integer lastFoldUpdate;
    public float lastHingeAngle;
    public float lastHingeAngleBeforeTransition;
    public final Handler progressHandler;
    public final RotationChangeProvider rotationChangeProvider;
    public final ScreenStatusProvider screenStatusProvider;
    public final UnfoldKeyguardVisibilityProvider unfoldKeyguardVisibilityProvider;
    public final CopyOnWriteArrayList outputListeners = new CopyOnWriteArrayList();
    public final HingeAngleListener hingeAngleListener = new HingeAngleListener();
    public final ScreenStatusListener screenListener = new ScreenStatusListener();
    public final FoldStateListener foldStateListener = new FoldStateListener();
    public final DeviceFoldStateProvider$timeoutRunnable$1 timeoutRunnable = new Runnable() { // from class: com.android.systemui.unfold.updates.DeviceFoldStateProvider$timeoutRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            DeviceFoldStateProvider deviceFoldStateProvider = DeviceFoldStateProvider.this;
            deviceFoldStateProvider.notifyFoldUpdate(deviceFoldStateProvider.lastHingeAngle, 2);
        }
    };
    public final FoldRotationListener rotationListener = new FoldRotationListener();
    public final DeviceFoldStateProvider$progressExecutor$1 progressExecutor = new Executor() { // from class: com.android.systemui.unfold.updates.DeviceFoldStateProvider$progressExecutor$1
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            DeviceFoldStateProvider.this.progressHandler.post(runnable);
        }
    };
    public boolean isUnfoldHandled = true;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FoldRotationListener implements RotationChangeProvider.RotationListener {
        public FoldRotationListener() {
        }

        @Override // com.android.systemui.unfold.updates.RotationChangeProvider.RotationListener
        public final void onRotationChanged(int i) {
            DeviceFoldStateProvider deviceFoldStateProvider = DeviceFoldStateProvider.this;
            deviceFoldStateProvider.assertInProgressThread$2();
            if (deviceFoldStateProvider.isTransitionInProgress()) {
                deviceFoldStateProvider.notifyFoldUpdate(deviceFoldStateProvider.lastHingeAngle, 2);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FoldStateListener implements FoldProvider.FoldCallback {
        public FoldStateListener() {
        }

        @Override // com.android.systemui.unfold.updates.FoldProvider.FoldCallback
        public final void onFoldUpdated(boolean z) {
            DeviceFoldStateProvider deviceFoldStateProvider = DeviceFoldStateProvider.this;
            deviceFoldStateProvider.assertInProgressThread$2();
            deviceFoldStateProvider.isFolded = z;
            deviceFoldStateProvider.lastHingeAngle = 0.0f;
            DeviceFoldStateProvider$timeoutRunnable$1 deviceFoldStateProvider$timeoutRunnable$1 = deviceFoldStateProvider.timeoutRunnable;
            Handler handler = deviceFoldStateProvider.progressHandler;
            HingeAngleProvider hingeAngleProvider = deviceFoldStateProvider.hingeAngleProvider;
            if (z) {
                hingeAngleProvider.stop();
                deviceFoldStateProvider.notifyFoldUpdate(deviceFoldStateProvider.lastHingeAngle, 4);
                handler.removeCallbacks(deviceFoldStateProvider$timeoutRunnable$1);
                deviceFoldStateProvider.isUnfoldHandled = false;
                return;
            }
            deviceFoldStateProvider.notifyFoldUpdate(0.0f, 0);
            if (deviceFoldStateProvider.isTransitionInProgress()) {
                handler.removeCallbacks(deviceFoldStateProvider$timeoutRunnable$1);
            }
            handler.postDelayed(deviceFoldStateProvider$timeoutRunnable$1, deviceFoldStateProvider.halfOpenedTimeoutMillis);
            hingeAngleProvider.start();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HingeAngleListener implements Consumer {
        public HingeAngleListener() {
        }

        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            Integer num;
            float floatValue = ((Number) obj).floatValue();
            DeviceFoldStateProvider.this.assertInProgressThread$2();
            DeviceFoldStateProvider deviceFoldStateProvider = DeviceFoldStateProvider.this;
            deviceFoldStateProvider.assertInProgressThread$2();
            boolean z = DeviceFoldStateProviderKt.DEBUG;
            if (z) {
                StringBuilder sb = new StringBuilder("Hinge angle: ");
                sb.append(floatValue);
                sb.append(", lastHingeAngle: ");
                sb.append(deviceFoldStateProvider.lastHingeAngle);
                sb.append(", lastHingeAngleBeforeTransition: ");
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m(deviceFoldStateProvider.lastHingeAngleBeforeTransition, "DeviceFoldProvider", sb);
            }
            boolean z2 = deviceFoldStateProvider.isTransitionInProgress() && ((num = deviceFoldStateProvider.lastFoldUpdate) == null || ((floatValue > deviceFoldStateProvider.lastHingeAngle ? 1 : (floatValue == deviceFoldStateProvider.lastHingeAngle ? 0 : -1)) < 0 ? 1 : 0) != num.intValue());
            boolean z3 = floatValue - deviceFoldStateProvider.lastHingeAngleBeforeTransition > 7.5f;
            if (z2 || z3) {
                deviceFoldStateProvider.lastHingeAngleBeforeTransition = deviceFoldStateProvider.lastHingeAngle;
            }
            float f = deviceFoldStateProvider.lastHingeAngleBeforeTransition;
            int i = floatValue < f ? 1 : 0;
            boolean z4 = Math.abs(floatValue - f) > 7.5f;
            boolean z5 = 180.0f - floatValue < 15.0f;
            Integer num2 = deviceFoldStateProvider.lastFoldUpdate;
            boolean z6 = num2 == null || num2.intValue() != i;
            boolean z7 = deviceFoldStateProvider.isUnfoldHandled;
            boolean z8 = deviceFoldStateProvider.context.getApplicationContext().getResources().getConfiguration().smallestScreenWidthDp > 600;
            if (z4 && z6 && !z5 && z7) {
                Boolean bool = ((ActivityManagerActivityTypeProvider) deviceFoldStateProvider.activityTypeProvider)._isHomeActivity;
                Integer num3 = null;
                if (bool != null) {
                    boolean booleanValue = bool.booleanValue();
                    deviceFoldStateProvider.unfoldKeyguardVisibilityProvider.getClass();
                    if (z) {
                        Log.d("DeviceFoldProvider", "isHomeActivity=" + booleanValue + ", isOnKeyguard=false");
                    }
                    if (!booleanValue) {
                        num3 = 60;
                    }
                }
                if ((num3 == null || floatValue < num3.intValue()) && z8) {
                    deviceFoldStateProvider.notifyFoldUpdate(floatValue, i);
                }
            }
            if (deviceFoldStateProvider.isTransitionInProgress()) {
                if (z5) {
                    deviceFoldStateProvider.notifyFoldUpdate(floatValue, 3);
                    deviceFoldStateProvider.progressHandler.removeCallbacks(deviceFoldStateProvider.timeoutRunnable);
                } else {
                    boolean isTransitionInProgress = deviceFoldStateProvider.isTransitionInProgress();
                    DeviceFoldStateProvider$timeoutRunnable$1 deviceFoldStateProvider$timeoutRunnable$1 = deviceFoldStateProvider.timeoutRunnable;
                    Handler handler = deviceFoldStateProvider.progressHandler;
                    if (isTransitionInProgress) {
                        handler.removeCallbacks(deviceFoldStateProvider$timeoutRunnable$1);
                    }
                    handler.postDelayed(deviceFoldStateProvider$timeoutRunnable$1, deviceFoldStateProvider.halfOpenedTimeoutMillis);
                }
            }
            deviceFoldStateProvider.lastHingeAngle = floatValue;
            Iterator it = deviceFoldStateProvider.outputListeners.iterator();
            while (it.hasNext()) {
                ((FoldStateProvider.FoldUpdatesListener) it.next()).onHingeAngleUpdate(floatValue);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ScreenStatusListener implements ScreenStatusProvider.ScreenListener {
        public ScreenStatusListener() {
        }

        @Override // com.android.systemui.unfold.updates.screen.ScreenStatusProvider.ScreenListener
        public final void onScreenTurnedOn() {
            DeviceFoldStateProvider deviceFoldStateProvider = DeviceFoldStateProvider.this;
            deviceFoldStateProvider.progressHandler.post(new DeviceFoldStateProvider$ScreenStatusListener$executeInProgressThread$1(new DeviceFoldStateProvider$ScreenStatusListener$$ExternalSyntheticLambda0(deviceFoldStateProvider, 0)));
        }

        @Override // com.android.systemui.unfold.updates.screen.ScreenStatusProvider.ScreenListener
        public final void onScreenTurningOff() {
            DeviceFoldStateProvider deviceFoldStateProvider = DeviceFoldStateProvider.this;
            deviceFoldStateProvider.progressHandler.post(new DeviceFoldStateProvider$ScreenStatusListener$executeInProgressThread$1(new DeviceFoldStateProvider$ScreenStatusListener$$ExternalSyntheticLambda0(deviceFoldStateProvider, 1)));
        }

        @Override // com.android.systemui.unfold.updates.screen.ScreenStatusProvider.ScreenListener
        public final void onScreenTurningOn() {
            DeviceFoldStateProvider deviceFoldStateProvider = DeviceFoldStateProvider.this;
            deviceFoldStateProvider.progressHandler.post(new DeviceFoldStateProvider$ScreenStatusListener$executeInProgressThread$1(new DeviceFoldStateProvider$ScreenStatusListener$$ExternalSyntheticLambda0(deviceFoldStateProvider, 2)));
        }
    }

    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.unfold.updates.DeviceFoldStateProvider$timeoutRunnable$1] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.unfold.updates.DeviceFoldStateProvider$progressExecutor$1] */
    public DeviceFoldStateProvider(UnfoldTransitionConfig unfoldTransitionConfig, Context context, ScreenStatusProvider screenStatusProvider, CurrentActivityTypeProvider currentActivityTypeProvider, UnfoldKeyguardVisibilityProvider unfoldKeyguardVisibilityProvider, FoldProvider foldProvider, HingeAngleProvider hingeAngleProvider, RotationChangeProvider rotationChangeProvider, Handler handler) {
        this.context = context;
        this.screenStatusProvider = screenStatusProvider;
        this.activityTypeProvider = currentActivityTypeProvider;
        this.unfoldKeyguardVisibilityProvider = unfoldKeyguardVisibilityProvider;
        this.foldProvider = foldProvider;
        this.hingeAngleProvider = hingeAngleProvider;
        this.rotationChangeProvider = rotationChangeProvider;
        this.progressHandler = handler;
        this.halfOpenedTimeoutMillis = ((Number) ((ResourceUnfoldTransitionConfig) unfoldTransitionConfig).halfFoldedTimeoutMillis$delegate.getValue()).intValue();
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        this.outputListeners.add((FoldStateProvider.FoldUpdatesListener) obj);
    }

    public final void assertInProgressThread$2() {
        Handler handler = this.progressHandler;
        if (handler.getLooper().isCurrentThread()) {
            return;
        }
        Thread thread = handler.getLooper().getThread();
        Thread currentThread = Thread.currentThread();
        throw new IllegalStateException(StringsKt__IndentKt.trimMargin$default("should be called from the progress thread.\n                progressThread=" + thread + " tid=" + thread.getId() + "\n                Thread.currentThread()=" + currentThread + " tid=" + currentThread.getId()).toString());
    }

    public final boolean isTransitionInProgress() {
        Integer num;
        Integer num2 = this.lastFoldUpdate;
        return (num2 != null && num2.intValue() == 0) || ((num = this.lastFoldUpdate) != null && num.intValue() == 1);
    }

    public final void notifyFoldUpdate(float f, int i) {
        if (DeviceFoldStateProviderKt.DEBUG) {
            Log.d("DeviceFoldProvider", DeviceFoldStateProviderKt.name(i));
        }
        boolean isTransitionInProgress = isTransitionInProgress();
        Iterator it = this.outputListeners.iterator();
        while (it.hasNext()) {
            ((FoldStateProvider.FoldUpdatesListener) it.next()).onFoldUpdate(i);
        }
        this.lastFoldUpdate = Integer.valueOf(i);
        if (isTransitionInProgress != isTransitionInProgress()) {
            this.lastHingeAngleBeforeTransition = f;
        }
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        this.outputListeners.remove((FoldStateProvider.FoldUpdatesListener) obj);
    }

    public final void start() {
        Boolean bool;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        if (this.isStarted) {
            return;
        }
        this.foldProvider.registerCallback(this.foldStateListener, this.progressExecutor);
        ((LifecycleScreenStatusProvider) this.screenStatusProvider).addCallback(this.screenListener);
        this.hingeAngleProvider.addCallback(this.hingeAngleListener);
        RotationChangeProvider rotationChangeProvider = this.rotationChangeProvider;
        FoldRotationListener foldRotationListener = this.rotationListener;
        rotationChangeProvider.getClass();
        rotationChangeProvider.bgHandler.post(new RotationChangeProvider$addCallback$1(rotationChangeProvider, foldRotationListener));
        ActivityManagerActivityTypeProvider activityManagerActivityTypeProvider = (ActivityManagerActivityTypeProvider) this.activityTypeProvider;
        ActivityManager activityManager = activityManagerActivityTypeProvider.activityManager;
        try {
            Trace.beginSection("isOnHomeActivity");
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            if (runningTasks == null || (runningTaskInfo = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull((List) runningTasks)) == null) {
                bool = null;
            } else {
                bool = Boolean.valueOf(runningTaskInfo.topActivityType == 2);
            }
            Trace.endSection();
            activityManagerActivityTypeProvider._isHomeActivity = bool;
            TaskStackChangeListeners.INSTANCE.registerTaskStackListener(activityManagerActivityTypeProvider.taskStackChangeListener);
            this.isStarted = true;
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }
}
