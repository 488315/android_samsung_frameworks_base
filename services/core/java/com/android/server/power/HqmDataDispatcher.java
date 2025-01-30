package com.android.server.power;

import android.R;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.android.server.LocalServices;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class HqmDataDispatcher {
    public static final String TAG = "HqmDataDispatcher";
    public final BatteryManagerInternal mBatteryManagerInternal;
    public final Context mContext;
    public final SparseArray mDisplayStats = new SparseArray();
    public float mGlobalBrightness = Float.NaN;
    public final DispatchHandler mHandler;
    public final SemHqmManager mHqmManager;

    public abstract class HqmDataDispatcherHolder {
        public static HqmDataDispatcher INSTANCE;
    }

    public static boolean isDefaultDisplay(int i) {
        return i == 0;
    }

    public final class HqmRequestReceiver extends BroadcastReceiver {
        public HqmRequestReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            HqmDataDispatcher.this.sendBrightnessDuration();
            HqmDataDispatcher.this.sendCurrentBrightnessSettings();
        }
    }

    public final class DispatchHandler extends Handler {
        public DispatchHandler(Looper looper) {
            super(looper);
        }

        public void sendMessageDelayed(int i, Object obj, int i2) {
            Message obtain = Message.obtain(this, i);
            obtain.obj = obj;
            sendMessageDelayed(obtain, i2);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 0) {
                HqmDataDispatcher.this.sendDataSetToHqm("DPUC", (String) message.obj);
                return;
            }
            Slog.m74e(HqmDataDispatcher.TAG, "Unknown message " + message.what);
        }
    }

    public static void init(Context context, Looper looper) {
        HqmDataDispatcherHolder.INSTANCE = new HqmDataDispatcher(context, looper);
    }

    public static HqmDataDispatcher getInstance() {
        return HqmDataDispatcherHolder.INSTANCE;
    }

    public HqmDataDispatcher(Context context, Looper looper) {
        Slog.m72d(TAG, "HqmDataDispatcher()");
        this.mContext = context;
        DispatchHandler dispatchHandler = new DispatchHandler(looper);
        this.mHandler = dispatchHandler;
        this.mHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
        this.mBatteryManagerInternal = (BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sec.android.intent.action.HQM_UPDATE_REQ");
        context.registerReceiver(new HqmRequestReceiver(), intentFilter, null, dispatchHandler);
    }

    public void sendBrightnessAdjustmentEventAsync(float f, float f2, float f3) {
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness_mode", 0, -2);
        String str = findForegroundPackageName() + "," + ((int) (f2 * 255.0f)) + "," + this.mBatteryManagerInternal.getBatteryLevel();
        HqmDataSetBuilder hqmDataSetBuilder = new HqmDataSetBuilder();
        hqmDataSetBuilder.put("LUBS_L", (int) f);
        hqmDataSetBuilder.put("LUBS_B", (int) (f3 * 255.0f));
        hqmDataSetBuilder.put("LUBS_M", intForUser);
        hqmDataSetBuilder.put("LUBS_P", str);
        this.mHandler.removeMessages(0);
        this.mHandler.sendMessageDelayed(0, hqmDataSetBuilder.build(), 10000);
    }

    public void noteScreenState(int i, int i2) {
        if (isBuiltInDisplay(i2)) {
            getDisplayStat(i2).setScreenState(i);
            if (PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY && i == 2) {
                noteScreenBrightness(this.mGlobalBrightness, i2);
            }
        }
    }

    public void noteScreenBrightness(float f, int i) {
        if (isBuiltInDisplay(i)) {
            getDisplayStat(i).setScreenBrightness(f);
            this.mGlobalBrightness = f;
        }
    }

    public void noteScreenCurtainEnabled(boolean z) {
        getDisplayStat(0).setScreenCurtainEnabled(z);
    }

    public final DisplayStat getDisplayStat(int i) {
        int type = DisplayType.getType(i);
        if (this.mDisplayStats.indexOfKey(type) < 0) {
            this.mDisplayStats.put(type, new DisplayStat(type));
        }
        return (DisplayStat) this.mDisplayStats.get(type);
    }

    public final void sendBrightnessDuration() {
        long[] jArr = new long[3];
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        long j6 = 0;
        for (int i = 0; i < this.mDisplayStats.size(); i++) {
            j += ((DisplayStat) this.mDisplayStats.get(i)).mScreenOnCount;
            j2 += ((DisplayStat) this.mDisplayStats.get(i)).mScreenOnDuration;
            jArr[0] = jArr[0] + ((DisplayStat) this.mDisplayStats.get(i)).mBrightnessDuration[0];
            jArr[1] = jArr[1] + ((DisplayStat) this.mDisplayStats.get(i)).mBrightnessDuration[1];
            jArr[2] = jArr[2] + ((DisplayStat) this.mDisplayStats.get(i)).mBrightnessDuration[2];
            if (PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY && ((DisplayStat) this.mDisplayStats.get(i)).mDisplayType == 0) {
                j3 = ((DisplayStat) this.mDisplayStats.get(i)).mBrightnessDuration[2];
            }
            if (PowerManagerUtil.SEC_FEATURE_SCREEN_CURTAIN && ((DisplayStat) this.mDisplayStats.get(i)).mDisplayType == 0) {
                j5 += ((DisplayStat) this.mDisplayStats.get(i)).mScreenCurtainCount;
                j4 += ((DisplayStat) this.mDisplayStats.get(i)).mScreenCurtainDuration;
            }
            if (PowerManagerUtil.SEC_FEATURE_HQM_SEND_LBHD_HIGHEST && ((DisplayStat) this.mDisplayStats.get(i)).mDisplayType == 0) {
                j6 += ((DisplayStat) this.mDisplayStats.get(i)).mScreenBrightnessHighestDuration;
            }
        }
        HqmDataSetBuilder hqmDataSetBuilder = new HqmDataSetBuilder();
        long j7 = j6;
        hqmDataSetBuilder.put("LBLD", jArr[0]);
        hqmDataSetBuilder.put("LBMD", jArr[1]);
        hqmDataSetBuilder.put("LBHD", jArr[2]);
        if (PowerManagerUtil.SEC_FEATURE_FOLD_COVER_DISPLAY) {
            hqmDataSetBuilder.put("LBHD_M", j3);
        }
        hqmDataSetBuilder.put("LOD", j2);
        hqmDataSetBuilder.put("LOCNT", j);
        if (PowerManagerUtil.SEC_FEATURE_SCREEN_CURTAIN) {
            hqmDataSetBuilder.put("SC_DUR", j4);
            hqmDataSetBuilder.put("SC_CNT", j5);
        }
        if (PowerManagerUtil.SEC_FEATURE_HQM_SEND_LBHD_HIGHEST) {
            hqmDataSetBuilder.put("LBHD_HIGHEST", j7);
        }
        sendDataSetToHqm("DPLD", hqmDataSetBuilder.build());
        for (int i2 = 0; i2 < this.mDisplayStats.size(); i2++) {
            ((DisplayStat) this.mDisplayStats.get(i2)).reset();
        }
    }

    public final void sendCurrentBrightnessSettings() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int intForUser = Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2);
        int intForUser2 = Settings.System.getIntForUser(contentResolver, "screen_brightness", 0, -2);
        HqmDataSetBuilder hqmDataSetBuilder = new HqmDataSetBuilder();
        hqmDataSetBuilder.put("MODE", intForUser);
        hqmDataSetBuilder.put("LEVEL", intForUser2);
        sendDataSetToHqm("DPSI", hqmDataSetBuilder.build());
    }

    public final void sendDataSetToHqm(String str, String str2) {
        Slog.m72d(TAG, "sendDataSetToHqm: feature=" + str + ", dataSet=" + str2);
        this.mHqmManager.sendHWParamToHQM(0, "Display", str, "sm", "0.0", "sec", "", str2, "");
    }

    public final String findForegroundPackageName() {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningTasks(1);
        return runningTasks.isEmpty() ? "" : runningTasks.get(0).topActivity.getPackageName();
    }

    public static boolean isBuiltInDisplay(int i) {
        return isDefaultDisplay(i) || isExtraDisplay(i);
    }

    public static boolean isExtraDisplay(int i) {
        return PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY && i == 1;
    }

    public final class DisplayStat {
        public static final int BRIGHTNESS_DIM = Math.max(0, Resources.getSystem().getInteger(R.integer.config_screenBrightnessSettingMaximum));
        public static final int BRIGHTNESS_HIGHEST = Resources.getSystem().getInteger(R.integer.config_screenTimeoutOverride);
        public final int mDisplayType;
        public final String mDisplayTypeStr;
        public long mScreenBrightnessHighestDuration;
        public boolean mScreenBrightnessHighestStarted;
        public Timer mScreenBrightnessHighestTimer;
        public long mScreenCurtainCount;
        public long mScreenCurtainDuration;
        public boolean mScreenCurtainEnabled;
        public Timer mScreenCurtainTimer;
        public Timer mScreenOnTimer;
        public int mScreenState = 0;
        public int mCurrentBrightnessRange = -1;
        public Timer[] mScreenBrightnessTimers = new Timer[3];
        public long[] mBrightnessDuration = new long[3];
        public long mScreenOnCount = 0;
        public long mScreenOnDuration = 0;

        public DisplayStat(int i) {
            this.mScreenOnTimer = new Timer();
            this.mScreenCurtainTimer = new Timer();
            this.mScreenBrightnessHighestTimer = new Timer();
            this.mDisplayType = i;
            this.mDisplayTypeStr = "[" + i + "]";
            for (int i2 = 0; i2 < 3; i2++) {
                this.mScreenBrightnessTimers[i2] = new Timer();
            }
            this.mScreenCurtainCount = 0L;
            this.mScreenCurtainDuration = 0L;
            this.mScreenBrightnessHighestDuration = 0L;
            this.mScreenBrightnessHighestStarted = false;
        }

        public void setScreenState(int i) {
            boolean z = this.mScreenState == 2;
            boolean z2 = i == 2;
            if (!z && z2) {
                this.mScreenOnCount++;
                this.mScreenOnTimer.start();
            } else if (z && !z2) {
                this.mScreenOnDuration = (long) (this.mScreenOnDuration + this.mScreenOnTimer.stop());
                updateBrightnessDuration(this.mCurrentBrightnessRange);
                if (PowerManagerUtil.SEC_FEATURE_HQM_SEND_LBHD_HIGHEST) {
                    updateBrightnessHighestDuration();
                }
            }
            this.mScreenState = i;
        }

        public void setScreenBrightness(float f) {
            int brightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(f);
            int i = 2;
            if (this.mScreenState == 2) {
                if (brightnessFloatToInt <= BRIGHTNESS_DIM) {
                    i = 0;
                } else if (brightnessFloatToInt >= 230 && brightnessFloatToInt <= 255) {
                    i = 1;
                } else if (brightnessFloatToInt <= 255) {
                    i = -1;
                }
                int i2 = this.mCurrentBrightnessRange;
                if (i != i2) {
                    updateBrightnessDuration(i2);
                    this.mCurrentBrightnessRange = i;
                    if (i != -1) {
                        this.mScreenBrightnessTimers[i].start();
                    }
                }
                if (PowerManagerUtil.SEC_FEATURE_HQM_SEND_LBHD_HIGHEST) {
                    setScreenBrightnessHighest(brightnessFloatToInt);
                }
            }
        }

        public final void updateBrightnessDuration(int i) {
            if (i != -1) {
                this.mBrightnessDuration[i] = (long) (r1[i] + this.mScreenBrightnessTimers[i].stop());
                this.mCurrentBrightnessRange = -1;
            }
        }

        public void setScreenCurtainEnabled(boolean z) {
            boolean z2 = this.mScreenCurtainEnabled;
            if (!z2 && z) {
                this.mScreenCurtainCount++;
                this.mScreenCurtainTimer.start();
            } else if (z2 && !z) {
                this.mScreenCurtainDuration = (long) (this.mScreenCurtainDuration + this.mScreenCurtainTimer.stop());
            }
            this.mScreenCurtainEnabled = z;
        }

        public void setScreenBrightnessHighest(int i) {
            boolean z = this.mScreenBrightnessHighestStarted;
            if (!z && i == BRIGHTNESS_HIGHEST) {
                this.mScreenBrightnessHighestTimer.start();
                this.mScreenBrightnessHighestStarted = true;
            } else {
                if (!z || i == BRIGHTNESS_HIGHEST) {
                    return;
                }
                updateBrightnessHighestDuration();
            }
        }

        public void updateBrightnessHighestDuration() {
            if (this.mScreenBrightnessHighestStarted) {
                this.mScreenBrightnessHighestDuration = (long) (this.mScreenBrightnessHighestDuration + this.mScreenBrightnessHighestTimer.stop());
                this.mScreenBrightnessHighestStarted = false;
            }
        }

        public void reset() {
            this.mScreenOnCount = 0L;
            this.mScreenOnDuration = 0L;
            this.mCurrentBrightnessRange = -1;
            for (int i = 0; i < 3; i++) {
                this.mBrightnessDuration[i] = 0;
            }
            this.mScreenCurtainCount = 0L;
            this.mScreenCurtainDuration = 0L;
            this.mScreenBrightnessHighestDuration = 0L;
            this.mScreenBrightnessHighestStarted = false;
        }
    }

    public abstract class DisplayType {
        public static int getType(int i) {
            return (!PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY || i == 0) ? 0 : 1;
        }
    }

    public class Timer {
        public long startTimeMillis;

        public Timer() {
        }

        public void start() {
            this.startTimeMillis = SystemClock.uptimeMillis();
        }

        public float stop() {
            return (float) ((SystemClock.uptimeMillis() - this.startTimeMillis) / 1000.0d);
        }
    }

    public final class HqmDataSetBuilder {
        public JSONObject mJsonObj = new JSONObject();

        public HqmDataSetBuilder put(String str, int i) {
            return put(str, String.valueOf(i));
        }

        public HqmDataSetBuilder put(String str, long j) {
            return put(str, String.valueOf(j));
        }

        public HqmDataSetBuilder put(String str, String str2) {
            try {
                this.mJsonObj.put(str, str2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return this;
        }

        public String build() {
            return this.mJsonObj.toString().replaceAll("\\{", "").replaceAll("\\}", "");
        }

        public String toString() {
            return this.mJsonObj.toString();
        }
    }
}
