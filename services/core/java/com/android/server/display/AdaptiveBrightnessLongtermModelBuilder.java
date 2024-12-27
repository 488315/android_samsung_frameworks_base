package com.android.server.display;

import android.R;
import android.app.ActivityTaskManager;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.hardware.display.BrightnessChangeEvent;
import android.hardware.display.BrightnessConfiguration;
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
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.UserManager;
import android.provider.Settings;
import android.util.AtomicFile;
import android.util.MathUtils;
import android.util.Slog;
import android.util.Spline;
import android.util.Xml;

import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.RingBuffer;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;
import com.android.server.power.PowerManagerUtil;

import libcore.io.IoUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AdaptiveBrightnessLongtermModelBuilder {
    public AdaptiveBrightnessStatsTracker mAdaptiveBrightnessStatsTracker;
    public boolean mAdaptiveBrightnessStatsTrackerStarted;
    public BrightnessConfiguration mBrightnessConfiguration;
    public final BrightnessMappingStrategy mBrightnessMapper;
    public Receiver mBroadcastReceiver;
    public boolean mColorSamplingEnabled;
    public final AnonymousClass1 mComparatorAscendingForLux;
    public final AnonymousClass1 mComparatorDecsendingForWeight;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public DisplayListener mDisplayListener;
    public boolean mEventsDirty;
    public float mFrameRate;
    public final float mMaxBrightnessForNonHbmLux;
    public final int[] mMaximumBrightnessLimitCount;
    public final float[] mMaximumBrightnessLimitLux;
    public final Spline mMaximumBrightnessSpline;
    public final Spline mMinimumBrightnessSpline;
    public int mNoFramesToSample;
    public Receiver mPackageBroadcastReceiver;
    public SettingsObserver mSettingsObserver;
    public boolean mStarted;
    public boolean mTestModeEnabled;
    public final UserManager mUserManager;
    public volatile boolean mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled;
    public static final long MAX_EVENT_AGE = TimeUnit.DAYS.toMillis(30);
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public static final long COLOR_SAMPLE_DURATION = TimeUnit.SECONDS.toSeconds(10);
    public final Object mEventsLock = new Object();
    public RingBuffer mEvents = new RingBuffer(BrightnessChangeEvent.class, 240);
    public final Object mDataCollectionLock = new Object();
    public float mLastBatteryLevel = Float.NaN;
    public float mLastBrightness = -1.0f;
    public float mLastAmbientLux = -1.0f;
    public Spline mLastBrightnessSpline = null;
    public BrightnessChangeEvent mLastBrightnessChangeEvent = null;
    public final Injector mInjector = new Injector();
    public final ModelBuilderHandler mBgHandler =
            new ModelBuilderHandler(BackgroundThread.getHandler().getLooper());
    public final LightData mLastLightData = new LightData();
    public final AnonymousClass3 mUserActivityStateListener =
            new PowerManagerInternal
                    .UserActivityStateListener() { // from class:
                                                   // com.android.server.display.AdaptiveBrightnessLongtermModelBuilder.3
                public final void onChanged(int i) {
                    if (i != 1) {
                        if (i == 0) {
                            AdaptiveBrightnessLongtermModelBuilder
                                    adaptiveBrightnessLongtermModelBuilder =
                                            AdaptiveBrightnessLongtermModelBuilder.this;
                            if (adaptiveBrightnessLongtermModelBuilder
                                    .mAdaptiveBrightnessStatsTrackerStarted) {
                                AdaptiveBrightnessStatsTracker adaptiveBrightnessStatsTracker =
                                        adaptiveBrightnessLongtermModelBuilder
                                                .mAdaptiveBrightnessStatsTracker;
                                synchronized (adaptiveBrightnessStatsTracker) {
                                    AdaptiveBrightnessStatsTracker.Timer timer =
                                            adaptiveBrightnessStatsTracker.mTimer;
                                    if (timer.started && !timer.paused) {
                                        timer.totalDurationSec +=
                                                (float)
                                                        ((timer.clock.elapsedTimeMillis()
                                                                        - timer.startTimeMillis)
                                                                / 1000.0d);
                                        timer.paused = true;
                                    }
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder2 =
                            AdaptiveBrightnessLongtermModelBuilder.this;
                    if (adaptiveBrightnessLongtermModelBuilder2
                            .mAdaptiveBrightnessStatsTrackerStarted) {
                        AdaptiveBrightnessStatsTracker adaptiveBrightnessStatsTracker2 =
                                adaptiveBrightnessLongtermModelBuilder2
                                        .mAdaptiveBrightnessStatsTracker;
                        synchronized (adaptiveBrightnessStatsTracker2) {
                            AdaptiveBrightnessStatsTracker.Timer timer2 =
                                    adaptiveBrightnessStatsTracker2.mTimer;
                            boolean z = timer2.started;
                            if (z) {
                                boolean z2 = timer2.paused;
                                if (z2 && z && z2) {
                                    timer2.startTimeMillis = timer2.clock.elapsedTimeMillis();
                                    timer2.paused = false;
                                }
                            } else if (!z) {
                                timer2.startTimeMillis = timer2.clock.elapsedTimeMillis();
                                timer2.started = true;
                            }
                        }
                    }
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BrightnessChangeValues {
        public final float brightness;
        public final Spline brightnessSpline;
        public final boolean isDefaultBrightnessConfig;
        public final boolean isUserSetBrightness;
        public final float powerBrightnessFactor;
        public final long timestamp;
        public final String uniqueDisplayId;

        public BrightnessChangeValues(
                float f, float f2, boolean z, boolean z2, long j, String str, Spline spline) {
            this.brightness = f;
            this.powerBrightnessFactor = f2;
            this.isUserSetBrightness = z;
            this.isDefaultBrightnessConfig = z2;
            this.timestamp = j;
            this.uniqueDisplayId = str;
            this.brightnessSpline = spline;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayListener implements DisplayManager.DisplayListener {
        public DisplayListener() {}

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {}

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            if (i == 0) {
                AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder =
                        AdaptiveBrightnessLongtermModelBuilder.this;
                if (adaptiveBrightnessLongtermModelBuilder.mColorSamplingEnabled) {
                    Context context = adaptiveBrightnessLongtermModelBuilder.mContext;
                    adaptiveBrightnessLongtermModelBuilder.mInjector.getClass();
                    if (((DisplayManager) context.getSystemService(DisplayManager.class))
                                    .getDisplay(0)
                                    .getRefreshRate()
                            != adaptiveBrightnessLongtermModelBuilder.mFrameRate) {
                        adaptiveBrightnessLongtermModelBuilder.disableColorSampling();
                        adaptiveBrightnessLongtermModelBuilder.enableColorSampling();
                    }
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
        public static AtomicFile getFile(String str) {
            return new AtomicFile(new File(Environment.getDataSystemDirectory(), str));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LightData {
        public float lux;
        public long timestamp;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModelBuilderHandler extends Handler {
        public ModelBuilderHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            AdaptiveBrightnessWeightStats adaptiveBrightnessWeightStats;
            AdaptiveBrightnessWeightStats.WeightStat[] weightStatArr;
            AdaptiveBrightnessWeightStats.BrightnessWeights[] brightnessWeightsArr;
            AdaptiveBrightnessWeightStats.WeightStat[] weightStatArr2;
            BrightnessConfiguration brightnessConfiguration;
            int i;
            float max;
            switch (message.what) {
                case 0:
                    AdaptiveBrightnessLongtermModelBuilder.m432$$Nest$mbackgroundStart(
                            AdaptiveBrightnessLongtermModelBuilder.this,
                            ((Float) message.obj).floatValue());
                    return;
                case 1:
                    BrightnessChangeValues brightnessChangeValues =
                            (BrightnessChangeValues) message.obj;
                    AdaptiveBrightnessLongtermModelBuilder.m433$$Nest$mhandleBrightnessChanged(
                            AdaptiveBrightnessLongtermModelBuilder.this,
                            brightnessChangeValues.brightness,
                            message.arg1 == 1,
                            brightnessChangeValues.powerBrightnessFactor,
                            brightnessChangeValues.isUserSetBrightness,
                            brightnessChangeValues.isDefaultBrightnessConfig,
                            brightnessChangeValues.timestamp,
                            brightnessChangeValues.uniqueDisplayId,
                            brightnessChangeValues.brightnessSpline);
                    return;
                case 2:
                    AdaptiveBrightnessLongtermModelBuilder.this
                            .stopAdaptiveBrightnessStatsTracker();
                    AdaptiveBrightnessLongtermModelBuilder.this.disableColorSampling();
                    return;
                case 3:
                    AdaptiveBrightnessLongtermModelBuilder.this
                            .startAdaptiveBrightnessStatsTracker();
                    AdaptiveBrightnessLongtermModelBuilder.this.enableColorSampling();
                    return;
                case 4:
                    AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder =
                            AdaptiveBrightnessLongtermModelBuilder.this;
                    BrightnessConfiguration brightnessConfiguration2 =
                            (BrightnessConfiguration) message.obj;
                    adaptiveBrightnessLongtermModelBuilder.mBrightnessConfiguration =
                            brightnessConfiguration2;
                    boolean z =
                            brightnessConfiguration2 != null
                                    && brightnessConfiguration2.shouldCollectColorSamples();
                    if (z) {
                        AdaptiveBrightnessLongtermModelBuilder
                                adaptiveBrightnessLongtermModelBuilder2 =
                                        AdaptiveBrightnessLongtermModelBuilder.this;
                        if (!adaptiveBrightnessLongtermModelBuilder2.mColorSamplingEnabled) {
                            adaptiveBrightnessLongtermModelBuilder2.enableColorSampling();
                            return;
                        }
                    }
                    if (z) {
                        return;
                    }
                    AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder3 =
                            AdaptiveBrightnessLongtermModelBuilder.this;
                    if (adaptiveBrightnessLongtermModelBuilder3.mColorSamplingEnabled) {
                        adaptiveBrightnessLongtermModelBuilder3.disableColorSampling();
                        return;
                    }
                    return;
                case 5:
                    float intBitsToFloat = Float.intBitsToFloat(message.arg1);
                    AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder4 =
                            AdaptiveBrightnessLongtermModelBuilder.this;
                    adaptiveBrightnessLongtermModelBuilder4.mLastAmbientLux = intBitsToFloat;
                    synchronized (adaptiveBrightnessLongtermModelBuilder4.mDataCollectionLock) {
                        Slog.v(
                                "AdaptiveBrightnessLongtermModelBuilder",
                                "Ambient Lux event " + intBitsToFloat);
                        LightData lightData =
                                adaptiveBrightnessLongtermModelBuilder4.mLastLightData;
                        adaptiveBrightnessLongtermModelBuilder4.mInjector.getClass();
                        lightData.timestamp = System.currentTimeMillis();
                        adaptiveBrightnessLongtermModelBuilder4.mLastLightData.lux = intBitsToFloat;
                    }
                    Slog.d(
                            "AdaptiveBrightnessLongtermModelBuilder",
                            "handleAmbientLuxChanged: ambientLux: " + intBitsToFloat);
                    adaptiveBrightnessLongtermModelBuilder4.updateAdaptiveBrightnessStats(false);
                    return;
                case 6:
                default:
                    return;
                case 7:
                    AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder5 =
                            AdaptiveBrightnessLongtermModelBuilder.this;
                    adaptiveBrightnessLongtermModelBuilder5.getClass();
                    Slog.d("AdaptiveBrightnessLongtermModelBuilder", "handleShortTermModelValid()");
                    AdaptiveBrightnessStatsTracker adaptiveBrightnessStatsTracker =
                            adaptiveBrightnessLongtermModelBuilder5.mAdaptiveBrightnessStatsTracker;
                    synchronized (adaptiveBrightnessStatsTracker) {
                        AdaptiveBrightnessStatsTracker.AdaptiveBrightnessStats
                                adaptiveBrightnessStats =
                                        adaptiveBrightnessStatsTracker.mAdaptiveBrightnessStats;
                        adaptiveBrightnessStatsTracker.mInjector.getClass();
                        LocalDate.now();
                        adaptiveBrightnessStats
                                .getOrCreateUserStats(0, adaptiveBrightnessStats.mStats)
                                .summarizeStats();
                    }
                    AdaptiveBrightnessStatsTracker adaptiveBrightnessStatsTracker2 =
                            adaptiveBrightnessLongtermModelBuilder5.mAdaptiveBrightnessStatsTracker;
                    synchronized (adaptiveBrightnessStatsTracker2) {
                        AdaptiveBrightnessStatsTracker.AdaptiveBrightnessStats
                                adaptiveBrightnessStats2 =
                                        adaptiveBrightnessStatsTracker2.mAdaptiveBrightnessStats;
                        adaptiveBrightnessWeightStats =
                                ((HashMap) adaptiveBrightnessStats2.mStats).containsKey(0)
                                        ? (AdaptiveBrightnessWeightStats)
                                                ((HashMap) adaptiveBrightnessStats2.mStats).get(0)
                                        : null;
                    }
                    if (adaptiveBrightnessWeightStats != null) {
                        AdaptiveBrightnessWeightStats.BrightnessWeights[] stats =
                                adaptiveBrightnessWeightStats.getStats();
                        weightStatArr2 = adaptiveBrightnessWeightStats.getTimeCollectorStats();
                        brightnessWeightsArr = stats;
                        weightStatArr = adaptiveBrightnessWeightStats.getContinuityCollectorStats();
                    } else {
                        weightStatArr = null;
                        brightnessWeightsArr = null;
                        weightStatArr2 = null;
                    }
                    if (brightnessWeightsArr == null
                            || weightStatArr2 == null
                            || weightStatArr == null) {
                        return;
                    }
                    int length = brightnessWeightsArr.length;
                    AdaptiveBrightnessWeightStats.BrightnessWeights[] brightnessWeightsArr2 =
                            new AdaptiveBrightnessWeightStats.BrightnessWeights[length];
                    for (int i2 = 0; i2 < length; i2++) {
                        brightnessWeightsArr2[i2] = brightnessWeightsArr[i2].copy();
                    }
                    Arrays.sort(
                            brightnessWeightsArr2,
                            adaptiveBrightnessLongtermModelBuilder5.mComparatorDecsendingForWeight);
                    int i3 = 0;
                    while (i3 < length) {
                        float lux = brightnessWeightsArr2[i3].getLux();
                        float brightness = brightnessWeightsArr2[i3].getBrightness();
                        i3++;
                        for (int i4 = i3; i4 < length; i4++) {
                            float lux2 = brightnessWeightsArr2[i4].getLux();
                            float brightness2 = brightnessWeightsArr2[i4].getBrightness();
                            if (PowerManagerUtil.USE_PERMISSIBLE_RATIO_FOR_LONGTERM_MODEL) {
                                float permissibleMinimumRatio =
                                        BrightnessMappingStrategy.permissibleMinimumRatio(
                                                lux2, lux);
                                float pow = MathUtils.pow((lux2 + 0.25f) / (lux + 0.25f), 1.0f);
                                max =
                                        lux2 > lux
                                                ? MathUtils.constrain(
                                                        brightness2,
                                                        permissibleMinimumRatio * brightness,
                                                        pow * brightness)
                                                : MathUtils.constrain(
                                                        brightness2,
                                                        pow * brightness,
                                                        permissibleMinimumRatio * brightness);
                            } else {
                                max =
                                        lux2 > lux
                                                ? MathUtils.max(brightness2, brightness)
                                                : MathUtils.min(brightness2, brightness);
                            }
                            if (Float.compare(brightness2, max) != 0) {
                                brightnessWeightsArr2[i4].setBrightness(max);
                            }
                        }
                    }
                    for (int i5 = 0; i5 < length; i5++) {
                        float brightness3 = brightnessWeightsArr2[i5].getBrightness();
                        float f =
                                adaptiveBrightnessLongtermModelBuilder5.mMaxBrightnessForNonHbmLux;
                        if (brightness3 > f) {
                            brightnessWeightsArr2[i5].setBrightness(f);
                        }
                    }
                    Arrays.sort(
                            brightnessWeightsArr2,
                            adaptiveBrightnessLongtermModelBuilder5.mComparatorAscendingForLux);
                    StringBuilder sb = new StringBuilder();
                    sb.append(System.lineSeparator());
                    for (int i6 = 0;
                            i6 < length;
                            i6 =
                                    MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                                            "%9s",
                                            new Object[] {brightnessWeightsArr2[i6]}, sb, i6, 1)) {}
                    sb.append(System.lineSeparator());
                    Slog.d(
                            "AdaptiveBrightnessLongtermModelBuilder",
                            "lux sorted: " + sb.toString());
                    float[] fArr = new float[length];
                    float[] fArr2 = new float[length];
                    int[] iArr =
                            adaptiveBrightnessLongtermModelBuilder5.mMaximumBrightnessLimitCount;
                    boolean[] zArr = new boolean[iArr.length];
                    int i7 = 0;
                    while (i7 < length) {
                        fArr[i7] = brightnessWeightsArr2[i7].getLux();
                        fArr2[i7] = brightnessWeightsArr2[i7].getBrightness();
                        float interpolate =
                                adaptiveBrightnessLongtermModelBuilder5.mMinimumBrightnessSpline
                                        .interpolate(fArr[i7]);
                        float interpolate2 =
                                adaptiveBrightnessLongtermModelBuilder5.mMaximumBrightnessSpline
                                        .interpolate(fArr[i7]);
                        float constrain = MathUtils.constrain(fArr2[i7], interpolate, interpolate2);
                        if (BrightnessSynchronizer.floatEquals(fArr2[i7], constrain)) {
                            i = length;
                        } else {
                            i = length;
                            Slog.d(
                                    "AdaptiveBrightnessLongtermModelBuilder",
                                    "buildBrightnessConfiguration: "
                                            + fArr[i7]
                                            + " lux, "
                                            + fArr2[i7]
                                            + " -> "
                                            + constrain
                                            + " nits Limit: ("
                                            + interpolate
                                            + " ~ "
                                            + interpolate2
                                            + ")");
                            if (fArr2[i7] > interpolate2) {
                                int i8 = 0;
                                while (true) {
                                    float[] fArr3 =
                                            adaptiveBrightnessLongtermModelBuilder5
                                                    .mMaximumBrightnessLimitLux;
                                    if (i8 < fArr3.length) {
                                        if (fArr3[i8] < fArr[i7]) {
                                            i8++;
                                        } else if (!zArr[i8]) {
                                            iArr[i8] = iArr[i8] + 1;
                                            zArr[i8] = true;
                                        }
                                    }
                                }
                            }
                            fArr2[i7] = constrain;
                        }
                        i7++;
                        length = i;
                    }
                    try {
                        BrightnessConfiguration.Builder builder =
                                new BrightnessConfiguration.Builder(fArr, fArr2);
                        builder.setDescription("sbs:0");
                        brightnessConfiguration = builder.build();
                    } catch (Exception e) {
                        e.printStackTrace();
                        brightnessConfiguration = null;
                    }
                    if (brightnessConfiguration == null) {
                        Slog.e(
                                "AdaptiveBrightnessLongtermModelBuilder",
                                "handleShortTermModelValid: brightnessConfiguration is null");
                        return;
                    }
                    DisplayManager displayManager =
                            (DisplayManager)
                                    adaptiveBrightnessLongtermModelBuilder5.mContext
                                            .getSystemService(DisplayManager.class);
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    for (AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights :
                            brightnessWeightsArr) {
                        arrayList.add(Float.toString(brightnessWeights.getWeight()));
                    }
                    for (AdaptiveBrightnessWeightStats.WeightStat weightStat : weightStatArr2) {
                        arrayList2.add(Float.toString(weightStat.getWeight()));
                    }
                    for (AdaptiveBrightnessWeightStats.WeightStat weightStat2 : weightStatArr) {
                        arrayList3.add(Float.toString(weightStat2.getWeight()));
                    }
                    displayManager.setBrightnessConfigurationForUser(
                            brightnessConfiguration, 0, "sbs", arrayList, arrayList2, arrayList3);
                    return;
                case 8:
                    AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder6 =
                            AdaptiveBrightnessLongtermModelBuilder.this;
                    adaptiveBrightnessLongtermModelBuilder6.getClass();
                    Slog.d(
                            "AdaptiveBrightnessLongtermModelBuilder",
                            "handleBrightnessBnrPackageCleared()");
                    ((DisplayManager)
                                    adaptiveBrightnessLongtermModelBuilder6.mContext
                                            .getSystemService(DisplayManager.class))
                            .resetBrightnessConfiguration();
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Receiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AdaptiveBrightnessLongtermModelBuilder this$0;

        public /* synthetic */ Receiver(
                AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder,
                int i) {
            this.$r8$classId = i;
            this.this$0 = adaptiveBrightnessLongtermModelBuilder;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    Slog.d(
                            "AdaptiveBrightnessLongtermModelBuilder",
                            "Received " + intent.getAction());
                    String action = intent.getAction();
                    if (!"android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                        if (!"android.intent.action.BATTERY_CHANGED".equals(action)) {
                            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                                this.this$0.mBgHandler.obtainMessage(2).sendToTarget();
                                return;
                            } else {
                                if ("android.intent.action.SCREEN_ON".equals(action)) {
                                    this.this$0.mBgHandler.obtainMessage(3).sendToTarget();
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
                        AdaptiveBrightnessLongtermModelBuilder
                                adaptiveBrightnessLongtermModelBuilder = this.this$0;
                        synchronized (adaptiveBrightnessLongtermModelBuilder.mDataCollectionLock) {
                            adaptiveBrightnessLongtermModelBuilder.mLastBatteryLevel =
                                    intExtra / intExtra2;
                        }
                        return;
                    }
                    AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder2 =
                            this.this$0;
                    adaptiveBrightnessLongtermModelBuilder2.getClass();
                    Slog.d("AdaptiveBrightnessLongtermModelBuilder", "Stop");
                    synchronized (adaptiveBrightnessLongtermModelBuilder2.mDataCollectionLock) {
                        try {
                            if (adaptiveBrightnessLongtermModelBuilder2.mStarted) {
                                adaptiveBrightnessLongtermModelBuilder2.mBgHandler.removeMessages(
                                        0);
                                adaptiveBrightnessLongtermModelBuilder2
                                        .stopAdaptiveBrightnessStatsTracker();
                                ((PowerManagerInternal)
                                                LocalServices.getService(
                                                        PowerManagerInternal.class))
                                        .unregisterUserActivityStateListener(
                                                adaptiveBrightnessLongtermModelBuilder2
                                                        .mUserActivityStateListener);
                                Injector injector =
                                        adaptiveBrightnessLongtermModelBuilder2.mInjector;
                                Context context2 = adaptiveBrightnessLongtermModelBuilder2.mContext;
                                SettingsObserver settingsObserver =
                                        adaptiveBrightnessLongtermModelBuilder2.mSettingsObserver;
                                injector.getClass();
                                context2.getContentResolver()
                                        .unregisterContentObserver(settingsObserver);
                                Injector injector2 =
                                        adaptiveBrightnessLongtermModelBuilder2.mInjector;
                                Context context3 = adaptiveBrightnessLongtermModelBuilder2.mContext;
                                Receiver receiver =
                                        adaptiveBrightnessLongtermModelBuilder2.mBroadcastReceiver;
                                injector2.getClass();
                                context3.unregisterReceiver(receiver);
                                Injector injector3 =
                                        adaptiveBrightnessLongtermModelBuilder2.mInjector;
                                Context context4 = adaptiveBrightnessLongtermModelBuilder2.mContext;
                                Receiver receiver2 =
                                        adaptiveBrightnessLongtermModelBuilder2
                                                .mPackageBroadcastReceiver;
                                injector3.getClass();
                                context4.unregisterReceiver(receiver2);
                                Injector injector4 =
                                        adaptiveBrightnessLongtermModelBuilder2.mInjector;
                                Context context5 = adaptiveBrightnessLongtermModelBuilder2.mContext;
                                injector4.getClass();
                                int i = BrightnessIdleJob.$r8$clinit;
                                ((JobScheduler) context5.getSystemService(JobScheduler.class))
                                        .cancel(3923512);
                                synchronized (
                                        adaptiveBrightnessLongtermModelBuilder2
                                                .mDataCollectionLock) {
                                    adaptiveBrightnessLongtermModelBuilder2.mStarted = false;
                                }
                                adaptiveBrightnessLongtermModelBuilder2.disableColorSampling();
                            }
                        } finally {
                        }
                    }
                    AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder3 =
                            this.this$0;
                    if (adaptiveBrightnessLongtermModelBuilder3
                            .mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled) {
                        return;
                    }
                    adaptiveBrightnessLongtermModelBuilder3.mBgHandler.post(
                            new AdaptiveBrightnessLongtermModelBuilder$$ExternalSyntheticLambda1(
                                    adaptiveBrightnessLongtermModelBuilder3));
                    adaptiveBrightnessLongtermModelBuilder3
                                    .mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled =
                            true;
                    return;
                default:
                    Slog.d(
                            "AdaptiveBrightnessLongtermModelBuilder",
                            "Received " + intent.getAction());
                    if ("android.intent.action.PACKAGE_DATA_CLEARED".equals(intent.getAction())
                            && "com.samsung.android.brightnessbackupservice"
                                    .equals(intent.getData().getSchemeSpecificPart())) {
                        this.this$0.mBgHandler.obtainMessage(8).sendToTarget();
                        return;
                    }
                    return;
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
            Slog.v("AdaptiveBrightnessLongtermModelBuilder", "settings change " + uri);
            AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder =
                    AdaptiveBrightnessLongtermModelBuilder.this;
            Injector injector = adaptiveBrightnessLongtermModelBuilder.mInjector;
            ContentResolver contentResolver =
                    adaptiveBrightnessLongtermModelBuilder.mContentResolver;
            injector.getClass();
            if (Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2)
                    == 1) {
                AdaptiveBrightnessLongtermModelBuilder.this
                        .mBgHandler
                        .obtainMessage(3)
                        .sendToTarget();
            } else {
                AdaptiveBrightnessLongtermModelBuilder.this
                        .mBgHandler
                        .obtainMessage(2)
                        .sendToTarget();
            }
        }
    }

    /* renamed from: -$$Nest$mbackgroundStart, reason: not valid java name */
    public static void m432$$Nest$mbackgroundStart(
            AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder,
            float f) {
        synchronized (adaptiveBrightnessLongtermModelBuilder.mEventsLock) {
            adaptiveBrightnessLongtermModelBuilder.mEventsDirty = true;
            adaptiveBrightnessLongtermModelBuilder.mEvents.clear();
            AtomicFile fileWithLegacyFallback =
                    adaptiveBrightnessLongtermModelBuilder.getFileWithLegacyFallback(
                            "brightness_events_sec.xml");
            if (fileWithLegacyFallback.exists()) {
                FileInputStream fileInputStream = null;
                try {
                    try {
                        fileInputStream = fileWithLegacyFallback.openRead();
                        adaptiveBrightnessLongtermModelBuilder.readEventsLocked(fileInputStream);
                    } catch (IOException e) {
                        fileWithLegacyFallback.delete();
                        Slog.e(
                                "AdaptiveBrightnessLongtermModelBuilder",
                                "Failed to read change mEvents.",
                                e);
                    }
                } finally {
                    IoUtils.closeQuietly(fileInputStream);
                }
            }
        }
        adaptiveBrightnessLongtermModelBuilder.readAdaptiveBrightnessStats();
        SettingsObserver settingsObserver =
                adaptiveBrightnessLongtermModelBuilder
                .new SettingsObserver(adaptiveBrightnessLongtermModelBuilder.mBgHandler);
        adaptiveBrightnessLongtermModelBuilder.mSettingsObserver = settingsObserver;
        Injector injector = adaptiveBrightnessLongtermModelBuilder.mInjector;
        ContentResolver contentResolver = adaptiveBrightnessLongtermModelBuilder.mContentResolver;
        injector.getClass();
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("screen_brightness_mode"), false, settingsObserver, -1);
        adaptiveBrightnessLongtermModelBuilder.startAdaptiveBrightnessStatsTracker();
        ((PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class))
                .registerUserActivityStateListener(
                        adaptiveBrightnessLongtermModelBuilder.mUserActivityStateListener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter2.addDataScheme("package");
        adaptiveBrightnessLongtermModelBuilder.mBroadcastReceiver =
                new Receiver(adaptiveBrightnessLongtermModelBuilder, 0);
        adaptiveBrightnessLongtermModelBuilder.mPackageBroadcastReceiver =
                new Receiver(adaptiveBrightnessLongtermModelBuilder, 1);
        Injector injector2 = adaptiveBrightnessLongtermModelBuilder.mInjector;
        Context context = adaptiveBrightnessLongtermModelBuilder.mContext;
        Receiver receiver = adaptiveBrightnessLongtermModelBuilder.mBroadcastReceiver;
        injector2.getClass();
        context.registerReceiver(receiver, intentFilter);
        Injector injector3 = adaptiveBrightnessLongtermModelBuilder.mInjector;
        Context context2 = adaptiveBrightnessLongtermModelBuilder.mContext;
        Receiver receiver2 = adaptiveBrightnessLongtermModelBuilder.mPackageBroadcastReceiver;
        injector3.getClass();
        context2.registerReceiver(receiver2, intentFilter2);
        Injector injector4 = adaptiveBrightnessLongtermModelBuilder.mInjector;
        Context context3 = adaptiveBrightnessLongtermModelBuilder.mContext;
        injector4.getClass();
        BrightnessIdleJob.scheduleJob(context3);
        synchronized (adaptiveBrightnessLongtermModelBuilder.mDataCollectionLock) {
            adaptiveBrightnessLongtermModelBuilder.mLastBrightness = f;
            adaptiveBrightnessLongtermModelBuilder.mStarted = true;
        }
        adaptiveBrightnessLongtermModelBuilder.enableColorSampling();
    }

    /* renamed from: -$$Nest$mhandleBrightnessChanged, reason: not valid java name */
    public static void m433$$Nest$mhandleBrightnessChanged(
            AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder,
            float f,
            boolean z,
            float f2,
            boolean z2,
            boolean z3,
            long j,
            String str,
            Spline spline) {
        synchronized (adaptiveBrightnessLongtermModelBuilder.mDataCollectionLock) {
            try {
                if (adaptiveBrightnessLongtermModelBuilder.mStarted) {
                    float f3 = adaptiveBrightnessLongtermModelBuilder.mLastBrightness;
                    adaptiveBrightnessLongtermModelBuilder.mLastBrightness = f;
                    adaptiveBrightnessLongtermModelBuilder.mLastBrightnessSpline = spline;
                    Slog.d(
                            "AdaptiveBrightnessLongtermModelBuilder",
                            "handleBrightnessChanged: brightness: " + f + " userInitiated: " + z);
                    if (!z) {
                        adaptiveBrightnessLongtermModelBuilder.updateAdaptiveBrightnessStats(z);
                        return;
                    }
                    BrightnessChangeEvent.Builder builder = new BrightnessChangeEvent.Builder();
                    builder.setBrightness(f);
                    builder.setTimeStamp(j);
                    builder.setPowerBrightnessFactor(f2);
                    builder.setUserBrightnessPoint(z2);
                    builder.setIsDefaultBrightnessConfig(z3);
                    builder.setUniqueDisplayId(str);
                    LightData lightData = adaptiveBrightnessLongtermModelBuilder.mLastLightData;
                    float[] fArr = {lightData.lux};
                    long[] jArr = {lightData.timestamp};
                    builder.setLuxValues(fArr);
                    builder.setLuxTimestamps(jArr);
                    builder.setBatteryLevel(
                            adaptiveBrightnessLongtermModelBuilder.mLastBatteryLevel);
                    builder.setLastBrightness(f3);
                    try {
                        adaptiveBrightnessLongtermModelBuilder.mInjector.getClass();
                        ActivityTaskManager.RootTaskInfo focusedRootTaskInfo =
                                ActivityTaskManager.getService().getFocusedRootTaskInfo();
                        if (focusedRootTaskInfo == null
                                || focusedRootTaskInfo.topActivity == null) {
                            Slog.d(
                                    "AdaptiveBrightnessLongtermModelBuilder",
                                    "Ignoring event due to null focusedTask.");
                        } else {
                            builder.setUserId(focusedRootTaskInfo.userId);
                            builder.setPackageName(
                                    focusedRootTaskInfo.topActivity.getPackageName());
                            Injector injector = adaptiveBrightnessLongtermModelBuilder.mInjector;
                            Context context = adaptiveBrightnessLongtermModelBuilder.mContext;
                            injector.getClass();
                            builder.setNightMode(
                                    ((ColorDisplayManager)
                                                    context.getSystemService(
                                                            ColorDisplayManager.class))
                                            .isNightDisplayActivated());
                            Injector injector2 = adaptiveBrightnessLongtermModelBuilder.mInjector;
                            Context context2 = adaptiveBrightnessLongtermModelBuilder.mContext;
                            injector2.getClass();
                            builder.setColorTemperature(
                                    ((ColorDisplayManager)
                                                    context2.getSystemService(
                                                            ColorDisplayManager.class))
                                            .getNightDisplayColorTemperature());
                            Injector injector3 = adaptiveBrightnessLongtermModelBuilder.mInjector;
                            Context context3 = adaptiveBrightnessLongtermModelBuilder.mContext;
                            injector3.getClass();
                            builder.setReduceBrightColors(
                                    ((ColorDisplayManager)
                                                    context3.getSystemService(
                                                            ColorDisplayManager.class))
                                            .isReduceBrightColorsActivated());
                            Injector injector4 = adaptiveBrightnessLongtermModelBuilder.mInjector;
                            Context context4 = adaptiveBrightnessLongtermModelBuilder.mContext;
                            injector4.getClass();
                            builder.setReduceBrightColorsStrength(
                                    ((ColorDisplayManager)
                                                    context4.getSystemService(
                                                            ColorDisplayManager.class))
                                            .getReduceBrightColorsStrength());
                            Injector injector5 = adaptiveBrightnessLongtermModelBuilder.mInjector;
                            Context context5 = adaptiveBrightnessLongtermModelBuilder.mContext;
                            injector5.getClass();
                            builder.setReduceBrightColorsOffset(
                                    ((ColorDisplayManager)
                                                            context5.getSystemService(
                                                                    ColorDisplayManager.class))
                                                    .getReduceBrightColorsOffsetFactor()
                                            * f);
                            if (adaptiveBrightnessLongtermModelBuilder.mColorSamplingEnabled) {
                                Injector injector6 =
                                        adaptiveBrightnessLongtermModelBuilder.mInjector;
                                int i = adaptiveBrightnessLongtermModelBuilder.mNoFramesToSample;
                                injector6.getClass();
                                DisplayedContentSample displayedContentSample =
                                        ((DisplayManagerInternal)
                                                        LocalServices.getService(
                                                                DisplayManagerInternal.class))
                                                .getDisplayedContentSample(0, i, 0L);
                                if (displayedContentSample != null) {
                                    DisplayedContentSample.ColorComponent colorComponent =
                                            DisplayedContentSample.ColorComponent.CHANNEL2;
                                    if (displayedContentSample.getSampleComponent(colorComponent)
                                            != null) {
                                        builder.setColorValues(
                                                displayedContentSample.getSampleComponent(
                                                        colorComponent),
                                                Math.round(
                                                        (displayedContentSample.getNumFrames()
                                                                        / adaptiveBrightnessLongtermModelBuilder
                                                                                .mFrameRate)
                                                                * 1000.0f));
                                    }
                                }
                            }
                            BrightnessChangeEvent build = builder.build();
                            StringBuilder sb = new StringBuilder("Event ");
                            sb.append(build.brightness);
                            sb.append(" ");
                            BootReceiver$$ExternalSyntheticOutline0.m(
                                    sb,
                                    build.packageName,
                                    "AdaptiveBrightnessLongtermModelBuilder");
                            synchronized (adaptiveBrightnessLongtermModelBuilder.mEventsLock) {
                                adaptiveBrightnessLongtermModelBuilder.mEventsDirty = true;
                                adaptiveBrightnessLongtermModelBuilder.mEvents.append(build);
                            }
                            adaptiveBrightnessLongtermModelBuilder.mLastBrightnessChangeEvent =
                                    build;
                            adaptiveBrightnessLongtermModelBuilder.updateAdaptiveBrightnessStats(z);
                        }
                    } catch (RemoteException unused) {
                    }
                }
            } finally {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v15, types: [com.android.server.display.AdaptiveBrightnessLongtermModelBuilder$3] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.display.AdaptiveBrightnessLongtermModelBuilder$1] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.server.display.AdaptiveBrightnessLongtermModelBuilder$1] */
    public AdaptiveBrightnessLongtermModelBuilder(
            Context context, BrightnessMappingStrategy brightnessMappingStrategy) {
        final int i = 0;
        this.mComparatorDecsendingForWeight =
                new Comparator() { // from class:
                                   // com.android.server.display.AdaptiveBrightnessLongtermModelBuilder.1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i) {
                            case 0:
                                AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights =
                                        (AdaptiveBrightnessWeightStats.BrightnessWeights) obj;
                                AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights2 =
                                        (AdaptiveBrightnessWeightStats.BrightnessWeights) obj2;
                                if (brightnessWeights2.getWeight()
                                        > brightnessWeights.getWeight()) {
                                    return 1;
                                }
                                if (brightnessWeights2.getWeight()
                                        >= brightnessWeights.getWeight()) {
                                    if (brightnessWeights2.getLastUserBrightnessTime()
                                            > brightnessWeights.getLastUserBrightnessTime()) {
                                        return 1;
                                    }
                                    if (brightnessWeights2.getLastUserBrightnessTime()
                                            >= brightnessWeights.getLastUserBrightnessTime()) {
                                        return 0;
                                    }
                                }
                                return -1;
                            default:
                                AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights3 =
                                        (AdaptiveBrightnessWeightStats.BrightnessWeights) obj;
                                AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights4 =
                                        (AdaptiveBrightnessWeightStats.BrightnessWeights) obj2;
                                if (brightnessWeights3.getLux() > brightnessWeights4.getLux()) {
                                    return 1;
                                }
                                return brightnessWeights3.getLux() < brightnessWeights4.getLux()
                                        ? -1
                                        : 0;
                        }
                    }
                };
        final int i2 = 1;
        this.mComparatorAscendingForLux =
                new Comparator() { // from class:
                                   // com.android.server.display.AdaptiveBrightnessLongtermModelBuilder.1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i2) {
                            case 0:
                                AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights =
                                        (AdaptiveBrightnessWeightStats.BrightnessWeights) obj;
                                AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights2 =
                                        (AdaptiveBrightnessWeightStats.BrightnessWeights) obj2;
                                if (brightnessWeights2.getWeight()
                                        > brightnessWeights.getWeight()) {
                                    return 1;
                                }
                                if (brightnessWeights2.getWeight()
                                        >= brightnessWeights.getWeight()) {
                                    if (brightnessWeights2.getLastUserBrightnessTime()
                                            > brightnessWeights.getLastUserBrightnessTime()) {
                                        return 1;
                                    }
                                    if (brightnessWeights2.getLastUserBrightnessTime()
                                            >= brightnessWeights.getLastUserBrightnessTime()) {
                                        return 0;
                                    }
                                }
                                return -1;
                            default:
                                AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights3 =
                                        (AdaptiveBrightnessWeightStats.BrightnessWeights) obj;
                                AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights4 =
                                        (AdaptiveBrightnessWeightStats.BrightnessWeights) obj2;
                                if (brightnessWeights3.getLux() > brightnessWeights4.getLux()) {
                                    return 1;
                                }
                                return brightnessWeights3.getLux() < brightnessWeights4.getLux()
                                        ? -1
                                        : 0;
                        }
                    }
                };
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mBrightnessMapper = brightnessMappingStrategy;
        this.mMaxBrightnessForNonHbmLux = brightnessMappingStrategy.convertToNits(1.0f);
        Resources resources = context.getResources();
        float[] floatArray =
                getFloatArray(resources.obtainTypedArray(R.array.vendor_policy_exempt_apps));
        float[] floatArray2 = getFloatArray(resources.obtainTypedArray(17236256));
        float[] floatArray3 = getFloatArray(resources.obtainTypedArray(17236253));
        float[] fArr = new float[floatArray.length];
        float[] fArr2 = new float[floatArray.length];
        for (int i3 = 0; i3 < floatArray.length; i3++) {
            float f = this.mMaxBrightnessForNonHbmLux;
            fArr[i3] = floatArray2[i3] * f;
            fArr2[i3] = f * floatArray3[i3];
        }
        this.mMinimumBrightnessSpline = Spline.createSpline(floatArray, fArr);
        this.mMaximumBrightnessSpline = Spline.createSpline(floatArray, fArr2);
        float[] copyOfRange = Arrays.copyOfRange(floatArray, 1, floatArray.length);
        this.mMaximumBrightnessLimitLux = copyOfRange;
        int[] iArr = new int[copyOfRange.length];
        this.mMaximumBrightnessLimitCount = iArr;
        Arrays.fill(iArr, 0);
    }

    public static float[] getFloatArray(TypedArray typedArray) {
        int length = typedArray.length();
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = typedArray.getFloat(i, Float.NaN);
        }
        typedArray.recycle();
        return fArr;
    }

    public final void disableColorSampling() {
        if (this.mColorSamplingEnabled) {
            this.mInjector.getClass();
            ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class))
                    .setDisplayedContentSamplingEnabled(0, false, 4, 0);
            this.mColorSamplingEnabled = false;
            DisplayListener displayListener = this.mDisplayListener;
            if (displayListener != null) {
                ((DisplayManager) this.mContext.getSystemService(DisplayManager.class))
                        .unregisterDisplayListener(displayListener);
                this.mDisplayListener = null;
            }
            Slog.i("AdaptiveBrightnessLongtermModelBuilder", "turning off color sampling");
        }
    }

    public final void dump(final PrintWriter printWriter) {
        int i;
        printWriter.println("AdaptiveBrightnessLongtermModelBuilder state:");
        synchronized (this.mDataCollectionLock) {
            printWriter.println("  mStarted=" + this.mStarted);
            printWriter.println("  mLastBatteryLevel=" + this.mLastBatteryLevel);
            printWriter.println("  mLastBrightness=" + this.mLastBrightness);
            printWriter.println("  mLastLightData.lux=" + this.mLastLightData.lux);
            printWriter.println("  mLastLightData.timestamp=" + this.mLastLightData.timestamp);
        }
        synchronized (this.mEventsLock) {
            try {
                printWriter.println("  mEventsDirty=" + this.mEventsDirty);
                printWriter.println("  mEvents.size=" + this.mEvents.size());
                BrightnessChangeEvent[] brightnessChangeEventArr =
                        (BrightnessChangeEvent[]) this.mEvents.toArray();
                i = 0;
                for (int i2 = 0; i2 < brightnessChangeEventArr.length; i2++) {
                    printWriter.print(
                            "    "
                                    + FORMAT.format(
                                            new Date(brightnessChangeEventArr[i2].timeStamp)));
                    printWriter.print(", userId=" + brightnessChangeEventArr[i2].userId);
                    printWriter.print(
                            ", "
                                    + brightnessChangeEventArr[i2].lastBrightness
                                    + "->"
                                    + brightnessChangeEventArr[i2].brightness);
                    StringBuilder sb = new StringBuilder();
                    sb.append(", isUserSetBrightness=");
                    sb.append(brightnessChangeEventArr[i2].isUserSetBrightness);
                    printWriter.print(sb.toString());
                    printWriter.print(
                            ", powerBrightnessFactor="
                                    + brightnessChangeEventArr[i2].powerBrightnessFactor);
                    printWriter.print(
                            ", isDefaultBrightnessConfig="
                                    + brightnessChangeEventArr[i2].isDefaultBrightnessConfig);
                    printWriter.print(" {");
                    for (int i3 = 0; i3 < brightnessChangeEventArr[i2].luxValues.length; i3++) {
                        if (i3 != 0) {
                            printWriter.print(", ");
                        }
                        printWriter.print(
                                "("
                                        + brightnessChangeEventArr[i2].luxValues[i3]
                                        + ","
                                        + brightnessChangeEventArr[i2].luxTimestamps[i3]
                                        + ")");
                    }
                    printWriter.println("}");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println("  mMinimumBrightnessSpline=" + this.mMinimumBrightnessSpline);
        printWriter.println("  mMaximumBrightnessSpline=" + this.mMaximumBrightnessSpline);
        printWriter.print("  mMaximumBrightnessLimitLux=[");
        int i4 = 0;
        while (i4 < this.mMaximumBrightnessLimitLux.length) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mMaximumBrightnessLimitLux[i4]);
            sb2.append(i4 != this.mMaximumBrightnessLimitLux.length + (-1) ? ", " : "]");
            printWriter.print(sb2.toString());
            i4++;
        }
        printWriter.println();
        printWriter.print("  mMaximumBrightnessLimitCount=[");
        while (i < this.mMaximumBrightnessLimitCount.length) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.mMaximumBrightnessLimitCount[i]);
            sb3.append(i != this.mMaximumBrightnessLimitCount.length + (-1) ? ", " : "]");
            printWriter.print(sb3.toString());
            i++;
        }
        printWriter.println();
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                KillPolicyManager$$ExternalSyntheticOutline0.m(
                        new StringBuilder("  mMaxBrightnessForNonHbmLux="),
                        this.mMaxBrightnessForNonHbmLux,
                        printWriter,
                        "  mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled="),
                this.mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled,
                printWriter);
        this.mBgHandler.runWithScissors(
                new Runnable() { // from class:
                    // com.android.server.display.AdaptiveBrightnessLongtermModelBuilder$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AdaptiveBrightnessLongtermModelBuilder
                                adaptiveBrightnessLongtermModelBuilder =
                                        AdaptiveBrightnessLongtermModelBuilder.this;
                        PrintWriter printWriter2 = printWriter;
                        AggressivePolicyHandler$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                        .m(
                                                                new StringBuilder(
                                                                        "  mAdaptiveBrightnessStatsTrackerStarted="),
                                                                adaptiveBrightnessLongtermModelBuilder
                                                                        .mAdaptiveBrightnessStatsTrackerStarted,
                                                                printWriter2,
                                                                "  mColorSamplingEnabled="),
                                                adaptiveBrightnessLongtermModelBuilder
                                                        .mColorSamplingEnabled,
                                                printWriter2,
                                                "  mNoFramesToSample="),
                                        adaptiveBrightnessLongtermModelBuilder.mNoFramesToSample,
                                        printWriter2,
                                        "  mFrameRate="),
                                adaptiveBrightnessLongtermModelBuilder.mFrameRate,
                                printWriter2);
                    }
                },
                1000L);
        if (this.mAdaptiveBrightnessStatsTracker != null) {
            printWriter.println();
            AdaptiveBrightnessStatsTracker adaptiveBrightnessStatsTracker =
                    this.mAdaptiveBrightnessStatsTracker;
            synchronized (adaptiveBrightnessStatsTracker) {
                printWriter.println("AdaptiveBrightnessStats:");
                printWriter.print(adaptiveBrightnessStatsTracker.mAdaptiveBrightnessStats);
            }
        }
    }

    public final void enableColorSampling() {
        BrightnessConfiguration brightnessConfiguration;
        ContentResolver contentResolver = this.mContentResolver;
        this.mInjector.getClass();
        if (Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2) == 1
                && ((PowerManager) this.mContext.getSystemService(PowerManager.class))
                        .isInteractive()
                && !this.mColorSamplingEnabled
                && (brightnessConfiguration = this.mBrightnessConfiguration) != null
                && brightnessConfiguration.shouldCollectColorSamples()) {
            float refreshRate =
                    ((DisplayManager) this.mContext.getSystemService(DisplayManager.class))
                            .getDisplay(0)
                            .getRefreshRate();
            this.mFrameRate = refreshRate;
            if (refreshRate <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                Slog.wtf(
                        "AdaptiveBrightnessLongtermModelBuilder",
                        "Default display has a zero or negative framerate.");
                return;
            }
            this.mNoFramesToSample = (int) (refreshRate * COLOR_SAMPLE_DURATION);
            DisplayedContentSamplingAttributes displayedContentSamplingAttributes =
                    ((DisplayManagerInternal)
                                    LocalServices.getService(DisplayManagerInternal.class))
                            .getDisplayedContentSamplingAttributes(0);
            if (displayedContentSamplingAttributes != null) {
                Slog.d(
                        "AdaptiveBrightnessLongtermModelBuilder",
                        "Color sampling mask=0x"
                                + Integer.toHexString(
                                        displayedContentSamplingAttributes.getComponentMask())
                                + " dataSpace=0x"
                                + Integer.toHexString(
                                        displayedContentSamplingAttributes.getDataspace())
                                + " pixelFormat=0x"
                                + Integer.toHexString(
                                        displayedContentSamplingAttributes.getPixelFormat()));
            }
            if (displayedContentSamplingAttributes != null
                    && displayedContentSamplingAttributes.getPixelFormat() == 55
                    && (displayedContentSamplingAttributes.getComponentMask() & 4) != 0) {
                this.mColorSamplingEnabled =
                        ((DisplayManagerInternal)
                                        LocalServices.getService(DisplayManagerInternal.class))
                                .setDisplayedContentSamplingEnabled(
                                        0, true, 4, this.mNoFramesToSample);
                StringBuilder sb = new StringBuilder("turning on color sampling for ");
                sb.append(this.mNoFramesToSample);
                sb.append(" frames, success=");
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "AdaptiveBrightnessLongtermModelBuilder", sb, this.mColorSamplingEnabled);
            }
            if (this.mColorSamplingEnabled && this.mDisplayListener == null) {
                DisplayListener displayListener = new DisplayListener();
                this.mDisplayListener = displayListener;
                ((DisplayManager) this.mContext.getSystemService(DisplayManager.class))
                        .registerDisplayListener(displayListener, this.mBgHandler);
            }
        }
    }

    public final AtomicFile getFileWithLegacyFallback(String str) {
        this.mInjector.getClass();
        AtomicFile file = Injector.getFile(str);
        if (!file.exists()) {
            AtomicFile atomicFile =
                    new AtomicFile(new File(Environment.getDataSystemDeDirectory(), str));
            if (atomicFile.exists()) {
                BootReceiver$$ExternalSyntheticOutline0.m58m(
                        "Reading ",
                        str,
                        " from old location",
                        "AdaptiveBrightnessLongtermModelBuilder");
                return atomicFile;
            }
        }
        return file;
    }

    public final void readAdaptiveBrightnessStats() {
        this.mAdaptiveBrightnessStatsTracker =
                new AdaptiveBrightnessStatsTracker(this.mUserManager, this.mBrightnessMapper);
        AtomicFile fileWithLegacyFallback =
                getFileWithLegacyFallback("adaptive_brightness_stats_sec.xml");
        if (fileWithLegacyFallback.exists()) {
            FileInputStream fileInputStream = null;
            try {
                try {
                    fileInputStream = fileWithLegacyFallback.openRead();
                    AdaptiveBrightnessStatsTracker adaptiveBrightnessStatsTracker =
                            this.mAdaptiveBrightnessStatsTracker;
                    synchronized (adaptiveBrightnessStatsTracker) {
                        adaptiveBrightnessStatsTracker.mAdaptiveBrightnessStats.readFromXML(
                                fileInputStream);
                    }
                } catch (IOException e) {
                    fileWithLegacyFallback.delete();
                    Slog.e(
                            "AdaptiveBrightnessLongtermModelBuilder",
                            "Failed to read ambient brightness stats.",
                            e);
                }
            } finally {
                IoUtils.closeQuietly((AutoCloseable) null);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0048, code lost:

       if (r9 != 4) goto L62;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0056, code lost:

       if ("event".equals(r4.getName()) == false) goto L69;
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

       if (r10.length == r12.length) goto L65;
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ff, code lost:

       r13 = r10.length;
       r14 = new float[r13];
       r15 = new long[r10.length];
       r6 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0106, code lost:

       if (r6 >= r13) goto L73;
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

       if (r10 >= r11) goto L74;
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
       android.util.Slog.i("AdaptiveBrightnessLongtermModelBuilder", "Read event " + r6.brightness + " " + r6.packageName);
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0197, code lost:

       if (r6.userId == (-1)) goto L50;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x019d, code lost:

       if (r6.timeStamp <= r7) goto L50;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x01a2, code lost:

       if (r6.luxValues.length <= 0) goto L50;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01a4, code lost:

       r19.mEvents.append(r6);
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x01a9, code lost:

       r6 = 1;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void readEventsLocked(java.io.InputStream r20) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 477
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.AdaptiveBrightnessLongtermModelBuilder.readEventsLocked(java.io.InputStream):void");
    }

    public final void startAdaptiveBrightnessStatsTracker() {
        if (this.mAdaptiveBrightnessStatsTrackerStarted) {
            return;
        }
        Injector injector = this.mInjector;
        Context context = this.mContext;
        injector.getClass();
        if (((PowerManager) context.getSystemService(PowerManager.class)).isInteractive()) {
            Injector injector2 = this.mInjector;
            ContentResolver contentResolver = this.mContentResolver;
            injector2.getClass();
            if (Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2) != 1
                    || this.mTestModeEnabled) {
                return;
            }
            PowerManagerInternal powerManagerInternal =
                    (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
            AdaptiveBrightnessStatsTracker adaptiveBrightnessStatsTracker =
                    this.mAdaptiveBrightnessStatsTracker;
            boolean z = powerManagerInternal.getLastUserActivityState() == 1;
            synchronized (adaptiveBrightnessStatsTracker) {
                AdaptiveBrightnessStatsTracker.Timer timer = adaptiveBrightnessStatsTracker.mTimer;
                timer.started = false;
                timer.paused = false;
                timer.totalDurationSec = FullScreenMagnificationGestureHandler.MAX_SCALE;
                if (z) {
                    timer.startTimeMillis = timer.clock.elapsedTimeMillis();
                    timer.started = true;
                }
            }
            this.mAdaptiveBrightnessStatsTrackerStarted = true;
        }
    }

    public final void stopAdaptiveBrightnessStatsTracker() {
        if (this.mAdaptiveBrightnessStatsTrackerStarted) {
            AdaptiveBrightnessStatsTracker adaptiveBrightnessStatsTracker =
                    this.mAdaptiveBrightnessStatsTracker;
            synchronized (adaptiveBrightnessStatsTracker) {
                try {
                    if (adaptiveBrightnessStatsTracker.mTimer.started) {
                        AdaptiveBrightnessStatsTracker.AdaptiveBrightnessStats
                                adaptiveBrightnessStats =
                                        adaptiveBrightnessStatsTracker.mAdaptiveBrightnessStats;
                        int i = adaptiveBrightnessStatsTracker.mCurrentUserId;
                        adaptiveBrightnessStatsTracker.mInjector.getClass();
                        LocalDate.now();
                        adaptiveBrightnessStats
                                .getOrCreateUserStats(i, adaptiveBrightnessStats.mStats)
                                .log(
                                        adaptiveBrightnessStatsTracker.mCurrentAmbientLux,
                                        adaptiveBrightnessStatsTracker.mCurrentScreenBrightness,
                                        adaptiveBrightnessStatsTracker.mTimer.totalDurationSec(),
                                        adaptiveBrightnessStatsTracker
                                                .mCurrentScreenBrightnessSpline,
                                        null,
                                        null,
                                        false);
                    }
                    AdaptiveBrightnessStatsTracker.Timer timer =
                            adaptiveBrightnessStatsTracker.mTimer;
                    timer.started = false;
                    timer.paused = false;
                    timer.totalDurationSec = FullScreenMagnificationGestureHandler.MAX_SCALE;
                    adaptiveBrightnessStatsTracker.mCurrentAmbientLux = -1.0f;
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mAdaptiveBrightnessStatsTrackerStarted = false;
        }
    }

    public final void updateAdaptiveBrightnessStats(boolean z) {
        Slog.d(
                "AdaptiveBrightnessLongtermModelBuilder",
                "updateAdaptiveBrightnessStats: l:"
                        + this.mLastAmbientLux
                        + " b:"
                        + this.mLastBrightness
                        + " u: "
                        + z);
        AdaptiveBrightnessStatsTracker adaptiveBrightnessStatsTracker =
                this.mAdaptiveBrightnessStatsTracker;
        float f = this.mLastAmbientLux;
        float f2 = this.mLastBrightness;
        Spline spline = this.mLastBrightnessSpline;
        BrightnessChangeEvent brightnessChangeEvent = this.mLastBrightnessChangeEvent;
        synchronized (adaptiveBrightnessStatsTracker) {
            try {
                if (adaptiveBrightnessStatsTracker.mTimer.started) {
                    int i = adaptiveBrightnessStatsTracker.mCurrentUserId;
                    if (i == 0) {
                        AdaptiveBrightnessStatsTracker.AdaptiveBrightnessStats
                                adaptiveBrightnessStats =
                                        adaptiveBrightnessStatsTracker.mAdaptiveBrightnessStats;
                        adaptiveBrightnessStatsTracker.mInjector.getClass();
                        LocalDate.now();
                        adaptiveBrightnessStats
                                .getOrCreateUserStats(i, adaptiveBrightnessStats.mStats)
                                .log(
                                        adaptiveBrightnessStatsTracker.mCurrentAmbientLux,
                                        adaptiveBrightnessStatsTracker.mCurrentScreenBrightness,
                                        adaptiveBrightnessStatsTracker.mTimer.totalDurationSec(),
                                        adaptiveBrightnessStatsTracker
                                                .mCurrentScreenBrightnessSpline,
                                        brightnessChangeEvent,
                                        spline,
                                        z);
                    } else {
                        adaptiveBrightnessStatsTracker.mCurrentUserId = 0;
                    }
                    AdaptiveBrightnessStatsTracker.Timer timer =
                            adaptiveBrightnessStatsTracker.mTimer;
                    boolean z2 = !timer.paused;
                    timer.started = false;
                    timer.paused = false;
                    timer.totalDurationSec = FullScreenMagnificationGestureHandler.MAX_SCALE;
                    if (z2) {
                        timer.startTimeMillis = timer.clock.elapsedTimeMillis();
                        timer.started = true;
                    }
                }
                adaptiveBrightnessStatsTracker.mCurrentAmbientLux = f;
                adaptiveBrightnessStatsTracker.mCurrentScreenBrightness = f2;
                adaptiveBrightnessStatsTracker.mCurrentScreenBrightnessSpline = spline;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void writeEventsLocked(OutputStream outputStream) throws IOException {
        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(outputStream);
        resolveSerializer.startDocument((String) null, Boolean.TRUE);
        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        resolveSerializer.startTag((String) null, "events");
        BrightnessChangeEvent[] brightnessChangeEventArr =
                (BrightnessChangeEvent[]) this.mEvents.toArray();
        this.mEvents.clear();
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("Writing events "),
                brightnessChangeEventArr.length,
                "AdaptiveBrightnessLongtermModelBuilder");
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
                    resolveSerializer.attributeFloat(
                            (String) null, "nits", brightnessChangeEventArr[i].brightness);
                    resolveSerializer.attributeLong(
                            (String) null, "timestamp", brightnessChangeEventArr[i].timeStamp);
                    resolveSerializer.attribute(
                            (String) null, "packageName", brightnessChangeEventArr[i].packageName);
                    resolveSerializer.attributeInt((String) null, "user", userSerialNumber);
                    String str = brightnessChangeEventArr[i].uniqueDisplayId;
                    if (str == null) {
                        str = "";
                    }
                    resolveSerializer.attribute((String) null, "uniqueDisplayId", str);
                    resolveSerializer.attributeFloat(
                            (String) null,
                            "batteryLevel",
                            brightnessChangeEventArr[i].batteryLevel);
                    resolveSerializer.attributeBoolean(
                            (String) null, "nightMode", brightnessChangeEventArr[i].nightMode);
                    resolveSerializer.attributeInt(
                            (String) null,
                            "colorTemperature",
                            brightnessChangeEventArr[i].colorTemperature);
                    resolveSerializer.attributeBoolean(
                            (String) null,
                            "reduceBrightColors",
                            brightnessChangeEventArr[i].reduceBrightColors);
                    resolveSerializer.attributeInt(
                            (String) null,
                            "reduceBrightColorsStrength",
                            brightnessChangeEventArr[i].reduceBrightColorsStrength);
                    resolveSerializer.attributeFloat(
                            (String) null,
                            "reduceBrightColorsOffset",
                            brightnessChangeEventArr[i].reduceBrightColorsOffset);
                    resolveSerializer.attributeFloat(
                            (String) null, "lastNits", brightnessChangeEventArr[i].lastBrightness);
                    resolveSerializer.attributeBoolean(
                            (String) null,
                            "defaultConfig",
                            brightnessChangeEventArr[i].isDefaultBrightnessConfig);
                    resolveSerializer.attributeFloat(
                            (String) null,
                            "powerSaveFactor",
                            brightnessChangeEventArr[i].powerBrightnessFactor);
                    resolveSerializer.attributeBoolean(
                            (String) null,
                            "userPoint",
                            brightnessChangeEventArr[i].isUserSetBrightness);
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
                        resolveSerializer.attributeLong(
                                (String) null,
                                "colorSampleDuration",
                                brightnessChangeEvent2.colorSampleDuration);
                        StringBuilder sb3 = new StringBuilder();
                        for (int i4 = 0;
                                i4 < brightnessChangeEventArr[i].colorValueBuckets.length;
                                i4++) {
                            if (i4 > 0) {
                                sb3.append(',');
                            }
                            sb3.append(
                                    Long.toString(
                                            brightnessChangeEventArr[i].colorValueBuckets[i4]));
                        }
                        resolveSerializer.attribute(
                                (String) null, "colorValueBuckets", sb3.toString());
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
