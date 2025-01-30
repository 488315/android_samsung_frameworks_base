package com.android.systemui.sensorprivacy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenSensorUseUtil {
    public final Handler bgHandler;
    public final BroadcastDispatcher broadcastDispatcher;
    public final DisplayLifecycle displayLifecycle;
    public final SubscreenSensorUseUtil$displayLifecycleObserver$1 displayLifecycleObserver = new DisplayLifecycle.Observer() { // from class: com.android.systemui.sensorprivacy.SubscreenSensorUseUtil$displayLifecycleObserver$1
        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            if (z) {
                final SubscreenSensorUseUtil subscreenSensorUseUtil = SubscreenSensorUseUtil.this;
                subscreenSensorUseUtil.bgHandler.postDelayed(new Runnable() { // from class: com.android.systemui.sensorprivacy.SubscreenSensorUseUtil$displayLifecycleObserver$1$onFolderStateChanged$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SubscreenSensorUseUtil subscreenSensorUseUtil2 = SubscreenSensorUseUtil.this;
                        subscreenSensorUseUtil2.registered = false;
                        subscreenSensorUseUtil2.runnable = null;
                        try {
                            subscreenSensorUseUtil2.broadcastDispatcher.unregisterReceiver(subscreenSensorUseUtil2.intentReceiver);
                            subscreenSensorUseUtil2.displayLifecycle.removeObserver(subscreenSensorUseUtil2.displayLifecycleObserver);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 200L);
            }
        }
    };
    public final SubscreenSensorUseUtil$intentReceiver$1 intentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.sensorprivacy.SubscreenSensorUseUtil$intentReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.android.systemui.sensorprivacy.SensorPolicyAction".equals(intent.getAction())) {
                Runnable runnable = SubscreenSensorUseUtil.this.runnable;
                if (runnable != null) {
                    runnable.run();
                }
                SubscreenSensorUseUtil subscreenSensorUseUtil = SubscreenSensorUseUtil.this;
                subscreenSensorUseUtil.registered = false;
                subscreenSensorUseUtil.runnable = null;
                try {
                    subscreenSensorUseUtil.broadcastDispatcher.unregisterReceiver(subscreenSensorUseUtil.intentReceiver);
                    subscreenSensorUseUtil.displayLifecycle.removeObserver(subscreenSensorUseUtil.displayLifecycleObserver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    public boolean registered;
    public Runnable runnable;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.sensorprivacy.SubscreenSensorUseUtil$displayLifecycleObserver$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.sensorprivacy.SubscreenSensorUseUtil$intentReceiver$1] */
    public SubscreenSensorUseUtil(Handler handler, BroadcastDispatcher broadcastDispatcher, DisplayLifecycle displayLifecycle) {
        this.bgHandler = handler;
        this.broadcastDispatcher = broadcastDispatcher;
        this.displayLifecycle = displayLifecycle;
    }
}
