package com.android.internal.util;

import android.Manifest;
import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;
import android.media.MediaMetrics;
import android.os.Build;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.os.BackgroundThread;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class LatencyTracker {
    public static final int ACTION_BACK_SYSTEM_ANIMATION = 25;
    public static final int ACTION_CHECK_CREDENTIAL = 3;
    public static final int ACTION_CHECK_CREDENTIAL_UNLOCKED = 4;
    public static final int ACTION_DESKTOP_MODE_ENTER_APP_HANDLE_DRAG = 30;
    public static final int ACTION_DESKTOP_MODE_ENTER_APP_HANDLE_MENU = 31;
    public static final int ACTION_DESKTOP_MODE_EXIT_MODE = 32;
    public static final int ACTION_EXPAND_PANEL = 0;
    public static final int ACTION_FACE_WAKE_AND_UNLOCK = 7;
    public static final int ACTION_FINGERPRINT_WAKE_AND_UNLOCK = 2;
    public static final int ACTION_FOLD_TO_AOD = 18;
    public static final int ACTION_KEYGUARD_FACE_UNLOCK_TO_HOME = 28;
    public static final int ACTION_KEYGUARD_FPS_UNLOCK_TO_HOME = 24;
    public static final int ACTION_LOAD_SHARE_SHEET = 16;
    public static final int ACTION_LOCKSCREEN_UNLOCK = 11;
    public static final int ACTION_NOTIFICATIONS_HIDDEN_FOR_MEASURE = 26;
    public static final int ACTION_NOTIFICATIONS_HIDDEN_FOR_MEASURE_WITH_SHADE_OPEN = 27;
    public static final int ACTION_NOTIFICATION_BIG_PICTURE_LOADED = 23;
    public static final int ACTION_REQUEST_IME_HIDDEN = 21;
    public static final int ACTION_REQUEST_IME_SHOWN = 20;
    public static final int ACTION_ROTATE_SCREEN = 6;
    public static final int ACTION_ROTATE_SCREEN_CAMERA_CHECK = 9;
    public static final int ACTION_ROTATE_SCREEN_SENSOR = 10;
    public static final int ACTION_SHADE_WINDOW_DISPLAY_CHANGE = 29;
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
    private static final int[] ACTIONS_ALL = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32};
    public static final int[] STATSD_ACTION = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36};
    private final Object mLock = new Object();
    private final SparseArray<Session> mSessions = new SparseArray<>();
    private final SparseArray<ActionProperties> mActionPropertiesMap = new SparseArray<>();
    private final DeviceConfig.OnPropertiesChangedListener mOnPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.internal.util.LatencyTracker$$ExternalSyntheticLambda2
        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            this.f$0.updateProperties(properties);
        }
    };
    private boolean mEnabled = DEFAULT_ENABLED;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Action {
    }

    public void onDeviceConfigPropertiesUpdated(SparseArray<ActionProperties> sparseArray) {
    }

    private static final class SLatencyTrackerHolder {
        private static final LatencyTracker sLatencyTracker;

        private SLatencyTrackerHolder() {
        }

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
            int i = properties.getInt("sampling_interval", 5);
            boolean z = this.mEnabled;
            boolean z2 = properties.getBoolean("enabled", DEFAULT_ENABLED);
            this.mEnabled = z2;
            if (z != z2) {
                StringBuilder sb = new StringBuilder("Latency tracker ");
                sb.append(this.mEnabled ? "enabled" : "disabled");
                sb.append(MediaMetrics.SEPARATOR);
                Log.d(TAG, sb.toString());
            }
            for (int i2 : ACTIONS_ALL) {
                String lowerCase = getNameOfAction(STATSD_ACTION[i2]).toLowerCase(Locale.ROOT);
                this.mActionPropertiesMap.put(i2, new ActionProperties(i2, properties.getBoolean(lowerCase + "_enable", this.mEnabled), properties.getInt(lowerCase + "_sample_interval", i), properties.getInt(lowerCase + "_trace_threshold", properties.getInt(lowerCase.toUpperCase(Locale.ROOT) + "", -1))));
            }
            onDeviceConfigPropertiesUpdated(this.mActionPropertiesMap);
        }
    }

    public void startListeningForLatencyTrackerConfigChanges() {
        final Application applicationCurrentApplication = ActivityThread.currentApplication();
        if (applicationCurrentApplication == null) {
            Log.e(TAG, String.format("No application for package: %s. Latency Tracker Disabled", ActivityThread.currentPackageName()));
        } else {
            if (applicationCurrentApplication.checkCallingOrSelfPermission(Manifest.permission.READ_DEVICE_CONFIG) != 0) {
                return;
            }
            BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.internal.util.LatencyTracker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$startListeningForLatencyTrackerConfigChanges$0(applicationCurrentApplication);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startListeningForLatencyTrackerConfigChanges$0(Context context) {
        try {
            updateProperties(DeviceConfig.getProperties("latency_tracker", new String[0]));
            DeviceConfig.addOnPropertiesChangedListener("latency_tracker", BackgroundThread.getExecutor(), this.mOnPropertiesChangedListener);
        } catch (SecurityException unused) {
            Log.d(TAG, "Can't get properties: READ_DEVICE_CONFIG granted=" + context.checkCallingOrSelfPermission(Manifest.permission.READ_DEVICE_CONFIG) + ", package=" + context.getPackageName());
        }
    }

    public void stopListeningForLatencyTrackerConfigChanges() {
        DeviceConfig.removeOnPropertiesChangedListener(this.mOnPropertiesChangedListener);
    }

    public static String getNameOfAction(int i) {
        switch (i) {
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
            case 24:
            case 25:
            case 26:
            default:
                throw new IllegalArgumentException("Invalid action");
            case 27:
                return "ACTION_NOTIFICATION_BIG_PICTURE_LOADED";
            case 28:
                return "ACTION_KEYGUARD_FPS_UNLOCK_TO_HOME";
            case 29:
                return "ACTION_BACK_SYSTEM_ANIMATION";
            case 30:
                return "ACTION_NOTIFICATIONS_HIDDEN_FOR_MEASURE";
            case 31:
                return "ACTION_NOTIFICATIONS_HIDDEN_FOR_MEASURE_WITH_SHADE_OPEN";
            case 32:
                return "ACTION_KEYGUARD_FACE_UNLOCK_TO_HOME";
            case 33:
                return "ACTION_SHADE_WINDOW_DISPLAY_CHANGE";
            case 34:
                return "ACTION_DESKTOP_MODE_ENTER_APP_HANDLE_DRAG";
            case 35:
                return "ACTION_DESKTOP_MODE_ENTER_APP_HANDLE_MENU";
            case 36:
                return "ACTION_DESKTOP_MODE_EXIT_MODE";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getTraceNameOfAction(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            return "L<" + getNameOfAction(STATSD_ACTION[i]) + ">";
        }
        return "L<" + getNameOfAction(STATSD_ACTION[i]) + "::" + str + ">";
    }

    private static String getTraceTriggerNameForAction(int i) {
        return "com.android.telemetry.latency-tracker-" + getNameOfAction(STATSD_ACTION[i]);
    }

    @Deprecated
    public static boolean isEnabled(Context context) {
        return getInstance(context).isEnabled();
    }

    @Deprecated
    public boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mEnabled;
        }
        return z;
    }

    public static boolean isEnabled(Context context, int i) {
        return getInstance(context).isEnabled(i);
    }

    public boolean isEnabled(int i) {
        synchronized (this.mLock) {
            ActionProperties actionProperties = this.mActionPropertiesMap.get(i);
            if (actionProperties == null) {
                return false;
            }
            return actionProperties.isEnabled();
        }
    }

    public void onActionStart(int i) {
        onActionStart(i, null);
    }

    public void onActionStart(final int i, String str) {
        synchronized (this.mLock) {
            if (isEnabled(i)) {
                if (this.mSessions.get(i) != null) {
                    return;
                }
                Session session = new Session(i, str);
                session.begin(new Runnable() { // from class: com.android.internal.util.LatencyTracker$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onActionStart$1(i);
                    }
                });
                this.mSessions.put(i, session);
            }
        }
    }

    public void onActionEnd(int i) {
        synchronized (this.mLock) {
            if (isEnabled(i)) {
                Session session = this.mSessions.get(i);
                if (session == null) {
                    return;
                }
                session.end();
                this.mSessions.delete(i);
                logAction(i, session.duration());
            }
        }
    }

    /* renamed from: onActionCancel, reason: merged with bridge method [inline-methods] */
    public void lambda$onActionStart$1(int i) {
        synchronized (this.mLock) {
            Session session = this.mSessions.get(i);
            if (session == null) {
                return;
            }
            session.cancel();
            this.mSessions.delete(i);
        }
    }

    public long getActiveActionStartTime(int i) {
        synchronized (this.mLock) {
            if (!this.mSessions.contains(i)) {
                return -1L;
            }
            return this.mSessions.get(i).mStartRtc;
        }
    }

    public void logAction(int i, int i2) throws IOException {
        synchronized (this.mLock) {
            if (isEnabled(i)) {
                ActionProperties actionProperties = this.mActionPropertiesMap.get(i);
                if (actionProperties == null) {
                    return;
                }
                boolean z = ThreadLocalRandom.current().nextInt(actionProperties.getSamplingInterval()) == 0;
                int traceThreshold = actionProperties.getTraceThreshold();
                boolean z2 = traceThreshold > 0 && i2 >= traceThreshold;
                EventLog.writeEvent(36070, Integer.valueOf(i), Integer.valueOf(i2));
                if (z2) {
                    onTriggerPerfetto(getTraceTriggerNameForAction(i));
                }
                if (z) {
                    onLogToFrameworkStats(new FrameworkStatsLogEvent(i, 306, STATSD_ACTION[i], i2));
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

        Session(int i, String str) {
            String nameOfAction;
            this.mAction = i;
            this.mTag = str;
            if (TextUtils.isEmpty(str)) {
                nameOfAction = LatencyTracker.getNameOfAction(LatencyTracker.STATSD_ACTION[i]);
            } else {
                nameOfAction = LatencyTracker.getNameOfAction(LatencyTracker.STATSD_ACTION[i]) + "::" + str;
            }
            this.mName = nameOfAction;
        }

        String name() {
            return this.mName;
        }

        String traceName() {
            return LatencyTracker.getTraceNameOfAction(this.mAction, this.mTag);
        }

        void begin(final Runnable runnable) {
            this.mStartRtc = SystemClock.elapsedRealtime();
            Trace.asyncTraceForTrackBegin(4096L, traceName(), traceName(), 0);
            this.mTimeoutRunnable = new Runnable() { // from class: com.android.internal.util.LatencyTracker$Session$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$begin$0(runnable);
                }
            };
            BackgroundThread.getHandler().postDelayed(this.mTimeoutRunnable, TimeUnit.SECONDS.toMillis(15L));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$begin$0(Runnable runnable) {
            Trace.instantForTrack(4096L, traceName(), "timeout");
            runnable.run();
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

        public ActionProperties(int i, boolean z, int i2, int i3) {
            this.mAction = i;
            AnnotationValidations.validate((Class<? extends Annotation>) Action.class, (Annotation) null, i);
            this.mEnabled = z;
            this.mSamplingInterval = i2;
            this.mTraceThreshold = i3;
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
            return "ActionProperties{ mAction=" + this.mAction + ", mEnabled=" + this.mEnabled + ", mSamplingInterval=" + this.mSamplingInterval + ", mTraceThreshold=" + this.mTraceThreshold + "}";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof ActionProperties)) {
                return false;
            }
            ActionProperties actionProperties = (ActionProperties) obj;
            return this.mAction == actionProperties.mAction && this.mEnabled == actionProperties.mEnabled && this.mSamplingInterval == actionProperties.mSamplingInterval && this.mTraceThreshold == actionProperties.mTraceThreshold;
        }

        public int hashCode() {
            return ((((((this.mAction + 31) * 31) + Boolean.hashCode(this.mEnabled)) * 31) + this.mSamplingInterval) * 31) + this.mTraceThreshold;
        }
    }

    public void onTriggerPerfetto(String str) throws IOException {
        PerfettoTrigger.trigger(str);
    }

    public void onLogToFrameworkStats(FrameworkStatsLogEvent frameworkStatsLogEvent) {
        FrameworkStatsLog.write(frameworkStatsLogEvent.logCode, frameworkStatsLogEvent.statsdAction, frameworkStatsLogEvent.durationMillis);
    }

    public static class FrameworkStatsLogEvent {
        public final int action;
        public final int durationMillis;
        public final int logCode;
        public final int statsdAction;

        private FrameworkStatsLogEvent(int i, int i2, int i3, int i4) {
            this.action = i;
            this.logCode = i2;
            this.statsdAction = i3;
            this.durationMillis = i4;
        }

        public String toString() {
            return "FrameworkStatsLogEvent{ logCode=" + this.logCode + ", statsdAction=" + this.statsdAction + ", durationMillis=" + this.durationMillis + "}";
        }
    }
}
