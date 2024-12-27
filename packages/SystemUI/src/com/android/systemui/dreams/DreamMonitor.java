package com.android.systemui.dreams;

import android.util.Log;
import com.android.systemui.dreams.callbacks.DreamStatusBarStateCallback;
import com.android.systemui.dreams.conditions.DreamCondition;
import com.android.systemui.flags.RestartDozeListener;
import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.condition.ConditionalCoreStartable;
import com.android.systemui.util.settings.SecureSettings;

public final class DreamMonitor extends ConditionalCoreStartable {
    public static final boolean IS_ENABLED = DeviceState.isAlreadyBooted();
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
        if (!IS_ENABLED) {
            Log.w("DreamMonitor", "skipped on boot");
            return;
        }
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
        restartDozeListener.bgExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.flags.RestartDozeListener$maybeRestartSleep$1
            @Override // java.lang.Runnable
            public final void run() {
                SecureSettings secureSettings = RestartDozeListener.this.settings;
                RestartDozeListener.Companion.getClass();
                if (secureSettings.getBool(RestartDozeListener.RESTART_SLEEP_KEY, false)) {
                    Log.d("RestartDozeListener", "Restarting sleep state");
                    RestartDozeListener restartDozeListener2 = RestartDozeListener.this;
                    restartDozeListener2.powerManager.wakeUp(restartDozeListener2.systemClock.uptimeMillis(), 2, "RestartDozeListener");
                    final RestartDozeListener restartDozeListener3 = RestartDozeListener.this;
                    restartDozeListener3.bgExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.flags.RestartDozeListener$maybeRestartSleep$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Log.d("RestartDozeListener", "Restarting goToSleep");
                            RestartDozeListener restartDozeListener4 = RestartDozeListener.this;
                            restartDozeListener4.powerManager.goToSleep(restartDozeListener4.systemClock.uptimeMillis());
                        }
                    }, 100L);
                }
            }
        }, 1000L);
    }
}
