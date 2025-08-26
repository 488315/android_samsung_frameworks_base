package android.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.SystemClock;
import android.os.Trace;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class TimingsTraceLog {
    public static final boolean DEBUG_BOOT_TIME = true;
    public static final long DEBUG_BOOT_TIME_THRESHOLD = 200;
    private static final int MAX_NESTED_CALLS = 10;
    private int mCurrentLevel;
    private final int mMaxNestedCalls;
    private final String[] mStartNames;
    private final long[] mStartTimes;
    private final String mTag;
    private final long mThreadId;
    private final long mTraceTag;

    public TimingsTraceLog(String str, long j) {
        this(str, j, 10);
    }

    public TimingsTraceLog(String str, long j, int i) {
        this.mCurrentLevel = -1;
        this.mTag = str;
        this.mTraceTag = j;
        this.mThreadId = Thread.currentThread().getId();
        this.mMaxNestedCalls = i;
        this.mStartNames = createAndGetStartNamesArray();
        this.mStartTimes = createAndGetStartTimesArray();
    }

    protected TimingsTraceLog(TimingsTraceLog timingsTraceLog) {
        this.mCurrentLevel = -1;
        this.mTag = timingsTraceLog.mTag;
        this.mTraceTag = timingsTraceLog.mTraceTag;
        this.mThreadId = Thread.currentThread().getId();
        this.mMaxNestedCalls = timingsTraceLog.mMaxNestedCalls;
        this.mStartNames = createAndGetStartNamesArray();
        this.mStartTimes = createAndGetStartTimesArray();
        this.mCurrentLevel = timingsTraceLog.mCurrentLevel;
    }

    private String[] createAndGetStartNamesArray() {
        int i = this.mMaxNestedCalls;
        if (i > 0) {
            return new String[i];
        }
        return null;
    }

    private long[] createAndGetStartTimesArray() {
        int i = this.mMaxNestedCalls;
        if (i > 0) {
            return new long[i];
        }
        return null;
    }

    public void traceBegin(String str) {
        assertSameThread();
        Trace.traceBegin(this.mTraceTag, str);
        int i = this.mCurrentLevel;
        if (i + 1 >= this.mMaxNestedCalls) {
            Slog.w(this.mTag, "not tracing duration of '" + str + "' because already reached " + this.mMaxNestedCalls + " levels");
            return;
        }
        int i2 = i + 1;
        this.mCurrentLevel = i2;
        this.mStartNames[i2] = str;
        this.mStartTimes[i2] = SystemClock.elapsedRealtime();
    }

    public void traceEnd() {
        assertSameThread();
        Trace.traceEnd(this.mTraceTag);
        int i = this.mCurrentLevel;
        if (i < 0) {
            Slog.w(this.mTag, "traceEnd called more times than traceBegin");
            return;
        }
        String str = this.mStartNames[i];
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long[] jArr = this.mStartTimes;
        int i2 = this.mCurrentLevel;
        long j = jElapsedRealtime - jArr[i2];
        this.mCurrentLevel = i2 - 1;
        logDuration(str, j);
    }

    private void assertSameThread() {
        Thread threadCurrentThread = Thread.currentThread();
        if (threadCurrentThread.getId() == this.mThreadId) {
            return;
        }
        throw new IllegalStateException("Instance of TimingsTraceLog can only be called from the thread it was created on (tid: " + this.mThreadId + "), but was from " + threadCurrentThread.getName() + " (tid: " + threadCurrentThread.getId() + NavigationBarInflaterView.KEY_CODE_END);
    }

    public void logDuration(String str, long j) {
        Slog.v(this.mTag, str + " took to complete: " + j + "ms");
        if (j <= 200 || !"SystemServerTiming".equals(this.mTag) || "PhaseActivityManagerReady".equals(str) || "SystemUserUnlock".equals(str) || "StartServices".equals(str)) {
            return;
        }
        Slog.d(this.mTag, "!@Boot_SystemServer: " + j + "ms : " + str.substring(0, Math.min(50, str.length())));
        Slog.i(this.mTag, "!@Boot_EBS:   Took " + j + "ms by '" + str.substring(0, Math.min(50, str.length())) + "'");
    }

    public final List<String> getUnfinishedTracesForDebug() {
        if (this.mStartTimes == null || this.mCurrentLevel < 0) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList(this.mCurrentLevel + 1);
        for (int i = 0; i <= this.mCurrentLevel; i++) {
            arrayList.add(this.mStartNames[i]);
        }
        return arrayList;
    }
}
