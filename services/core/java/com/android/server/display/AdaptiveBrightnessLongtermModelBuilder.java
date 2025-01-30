package com.android.server.display;

import android.R;
import android.app.ActivityTaskManager;
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
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.display.AdaptiveBrightnessWeightStats;
import com.android.server.power.PowerManagerUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class AdaptiveBrightnessLongtermModelBuilder {
    public AdaptiveBrightnessStatsTracker mAdaptiveBrightnessStatsTracker;
    public boolean mAdaptiveBrightnessStatsTrackerStarted;
    public final Handler mBgHandler;
    public BrightnessConfiguration mBrightnessConfiguration;
    public final BrightnessMappingStrategy mBrightnessMapper;
    public BroadcastReceiver mBroadcastReceiver;
    public boolean mColorSamplingEnabled;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public DisplayListener mDisplayListener;
    public boolean mEventsDirty;
    public float mFrameRate;
    public final Injector mInjector;
    public LightData mLastLightData;
    public final float mMaxBrightnessForNonHbmLux;
    public int[] mMaximumBrightnessLimitCount;
    public float[] mMaximumBrightnessLimitLux;
    public final Spline mMaximumBrightnessSpline;
    public final Spline mMinimumBrightnessSpline;
    public int mNoFramesToSample;
    public PackageBroadcastReceiver mPackageBroadcastReceiver;
    public SettingsObserver mSettingsObserver;
    public boolean mStarted;
    public boolean mTestModeEnabled;
    public PowerManagerInternal.UserActivityStateListener mUserActivityStateListener;
    public final UserManager mUserManager;
    public volatile boolean mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled;
    public static final long MAX_EVENT_AGE = TimeUnit.DAYS.toMillis(30);
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public static final long COLOR_SAMPLE_DURATION = TimeUnit.SECONDS.toSeconds(10);
    public final Object mEventsLock = new Object();
    public RingBuffer mEvents = new RingBuffer(BrightnessChangeEvent.class, 240);
    public final int mCurrentUserId = 0;
    public final Object mDataCollectionLock = new Object();
    public float mLastBatteryLevel = Float.NaN;
    public float mLastBrightness = -1.0f;
    public float mLastAmbientLux = -1.0f;
    public Spline mLastBrightnessSpline = null;
    public BrightnessChangeEvent mLastBrightnessChangeEvent = null;
    public final Comparator mComparatorDecsendingForWeight = new Comparator() { // from class: com.android.server.display.AdaptiveBrightnessLongtermModelBuilder.1
        @Override // java.util.Comparator
        public int compare(AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights, AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights2) {
            if (brightnessWeights2.getWeight() > brightnessWeights.getWeight()) {
                return 1;
            }
            if (brightnessWeights2.getWeight() < brightnessWeights.getWeight()) {
                return -1;
            }
            if (brightnessWeights2.getLastUserBrightnessTime() > brightnessWeights.getLastUserBrightnessTime()) {
                return 1;
            }
            return brightnessWeights2.getLastUserBrightnessTime() < brightnessWeights.getLastUserBrightnessTime() ? -1 : 0;
        }
    };
    public final Comparator mComparatorAscendingForLux = new Comparator() { // from class: com.android.server.display.AdaptiveBrightnessLongtermModelBuilder.2
        @Override // java.util.Comparator
        public int compare(AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights, AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights2) {
            if (brightnessWeights.getLux() > brightnessWeights2.getLux()) {
                return 1;
            }
            return brightnessWeights.getLux() < brightnessWeights2.getLux() ? -1 : 0;
        }
    };

    public class LightData {
        public float lux;
        public long timestamp;

        public LightData() {
        }
    }

    public AdaptiveBrightnessLongtermModelBuilder(Context context, Injector injector, BrightnessMappingStrategy brightnessMappingStrategy) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        if (injector != null) {
            this.mInjector = injector;
        } else {
            this.mInjector = new Injector();
        }
        this.mBgHandler = new ModelBuilderHandler(this.mInjector.getBackgroundHandler().getLooper());
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mLastLightData = new LightData();
        this.mBrightnessMapper = brightnessMappingStrategy;
        this.mUserActivityStateListener = new PowerManagerInternal.UserActivityStateListener() { // from class: com.android.server.display.AdaptiveBrightnessLongtermModelBuilder.3
            public void onChanged(int i) {
                if (i == 1) {
                    if (AdaptiveBrightnessLongtermModelBuilder.this.mAdaptiveBrightnessStatsTrackerStarted) {
                        AdaptiveBrightnessLongtermModelBuilder.this.mAdaptiveBrightnessStatsTracker.resume();
                    }
                } else if (i == 0 && AdaptiveBrightnessLongtermModelBuilder.this.mAdaptiveBrightnessStatsTrackerStarted) {
                    AdaptiveBrightnessLongtermModelBuilder.this.mAdaptiveBrightnessStatsTracker.pause();
                }
            }
        };
        this.mMaxBrightnessForNonHbmLux = brightnessMappingStrategy.convertToNits(1.0f);
        Resources resources = context.getResources();
        float[] floatArray = getFloatArray(resources.obtainTypedArray(R.array.special_locale_names));
        float[] floatArray2 = getFloatArray(resources.obtainTypedArray(17236248));
        float[] floatArray3 = getFloatArray(resources.obtainTypedArray(R.array.wfcOperatorErrorNotificationMessages));
        float[] fArr = new float[floatArray.length];
        float[] fArr2 = new float[floatArray.length];
        for (int i = 0; i < floatArray.length; i++) {
            float f = this.mMaxBrightnessForNonHbmLux;
            fArr[i] = floatArray2[i] * f;
            fArr2[i] = f * floatArray3[i];
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

    public void start(float f) {
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", "Start");
        this.mBgHandler.obtainMessage(0, Float.valueOf(f)).sendToTarget();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void backgroundStart(float f) {
        readEvents();
        readAdaptiveBrightnessStats();
        SettingsObserver settingsObserver = new SettingsObserver(this.mBgHandler);
        this.mSettingsObserver = settingsObserver;
        this.mInjector.registerBrightnessModeObserver(this.mContentResolver, settingsObserver);
        startAdaptiveBrightnessStatsTracker();
        ((PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class)).registerUserActivityStateListener(this.mUserActivityStateListener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("com.samsung.intent.action.SETTINGS_SOFT_RESET");
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter2.addDataScheme("package");
        this.mBroadcastReceiver = new Receiver();
        this.mPackageBroadcastReceiver = new PackageBroadcastReceiver();
        this.mInjector.registerReceiver(this.mContext, this.mBroadcastReceiver, intentFilter);
        this.mInjector.registerReceiver(this.mContext, this.mPackageBroadcastReceiver, intentFilter2);
        this.mInjector.scheduleIdleJob(this.mContext);
        synchronized (this.mDataCollectionLock) {
            this.mLastBrightness = f;
            this.mStarted = true;
        }
        enableColorSampling();
    }

    public void stop() {
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", "Stop");
        synchronized (this.mDataCollectionLock) {
            if (this.mStarted) {
                this.mBgHandler.removeMessages(0);
                stopAdaptiveBrightnessStatsTracker();
                ((PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class)).unregisterUserActivityStateListener(this.mUserActivityStateListener);
                this.mInjector.unregisterBrightnessModeObserver(this.mContext, this.mSettingsObserver);
                this.mInjector.unregisterReceiver(this.mContext, this.mBroadcastReceiver);
                this.mInjector.unregisterReceiver(this.mContext, this.mPackageBroadcastReceiver);
                this.mInjector.cancelIdleJob(this.mContext);
                synchronized (this.mDataCollectionLock) {
                    this.mStarted = false;
                }
                disableColorSampling();
            }
        }
    }

    public void restartAdaptiveBrightnessStatsTracker(final boolean z) {
        this.mBgHandler.post(new Runnable() { // from class: com.android.server.display.AdaptiveBrightnessLongtermModelBuilder$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AdaptiveBrightnessLongtermModelBuilder.this.lambda$restartAdaptiveBrightnessStatsTracker$0(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restartAdaptiveBrightnessStatsTracker$0(boolean z) {
        readAdaptiveBrightnessStats();
        if (z) {
            setMaxWeight(0);
        }
        stopAdaptiveBrightnessStatsTracker();
        startAdaptiveBrightnessStatsTracker();
    }

    public void clearBrightnessEvents() {
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", "clearBrightnessEvents()");
        synchronized (this.mEventsLock) {
            this.mEvents.clear();
            AtomicFile file = this.mInjector.getFile("brightness_events_sec.xml");
            if (file != null && file.exists()) {
                file.delete();
            }
            AtomicFile file2 = this.mInjector.getFile("adaptive_brightness_stats_sec.xml");
            if (file2 != null && file2.exists()) {
                file2.delete();
            }
        }
    }

    public void persistAdaptiveBrightnessLongtermModelBuilderState() {
        scheduleWriteAdaptiveBrightnessLongtermModelBuilderState();
    }

    public void notifyBrightnessChanged(float f, boolean z, float f2, boolean z2, boolean z3, String str, Spline spline) {
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", String.format("notifyBrightnessChanged(brightness=%f, userInitiated=%b)", Float.valueOf(f), Boolean.valueOf(z)));
        this.mBgHandler.obtainMessage(1, z ? 1 : 0, 0, new BrightnessChangeValues(f, f2, z2, z3, this.mInjector.currentTimeMillis(), str, spline)).sendToTarget();
    }

    public final void handleBrightnessChanged(float f, boolean z, float f2, boolean z2, boolean z3, long j, String str, Spline spline) {
        DisplayedContentSample sampleColor;
        synchronized (this.mDataCollectionLock) {
            if (this.mStarted) {
                float f3 = this.mLastBrightness;
                this.mLastBrightness = f;
                this.mLastBrightnessSpline = spline;
                Slog.d("AdaptiveBrightnessLongtermModelBuilder", "handleBrightnessChanged: brightness: " + f + " userInitiated: " + z);
                if (!z) {
                    updateAdaptiveBrightnessStats(z);
                    return;
                }
                BrightnessChangeEvent.Builder builder = new BrightnessChangeEvent.Builder();
                builder.setBrightness(f);
                builder.setTimeStamp(j);
                builder.setPowerBrightnessFactor(f2);
                builder.setUserBrightnessPoint(z2);
                builder.setIsDefaultBrightnessConfig(z3);
                builder.setUniqueDisplayId(str);
                LightData lightData = this.mLastLightData;
                float[] fArr = {lightData.lux};
                long[] jArr = {lightData.timestamp};
                builder.setLuxValues(fArr);
                builder.setLuxTimestamps(jArr);
                builder.setBatteryLevel(this.mLastBatteryLevel);
                builder.setLastBrightness(f3);
                try {
                    ActivityTaskManager.RootTaskInfo focusedStack = this.mInjector.getFocusedStack();
                    if (focusedStack != null && focusedStack.topActivity != null) {
                        builder.setUserId(focusedStack.userId);
                        builder.setPackageName(focusedStack.topActivity.getPackageName());
                        builder.setNightMode(this.mInjector.isNightDisplayActivated(this.mContext));
                        builder.setColorTemperature(this.mInjector.getNightDisplayColorTemperature(this.mContext));
                        builder.setReduceBrightColors(this.mInjector.isReduceBrightColorsActivated(this.mContext));
                        builder.setReduceBrightColorsStrength(this.mInjector.getReduceBrightColorsStrength(this.mContext));
                        builder.setReduceBrightColorsOffset(this.mInjector.getReduceBrightColorsOffsetFactor(this.mContext) * f);
                        if (this.mColorSamplingEnabled && (sampleColor = this.mInjector.sampleColor(this.mNoFramesToSample)) != null && sampleColor.getSampleComponent(DisplayedContentSample.ColorComponent.CHANNEL2) != null) {
                            builder.setColorValues(sampleColor.getSampleComponent(DisplayedContentSample.ColorComponent.CHANNEL2), Math.round((sampleColor.getNumFrames() / this.mFrameRate) * 1000.0f));
                        }
                        BrightnessChangeEvent build = builder.build();
                        Slog.d("AdaptiveBrightnessLongtermModelBuilder", "Event " + build.brightness + " " + build.packageName);
                        synchronized (this.mEventsLock) {
                            this.mEventsDirty = true;
                            this.mEvents.append(build);
                        }
                        this.mLastBrightnessChangeEvent = build;
                        updateAdaptiveBrightnessStats(z);
                        return;
                    }
                    Slog.d("AdaptiveBrightnessLongtermModelBuilder", "Ignoring event due to null focusedTask.");
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public void notifyAmbientLuxChanged(float f) {
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", String.format("notifyAmbientLuxChanged(lux=%f)", Float.valueOf(f)));
        this.mBgHandler.obtainMessage(5, Float.floatToIntBits(f), 0).sendToTarget();
    }

    public final void handleAmbientLuxChanged(float f) {
        this.mLastAmbientLux = f;
        recordAmbientLuxEvent(f);
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", "handleAmbientLuxChanged: ambientLux: " + f);
        updateAdaptiveBrightnessStats(false);
    }

    public void notifyShortTermResetValid() {
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", "notifyShortTermResetValid()");
        this.mBgHandler.obtainMessage(7).sendToTarget();
    }

    public boolean isStarted() {
        boolean z;
        synchronized (this.mDataCollectionLock) {
            z = this.mStarted;
        }
        return z;
    }

    public final void handleShortTermModelValid() {
        AdaptiveBrightnessWeightStats.BrightnessWeights[] brightnessWeightsArr;
        AdaptiveBrightnessWeightStats.WeightStat[] weightStatArr;
        AdaptiveBrightnessWeightStats.WeightStat[] weightStatArr2;
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", "handleShortTermModelValid()");
        this.mAdaptiveBrightnessStatsTracker.summarizeStats(0);
        AdaptiveBrightnessWeightStats userStats = this.mAdaptiveBrightnessStatsTracker.getUserStats(0);
        if (userStats != null) {
            brightnessWeightsArr = userStats.getStats();
            weightStatArr2 = userStats.getTimeCollectorStats();
            weightStatArr = userStats.getContinuityCollectorStats();
        } else {
            brightnessWeightsArr = null;
            weightStatArr = null;
            weightStatArr2 = null;
        }
        if (brightnessWeightsArr == null || weightStatArr2 == null || weightStatArr == null) {
            return;
        }
        BrightnessConfiguration buildBrightnessConfiguration = buildBrightnessConfiguration(brightnessWeightsArr);
        if (buildBrightnessConfiguration != null) {
            DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights : brightnessWeightsArr) {
                arrayList.add(Float.toString(brightnessWeights.getWeight()));
            }
            for (AdaptiveBrightnessWeightStats.WeightStat weightStat : weightStatArr2) {
                arrayList2.add(Float.toString(weightStat.getWeight()));
            }
            for (AdaptiveBrightnessWeightStats.WeightStat weightStat2 : weightStatArr) {
                arrayList3.add(Float.toString(weightStat2.getWeight()));
            }
            displayManager.setBrightnessConfigurationForUser(buildBrightnessConfiguration, 0, "sbs", arrayList, arrayList2, arrayList3);
            return;
        }
        Slog.e("AdaptiveBrightnessLongtermModelBuilder", "handleShortTermModelValid: brightnessConfiguration is null");
    }

    public final void handleBrightnessBnrPackageCleared() {
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", "handleBrightnessBnrPackageCleared()");
        ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).resetBrightnessConfiguration();
    }

    public final BrightnessConfiguration buildBrightnessConfiguration(AdaptiveBrightnessWeightStats.BrightnessWeights[] brightnessWeightsArr) {
        float min;
        int length = brightnessWeightsArr.length;
        AdaptiveBrightnessWeightStats.BrightnessWeights[] brightnessWeightsArr2 = new AdaptiveBrightnessWeightStats.BrightnessWeights[length];
        for (int i = 0; i < length; i++) {
            brightnessWeightsArr2[i] = brightnessWeightsArr[i].copy();
        }
        Arrays.sort(brightnessWeightsArr2, this.mComparatorDecsendingForWeight);
        int i2 = 0;
        while (i2 < length) {
            float lux = brightnessWeightsArr2[i2].getLux();
            float brightness = brightnessWeightsArr2[i2].getBrightness();
            i2++;
            for (int i3 = i2; i3 < length; i3++) {
                float lux2 = brightnessWeightsArr2[i3].getLux();
                float brightness2 = brightnessWeightsArr2[i3].getBrightness();
                if (PowerManagerUtil.USE_PERMISSIBLE_RATIO_FOR_LONGTERM_MODEL) {
                    float permissibleMinimumRatio = BrightnessMappingStrategy.permissibleMinimumRatio(lux2, lux);
                    float permissibleRatio = BrightnessMappingStrategy.permissibleRatio(lux2, lux);
                    if (lux2 > lux) {
                        min = MathUtils.constrain(brightness2, permissibleMinimumRatio * brightness, permissibleRatio * brightness);
                    } else {
                        min = MathUtils.constrain(brightness2, permissibleRatio * brightness, permissibleMinimumRatio * brightness);
                    }
                } else if (lux2 > lux) {
                    min = MathUtils.max(brightness2, brightness);
                } else {
                    min = MathUtils.min(brightness2, brightness);
                }
                if (Float.compare(brightness2, min) != 0) {
                    brightnessWeightsArr2[i3].setBrightness(min);
                }
            }
        }
        for (int i4 = 0; i4 < length; i4++) {
            float brightness3 = brightnessWeightsArr2[i4].getBrightness();
            float f = this.mMaxBrightnessForNonHbmLux;
            if (brightness3 > f) {
                brightnessWeightsArr2[i4].setBrightness(f);
            }
        }
        Arrays.sort(brightnessWeightsArr2, this.mComparatorAscendingForLux);
        printArrayBrightnessWeights(brightnessWeightsArr2, "lux sorted: ");
        float[] fArr = new float[length];
        float[] fArr2 = new float[length];
        boolean[] zArr = new boolean[this.mMaximumBrightnessLimitCount.length];
        for (int i5 = 0; i5 < length; i5++) {
            fArr[i5] = brightnessWeightsArr2[i5].getLux();
            fArr2[i5] = brightnessWeightsArr2[i5].getBrightness();
            float interpolate = this.mMinimumBrightnessSpline.interpolate(fArr[i5]);
            float interpolate2 = this.mMaximumBrightnessSpline.interpolate(fArr[i5]);
            float constrain = MathUtils.constrain(fArr2[i5], interpolate, interpolate2);
            if (!BrightnessSynchronizer.floatEquals(fArr2[i5], constrain)) {
                Slog.d("AdaptiveBrightnessLongtermModelBuilder", "buildBrightnessConfiguration: " + fArr[i5] + " lux, " + fArr2[i5] + " -> " + constrain + " nits Limit: (" + interpolate + " ~ " + interpolate2 + ")");
                if (fArr2[i5] > interpolate2) {
                    int i6 = 0;
                    while (true) {
                        float[] fArr3 = this.mMaximumBrightnessLimitLux;
                        if (i6 >= fArr3.length) {
                            break;
                        }
                        if (fArr3[i6] < fArr[i5]) {
                            i6++;
                        } else if (!zArr[i6]) {
                            int[] iArr = this.mMaximumBrightnessLimitCount;
                            iArr[i6] = iArr[i6] + 1;
                            zArr[i6] = true;
                        }
                    }
                }
                fArr2[i5] = constrain;
            }
        }
        try {
            BrightnessConfiguration.Builder builder = new BrightnessConfiguration.Builder(fArr, fArr2);
            builder.setDescription("sbs:0");
            return builder.build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final void printArrayBrightnessWeights(AdaptiveBrightnessWeightStats.BrightnessWeights[] brightnessWeightsArr, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        for (AdaptiveBrightnessWeightStats.BrightnessWeights brightnessWeights : brightnessWeightsArr) {
            sb.append(String.format("%9s", brightnessWeights));
        }
        sb.append(System.lineSeparator());
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", str + sb.toString());
    }

    public final void setMaxWeight(int i) {
        this.mAdaptiveBrightnessStatsTracker.setMaxWeight(i);
    }

    public final void startAdaptiveBrightnessStatsTracker() {
        if (this.mAdaptiveBrightnessStatsTrackerStarted || !this.mInjector.isInteractive(this.mContext) || !this.mInjector.isBrightnessModeAutomatic(this.mContentResolver) || this.mTestModeEnabled) {
            return;
        }
        this.mAdaptiveBrightnessStatsTracker.start(((PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class)).getLastUserActivityState() == 1);
        this.mAdaptiveBrightnessStatsTrackerStarted = true;
    }

    public final void stopAdaptiveBrightnessStatsTracker() {
        if (this.mAdaptiveBrightnessStatsTrackerStarted) {
            this.mAdaptiveBrightnessStatsTracker.stop();
            this.mAdaptiveBrightnessStatsTrackerStarted = false;
        }
    }

    public final void scheduleWriteAdaptiveBrightnessLongtermModelBuilderState() {
        if (this.mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled) {
            return;
        }
        this.mBgHandler.post(new Runnable() { // from class: com.android.server.display.AdaptiveBrightnessLongtermModelBuilder$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AdaptiveBrightnessLongtermModelBuilder.this.m25x371166b2();
            }
        });
        this.mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: lambda$scheduleWriteAdaptiveBrightnessLongtermModelBuilderState$1 */
    public /* synthetic */ void m25x371166b2() {
        this.mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled = false;
        writeEvents();
        writeAdaptiveBrightnessStats();
    }

    public final void writeEvents() {
        FileOutputStream fileOutputStream;
        synchronized (this.mEventsLock) {
            if (this.mEventsDirty) {
                AtomicFile file = this.mInjector.getFile("brightness_events_sec.xml");
                if (file == null) {
                    return;
                }
                if (this.mEvents.isEmpty()) {
                    if (file.exists()) {
                        file.delete();
                    }
                    this.mEventsDirty = false;
                } else {
                    try {
                        fileOutputStream = file.startWrite();
                    } catch (IOException e) {
                        e = e;
                        fileOutputStream = null;
                    }
                    try {
                        writeEventsLocked(fileOutputStream);
                        file.finishWrite(fileOutputStream);
                        this.mEventsDirty = false;
                    } catch (IOException e2) {
                        e = e2;
                        file.failWrite(fileOutputStream);
                        Slog.e("AdaptiveBrightnessLongtermModelBuilder", "Failed to write change mEvents.", e);
                    }
                }
            }
        }
    }

    public final void writeAdaptiveBrightnessStats() {
        FileOutputStream fileOutputStream;
        AtomicFile file = this.mInjector.getFile("adaptive_brightness_stats_sec.xml");
        if (file == null) {
            Slog.d("AdaptiveBrightnessLongtermModelBuilder", "writeAdaptiveBrightnessStats: writeTo: " + file);
            return;
        }
        try {
            try {
                fileOutputStream = file.startWrite();
            } catch (IOException e) {
                e = e;
                fileOutputStream = null;
            }
            try {
                this.mAdaptiveBrightnessStatsTracker.writeStats(fileOutputStream);
                file.finishWrite(fileOutputStream);
            } catch (IOException e2) {
                e = e2;
                file.failWrite(fileOutputStream);
                Slog.e("AdaptiveBrightnessLongtermModelBuilder", "Failed to write ambient brightness stats. IOException", e);
            }
        } catch (Exception e3) {
            Slog.e("AdaptiveBrightnessLongtermModelBuilder", "Failed to write ambient brightness stats. Exception", e3);
        }
    }

    public final AtomicFile getFileWithLegacyFallback(String str) {
        AtomicFile legacyFile;
        AtomicFile file = this.mInjector.getFile(str);
        if (file == null || file.exists() || (legacyFile = this.mInjector.getLegacyFile(str)) == null || !legacyFile.exists()) {
            return file;
        }
        Slog.i("AdaptiveBrightnessLongtermModelBuilder", "Reading " + str + " from old location");
        return legacyFile;
    }

    public final void readEvents() {
        synchronized (this.mEventsLock) {
            this.mEventsDirty = true;
            this.mEvents.clear();
            AtomicFile fileWithLegacyFallback = getFileWithLegacyFallback("brightness_events_sec.xml");
            if (fileWithLegacyFallback != null && fileWithLegacyFallback.exists()) {
                FileInputStream fileInputStream = null;
                try {
                    try {
                        fileInputStream = fileWithLegacyFallback.openRead();
                        readEventsLocked(fileInputStream);
                    } catch (IOException e) {
                        fileWithLegacyFallback.delete();
                        Slog.e("AdaptiveBrightnessLongtermModelBuilder", "Failed to read change mEvents.", e);
                    }
                } finally {
                    IoUtils.closeQuietly(fileInputStream);
                }
            }
        }
    }

    public final void readAdaptiveBrightnessStats() {
        FileInputStream fileInputStream = null;
        this.mAdaptiveBrightnessStatsTracker = new AdaptiveBrightnessStatsTracker(this.mUserManager, null, this.mBrightnessMapper);
        AtomicFile fileWithLegacyFallback = getFileWithLegacyFallback("adaptive_brightness_stats_sec.xml");
        if (fileWithLegacyFallback == null || !fileWithLegacyFallback.exists()) {
            return;
        }
        try {
            try {
                fileInputStream = fileWithLegacyFallback.openRead();
                this.mAdaptiveBrightnessStatsTracker.readStats(fileInputStream);
            } catch (IOException e) {
                fileWithLegacyFallback.delete();
                Slog.e("AdaptiveBrightnessLongtermModelBuilder", "Failed to read ambient brightness stats.", e);
            }
        } finally {
            IoUtils.closeQuietly(fileInputStream);
        }
    }

    public void writeEventsLocked(OutputStream outputStream) {
        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(outputStream);
        resolveSerializer.startDocument((String) null, Boolean.TRUE);
        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        resolveSerializer.startTag((String) null, "events");
        BrightnessChangeEvent[] brightnessChangeEventArr = (BrightnessChangeEvent[]) this.mEvents.toArray();
        this.mEvents.clear();
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", "Writing events " + brightnessChangeEventArr.length);
        long currentTimeMillis = this.mInjector.currentTimeMillis() - MAX_EVENT_AGE;
        for (int i = 0; i < brightnessChangeEventArr.length; i++) {
            int userSerialNumber = this.mInjector.getUserSerialNumber(this.mUserManager, brightnessChangeEventArr[i].userId);
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
                    for (int i2 = 0; i2 < brightnessChangeEventArr[i].luxValues.length; i2++) {
                        if (i2 > 0) {
                            sb.append(',');
                            sb2.append(',');
                        }
                        sb.append(Float.toString(brightnessChangeEventArr[i].luxValues[i2]));
                        sb2.append(Long.toString(brightnessChangeEventArr[i].luxTimestamps[i2]));
                    }
                    resolveSerializer.attribute((String) null, "lux", sb.toString());
                    resolveSerializer.attribute((String) null, "luxTimestamps", sb2.toString());
                    BrightnessChangeEvent brightnessChangeEvent2 = brightnessChangeEventArr[i];
                    long[] jArr = brightnessChangeEvent2.colorValueBuckets;
                    if (jArr != null && jArr.length > 0) {
                        resolveSerializer.attributeLong((String) null, "colorSampleDuration", brightnessChangeEvent2.colorSampleDuration);
                        StringBuilder sb3 = new StringBuilder();
                        for (int i3 = 0; i3 < brightnessChangeEventArr[i].colorValueBuckets.length; i3++) {
                            if (i3 > 0) {
                                sb3.append(',');
                            }
                            sb3.append(Long.toString(brightnessChangeEventArr[i].colorValueBuckets[i3]));
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

    public void readEventsLocked(InputStream inputStream) {
        int next;
        int i;
        try {
            TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(inputStream);
            do {
                next = resolvePullParser.next();
                i = 1;
                if (next == 1) {
                    break;
                }
            } while (next != 2);
            String name = resolvePullParser.getName();
            if (!"events".equals(name)) {
                throw new XmlPullParserException("Events not found in brightness tracker file " + name);
            }
            long currentTimeMillis = this.mInjector.currentTimeMillis() - MAX_EVENT_AGE;
            int depth = resolvePullParser.getDepth();
            while (true) {
                int next2 = resolvePullParser.next();
                if (next2 == i) {
                    return;
                }
                if (next2 == 3 && resolvePullParser.getDepth() <= depth) {
                    return;
                }
                if (next2 != 3 && next2 != 4 && "event".equals(resolvePullParser.getName())) {
                    BrightnessChangeEvent.Builder builder = new BrightnessChangeEvent.Builder();
                    builder.setBrightness(resolvePullParser.getAttributeFloat((String) null, "nits"));
                    builder.setTimeStamp(resolvePullParser.getAttributeLong((String) null, "timestamp"));
                    builder.setPackageName(resolvePullParser.getAttributeValue((String) null, "packageName"));
                    builder.setUserId(this.mInjector.getUserId(this.mUserManager, resolvePullParser.getAttributeInt((String) null, "user")));
                    String attributeValue = resolvePullParser.getAttributeValue((String) null, "uniqueDisplayId");
                    if (attributeValue == null) {
                        attributeValue = "";
                    }
                    builder.setUniqueDisplayId(attributeValue);
                    builder.setBatteryLevel(resolvePullParser.getAttributeFloat((String) null, "batteryLevel"));
                    builder.setNightMode(resolvePullParser.getAttributeBoolean((String) null, "nightMode"));
                    builder.setColorTemperature(resolvePullParser.getAttributeInt((String) null, "colorTemperature"));
                    builder.setReduceBrightColors(resolvePullParser.getAttributeBoolean((String) null, "reduceBrightColors"));
                    builder.setReduceBrightColorsStrength(resolvePullParser.getAttributeInt((String) null, "reduceBrightColorsStrength"));
                    builder.setReduceBrightColorsOffset(resolvePullParser.getAttributeFloat((String) null, "reduceBrightColorsOffset"));
                    builder.setLastBrightness(resolvePullParser.getAttributeFloat((String) null, "lastNits"));
                    String attributeValue2 = resolvePullParser.getAttributeValue((String) null, "lux");
                    String attributeValue3 = resolvePullParser.getAttributeValue((String) null, "luxTimestamps");
                    String[] split = attributeValue2.split(",");
                    String[] split2 = attributeValue3.split(",");
                    if (split.length == split2.length) {
                        int length = split.length;
                        float[] fArr = new float[length];
                        long[] jArr = new long[split.length];
                        for (int i2 = 0; i2 < length; i2++) {
                            fArr[i2] = Float.parseFloat(split[i2]);
                            jArr[i2] = Long.parseLong(split2[i2]);
                        }
                        builder.setLuxValues(fArr);
                        builder.setLuxTimestamps(jArr);
                        builder.setIsDefaultBrightnessConfig(resolvePullParser.getAttributeBoolean((String) null, "defaultConfig", false));
                        builder.setPowerBrightnessFactor(resolvePullParser.getAttributeFloat((String) null, "powerSaveFactor", 1.0f));
                        builder.setUserBrightnessPoint(resolvePullParser.getAttributeBoolean((String) null, "userPoint", false));
                        long attributeLong = resolvePullParser.getAttributeLong((String) null, "colorSampleDuration", -1L);
                        String attributeValue4 = resolvePullParser.getAttributeValue((String) null, "colorValueBuckets");
                        if (attributeLong != -1 && attributeValue4 != null) {
                            String[] split3 = attributeValue4.split(",");
                            int length2 = split3.length;
                            long[] jArr2 = new long[length2];
                            for (int i3 = 0; i3 < length2; i3++) {
                                jArr2[i3] = Long.parseLong(split3[i3]);
                            }
                            builder.setColorValues(jArr2, attributeLong);
                        }
                        BrightnessChangeEvent build = builder.build();
                        Slog.i("AdaptiveBrightnessLongtermModelBuilder", "Read event " + build.brightness + " " + build.packageName);
                        if (build.userId != -1 && build.timeStamp > currentTimeMillis && build.luxValues.length > 0) {
                            this.mEvents.append(build);
                        }
                        i = 1;
                    }
                }
            }
        } catch (IOException | NullPointerException | NumberFormatException | XmlPullParserException e) {
            this.mEvents = new RingBuffer(BrightnessChangeEvent.class, 20);
            Slog.e("AdaptiveBrightnessLongtermModelBuilder", "Failed to parse brightness event", e);
            throw new IOException("failed to parse file", e);
        }
    }

    public void dump(final PrintWriter printWriter) {
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
            printWriter.println("  mEventsDirty=" + this.mEventsDirty);
            printWriter.println("  mEvents.size=" + this.mEvents.size());
            BrightnessChangeEvent[] brightnessChangeEventArr = (BrightnessChangeEvent[]) this.mEvents.toArray();
            i = 0;
            for (int i2 = 0; i2 < brightnessChangeEventArr.length; i2++) {
                printWriter.print("    " + FORMAT.format(new Date(brightnessChangeEventArr[i2].timeStamp)));
                printWriter.print(", userId=" + brightnessChangeEventArr[i2].userId);
                printWriter.print(", " + brightnessChangeEventArr[i2].lastBrightness + "->" + brightnessChangeEventArr[i2].brightness);
                StringBuilder sb = new StringBuilder();
                sb.append(", isUserSetBrightness=");
                sb.append(brightnessChangeEventArr[i2].isUserSetBrightness);
                printWriter.print(sb.toString());
                printWriter.print(", powerBrightnessFactor=" + brightnessChangeEventArr[i2].powerBrightnessFactor);
                printWriter.print(", isDefaultBrightnessConfig=" + brightnessChangeEventArr[i2].isDefaultBrightnessConfig);
                printWriter.print(" {");
                for (int i3 = 0; i3 < brightnessChangeEventArr[i2].luxValues.length; i3++) {
                    if (i3 != 0) {
                        printWriter.print(", ");
                    }
                    printWriter.print("(" + brightnessChangeEventArr[i2].luxValues[i3] + "," + brightnessChangeEventArr[i2].luxTimestamps[i3] + ")");
                }
                printWriter.println("}");
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
        printWriter.println("  mMaxBrightnessForNonHbmLux=" + this.mMaxBrightnessForNonHbmLux);
        printWriter.println("  mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled=" + this.mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled);
        this.mBgHandler.runWithScissors(new Runnable() { // from class: com.android.server.display.AdaptiveBrightnessLongtermModelBuilder$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AdaptiveBrightnessLongtermModelBuilder.this.lambda$dump$2(printWriter);
            }
        }, 1000L);
        if (this.mAdaptiveBrightnessStatsTracker != null) {
            printWriter.println();
            this.mAdaptiveBrightnessStatsTracker.dump(printWriter);
        }
    }

    /* renamed from: dumpLocal, reason: merged with bridge method [inline-methods] */
    public final void lambda$dump$2(PrintWriter printWriter) {
        printWriter.println("  mAdaptiveBrightnessStatsTrackerStarted=" + this.mAdaptiveBrightnessStatsTrackerStarted);
        printWriter.println("  mColorSamplingEnabled=" + this.mColorSamplingEnabled);
        printWriter.println("  mNoFramesToSample=" + this.mNoFramesToSample);
        printWriter.println("  mFrameRate=" + this.mFrameRate);
    }

    public final void enableColorSampling() {
        BrightnessConfiguration brightnessConfiguration;
        if (this.mInjector.isBrightnessModeAutomatic(this.mContentResolver) && this.mInjector.isInteractive(this.mContext) && !this.mColorSamplingEnabled && (brightnessConfiguration = this.mBrightnessConfiguration) != null && brightnessConfiguration.shouldCollectColorSamples()) {
            float frameRate = this.mInjector.getFrameRate(this.mContext);
            this.mFrameRate = frameRate;
            if (frameRate <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                Slog.wtf("AdaptiveBrightnessLongtermModelBuilder", "Default display has a zero or negative framerate.");
                return;
            }
            this.mNoFramesToSample = (int) (frameRate * COLOR_SAMPLE_DURATION);
            DisplayedContentSamplingAttributes samplingAttributes = this.mInjector.getSamplingAttributes();
            if (samplingAttributes != null) {
                Slog.d("AdaptiveBrightnessLongtermModelBuilder", "Color sampling mask=0x" + Integer.toHexString(samplingAttributes.getComponentMask()) + " dataSpace=0x" + Integer.toHexString(samplingAttributes.getDataspace()) + " pixelFormat=0x" + Integer.toHexString(samplingAttributes.getPixelFormat()));
            }
            if (samplingAttributes != null && samplingAttributes.getPixelFormat() == 55 && (samplingAttributes.getComponentMask() & 4) != 0) {
                this.mColorSamplingEnabled = this.mInjector.enableColorSampling(true, this.mNoFramesToSample);
                Slog.i("AdaptiveBrightnessLongtermModelBuilder", "turning on color sampling for " + this.mNoFramesToSample + " frames, success=" + this.mColorSamplingEnabled);
            }
            if (this.mColorSamplingEnabled && this.mDisplayListener == null) {
                DisplayListener displayListener = new DisplayListener();
                this.mDisplayListener = displayListener;
                this.mInjector.registerDisplayListener(this.mContext, displayListener, this.mBgHandler);
            }
        }
    }

    public final void disableColorSampling() {
        if (this.mColorSamplingEnabled) {
            this.mInjector.enableColorSampling(false, 0);
            this.mColorSamplingEnabled = false;
            DisplayListener displayListener = this.mDisplayListener;
            if (displayListener != null) {
                this.mInjector.unRegisterDisplayListener(this.mContext, displayListener);
                this.mDisplayListener = null;
            }
            Slog.i("AdaptiveBrightnessLongtermModelBuilder", "turning off color sampling");
        }
    }

    public final void updateColorSampling() {
        if (this.mColorSamplingEnabled && this.mInjector.getFrameRate(this.mContext) != this.mFrameRate) {
            disableColorSampling();
            enableColorSampling();
        }
    }

    public final void recordAmbientLuxEvent(float f) {
        synchronized (this.mDataCollectionLock) {
            Slog.v("AdaptiveBrightnessLongtermModelBuilder", "Ambient Lux event " + f);
            this.mLastLightData.timestamp = this.mInjector.currentTimeMillis();
            this.mLastLightData.lux = f;
        }
    }

    public final void updateAdaptiveBrightnessStats(boolean z) {
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", "updateAdaptiveBrightnessStats: l:" + this.mLastAmbientLux + " b:" + this.mLastBrightness + " u: " + z);
        this.mAdaptiveBrightnessStatsTracker.add(0, this.mLastAmbientLux, this.mLastBrightness, this.mLastBrightnessSpline, this.mLastBrightnessChangeEvent, z);
    }

    public final void batteryLevelChanged(int i, int i2) {
        synchronized (this.mDataCollectionLock) {
            this.mLastBatteryLevel = i / i2;
        }
    }

    public void addBrightnessWeightDirectly(float f, float f2, float f3, float f4) {
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", "addBrightnessWeightDirectly: l:" + f + " b:" + f2 + " d:" + f3 + " c:" + f4);
        this.mBrightnessMapper.addUserDataPoint(f, f2);
        Spline brightnessSpline = this.mBrightnessMapper.getBrightnessSpline();
        this.mLastBrightnessSpline = brightnessSpline;
        this.mAdaptiveBrightnessStatsTracker.addDirectly(0, f, f2, f3, brightnessSpline);
    }

    public void setTestModeEnabled(boolean z) {
        Slog.d("AdaptiveBrightnessLongtermModelBuilder", "setTestModeEnabled:" + z);
        if (this.mTestModeEnabled != z) {
            this.mTestModeEnabled = z;
            if (z) {
                this.mBgHandler.obtainMessage(2).sendToTarget();
            } else {
                this.mBgHandler.obtainMessage(3).sendToTarget();
            }
        }
    }

    public int[] getBrightnessLearningMaxLimitCount() {
        int[] iArr = (int[]) this.mMaximumBrightnessLimitCount.clone();
        Arrays.fill(this.mMaximumBrightnessLimitCount, 0);
        return iArr;
    }

    public final class DisplayListener implements DisplayManager.DisplayListener {
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        public DisplayListener() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            if (i == 0) {
                AdaptiveBrightnessLongtermModelBuilder.this.updateColorSampling();
            }
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            Slog.v("AdaptiveBrightnessLongtermModelBuilder", "settings change " + uri);
            if (AdaptiveBrightnessLongtermModelBuilder.this.mInjector.isBrightnessModeAutomatic(AdaptiveBrightnessLongtermModelBuilder.this.mContentResolver)) {
                AdaptiveBrightnessLongtermModelBuilder.this.mBgHandler.obtainMessage(3).sendToTarget();
            } else {
                AdaptiveBrightnessLongtermModelBuilder.this.mBgHandler.obtainMessage(2).sendToTarget();
            }
        }
    }

    public final class Receiver extends BroadcastReceiver {
        public Receiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Slog.d("AdaptiveBrightnessLongtermModelBuilder", "Received " + intent.getAction());
            String action = intent.getAction();
            if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                AdaptiveBrightnessLongtermModelBuilder.this.stop();
                AdaptiveBrightnessLongtermModelBuilder.this.scheduleWriteAdaptiveBrightnessLongtermModelBuilderState();
                return;
            }
            if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                int intExtra = intent.getIntExtra("level", -1);
                int intExtra2 = intent.getIntExtra("scale", 0);
                if (intExtra == -1 || intExtra2 == 0) {
                    return;
                }
                AdaptiveBrightnessLongtermModelBuilder.this.batteryLevelChanged(intExtra, intExtra2);
                return;
            }
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                AdaptiveBrightnessLongtermModelBuilder.this.mBgHandler.obtainMessage(2).sendToTarget();
            } else if ("android.intent.action.SCREEN_ON".equals(action)) {
                AdaptiveBrightnessLongtermModelBuilder.this.mBgHandler.obtainMessage(3).sendToTarget();
            } else if ("com.samsung.intent.action.SETTINGS_SOFT_RESET".equals(action)) {
                AdaptiveBrightnessLongtermModelBuilder.this.mBgHandler.obtainMessage(8).sendToTarget();
            }
        }
    }

    public final class PackageBroadcastReceiver extends BroadcastReceiver {
        public PackageBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Slog.d("AdaptiveBrightnessLongtermModelBuilder", "Received " + intent.getAction());
            if ("android.intent.action.PACKAGE_DATA_CLEARED".equals(intent.getAction()) && "com.samsung.android.brightnessbackupservice".equals(intent.getData().getSchemeSpecificPart())) {
                AdaptiveBrightnessLongtermModelBuilder.this.mBgHandler.obtainMessage(8).sendToTarget();
            }
        }
    }

    public final class ModelBuilderHandler extends Handler {
        public ModelBuilderHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z = false;
            switch (message.what) {
                case 0:
                    AdaptiveBrightnessLongtermModelBuilder.this.backgroundStart(((Float) message.obj).floatValue());
                    break;
                case 1:
                    BrightnessChangeValues brightnessChangeValues = (BrightnessChangeValues) message.obj;
                    AdaptiveBrightnessLongtermModelBuilder.this.handleBrightnessChanged(brightnessChangeValues.brightness, message.arg1 == 1, brightnessChangeValues.powerBrightnessFactor, brightnessChangeValues.isUserSetBrightness, brightnessChangeValues.isDefaultBrightnessConfig, brightnessChangeValues.timestamp, brightnessChangeValues.uniqueDisplayId, brightnessChangeValues.brightnessSpline);
                    break;
                case 2:
                    AdaptiveBrightnessLongtermModelBuilder.this.stopAdaptiveBrightnessStatsTracker();
                    AdaptiveBrightnessLongtermModelBuilder.this.disableColorSampling();
                    break;
                case 3:
                    AdaptiveBrightnessLongtermModelBuilder.this.startAdaptiveBrightnessStatsTracker();
                    AdaptiveBrightnessLongtermModelBuilder.this.enableColorSampling();
                    break;
                case 4:
                    AdaptiveBrightnessLongtermModelBuilder.this.mBrightnessConfiguration = (BrightnessConfiguration) message.obj;
                    if (AdaptiveBrightnessLongtermModelBuilder.this.mBrightnessConfiguration != null && AdaptiveBrightnessLongtermModelBuilder.this.mBrightnessConfiguration.shouldCollectColorSamples()) {
                        z = true;
                    }
                    if (z && !AdaptiveBrightnessLongtermModelBuilder.this.mColorSamplingEnabled) {
                        AdaptiveBrightnessLongtermModelBuilder.this.enableColorSampling();
                        break;
                    } else if (!z && AdaptiveBrightnessLongtermModelBuilder.this.mColorSamplingEnabled) {
                        AdaptiveBrightnessLongtermModelBuilder.this.disableColorSampling();
                        break;
                    }
                    break;
                case 5:
                    AdaptiveBrightnessLongtermModelBuilder.this.handleAmbientLuxChanged(Float.intBitsToFloat(message.arg1));
                    break;
                case 7:
                    AdaptiveBrightnessLongtermModelBuilder.this.handleShortTermModelValid();
                    break;
                case 8:
                    AdaptiveBrightnessLongtermModelBuilder.this.handleBrightnessBnrPackageCleared();
                    break;
            }
        }
    }

    public class BrightnessChangeValues {
        public final float brightness;
        public final Spline brightnessSpline;
        public final boolean isDefaultBrightnessConfig;
        public final boolean isUserSetBrightness;
        public final float powerBrightnessFactor;
        public final long timestamp;
        public final String uniqueDisplayId;

        public BrightnessChangeValues(float f, float f2, boolean z, boolean z2, long j, String str, Spline spline) {
            this.brightness = f;
            this.powerBrightnessFactor = f2;
            this.isUserSetBrightness = z;
            this.isDefaultBrightnessConfig = z2;
            this.timestamp = j;
            this.uniqueDisplayId = str;
            this.brightnessSpline = spline;
        }
    }

    class Injector {
        public void registerBrightnessModeObserver(ContentResolver contentResolver, ContentObserver contentObserver) {
            contentResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness_mode"), false, contentObserver, -1);
        }

        public void unregisterBrightnessModeObserver(Context context, ContentObserver contentObserver) {
            context.getContentResolver().unregisterContentObserver(contentObserver);
        }

        public void registerReceiver(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
            context.registerReceiver(broadcastReceiver, intentFilter);
        }

        public void unregisterReceiver(Context context, BroadcastReceiver broadcastReceiver) {
            context.unregisterReceiver(broadcastReceiver);
        }

        public Handler getBackgroundHandler() {
            return BackgroundThread.getHandler();
        }

        public boolean isBrightnessModeAutomatic(ContentResolver contentResolver) {
            return Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2) == 1;
        }

        public AtomicFile getFile(String str) {
            return new AtomicFile(new File(Environment.getDataSystemDirectory(), str));
        }

        public AtomicFile getLegacyFile(String str) {
            return new AtomicFile(new File(Environment.getDataSystemDeDirectory(), str));
        }

        public long currentTimeMillis() {
            return System.currentTimeMillis();
        }

        public int getUserSerialNumber(UserManager userManager, int i) {
            return userManager.getUserSerialNumber(i);
        }

        public int getUserId(UserManager userManager, int i) {
            return userManager.getUserHandle(i);
        }

        public ActivityTaskManager.RootTaskInfo getFocusedStack() {
            return ActivityTaskManager.getService().getFocusedRootTaskInfo();
        }

        public void scheduleIdleJob(Context context) {
            BrightnessIdleJob.scheduleJob(context);
        }

        public void cancelIdleJob(Context context) {
            BrightnessIdleJob.cancelJob(context);
        }

        public boolean isInteractive(Context context) {
            return ((PowerManager) context.getSystemService(PowerManager.class)).isInteractive();
        }

        public int getNightDisplayColorTemperature(Context context) {
            return ((ColorDisplayManager) context.getSystemService(ColorDisplayManager.class)).getNightDisplayColorTemperature();
        }

        public boolean isNightDisplayActivated(Context context) {
            return ((ColorDisplayManager) context.getSystemService(ColorDisplayManager.class)).isNightDisplayActivated();
        }

        public int getReduceBrightColorsStrength(Context context) {
            return ((ColorDisplayManager) context.getSystemService(ColorDisplayManager.class)).getReduceBrightColorsStrength();
        }

        public float getReduceBrightColorsOffsetFactor(Context context) {
            return ((ColorDisplayManager) context.getSystemService(ColorDisplayManager.class)).getReduceBrightColorsOffsetFactor();
        }

        public boolean isReduceBrightColorsActivated(Context context) {
            return ((ColorDisplayManager) context.getSystemService(ColorDisplayManager.class)).isReduceBrightColorsActivated();
        }

        public DisplayedContentSample sampleColor(int i) {
            return ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class)).getDisplayedContentSample(0, i, 0L);
        }

        public float getFrameRate(Context context) {
            return ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(0).getRefreshRate();
        }

        public DisplayedContentSamplingAttributes getSamplingAttributes() {
            return ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class)).getDisplayedContentSamplingAttributes(0);
        }

        public boolean enableColorSampling(boolean z, int i) {
            return ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class)).setDisplayedContentSamplingEnabled(0, z, 4, i);
        }

        public void registerDisplayListener(Context context, DisplayManager.DisplayListener displayListener, Handler handler) {
            ((DisplayManager) context.getSystemService(DisplayManager.class)).registerDisplayListener(displayListener, handler);
        }

        public void unRegisterDisplayListener(Context context, DisplayManager.DisplayListener displayListener) {
            ((DisplayManager) context.getSystemService(DisplayManager.class)).unregisterDisplayListener(displayListener);
        }
    }
}
