package com.android.server.power;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BatteryManagerInternal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SemHqmManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.SparseArray;

import com.android.internal.display.BrightnessSynchronizer;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;

import org.json.JSONException;
import org.json.JSONObject;

public final class HqmDataDispatcher {
    public final BatteryManagerInternal mBatteryManagerInternal;
    public final Context mContext;
    public final SparseArray mDisplayStats = new SparseArray();
    public float mGlobalBrightness = Float.NaN;
    public final DispatchHandler mHandler;
    public final SemHqmManager mHqmManager;

    public final class DispatchHandler extends Handler {
        public DispatchHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == 0) {
                HqmDataDispatcher.this.sendDataSetToHqm("DPUC", (String) message.obj);
            } else {
                Slog.e("HqmDataDispatcher", "Unknown message " + message.what);
            }
        }
    }

    public final class DisplayStat {
        public static final int BRIGHTNESS_DIM =
                Math.max(
                        0,
                        Resources.getSystem()
                                .getInteger(R.integer.config_vibrationWaveformRampStepDuration));
        public static final int BRIGHTNESS_HIGHEST =
                Resources.getSystem()
                        .getInteger(R.integer.config_vibratorControlServiceDumpSizeLimit);
        public final int mDisplayType;
        public long mScreenBrightnessHighestDuration;
        public boolean mScreenBrightnessHighestStarted;
        public long mScreenCurtainCount;
        public long mScreenCurtainDuration;
        public boolean mScreenCurtainEnabled;
        public int mScreenState = 0;
        public int mCurrentBrightnessRange = -1;
        public final Timer mScreenOnTimer = new Timer();
        public final Timer[] mScreenBrightnessTimers = new Timer[3];
        public final long[] mBrightnessDuration = new long[3];
        public final Timer mScreenCurtainTimer = new Timer();
        public final Timer mScreenBrightnessHighestTimer = new Timer();
        public long mScreenOnCount = 0;
        public long mScreenOnDuration = 0;

        public DisplayStat(int i) {
            this.mDisplayType = i;
            for (int i2 = 0; i2 < 3; i2++) {
                this.mScreenBrightnessTimers[i2] = new Timer();
            }
            this.mScreenCurtainCount = 0L;
            this.mScreenCurtainDuration = 0L;
            this.mScreenBrightnessHighestDuration = 0L;
            this.mScreenBrightnessHighestStarted = false;
        }
    }

    public abstract class HqmDataDispatcherHolder {
        public static HqmDataDispatcher INSTANCE;
    }

    public final class HqmDataSetBuilder {
        public final JSONObject mJsonObj = new JSONObject();

        public final void put(long j, String str) {
            try {
                this.mJsonObj.put(str, String.valueOf(j));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public final String toString() {
            return this.mJsonObj.toString();
        }
    }

    public final class HqmRequestReceiver extends BroadcastReceiver {
        public HqmRequestReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            HqmDataDispatcher hqmDataDispatcher = HqmDataDispatcher.this;
            hqmDataDispatcher.getClass();
            HqmDataSetBuilder hqmDataSetBuilder = new HqmDataSetBuilder();
            long j = 0;
            long j2 = 0;
            for (int i = 0; i < hqmDataDispatcher.mDisplayStats.size(); i++) {
                j2 += ((DisplayStat) hqmDataDispatcher.mDisplayStats.get(i)).mScreenOnCount;
                j += ((DisplayStat) hqmDataDispatcher.mDisplayStats.get(i)).mScreenOnDuration;
            }
            hqmDataSetBuilder.put(j, "LOD");
            hqmDataSetBuilder.put(j2, "LOCNT");
            long[] jArr = new long[3];
            for (int i2 = 0; i2 < hqmDataDispatcher.mDisplayStats.size(); i2++) {
                for (int i3 = 0; i3 < 3; i3++) {
                    jArr[i3] =
                            jArr[i3]
                                    + ((DisplayStat) hqmDataDispatcher.mDisplayStats.get(i2))
                                            .mBrightnessDuration[i3];
                }
            }
            hqmDataSetBuilder.put(jArr[0], "LBLD");
            hqmDataSetBuilder.put(jArr[1], "LBMD");
            hqmDataSetBuilder.put(jArr[2], "LBHD");
            if (PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY) {
                long j3 = 0;
                for (int i4 = 0; i4 < hqmDataDispatcher.mDisplayStats.size(); i4++) {
                    if (((DisplayStat) hqmDataDispatcher.mDisplayStats.get(i4)).mDisplayType == 0) {
                        j3 +=
                                ((DisplayStat) hqmDataDispatcher.mDisplayStats.get(i4))
                                        .mBrightnessDuration[2];
                    }
                }
                hqmDataSetBuilder.put(j3, "LBHD_M");
            }
            if (PowerManagerUtil.SEC_FEATURE_HQM_SEND_LBHD_HIGHEST) {
                long j4 = 0;
                for (int i5 = 0; i5 < hqmDataDispatcher.mDisplayStats.size(); i5++) {
                    if (((DisplayStat) hqmDataDispatcher.mDisplayStats.get(i5)).mDisplayType == 0) {
                        j4 +=
                                ((DisplayStat) hqmDataDispatcher.mDisplayStats.get(i5))
                                        .mScreenBrightnessHighestDuration;
                    }
                }
                hqmDataSetBuilder.put(j4, "LBHD_HIGHEST");
            }
            if (PowerManagerUtil.SEC_FEATURE_SCREEN_CURTAIN) {
                long j5 = 0;
                long j6 = 0;
                for (int i6 = 0; i6 < hqmDataDispatcher.mDisplayStats.size(); i6++) {
                    if (((DisplayStat) hqmDataDispatcher.mDisplayStats.get(i6)).mDisplayType == 0) {
                        j6 +=
                                ((DisplayStat) hqmDataDispatcher.mDisplayStats.get(i6))
                                        .mScreenCurtainCount;
                        j5 +=
                                ((DisplayStat) hqmDataDispatcher.mDisplayStats.get(i6))
                                        .mScreenCurtainDuration;
                    }
                }
                hqmDataSetBuilder.put(j5, "SC_DUR");
                hqmDataSetBuilder.put(j6, "SC_CNT");
            }
            hqmDataDispatcher.sendDataSetToHqm(
                    "DPLD",
                    hqmDataSetBuilder
                            .mJsonObj
                            .toString()
                            .replaceAll("\\{", "")
                            .replaceAll("\\}", ""));
            for (int i7 = 0; i7 < hqmDataDispatcher.mDisplayStats.size(); i7++) {
                DisplayStat displayStat = (DisplayStat) hqmDataDispatcher.mDisplayStats.get(i7);
                displayStat.mScreenOnCount = 0L;
                displayStat.mScreenOnDuration = 0L;
                displayStat.mCurrentBrightnessRange = -1;
                for (int i8 = 0; i8 < 3; i8++) {
                    displayStat.mBrightnessDuration[i8] = 0;
                }
                displayStat.mScreenCurtainCount = 0L;
                displayStat.mScreenCurtainDuration = 0L;
                displayStat.mScreenBrightnessHighestDuration = 0L;
                displayStat.mScreenBrightnessHighestStarted = false;
            }
            HqmDataDispatcher hqmDataDispatcher2 = HqmDataDispatcher.this;
            ContentResolver contentResolver = hqmDataDispatcher2.mContext.getContentResolver();
            int intForUser =
                    Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2);
            int intForUser2 =
                    Settings.System.getIntForUser(contentResolver, "screen_brightness", 0, -2);
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("MODE", String.valueOf(intForUser));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                jSONObject.put("LEVEL", String.valueOf(intForUser2));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            hqmDataDispatcher2.sendDataSetToHqm(
                    "DPSI", jSONObject.toString().replaceAll("\\{", "").replaceAll("\\}", ""));
        }
    }

    public final class Timer {
        public long startTimeMillis;
    }

    public HqmDataDispatcher(Context context, Looper looper) {
        Slog.d("HqmDataDispatcher", "HqmDataDispatcher()");
        this.mContext = context;
        DispatchHandler dispatchHandler = new DispatchHandler(looper);
        this.mHandler = dispatchHandler;
        this.mHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
        this.mBatteryManagerInternal =
                (BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class);
        context.registerReceiver(
                new HqmRequestReceiver(),
                BatteryService$$ExternalSyntheticOutline0.m(
                        "com.sec.android.intent.action.HQM_UPDATE_REQ"),
                null,
                dispatchHandler);
    }

    public final DisplayStat getDisplayStat(int i) {
        int i2 = 0;
        if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY && i != 0) {
            i2 = 1;
        }
        if (this.mDisplayStats.indexOfKey(i2) < 0) {
            this.mDisplayStats.put(i2, new DisplayStat(i2));
        }
        return (DisplayStat) this.mDisplayStats.get(i2);
    }

    public final void noteScreenBrightness(float f, int i) {
        int i2;
        if (i == 0 || (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY && i == 1)) {
            DisplayStat displayStat = getDisplayStat(i);
            displayStat.getClass();
            int brightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(f);
            int i3 = 2;
            if (displayStat.mScreenState == 2) {
                if (brightnessFloatToInt <= DisplayStat.BRIGHTNESS_DIM) {
                    i3 = 0;
                } else if (brightnessFloatToInt >= 230 && brightnessFloatToInt <= 255) {
                    i3 = 1;
                } else if (brightnessFloatToInt <= 255) {
                    i3 = -1;
                }
                int i4 = displayStat.mCurrentBrightnessRange;
                if (i3 != i4) {
                    if (i4 != -1) {
                        long[] jArr = displayStat.mBrightnessDuration;
                        float f2 = jArr[i4];
                        displayStat.mScreenBrightnessTimers[i4].getClass();
                        i2 = brightnessFloatToInt;
                        jArr[i4] =
                                (long)
                                        (((float)
                                                        ((SystemClock.uptimeMillis()
                                                                        - r12.startTimeMillis)
                                                                / 1000.0d))
                                                + f2);
                        displayStat.mCurrentBrightnessRange = -1;
                    } else {
                        i2 = brightnessFloatToInt;
                    }
                    displayStat.mCurrentBrightnessRange = i3;
                    if (i3 != -1) {
                        Timer timer = displayStat.mScreenBrightnessTimers[i3];
                        timer.getClass();
                        timer.startTimeMillis = SystemClock.uptimeMillis();
                    }
                } else {
                    i2 = brightnessFloatToInt;
                }
                if (PowerManagerUtil.SEC_FEATURE_HQM_SEND_LBHD_HIGHEST) {
                    boolean z = displayStat.mScreenBrightnessHighestStarted;
                    int i5 = DisplayStat.BRIGHTNESS_HIGHEST;
                    int i6 = i2;
                    if (!z && i6 == i5) {
                        Timer timer2 = displayStat.mScreenBrightnessHighestTimer;
                        timer2.getClass();
                        timer2.startTimeMillis = SystemClock.uptimeMillis();
                        displayStat.mScreenBrightnessHighestStarted = true;
                    } else if (z && i6 != i5 && z) {
                        float f3 = displayStat.mScreenBrightnessHighestDuration;
                        displayStat.mScreenBrightnessHighestTimer.getClass();
                        displayStat.mScreenBrightnessHighestDuration =
                                (long)
                                        (((float)
                                                        ((SystemClock.uptimeMillis()
                                                                        - r3.startTimeMillis)
                                                                / 1000.0d))
                                                + f3);
                        displayStat.mScreenBrightnessHighestStarted = false;
                    }
                }
            }
            this.mGlobalBrightness = f;
        }
    }

    public final void sendDataSetToHqm(String str, String str2) {
        Slog.d("HqmDataDispatcher", "sendDataSetToHqm: feature=" + str + ", dataSet=" + str2);
        this.mHqmManager.sendHWParamToHQM(0, "Display", str, "sm", "0.0", "sec", "", str2, "");
    }
}
