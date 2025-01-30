package android.view;

import android.animation.AnimationHandler;
import android.app.ActivityThread;
import android.graphics.FrameInfo;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TimeUtils;
import android.view.animation.AnimationUtils;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/* loaded from: classes4.dex */
public final class Choreographer {
  public static final int CALLBACK_ANIMATION = 1;
  public static final int CALLBACK_COMMIT = 4;
  public static final int CALLBACK_INPUT = 0;
  public static final int CALLBACK_INSETS_ANIMATION = 2;
  private static final int CALLBACK_LAST = 4;
  public static final int CALLBACK_TRAVERSAL = 3;
  private static final boolean DEBUG_FRAMES = false;
  private static final boolean DEBUG_JANK = false;
  private static final long DEFAULT_FRAME_DELAY = 10;
  private static final long DEFAULT_THRESHOLD_BG_DELAY = 4000;
  private static final String IDS_TAG = "IDS_TAG";
  private static final int MSG_DO_FRAME = 0;
  private static final int MSG_DO_SCHEDULE_CALLBACK = 2;
  private static final int MSG_DO_SCHEDULE_VSYNC = 1;
  private static final int MSG_UPDATE_ACTIVITY_STATE = 3;
  private static final int STB_FRAME_INTERVAL_FLOOR_120 = 8;
  private static final int STB_FRAME_INTERVAL_FLOOR_60 = 16;
  private static final String TAG = "Choreographer";
  private static volatile Choreographer mMainInstance;
  private static AtomicBoolean sScrollDf;
  public final int DO_AID;
  public final int DO_DOT;
  public final int DO_IDS;
  public final int DO_STB;
  private final long FLING_TIME_THRESHOLD_NANOS;
  private boolean mBgWaitingDelaySetting;
  private CallbackRecord mCallbackPool;
  private final CallbackQueue[] mCallbackQueues;
  private boolean mCallbacksRunning;
  private int mDebugCallStackCnt;
  private BiConsumer<String, String> mDebugCallbackConsumer;
  private int mDebugDispatchThresholdMs;
  private boolean mDebugPrintNextFrameTimeDelta;
  private final FrameDisplayEventReceiver mDisplayEventReceiver;
  private boolean mEnabledDebugCallback;
  private int mFPSDivisor;
  private boolean[] mFlingSTBFlag;
  private long mFlingStartTime;
  private final FrameData mFrameData;
  FrameInfo mFrameInfo;

  @Deprecated private long mFrameIntervalNanos;
  private boolean mFrameScheduled;
  private long mFramesSinceSTB;
  private final FrameHandler mHandler;
  private boolean mIsFg;
  private boolean mIsFirstBBA;
  private long mLastFrameIntervalNanos;
  private long mLastFrameTimeNanos;
  private final DisplayEventReceiver.VsyncEventData mLastVsyncEventData;
  private final Object mLock;
  private final Looper mLooper;
  private long mSTBCount;
  private static long sSTBFrameTimeThreshold = Long.MAX_VALUE;
  private static volatile long sFrameDelay = 10;
  private static final ThreadLocal<Choreographer> sThreadInstance =
      new ThreadLocal<Choreographer>() { // from class: android.view.Choreographer.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public Choreographer initialValue() {
          Looper looper = Looper.myLooper();
          if (looper == null) {
            throw new IllegalStateException("The current thread must have a looper!");
          }
          Choreographer choreographer = new Choreographer(looper, 0);
          if (looper == Looper.getMainLooper()) {
            Choreographer.mMainInstance = choreographer;
          }
          return choreographer;
        }
      };
  private static final ThreadLocal<Choreographer> sSfThreadInstance =
      new ThreadLocal<Choreographer>() { // from class: android.view.Choreographer.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public Choreographer initialValue() {
          Looper looper = Looper.myLooper();
          if (looper == null) {
            throw new IllegalStateException("The current thread must have a looper!");
          }
          return new Choreographer(looper, 1);
        }
      };
  private static final boolean USE_VSYNC =
      SystemProperties.getBoolean("debug.choreographer.vsync", true);
  private static final boolean USE_FRAME_TIME =
      SystemProperties.getBoolean("debug.choreographer.frametime", true);
  private static final int SKIPPED_FRAME_WARNING_LIMIT =
      SystemProperties.getInt("debug.choreographer.skipwarning", 30);
  private static final Object FRAME_CALLBACK_TOKEN =
      new Object() { // from class: android.view.Choreographer.3
        public String toString() {
          return "FRAME_CALLBACK_TOKEN";
        }
      };
  private static final Object VSYNC_CALLBACK_TOKEN =
      new Object() { // from class: android.view.Choreographer.4
        public String toString() {
          return "VSYNC_CALLBACK_TOKEN";
        }
      };
  private static final String[] CALLBACK_TRACE_TITLES = {
    "input", "animation", "insets_animation", "traversal", "commit"
  };

  public interface FrameCallback {
    void doFrame(long j);
  }

  public interface VsyncCallback {
    void onVsync(FrameData frameData);
  }

  private Choreographer(Looper looper, int vsyncSource) {
    this(looper, vsyncSource, 0L);
  }

  private Choreographer(Looper looper, int vsyncSource, long layerHandle) {
    FrameDisplayEventReceiver frameDisplayEventReceiver;
    this.DO_AID = 0;
    this.DO_DOT = 1;
    this.DO_IDS = 2;
    this.DO_STB = 3;
    this.mFramesSinceSTB = Long.MIN_VALUE;
    this.mSTBCount = 0L;
    this.mFlingStartTime = 0L;
    this.FLING_TIME_THRESHOLD_NANOS = 3000000000L;
    this.mIsFg = true;
    this.mIsFirstBBA = true;
    this.mBgWaitingDelaySetting = false;
    this.mLock = new Object();
    this.mFPSDivisor = 1;
    this.mLastVsyncEventData = new DisplayEventReceiver.VsyncEventData();
    this.mFrameData = new FrameData();
    this.mFrameInfo = new FrameInfo();
    this.mEnabledDebugCallback = false;
    this.mDebugCallbackConsumer = null;
    this.mDebugCallStackCnt = 5;
    this.mDebugDispatchThresholdMs = 20;
    this.mLooper = looper;
    this.mHandler = new FrameHandler(looper);
    if (USE_VSYNC) {
      frameDisplayEventReceiver = new FrameDisplayEventReceiver(looper, vsyncSource, layerHandle);
    } else {
      frameDisplayEventReceiver = null;
    }
    this.mDisplayEventReceiver = frameDisplayEventReceiver;
    this.mLastFrameTimeNanos = Long.MIN_VALUE;
    this.mFrameIntervalNanos = (long) (1.0E9f / getRefreshRate());
    this.mCallbackQueues = new CallbackQueue[5];
    for (int i = 0; i <= 4; i++) {
      this.mCallbackQueues[i] = new CallbackQueue();
    }
    setFPSDivisor(SystemProperties.getInt(ThreadedRenderer.DEBUG_FPS_DIVISOR, 1));
  }

  private static float getRefreshRate() {
    DisplayInfo di = DisplayManagerGlobal.getInstance().getDisplayInfo(0);
    return di.getRefreshRate();
  }

  public static Choreographer getInstance() {
    Choreographer ch = sThreadInstance.get();
    if (mMainInstance != null && ch != mMainInstance) {
      ch.mIsFg = true;
    }
    return ch;
  }

  public static Choreographer getSfInstance() {
    return sSfThreadInstance.get();
  }

  static Choreographer getInstanceForSurfaceControl(long layerHandle, Looper looper) {
    if (looper == null) {
      throw new IllegalStateException("The current thread must have a looper!");
    }
    return new Choreographer(looper, 0, layerHandle);
  }

  public static Choreographer getMainThreadInstance() {
    return mMainInstance;
  }

  public static void releaseInstance() {
    ThreadLocal<Choreographer> threadLocal = sThreadInstance;
    Choreographer old = threadLocal.get();
    threadLocal.remove();
    old.dispose();
  }

  private void dispose() {
    this.mDisplayEventReceiver.dispose();
  }

  void invalidate() {
    dispose();
  }

  boolean isTheLooperSame(Looper looper) {
    return this.mLooper == looper;
  }

  public static long getFrameDelay() {
    return sFrameDelay;
  }

  public static void setFrameDelay(long frameDelay) {
    sFrameDelay = frameDelay;
  }

  public static long subtractFrameDelay(long delayMillis) {
    long frameDelay = sFrameDelay;
    if (delayMillis <= frameDelay) {
      return 0L;
    }
    return delayMillis - frameDelay;
  }

  public long getFrameIntervalNanos() {
    long j;
    synchronized (this.mLock) {
      j = this.mLastFrameIntervalNanos;
    }
    return j;
  }

  void dump(String prefix, PrintWriter writer) {
    String innerPrefix = prefix + "  ";
    writer.print(prefix);
    writer.println("Choreographer:");
    writer.print(innerPrefix);
    writer.print("mFrameScheduled=");
    writer.println(this.mFrameScheduled);
    writer.print(innerPrefix);
    writer.print("mLastFrameTime=");
    writer.println(TimeUtils.formatUptime(this.mLastFrameTimeNanos / 1000000));
  }

  public void postCallback(int callbackType, Runnable action, Object token) {
    postCallbackDelayed(callbackType, action, token, 0L);
  }

  public void postCallbackDelayed(
      int callbackType, Runnable action, Object token, long delayMillis) {
    if (action == null) {
      throw new IllegalArgumentException("action must not be null");
    }
    if (callbackType < 0 || callbackType > 4) {
      throw new IllegalArgumentException("callbackType is invalid");
    }
    postCallbackDelayedInternal(callbackType, action, token, delayMillis);
  }

  public void setIsFg(boolean isFg) {
    if (this.mIsFg == isFg) {
      return;
    }
    long delayValue =
        AnimationHandler.getInstance().getMaxAnimationCallbackDuration()
            + DEFAULT_THRESHOLD_BG_DELAY;
    synchronized (this.mLock) {
      this.mHandler.removeMessages(3);
      int i = 0;
      if (!isFg) {
        this.mBgWaitingDelaySetting = true;
        Message msg = this.mHandler.obtainMessage();
        msg.what = 3;
        if (!isFg) {
          i = 1;
        }
        msg.arg1 = i;
        msg.setAsynchronous(true);
        this.mHandler.sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayValue);
      } else {
        this.mIsFg = isFg;
        this.mIsFirstBBA = true;
        this.mBgWaitingDelaySetting = false;
      }
    }
  }

  private void postCallbackDelayedInternal(
      int callbackType, Object action, Object token, long delayMillis) {
    synchronized (this.mLock) {
      try {
        try {
          boolean z = this.mIsFg;
          boolean z2 = this.mBgWaitingDelaySetting;
          if (z == z2 && (callbackType == 0 || callbackType == 3)) {
            this.mIsFg = true;
            this.mIsFirstBBA = true;
            if (z2) {
              this.mHandler.removeMessages(3);
            }
            this.mBgWaitingDelaySetting = false;
          }
          long now = SystemClock.uptimeMillis();
          long dueTime = now + delayMillis;
          if (this.mEnabledDebugCallback) {
            this.mCallbackQueues[callbackType].addCallbackLocked(
                dueTime, action, token, Debug.getCallers(this.mDebugCallStackCnt, " "));
          } else {
            this.mCallbackQueues[callbackType].addCallbackLocked(dueTime, action, token, null);
          }
          if (dueTime <= now) {
            scheduleFrameLocked(now);
          } else {
            Message msg = this.mHandler.obtainMessage(2, action);
            msg.arg1 = callbackType;
            msg.setAsynchronous(true);
            this.mHandler.sendMessageAtTime(msg, dueTime);
          }
        } catch (Throwable th) {
          th = th;
          throw th;
        }
      } catch (Throwable th2) {
        th = th2;
        throw th;
      }
    }
  }

  public void postVsyncCallback(VsyncCallback callback) {
    if (callback == null) {
      throw new IllegalArgumentException("callback must not be null");
    }
    postCallbackDelayedInternal(1, callback, VSYNC_CALLBACK_TOKEN, 0L);
  }

  public void removeCallbacks(int callbackType, Runnable action, Object token) {
    if (callbackType < 0 || callbackType > 4) {
      throw new IllegalArgumentException("callbackType is invalid");
    }
    removeCallbacksInternal(callbackType, action, token);
  }

  private void removeCallbacksInternal(int callbackType, Object action, Object token) {
    synchronized (this.mLock) {
      this.mCallbackQueues[callbackType].removeCallbacksLocked(action, token);
      if (action != null && token == null) {
        this.mHandler.removeMessages(2, action);
      }
    }
  }

  public void postFrameCallback(FrameCallback callback) {
    postFrameCallbackDelayed(callback, 0L);
  }

  public void postFrameCallbackDelayed(FrameCallback callback, long delayMillis) {
    if (callback == null) {
      throw new IllegalArgumentException("callback must not be null");
    }
    postCallbackDelayedInternal(1, callback, FRAME_CALLBACK_TOKEN, delayMillis);
  }

  public void removeFrameCallback(FrameCallback callback) {
    if (callback == null) {
      throw new IllegalArgumentException("callback must not be null");
    }
    removeCallbacksInternal(1, callback, FRAME_CALLBACK_TOKEN);
  }

  public void removeVsyncCallback(VsyncCallback callback) {
    if (callback == null) {
      throw new IllegalArgumentException("callback must not be null");
    }
    removeCallbacksInternal(1, callback, VSYNC_CALLBACK_TOKEN);
  }

  public long getFrameTime() {
    return getFrameTimeNanos() / 1000000;
  }

  public long getFrameTimeNanos() {
    long nanoTime;
    synchronized (this.mLock) {
      if (!this.mCallbacksRunning) {
        throw new IllegalStateException(
            "This method must only be called as part of a callback while a frame is in progress.");
      }
      nanoTime = USE_FRAME_TIME ? this.mLastFrameTimeNanos : System.nanoTime();
    }
    return nanoTime;
  }

  public long getLastFrameTimeNanos() {
    long nanoTime;
    synchronized (this.mLock) {
      nanoTime = USE_FRAME_TIME ? this.mLastFrameTimeNanos : System.nanoTime();
    }
    return nanoTime;
  }

  private void scheduleFrameLocked(long now) {
    if (!this.mFrameScheduled) {
      this.mFrameScheduled = true;
      if (USE_VSYNC) {
        if (!isRunningOnLooperThreadLocked()) {
          Message msg = this.mHandler.obtainMessage(1);
          msg.setAsynchronous(true);
          this.mHandler.sendMessageAtFrontOfQueue(msg);
          return;
        }
        scheduleVsyncLocked();
        return;
      }
      long nextFrameTime = Math.max((this.mLastFrameTimeNanos / 1000000) + sFrameDelay, now);
      Message msg2 = this.mHandler.obtainMessage(0);
      msg2.setAsynchronous(true);
      this.mHandler.sendMessageAtTime(msg2, nextFrameTime);
    }
  }

  public long getVsyncId() {
    return this.mLastVsyncEventData.preferredFrameTimeline().vsyncId;
  }

  public long getFrameDeadline() {
    return this.mLastVsyncEventData.preferredFrameTimeline().deadline;
  }

  void setFPSDivisor(int divisor) {
    if (divisor <= 0) {
      divisor = 1;
    }
    this.mFPSDivisor = divisor;
    ThreadedRenderer.setFPSDivisor(divisor);
  }

  private void traceMessage(String msg) {
    Trace.traceBegin(8L, msg);
    Trace.traceEnd(8L);
  }

  public DisplayMetrics getMetrics() {
    DisplayMetrics dm = new DisplayMetrics();
    DisplayInfo di = DisplayManagerGlobal.getInstance().getDisplayInfo(0);
    if (di != null) {
      di.getAppMetrics(dm);
      return dm;
    }
    return null;
  }

  long getSTBCount() {
    return this.mSTBCount;
  }

  void resetSTBCount() {
    this.mSTBCount = 0L;
  }

  public long getFramesSinceSTB() {
    return this.mFramesSinceSTB;
  }

  public void setFlingSTBFlag(boolean STBFlag, int index) {
    this.mFlingSTBFlag[index] = STBFlag;
    if (STBFlag) {
      this.mFlingStartTime = System.nanoTime();
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:71:0x01d4 A[Catch: all -> 0x0205, TryCatch #0 {all -> 0x0205, blocks: (B:97:0x01b1, B:69:0x01bb, B:71:0x01d4, B:72:0x01d7, B:74:0x01db, B:76:0x01df, B:77:0x01e8), top: B:96:0x01b1 }] */
  /* JADX WARN: Removed duplicated region for block: B:80:0x01fd  */
  /* JADX WARN: Removed duplicated region for block: B:88:0x025c  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  void doFrame(long frameTimeNanos, int frame, DisplayEventReceiver.VsyncEventData vsyncEventData) {
    long j;
    Object obj;
    boolean resynced;
    long frameTimeNanos2;
    FrameTimeline timeline;
    long j2;
    long j3;
    long j4;
    ActivityThread.currentActivityThread().getIdsController().closeIdsWindow();
    long frameIntervalNanos = vsyncEventData.frameInterval;
    boolean resynced2 = false;
    long j5 = 8;
    try {
      FrameTimeline timeline2 = this.mFrameData.update(frameTimeNanos, vsyncEventData);
      if (Trace.isTagEnabled(8L)) {
        try {
          Trace.traceBegin(8L, "Choreographer#doFrame " + timeline2.mVsyncId);
        } catch (Throwable th) {
          th = th;
          j = 8;
          AnimationUtils.unlockAnimationClock();
          if (resynced2) {}
          Trace.traceEnd(j);
          throw th;
        }
      }
      Object obj2 = this.mLock;
      synchronized (obj2) {
        try {
          if (this.mFrameScheduled) {
            long startNanos = System.nanoTime();
            long jitterNanos = startNanos - frameTimeNanos;
            if (jitterNanos >= frameIntervalNanos) {
              long frameTimeNanos3 = startNanos;
              try {
                if (frameIntervalNanos == 0) {
                  Log.m98i(TAG, "Vsync data empty due to timeout");
                } else {
                  long lastFrameOffset = jitterNanos % frameIntervalNanos;
                  long frameTimeNanos4 = frameTimeNanos3 - lastFrameOffset;
                  try {
                    long skippedFrames = jitterNanos / frameIntervalNanos;
                    j5 = SKIPPED_FRAME_WARNING_LIMIT;
                    if (skippedFrames >= j5) {
                      try {
                        Log.m98i(
                            TAG,
                            "Skipped "
                                + skippedFrames
                                + " frames!  The application may be doing too much work on its main"
                                + " thread.");
                      } catch (Throwable th2) {
                        th = th2;
                        obj = obj2;
                        j = 8;
                      }
                    }
                    frameTimeNanos3 = frameTimeNanos4;
                  } catch (Throwable th3) {
                    th = th3;
                    obj = obj2;
                    j = 8;
                  }
                }
                try {
                  resynced = true;
                  frameTimeNanos2 = frameTimeNanos3;
                  timeline =
                      this.mFrameData.update(
                          frameTimeNanos3, this.mDisplayEventReceiver, jitterNanos);
                } catch (Throwable th4) {
                  th = th4;
                  obj = obj2;
                  j = 8;
                }
              } catch (Throwable th5) {
                th = th5;
                obj = obj2;
                j = j5;
              }
            } else {
              resynced = false;
              frameTimeNanos2 = frameTimeNanos;
              timeline = timeline2;
            }
            try {
              long j6 = this.mLastFrameTimeNanos;
              try {
                if (frameTimeNanos2 < j6) {
                  traceMessage("Frame time goes backward");
                  scheduleVsyncLocked();
                  AnimationUtils.unlockAnimationClock();
                  if (resynced) {
                    j4 = 8;
                    Trace.traceEnd(8L);
                  } else {
                    j4 = 8;
                  }
                  Trace.traceEnd(j4);
                  return;
                }
                int i = this.mFPSDivisor;
                if (i > 1) {
                  long timeSinceVsync = frameTimeNanos2 - j6;
                  if (timeSinceVsync < i * frameIntervalNanos && timeSinceVsync > 0) {
                    traceMessage("Frame skipped due to FPSDivisor");
                    scheduleVsyncLocked();
                    AnimationUtils.unlockAnimationClock();
                    if (resynced) {
                      j3 = 8;
                      Trace.traceEnd(8L);
                    } else {
                      j3 = 8;
                    }
                    Trace.traceEnd(j3);
                    return;
                  }
                  j2 = 8;
                } else {
                  j2 = 8;
                }
                try {
                  try {
                    obj = obj2;
                    long frameTimeNanos5 = frameTimeNanos2;
                    try {
                      this.mFrameInfo.setVsync(
                          frameTimeNanos,
                          frameTimeNanos2,
                          vsyncEventData.preferredFrameTimeline().vsyncId,
                          vsyncEventData.preferredFrameTimeline().deadline,
                          startNanos,
                          vsyncEventData.frameInterval);
                      this.mFrameScheduled = false;
                      try {
                        this.mLastFrameTimeNanos = frameTimeNanos5;
                        try {
                          this.mLastFrameIntervalNanos = frameIntervalNanos;
                          this.mLastVsyncEventData.copyFrom(vsyncEventData);
                          if (resynced) {
                            try {
                              if (Trace.isTagEnabled(j2)) {
                                String message =
                                    String.format(
                                        "Choreographer#doFrame - resynced to %d in %.1fms",
                                        Long.valueOf(timeline.mVsyncId),
                                        Float.valueOf(
                                            (timeline.mDeadlineNanos - startNanos) * 1.0E-6f));
                                j = j2;
                                try {
                                  Trace.traceBegin(j, message);
                                  AnimationUtils.lockAnimationClock(frameTimeNanos5 / 1000000);
                                  this.mFrameInfo.markInputHandlingStart();
                                  doCallbacks(0, frameIntervalNanos);
                                  this.mFrameInfo.markAnimationsStart();
                                  if (this.mIsFg) {
                                    doCallbacks(1, frameIntervalNanos);
                                  }
                                  if (!this.mIsFg && this.mIsFirstBBA) {
                                    this.mIsFirstBBA = false;
                                    Log.m94d(
                                        TAG,
                                        "CoreRune.SYSPERF_ACTIVE_APP_BBA_ENABLE : stop animation in"
                                            + " background states");
                                  }
                                  doCallbacks(2, frameIntervalNanos);
                                  this.mFrameInfo.markPerformTraversalsStart();
                                  doCallbacks(3, frameIntervalNanos);
                                  doCallbacks(4, frameIntervalNanos);
                                  AnimationUtils.unlockAnimationClock();
                                  if (resynced) {
                                    Trace.traceEnd(j);
                                  }
                                  Trace.traceEnd(j);
                                  return;
                                } catch (Throwable th6) {
                                  th = th6;
                                  resynced2 = resynced;
                                  AnimationUtils.unlockAnimationClock();
                                  if (resynced2) {
                                    Trace.traceEnd(j);
                                  }
                                  Trace.traceEnd(j);
                                  throw th;
                                }
                              }
                            } catch (Throwable th7) {
                              th = th7;
                              j = j2;
                              resynced2 = resynced;
                              AnimationUtils.unlockAnimationClock();
                              if (resynced2) {}
                              Trace.traceEnd(j);
                              throw th;
                            }
                          }
                          j = j2;
                          AnimationUtils.lockAnimationClock(frameTimeNanos5 / 1000000);
                          this.mFrameInfo.markInputHandlingStart();
                          doCallbacks(0, frameIntervalNanos);
                          this.mFrameInfo.markAnimationsStart();
                          if (this.mIsFg) {}
                          if (!this.mIsFg) {
                            this.mIsFirstBBA = false;
                            Log.m94d(
                                TAG,
                                "CoreRune.SYSPERF_ACTIVE_APP_BBA_ENABLE : stop animation in"
                                    + " background states");
                          }
                          doCallbacks(2, frameIntervalNanos);
                          this.mFrameInfo.markPerformTraversalsStart();
                          doCallbacks(3, frameIntervalNanos);
                          doCallbacks(4, frameIntervalNanos);
                          AnimationUtils.unlockAnimationClock();
                          if (resynced) {}
                          Trace.traceEnd(j);
                          return;
                        } catch (Throwable th8) {
                          th = th8;
                          j = j2;
                          resynced2 = resynced;
                        }
                      } catch (Throwable th9) {
                        th = th9;
                        j = j2;
                        resynced2 = resynced;
                      }
                    } catch (Throwable th10) {
                      th = th10;
                      j = j2;
                      resynced2 = resynced;
                    }
                  } catch (Throwable th11) {
                    th = th11;
                    obj = obj2;
                    j = j2;
                    resynced2 = resynced;
                  }
                } catch (Throwable th12) {
                  th = th12;
                  obj = obj2;
                  j = j2;
                  resynced2 = resynced;
                  while (true) {
                    try {
                      try {
                        throw th;
                      } catch (Throwable th13) {
                        th = th13;
                        AnimationUtils.unlockAnimationClock();
                        if (resynced2) {}
                        Trace.traceEnd(j);
                        throw th;
                      }
                    } catch (Throwable th14) {
                      th = th14;
                    }
                  }
                }
              } catch (Throwable th15) {
                th = th15;
                obj = obj2;
                resynced2 = resynced;
                j = 8;
              }
            } catch (Throwable th16) {
              th = th16;
              obj = obj2;
              j = 8;
            }
          } else {
            try {
              traceMessage("Frame not scheduled");
              AnimationUtils.unlockAnimationClock();
              if (0 != 0) {
                Trace.traceEnd(8L);
              }
              Trace.traceEnd(8L);
              return;
            } catch (Throwable th17) {
              th = th17;
              obj = obj2;
              j = 8;
            }
          }
        } catch (Throwable th18) {
          th = th18;
          obj = obj2;
          j = 8;
        }
        while (true) {
          throw th;
        }
      }
    } catch (Throwable th19) {
      th = th19;
      j = 8;
    }
  }

  void doCallbacks(int callbackType, long frameIntervalNanos) {
    long frameTimeNanos = this.mFrameData.mFrameTimeNanos;
    synchronized (this.mLock) {
      long now = System.nanoTime();
      CallbackRecord callbacks =
          this.mCallbackQueues[callbackType].extractDueCallbacksLocked(now / 1000000);
      if (callbacks == null) {
        return;
      }
      this.mCallbacksRunning = true;
      if (callbackType == 4) {
        long jitterNanos = now - frameTimeNanos;
        Trace.traceCounter(8L, "jitterNanos", (int) jitterNanos);
        if (jitterNanos >= 2 * frameIntervalNanos) {
          long lastFrameOffset = (jitterNanos % frameIntervalNanos) + frameIntervalNanos;
          long frameTimeNanos2 = now - lastFrameOffset;
          this.mLastFrameTimeNanos = frameTimeNanos2;
          this.mFrameData.update(frameTimeNanos2, this.mDisplayEventReceiver, jitterNanos);
        }
      }
      try {
        Trace.traceBegin(8L, CALLBACK_TRACE_TITLES[callbackType]);
        for (CallbackRecord c = callbacks; c != null; c = c.next) {
          long begin = this.mEnabledDebugCallback ? SystemClock.elapsedRealtime() : 0L;
          c.run(this.mFrameData);
          if (this.mEnabledDebugCallback && this.mDebugCallbackConsumer != null && c.log != null) {
            long dur = SystemClock.elapsedRealtime() - begin;
            if (dur >= this.mDebugDispatchThresholdMs) {
              this.mDebugCallbackConsumer.accept(
                  "RunCallback: type="
                      + callbackType
                      + ", action="
                      + c.action
                      + ", token="
                      + c.token
                      + ", latencyMillis="
                      + (SystemClock.uptimeMillis() - c.dueTime)
                      + ", dur="
                      + dur
                      + "ms\n",
                  c.log);
            }
          }
        }
        synchronized (this.mLock) {
          this.mCallbacksRunning = false;
          do {
            CallbackRecord next = callbacks.next;
            recycleCallbackLocked(callbacks);
            callbacks = next;
          } while (callbacks != null);
        }
        Trace.traceEnd(8L);
      } catch (Throwable th) {
        synchronized (this.mLock) {
          this.mCallbacksRunning = false;
          do {
            CallbackRecord next2 = callbacks.next;
            recycleCallbackLocked(callbacks);
            callbacks = next2;
          } while (callbacks != null);
          Trace.traceEnd(8L);
          throw th;
        }
      }
    }
  }

  void doScheduleVsync() {
    synchronized (this.mLock) {
      if (this.mFrameScheduled) {
        scheduleVsyncLocked();
      }
    }
  }

  void doScheduleCallback(int callbackType) {
    synchronized (this.mLock) {
      if (!this.mFrameScheduled) {
        long now = SystemClock.uptimeMillis();
        if (this.mCallbackQueues[callbackType].hasDueCallbacksLocked(now)) {
          scheduleFrameLocked(now);
        }
      }
    }
  }

  void setActivityState(boolean isFg) {
    synchronized (this.mLock) {
      this.mIsFg = isFg;
      this.mBgWaitingDelaySetting = false;
    }
  }

  public void scheduleVsyncSS(int solutionType) {
    synchronized (this.mLock) {
      this.mDisplayEventReceiver.onVsyncSS(solutionType);
    }
  }

  private void scheduleVsyncLocked() {
    try {
      Trace.traceBegin(8L, "Choreographer#scheduleVsyncLocked");
      this.mDisplayEventReceiver.scheduleVsync();
    } finally {
      Trace.traceEnd(8L);
    }
  }

  private boolean isRunningOnLooperThreadLocked() {
    return Looper.myLooper() == this.mLooper;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public CallbackRecord obtainCallbackLocked(
      long dueTime, Object action, Object token, String log) {
    CallbackRecord callback = this.mCallbackPool;
    if (callback == null) {
      callback = new CallbackRecord();
    } else {
      this.mCallbackPool = callback.next;
      callback.next = null;
    }
    callback.dueTime = dueTime;
    callback.action = action;
    callback.token = token;
    callback.log = log;
    return callback;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void recycleCallbackLocked(CallbackRecord callback) {
    callback.action = null;
    callback.token = null;
    callback.next = this.mCallbackPool;
    callback.log = null;
    this.mCallbackPool = callback;
  }

  public void setEnabledDebugCallback(
      boolean enabled,
      BiConsumer<String, String> consumer,
      int callStackCnt,
      int dispatchThresholdMs) {
    this.mEnabledDebugCallback = enabled;
    this.mDebugCallbackConsumer = consumer;
    this.mDebugCallStackCnt = callStackCnt;
    this.mDebugDispatchThresholdMs = dispatchThresholdMs;
  }

  public static class FrameTimeline {
    private long mVsyncId = -1;
    private long mExpectedPresentationTimeNanos = -1;
    private long mDeadlineNanos = -1;
    private boolean mInCallback = false;

    FrameTimeline() {}

    void setInCallback(boolean inCallback) {
      this.mInCallback = inCallback;
    }

    private void checkInCallback() {
      if (!this.mInCallback) {
        throw new IllegalStateException("FrameTimeline is not valid outside of the vsync callback");
      }
    }

    void update(long vsyncId, long expectedPresentationTimeNanos, long deadlineNanos) {
      this.mVsyncId = vsyncId;
      this.mExpectedPresentationTimeNanos = expectedPresentationTimeNanos;
      this.mDeadlineNanos = deadlineNanos;
    }

    public long getVsyncId() {
      checkInCallback();
      return this.mVsyncId;
    }

    public long getExpectedPresentationTimeNanos() {
      checkInCallback();
      return this.mExpectedPresentationTimeNanos;
    }

    public long getDeadlineNanos() {
      checkInCallback();
      return this.mDeadlineNanos;
    }
  }

  public static class FrameData {
    private long mFrameTimeNanos;
    private FrameTimeline[] mFrameTimelines;
    private boolean mInCallback = false;
    private int mPreferredFrameTimelineIndex;

    FrameData() {
      allocateFrameTimelines(7);
    }

    public long getFrameTimeNanos() {
      checkInCallback();
      return this.mFrameTimeNanos;
    }

    public FrameTimeline[] getFrameTimelines() {
      checkInCallback();
      return this.mFrameTimelines;
    }

    public FrameTimeline getPreferredFrameTimeline() {
      checkInCallback();
      return this.mFrameTimelines[this.mPreferredFrameTimelineIndex];
    }

    void setInCallback(boolean inCallback) {
      this.mInCallback = inCallback;
      int i = 0;
      while (true) {
        FrameTimeline[] frameTimelineArr = this.mFrameTimelines;
        if (i < frameTimelineArr.length) {
          frameTimelineArr[i].setInCallback(inCallback);
          i++;
        } else {
          return;
        }
      }
    }

    private void checkInCallback() {
      if (!this.mInCallback) {
        throw new IllegalStateException("FrameData is not valid outside of the vsync callback");
      }
    }

    private void allocateFrameTimelines(int length) {
      this.mFrameTimelines = new FrameTimeline[length];
      int i = 0;
      while (true) {
        FrameTimeline[] frameTimelineArr = this.mFrameTimelines;
        if (i < frameTimelineArr.length) {
          frameTimelineArr[i] = new FrameTimeline();
          i++;
        } else {
          return;
        }
      }
    }

    FrameTimeline update(long frameTimeNanos, DisplayEventReceiver.VsyncEventData vsyncEventData) {
      if (vsyncEventData.frameTimelinesLength == 0) {
        throw new IllegalArgumentException("Vsync event timelines length must be greater than 0");
      }
      if (this.mFrameTimelines.length != vsyncEventData.frameTimelinesLength) {
        allocateFrameTimelines(vsyncEventData.frameTimelinesLength);
      }
      this.mFrameTimeNanos = frameTimeNanos;
      this.mPreferredFrameTimelineIndex = vsyncEventData.preferredFrameTimelineIndex;
      int i = 0;
      while (true) {
        FrameTimeline[] frameTimelineArr = this.mFrameTimelines;
        if (i < frameTimelineArr.length) {
          DisplayEventReceiver.VsyncEventData.FrameTimeline frameTimeline =
              vsyncEventData.frameTimelines[i];
          this.mFrameTimelines[i].update(
              frameTimeline.vsyncId,
              frameTimeline.expectedPresentationTime,
              frameTimeline.deadline);
          i++;
        } else {
          int i2 = this.mPreferredFrameTimelineIndex;
          return frameTimelineArr[i2];
        }
      }
    }

    FrameTimeline update(
        long frameTimeNanos, DisplayEventReceiver displayEventReceiver, long jitterNanos) {
      int newPreferredIndex = 0;
      long minimumDeadline =
          this.mFrameTimelines[this.mPreferredFrameTimelineIndex].mDeadlineNanos + jitterNanos;
      while (true) {
        FrameTimeline[] frameTimelineArr = this.mFrameTimelines;
        if (newPreferredIndex >= frameTimelineArr.length - 1
            || frameTimelineArr[newPreferredIndex].mDeadlineNanos >= minimumDeadline) {
          break;
        }
        newPreferredIndex++;
      }
      long newPreferredDeadline = this.mFrameTimelines[newPreferredIndex].mDeadlineNanos;
      if (newPreferredDeadline < minimumDeadline) {
        DisplayEventReceiver.VsyncEventData latestVsyncEventData =
            displayEventReceiver.getLatestVsyncEventData();
        update(frameTimeNanos, latestVsyncEventData);
      } else {
        update(frameTimeNanos, newPreferredIndex);
      }
      return this.mFrameTimelines[this.mPreferredFrameTimelineIndex];
    }

    void update(long frameTimeNanos, int newPreferredFrameTimelineIndex) {
      this.mFrameTimeNanos = frameTimeNanos;
      this.mPreferredFrameTimelineIndex = newPreferredFrameTimelineIndex;
    }
  }

  private final class FrameHandler extends Handler {
    public FrameHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case 0:
          Choreographer.this.doFrame(
              System.nanoTime(), 0, new DisplayEventReceiver.VsyncEventData());
          break;
        case 1:
          Choreographer.this.doScheduleVsync();
          break;
        case 2:
          Choreographer.this.doScheduleCallback(msg.arg1);
          break;
        case 3:
          Choreographer.this.setActivityState(msg.arg1 == 0);
          break;
      }
    }
  }

  private final class FrameDisplayEventReceiver extends DisplayEventReceiver implements Runnable {
    private int mFrame;
    private boolean mHavePendingVsync;
    private final DisplayEventReceiver.VsyncEventData mLastVsyncEventData;
    private long mTimestampNanos;

    FrameDisplayEventReceiver(Looper looper, int vsyncSource, long layerHandle) {
      super(looper, vsyncSource, 0, layerHandle);
      this.mLastVsyncEventData = new DisplayEventReceiver.VsyncEventData();
    }

    public void onVsyncSS(int solutionType) {
      this.mTimestampNanos = System.nanoTime();
      this.mFrame = 0;
      Message msg = Message.obtain(Choreographer.this.mHandler, this);
      msg.setAsynchronous(true);
      DisplayEventReceiver.VsyncEventData latestVsyncEventData = getLatestVsyncEventData();
      if (latestVsyncEventData != null && latestVsyncEventData.frameTimelinesLength > 0) {
        this.mLastVsyncEventData.preferredFrameTimelineIndex =
            latestVsyncEventData.preferredFrameTimelineIndex;
        this.mLastVsyncEventData.frameTimelinesLength = latestVsyncEventData.frameTimelinesLength;
        this.mLastVsyncEventData.frameInterval = latestVsyncEventData.frameInterval;
        for (int i = 0; i < latestVsyncEventData.frameTimelinesLength; i++) {
          this.mLastVsyncEventData.frameTimelines[i].copyFrom(
              latestVsyncEventData.frameTimelines[i]);
        }
        switch (solutionType) {
          case 0:
            Choreographer.this.mHandler.sendMessage(msg);
            break;
          case 1:
          case 2:
          case 3:
            Choreographer.this.mHandler.sendMessageAtFrontOfQueue(msg);
            break;
        }
        return;
      }
      Log.m102w(Choreographer.IDS_TAG, "Could not get FrameData");
    }

    private void scheduleSTB() {
      try {
        if (Trace.isTagEnabled(8L)) {
          Trace.traceBegin(8L, "STB invocation");
        }
        Choreographer choreographer =
            Looper.myLooper() != null ? Choreographer.getInstance() : null;
        if (choreographer != null) {
          Choreographer.sScrollDf.set(true);
          choreographer.scheduleVsyncSS(3);
          Choreographer.this.mSTBCount++;
          Choreographer.this.mFramesSinceSTB = 0L;
          Choreographer.sScrollDf.set(false);
        }
      } finally {
        Trace.traceEnd(8L);
      }
    }

    private boolean isSTBNeeded() {
      return (Choreographer.this.mFlingSTBFlag[0] || Choreographer.this.mFlingSTBFlag[1])
          && System.nanoTime() - Choreographer.this.mFlingStartTime < 3000000000L;
    }

    @Override // android.view.DisplayEventReceiver
    public void onVsync(
        long timestampNanos,
        long physicalDisplayId,
        int frame,
        DisplayEventReceiver.VsyncEventData vsyncEventData) {
      try {
        if (Trace.isTagEnabled(8L)) {
          Trace.traceBegin(
              8L, "Choreographer#onVsync " + vsyncEventData.preferredFrameTimeline().vsyncId);
        }
        long now = System.nanoTime();
        if (timestampNanos > now) {
          Log.m102w(
              Choreographer.TAG,
              "Frame time is "
                  + ((timestampNanos - now) * 1.0E-6f)
                  + " ms in the future!  Check that graphics HAL is generating vsync timestamps"
                  + " using the correct timebase.");
          timestampNanos = now;
        }
        if (this.mHavePendingVsync) {
          Log.m102w(
              Choreographer.TAG,
              "Already have a pending vsync event.  There should only be one at a time.");
        } else {
          this.mHavePendingVsync = true;
        }
        this.mTimestampNanos = timestampNanos;
        this.mFrame = frame;
        this.mLastVsyncEventData.copyFrom(vsyncEventData);
        Message msg = Message.obtain(Choreographer.this.mHandler, this);
        msg.setAsynchronous(true);
        Choreographer.this.mHandler.sendMessageAtTime(msg, timestampNanos / 1000000);
      } finally {
        Trace.traceEnd(8L);
      }
    }

    @Override // java.lang.Runnable
    public void run() {
      this.mHavePendingVsync = false;
      System.nanoTime();
      Choreographer.this.doFrame(this.mTimestampNanos, this.mFrame, this.mLastVsyncEventData);
    }
  }

  private static final class CallbackRecord {
    public Object action;
    public long dueTime;
    public String log;
    public CallbackRecord next;
    public Object token;

    private CallbackRecord() {}

    public void run(long frameTimeNanos) {
      if (this.token == Choreographer.FRAME_CALLBACK_TOKEN) {
        ((FrameCallback) this.action).doFrame(frameTimeNanos);
      } else {
        ((Runnable) this.action).run();
      }
    }

    void run(FrameData frameData) {
      frameData.setInCallback(true);
      if (this.token == Choreographer.VSYNC_CALLBACK_TOKEN) {
        ((VsyncCallback) this.action).onVsync(frameData);
      } else {
        run(frameData.getFrameTimeNanos());
      }
      frameData.setInCallback(false);
    }
  }

  private final class CallbackQueue {
    private CallbackRecord mHead;

    private CallbackQueue() {}

    public boolean hasDueCallbacksLocked(long now) {
      CallbackRecord callbackRecord = this.mHead;
      return callbackRecord != null && callbackRecord.dueTime <= now;
    }

    public CallbackRecord extractDueCallbacksLocked(long now) {
      CallbackRecord callbacks = this.mHead;
      if (callbacks == null || callbacks.dueTime > now) {
        return null;
      }
      CallbackRecord last = callbacks;
      CallbackRecord next = last.next;
      while (true) {
        if (next == null) {
          break;
        }
        if (next.dueTime > now) {
          last.next = null;
          break;
        }
        last = next;
        next = next.next;
      }
      this.mHead = next;
      return callbacks;
    }

    public void addCallbackLocked(long dueTime, Object action, Object token, String log) {
      CallbackRecord callback =
          Choreographer.this.obtainCallbackLocked(dueTime, action, token, log);
      CallbackRecord entry = this.mHead;
      if (entry == null) {
        this.mHead = callback;
        return;
      }
      if (dueTime < entry.dueTime) {
        callback.next = entry;
        this.mHead = callback;
        return;
      }
      while (true) {
        if (entry.next == null) {
          break;
        }
        if (dueTime < entry.next.dueTime) {
          callback.next = entry.next;
          break;
        }
        entry = entry.next;
      }
      entry.next = callback;
    }

    public void removeCallbacksLocked(Object action, Object token) {
      CallbackRecord predecessor = null;
      CallbackRecord callback = this.mHead;
      while (callback != null) {
        CallbackRecord next = callback.next;
        if ((action == null || callback.action == action)
            && (token == null || callback.token == token)) {
          if (predecessor != null) {
            predecessor.next = next;
          } else {
            this.mHead = next;
          }
          Choreographer.this.recycleCallbackLocked(callback);
        } else {
          predecessor = callback;
        }
        callback = next;
      }
    }
  }
}
