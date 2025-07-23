package com.android.internal.os;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.SparseArray;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.os.BinderCallsStats;
import com.android.internal.os.CachedDeviceState;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

/* loaded from: classes5.dex */
public class LooperStats implements Looper.Observer {
    public static final String DEBUG_ENTRY_PREFIX = "__DEBUG_";
    public static final boolean DEFAULT_IGNORE_BATTERY_STATUS = false;
    private static final boolean DISABLED_SCREEN_STATE_TRACKING_VALUE = false;
    private static final int SESSION_POOL_SIZE = 50;
    private CachedDeviceState.TimeInStateStopwatch mBatteryStopwatch;
    private CachedDeviceState.Readonly mDeviceState;
    private final int mEntriesSizeCap;
    private int mSamplingInterval;
    private final SparseArray<Entry> mEntries = new SparseArray<>(512);
    private final Object mLock = new Object();
    private final Entry mOverflowEntry = new Entry("OVERFLOW");
    private final Entry mHashCollisionEntry = new Entry("HASH_COLLISION");
    private final ConcurrentLinkedQueue<DispatchSession> mSessionPool = new ConcurrentLinkedQueue<>();
    private long mStartCurrentTime = System.currentTimeMillis();
    private long mStartElapsedTime = SystemClock.elapsedRealtime();
    private boolean mAddDebugEntries = false;
    private boolean mTrackScreenInteractive = false;
    private boolean mIgnoreBatteryStatus = false;

    public LooperStats(int i, int i2) {
        this.mSamplingInterval = i;
        this.mEntriesSizeCap = i2;
    }

    public void setDeviceState(CachedDeviceState.Readonly readonly) {
        CachedDeviceState.TimeInStateStopwatch timeInStateStopwatch = this.mBatteryStopwatch;
        if (timeInStateStopwatch != null) {
            timeInStateStopwatch.close();
        }
        this.mDeviceState = readonly;
        this.mBatteryStopwatch = readonly.createTimeOnBatteryStopwatch();
    }

    public void setAddDebugEntries(boolean z) {
        this.mAddDebugEntries = z;
    }

    @Override // android.os.Looper.Observer
    public Object messageDispatchStarting() {
        if (deviceStateAllowsCollection() && shouldCollectDetailedData()) {
            DispatchSession poll = this.mSessionPool.poll();
            if (poll == null) {
                poll = new DispatchSession();
            }
            poll.startTimeMicro = getElapsedRealtimeMicro();
            poll.cpuStartMicro = getThreadTimeMicro();
            poll.systemUptimeMillis = getSystemUptimeMillis();
            return poll;
        }
        return DispatchSession.NOT_SAMPLED;
    }

    @Override // android.os.Looper.Observer
    public void messageDispatched(Object obj, Message message) {
        if (deviceStateAllowsCollection()) {
            DispatchSession dispatchSession = (DispatchSession) obj;
            Entry findEntry = findEntry(message, dispatchSession != DispatchSession.NOT_SAMPLED);
            if (findEntry != null) {
                synchronized (findEntry) {
                    findEntry.messageCount++;
                    if (dispatchSession != DispatchSession.NOT_SAMPLED) {
                        findEntry.recordedMessageCount++;
                        long elapsedRealtimeMicro = getElapsedRealtimeMicro() - dispatchSession.startTimeMicro;
                        long threadTimeMicro = getThreadTimeMicro() - dispatchSession.cpuStartMicro;
                        findEntry.totalLatencyMicro += elapsedRealtimeMicro;
                        findEntry.maxLatencyMicro = Math.max(findEntry.maxLatencyMicro, elapsedRealtimeMicro);
                        findEntry.cpuUsageMicro += threadTimeMicro;
                        findEntry.maxCpuUsageMicro = Math.max(findEntry.maxCpuUsageMicro, threadTimeMicro);
                        if (message.getWhen() > 0) {
                            long max = Math.max(0L, dispatchSession.systemUptimeMillis - message.getWhen());
                            findEntry.delayMillis += max;
                            findEntry.maxDelayMillis = Math.max(findEntry.maxDelayMillis, max);
                            findEntry.recordedDelayMessageCount++;
                        }
                    }
                }
            }
            recycleSession(dispatchSession);
        }
    }

    @Override // android.os.Looper.Observer
    public void dispatchingThrewException(Object obj, Message message, Exception exc) {
        if (deviceStateAllowsCollection()) {
            DispatchSession dispatchSession = (DispatchSession) obj;
            Entry findEntry = findEntry(message, dispatchSession != DispatchSession.NOT_SAMPLED);
            if (findEntry != null) {
                synchronized (findEntry) {
                    findEntry.exceptionCount++;
                }
            }
            recycleSession(dispatchSession);
        }
    }

    private boolean deviceStateAllowsCollection() {
        if (this.mIgnoreBatteryStatus) {
            return true;
        }
        CachedDeviceState.Readonly readonly = this.mDeviceState;
        return (readonly == null || readonly.isCharging()) ? false : true;
    }

    public List<ExportedEntry> getEntries() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            int size = this.mEntries.size();
            arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                Entry valueAt = this.mEntries.valueAt(i);
                synchronized (valueAt) {
                    arrayList.add(new ExportedEntry(valueAt));
                }
            }
        }
        maybeAddSpecialEntry(arrayList, this.mOverflowEntry);
        maybeAddSpecialEntry(arrayList, this.mHashCollisionEntry);
        if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
            arrayList.add(createDebugEntry("start_time_millis", this.mStartElapsedTime));
            arrayList.add(createDebugEntry("end_time_millis", SystemClock.elapsedRealtime()));
            arrayList.add(createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
            arrayList.add(createDebugEntry(BinderCallsStats.SettingsObserver.SETTINGS_SAMPLING_INTERVAL_KEY, this.mSamplingInterval));
        }
        return arrayList;
    }

    private ExportedEntry createDebugEntry(String str, long j) {
        Entry entry = new Entry(DEBUG_ENTRY_PREFIX + str);
        entry.messageCount = 1L;
        entry.recordedMessageCount = 1L;
        entry.totalLatencyMicro = j;
        return new ExportedEntry(entry);
    }

    public long getStartTimeMillis() {
        return this.mStartCurrentTime;
    }

    public long getStartElapsedTimeMillis() {
        return this.mStartElapsedTime;
    }

    public long getBatteryTimeMillis() {
        CachedDeviceState.TimeInStateStopwatch timeInStateStopwatch = this.mBatteryStopwatch;
        if (timeInStateStopwatch != null) {
            return timeInStateStopwatch.getMillis();
        }
        return 0L;
    }

    private void maybeAddSpecialEntry(List<ExportedEntry> list, Entry entry) {
        synchronized (entry) {
            if (entry.messageCount > 0 || entry.exceptionCount > 0) {
                list.add(new ExportedEntry(entry));
            }
        }
    }

    public void reset() {
        synchronized (this.mLock) {
            this.mEntries.clear();
        }
        synchronized (this.mHashCollisionEntry) {
            this.mHashCollisionEntry.reset();
        }
        synchronized (this.mOverflowEntry) {
            this.mOverflowEntry.reset();
        }
        this.mStartCurrentTime = System.currentTimeMillis();
        this.mStartElapsedTime = SystemClock.elapsedRealtime();
        CachedDeviceState.TimeInStateStopwatch timeInStateStopwatch = this.mBatteryStopwatch;
        if (timeInStateStopwatch != null) {
            timeInStateStopwatch.reset();
        }
    }

    public void setSamplingInterval(int i) {
        this.mSamplingInterval = i;
    }

    public void setTrackScreenInteractive(boolean z) {
        this.mTrackScreenInteractive = z;
    }

    public void setIgnoreBatteryStatus(boolean z) {
        this.mIgnoreBatteryStatus = z;
    }

    private Entry findEntry(Message message, boolean z) {
        boolean isScreenInteractive = this.mTrackScreenInteractive ? this.mDeviceState.isScreenInteractive() : false;
        int idFor = Entry.idFor(message, isScreenInteractive);
        synchronized (this.mLock) {
            Entry entry = this.mEntries.get(idFor);
            if (entry == null) {
                if (!z) {
                    return null;
                }
                if (this.mEntries.size() >= this.mEntriesSizeCap) {
                    return this.mOverflowEntry;
                }
                entry = new Entry(message, isScreenInteractive);
                this.mEntries.put(idFor, entry);
            }
            return (entry.workSourceUid == message.workSourceUid && entry.handler.getClass() == message.getTarget().getClass() && entry.handler.getLooper().getThread() == message.getTarget().getLooper().getThread() && entry.isInteractive == isScreenInteractive) ? entry : this.mHashCollisionEntry;
        }
    }

    private void recycleSession(DispatchSession dispatchSession) {
        if (dispatchSession == DispatchSession.NOT_SAMPLED || this.mSessionPool.size() >= 50) {
            return;
        }
        this.mSessionPool.add(dispatchSession);
    }

    protected long getThreadTimeMicro() {
        return SystemClock.currentThreadTimeMicro();
    }

    protected long getElapsedRealtimeMicro() {
        return SystemClock.elapsedRealtimeNanos() / 1000;
    }

    protected long getSystemUptimeMillis() {
        return SystemClock.uptimeMillis();
    }

    protected boolean shouldCollectDetailedData() {
        return ThreadLocalRandom.current().nextInt(this.mSamplingInterval) == 0;
    }

    private static class DispatchSession {
        static final DispatchSession NOT_SAMPLED = new DispatchSession();
        public long cpuStartMicro;
        public long startTimeMicro;
        public long systemUptimeMillis;

        private DispatchSession() {
        }
    }

    private static class Entry {
        public long cpuUsageMicro;
        public long delayMillis;
        public long exceptionCount;
        public final Handler handler;
        public final boolean isInteractive;
        public long maxCpuUsageMicro;
        public long maxDelayMillis;
        public long maxLatencyMicro;
        public long messageCount;
        public final String messageName;
        public long recordedDelayMessageCount;
        public long recordedMessageCount;
        public long totalLatencyMicro;
        public final int workSourceUid;

        Entry(Message message, boolean z) {
            this.workSourceUid = message.workSourceUid;
            Handler target = message.getTarget();
            this.handler = target;
            this.messageName = target.getMessageName(message);
            this.isInteractive = z;
        }

        Entry(String str) {
            this.workSourceUid = -1;
            this.messageName = str;
            this.handler = null;
            this.isInteractive = false;
        }

        void reset() {
            this.messageCount = 0L;
            this.recordedMessageCount = 0L;
            this.exceptionCount = 0L;
            this.totalLatencyMicro = 0L;
            this.maxLatencyMicro = 0L;
            this.cpuUsageMicro = 0L;
            this.maxCpuUsageMicro = 0L;
            this.delayMillis = 0L;
            this.maxDelayMillis = 0L;
            this.recordedDelayMessageCount = 0L;
        }

        static int idFor(Message message, boolean z) {
            int i;
            int i2;
            int hashCode = ((((((217 + message.workSourceUid) * 31) + message.getTarget().getLooper().getThread().hashCode()) * 31) + message.getTarget().getClass().hashCode()) * 31) + (z ? MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT);
            if (message.getCallback() != null) {
                i = hashCode * 31;
                i2 = message.getCallback().getClass().hashCode();
            } else {
                i = hashCode * 31;
                i2 = message.what;
            }
            return i + i2;
        }
    }

    public static class ExportedEntry {
        public final long cpuUsageMicros;
        public final long delayMillis;
        public final long exceptionCount;
        public final String handlerClassName;
        public final boolean isInteractive;
        public final long maxCpuUsageMicros;
        public final long maxDelayMillis;
        public final long maxLatencyMicros;
        public final long messageCount;
        public final String messageName;
        public final long recordedDelayMessageCount;
        public final long recordedMessageCount;
        public final String threadName;
        public final long totalLatencyMicros;
        public final int workSourceUid;

        ExportedEntry(Entry entry) {
            this.workSourceUid = entry.workSourceUid;
            if (entry.handler != null) {
                this.handlerClassName = entry.handler.getClass().getName();
                this.threadName = entry.handler.getLooper().getThread().getName();
            } else {
                this.handlerClassName = "";
                this.threadName = "";
            }
            this.isInteractive = entry.isInteractive;
            this.messageName = entry.messageName;
            this.messageCount = entry.messageCount;
            this.recordedMessageCount = entry.recordedMessageCount;
            this.exceptionCount = entry.exceptionCount;
            this.totalLatencyMicros = entry.totalLatencyMicro;
            this.maxLatencyMicros = entry.maxLatencyMicro;
            this.cpuUsageMicros = entry.cpuUsageMicro;
            this.maxCpuUsageMicros = entry.maxCpuUsageMicro;
            this.delayMillis = entry.delayMillis;
            this.maxDelayMillis = entry.maxDelayMillis;
            this.recordedDelayMessageCount = entry.recordedDelayMessageCount;
        }
    }
}
