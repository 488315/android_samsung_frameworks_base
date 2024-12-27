package com.android.server.display;

import android.app.ActivityTaskManager;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.display.BrightnessChangeEvent;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.display.DisplayedContentSample;
import android.hardware.display.DisplayedContentSamplingAttributes;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserManager;
import android.provider.Settings;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.RingBuffer;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;
import com.android.server.display.AmbientBrightnessStatsTracker;
import com.android.server.display.utils.DebugUtils;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BrightnessTracker {
    public AmbientBrightnessStatsTracker mAmbientBrightnessStatsTracker;
    public Receiver mBroadcastReceiver;
    public boolean mColorSamplingEnabled;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public DisplayListener mDisplayListener;
    public boolean mEventsDirty;
    public float mFrameRate;
    public Sensor mLightSensor;
    public int mNoFramesToSample;
    public SensorListener mSensorListener;
    public boolean mSensorRegistered;
    public SettingsObserver mSettingsObserver;
    public boolean mStarted;
    public final UserManager mUserManager;
    public volatile boolean mWriteBrightnessTrackerStateScheduled;
    public static final boolean DEBUG = DebugUtils.isDebuggable("BrightnessTracker");
    public static final long MAX_EVENT_AGE = TimeUnit.DAYS.toMillis(30);
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public static final long COLOR_SAMPLE_DURATION = TimeUnit.SECONDS.toSeconds(10);
    public final Object mEventsLock = new Object();
    public RingBuffer mEvents = new RingBuffer(BrightnessChangeEvent.class, 100);
    public boolean mShouldCollectColorSample = false;
    public int mCurrentUserId = -10000;
    public final Object mDataCollectionLock = new Object();
    public float mLastBatteryLevel = Float.NaN;
    public float mLastBrightness = -1.0f;
    public final Injector mInjector = new Injector();
    public final TrackerHandler mBgHandler = new TrackerHandler(BackgroundThread.getHandler().getLooper());

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BrightnessChangeValues {
        public final float brightness;
        public final boolean isDefaultBrightnessConfig;
        public final long[] luxTimestamps;
        public final float[] luxValues;
        public final float powerBrightnessFactor;
        public final long timestamp;
        public final String uniqueDisplayId;
        public final boolean wasShortTermModelActive;

        public BrightnessChangeValues(float f, float f2, boolean z, boolean z2, long j, String str, float[] fArr, long[] jArr) {
            this.brightness = f;
            this.powerBrightnessFactor = f2;
            this.wasShortTermModelActive = z;
            this.isDefaultBrightnessConfig = z2;
            this.timestamp = j;
            this.uniqueDisplayId = str;
            this.luxValues = fArr;
            this.luxTimestamps = jArr;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayListener implements DisplayManager.DisplayListener {
        public DisplayListener() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            if (i == 0) {
                BrightnessTracker brightnessTracker = BrightnessTracker.this;
                if (brightnessTracker.mColorSamplingEnabled) {
                    Context context = brightnessTracker.mContext;
                    brightnessTracker.mInjector.getClass();
                    if (((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(0).getRefreshRate() != brightnessTracker.mFrameRate) {
                        brightnessTracker.disableColorSampling();
                        brightnessTracker.enableColorSampling();
                    }
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            boolean z = BrightnessTracker.DEBUG;
            if (z) {
                Slog.d("BrightnessTracker", "Received " + intent.getAction());
            }
            String action = intent.getAction();
            if (!"android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                if (!"android.intent.action.BATTERY_CHANGED".equals(action)) {
                    if ("android.intent.action.SCREEN_OFF".equals(action)) {
                        BrightnessTracker.this.mBgHandler.obtainMessage(2).sendToTarget();
                        return;
                    } else {
                        if ("android.intent.action.SCREEN_ON".equals(action)) {
                            BrightnessTracker.this.mBgHandler.obtainMessage(3).sendToTarget();
                            return;
                        }
                        return;
                    }
                }
                int intExtra = intent.getIntExtra("level", -1);
                int intExtra2 = intent.getIntExtra("scale", 0);
                if (intExtra == -1 || intExtra2 == 0) {
                    return;
                }
                BrightnessTracker brightnessTracker = BrightnessTracker.this;
                synchronized (brightnessTracker.mDataCollectionLock) {
                    brightnessTracker.mLastBatteryLevel = intExtra / intExtra2;
                }
                return;
            }
            BrightnessTracker brightnessTracker2 = BrightnessTracker.this;
            synchronized (brightnessTracker2.mDataCollectionLock) {
                try {
                    if (brightnessTracker2.mStarted) {
                        if (z) {
                            Slog.d("BrightnessTracker", "Stop");
                        }
                        brightnessTracker2.mBgHandler.removeMessages(0);
                        brightnessTracker2.stopSensorListener();
                        Injector injector = brightnessTracker2.mInjector;
                        Context context2 = brightnessTracker2.mContext;
                        SensorListener sensorListener = brightnessTracker2.mSensorListener;
                        injector.getClass();
                        ((SensorManager) context2.getSystemService(SensorManager.class)).unregisterListener(sensorListener);
                        Injector injector2 = brightnessTracker2.mInjector;
                        Context context3 = brightnessTracker2.mContext;
                        SettingsObserver settingsObserver = brightnessTracker2.mSettingsObserver;
                        injector2.getClass();
                        context3.getContentResolver().unregisterContentObserver(settingsObserver);
                        Injector injector3 = brightnessTracker2.mInjector;
                        Context context4 = brightnessTracker2.mContext;
                        Receiver receiver = brightnessTracker2.mBroadcastReceiver;
                        injector3.getClass();
                        context4.unregisterReceiver(receiver);
                        Injector injector4 = brightnessTracker2.mInjector;
                        Context context5 = brightnessTracker2.mContext;
                        injector4.getClass();
                        int i = BrightnessIdleJob.$r8$clinit;
                        ((JobScheduler) context5.getSystemService(JobScheduler.class)).cancel(3923512);
                        synchronized (brightnessTracker2.mDataCollectionLock) {
                            brightnessTracker2.mStarted = false;
                        }
                        brightnessTracker2.disableColorSampling();
                    }
                } finally {
                }
            }
            BrightnessTracker brightnessTracker3 = BrightnessTracker.this;
            if (brightnessTracker3.mWriteBrightnessTrackerStateScheduled) {
                return;
            }
            brightnessTracker3.mBgHandler.post(new BrightnessTracker$$ExternalSyntheticLambda1(brightnessTracker3));
            brightnessTracker3.mWriteBrightnessTrackerStateScheduled = true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SensorListener implements SensorEventListener {
        public SensorListener() {
        }

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
            BrightnessTracker brightnessTracker = BrightnessTracker.this;
            AmbientBrightnessStatsTracker ambientBrightnessStatsTracker = brightnessTracker.mAmbientBrightnessStatsTracker;
            int i = brightnessTracker.mCurrentUserId;
            float f = sensorEvent.values[0];
            synchronized (ambientBrightnessStatsTracker) {
                try {
                    if (ambientBrightnessStatsTracker.mTimer.started) {
                        int i2 = ambientBrightnessStatsTracker.mCurrentUserId;
                        if (i == i2) {
                            AmbientBrightnessStatsTracker.AmbientBrightnessStats ambientBrightnessStats = ambientBrightnessStatsTracker.mAmbientBrightnessStats;
                            ambientBrightnessStatsTracker.mInjector.getClass();
                            ambientBrightnessStats.log(i2, LocalDate.now(), ambientBrightnessStatsTracker.mCurrentAmbientBrightness, ambientBrightnessStatsTracker.mTimer.started ? (float) ((r5.clock.elapsedTimeMillis() - r5.startTimeMillis) / 1000.0d) : FullScreenMagnificationGestureHandler.MAX_SCALE);
                        } else {
                            if (AmbientBrightnessStatsTracker.DEBUG) {
                                Slog.v("AmbientBrightnessStatsTracker", "User switched since last sensor event.");
                            }
                            ambientBrightnessStatsTracker.mCurrentUserId = i;
                        }
                        AmbientBrightnessStatsTracker.Timer timer = ambientBrightnessStatsTracker.mTimer;
                        timer.started = false;
                        timer.startTimeMillis = timer.clock.elapsedTimeMillis();
                        timer.started = true;
                        ambientBrightnessStatsTracker.mCurrentAmbientBrightness = f;
                    } else if (AmbientBrightnessStatsTracker.DEBUG) {
                        Slog.e("AmbientBrightnessStatsTracker", "Timer not running while trying to add brightness stats.");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (BrightnessTracker.DEBUG) {
                Slog.v("BrightnessTracker", "settings change " + uri);
            }
            BrightnessTracker brightnessTracker = BrightnessTracker.this;
            Injector injector = brightnessTracker.mInjector;
            ContentResolver contentResolver = brightnessTracker.mContentResolver;
            injector.getClass();
            if (Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2) == 1) {
                BrightnessTracker.this.mBgHandler.obtainMessage(3).sendToTarget();
            } else {
                BrightnessTracker.this.mBgHandler.obtainMessage(2).sendToTarget();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TrackerHandler extends Handler {
        public TrackerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                BrightnessTracker.m436$$Nest$mbackgroundStart(BrightnessTracker.this, ((Float) message.obj).floatValue());
                return;
            }
            if (i != 1) {
                if (i == 2) {
                    BrightnessTracker.this.stopSensorListener();
                    BrightnessTracker.this.disableColorSampling();
                    return;
                }
                if (i == 3) {
                    BrightnessTracker.this.startSensorListener();
                    BrightnessTracker.this.enableColorSampling();
                    return;
                }
                if (i != 4) {
                    if (i != 5) {
                        return;
                    }
                    BrightnessTracker brightnessTracker = BrightnessTracker.this;
                    Sensor sensor = (Sensor) message.obj;
                    if (brightnessTracker.mLightSensor != sensor) {
                        brightnessTracker.mLightSensor = sensor;
                        brightnessTracker.stopSensorListener();
                        brightnessTracker.startSensorListener();
                        return;
                    }
                    return;
                }
                BrightnessTracker.this.mShouldCollectColorSample = ((Boolean) message.obj).booleanValue();
                BrightnessTracker brightnessTracker2 = BrightnessTracker.this;
                boolean z = brightnessTracker2.mShouldCollectColorSample;
                if (z && !brightnessTracker2.mColorSamplingEnabled) {
                    brightnessTracker2.enableColorSampling();
                    return;
                } else {
                    if (z || !brightnessTracker2.mColorSamplingEnabled) {
                        return;
                    }
                    brightnessTracker2.disableColorSampling();
                    return;
                }
            }
            BrightnessChangeValues brightnessChangeValues = (BrightnessChangeValues) message.obj;
            boolean z2 = message.arg1 == 1;
            BrightnessTracker brightnessTracker3 = BrightnessTracker.this;
            float f = brightnessChangeValues.brightness;
            float f2 = brightnessChangeValues.powerBrightnessFactor;
            boolean z3 = brightnessChangeValues.wasShortTermModelActive;
            boolean z4 = brightnessChangeValues.isDefaultBrightnessConfig;
            long j = brightnessChangeValues.timestamp;
            String str = brightnessChangeValues.uniqueDisplayId;
            float[] fArr = brightnessChangeValues.luxValues;
            long[] jArr = brightnessChangeValues.luxTimestamps;
            synchronized (brightnessTracker3.mDataCollectionLock) {
                try {
                    if (brightnessTracker3.mStarted) {
                        float f3 = brightnessTracker3.mLastBrightness;
                        brightnessTracker3.mLastBrightness = f;
                        if (z2) {
                            BrightnessChangeEvent.Builder builder = new BrightnessChangeEvent.Builder();
                            builder.setBrightness(f);
                            builder.setTimeStamp(j);
                            builder.setPowerBrightnessFactor(f2);
                            builder.setUserBrightnessPoint(z3);
                            builder.setIsDefaultBrightnessConfig(z4);
                            builder.setUniqueDisplayId(str);
                            if (fArr.length == 0) {
                                return;
                            }
                            long[] jArr2 = new long[jArr.length];
                            brightnessTracker3.mInjector.getClass();
                            long currentTimeMillis = System.currentTimeMillis();
                            brightnessTracker3.mInjector.getClass();
                            long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
                            for (int i2 = 0; i2 < jArr.length; i2++) {
                                jArr2[i2] = currentTimeMillis - (TimeUnit.NANOSECONDS.toMillis(elapsedRealtimeNanos) - jArr[i2]);
                            }
                            builder.setLuxValues(fArr);
                            builder.setLuxTimestamps(jArr2);
                            builder.setBatteryLevel(brightnessTracker3.mLastBatteryLevel);
                            builder.setLastBrightness(f3);
                            try {
                                brightnessTracker3.mInjector.getClass();
                                ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = ActivityTaskManager.getService().getFocusedRootTaskInfo();
                                if (focusedRootTaskInfo != null && focusedRootTaskInfo.topActivity != null) {
                                    builder.setUserId(focusedRootTaskInfo.userId);
                                    builder.setPackageName(focusedRootTaskInfo.topActivity.getPackageName());
                                    Injector injector = brightnessTracker3.mInjector;
                                    Context context = brightnessTracker3.mContext;
                                    injector.getClass();
                                    builder.setNightMode(((ColorDisplayManager) context.getSystemService(ColorDisplayManager.class)).isNightDisplayActivated());
                                    Injector injector2 = brightnessTracker3.mInjector;
                                    Context context2 = brightnessTracker3.mContext;
                                    injector2.getClass();
                                    builder.setColorTemperature(((ColorDisplayManager) context2.getSystemService(ColorDisplayManager.class)).getNightDisplayColorTemperature());
                                    Injector injector3 = brightnessTracker3.mInjector;
                                    Context context3 = brightnessTracker3.mContext;
                                    injector3.getClass();
                                    builder.setReduceBrightColors(((ColorDisplayManager) context3.getSystemService(ColorDisplayManager.class)).isReduceBrightColorsActivated());
                                    Injector injector4 = brightnessTracker3.mInjector;
                                    Context context4 = brightnessTracker3.mContext;
                                    injector4.getClass();
                                    builder.setReduceBrightColorsStrength(((ColorDisplayManager) context4.getSystemService(ColorDisplayManager.class)).getReduceBrightColorsStrength());
                                    Injector injector5 = brightnessTracker3.mInjector;
                                    Context context5 = brightnessTracker3.mContext;
                                    injector5.getClass();
                                    builder.setReduceBrightColorsOffset(((ColorDisplayManager) context5.getSystemService(ColorDisplayManager.class)).getReduceBrightColorsOffsetFactor() * f);
                                    if (brightnessTracker3.mColorSamplingEnabled) {
                                        Injector injector6 = brightnessTracker3.mInjector;
                                        int i3 = brightnessTracker3.mNoFramesToSample;
                                        injector6.getClass();
                                        DisplayedContentSample displayedContentSample = ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class)).getDisplayedContentSample(0, i3, 0L);
                                        if (displayedContentSample != null) {
                                            DisplayedContentSample.ColorComponent colorComponent = DisplayedContentSample.ColorComponent.CHANNEL2;
                                            if (displayedContentSample.getSampleComponent(colorComponent) != null) {
                                                builder.setColorValues(displayedContentSample.getSampleComponent(colorComponent), Math.round((displayedContentSample.getNumFrames() / brightnessTracker3.mFrameRate) * 1000.0f));
                                            }
                                        }
                                    }
                                    BrightnessChangeEvent build = builder.build();
                                    if (BrightnessTracker.DEBUG) {
                                        Slog.d("BrightnessTracker", "Event: " + build.toString());
                                    }
                                    synchronized (brightnessTracker3.mEventsLock) {
                                        brightnessTracker3.mEventsDirty = true;
                                        brightnessTracker3.mEvents.append(build);
                                    }
                                } else if (BrightnessTracker.DEBUG) {
                                    Slog.d("BrightnessTracker", "Ignoring event due to null focusedTask.");
                                }
                            } catch (RemoteException unused) {
                            }
                        }
                    }
                } finally {
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00f2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x006e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.lang.AutoCloseable] */
    /* renamed from: -$$Nest$mbackgroundStart, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m436$$Nest$mbackgroundStart(com.android.server.display.BrightnessTracker r7, float r8) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.BrightnessTracker.m436$$Nest$mbackgroundStart(com.android.server.display.BrightnessTracker, float):void");
    }

    public BrightnessTracker(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    public final void disableColorSampling() {
        if (this.mColorSamplingEnabled) {
            this.mInjector.getClass();
            ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class)).setDisplayedContentSamplingEnabled(0, false, 4, 0);
            this.mColorSamplingEnabled = false;
            DisplayListener displayListener = this.mDisplayListener;
            if (displayListener != null) {
                ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).unregisterDisplayListener(displayListener);
                this.mDisplayListener = null;
            }
            if (DEBUG) {
                Slog.i("BrightnessTracker", "turning off color sampling");
            }
        }
    }

    public final void dump(final PrintWriter printWriter) {
        printWriter.println("BrightnessTracker state:");
        synchronized (this.mDataCollectionLock) {
            printWriter.println("  mStarted=" + this.mStarted);
            printWriter.println("  mLightSensor=" + this.mLightSensor);
            printWriter.println("  mLastBatteryLevel=" + this.mLastBatteryLevel);
            printWriter.println("  mLastBrightness=" + this.mLastBrightness);
        }
        synchronized (this.mEventsLock) {
            try {
                printWriter.println("  mEventsDirty=" + this.mEventsDirty);
                printWriter.println("  mEvents.size=" + this.mEvents.size());
                BrightnessChangeEvent[] brightnessChangeEventArr = (BrightnessChangeEvent[]) this.mEvents.toArray();
                for (int i = 0; i < brightnessChangeEventArr.length; i++) {
                    printWriter.print("    " + FORMAT.format(new Date(brightnessChangeEventArr[i].timeStamp)));
                    printWriter.print(", userId=" + brightnessChangeEventArr[i].userId);
                    printWriter.print(", " + brightnessChangeEventArr[i].lastBrightness + "->" + brightnessChangeEventArr[i].brightness);
                    StringBuilder sb = new StringBuilder();
                    sb.append(", isUserSetBrightness=");
                    sb.append(brightnessChangeEventArr[i].isUserSetBrightness);
                    printWriter.print(sb.toString());
                    printWriter.print(", powerBrightnessFactor=" + brightnessChangeEventArr[i].powerBrightnessFactor);
                    printWriter.print(", isDefaultBrightnessConfig=" + brightnessChangeEventArr[i].isDefaultBrightnessConfig);
                    printWriter.print(", recent lux values=");
                    printWriter.print(" {");
                    for (int i2 = 0; i2 < brightnessChangeEventArr[i].luxValues.length; i2++) {
                        if (i2 != 0) {
                            printWriter.print(", ");
                        }
                        printWriter.print("(" + brightnessChangeEventArr[i].luxValues[i2] + "," + brightnessChangeEventArr[i].luxTimestamps[i2] + ")");
                    }
                    printWriter.println("}");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mWriteBrightnessTrackerStateScheduled="), this.mWriteBrightnessTrackerStateScheduled, printWriter);
        this.mBgHandler.runWithScissors(new Runnable() { // from class: com.android.server.display.BrightnessTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessTracker brightnessTracker = BrightnessTracker.this;
                PrintWriter printWriter2 = printWriter;
                AggressivePolicyHandler$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mSensorRegistered="), brightnessTracker.mSensorRegistered, printWriter2, "  mColorSamplingEnabled="), brightnessTracker.mColorSamplingEnabled, printWriter2, "  mNoFramesToSample="), brightnessTracker.mNoFramesToSample, printWriter2, "  mFrameRate="), brightnessTracker.mFrameRate, printWriter2);
            }
        }, 1000L);
        if (this.mAmbientBrightnessStatsTracker != null) {
            printWriter.println();
            AmbientBrightnessStatsTracker ambientBrightnessStatsTracker = this.mAmbientBrightnessStatsTracker;
            synchronized (ambientBrightnessStatsTracker) {
                printWriter.println("AmbientBrightnessStats:");
                printWriter.print(ambientBrightnessStatsTracker.mAmbientBrightnessStats);
            }
        }
    }

    public final void enableColorSampling() {
        ContentResolver contentResolver = this.mContentResolver;
        this.mInjector.getClass();
        if (Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2) == 1 && ((PowerManager) this.mContext.getSystemService(PowerManager.class)).isInteractive() && !this.mColorSamplingEnabled && this.mShouldCollectColorSample) {
            float refreshRate = ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(0).getRefreshRate();
            this.mFrameRate = refreshRate;
            if (refreshRate <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                Slog.wtf("BrightnessTracker", "Default display has a zero or negative framerate.");
                return;
            }
            this.mNoFramesToSample = (int) (refreshRate * COLOR_SAMPLE_DURATION);
            DisplayedContentSamplingAttributes displayedContentSamplingAttributes = ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class)).getDisplayedContentSamplingAttributes(0);
            boolean z = DEBUG;
            if (z && displayedContentSamplingAttributes != null) {
                Slog.d("BrightnessTracker", "Color sampling mask=0x" + Integer.toHexString(displayedContentSamplingAttributes.getComponentMask()) + " dataSpace=0x" + Integer.toHexString(displayedContentSamplingAttributes.getDataspace()) + " pixelFormat=0x" + Integer.toHexString(displayedContentSamplingAttributes.getPixelFormat()));
            }
            if (displayedContentSamplingAttributes != null && displayedContentSamplingAttributes.getPixelFormat() == 55 && (displayedContentSamplingAttributes.getComponentMask() & 4) != 0) {
                this.mColorSamplingEnabled = ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class)).setDisplayedContentSamplingEnabled(0, true, 4, this.mNoFramesToSample);
                if (z) {
                    StringBuilder sb = new StringBuilder("turning on color sampling for ");
                    sb.append(this.mNoFramesToSample);
                    sb.append(" frames, success=");
                    HeimdAllFsService$$ExternalSyntheticOutline0.m("BrightnessTracker", sb, this.mColorSamplingEnabled);
                }
            }
            if (this.mColorSamplingEnabled && this.mDisplayListener == null) {
                DisplayListener displayListener = new DisplayListener();
                this.mDisplayListener = displayListener;
                ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).registerDisplayListener(displayListener, this.mBgHandler);
            }
        }
    }

    public final AtomicFile getFileWithLegacyFallback(String str) {
        this.mInjector.getClass();
        AtomicFile atomicFile = new AtomicFile(new File(Environment.getDataSystemDirectory(), str));
        if (!atomicFile.exists()) {
            AtomicFile atomicFile2 = new AtomicFile(new File(Environment.getDataSystemDeDirectory(), str));
            if (atomicFile2.exists()) {
                BootReceiver$$ExternalSyntheticOutline0.m58m("Reading ", str, " from old location", "BrightnessTracker");
                return atomicFile2;
            }
        }
        return atomicFile;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0048, code lost:
    
        if (r9 != 4) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0056, code lost:
    
        if ("event".equals(r4.getName()) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0058, code lost:
    
        r9 = new android.hardware.display.BrightnessChangeEvent.Builder();
        r9.setBrightness(r4.getAttributeFloat((java.lang.String) null, "nits"));
        r9.setTimeStamp(r4.getAttributeLong((java.lang.String) null, "timestamp"));
        r9.setPackageName(r4.getAttributeValue((java.lang.String) null, "packageName"));
        r10 = r19.mUserManager;
        r12 = r4.getAttributeInt((java.lang.String) null, "user");
        r0.getClass();
        r9.setUserId(r10.getUserHandle(r12));
        r10 = r4.getAttributeValue((java.lang.String) null, "uniqueDisplayId");
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0096, code lost:
    
        if (r10 != null) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0098, code lost:
    
        r10 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x009a, code lost:
    
        r9.setUniqueDisplayId(r10);
        r9.setBatteryLevel(r4.getAttributeFloat((java.lang.String) null, "batteryLevel"));
        r9.setNightMode(r4.getAttributeBoolean((java.lang.String) null, "nightMode"));
        r9.setColorTemperature(r4.getAttributeInt((java.lang.String) null, "colorTemperature"));
        r9.setReduceBrightColors(r4.getAttributeBoolean((java.lang.String) null, "reduceBrightColors"));
        r9.setReduceBrightColorsStrength(r4.getAttributeInt((java.lang.String) null, "reduceBrightColorsStrength"));
        r9.setReduceBrightColorsOffset(r4.getAttributeFloat((java.lang.String) null, "reduceBrightColorsOffset"));
        r9.setLastBrightness(r4.getAttributeFloat((java.lang.String) null, "lastNits"));
        r10 = r4.getAttributeValue((java.lang.String) null, "lux");
        r12 = r4.getAttributeValue((java.lang.String) null, "luxTimestamps");
        r10 = r10.split(",");
        r12 = r12.split(",");
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00fb, code lost:
    
        if (r10.length == r12.length) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ff, code lost:
    
        r13 = r10.length;
        r14 = new float[r13];
        r15 = new long[r10.length];
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0106, code lost:
    
        if (r6 >= r13) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0108, code lost:
    
        r14[r6] = java.lang.Float.parseFloat(r10[r6]);
        r15[r6] = java.lang.Long.parseLong(r12[r6]);
        r6 = r6 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x011b, code lost:
    
        r9.setLuxValues(r14);
        r9.setLuxTimestamps(r15);
        r9.setIsDefaultBrightnessConfig(r4.getAttributeBoolean((java.lang.String) null, "defaultConfig", false));
        r9.setPowerBrightnessFactor(r4.getAttributeFloat((java.lang.String) null, "powerSaveFactor", 1.0f));
        r10 = 0;
        r9.setUserBrightnessPoint(r4.getAttributeBoolean((java.lang.String) null, "userPoint", false));
        r14 = r4.getAttributeLong((java.lang.String) null, "colorSampleDuration", -1);
        r6 = r4.getAttributeValue((java.lang.String) null, "colorValueBuckets");
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0155, code lost:
    
        if (r14 == (-1)) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0157, code lost:
    
        if (r6 == null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0159, code lost:
    
        r6 = r6.split(",");
        r11 = r6.length;
        r12 = new long[r11];
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0160, code lost:
    
        if (r10 >= r11) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0162, code lost:
    
        r12[r10] = java.lang.Long.parseLong(r6[r10]);
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x016d, code lost:
    
        r9.setColorValues(r12, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0170, code lost:
    
        r6 = r9.build();
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0176, code lost:
    
        if (com.android.server.display.BrightnessTracker.DEBUG == false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0178, code lost:
    
        android.util.Slog.i("BrightnessTracker", "Read event " + r6.brightness + " " + r6.packageName);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x019b, code lost:
    
        if (r6.userId == (-1)) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01a1, code lost:
    
        if (r6.timeStamp <= r7) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01a6, code lost:
    
        if (r6.luxValues.length <= 0) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x01a8, code lost:
    
        r19.mEvents.append(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01ad, code lost:
    
        r6 = 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void readEventsLocked(java.io.InputStream r20) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 481
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.BrightnessTracker.readEventsLocked(java.io.InputStream):void");
    }

    public final void startSensorListener() {
        if (this.mSensorRegistered || this.mLightSensor == null || this.mAmbientBrightnessStatsTracker == null) {
            return;
        }
        Injector injector = this.mInjector;
        Context context = this.mContext;
        injector.getClass();
        if (((PowerManager) context.getSystemService(PowerManager.class)).isInteractive()) {
            Injector injector2 = this.mInjector;
            ContentResolver contentResolver = this.mContentResolver;
            injector2.getClass();
            if (Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2) == 1) {
                AmbientBrightnessStatsTracker ambientBrightnessStatsTracker = this.mAmbientBrightnessStatsTracker;
                synchronized (ambientBrightnessStatsTracker) {
                    AmbientBrightnessStatsTracker.Timer timer = ambientBrightnessStatsTracker.mTimer;
                    timer.started = false;
                    timer.startTimeMillis = timer.clock.elapsedTimeMillis();
                    timer.started = true;
                }
                this.mSensorRegistered = true;
                Injector injector3 = this.mInjector;
                Context context2 = this.mContext;
                SensorListener sensorListener = this.mSensorListener;
                Sensor sensor = this.mLightSensor;
                injector3.getClass();
                ((SensorManager) context2.getSystemService(SensorManager.class)).registerListener(sensorListener, sensor, 3, BackgroundThread.getHandler());
            }
        }
    }

    public final void stopSensorListener() {
        if (this.mSensorRegistered) {
            AmbientBrightnessStatsTracker ambientBrightnessStatsTracker = this.mAmbientBrightnessStatsTracker;
            synchronized (ambientBrightnessStatsTracker) {
                try {
                    if (ambientBrightnessStatsTracker.mTimer.started) {
                        AmbientBrightnessStatsTracker.AmbientBrightnessStats ambientBrightnessStats = ambientBrightnessStatsTracker.mAmbientBrightnessStats;
                        int i = ambientBrightnessStatsTracker.mCurrentUserId;
                        ambientBrightnessStatsTracker.mInjector.getClass();
                        ambientBrightnessStats.log(i, LocalDate.now(), ambientBrightnessStatsTracker.mCurrentAmbientBrightness, ambientBrightnessStatsTracker.mTimer.started ? (float) ((r5.clock.elapsedTimeMillis() - r5.startTimeMillis) / 1000.0d) : FullScreenMagnificationGestureHandler.MAX_SCALE);
                    }
                    ambientBrightnessStatsTracker.mTimer.started = false;
                    ambientBrightnessStatsTracker.mCurrentAmbientBrightness = -1.0f;
                } catch (Throwable th) {
                    throw th;
                }
            }
            Injector injector = this.mInjector;
            Context context = this.mContext;
            SensorListener sensorListener = this.mSensorListener;
            injector.getClass();
            ((SensorManager) context.getSystemService(SensorManager.class)).unregisterListener(sensorListener);
            this.mSensorRegistered = false;
        }
    }

    public void writeEventsLocked(OutputStream outputStream) throws IOException {
        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(outputStream);
        resolveSerializer.startDocument((String) null, Boolean.TRUE);
        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        resolveSerializer.startTag((String) null, "events");
        BrightnessChangeEvent[] brightnessChangeEventArr = (BrightnessChangeEvent[]) this.mEvents.toArray();
        this.mEvents.clear();
        if (DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Writing events "), brightnessChangeEventArr.length, "BrightnessTracker");
        }
        Injector injector = this.mInjector;
        injector.getClass();
        long currentTimeMillis = System.currentTimeMillis() - MAX_EVENT_AGE;
        for (int i = 0; i < brightnessChangeEventArr.length; i++) {
            UserManager userManager = this.mUserManager;
            int i2 = brightnessChangeEventArr[i].userId;
            injector.getClass();
            int userSerialNumber = userManager.getUserSerialNumber(i2);
            if (userSerialNumber != -1) {
                BrightnessChangeEvent brightnessChangeEvent = brightnessChangeEventArr[i];
                if (brightnessChangeEvent.timeStamp > currentTimeMillis) {
                    this.mEvents.append(brightnessChangeEvent);
                    resolveSerializer.startTag((String) null, "event");
                    resolveSerializer.attributeFloat((String) null, "nits", brightnessChangeEventArr[i].brightness);
                    resolveSerializer.attributeLong((String) null, "timestamp", brightnessChangeEventArr[i].timeStamp);
                    resolveSerializer.attribute((String) null, "packageName", brightnessChangeEventArr[i].packageName);
                    resolveSerializer.attributeInt((String) null, "user", userSerialNumber);
                    String str = brightnessChangeEventArr[i].uniqueDisplayId;
                    if (str == null) {
                        str = "";
                    }
                    resolveSerializer.attribute((String) null, "uniqueDisplayId", str);
                    resolveSerializer.attributeFloat((String) null, "batteryLevel", brightnessChangeEventArr[i].batteryLevel);
                    resolveSerializer.attributeBoolean((String) null, "nightMode", brightnessChangeEventArr[i].nightMode);
                    resolveSerializer.attributeInt((String) null, "colorTemperature", brightnessChangeEventArr[i].colorTemperature);
                    resolveSerializer.attributeBoolean((String) null, "reduceBrightColors", brightnessChangeEventArr[i].reduceBrightColors);
                    resolveSerializer.attributeInt((String) null, "reduceBrightColorsStrength", brightnessChangeEventArr[i].reduceBrightColorsStrength);
                    resolveSerializer.attributeFloat((String) null, "reduceBrightColorsOffset", brightnessChangeEventArr[i].reduceBrightColorsOffset);
                    resolveSerializer.attributeFloat((String) null, "lastNits", brightnessChangeEventArr[i].lastBrightness);
                    resolveSerializer.attributeBoolean((String) null, "defaultConfig", brightnessChangeEventArr[i].isDefaultBrightnessConfig);
                    resolveSerializer.attributeFloat((String) null, "powerSaveFactor", brightnessChangeEventArr[i].powerBrightnessFactor);
                    resolveSerializer.attributeBoolean((String) null, "userPoint", brightnessChangeEventArr[i].isUserSetBrightness);
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    for (int i3 = 0; i3 < brightnessChangeEventArr[i].luxValues.length; i3++) {
                        if (i3 > 0) {
                            sb.append(',');
                            sb2.append(',');
                        }
                        sb.append(Float.toString(brightnessChangeEventArr[i].luxValues[i3]));
                        sb2.append(Long.toString(brightnessChangeEventArr[i].luxTimestamps[i3]));
                    }
                    resolveSerializer.attribute((String) null, "lux", sb.toString());
                    resolveSerializer.attribute((String) null, "luxTimestamps", sb2.toString());
                    BrightnessChangeEvent brightnessChangeEvent2 = brightnessChangeEventArr[i];
                    long[] jArr = brightnessChangeEvent2.colorValueBuckets;
                    if (jArr != null && jArr.length > 0) {
                        resolveSerializer.attributeLong((String) null, "colorSampleDuration", brightnessChangeEvent2.colorSampleDuration);
                        StringBuilder sb3 = new StringBuilder();
                        for (int i4 = 0; i4 < brightnessChangeEventArr[i].colorValueBuckets.length; i4++) {
                            if (i4 > 0) {
                                sb3.append(',');
                            }
                            sb3.append(Long.toString(brightnessChangeEventArr[i].colorValueBuckets[i4]));
                        }
                        resolveSerializer.attribute((String) null, "colorValueBuckets", sb3.toString());
                    }
                    resolveSerializer.endTag((String) null, "event");
                }
            }
        }
        resolveSerializer.endTag((String) null, "events");
        resolveSerializer.endDocument();
        outputStream.flush();
    }
}
