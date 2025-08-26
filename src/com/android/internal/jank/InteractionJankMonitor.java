package com.android.internal.jank;

import android.Manifest;
import android.app.ActivityThread;
import android.app.Application;
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
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.PerfettoTrigger;
import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class InteractionJankMonitor {
    private static final String ACTION_PREFIX = "com.android.internal.jank.InteractionJankMonitor";
    public static final String ACTION_SESSION_CANCEL;
    public static final String ACTION_SESSION_END;

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
    private static final boolean DEFAULT_ENABLED;
    private static final int DEFAULT_SAMPLING_INTERVAL = 1;
    private static final long DEFAULT_TIMEOUT_MS;
    private static final int DEFAULT_TRACE_THRESHOLD_FRAME_TIME_MILLIS = 64;
    private static final int DEFAULT_TRACE_THRESHOLD_MISSED_FRAMES = 3;
    private static final String DEFAULT_WORKER_NAME;
    static final long EXECUTOR_TASK_TIMEOUT = 500;
    private static final int MAX_LENGTH_SESSION_NAME = 100;
    private static final String SETTINGS_DEBUG_OVERLAY_ENABLED_KEY = "debug_overlay_enabled";
    private static final String SETTINGS_ENABLED_KEY = "enabled";
    private static final String SETTINGS_SAMPLING_INTERVAL_KEY = "sampling_interval";
    private static final String SETTINGS_THRESHOLD_FRAME_TIME_MILLIS_KEY = "trace_threshold_frame_time_millis";
    private static final String SETTINGS_THRESHOLD_MISSED_FRAMES_KEY = "trace_threshold_missed_frames";
    private static final String TAG = "InteractionJankMonitor";
    private final Application mCurrentApplication;
    private InteractionMonitorDebugOverlay mDebugOverlay;
    private final DisplayResolutionTracker mDisplayResolutionTracker;
    private final Handler mWorker;
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

    static {
        String canonicalName = InteractionJankMonitor.class.getCanonicalName();
        DEFAULT_WORKER_NAME = "InteractionJankMonitor-Worker";
        DEFAULT_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(2L);
        DEFAULT_ENABLED = Build.IS_DEBUGGABLE;
        ACTION_SESSION_END = canonicalName + ".ACTION_SESSION_END";
        ACTION_SESSION_CANCEL = canonicalName + ".ACTION_SESSION_CANCEL";
    }

    private static class InstanceHolder {
        public static final InteractionJankMonitor INSTANCE = new InteractionJankMonitor(new HandlerThread(InteractionJankMonitor.DEFAULT_WORKER_NAME));

        private InstanceHolder() {
        }
    }

    public static InteractionJankMonitor getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public InteractionJankMonitor(HandlerThread handlerThread) {
        String packageName;
        handlerThread.start();
        Handler threadHandler = handlerThread.getThreadHandler();
        this.mWorker = threadHandler;
        this.mDisplayResolutionTracker = new DisplayResolutionTracker(threadHandler);
        Application applicationCurrentApplication = ActivityThread.currentApplication();
        this.mCurrentApplication = applicationCurrentApplication;
        if (applicationCurrentApplication == null || applicationCurrentApplication.checkCallingOrSelfPermission(Manifest.permission.READ_DEVICE_CONFIG) != 0) {
            String str = TAG;
            StringBuilder sb = new StringBuilder("Initializing without READ_DEVICE_CONFIG permission. enabled=");
            sb.append(this.mEnabled);
            sb.append(", interval=");
            sb.append(this.mSamplingInterval);
            sb.append(", missedFrameThreshold=");
            sb.append(this.mTraceThresholdMissedFrames);
            sb.append(", frameTimeThreshold=");
            sb.append(this.mTraceThresholdFrameTimeMillis);
            sb.append(", package=");
            if (applicationCurrentApplication == null) {
                packageName = PerfettoProtoLogImpl.NULL_STRING;
            } else {
                packageName = applicationCurrentApplication.getPackageName();
            }
            sb.append(packageName);
            Log.w(str, sb.toString());
            return;
        }
        threadHandler.post(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        try {
            updateProperties(DeviceConfig.getProperties("interaction_jank_monitor", new String[0]));
            DeviceConfig.addOnPropertiesChangedListener("interaction_jank_monitor", new HandlerExecutor(this.mWorker), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda3
                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                    this.f$0.updateProperties(properties);
                }
            });
        } catch (SecurityException unused) {
            Log.d(TAG, "Can't get properties: READ_DEVICE_CONFIG granted=" + this.mCurrentApplication.checkCallingOrSelfPermission(Manifest.permission.READ_DEVICE_CONFIG) + ", package=" + this.mCurrentApplication.getPackageName());
        }
    }

    public FrameTracker createFrameTracker(Configuration configuration) {
        View view = configuration.mView;
        return new FrameTracker(configuration, view == null ? null : new FrameTracker.ThreadedRendererWrapper(view.getThreadedRenderer()), view != null ? new FrameTracker.ViewRootWrapper(view.getViewRootImpl()) : null, new FrameTracker.SurfaceControlWrapper(), new FrameTracker.ChoreographerWrapper(Choreographer.getInstance()), new FrameTracker.FrameMetricsWrapper(), new FrameTracker.StatsLogWrapper(this.mDisplayResolutionTracker), this.mTraceThresholdMissedFrames, this.mTraceThresholdFrameTimeMillis, new AnonymousClass1(configuration));
    }

    /* renamed from: com.android.internal.jank.InteractionJankMonitor$1, reason: invalid class name */
    class AnonymousClass1 implements FrameTracker.FrameTrackerListener {
        final /* synthetic */ Configuration val$config;

        AnonymousClass1(Configuration configuration) {
            this.val$config = configuration;
        }

        @Override // com.android.internal.jank.FrameTracker.FrameTrackerListener
        public void onCujEvents(final FrameTracker frameTracker, final String str, final int i) {
            Handler handler = this.val$config.getHandler();
            final Configuration configuration = this.val$config;
            handler.runWithScissors(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCujEvents$0(configuration, frameTracker, str, i);
                }
            }, InteractionJankMonitor.EXECUTOR_TASK_TIMEOUT);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCujEvents$0(Configuration configuration, FrameTracker frameTracker, String str, int i) {
            InteractionJankMonitor.this.handleCujEvents(configuration.mCujType, frameTracker, str, i);
        }

        @Override // com.android.internal.jank.FrameTracker.FrameTrackerListener
        public void triggerPerfetto(final Configuration configuration) {
            InteractionJankMonitor.this.mWorker.post(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws IOException {
                    PerfettoTrigger.trigger(configuration.getPerfettoTrigger());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCujEvents(int i, FrameTracker frameTracker, String str, int i2) {
        if (needRemoveTasks(str, i2)) {
            removeTrackerIfCurrent(i, frameTracker, i2);
        }
    }

    private static boolean needRemoveTasks(String str, int i) {
        return (str.equals(ACTION_SESSION_END) && i != 0) || (str.equals(ACTION_SESSION_CANCEL) && i != 16 && i != 19);
    }

    public boolean isInstrumenting(int i) {
        boolean zContains;
        synchronized (this.mLock) {
            zContains = this.mRunningTrackers.contains(i);
        }
        return zContains;
    }

    public boolean begin(View view, int i) {
        try {
            return begin(Configuration.Builder.withView(i, view));
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "Build configuration failed!", e);
            return false;
        }
    }

    public boolean begin(SurfaceControl surfaceControl, Context context, Handler handler, int i) {
        try {
            return begin(Configuration.Builder.withSurface(i, context, surfaceControl, handler));
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "Build configuration failed!", e);
            return false;
        }
    }

    public boolean begin(SurfaceControl surfaceControl, Context context, Handler handler, int i, String str) {
        try {
            Configuration.Builder builderWithSurface = Configuration.Builder.withSurface(i, context, surfaceControl, handler);
            if (!TextUtils.isEmpty(str)) {
                builderWithSurface.setTag(str);
            }
            return begin(builderWithSurface);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "Build configuration failed!", e);
            return false;
        }
    }

    public boolean begin(Configuration.Builder builder) {
        try {
            final Configuration configurationBuild = builder.build();
            postEventLogToWorkerThread(new TimeFunction() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda1
                @Override // com.android.internal.jank.InteractionJankMonitor.TimeFunction
                public final void invoke(long j, long j2, long j3) {
                    InteractionJankMonitor.Configuration configuration = configurationBuild;
                    EventLogTags.writeJankCujEventsBeginRequest(configuration.mCujType, j, j2, j3, configuration.mTag);
                }
            });
            final TrackerResult trackerResult = new TrackerResult();
            if (!configurationBuild.getHandler().runWithScissors(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$begin$2(trackerResult, configurationBuild);
                }
            }, EXECUTOR_TASK_TIMEOUT)) {
                Log.d(TAG, "begin failed due to timeout, CUJ=" + Cuj.getNameOfCuj(configurationBuild.mCujType));
                return false;
            }
            return trackerResult.mResult;
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "Build configuration failed!", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$begin$2(TrackerResult trackerResult, Configuration configuration) {
        trackerResult.mResult = beginInternal(configuration);
    }

    private boolean beginInternal(final Configuration configuration) {
        final int i = configuration.mCujType;
        if (!shouldMonitor()) {
            return false;
        }
        if (!configuration.hasValidView()) {
            Log.w(TAG, "The view has since become invalid, aborting the CUJ.");
            return false;
        }
        RunningTracker runningTrackerPutTrackerIfNoCurrent = putTrackerIfNoCurrent(i, new Supplier() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$beginInternal$4(configuration, i);
            }
        });
        if (runningTrackerPutTrackerIfNoCurrent == null) {
            return false;
        }
        runningTrackerPutTrackerIfNoCurrent.mTracker.begin();
        scheduleTimeoutAction(runningTrackerPutTrackerIfNoCurrent.mConfig, runningTrackerPutTrackerIfNoCurrent.mTimeoutAction);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ RunningTracker lambda$beginInternal$4(Configuration configuration, final int i) {
        return new RunningTracker(configuration, createFrameTracker(configuration), new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$beginInternal$3(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$beginInternal$3(int i) {
        Log.w(TAG, "CUJ cancelled due to timeout, CUJ=" + Cuj.getNameOfCuj(i));
        cancel(i, 19);
    }

    public boolean shouldMonitor() {
        return this.mEnabled && ThreadLocalRandom.current().nextInt(this.mSamplingInterval) == 0;
    }

    public void scheduleTimeoutAction(Configuration configuration, Runnable runnable) {
        configuration.getHandler().postDelayed(runnable, configuration.mTimeout);
    }

    public boolean end(final int i) {
        postEventLogToWorkerThread(new TimeFunction() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda8
            @Override // com.android.internal.jank.InteractionJankMonitor.TimeFunction
            public final void invoke(long j, long j2, long j3) {
                EventLogTags.writeJankCujEventsEndRequest(i, j, j2, j3);
            }
        });
        final RunningTracker tracker = getTracker(i);
        if (tracker == null) {
            return false;
        }
        try {
            final TrackerResult trackerResult = new TrackerResult();
            if (!tracker.mConfig.getHandler().runWithScissors(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$end$6(trackerResult, tracker);
                }
            }, EXECUTOR_TASK_TIMEOUT)) {
                Log.d(TAG, "end failed due to timeout, CUJ=" + Cuj.getNameOfCuj(i));
                return false;
            }
            return trackerResult.mResult;
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "Execute end task failed!", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$end$6(TrackerResult trackerResult, RunningTracker runningTracker) {
        trackerResult.mResult = endInternal(runningTracker);
    }

    private boolean endInternal(RunningTracker runningTracker) {
        if (removeTrackerIfCurrent(runningTracker, 0)) {
            return false;
        }
        runningTracker.mTracker.end(0);
        return true;
    }

    public boolean cancel(final int i) {
        postEventLogToWorkerThread(new TimeFunction() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda5
            @Override // com.android.internal.jank.InteractionJankMonitor.TimeFunction
            public final void invoke(long j, long j2, long j3) {
                EventLogTags.writeJankCujEventsCancelRequest(i, j, j2, j3);
            }
        });
        return cancel(i, 16);
    }

    public boolean cancel(int i, final int i2) {
        final RunningTracker tracker = getTracker(i);
        if (tracker == null) {
            return false;
        }
        try {
            final TrackerResult trackerResult = new TrackerResult();
            if (!tracker.mConfig.getHandler().runWithScissors(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$cancel$8(trackerResult, tracker, i2);
                }
            }, EXECUTOR_TASK_TIMEOUT)) {
                Log.d(TAG, "cancel failed due to timeout, CUJ=" + Cuj.getNameOfCuj(i));
                return false;
            }
            return trackerResult.mResult;
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "Execute cancel task failed!", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancel$8(TrackerResult trackerResult, RunningTracker runningTracker, int i) {
        trackerResult.mResult = cancelInternal(runningTracker, i);
    }

    private boolean cancelInternal(RunningTracker runningTracker, int i) {
        if (removeTrackerIfCurrent(runningTracker, i)) {
            return false;
        }
        runningTracker.mTracker.cancel(i);
        return true;
    }

    private RunningTracker putTrackerIfNoCurrent(int i, Supplier<RunningTracker> supplier) {
        synchronized (this.mLock) {
            if (this.mRunningTrackers.contains(i)) {
                return null;
            }
            RunningTracker runningTracker = supplier.get();
            if (runningTracker == null) {
                return null;
            }
            this.mRunningTrackers.put(i, runningTracker);
            InteractionMonitorDebugOverlay interactionMonitorDebugOverlay = this.mDebugOverlay;
            if (interactionMonitorDebugOverlay != null) {
                interactionMonitorDebugOverlay.onTrackerAdded(i, runningTracker.mTracker.hashCode());
            }
            return runningTracker;
        }
    }

    private RunningTracker getTracker(int i) {
        RunningTracker runningTracker;
        synchronized (this.mLock) {
            runningTracker = this.mRunningTrackers.get(i);
        }
        return runningTracker;
    }

    private boolean removeTrackerIfCurrent(RunningTracker runningTracker, int i) {
        return removeTrackerIfCurrent(runningTracker.mConfig.mCujType, runningTracker.mTracker, i);
    }

    private boolean removeTrackerIfCurrent(int i, FrameTracker frameTracker, int i2) {
        synchronized (this.mLock) {
            RunningTracker runningTracker = this.mRunningTrackers.get(i);
            if (runningTracker != null && runningTracker.mTracker == frameTracker) {
                runningTracker.mConfig.getHandler().removeCallbacks(runningTracker.mTimeoutAction);
                this.mRunningTrackers.remove(i);
                InteractionMonitorDebugOverlay interactionMonitorDebugOverlay = this.mDebugOverlay;
                if (interactionMonitorDebugOverlay != null) {
                    interactionMonitorDebugOverlay.onTrackerRemoved(i, i2, frameTracker.hashCode());
                }
                return false;
            }
            return true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateProperties(DeviceConfig.Properties properties) {
        InteractionMonitorDebugOverlay interactionMonitorDebugOverlay;
        for (String str : properties.getKeyset()) {
            str.hashCode();
            switch (str) {
                case "enabled":
                    this.mEnabled = properties.getBoolean(str, DEFAULT_ENABLED);
                    break;
                case "trace_threshold_frame_time_millis":
                    this.mTraceThresholdFrameTimeMillis = properties.getInt(str, 64);
                    break;
                case "sampling_interval":
                    this.mSamplingInterval = properties.getInt(str, 1);
                    break;
                case "debug_overlay_enabled":
                    if (!Build.IS_USER) {
                        boolean z = properties.getBoolean(str, false);
                        synchronized (this.mLock) {
                            if (z) {
                                try {
                                    if (this.mDebugOverlay == null) {
                                        this.mDebugOverlay = new InteractionMonitorDebugOverlay(this.mCurrentApplication, this.mWorker, this.mDebugBgColor, this.mDebugYOffset);
                                    } else if (!z && (interactionMonitorDebugOverlay = this.mDebugOverlay) != null) {
                                        interactionMonitorDebugOverlay.dispose();
                                        this.mDebugOverlay = null;
                                    }
                                } finally {
                                }
                            }
                        }
                        break;
                    } else {
                        continue;
                    }
                case "trace_threshold_missed_frames":
                    this.mTraceThresholdMissedFrames = properties.getInt(str, 3);
                    break;
                default:
                    Log.w(TAG, "Got a change event for an unknown property: " + str + " => " + properties.getString(str, ""));
                    break;
            }
        }
    }

    @Deprecated
    public static String getNameOfInteraction(int i) {
        return Cuj.getNameOfInteraction(i);
    }

    @Deprecated
    public static String getNameOfCuj(int i) {
        return Cuj.getNameOfCuj(i);
    }

    public void configDebugOverlay(int i, double d) {
        this.mDebugBgColor = i;
        this.mDebugYOffset = d;
    }

    private void postEventLogToWorkerThread(final TimeFunction timeFunction) {
        final long jConvert = TimeUnit.NANOSECONDS.convert(Instant.now().getEpochSecond(), TimeUnit.SECONDS) + r0.getNano();
        final long jElapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        final long jUptimeNanos = SystemClock.uptimeNanos();
        this.mWorker.post(new Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                timeFunction.invoke(jConvert, jElapsedRealtimeNanos, jUptimeNanos);
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
            private Handler mHandler = null;

            public static Builder withSurface(int i, Context context, SurfaceControl surfaceControl, Handler handler) {
                return new Builder(i).setContext(context).setSurfaceControl(surfaceControl).setSurfaceOnly(true).setHandler(handler);
            }

            public static Builder withView(int i, View view) {
                return new Builder(i).setView(view).setContext(view.getContext());
            }

            private Builder(int i) {
                this.mAttrCujType = i;
            }

            public Builder setHandler(Handler handler) {
                this.mHandler = handler;
                return this;
            }

            private Builder setView(View view) {
                this.mAttrView = view;
                return this;
            }

            public Builder setTimeout(long j) {
                this.mAttrTimeout = j;
                return this;
            }

            public Builder setTag(String str) {
                this.mAttrTag = str;
                return this;
            }

            private Builder setSurfaceOnly(boolean z) {
                this.mAttrSurfaceOnly = z;
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

            public Builder setDeferMonitorForAnimationStart(boolean z) {
                this.mAttrDeferMonitor = z;
                return this;
            }

            public Configuration build() throws IllegalArgumentException {
                return new Configuration(this.mAttrCujType, this.mAttrView, this.mAttrTag, this.mAttrTimeout, this.mAttrSurfaceOnly, this.mAttrContext, this.mAttrSurfaceControl, this.mAttrDeferMonitor, this.mHandler);
            }
        }

        private Configuration(int i, View view, String str, long j, boolean z, Context context, SurfaceControl surfaceControl, boolean z2, Handler handler) {
            this.mCujType = i;
            this.mTag = str;
            String strGenerateSessionName = generateSessionName(Cuj.getNameOfCuj(i), str);
            this.mSessionName = strGenerateSessionName;
            this.mTimeout = j;
            this.mView = view;
            this.mSurfaceOnly = z;
            context = context == null ? view != null ? view.getContext().getApplicationContext() : null : context;
            this.mContext = context;
            this.mSurfaceControl = surfaceControl;
            this.mDeferMonitor = z2;
            if (handler != null) {
                this.mHandler = handler;
            } else if (z) {
                Log.w(InteractionJankMonitor.TAG, "No UIThread provided for " + strGenerateSessionName + " (surface only). Defaulting to app main thread.");
                this.mHandler = context.getMainThreadHandler();
            } else {
                this.mHandler = view.getHandler();
            }
            validate();
        }

        public static String generateSessionName(String str, String str2) {
            boolean zIsEmpty = TextUtils.isEmpty(str2);
            if (!zIsEmpty) {
                int length = str.length();
                if (str2.length() > 100 - length) {
                    str2 = str2.substring(0, 97 - length).concat(Session.TRUNCATE_STRING);
                }
            }
            if (!zIsEmpty) {
                return TextUtils.formatSimple("J<%s::%s>", str, str2);
            }
            return TextUtils.formatSimple("J<%s>", str);
        }

        /* JADX WARN: Removed duplicated region for block: B:36:0x009d A[PHI: r1
          0x009d: PHI (r1v9 boolean) = (r1v3 boolean), (r1v13 boolean) binds: [B:23:0x004d, B:20:0x0041] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void validate() {
            boolean z;
            boolean z2;
            boolean z3;
            StringBuilder sb = new StringBuilder();
            boolean z4 = true;
            if (this.mTag == null) {
                sb.append("Invalid tag; ");
                z = true;
            } else {
                z = false;
            }
            if (this.mTimeout < 0) {
                sb.append("Invalid timeout value; ");
                z = true;
            }
            if (this.mSurfaceOnly) {
                if (this.mContext == null) {
                    sb.append("Must pass in a context if only instrument surface; ");
                    z = true;
                }
                SurfaceControl surfaceControl = this.mSurfaceControl;
                if (surfaceControl == null || !surfaceControl.isValid()) {
                    sb.append("Must pass in a valid surface control if only instrument surface; ");
                    z = true;
                }
                if (this.mHandler == null) {
                    sb.append("Must pass a UI thread handler when only a surface control is provided.");
                } else {
                    z4 = z;
                }
            } else if (!hasValidView()) {
                View view = this.mView;
                if (view != null) {
                    boolean zIsAttachedToWindow = view.isAttachedToWindow();
                    z3 = this.mView.getViewRootImpl() != null;
                    z = zIsAttachedToWindow;
                    z2 = this.mView.getThreadedRenderer() != null;
                } else {
                    z2 = false;
                    z3 = false;
                }
                sb.append("invalid view: view=" + this.mView + ", attached=" + z + ", hasViewRoot=" + z3 + ", hasRenderer=" + z2);
            }
            if (z4) {
                throw new IllegalArgumentException(sb.toString());
            }
        }

        boolean hasValidView() {
            if (this.mSurfaceOnly) {
                return true;
            }
            View view = this.mView;
            return (view == null || !view.isAttachedToWindow() || this.mView.getViewRootImpl() == null || this.mView.getThreadedRenderer() == null) ? false : true;
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

        RunningTracker(Configuration configuration, FrameTracker frameTracker, Runnable runnable) {
            this.mConfig = configuration;
            this.mTracker = frameTracker;
            this.mTimeoutAction = runnable;
        }
    }
}
