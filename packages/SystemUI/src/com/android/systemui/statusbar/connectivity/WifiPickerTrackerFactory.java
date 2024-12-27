package com.android.systemui.statusbar.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.SimpleClock;
import androidx.lifecycle.LifecycleRegistry;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.time.SystemClock;
import com.android.wifitrackerlib.WifiPickerTracker;
import java.time.ZoneOffset;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WifiPickerTrackerFactory {
    public final WifiPickerTrackerFactory$clock$1 clock;
    public final ConnectivityManager connectivityManager;
    public final Context context;
    public final Handler mainHandler;
    public final SystemClock systemClock;
    public final ThreadFactory threadFactory;
    public final WifiManager wifiManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.statusbar.connectivity.WifiPickerTrackerFactory$clock$1] */
    public WifiPickerTrackerFactory(Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, SystemClock systemClock, Handler handler, ThreadFactory threadFactory) {
        this.context = context;
        this.wifiManager = wifiManager;
        this.connectivityManager = connectivityManager;
        this.systemClock = systemClock;
        this.mainHandler = handler;
        this.threadFactory = threadFactory;
        final ZoneOffset zoneOffset = ZoneOffset.UTC;
        this.clock = new SimpleClock(zoneOffset) { // from class: com.android.systemui.statusbar.connectivity.WifiPickerTrackerFactory$clock$1
            public final long millis() {
                return WifiPickerTrackerFactory.this.systemClock.elapsedRealtime();
            }
        };
    }

    public final WifiPickerTracker create(LifecycleRegistry lifecycleRegistry, WifiPickerTracker.WifiPickerTrackerCallback wifiPickerTrackerCallback, String str) {
        WifiManager wifiManager = this.wifiManager;
        if (wifiManager == null) {
            return null;
        }
        return new WifiPickerTracker(lifecycleRegistry, this.context, wifiManager, this.connectivityManager, this.mainHandler, this.threadFactory.buildHandlerOnNewThread("WifiPickerTracker-".concat(str)), this.clock, 15000L, 10000L, wifiPickerTrackerCallback, null, true);
    }
}
