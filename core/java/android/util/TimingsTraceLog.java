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

  public void traceBegin(String name) {
    assertSameThread();
    Trace.traceBegin(this.mTraceTag, name);
    int i = this.mCurrentLevel;
    if (i + 1 >= this.mMaxNestedCalls) {
      Slog.m121w(
          this.mTag,
          "not tracing duration of '"
              + name
              + "' because already reached "
              + this.mMaxNestedCalls
              + " levels");
      return;
    }
    int i2 = i + 1;
    this.mCurrentLevel = i2;
    this.mStartNames[i2] = name;
    this.mStartTimes[i2] = SystemClock.elapsedRealtime();
  }

  public void traceEnd() {
    assertSameThread();
    Trace.traceEnd(this.mTraceTag);
    int i = this.mCurrentLevel;
    if (i < 0) {
      Slog.m121w(this.mTag, "traceEnd called more times than traceBegin");
      return;
    }
    String name = this.mStartNames[i];
    long elapsedRealtime = SystemClock.elapsedRealtime();
    long[] jArr = this.mStartTimes;
    int i2 = this.mCurrentLevel;
    long duration = elapsedRealtime - jArr[i2];
    this.mCurrentLevel = i2 - 1;
    logDuration(name, duration);
  }

  private void assertSameThread() {
    Thread currentThread = Thread.currentThread();
    if (currentThread.getId() != this.mThreadId) {
      throw new IllegalStateException(
          "Instance of TimingsTraceLog can only be called from the thread it was created on (tid: "
              + this.mThreadId
              + "), but was from "
              + currentThread.getName()
              + " (tid: "
              + currentThread.getId()
              + NavigationBarInflaterView.KEY_CODE_END);
    }
  }

  public void logDuration(String name, long timeMs) {
    Slog.m119v(this.mTag, name + " took to complete: " + timeMs + "ms");
    if (timeMs <= 200
        || !"SystemServerTiming".equals(this.mTag)
        || "PhaseActivityManagerReady".equals(name)
        || "SystemUserUnlock".equals(name)
        || "StartServices".equals(name)) {
      return;
    }
    Slog.m113d(
        this.mTag,
        "!@Boot_SystemServer: "
            + timeMs
            + "ms : "
            + name.substring(0, Math.min(50, name.length())));
    Slog.m117i(
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
