package com.android.internal.jank;

import android.Manifest;
import android.app.ActivityThread;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.provider.DeviceConfig;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.View;
import com.android.internal.jank.FrameTracker;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.util.PerfettoTrigger;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class InteractionJankMonitor {

    @Deprecated
    public static final int CUJ_BIOMETRIC_PROMPT_TRANSITION = 56;

    @Deprecated
    public static final int CUJ_LAUNCHER_ALL_APPS_SEARCH_BACK = 95;

    @Deprecated
    public static final int CUJ_LAUNCHER_LAUNCH_APP_PAIR_FROM_TASKBAR = 92;

    @Deprecated
    public static final int CUJ_LAUNCHER_LAUNCH_APP_PAIR_FROM_WORKSPACE = 91;

    @Deprecated
    public static final int CUJ_LAUNCHER_SAVE_APP_PAIR = 93;

    @Deprecated
    public static final int CUJ_LAUNCHER_TASKBAR_ALL_APPS_CLOSE_BACK = 96;

    @Deprecated
    public static final int CUJ_LAUNCHER_TASKBAR_ALL_APPS_SEARCH_BACK = 97;

    @Deprecated
    public static final int CUJ_LAUNCHER_WIDGET_BOTTOM_SHEET_CLOSE_BACK = 100;

    @Deprecated
    public static final int CUJ_LAUNCHER_WIDGET_EDU_SHEET_CLOSE_BACK = 101;

    @Deprecated
    public static final int CUJ_LAUNCHER_WIDGET_PICKER_CLOSE_BACK = 98;

    @Deprecated
    public static final int CUJ_LAUNCHER_WIDGET_PICKER_SEARCH_BACK = 99;

    @Deprecated
    public static final int CUJ_LOCKSCREEN_CLOCK_MOVE_ANIMATION = 70;

    @Deprecated
    public static final int CUJ_LOCKSCREEN_OCCLUSION = 64;

    @Deprecated
    public static final int CUJ_LOCKSCREEN_PASSWORD_APPEAR = 17;

    @Deprecated
    public static final int CUJ_LOCKSCREEN_PASSWORD_DISAPPEAR = 20;

    @Deprecated
    public static final int CUJ_LOCKSCREEN_PATTERN_APPEAR = 18;

    @Deprecated
    public static final int CUJ_LOCKSCREEN_PATTERN_DISAPPEAR = 21;

    @Deprecated
    public static final int CUJ_LOCKSCREEN_PIN_APPEAR = 19;

    @Deprecated
    public static final int CUJ_LOCKSCREEN_PIN_DISAPPEAR = 22;

    @Deprecated
    public static final int CUJ_LOCKSCREEN_TRANSITION_FROM_AOD = 23;

    @Deprecated
    public static final int CUJ_LOCKSCREEN_TRANSITION_TO_AOD = 24;

    @Deprecated
    public static final int CUJ_LOCKSCREEN_UNLOCK_ANIMATION = 29;

    @Deprecated
    public static final int CUJ_NOTIFICATION_ADD = 14;

    @Deprecated
    public static final int CUJ_NOTIFICATION_APP_START = 16;

    @Deprecated
    public static final int CUJ_NOTIFICATION_HEADS_UP_APPEAR = 12;

    @Deprecated
    public static final int CUJ_NOTIFICATION_HEADS_UP_DISAPPEAR = 13;

    @Deprecated
    public static final int CUJ_NOTIFICATION_REMOVE = 15;

    @Deprecated
    public static final int CUJ_NOTIFICATION_SHADE_EXPAND_COLLAPSE = 0;

    @Deprecated
    public static final int CUJ_NOTIFICATION_SHADE_QS_EXPAND_COLLAPSE = 5;

    @Deprecated
    public static final int CUJ_NOTIFICATION_SHADE_QS_SCROLL_SWIPE = 6;

    @Deprecated
    public static final int CUJ_NOTIFICATION_SHADE_ROW_EXPAND = 3;

    @Deprecated
    public static final int CUJ_NOTIFICATION_SHADE_ROW_SWIPE = 4;

    @Deprecated
    public static final int CUJ_NOTIFICATION_SHADE_SCROLL_FLING = 2;

    @Deprecated
    public static final int CUJ_PIP_TRANSITION = 35;

    @Deprecated
    public static final int CUJ_PREDICTIVE_BACK_CROSS_ACTIVITY = 84;

    @Deprecated
    public static final int CUJ_PREDICTIVE_BACK_CROSS_TASK = 85;

    @Deprecated
    public static final int CUJ_PREDICTIVE_BACK_HOME = 86;

    @Deprecated
    public static final int CUJ_SCREEN_OFF = 40;

    @Deprecated
    public static final int CUJ_SCREEN_OFF_SHOW_AOD = 41;

    @Deprecated
    public static final int CUJ_SETTINGS_PAGE_SCROLL = 28;

    @Deprecated
    public static final int CUJ_SETTINGS_SLIDER = 53;

    @Deprecated
    public static final int CUJ_SETTINGS_TOGGLE = 57;

    @Deprecated
    public static final int CUJ_SHADE_APP_LAUNCH_FROM_HISTORY_BUTTON = 30;

    @Deprecated
    public static final int CUJ_SHADE_APP_LAUNCH_FROM_MEDIA_PLAYER = 31;

    @Deprecated
    public static final int CUJ_SHADE_APP_LAUNCH_FROM_QS_TILE = 32;

    @Deprecated
    public static final int CUJ_SHADE_APP_LAUNCH_FROM_SETTINGS_BUTTON = 33;

    @Deprecated
    public static final int CUJ_SHADE_CLEAR_ALL = 62;

    @Deprecated
    public static final int CUJ_SHADE_DIALOG_OPEN = 58;

    @Deprecated
    public static final int CUJ_SPLASHSCREEN_AVD = 38;

    @Deprecated
    public static final int CUJ_SPLASHSCREEN_EXIT_ANIM = 39;

    @Deprecated
    public static final int CUJ_SPLIT_SCREEN_DOUBLE_TAP_DIVIDER = 82;

    @Deprecated
    public static final int CUJ_SPLIT_SCREEN_RESIZE = 52;

    @Deprecated
    public static final int CUJ_STATUS_BAR_APP_LAUNCH_FROM_CALL_CHIP = 34;

    @Deprecated
    public static final int CUJ_SUW_LOADING_SCREEN_FOR_STATUS = 48;

    @Deprecated
    public static final int CUJ_SUW_LOADING_TO_NEXT_FLOW = 47;

    @Deprecated
    public static final int CUJ_SUW_LOADING_TO_SHOW_INFO_WITH_ACTIONS = 45;

    @Deprecated
    public static final int CUJ_SUW_SHOW_FUNCTION_SCREEN_WITH_ACTIONS = 46;

    @Deprecated
    public static final int CUJ_TAKE_SCREENSHOT = 54;

    @Deprecated
    public static final int CUJ_TASKBAR_COLLAPSE = 61;

    @Deprecated
    public static final int CUJ_TASKBAR_EXPAND = 60;

    @Deprecated
    public static final int CUJ_UNFOLD_ANIM = 44;

    @Deprecated
    public static final int CUJ_USER_DIALOG_OPEN = 59;

    @Deprecated
    public static final int CUJ_USER_SWITCH = 37;

    @Deprecated
    public static final int CUJ_VOLUME_CONTROL = 55;
    private static final boolean DEFAULT_DEBUG_OVERLAY_ENABLED = false;
    private static final int DEFAULT_SAMPLING_INTERVAL = 1;
    private static final int DEFAULT_TRACE_THRESHOLD_FRAME_TIME_MILLIS = 64;
    private static final int DEFAULT_TRACE_THRESHOLD_MISSED_FRAMES = 3;
    static final long EXECUTOR_TASK_TIMEOUT = 500;
    private static final int MAX_LENGTH_SESSION_NAME = 100;
    private static final String SETTINGS_DEBUG_OVERLAY_ENABLED_KEY = "debug_overlay_enabled";
    private static final String SETTINGS_ENABLED_KEY = "enabled";
    private static final String SETTINGS_SAMPLING_INTERVAL_KEY = "sampling_interval";
    private static final String SETTINGS_THRESHOLD_FRAME_TIME_MILLIS_KEY = "trace_threshold_frame_time_millis";
    private static final String SETTINGS_THRESHOLD_MISSED_FRAMES_KEY = "trace_threshold_missed_frames";
    private InteractionMonitorDebugOverlay mDebugOverlay;
    private final DisplayResolutionTracker mDisplayResolutionTracker;
    private final Handler mWorker;
    private static final String TAG = InteractionJankMonitor.class.getSimpleName();
    private static final String ACTION_PREFIX = InteractionJankMonitor.class.getCanonicalName();
    private static final String DEFAULT_WORKER_NAME = TAG + "-Worker";
    private static final long DEFAULT_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(2);
    private static final boolean DEFAULT_ENABLED = Build.IS_DEBUGGABLE;
    public static final String ACTION_SESSION_END = ACTION_PREFIX + ".ACTION_SESSION_END";
    public static final String ACTION_SESSION_CANCEL = ACTION_PREFIX + ".ACTION_SESSION_CANCEL";
    private final SparseArray<RunningTracker> mRunningTrackers = new SparseArray<>();
    private final Object mLock = new Object();
    private int mDebugBgColor = Color.CYAN;
    private double mDebugYOffset = 0.1d;
    private volatile boolean mEnabled = DEFAULT_ENABLED;
    private int mSamplingInterval = 1;
    private int mTraceThresholdMissedFrames = 3;
    private int mTraceThresholdFrameTimeMillis = 64;

    /* JADX INFO: Access modifiers changed from: private */
    @FunctionalInterface
    interface TimeFunction {
        void invoke(long j, long j2, long j3);
    }

    private static class InstanceHolder {
        public static final InteractionJankMonitor INSTANCE = new InteractionJankMonitor(new HandlerThread(InteractionJankMonitor.DEFAULT_WORKER_NAME));

        private InstanceHolder() {
        }
    }

    public static InteractionJankMonitor getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public InteractionJankMonitor(HandlerThread worker) {
        worker.start();
        this.mWorker = worker.getThreadHandler();
        this.mDisplayResolutionTracker = new DisplayResolutionTracker(this.mWorker);
        final Context context = ActivityThread.currentApplication();
        if (context == null || context.checkCallingOrSelfPermission(Manifest.permission.READ_DEVICE_CONFIG) != 0) {
            Log.w(TAG, "Initializing without READ_DEVICE_CONFIG permission. enabled=" + this.mEnabled + ", interval=" + this.mSamplingInterval + ", missedFrameThreshold=" + this.mTraceThresholdMissedFrames + ", frameTimeThreshold=" + this.mTraceThresholdFrameTimeMillis + ", package=" + (context == null ? "null" : context.getPackageName()));
        } else {
            this.mWorker.post(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    InteractionJankMonitor.this.lambda$new$0(context);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Context context) {
        try {
            updateProperties(DeviceConfig.getProperties("interaction_jank_monitor", new String[0]));
            DeviceConfig.addOnPropertiesChangedListener("interaction_jank_monitor", new HandlerExecutor(this.mWorker), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda0
                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                    InteractionJankMonitor.this.updateProperties(properties);
                }
            });
        } catch (SecurityException e) {
            Log.d(TAG, "Can't get properties: READ_DEVICE_CONFIG granted=" + context.checkCallingOrSelfPermission(Manifest.permission.READ_DEVICE_CONFIG) + ", package=" + context.getPackageName());
        }
    }

    public FrameTracker createFrameTracker(Configuration config) {
        View view = config.mView;
        FrameTracker.ThreadedRendererWrapper threadedRenderer = view == null ? null : new FrameTracker.ThreadedRendererWrapper(view.getThreadedRenderer());
        FrameTracker.ViewRootWrapper viewRoot = view != null ? new FrameTracker.ViewRootWrapper(view.getViewRootImpl()) : null;
        FrameTracker.SurfaceControlWrapper surfaceControl = new FrameTracker.SurfaceControlWrapper();
        FrameTracker.ChoreographerWrapper choreographer = new FrameTracker.ChoreographerWrapper(Choreographer.getInstance());
        FrameTracker.FrameTrackerListener eventsListener = new AnonymousClass1(config);
        FrameTracker.FrameMetricsWrapper frameMetrics = new FrameTracker.FrameMetricsWrapper();
        return new FrameTracker(config, threadedRenderer, viewRoot, surfaceControl, choreographer, frameMetrics, new FrameTracker.StatsLogWrapper(this.mDisplayResolutionTracker), this.mTraceThresholdMissedFrames, this.mTraceThresholdFrameTimeMillis, eventsListener);
    }

    /* renamed from: com.android.internal.jank.InteractionJankMonitor$1, reason: invalid class name */
    class AnonymousClass1 implements FrameTracker.FrameTrackerListener {
        final /* synthetic */ Configuration val$config;

        AnonymousClass1(Configuration configuration) {
            this.val$config = configuration;
        }

        @Override // com.android.internal.jank.FrameTracker.FrameTrackerListener
        public void onCujEvents(final FrameTracker tracker, final String action, final int reason) {
            Handler handler = this.val$config.getHandler();
            final Configuration configuration = this.val$config;
            handler.runWithScissors(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    InteractionJankMonitor.AnonymousClass1.this.lambda$onCujEvents$0(configuration, tracker, action, reason);
                }
            }, InteractionJankMonitor.EXECUTOR_TASK_TIMEOUT);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCujEvents$0(Configuration config, FrameTracker tracker, String action, int reason) {
            InteractionJankMonitor.this.handleCujEvents(config.mCujType, tracker, action, reason);
        }

        @Override // com.android.internal.jank.FrameTracker.FrameTrackerListener
        public void triggerPerfetto(final Configuration config) {
            InteractionJankMonitor.this.mWorker.post(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PerfettoTrigger.trigger(InteractionJankMonitor.Configuration.this.getPerfettoTrigger());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCujEvents(int cuj, FrameTracker tracker, String action, int reason) {
        if (needRemoveTasks(action, reason)) {
            removeTrackerIfCurrent(cuj, tracker, reason);
        }
    }

    private static boolean needRemoveTasks(String action, int reason) {
        boolean badEnd = action.equals(ACTION_SESSION_END) && reason != 0;
        boolean badCancel = (!action.equals(ACTION_SESSION_CANCEL) || reason == 16 || reason == 19) ? false : true;
        return badEnd || badCancel;
    }

    public boolean isInstrumenting(int cujType) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mRunningTrackers.contains(cujType);
        }
        return contains;
    }

    public boolean begin(View v, int cujType) {
        try {
            return begin(Configuration.Builder.withView(cujType, v));
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "Build configuration failed!", ex);
            return false;
        }
    }

    public boolean begin(Configuration.Builder builder) {
        try {
            final Configuration config = builder.build();
            postEventLogToWorkerThread(new TimeFunction() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda2
                @Override // com.android.internal.jank.InteractionJankMonitor.TimeFunction
                public final void invoke(long j, long j2, long j3) {
                    EventLogTags.writeJankCujEventsBeginRequest(r0.mCujType, j, j2, j3, InteractionJankMonitor.Configuration.this.mTag);
                }
            });
            final TrackerResult result = new TrackerResult();
            boolean success = config.getHandler().runWithScissors(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    InteractionJankMonitor.this.lambda$begin$2(result, config);
                }
            }, EXECUTOR_TASK_TIMEOUT);
            if (!success) {
                Log.d(TAG, "begin failed due to timeout, CUJ=" + Cuj.getNameOfCuj(config.mCujType));
                return false;
            }
            return result.mResult;
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "Build configuration failed!", ex);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$begin$2(TrackerResult result, Configuration config) {
        result.mResult = beginInternal(config);
    }

    private boolean beginInternal(final Configuration conf) {
        RunningTracker tracker;
        final int cujType = conf.mCujType;
        if (!shouldMonitor() || (tracker = putTrackerIfNoCurrent(cujType, new Supplier() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                InteractionJankMonitor.RunningTracker lambda$beginInternal$4;
                lambda$beginInternal$4 = InteractionJankMonitor.this.lambda$beginInternal$4(conf, cujType);
                return lambda$beginInternal$4;
            }
        })) == null) {
            return false;
        }
        tracker.mTracker.begin();
        scheduleTimeoutAction(tracker.mConfig, tracker.mTimeoutAction);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ RunningTracker lambda$beginInternal$4(Configuration conf, final int cujType) {
        return new RunningTracker(conf, createFrameTracker(conf), new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                InteractionJankMonitor.this.lambda$beginInternal$3(cujType);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$beginInternal$3(int cujType) {
        cancel(cujType, 19);
    }

    public boolean shouldMonitor() {
        return this.mEnabled && ThreadLocalRandom.current().nextInt(this.mSamplingInterval) == 0;
    }

    public void scheduleTimeoutAction(Configuration config, Runnable action) {
        config.getHandler().postDelayed(action, config.mTimeout);
    }

    public boolean end(final int cujType) {
        postEventLogToWorkerThread(new TimeFunction() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda8
            @Override // com.android.internal.jank.InteractionJankMonitor.TimeFunction
            public final void invoke(long j, long j2, long j3) {
                EventLogTags.writeJankCujEventsEndRequest(cujType, j, j2, j3);
            }
        });
        final RunningTracker tracker = getTracker(cujType);
        if (tracker == null) {
            return false;
        }
        try {
            final TrackerResult result = new TrackerResult();
            boolean success = tracker.mConfig.getHandler().runWithScissors(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    InteractionJankMonitor.this.lambda$end$6(result, tracker);
                }
            }, EXECUTOR_TASK_TIMEOUT);
            if (!success) {
                Log.d(TAG, "end failed due to timeout, CUJ=" + Cuj.getNameOfCuj(cujType));
                return false;
            }
            return result.mResult;
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "Execute end task failed!", ex);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$end$6(TrackerResult result, RunningTracker tracker) {
        result.mResult = endInternal(tracker);
    }

    private boolean endInternal(RunningTracker tracker) {
        if (removeTrackerIfCurrent(tracker, 0)) {
            return false;
        }
        tracker.mTracker.end(0);
        return true;
    }

    public boolean cancel(final int cujType) {
        postEventLogToWorkerThread(new TimeFunction() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda5
            @Override // com.android.internal.jank.InteractionJankMonitor.TimeFunction
            public final void invoke(long j, long j2, long j3) {
                EventLogTags.writeJankCujEventsCancelRequest(cujType, j, j2, j3);
            }
        });
        return cancel(cujType, 16);
    }

    public boolean cancel(int cujType, final int reason) {
        final RunningTracker tracker = getTracker(cujType);
        if (tracker == null) {
            return false;
        }
        try {
            final TrackerResult result = new TrackerResult();
            boolean success = tracker.mConfig.getHandler().runWithScissors(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    InteractionJankMonitor.this.lambda$cancel$8(result, tracker, reason);
                }
            }, EXECUTOR_TASK_TIMEOUT);
            if (!success) {
                Log.d(TAG, "cancel failed due to timeout, CUJ=" + Cuj.getNameOfCuj(cujType));
                return false;
            }
            return result.mResult;
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "Execute cancel task failed!", ex);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancel$8(TrackerResult result, RunningTracker tracker, int reason) {
        result.mResult = cancelInternal(tracker, reason);
    }

    private boolean cancelInternal(RunningTracker tracker, int reason) {
        if (removeTrackerIfCurrent(tracker, reason)) {
            return false;
        }
        tracker.mTracker.cancel(reason);
        return true;
    }

    private RunningTracker putTrackerIfNoCurrent(int cuj, Supplier<RunningTracker> supplier) {
        synchronized (this.mLock) {
            if (this.mRunningTrackers.contains(cuj)) {
                return null;
            }
            RunningTracker tracker = supplier.get();
            if (tracker == null) {
                return null;
            }
            this.mRunningTrackers.put(cuj, tracker);
            if (this.mDebugOverlay != null) {
                this.mDebugOverlay.onTrackerAdded(cuj, tracker);
            }
            return tracker;
        }
    }

    private RunningTracker getTracker(int cuj) {
        RunningTracker runningTracker;
        synchronized (this.mLock) {
            runningTracker = this.mRunningTrackers.get(cuj);
        }
        return runningTracker;
    }

    private boolean removeTrackerIfCurrent(RunningTracker tracker, int reason) {
        return removeTrackerIfCurrent(tracker.mConfig.mCujType, tracker.mTracker, reason);
    }

    private boolean removeTrackerIfCurrent(int cuj, FrameTracker tracker, int reason) {
        synchronized (this.mLock) {
            RunningTracker running = this.mRunningTrackers.get(cuj);
            if (running != null && running.mTracker == tracker) {
                running.mConfig.getHandler().removeCallbacks(running.mTimeoutAction);
                this.mRunningTrackers.remove(cuj);
                if (this.mDebugOverlay != null) {
                    this.mDebugOverlay.onTrackerRemoved(cuj, reason, this.mRunningTrackers);
                }
                return false;
            }
            return true;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void updateProperties(DeviceConfig.Properties properties) {
        char c;
        for (String property : properties.getKeyset()) {
            boolean z = false;
            switch (property.hashCode()) {
                case -1609594047:
                    if (property.equals("enabled")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -464409256:
                    if (property.equals(SETTINGS_THRESHOLD_FRAME_TIME_MILLIS_KEY)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -94225891:
                    if (property.equals("sampling_interval")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1147866214:
                    if (property.equals(SETTINGS_DEBUG_OVERLAY_ENABLED_KEY)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1589403900:
                    if (property.equals(SETTINGS_THRESHOLD_MISSED_FRAMES_KEY)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    this.mSamplingInterval = properties.getInt(property, 1);
                    break;
                case 1:
                    this.mTraceThresholdMissedFrames = properties.getInt(property, 3);
                    break;
                case 2:
                    this.mTraceThresholdFrameTimeMillis = properties.getInt(property, 64);
                    break;
                case 3:
                    this.mEnabled = properties.getBoolean(property, DEFAULT_ENABLED);
                    break;
                case 4:
                    if (Build.IS_DEBUGGABLE && properties.getBoolean(property, false)) {
                        z = true;
                    }
                    boolean debugOverlayEnabled = z;
                    if (debugOverlayEnabled && this.mDebugOverlay == null) {
                        this.mDebugOverlay = new InteractionMonitorDebugOverlay(this.mLock, this.mDebugBgColor, this.mDebugYOffset);
                        break;
                    } else if (!debugOverlayEnabled && this.mDebugOverlay != null) {
                        this.mDebugOverlay.dispose();
                        this.mDebugOverlay = null;
                        break;
                    }
                    break;
                default:
                    Log.w(TAG, "Got a change event for an unknown property: " + property + " => " + properties.getString(property, ""));
                    break;
            }
        }
    }

    @Deprecated
    public static String getNameOfInteraction(int interactionType) {
        return Cuj.getNameOfInteraction(interactionType);
    }

    @Deprecated
    public static String getNameOfCuj(int cujType) {
        return Cuj.getNameOfCuj(cujType);
    }

    public void configDebugOverlay(int bgColor, double yOffset) {
        this.mDebugBgColor = bgColor;
        this.mDebugYOffset = yOffset;
    }

    private void postEventLogToWorkerThread(final TimeFunction logFunction) {
        Instant now = Instant.now();
        final long unixNanos = TimeUnit.NANOSECONDS.convert(now.getEpochSecond(), TimeUnit.SECONDS) + now.getNano();
        final long elapsedNanos = SystemClock.elapsedRealtimeNanos();
        final long realtimeNanos = SystemClock.uptimeNanos();
        this.mWorker.post(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                InteractionJankMonitor.TimeFunction.this.invoke(unixNanos, elapsedNanos, realtimeNanos);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TrackerResult {
        private boolean mResult;

        private TrackerResult() {
        }
    }

    public static class Configuration {
        private final Context mContext;
        private final int mCujType;
        private final boolean mDeferMonitor;
        private final Handler mHandler;
        private final String mSessionName;
        private final SurfaceControl mSurfaceControl;
        private final boolean mSurfaceOnly;
        private final String mTag;
        private final long mTimeout;
        private final View mView;

        public static class Builder {
            private final int mAttrCujType;
            private SurfaceControl mAttrSurfaceControl;
            private boolean mAttrSurfaceOnly;
            private View mAttrView = null;
            private Context mAttrContext = null;
            private long mAttrTimeout = InteractionJankMonitor.DEFAULT_TIMEOUT_MS;
            private String mAttrTag = "";
            private boolean mAttrDeferMonitor = true;

            public static Builder withSurface(int cuj, Context context, SurfaceControl surfaceControl) {
                return new Builder(cuj).setContext(context).setSurfaceControl(surfaceControl).setSurfaceOnly(true);
            }

            public static Builder withView(int cuj, View view) {
                return new Builder(cuj).setView(view).setContext(view.getContext());
            }

            private Builder(int cuj) {
                this.mAttrCujType = cuj;
            }

            private Builder setView(View view) {
                this.mAttrView = view;
                return this;
            }

            public Builder setTimeout(long timeout) {
                this.mAttrTimeout = timeout;
                return this;
            }

            public Builder setTag(String tag) {
                this.mAttrTag = tag;
                return this;
            }

            private Builder setSurfaceOnly(boolean surfaceOnly) {
                this.mAttrSurfaceOnly = surfaceOnly;
                return this;
            }

            private Builder setContext(Context context) {
                this.mAttrContext = context;
                return this;
            }

            private Builder setSurfaceControl(SurfaceControl surfaceControl) {
                this.mAttrSurfaceControl = surfaceControl;
                return this;
            }

            public Builder setDeferMonitorForAnimationStart(boolean defer) {
                this.mAttrDeferMonitor = defer;
                return this;
            }

            public Configuration build() throws IllegalArgumentException {
                return new Configuration(this.mAttrCujType, this.mAttrView, this.mAttrTag, this.mAttrTimeout, this.mAttrSurfaceOnly, this.mAttrContext, this.mAttrSurfaceControl, this.mAttrDeferMonitor);
            }
        }

        private Configuration(int cuj, View view, String tag, long timeout, boolean surfaceOnly, Context context, SurfaceControl surfaceControl, boolean deferMonitor) {
            Context applicationContext;
            this.mCujType = cuj;
            this.mTag = tag;
            this.mSessionName = generateSessionName(Cuj.getNameOfCuj(cuj), tag);
            this.mTimeout = timeout;
            this.mView = view;
            this.mSurfaceOnly = surfaceOnly;
            if (context != null) {
                applicationContext = context;
            } else {
                applicationContext = view != null ? view.getContext().getApplicationContext() : null;
            }
            this.mContext = applicationContext;
            this.mSurfaceControl = surfaceControl;
            this.mDeferMonitor = deferMonitor;
            validate();
            this.mHandler = this.mSurfaceOnly ? this.mContext.getMainThreadHandler() : this.mView.getHandler();
        }

        public static String generateSessionName(String cujName, String cujPostfix) {
            int remaining;
            boolean hasPostfix = !TextUtils.isEmpty(cujPostfix);
            if (hasPostfix && cujPostfix.length() > (remaining = 100 - cujName.length())) {
                cujPostfix = cujPostfix.substring(0, remaining - 3).concat(Session.TRUNCATE_STRING);
            }
            if (hasPostfix) {
                return TextUtils.formatSimple("J<%s::%s>", cujName, cujPostfix);
            }
            return TextUtils.formatSimple("J<%s>", cujName);
        }

        private void validate() {
            boolean shouldThrow = false;
            StringBuilder msg = new StringBuilder();
            if (this.mTag == null) {
                shouldThrow = true;
                msg.append("Invalid tag; ");
            }
            if (this.mTimeout < 0) {
                shouldThrow = true;
                msg.append("Invalid timeout value; ");
            }
            if (this.mSurfaceOnly) {
                if (this.mContext == null) {
                    shouldThrow = true;
                    msg.append("Must pass in a context if only instrument surface; ");
                }
                if (this.mSurfaceControl == null || !this.mSurfaceControl.isValid()) {
                    shouldThrow = true;
                    msg.append("Must pass in a valid surface control if only instrument surface; ");
                }
            } else if (!hasValidView()) {
                shouldThrow = true;
                boolean attached = false;
                boolean hasViewRoot = false;
                boolean hasRenderer = false;
                if (this.mView != null) {
                    attached = this.mView.isAttachedToWindow();
                    hasViewRoot = this.mView.getViewRootImpl() != null;
                    hasRenderer = this.mView.getThreadedRenderer() != null;
                }
                String err = "invalid view: view=" + this.mView + ", attached=" + attached + ", hasViewRoot=" + hasViewRoot + ", hasRenderer=" + hasRenderer;
                msg.append(err);
            }
            if (shouldThrow) {
                throw new IllegalArgumentException(msg.toString());
            }
        }

        boolean hasValidView() {
            return this.mSurfaceOnly || !(this.mView == null || !this.mView.isAttachedToWindow() || this.mView.getViewRootImpl() == null || this.mView.getThreadedRenderer() == null);
        }

        public boolean isSurfaceOnly() {
            return this.mSurfaceOnly;
        }

        public SurfaceControl getSurfaceControl() {
            return this.mSurfaceControl;
        }

        public View getView() {
            return this.mView;
        }

        public boolean shouldDeferMonitor() {
            return this.mDeferMonitor;
        }

        public Handler getHandler() {
            return this.mHandler;
        }

        public int getDisplayId() {
            return (this.mSurfaceOnly ? this.mContext : this.mView.getContext()).getDisplayId();
        }

        public String getSessionName() {
            return this.mSessionName;
        }

        public int getStatsdInteractionType() {
            return Cuj.getStatsdInteractionType(this.mCujType);
        }

        public boolean logToStatsd() {
            return Cuj.logToStatsd(this.mCujType);
        }

        public String getPerfettoTrigger() {
            return TextUtils.formatSimple("com.android.telemetry.interaction-jank-monitor-%d", Integer.valueOf(this.mCujType));
        }

        public int getCujType() {
            return this.mCujType;
        }
    }

    static class RunningTracker {
        public final Configuration mConfig;
        public final Runnable mTimeoutAction;
        public final FrameTracker mTracker;

        RunningTracker(Configuration config, FrameTracker tracker, Runnable timeoutAction) {
            this.mConfig = config;
            this.mTracker = tracker;
            this.mTimeoutAction = timeoutAction;
        }
    }
}
