package com.android.internal.util;

import android.Manifest;
import android.app.ActivityThread;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.location.GpsNetInitiatedHandler;
import com.android.internal.os.BackgroundThread;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public class LatencyTracker {
  public static final int ACTION_CHECK_CREDENTIAL = 3;
  public static final int ACTION_CHECK_CREDENTIAL_UNLOCKED = 4;
  public static final int ACTION_EXPAND_PANEL = 0;
  public static final int ACTION_FACE_WAKE_AND_UNLOCK = 7;
  public static final int ACTION_FINGERPRINT_WAKE_AND_UNLOCK = 2;
  public static final int ACTION_FOLD_TO_AOD = 18;
  public static final int ACTION_LOAD_SHARE_SHEET = 16;
  public static final int ACTION_LOCKSCREEN_UNLOCK = 11;
  public static final int ACTION_REQUEST_IME_HIDDEN = 21;
  public static final int ACTION_REQUEST_IME_SHOWN = 20;
  public static final int ACTION_ROTATE_SCREEN = 6;
  public static final int ACTION_ROTATE_SCREEN_CAMERA_CHECK = 9;
  public static final int ACTION_ROTATE_SCREEN_SENSOR = 10;
  public static final int ACTION_SHOW_BACK_ARROW = 15;
  public static final int ACTION_SHOW_SELECTION_TOOLBAR = 17;
  public static final int ACTION_SHOW_VOICE_INTERACTION = 19;
  public static final int ACTION_SMARTSPACE_DOORBELL = 22;
  public static final int ACTION_START_RECENTS_ANIMATION = 8;
  public static final int ACTION_SWITCH_DISPLAY_UNFOLD = 13;
  public static final int ACTION_TOGGLE_RECENTS = 1;
  public static final int ACTION_TURN_ON_SCREEN = 5;
  public static final int ACTION_UDFPS_ILLUMINATE = 14;
  public static final int ACTION_USER_SWITCH = 12;
  private static final boolean DEBUG = false;
  private static final int DEFAULT_SAMPLING_INTERVAL = 5;
  public static final String SETTINGS_ENABLED_KEY = "enabled";
  private static final String SETTINGS_SAMPLING_INTERVAL_KEY = "sampling_interval";
  private static final String TAG = "LatencyTracker";
  private static final boolean DEFAULT_ENABLED = Build.IS_DEBUGGABLE;
  private static final int[] ACTIONS_ALL = {
    0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22
  };
  public static final int[] STATSD_ACTION = {
    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23
  };
  private final Object mLock = new Object();
  private final SparseArray<Session> mSessions = new SparseArray<>();
  private final SparseArray<ActionProperties> mActionPropertiesMap = new SparseArray<>();
  private final DeviceConfig.OnPropertiesChangedListener mOnPropertiesChangedListener =
      new DeviceConfig
          .OnPropertiesChangedListener() { // from class:
                                           // com.android.internal.util.LatencyTracker$$ExternalSyntheticLambda2
        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
          LatencyTracker.this.updateProperties(properties);
        }
      };
  private boolean mEnabled = DEFAULT_ENABLED;

  @Retention(RetentionPolicy.SOURCE)
  public @interface Action {}

  private static final class SLatencyTrackerHolder {
    private static final LatencyTracker sLatencyTracker;

    private SLatencyTrackerHolder() {}

    static {
      LatencyTracker latencyTracker = new LatencyTracker();
      sLatencyTracker = latencyTracker;
      latencyTracker.startListeningForLatencyTrackerConfigChanges();
    }
  }

  public static LatencyTracker getInstance(Context context) {
    return SLatencyTrackerHolder.sLatencyTracker;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void updateProperties(DeviceConfig.Properties properties) {
    synchronized (this.mLock) {
      int samplingInterval = properties.getInt("sampling_interval", 5);
      this.mEnabled = properties.getBoolean("enabled", DEFAULT_ENABLED);
      for (int action : ACTIONS_ALL) {
        String actionName = getNameOfAction(STATSD_ACTION[action]).toLowerCase(Locale.ROOT);
        int legacyActionTraceThreshold =
            properties.getInt(actionName.toUpperCase(Locale.ROOT) + "", -1);
        this.mActionPropertiesMap.put(
            action,
            new ActionProperties(
                action,
                properties.getBoolean(actionName + "_enable", this.mEnabled),
                properties.getInt(actionName + "_sample_interval", samplingInterval),
                properties.getInt(actionName + "_trace_threshold", legacyActionTraceThreshold)));
      }
      onDeviceConfigPropertiesUpdated(this.mActionPropertiesMap);
    }
  }

  public void startListeningForLatencyTrackerConfigChanges() {
    final Context context = ActivityThread.currentApplication();
    if (context == null
        || context.checkCallingOrSelfPermission(Manifest.permission.READ_DEVICE_CONFIG) != 0) {
      return;
    }
    BackgroundThread.getHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.internal.util.LatencyTracker$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                LatencyTracker.this.lambda$startListeningForLatencyTrackerConfigChanges$0(context);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startListeningForLatencyTrackerConfigChanges$0(
      Context context) {
    try {
      updateProperties(DeviceConfig.getProperties("latency_tracker", new String[0]));
      DeviceConfig.addOnPropertiesChangedListener(
          "latency_tracker", BackgroundThread.getExecutor(), this.mOnPropertiesChangedListener);
    } catch (SecurityException e) {
      Log.m94d(
          TAG,
          "Can't get properties: READ_DEVICE_CONFIG granted="
              + context.checkCallingOrSelfPermission(Manifest.permission.READ_DEVICE_CONFIG)
              + ", package="
              + context.getPackageName());
    }
  }

  public void stopListeningForLatencyTrackerConfigChanges() {
    DeviceConfig.removeOnPropertiesChangedListener(this.mOnPropertiesChangedListener);
  }

  public static String getNameOfAction(int atomsProtoAction) {
    switch (atomsProtoAction) {
      case 0:
        return "UNKNOWN";
      case 1:
        return "ACTION_EXPAND_PANEL";
      case 2:
        return "ACTION_TOGGLE_RECENTS";
      case 3:
        return "ACTION_FINGERPRINT_WAKE_AND_UNLOCK";
      case 4:
        return "ACTION_CHECK_CREDENTIAL";
      case 5:
        return "ACTION_CHECK_CREDENTIAL_UNLOCKED";
      case 6:
        return "ACTION_TURN_ON_SCREEN";
      case 7:
        return "ACTION_ROTATE_SCREEN";
      case 8:
        return "ACTION_FACE_WAKE_AND_UNLOCK";
      case 9:
        return "ACTION_START_RECENTS_ANIMATION";
      case 10:
        return "ACTION_ROTATE_SCREEN_CAMERA_CHECK";
      case 11:
        return "ACTION_ROTATE_SCREEN_SENSOR";
      case 12:
        return "ACTION_LOCKSCREEN_UNLOCK";
      case 13:
        return "ACTION_USER_SWITCH";
      case 14:
        return "ACTION_SWITCH_DISPLAY_UNFOLD";
      case 15:
        return "ACTION_UDFPS_ILLUMINATE";
      case 16:
        return "ACTION_SHOW_BACK_ARROW";
      case 17:
        return "ACTION_LOAD_SHARE_SHEET";
      case 18:
        return "ACTION_SHOW_SELECTION_TOOLBAR";
      case 19:
        return "ACTION_FOLD_TO_AOD";
      case 20:
        return "ACTION_SHOW_VOICE_INTERACTION";
      case 21:
        return "ACTION_REQUEST_IME_SHOWN";
      case 22:
        return "ACTION_REQUEST_IME_HIDDEN";
      case 23:
        return "ACTION_SMARTSPACE_DOORBELL";
      default:
        throw new IllegalArgumentException("Invalid action");
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static String getTraceNameOfAction(int action, String tag) {
    if (TextUtils.isEmpty(tag)) {
      return "L<" + getNameOfAction(STATSD_ACTION[action]) + ">";
    }
    return "L<" + getNameOfAction(STATSD_ACTION[action]) + "::" + tag + ">";
  }

  private static String getTraceTriggerNameForAction(int action) {
    return "com.android.telemetry.latency-tracker-" + getNameOfAction(STATSD_ACTION[action]);
  }

  @Deprecated
  public static boolean isEnabled(Context ctx) {
    return getInstance(ctx).isEnabled();
  }

  @Deprecated
  public boolean isEnabled() {
    boolean z;
    synchronized (this.mLock) {
      z = this.mEnabled;
    }
    return z;
  }

  public static boolean isEnabled(Context ctx, int action) {
    return getInstance(ctx).isEnabled(action);
  }

  public boolean isEnabled(int action) {
    synchronized (this.mLock) {
      ActionProperties actionProperties = this.mActionPropertiesMap.get(action);
      if (actionProperties == null) {
        return false;
      }
      return actionProperties.isEnabled();
    }
  }

  public void onActionStart(int action) {
    onActionStart(action, null);
  }

  public void onActionStart(final int action, String tag) {
    synchronized (this.mLock) {
      if (isEnabled(action)) {
        if (this.mSessions.get(action) != null) {
          return;
        }
        Session session = new Session(action, tag);
        session.begin(
            new Runnable() { // from class:
                             // com.android.internal.util.LatencyTracker$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                LatencyTracker.this.lambda$onActionStart$1(action);
              }
            });
        this.mSessions.put(action, session);
      }
    }
  }

  public void onActionEnd(int action) {
    synchronized (this.mLock) {
      if (isEnabled(action)) {
        Session session = this.mSessions.get(action);
        if (session == null) {
          return;
        }
        session.end();
        this.mSessions.delete(action);
        logAction(action, session.duration());
      }
    }
  }

  /* renamed from: onActionCancel, reason: merged with bridge method [inline-methods] */
  public void lambda$onActionStart$1(int action) {
    synchronized (this.mLock) {
      Session session = this.mSessions.get(action);
      if (session == null) {
        return;
      }
      session.cancel();
      this.mSessions.delete(action);
    }
  }

  public long getActiveActionStartTime(int action) {
    synchronized (this.mLock) {
      if (!this.mSessions.contains(action)) {
        return -1L;
      }
      return this.mSessions.get(action).mStartRtc;
    }
  }

  public void logAction(int action, int duration) {
    synchronized (this.mLock) {
      if (isEnabled(action)) {
        ActionProperties actionProperties = this.mActionPropertiesMap.get(action);
        if (actionProperties == null) {
          return;
        }
        int nextRandNum =
            ThreadLocalRandom.current().nextInt(actionProperties.getSamplingInterval());
        boolean shouldSample = nextRandNum == 0;
        int traceThreshold = actionProperties.getTraceThreshold();
        boolean shouldTriggerPerfettoTrace = traceThreshold > 0 && duration >= traceThreshold;
        EventLog.writeEvent(36070, Integer.valueOf(action), Integer.valueOf(duration));
        if (shouldTriggerPerfettoTrace) {
          onTriggerPerfetto(getTraceTriggerNameForAction(action));
        }
        if (shouldSample) {
          onLogToFrameworkStats(
              new FrameworkStatsLogEvent(action, 306, STATSD_ACTION[action], duration));
        }
      }
    }
  }

  static class Session {
    private final int mAction;
    private final String mName;
    private final String mTag;
    private Runnable mTimeoutRunnable;
    private long mStartRtc = -1;
    private long mEndRtc = -1;

    Session(int action, String tag) {
      String str;
      this.mAction = action;
      this.mTag = tag;
      if (TextUtils.isEmpty(tag)) {
        str = LatencyTracker.getNameOfAction(LatencyTracker.STATSD_ACTION[action]);
      } else {
        str = LatencyTracker.getNameOfAction(LatencyTracker.STATSD_ACTION[action]) + "::" + tag;
      }
      this.mName = str;
    }

    String name() {
      return this.mName;
    }

    String traceName() {
      return LatencyTracker.getTraceNameOfAction(this.mAction, this.mTag);
    }

    void begin(final Runnable timeoutAction) {
      this.mStartRtc = SystemClock.elapsedRealtime();
      Trace.asyncTraceForTrackBegin(4096L, traceName(), traceName(), 0);
      this.mTimeoutRunnable =
          new Runnable() { // from class:
                           // com.android.internal.util.LatencyTracker$Session$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              LatencyTracker.Session.this.lambda$begin$0(timeoutAction);
            }
          };
      BackgroundThread.getHandler()
          .postDelayed(this.mTimeoutRunnable, TimeUnit.SECONDS.toMillis(15L));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$begin$0(Runnable timeoutAction) {
      Trace.instantForTrack(4096L, traceName(), GpsNetInitiatedHandler.NI_INTENT_KEY_TIMEOUT);
      timeoutAction.run();
    }

    void end() {
      this.mEndRtc = SystemClock.elapsedRealtime();
      Trace.asyncTraceForTrackEnd(4096L, traceName(), 0);
      BackgroundThread.getHandler().removeCallbacks(this.mTimeoutRunnable);
      this.mTimeoutRunnable = null;
    }

    void cancel() {
      Trace.instantForTrack(4096L, traceName(), "cancel");
      Trace.asyncTraceForTrackEnd(4096L, traceName(), 0);
      BackgroundThread.getHandler().removeCallbacks(this.mTimeoutRunnable);
      this.mTimeoutRunnable = null;
    }

    int duration() {
      return (int) (this.mEndRtc - this.mStartRtc);
    }
  }

  public static class ActionProperties {
    static final String ENABLE_SUFFIX = "_enable";
    static final String LEGACY_TRACE_THRESHOLD_SUFFIX = "";
    static final String SAMPLE_INTERVAL_SUFFIX = "_sample_interval";
    static final String TRACE_THRESHOLD_SUFFIX = "_trace_threshold";
    private final int mAction;
    private final boolean mEnabled;
    private final int mSamplingInterval;
    private final int mTraceThreshold;

    public ActionProperties(int action, boolean enabled, int samplingInterval, int traceThreshold) {
      this.mAction = action;
      AnnotationValidations.validate(
          (Class<? extends Annotation>) Action.class, (Annotation) null, action);
      this.mEnabled = enabled;
      this.mSamplingInterval = samplingInterval;
      this.mTraceThreshold = traceThreshold;
    }

    public int getAction() {
      return this.mAction;
    }

    public boolean isEnabled() {
      return this.mEnabled;
    }

    public int getSamplingInterval() {
      return this.mSamplingInterval;
    }

    public int getTraceThreshold() {
      return this.mTraceThreshold;
    }

    public String toString() {
      return "ActionProperties{ mAction="
          + this.mAction
          + ", mEnabled="
          + this.mEnabled
          + ", mSamplingInterval="
          + this.mSamplingInterval
          + ", mTraceThreshold="
          + this.mTraceThreshold
          + "}";
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || !(o instanceof ActionProperties)) {
        return false;
      }
      ActionProperties that = (ActionProperties) o;
      if (this.mAction == that.mAction
          && this.mEnabled == that.mEnabled
          && this.mSamplingInterval == that.mSamplingInterval
          && this.mTraceThreshold == that.mTraceThreshold) {
        return true;
      }
      return false;
    }

    public int hashCode() {
      int _hash = (1 * 31) + this.mAction;
      return (((((_hash * 31) + Boolean.hashCode(this.mEnabled)) * 31) + this.mSamplingInterval)
              * 31)
          + this.mTraceThreshold;
    }
  }

  public void onDeviceConfigPropertiesUpdated(SparseArray<ActionProperties> actionProperties) {}

  public void onTriggerPerfetto(String triggerName) {
    PerfettoTrigger.trigger(triggerName);
  }

  public void onLogToFrameworkStats(FrameworkStatsLogEvent event) {
    FrameworkStatsLog.write(event.logCode, event.statsdAction, event.durationMillis);
  }

  public static class FrameworkStatsLogEvent {
    public final int action;
    public final int durationMillis;
    public final int logCode;
    public final int statsdAction;

    private FrameworkStatsLogEvent(int action, int logCode, int statsdAction, int durationMillis) {
      this.action = action;
      this.logCode = logCode;
      this.statsdAction = statsdAction;
      this.durationMillis = durationMillis;
    }

    public String toString() {
      return "FrameworkStatsLogEvent{ logCode="
          + this.logCode
          + ", statsdAction="
          + this.statsdAction
          + ", durationMillis="
          + this.durationMillis
          + "}";
    }
  }
}
