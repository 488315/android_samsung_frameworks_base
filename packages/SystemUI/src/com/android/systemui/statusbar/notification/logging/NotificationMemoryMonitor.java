package com.android.systemui.statusbar.notification.logging;

import android.app.StatsManager;
import android.util.Log;
import com.android.systemui.CoreStartable;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class NotificationMemoryMonitor implements CoreStartable {
    public final NotificationMemoryDumper notificationMemoryDumper;
    public final Lazy notificationMemoryLogger;

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

    public NotificationMemoryMonitor(NotificationMemoryDumper notificationMemoryDumper, Lazy lazy) {
        this.notificationMemoryDumper = notificationMemoryDumper;
        this.notificationMemoryLogger = lazy;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Log.d("NotificationMemory", "NotificationMemoryMonitor initialized.");
        NotificationMemoryDumper notificationMemoryDumper = this.notificationMemoryDumper;
        notificationMemoryDumper.dumpManager.registerNormalDumpable("NotificationMemoryDumper", notificationMemoryDumper);
        Log.i("NotificationMemory", "Registered dumpable.");
        NotificationMemoryLogger notificationMemoryLogger = (NotificationMemoryLogger) this.notificationMemoryLogger.get();
        notificationMemoryLogger.statsManager.setPullAtomCallback(10174, (StatsManager.PullAtomMetadata) null, notificationMemoryLogger.backgroundExecutor, notificationMemoryLogger);
    }
}
