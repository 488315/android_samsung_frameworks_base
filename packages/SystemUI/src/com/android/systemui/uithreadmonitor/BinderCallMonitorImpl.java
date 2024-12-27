package com.android.systemui.uithreadmonitor;

import android.util.Log;
import android.util.SparseArray;
import com.android.systemui.log.SamsungServiceLogger;

public final class BinderCallMonitorImpl implements BinderCallMonitor {
    public static int sSkipCallCount = -1;
    public SamsungServiceLogger mLogger;
    public final SparseArray mMonitorInfo = new SparseArray();
    public long mDuration = 1;

    public final class Item {
        public final long compareDuration;
        public String stackTrace;
        public final long startTime;

        public /* synthetic */ Item(long j, int i) {
            this(j);
        }

        private Item(long j) {
            this.compareDuration = j;
            this.startTime = System.nanoTime();
        }
    }

    public final class MonitorInfo {
        public long duration;
        public boolean enabled;
        public boolean infinite;
        public long timeOut;

        public /* synthetic */ MonitorInfo(int i) {
            this();
        }

        private MonitorInfo() {
            this.enabled = false;
            this.infinite = false;
        }
    }

    public final boolean startMonitoring(int i, long j, long j2) {
        int i2 = 0;
        if (i < 0 || i >= 6 || j < 1 || j2 < 3000 || j2 > 8000) {
            Log.w("BinderCallMonitor", "not monitoring started");
            return false;
        }
        synchronized (this.mMonitorInfo) {
            try {
                MonitorInfo monitorInfo = (MonitorInfo) this.mMonitorInfo.get(i);
                if (monitorInfo == null) {
                    monitorInfo = new MonitorInfo(i2);
                }
                monitorInfo.duration = j * 1000000;
                if (i == 0) {
                    monitorInfo.infinite = true;
                } else {
                    monitorInfo.timeOut = System.currentTimeMillis() + j2;
                }
                monitorInfo.enabled = true;
                this.mMonitorInfo.put(i, monitorInfo);
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }
}
