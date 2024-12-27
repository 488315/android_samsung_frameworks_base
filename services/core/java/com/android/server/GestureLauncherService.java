package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.provider.Settings;
import android.util.EventLog;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.wm.WindowManagerInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GestureLauncherService extends SystemService {
    static final long CAMERA_POWER_DOUBLE_TAP_MAX_TIME_MS = 300;
    static final int EMERGENCY_GESTURE_POWER_BUTTON_COOLDOWN_PERIOD_MS_MAX = 5000;
    static final long POWER_SHORT_TAP_SEQUENCE_MAX_INTERVAL_MS = 500;
    public long mCameraGestureLastEventTime;
    public long mCameraGestureOnTimeMs;
    public long mCameraGestureSensor1LastOnTimeMs;
    public long mCameraGestureSensor2LastOnTimeMs;
    public int mCameraLaunchLastEventExtra;
    public boolean mCameraLaunchRegistered;
    public Sensor mCameraLaunchSensor;
    public boolean mCameraLiftRegistered;
    public final CameraLiftTriggerEventListener mCameraLiftTriggerListener;
    public Sensor mCameraLiftTriggerSensor;
    public final Context mContext;
    public final GestureEventListener mGestureListener;
    public final MetricsLogger mMetricsLogger;
    public PowerManager mPowerManager;
    public final AnonymousClass2 mSettingObserver;
    public final UiEventLogger mUiEventLogger;
    public int mUserId;
    public final AnonymousClass1 mUserReceiver;
    public PowerManager.WakeLock mWakeLock;
    public WindowManagerInternal mWindowManagerInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CameraLiftTriggerEventListener extends TriggerEventListener {
        public CameraLiftTriggerEventListener() {}

        @Override // android.hardware.TriggerEventListener
        public final void onTrigger(TriggerEvent triggerEvent) {
            GestureLauncherService gestureLauncherService = GestureLauncherService.this;
            if (gestureLauncherService.mCameraLiftRegistered
                    && triggerEvent.sensor == gestureLauncherService.mCameraLiftTriggerSensor) {
                gestureLauncherService.mContext.getResources();
                SensorManager sensorManager =
                        (SensorManager)
                                GestureLauncherService.this.mContext.getSystemService("sensor");
                boolean isKeyguardShowingAndNotOccluded =
                        GestureLauncherService.this.mWindowManagerInternal
                                .isKeyguardShowingAndNotOccluded();
                boolean isInteractive = GestureLauncherService.this.mPowerManager.isInteractive();
                if ((isKeyguardShowingAndNotOccluded || !isInteractive)
                        && GestureLauncherService.this.handleCameraGesture(true, 2)) {
                    MetricsLogger.action(GestureLauncherService.this.mContext, 989);
                    GestureLauncherService.this.mUiEventLogger.log(
                            GestureLauncherEvent.GESTURE_CAMERA_LIFT);
                }
                GestureLauncherService gestureLauncherService2 = GestureLauncherService.this;
                gestureLauncherService2.mCameraLiftRegistered =
                        sensorManager.requestTriggerSensor(
                                gestureLauncherService2.mCameraLiftTriggerListener,
                                gestureLauncherService2.mCameraLiftTriggerSensor);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GestureEventListener implements SensorEventListener {
        public GestureEventListener() {}

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {}

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
            GestureLauncherService gestureLauncherService = GestureLauncherService.this;
            if (gestureLauncherService.mCameraLaunchRegistered
                    && sensorEvent.sensor == gestureLauncherService.mCameraLaunchSensor
                    && gestureLauncherService.handleCameraGesture(true, 0)) {
                GestureLauncherService.this.mMetricsLogger.action(256);
                GestureLauncherService.this.mUiEventLogger.log(
                        GestureLauncherEvent.GESTURE_CAMERA_WIGGLE);
                long elapsedRealtime = SystemClock.elapsedRealtime();
                GestureLauncherService gestureLauncherService2 = GestureLauncherService.this;
                long j = elapsedRealtime - gestureLauncherService2.mCameraGestureOnTimeMs;
                double d = j;
                long j2 = (long) (r1[0] * d);
                long j3 = (long) (d * r1[1]);
                int i = (int) sensorEvent.values[2];
                long j4 = elapsedRealtime - gestureLauncherService2.mCameraGestureLastEventTime;
                long j5 = j2 - gestureLauncherService2.mCameraGestureSensor1LastOnTimeMs;
                long j6 = j3 - gestureLauncherService2.mCameraGestureSensor2LastOnTimeMs;
                int i2 = i - gestureLauncherService2.mCameraLaunchLastEventExtra;
                if (j4 < 0 || j5 < 0 || j6 < 0) {
                    return;
                }
                EventLog.writeEvent(
                        40100,
                        Long.valueOf(j4),
                        Long.valueOf(j5),
                        Long.valueOf(j6),
                        Integer.valueOf(i2));
                GestureLauncherService gestureLauncherService3 = GestureLauncherService.this;
                gestureLauncherService3.mCameraGestureLastEventTime = elapsedRealtime;
                gestureLauncherService3.mCameraGestureSensor1LastOnTimeMs = j2;
                gestureLauncherService3.mCameraGestureSensor2LastOnTimeMs = j3;
                gestureLauncherService3.mCameraLaunchLastEventExtra = i;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum GestureLauncherEvent implements UiEventLogger.UiEventEnum {
        GESTURE_CAMERA_LIFT("GESTURE_CAMERA_LIFT"),
        GESTURE_CAMERA_WIGGLE("GESTURE_CAMERA_WIGGLE"),
        /* JADX INFO: Fake field, exist only in values array */
        EF31("GESTURE_CAMERA_DOUBLE_TAP_POWER"),
        /* JADX INFO: Fake field, exist only in values array */
        EF41("GESTURE_EMERGENCY_TAP_POWER");

        private final int mId;

        GestureLauncherEvent(String str) {
            this.mId = r2;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public GestureLauncherService(Context context) {
        this(context, new MetricsLogger(), new UiEventLoggerImpl());
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.GestureLauncherService$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.GestureLauncherService$2] */
    public GestureLauncherService(
            Context context, MetricsLogger metricsLogger, UiEventLogger uiEventLogger) {
        super(context);
        this.mGestureListener = new GestureEventListener();
        this.mCameraLiftTriggerListener = new CameraLiftTriggerEventListener();
        this.mCameraGestureOnTimeMs = 0L;
        this.mCameraGestureLastEventTime = 0L;
        this.mCameraGestureSensor1LastOnTimeMs = 0L;
        this.mCameraGestureSensor2LastOnTimeMs = 0L;
        this.mCameraLaunchLastEventExtra = 0;
        this.mUserReceiver =
                new BroadcastReceiver() { // from class: com.android.server.GestureLauncherService.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                            GestureLauncherService.this.mUserId =
                                    intent.getIntExtra("android.intent.extra.user_handle", 0);
                            GestureLauncherService.this
                                    .mContext
                                    .getContentResolver()
                                    .unregisterContentObserver(
                                            GestureLauncherService.this.mSettingObserver);
                            GestureLauncherService.this.registerContentObservers();
                            GestureLauncherService.this.updateCameraRegistered();
                            GestureLauncherService.this.updateCameraDoubleTapPowerEnabled();
                            GestureLauncherService.this.updateEmergencyGestureEnabled();
                            GestureLauncherService.this
                                    .updateEmergencyGesturePowerButtonCooldownPeriodMs();
                        }
                    }
                };
        this.mSettingObserver =
                new ContentObserver(
                        new Handler()) { // from class: com.android.server.GestureLauncherService.2
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri, int i) {
                        GestureLauncherService gestureLauncherService = GestureLauncherService.this;
                        if (i == gestureLauncherService.mUserId) {
                            gestureLauncherService.updateCameraRegistered();
                            GestureLauncherService.this.updateCameraDoubleTapPowerEnabled();
                            GestureLauncherService.this.updateEmergencyGestureEnabled();
                            GestureLauncherService.this
                                    .updateEmergencyGesturePowerButtonCooldownPeriodMs();
                        }
                    }
                };
        this.mContext = context;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
    }

    public static int getEmergencyGesturePowerButtonCooldownPeriodMs(Context context, int i) {
        return Math.min(
                Settings.Global.getInt(
                        context.getContentResolver(),
                        "emergency_gesture_power_button_cooldown_period_ms",
                        3000),
                5000);
    }

    public static boolean isCameraDoubleTapPowerEnabled(Resources resources) {
        return resources.getBoolean(R.bool.config_canSwitchToHeadlessSystemUser);
    }

    public static boolean isGestureLauncherEnabled(Resources resources) {
        return !(resources.getInteger(R.integer.config_defaultNotificationLedOff) == -1
                        || SystemProperties.getBoolean("gesture.disable_camera_launch", false))
                || isCameraDoubleTapPowerEnabled(resources)
                || resources.getInteger(R.integer.config_defaultNotificationLedOn) != -1
                || resources.getBoolean(R.bool.config_enableBurnInProtection);
    }

    public boolean handleCameraGesture(boolean z, int i) {
        Trace.traceBegin(64L, "GestureLauncher:handleCameraGesture");
        try {
            if (Settings.Secure.getIntForUser(
                            this.mContext.getContentResolver(), "user_setup_complete", 0, -2)
                    == 0) {
                Trace.traceEnd(64L);
                return false;
            }
            if (z) {
                this.mWakeLock.acquire(POWER_SHORT_TAP_SEQUENCE_MAX_INTERVAL_MS);
            }
            IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.onCameraLaunchGestureDetected(i);
                } catch (RemoteException unused) {
                }
            }
            Trace.traceEnd(64L);
            return true;
        } catch (Throwable th) {
            Trace.traceEnd(64L);
            throw th;
        }
    }

    public boolean handleEmergencyGesture() {
        Trace.traceBegin(64L, "GestureLauncher:handleEmergencyGesture");
        try {
            if (Settings.Secure.getIntForUser(
                            this.mContext.getContentResolver(), "user_setup_complete", 0, -2)
                    == 0) {
                return false;
            }
            IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.onEmergencyActionLaunchGestureDetected();
                } catch (RemoteException unused) {
                }
            }
            Trace.traceEnd(64L);
            return true;
        } finally {
            Trace.traceEnd(64L);
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 600 && isGestureLauncherEnabled(this.mContext.getResources())) {
            this.mWindowManagerInternal =
                    (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
            PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
            this.mPowerManager = powerManager;
            this.mWakeLock = powerManager.newWakeLock(1, "GestureLauncherService");
            updateCameraRegistered();
            updateCameraDoubleTapPowerEnabled();
            updateEmergencyGestureEnabled();
            updateEmergencyGesturePowerButtonCooldownPeriodMs();
            this.mUserId = ActivityManager.getCurrentUser();
            this.mContext.registerReceiver(
                    this.mUserReceiver, new IntentFilter("android.intent.action.USER_SWITCHED"));
            registerContentObservers();
            this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch");
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        LocalServices.addService(GestureLauncherService.class, this);
    }

    public final void registerContentObservers() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri uriFor = Settings.Secure.getUriFor("camera_gesture_disabled");
        int i = this.mUserId;
        AnonymousClass2 anonymousClass2 = this.mSettingObserver;
        contentResolver.registerContentObserver(uriFor, false, anonymousClass2, i);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("camera_double_tap_power_gesture_disabled"),
                        false,
                        anonymousClass2,
                        this.mUserId);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("camera_lift_trigger_enabled"),
                        false,
                        anonymousClass2,
                        this.mUserId);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("emergency_gesture_enabled"),
                        false,
                        anonymousClass2,
                        this.mUserId);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor(
                                "emergency_gesture_power_button_cooldown_period_ms"),
                        false,
                        anonymousClass2,
                        this.mUserId);
    }

    public void updateCameraDoubleTapPowerEnabled() {
        Context context = this.mContext;
        int i = this.mUserId;
        if (isCameraDoubleTapPowerEnabled(context.getResources())) {
            Settings.Secure.getIntForUser(
                    context.getContentResolver(), "camera_double_tap_power_gesture_disabled", 0, i);
        }
        synchronized (this) {
        }
    }

    public final void updateCameraRegistered() {
        Resources resources = this.mContext.getResources();
        Context context = this.mContext;
        int i = this.mUserId;
        boolean z =
                (context.getResources().getInteger(R.integer.config_defaultNotificationLedOff) == -1
                                || SystemProperties.getBoolean(
                                        "gesture.disable_camera_launch", false))
                        ? false
                        : true;
        GestureEventListener gestureEventListener = this.mGestureListener;
        if (z
                && Settings.Secure.getIntForUser(
                                context.getContentResolver(), "camera_gesture_disabled", 0, i)
                        == 0) {
            if (!this.mCameraLaunchRegistered) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                this.mCameraGestureOnTimeMs = elapsedRealtime;
                this.mCameraGestureLastEventTime = elapsedRealtime;
                SensorManager sensorManager =
                        (SensorManager) this.mContext.getSystemService("sensor");
                int integer = resources.getInteger(R.integer.config_defaultNotificationLedOff);
                if (integer != -1) {
                    this.mCameraLaunchRegistered = false;
                    String string =
                            resources.getString(R.string.conversation_title_fallback_group_chat);
                    Sensor defaultSensor = sensorManager.getDefaultSensor(integer, true);
                    this.mCameraLaunchSensor = defaultSensor;
                    if (defaultSensor != null) {
                        if (!string.equals(defaultSensor.getStringType())) {
                            throw new RuntimeException(
                                    XmlUtils$$ExternalSyntheticOutline0.m(
                                            "Wrong configuration. Sensor type and sensor string"
                                                + " type don't match: ",
                                            string,
                                            " in resources, ",
                                            this.mCameraLaunchSensor.getStringType(),
                                            " in the sensor."));
                        }
                        this.mCameraLaunchRegistered =
                                sensorManager.registerListener(
                                        gestureEventListener, this.mCameraLaunchSensor, 0);
                    }
                }
            }
        } else if (this.mCameraLaunchRegistered) {
            this.mCameraLaunchRegistered = false;
            this.mCameraGestureOnTimeMs = 0L;
            this.mCameraGestureLastEventTime = 0L;
            this.mCameraGestureSensor1LastOnTimeMs = 0L;
            this.mCameraGestureSensor2LastOnTimeMs = 0L;
            this.mCameraLaunchLastEventExtra = 0;
            ((SensorManager) this.mContext.getSystemService("sensor"))
                    .unregisterListener(gestureEventListener);
        }
        Context context2 = this.mContext;
        int i2 = this.mUserId;
        boolean z2 =
                context2.getResources().getInteger(R.integer.config_defaultNotificationLedOn) != -1;
        CameraLiftTriggerEventListener cameraLiftTriggerEventListener =
                this.mCameraLiftTriggerListener;
        if (!z2
                || Settings.Secure.getIntForUser(
                                context2.getContentResolver(), "camera_lift_trigger_enabled", 1, i2)
                        == 0) {
            if (this.mCameraLiftRegistered) {
                this.mCameraLiftRegistered = false;
                ((SensorManager) this.mContext.getSystemService("sensor"))
                        .cancelTriggerSensor(
                                cameraLiftTriggerEventListener, this.mCameraLiftTriggerSensor);
                return;
            }
            return;
        }
        if (this.mCameraLiftRegistered) {
            return;
        }
        SensorManager sensorManager2 = (SensorManager) this.mContext.getSystemService("sensor");
        int integer2 = resources.getInteger(R.integer.config_defaultNotificationLedOn);
        if (integer2 != -1) {
            this.mCameraLiftRegistered = false;
            String string2 = resources.getString(R.string.conversation_title_fallback_one_to_one);
            Sensor defaultSensor2 = sensorManager2.getDefaultSensor(integer2, true);
            this.mCameraLiftTriggerSensor = defaultSensor2;
            if (defaultSensor2 != null) {
                if (!string2.equals(defaultSensor2.getStringType())) {
                    throw new RuntimeException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "Wrong configuration. Sensor type and sensor string type don't"
                                        + " match: ",
                                    string2,
                                    " in resources, ",
                                    this.mCameraLiftTriggerSensor.getStringType(),
                                    " in the sensor."));
                }
                this.mCameraLiftRegistered =
                        sensorManager2.requestTriggerSensor(
                                cameraLiftTriggerEventListener, this.mCameraLiftTriggerSensor);
            }
        }
    }

    public void updateEmergencyGestureEnabled() {
        Context context = this.mContext;
        int i = this.mUserId;
        if (context.getResources().getBoolean(R.bool.config_enableBurnInProtection)) {
            Settings.Secure.getIntForUser(
                    context.getContentResolver(),
                    "emergency_gesture_enabled",
                    context.getResources().getBoolean(R.bool.config_defaultWindowFeatureContextMenu)
                            ? 1
                            : 0,
                    i);
        }
        synchronized (this) {
        }
    }

    public void updateEmergencyGesturePowerButtonCooldownPeriodMs() {
        getEmergencyGesturePowerButtonCooldownPeriodMs(this.mContext, this.mUserId);
        synchronized (this) {
        }
    }
}
