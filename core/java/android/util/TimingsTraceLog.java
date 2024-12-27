package android.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.SystemClock;
import android.os.Trace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public TimingsTraceLog(String tag, long traceTag) {
        this(tag, traceTag, 10);
    }

    public TimingsTraceLog(String tag, long traceTag, int maxNestedCalls) {
        this.mCurrentLevel = -1;
        this.mTag = tag;
        this.mTraceTag = traceTag;
        this.mThreadId = Thread.currentThread().getId();
        this.mMaxNestedCalls = maxNestedCalls;
        this.mStartNames = createAndGetStartNamesArray();
        this.mStartTimes = createAndGetStartTimesArray();
    }

    protected TimingsTraceLog(TimingsTraceLog other) {
        this.mCurrentLevel = -1;
        this.mTag = other.mTag;
        this.mTraceTag = other.mTraceTag;
        this.mThreadId = Thread.currentThread().getId();
        this.mMaxNestedCalls = other.mMaxNestedCalls;
        this.mStartNames = createAndGetStartNamesArray();
        this.mStartTimes = createAndGetStartTimesArray();
        this.mCurrentLevel = other.mCurrentLevel;
    }

    private String[] createAndGetStartNamesArray() {
        if (this.mMaxNestedCalls > 0) {
            return new String[this.mMaxNestedCalls];
        }
        return null;
    }

    private long[] createAndGetStartTimesArray() {
        if (this.mMaxNestedCalls > 0) {
            return new long[this.mMaxNestedCalls];
        }
        return null;
    }

    public void traceBegin(String name) {
        assertSameThread();
        Trace.traceBegin(this.mTraceTag, name);
        if (this.mCurrentLevel + 1 >= this.mMaxNestedCalls) {
            Slog.w(
                    this.mTag,
                    "not tracing duration of '"
                            + name
                            + "' because already reached "
                            + this.mMaxNestedCalls
                            + " levels");
            return;
        }
        this.mCurrentLevel++;
        this.mStartNames[this.mCurrentLevel] = name;
        this.mStartTimes[this.mCurrentLevel] = SystemClock.elapsedRealtime();
    }

    public void traceEnd() {
        assertSameThread();
        Trace.traceEnd(this.mTraceTag);
        if (this.mCurrentLevel < 0) {
            Slog.w(this.mTag, "traceEnd called more times than traceBegin");
            return;
        }
        String name = this.mStartNames[this.mCurrentLevel];
        long duration = SystemClock.elapsedRealtime() - this.mStartTimes[this.mCurrentLevel];
        this.mCurrentLevel--;
        logDuration(name, duration);
    }

    private void assertSameThread() {
        Thread currentThread = Thread.currentThread();
        if (currentThread.getId() != this.mThreadId) {
            throw new IllegalStateException(
                    "Instance of TimingsTraceLog can only be called from the thread it was created"
                        + " on (tid: "
                            + this.mThreadId
                            + "), but was from "
                            + currentThread.getName()
                            + " (tid: "
                            + currentThread.getId()
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public void logDuration(String name, long timeMs) {
        Slog.v(this.mTag, name + " took to complete: " + timeMs + "ms");
        if (timeMs <= 200
                || !"SystemServerTiming".equals(this.mTag)
                || "PhaseActivityManagerReady".equals(name)
                || "SystemUserUnlock".equals(name)
                || "StartServices".equals(name)) {
            return;
        }
        Slog.d(
                this.mTag,
                "!@Boot_SystemServer: "
                        + timeMs
                        + "ms : "
                        + name.substring(0, Math.min(50, name.length())));
        Slog.i(
                this.mTag,
                "!@Boot_EBS:   Took "
                        + timeMs
                        + "ms by '"
                        + name.substring(0, Math.min(50, name.length()))
                        + "'");
    }

    public final List<String> getUnfinishedTracesForDebug() {
        if (this.mStartTimes == null || this.mCurrentLevel < 0) {
            return Collections.emptyList();
        }
        ArrayList<String> list = new ArrayList<>(this.mCurrentLevel + 1);
        for (int i = 0; i <= this.mCurrentLevel; i++) {
            list.add(this.mStartNames[i]);
        }
        return list;
    }
}
