package com.android.systemui.dreams;

import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.dreams.callbacks.DreamStatusBarStateCallback;
import com.android.systemui.dreams.conditions.DreamCondition;
import com.android.systemui.flags.RestartDozeListener;
import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.condition.ConditionalCoreStartable;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClockImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamMonitor extends ConditionalCoreStartable {
    public static final boolean sIsAlreadyBooted = DeviceState.IS_ALREADY_BOOTED;
    public final DreamStatusBarStateCallback mCallback;
    public final Monitor mConditionMonitor;
    public final DreamCondition mDreamCondition;
    public final RestartDozeListener mRestartDozeListener;

    public DreamMonitor(Monitor monitor, DreamCondition dreamCondition, DreamStatusBarStateCallback dreamStatusBarStateCallback, RestartDozeListener restartDozeListener) {
        super(monitor);
        this.mConditionMonitor = monitor;
        this.mDreamCondition = dreamCondition;
        this.mCallback = dreamStatusBarStateCallback;
        this.mRestartDozeListener = restartDozeListener;
    }

    @Override // com.android.systemui.util.condition.ConditionalCoreStartable
    public final void onStart() {
        if (Log.isLoggable("DreamMonitor", 3)) {
            Log.d("DreamMonitor", "started");
        }
        Monitor.Subscription.Builder builder = new Monitor.Subscription.Builder(this.mCallback);
        builder.mConditions.add(this.mDreamCondition);
        Monitor.Subscription build = builder.build();
        Monitor monitor = this.mConditionMonitor;
        monitor.addSubscription(build, monitor.mPreconditions);
        final RestartDozeListener restartDozeListener = this.mRestartDozeListener;
        if (!restartDozeListener.inited) {
            restartDozeListener.inited = true;
            restartDozeListener.statusBarStateController.addCallback(restartDozeListener.listener);
        }
        if (!sIsAlreadyBooted) {
            Log.w("DreamMonitor", "skipped maybeRestartSleep on boot");
        } else {
            restartDozeListener.bgExecutor.executeDelayed(1000L, new Runnable() { // from class: com.android.systemui.flags.RestartDozeListener$maybeRestartSleep$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecureSettings secureSettings = RestartDozeListener.this.settings;
                    RestartDozeListener.Companion.getClass();
                    if (secureSettings.getBoolForUser(secureSettings.getUserId(), RestartDozeListener.RESTART_SLEEP_KEY, false)) {
                        Log.d("RestartDozeListener", "Restarting sleep state");
                        RestartDozeListener restartDozeListener2 = RestartDozeListener.this;
                        PowerManager powerManager = restartDozeListener2.powerManager;
                        ((SystemClockImpl) restartDozeListener2.systemClock).getClass();
                        powerManager.wakeUp(SystemClock.uptimeMillis(), 2, "RestartDozeListener");
                        RestartDozeListener restartDozeListener3 = RestartDozeListener.this;
                        PowerManager powerManager2 = restartDozeListener3.powerManager;
                        ((SystemClockImpl) restartDozeListener3.systemClock).getClass();
                        powerManager2.goToSleep(SystemClock.uptimeMillis());
                    }
                }
            });
        }
    }
}
