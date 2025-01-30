package com.android.server.ibs;

import android.R;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.net.TrafficStats;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Slog;
import android.view.Display;
import com.android.internal.os.PowerProfile;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.display.DisplayPowerController2;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class IntelligentBatterySaverFastDrainPolicy {
    public static float FAST_DROP_CURRENT_AOD_OFF = 35.0f;
    public static float FAST_DROP_CURRENT_AOD_ON = 55.0f;
    public ArrayList mActionsLevel;
    public AlarmManager mAlarmManager;
    public Context mContext;
    public Display mCurDisplay;
    public DisplayManager mDisplayManager;
    public float mFastDropCurrent;
    public IntentFilter mFilter;
    public IntelligentBatterySaverFastDrainHandler mHandler;
    public HandlerThread mHandlerThread;
    public IntelligentBatterySaverFastDrainAction mIntelligentBatterySaverFastDrainAction;
    public IntelligentBatterySaverLogger mIntelligentBatterySaverLogger;
    public IntelligentBatterySaverSurvey mIntelligentBatterySaverSurvey;
    public Sensor mMotionSensor;
    public PowerProfile mPowerProfile;
    public IntelligentBatterySaverFastDrainReceiver mReceiver;
    public SensorManager mSensorManager;
    public SharedPreferences mSharedPreferences;
    public final String TAG = "IntelligentBatterySaverFastDrainPolicy";
    public final Object mActionsLock = new Object();
    public final MotionListener mMotionListener = new MotionListener();
    public final AlarmManager.OnAlarmListener mTimeoutAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            IntelligentBatterySaverFastDrainPolicy.this.lambda$new$0();
        }
    };
    public final AlarmManager.OnAlarmListener mSafeCheckTimeoutAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda1
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            IntelligentBatterySaverFastDrainPolicy.this.lambda$new$1();
        }
    };
    public final AlarmManager.OnAlarmListener mMotionTimeoutAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda2
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            IntelligentBatterySaverFastDrainPolicy.this.lambda$new$2();
        }
    };
    public final AlarmManager.OnAlarmListener mInactiveTimeoutAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda3
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            IntelligentBatterySaverFastDrainPolicy.this.lambda$new$3();
        }
    };
    public float mEstimatedBatteryCapacity = 3300.0f;
    public long mEnterIBSTime = 0;
    public int mEnterIBSBatteryLevel = 0;
    public int mExitIBSBatteryLevel = 0;
    public IntelligentBatterySaverFastDrainTrafficStat mTrafiicStat = new IntelligentBatterySaverFastDrainTrafficStat();
    public IntelligentBatterySaverFastDrainBatteryInfo mBatteryInfo = new IntelligentBatterySaverFastDrainBatteryInfo();
    public IntelligentBatterySaverBatteryBigData mIBSBigData = new IntelligentBatterySaverBatteryBigData();
    public boolean mInited = false;
    public LocalTime mStartTime = getCustomStartTime();
    public LocalTime mEndTime = getCustomEndTime();
    public boolean mScreenOn = true;
    public boolean mCharging = true;
    public int mSysState = 0;
    public int mFastDrainInternalState = 2;

    public interface IIntelligentBatterySaverFastDrainCallBack {
        void cancelFastDrainRestriction();

        void doFastDrainRestriction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        if (this.mFastDrainInternalState == 5) {
            this.mIntelligentBatterySaverLogger.add("safe check alarm trigger");
            scheduleSaveCheckTimeoutAlarm();
            this.mHandler.sendEmptyMessage(5);
            return;
        }
        Slog.e("IntelligentBatterySaverFastDrainPolicy", " safe check alarm triggered but state is safe check!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        if (this.mFastDrainInternalState == 4) {
            this.mFastDrainInternalState = 5;
            this.mIntelligentBatterySaverLogger.add("move to safe check");
            clearSafeCheck();
            scheduleSaveCheckTimeoutAlarm();
            this.mHandler.sendEmptyMessage(5);
            return;
        }
        Slog.e("IntelligentBatterySaverFastDrainPolicy", " motion alarm triggered but state is not motion!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3() {
        if (this.mFastDrainInternalState == 3) {
            this.mFastDrainInternalState = 4;
            this.mIntelligentBatterySaverLogger.add("move to state motion");
            reportSetState(16);
            startMonitoringMotionLocked();
            scheduleMotionTimeoutAlarm();
            return;
        }
        Slog.e("IntelligentBatterySaverFastDrainPolicy", " inactive alarm triggered but state is not inactive!");
    }

    public IntelligentBatterySaverFastDrainPolicy(Context context, HandlerThread handlerThread, IntelligentBatterySaverLogger intelligentBatterySaverLogger, IntelligentBatterySaverSurvey intelligentBatterySaverSurvey) {
        this.mContext = context;
        this.mHandlerThread = handlerThread;
        this.mIntelligentBatterySaverLogger = intelligentBatterySaverLogger;
        this.mIntelligentBatterySaverSurvey = intelligentBatterySaverSurvey;
        ArrayList arrayList = new ArrayList();
        this.mActionsLevel = arrayList;
        arrayList.add(new ArrayList());
        this.mActionsLevel.add(new ArrayList());
        this.mHandler = new IntelligentBatterySaverFastDrainHandler(this.mHandlerThread.getLooper());
        IntelligentBatterySaverFastDrainAction intelligentBatterySaverFastDrainAction = new IntelligentBatterySaverFastDrainAction(context);
        this.mIntelligentBatterySaverFastDrainAction = intelligentBatterySaverFastDrainAction;
        intelligentBatterySaverFastDrainAction.regisiterAction();
    }

    public final void reportSetState(int i) {
        IntelligentBatterySaverFastDrainHandler intelligentBatterySaverFastDrainHandler = this.mHandler;
        if (intelligentBatterySaverFastDrainHandler != null) {
            this.mHandler.sendMessage(intelligentBatterySaverFastDrainHandler.obtainMessage(8, i, 0));
        }
    }

    public final void reportClearState(int i) {
        IntelligentBatterySaverFastDrainHandler intelligentBatterySaverFastDrainHandler = this.mHandler;
        if (intelligentBatterySaverFastDrainHandler != null) {
            this.mHandler.sendMessage(intelligentBatterySaverFastDrainHandler.obtainMessage(9, i, 0));
        }
    }

    public void setIBSFastDrainActionEnable(boolean z) {
        if (z) {
            reportSetState(128);
        } else {
            reportClearState(128);
        }
    }

    public void setIBSFastDrainPolicyEnable(boolean z) {
        if (z) {
            reportSetState(1);
            startIntelligentBatterySaverFastDrainPolicy();
        } else {
            reportClearState(1);
            stopIntelligentBatterySaverFastDrainPolicy();
        }
    }

    public void init() {
        this.mDisplayManager = (DisplayManager) this.mContext.getSystemService("display");
        this.mSensorManager = (SensorManager) this.mContext.getSystemService("sensor");
        int integer = this.mContext.getResources().getInteger(R.integer.config_burnInProtectionMinVerticalOffset);
        if (integer > 0) {
            this.mMotionSensor = this.mSensorManager.getDefaultSensor(integer, true);
        }
        if (this.mMotionSensor == null && this.mContext.getResources().getBoolean(R.bool.config_attachNavBarToAppDuringTransition)) {
            this.mMotionSensor = this.mSensorManager.getDefaultSensor(26, true);
        }
        if (this.mMotionSensor == null) {
            this.mMotionSensor = this.mSensorManager.getDefaultSensor(17, true);
        }
    }

    public final void start() {
        if (this.mInited) {
            return;
        }
        initBroadcast(true);
        initAlarm(true);
        this.mInited = true;
    }

    public final void stop() {
        if (this.mInited) {
            initBroadcast(false);
            initAlarm(false);
            this.mInited = false;
        }
    }

    public final void initBroadcast(boolean z) {
        if (z) {
            this.mReceiver = new IntelligentBatterySaverFastDrainReceiver();
            IntentFilter intentFilter = new IntentFilter();
            this.mFilter = intentFilter;
            intentFilter.addAction("android.intent.action.TIME_SET");
            this.mFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
            this.mFilter.addAction("android.intent.action.REBOOT");
            this.mContext.registerReceiver(this.mReceiver, this.mFilter);
            return;
        }
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    public final void checkDownloadSafe() {
        if (!this.mTrafiicStat.initialized) {
            this.mTrafiicStat.sampleTimeInSecs = System.currentTimeMillis() / 1000;
            this.mTrafiicStat.txBytes = TrafficStats.getTotalTxBytes();
            this.mTrafiicStat.rxBytes = TrafficStats.getTotalRxBytes();
            this.mTrafiicStat.initialized = true;
            return;
        }
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        long totalTxBytes = TrafficStats.getTotalTxBytes();
        long totalRxBytes = TrafficStats.getTotalRxBytes();
        long j = (totalTxBytes + totalRxBytes) - (this.mTrafiicStat.txBytes + this.mTrafiicStat.rxBytes);
        long j2 = currentTimeMillis - this.mTrafiicStat.sampleTimeInSecs;
        if (j2 <= 0) {
            return;
        }
        long j3 = j / j2;
        Slog.d("IntelligentBatterySaverFastDrainPolicy", "checkDownloadSafe speed: ," + currentTimeMillis + "," + totalTxBytes + "," + totalRxBytes + "," + j + "," + j2 + "," + j3);
        if (j3 < 5000) {
            reportSetState(64);
            this.mTrafiicStat.initialized = false;
        } else {
            this.mTrafiicStat.sampleTimeInSecs = currentTimeMillis;
            this.mTrafiicStat.txBytes = totalTxBytes;
            this.mTrafiicStat.rxBytes = totalRxBytes;
            reportClearState(64);
        }
    }

    public final void checkMusicSafe() {
        AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
        boolean isMusicActive = audioManager.isMusicActive();
        boolean semIsRecordActive = audioManager.semIsRecordActive(-1);
        if (isMusicActive || semIsRecordActive) {
            reportClearState(32);
        } else {
            reportSetState(32);
        }
    }

    public final boolean checkIdle() {
        return ((PowerManager) this.mContext.getSystemService("power")).isDeviceIdleMode();
    }

    public final void sendIntentToSmartManager() {
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.sm.ACTION_FAST_BATTERY_DRAIN_DETECTED");
        intent.putExtra("trigger_time", this.mEnterIBSTime);
        intent.setClassName("com.samsung.android.sm_cn", "com.samsung.android.sm.battery.receiver.FastBatteryDrainReceiver");
        this.mContext.sendBroadcast(intent);
    }

    public final void resetBatteryBigData() {
        this.mIBSBigData.initialized = false;
        this.mIBSBigData.actionEnabled = false;
        this.mIBSBigData.drainHightCurrent = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        this.mIBSBigData.restrictedCurrent = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }

    public final void resetBatteryInfo() {
        this.mBatteryInfo.initialized = false;
        this.mBatteryInfo.level = -1;
        this.mBatteryInfo.startTime = 0L;
    }

    public final double getBatteryCapacity() {
        if (this.mPowerProfile == null) {
            this.mPowerProfile = new PowerProfile(this.mContext);
        }
        return this.mPowerProfile.getBatteryCapacity();
    }

    public final void checkBatteryInfo(int i, int i2) {
        if (!this.mBatteryInfo.initialized) {
            int i3 = (i * 100) / i2;
            if (this.mBatteryInfo.level == -1) {
                this.mBatteryInfo.level = i3;
                return;
            }
            if (this.mBatteryInfo.level - i3 == 1) {
                this.mBatteryInfo.startTime = System.currentTimeMillis() / 1000;
                this.mBatteryInfo.level = i3;
                this.mBatteryInfo.initialized = true;
                Slog.d("IntelligentBatterySaverFastDrainPolicy", "checkBatteryInfo init level = " + i + " scale = " + i2 + " start time = " + this.mBatteryInfo.startTime);
                this.mIntelligentBatterySaverLogger.add("checkBatteryInfo init level = " + i + " scale = " + i2 + " start time = " + this.mBatteryInfo.startTime);
                return;
            }
            if (this.mBatteryInfo.level - i3 >= 2) {
                this.mBatteryInfo.level = -1;
                Slog.w("IntelligentBatterySaverFastDrainPolicy", "warning !!! battery drop is more than 2 level");
                return;
            }
            return;
        }
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        long j = currentTimeMillis - this.mBatteryInfo.startTime;
        int i4 = (i * 100) / i2;
        Slog.d("IntelligentBatterySaverFastDrainPolicy", "checkBatteryInfo level = " + i + " scale = " + i2 + " timeDelta = " + j);
        this.mIntelligentBatterySaverLogger.add("checkBatteryInfo level = " + i + " scale = " + i2 + " timeDelta = " + j);
        if (this.mBatteryInfo.level - i4 == 1) {
            this.mBatteryInfo.startTime = currentTimeMillis;
            this.mBatteryInfo.level = i4;
            float batteryCapacity = (float) getBatteryCapacity();
            this.mEstimatedBatteryCapacity = batteryCapacity;
            if (j >= 0 && j <= 60) {
                resetBatteryInfo();
                return;
            }
            float f = ((batteryCapacity / 100.0f) * 3600.0f) / j;
            boolean z = Settings.System.getInt(this.mContext.getContentResolver(), "aod_show_state", 0) != 0;
            if (z) {
                this.mFastDropCurrent = FAST_DROP_CURRENT_AOD_ON;
            } else {
                this.mFastDropCurrent = FAST_DROP_CURRENT_AOD_OFF;
            }
            if (f >= this.mFastDropCurrent) {
                Slog.d("IntelligentBatterySaverFastDrainPolicy", "curCurrent = " + f + " > mFastDropCurrent = " + this.mFastDropCurrent + " and isAodEnabled = " + z);
                if (!this.mIBSBigData.initialized) {
                    this.mIBSBigData.drainHightCurrent = f;
                    this.mIBSBigData.initialized = true;
                }
                WifiManager wifiManager = (WifiManager) this.mContext.getSystemService("wifi");
                boolean isWifiApEnabled = wifiManager != null ? wifiManager.isWifiApEnabled() : false;
                this.mIntelligentBatterySaverLogger.add("curCurrent = " + f + " > mFastDropCurrent = " + this.mFastDropCurrent + " and wifiApState = " + isWifiApEnabled);
                if (isWifiApEnabled) {
                    return;
                }
                sendEnterFastDrainRestrictionMessage();
                return;
            }
            Slog.d("IntelligentBatterySaverFastDrainPolicy", "curCurrent = " + f + " < mFastDropCurrent = " + this.mFastDropCurrent + " and isAodEnabled = " + z);
            IntelligentBatterySaverLogger intelligentBatterySaverLogger = this.mIntelligentBatterySaverLogger;
            StringBuilder sb = new StringBuilder();
            sb.append("curCurrent = ");
            sb.append(f);
            sb.append(" < mFastDropCurrent = ");
            sb.append(this.mFastDropCurrent);
            intelligentBatterySaverLogger.add(sb.toString());
            return;
        }
        if (this.mBatteryInfo.level - i4 >= 2) {
            resetBatteryInfo();
            Slog.w("IntelligentBatterySaverFastDrainPolicy", "warning !!! battery drop is more than 2 level");
        }
    }

    public void updateBatteryLevelChanged(int i, int i2) {
        IntelligentBatterySaverFastDrainHandler intelligentBatterySaverFastDrainHandler = this.mHandler;
        if (intelligentBatterySaverFastDrainHandler != null) {
            this.mHandler.sendMessage(Message.obtain(intelligentBatterySaverFastDrainHandler, 6, i, i2));
        }
    }

    public final void sendExitFastDrainRestrictionMessage() {
        IntelligentBatterySaverFastDrainHandler intelligentBatterySaverFastDrainHandler = this.mHandler;
        if (intelligentBatterySaverFastDrainHandler != null) {
            this.mHandler.sendMessage(intelligentBatterySaverFastDrainHandler.obtainMessage(4));
        }
    }

    public void sendForceEixtFastDrainRestrictionMessage() {
        IntelligentBatterySaverFastDrainHandler intelligentBatterySaverFastDrainHandler = this.mHandler;
        if (intelligentBatterySaverFastDrainHandler != null) {
            this.mHandler.sendMessage(intelligentBatterySaverFastDrainHandler.obtainMessage(4));
        }
    }

    public void sendBootCompletedMessage() {
        IntelligentBatterySaverFastDrainHandler intelligentBatterySaverFastDrainHandler = this.mHandler;
        if (intelligentBatterySaverFastDrainHandler != null) {
            this.mHandler.sendMessage(intelligentBatterySaverFastDrainHandler.obtainMessage(10));
        }
    }

    public final void sendEnterFastDrainRestrictionMessage() {
        IntelligentBatterySaverFastDrainHandler intelligentBatterySaverFastDrainHandler = this.mHandler;
        if (intelligentBatterySaverFastDrainHandler != null) {
            this.mHandler.sendMessage(intelligentBatterySaverFastDrainHandler.obtainMessage(3));
        }
    }

    public void updateChargingLocked(boolean z) {
        if (!z && this.mCharging) {
            this.mCharging = false;
            reportSetState(8);
        } else if (z) {
            this.mCharging = true;
            reportClearState(8);
        }
    }

    public void updateDisplayLocked() {
        Display display = this.mDisplayManager.getDisplay(0);
        this.mCurDisplay = display;
        boolean z = display.getState() == 2;
        if (!z && this.mScreenOn) {
            this.mScreenOn = false;
            reportSetState(4);
        } else if (z) {
            this.mScreenOn = true;
            reportClearState(4);
        }
    }

    public LocalTime getCustomStartTime() {
        return LocalTime.valueOf(23, 0, 0);
    }

    public LocalTime getCustomEndTime() {
        int nextInt = new Random().nextInt(600);
        return LocalTime.valueOf(6, (nextInt / 60) + 50, nextInt % 60);
    }

    public final void bootCompleted() {
        Slog.d("IntelligentBatterySaverFastDrainPolicy", "bootCompleted");
        if (getBooleanState("ibs_policy_activated")) {
            synchronized (this.mActionsLock) {
                for (int i = 1; i >= 0; i--) {
                    Iterator it = ((ArrayList) this.mActionsLevel.get(i)).iterator();
                    while (it.hasNext()) {
                        ActionEntry actionEntry = (ActionEntry) it.next();
                        try {
                            Slog.d("IntelligentBatterySaverFastDrainPolicy", " cancel fast drain restriction " + actionEntry.tag);
                            actionEntry.callBack.cancelFastDrainRestriction();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /* renamed from: updateActivated, reason: merged with bridge method [inline-methods] */
    public final void lambda$new$0() {
        Calendar calendar = Calendar.getInstance();
        updateNextAlarm(Boolean.valueOf(calendar.before(this.mEndTime.getDateTimeAfter(this.mStartTime.getDateTimeBefore(calendar)))), calendar);
    }

    public final void updateNextAlarm(Boolean bool, Calendar calendar) {
        if (bool != null) {
            Calendar dateTimeAfter = bool.booleanValue() ? this.mEndTime.getDateTimeAfter(calendar) : this.mStartTime.getDateTimeAfter(calendar);
            if (bool.booleanValue()) {
                reportSetState(2);
                Slog.d("IntelligentBatterySaverFastDrainPolicy", "In Active Duration, set inactive alarm at " + dateTimeAfter.get(11) + XmlUtils.STRING_ARRAY_SEPARATOR + dateTimeAfter.get(12));
            } else {
                reportClearState(2);
                Slog.d("IntelligentBatterySaverFastDrainPolicy", "Out Active Duration, set active alarm at " + dateTimeAfter.get(11) + XmlUtils.STRING_ARRAY_SEPARATOR + dateTimeAfter.get(12));
            }
            this.mAlarmManager.setExact(0, dateTimeAfter.getTimeInMillis(), "IntelligentBatterySaverFastDrainPolicy", this.mTimeoutAlarmListener, null);
        }
    }

    public final void initAlarm(boolean z) {
        AlarmManager.OnAlarmListener onAlarmListener;
        if (z) {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
            lambda$new$0();
            return;
        }
        AlarmManager alarmManager = this.mAlarmManager;
        if (alarmManager == null || (onAlarmListener = this.mTimeoutAlarmListener) == null) {
            return;
        }
        alarmManager.cancel(onAlarmListener);
    }

    public final void startMonitoringMotionLocked() {
        if (this.mMotionSensor != null) {
            MotionListener motionListener = this.mMotionListener;
            if (motionListener.active) {
                return;
            }
            motionListener.registerLocked();
        }
    }

    public final void stopMonitoringMotionLocked() {
        if (this.mMotionSensor != null) {
            MotionListener motionListener = this.mMotionListener;
            if (motionListener.active) {
                motionListener.unregisterLocked();
            }
        }
    }

    public final void startIntelligentBatterySaverFastDrainPolicy() {
        Slog.d("IntelligentBatterySaverFastDrainPolicy", "startIntelligentBatterySaverFastDrainPolicy");
        this.mHandler.sendEmptyMessage(1);
    }

    public final void stopIntelligentBatterySaverFastDrainPolicy() {
        Slog.d("IntelligentBatterySaverFastDrainPolicy", "stopIntelligentBatterySaverFastDrainPolicy");
        this.mHandler.sendEmptyMessage(2);
    }

    public final boolean testState(int i) {
        return (this.mSysState & i) == i;
    }

    public final void setSysState(int i) {
        int i2 = i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        int i3 = this.mSysState;
        if (i2 != i3) {
            int i4 = i3 ^ i2;
            Slog.d("IntelligentBatterySaverFastDrainPolicy", "Sys State changed : [old , new, changebits] : [" + Integer.toBinaryString(this.mSysState) + "," + Integer.toBinaryString(i2) + "," + Integer.toBinaryString(i4) + "]");
            this.mIntelligentBatterySaverLogger.add("Sys State changed : [old , new, changebits] : [" + Integer.toBinaryString(this.mSysState) + "," + Integer.toBinaryString(i2) + "," + Integer.toBinaryString(i4) + "]");
            this.mSysState = i2;
            updateFastDrainInternalState();
        }
    }

    public final boolean isFastDrainRestrictionOn() {
        return getIntelligentBatterySaverEnable() && this.mFastDrainInternalState == 1;
    }

    public final boolean isCheckBatteryDropState() {
        return getIntelligentBatterySaverEnable() && this.mFastDrainInternalState == 6;
    }

    public final boolean getIntelligentBatterySaverEnable() {
        return testState(1);
    }

    public final void scheduleInactiveTimeoutAlarm(long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime() + j;
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        }
        this.mAlarmManager.set(3, elapsedRealtime, "IntelligentBatterySaver.inactive", this.mInactiveTimeoutAlarmListener, this.mHandler);
    }

    public final void scheduleMotionTimeoutAlarm() {
        long elapsedRealtime = SystemClock.elapsedRealtime() + 0;
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        }
        this.mAlarmManager.set(3, elapsedRealtime, "IntelligentBatterySaver.motion", this.mMotionTimeoutAlarmListener, this.mHandler);
    }

    public final void scheduleSaveCheckTimeoutAlarm() {
        long elapsedRealtime = SystemClock.elapsedRealtime() + 0;
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        }
        this.mAlarmManager.set(3, elapsedRealtime, "IntelligentBatterySaver.safecheck", this.mSafeCheckTimeoutAlarmListener, this.mHandler);
    }

    public final void moveToStateOFF(String str, AlarmManager.OnAlarmListener onAlarmListener, boolean z) {
        this.mFastDrainInternalState = 2;
        Slog.d("IntelligentBatterySaverFastDrainPolicy", str);
        this.mIntelligentBatterySaverLogger.add(str);
        stopMonitoringMotionLocked();
        AlarmManager alarmManager = this.mAlarmManager;
        if (alarmManager != null && onAlarmListener != null) {
            alarmManager.cancel(onAlarmListener);
        }
        if (z) {
            clearSafeCheck();
        }
    }

    public final void updateFastDrainInternalState() {
        AlarmManager.OnAlarmListener onAlarmListener;
        switch (this.mFastDrainInternalState) {
            case 1:
                if (!testState(15)) {
                    exitFastDrainRestriction();
                    moveToStateOFF("move to off state", null, true);
                    break;
                } else if (!testState(16)) {
                    exitFastDrainRestriction();
                    moveToStateOFF("move to off state, device move", null, true);
                    updateFastDrainInternalState();
                    break;
                }
                break;
            case 2:
                if (testState(15)) {
                    this.mFastDrainInternalState = 3;
                    this.mIntelligentBatterySaverLogger.add("move to inactive");
                    if (checkIdle()) {
                        scheduleInactiveTimeoutAlarm(0L);
                        break;
                    } else {
                        scheduleInactiveTimeoutAlarm(900000L);
                        break;
                    }
                }
                break;
            case 3:
                if (!testState(15)) {
                    moveToStateOFF("move to off state from inactive", this.mInactiveTimeoutAlarmListener, false);
                    break;
                } else {
                    Slog.e("IntelligentBatterySaverFastDrainPolicy", " extra bits report in inactive state!");
                    break;
                }
            case 4:
                if (!testState(15)) {
                    moveToStateOFF("move to off state from motion", this.mMotionTimeoutAlarmListener, false);
                    break;
                } else if (!testState(16)) {
                    moveToStateOFF("move to off state from motion, device move", this.mMotionTimeoutAlarmListener, false);
                    updateFastDrainInternalState();
                    break;
                }
                break;
            case 5:
                if (!testState(15)) {
                    moveToStateOFF("move to off state from safe check", this.mSafeCheckTimeoutAlarmListener, true);
                    break;
                } else if (!testState(16)) {
                    moveToStateOFF("move to off state from safe check, device move", this.mSafeCheckTimeoutAlarmListener, true);
                    updateFastDrainInternalState();
                    break;
                } else if (testState(96)) {
                    AlarmManager alarmManager = this.mAlarmManager;
                    if (alarmManager != null && (onAlarmListener = this.mSafeCheckTimeoutAlarmListener) != null) {
                        alarmManager.cancel(onAlarmListener);
                    }
                    this.mFastDrainInternalState = 6;
                    this.mIntelligentBatterySaverLogger.add("move to battery check state");
                    break;
                }
                break;
            case 6:
                if (!testState(15)) {
                    moveToStateOFF("move to off state", null, true);
                    resetBatteryInfo();
                    break;
                }
                break;
        }
    }

    public final void enterFastDrainRestriction() {
        this.mEnterIBSTime = System.currentTimeMillis();
        this.mEnterIBSBatteryLevel = this.mBatteryInfo.level;
        if (!testState(128)) {
            Slog.d("IntelligentBatterySaverFastDrainPolicy", "UI switch off disable the fast drain restriction.");
            return;
        }
        synchronized (this.mActionsLock) {
            for (int i = 0; i <= 1; i++) {
                Iterator it = ((ArrayList) this.mActionsLevel.get(i)).iterator();
                while (it.hasNext()) {
                    ActionEntry actionEntry = (ActionEntry) it.next();
                    try {
                        Slog.d("IntelligentBatterySaverFastDrainPolicy", " do fast drain restriction " + actionEntry.tag);
                        actionEntry.callBack.doFastDrainRestriction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            saveBooleanState(true, "ibs_policy_activated");
        }
    }

    public final void exitFastDrainRestriction() {
        updateBigData();
        resetBatteryBigData();
        resetBatteryInfo();
        if (!testState(128)) {
            Slog.d("IntelligentBatterySaverFastDrainPolicy", "UI switch off disable the cancel fast drain restriction.");
            return;
        }
        if (needSendBroadCast()) {
            sendIntentToSmartManager();
        }
        synchronized (this.mActionsLock) {
            for (int i = 1; i >= 0; i--) {
                Iterator it = ((ArrayList) this.mActionsLevel.get(i)).iterator();
                while (it.hasNext()) {
                    ActionEntry actionEntry = (ActionEntry) it.next();
                    try {
                        Slog.d("IntelligentBatterySaverFastDrainPolicy", " cancel fast drain restriction " + actionEntry.tag);
                        actionEntry.callBack.cancelFastDrainRestriction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            saveBooleanState(false, "ibs_policy_activated");
        }
    }

    public final void updateBigData() {
        long j = this.mEnterIBSTime / 1000;
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        this.mExitIBSBatteryLevel = this.mBatteryInfo.level;
        long j2 = currentTimeMillis - j;
        if (j2 == 0) {
            j2 = 1;
        }
        this.mIBSBigData.restrictedCurrent = (((this.mEstimatedBatteryCapacity / 100.0f) * (this.mEnterIBSBatteryLevel - r2)) * 3600.0f) / j2;
        this.mIBSBigData.actionEnabled = testState(128);
        this.mIntelligentBatterySaverSurvey.insertLog("com.android.server.ibs", "IBS", bigData());
    }

    public final String bigData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("IBSEN", this.mIBSBigData.actionEnabled);
            jSONObject.put("IBSDHC", this.mIBSBigData.drainHightCurrent);
            jSONObject.put("IBSRC", this.mIBSBigData.restrictedCurrent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jSONObject2 = jSONObject.toString();
        return jSONObject2.substring(1, jSONObject2.length() - 1);
    }

    public final void clearSafeCheck() {
        this.mSysState &= -97;
        this.mTrafiicStat.initialized = false;
    }

    public final void registerIntelligentBatterySaverFastDrainAction(String str, IIntelligentBatterySaverFastDrainCallBack iIntelligentBatterySaverFastDrainCallBack, int i) {
        if (i < 0 || i > 1 || iIntelligentBatterySaverFastDrainCallBack == null) {
            Slog.d("IntelligentBatterySaverFastDrainPolicy", " registerIntelligentBatterySaverFastDrainAction error : level  " + i + ", callBack " + iIntelligentBatterySaverFastDrainCallBack);
            return;
        }
        ActionEntry actionEntry = new ActionEntry(str, iIntelligentBatterySaverFastDrainCallBack);
        synchronized (this.mActionsLock) {
            ((ArrayList) this.mActionsLevel.get(i)).add(actionEntry);
        }
    }

    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("");
        printWriter.println("IntelligentBatterySaverFastDrainPolicy ");
        printWriter.println("get current mSysState :" + Integer.toBinaryString(this.mSysState));
        printWriter.println("getBatteryCapacity :" + getBatteryCapacity());
        if (strArr.length == 2 && "ibs".equals(strArr[0])) {
            int parseInt = Integer.parseInt(strArr[1]);
            printWriter.println("set new mSysState :" + Integer.toBinaryString(parseInt));
            setSysState(parseInt);
            printWriter.println("get updated mSysState :" + Integer.toBinaryString(this.mSysState));
        }
    }

    public class LocalTime {
        public final int hourOfDay;
        public final int minute;
        public final int second;

        public LocalTime(int i, int i2, int i3) {
            if (i < 0 || i > 23) {
                throw new IllegalArgumentException("Invalid hourOfDay: " + i);
            }
            if (i2 < 0 || i2 > 59) {
                throw new IllegalArgumentException("Invalid minute: " + i2);
            }
            if (i3 < 0 || i3 > 59) {
                throw new IllegalArgumentException("Invalid second: " + i3);
            }
            this.hourOfDay = i;
            this.minute = i2;
            this.second = i3;
        }

        public static LocalTime valueOf(int i, int i2, int i3) {
            return new LocalTime(i, i2, i3);
        }

        public Calendar getDateTimeBefore(Calendar calendar) {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(1, calendar.get(1));
            calendar2.set(6, calendar.get(6));
            calendar2.set(11, this.hourOfDay);
            calendar2.set(12, this.minute);
            calendar2.set(13, this.second);
            calendar2.set(14, 0);
            if (calendar2.after(calendar)) {
                calendar2.add(5, -1);
            }
            return calendar2;
        }

        public Calendar getDateTimeAfter(Calendar calendar) {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(1, calendar.get(1));
            calendar2.set(6, calendar.get(6));
            calendar2.set(11, this.hourOfDay);
            calendar2.set(12, this.minute);
            calendar2.set(13, this.second);
            calendar2.set(14, 0);
            if (calendar2.before(calendar)) {
                calendar2.add(5, 1);
            }
            return calendar2;
        }

        public String toString() {
            return String.format(Locale.US, "%02d:%02d:%02d", Integer.valueOf(this.hourOfDay), Integer.valueOf(this.minute), Integer.valueOf(this.second));
        }
    }

    public class IntelligentBatterySaverFastDrainTrafficStat {
        public boolean initialized = false;
        public long sampleTimeInSecs = 0;
        public long txBytes = 0;
        public long rxBytes = 0;

        public IntelligentBatterySaverFastDrainTrafficStat() {
        }
    }

    public class IntelligentBatterySaverBatteryBigData {
        public boolean initialized = false;
        public boolean actionEnabled = false;
        public float drainHightCurrent = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        public float restrictedCurrent = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;

        public IntelligentBatterySaverBatteryBigData() {
        }
    }

    public class IntelligentBatterySaverFastDrainBatteryInfo {
        public boolean initialized = false;
        public long startTime = 0;
        public int level = -1;

        public IntelligentBatterySaverFastDrainBatteryInfo() {
        }
    }

    public class IntelligentBatterySaverFastDrainReceiver extends BroadcastReceiver {
        public IntelligentBatterySaverFastDrainReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            String action = intent.getAction();
            action.hashCode();
            switch (action) {
                case "android.intent.action.TIME_SET":
                    if (IntelligentBatterySaverFastDrainPolicy.this.mAlarmManager != null && IntelligentBatterySaverFastDrainPolicy.this.mTimeoutAlarmListener != null) {
                        IntelligentBatterySaverFastDrainPolicy.this.mAlarmManager.cancel(IntelligentBatterySaverFastDrainPolicy.this.mTimeoutAlarmListener);
                    }
                    IntelligentBatterySaverFastDrainPolicy.this.lambda$new$0();
                    break;
                case "android.intent.action.ACTION_SHUTDOWN":
                case "android.intent.action.REBOOT":
                    IntelligentBatterySaverFastDrainPolicy.this.sendExitFastDrainRestrictionMessage();
                    break;
            }
        }
    }

    public final class MotionListener extends TriggerEventListener implements SensorEventListener {
        public boolean active;

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public MotionListener() {
            this.active = false;
        }

        @Override // android.hardware.TriggerEventListener
        public void onTrigger(TriggerEvent triggerEvent) {
            synchronized (IntelligentBatterySaverFastDrainPolicy.this) {
                this.active = false;
                Slog.d("IntelligentBatterySaverFastDrainPolicy", "onTrigger clear MOTION_STILL bit");
                IntelligentBatterySaverFastDrainPolicy.this.reportClearState(16);
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            synchronized (IntelligentBatterySaverFastDrainPolicy.this) {
                IntelligentBatterySaverFastDrainPolicy.this.mSensorManager.unregisterListener(this, IntelligentBatterySaverFastDrainPolicy.this.mMotionSensor);
                this.active = false;
            }
        }

        public boolean registerLocked() {
            boolean registerListener;
            if (IntelligentBatterySaverFastDrainPolicy.this.mMotionSensor.getReportingMode() == 2) {
                registerListener = IntelligentBatterySaverFastDrainPolicy.this.mSensorManager.requestTriggerSensor(IntelligentBatterySaverFastDrainPolicy.this.mMotionListener, IntelligentBatterySaverFastDrainPolicy.this.mMotionSensor);
            } else {
                registerListener = IntelligentBatterySaverFastDrainPolicy.this.mSensorManager.registerListener(IntelligentBatterySaverFastDrainPolicy.this.mMotionListener, IntelligentBatterySaverFastDrainPolicy.this.mMotionSensor, 3);
            }
            if (registerListener) {
                this.active = true;
            } else {
                Slog.e("IntelligentBatterySaverFastDrainPolicy", "Unable to register for " + IntelligentBatterySaverFastDrainPolicy.this.mMotionSensor);
            }
            return registerListener;
        }

        public void unregisterLocked() {
            if (IntelligentBatterySaverFastDrainPolicy.this.mMotionSensor.getReportingMode() == 2) {
                IntelligentBatterySaverFastDrainPolicy.this.mSensorManager.cancelTriggerSensor(IntelligentBatterySaverFastDrainPolicy.this.mMotionListener, IntelligentBatterySaverFastDrainPolicy.this.mMotionSensor);
            } else {
                IntelligentBatterySaverFastDrainPolicy.this.mSensorManager.unregisterListener(IntelligentBatterySaverFastDrainPolicy.this.mMotionListener);
            }
            this.active = false;
        }
    }

    public class ActionEntry {
        public IIntelligentBatterySaverFastDrainCallBack callBack;
        public String tag;

        public ActionEntry(String str, IIntelligentBatterySaverFastDrainCallBack iIntelligentBatterySaverFastDrainCallBack) {
            this.tag = str;
            this.callBack = iIntelligentBatterySaverFastDrainCallBack;
        }

        public boolean equals(Object obj) {
            if (obj instanceof ActionEntry) {
                ActionEntry actionEntry = (ActionEntry) obj;
                return this.tag.equals(actionEntry.tag) && this.callBack == actionEntry.callBack;
            }
            return super.equals(obj);
        }
    }

    public class IntelligentBatterySaverFastDrainHandler extends Handler {
        public IntelligentBatterySaverFastDrainHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    IntelligentBatterySaverFastDrainPolicy.this.start();
                    break;
                case 2:
                    IntelligentBatterySaverFastDrainPolicy.this.stop();
                    IntelligentBatterySaverFastDrainPolicy.this.mFastDrainInternalState = 2;
                    Slog.d("IntelligentBatterySaverFastDrainPolicy", "stop, move to off state, message");
                    IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger.add("stop, move to off state, message");
                    IntelligentBatterySaverFastDrainPolicy.this.resetBatteryInfo();
                    break;
                case 3:
                    if (IntelligentBatterySaverFastDrainPolicy.this.mFastDrainInternalState == 6) {
                        IntelligentBatterySaverFastDrainPolicy.this.enterFastDrainRestriction();
                        IntelligentBatterySaverFastDrainPolicy.this.mFastDrainInternalState = 1;
                        Slog.d("IntelligentBatterySaverFastDrainPolicy", "move to state on, message");
                        IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger.add("move to state on, message");
                        break;
                    }
                    break;
                case 4:
                    if (IntelligentBatterySaverFastDrainPolicy.this.mFastDrainInternalState == 1) {
                        IntelligentBatterySaverFastDrainPolicy.this.exitFastDrainRestriction();
                        IntelligentBatterySaverFastDrainPolicy.this.mFastDrainInternalState = 2;
                        Slog.d("IntelligentBatterySaverFastDrainPolicy", "move to off state, message");
                        IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger.add("move to off state, message");
                        break;
                    }
                    break;
                case 5:
                    if (!IntelligentBatterySaverFastDrainPolicy.this.testState(32)) {
                        IntelligentBatterySaverFastDrainPolicy.this.checkMusicSafe();
                    }
                    if (!IntelligentBatterySaverFastDrainPolicy.this.testState(64)) {
                        IntelligentBatterySaverFastDrainPolicy.this.checkDownloadSafe();
                        break;
                    }
                    break;
                case 6:
                    if (IntelligentBatterySaverFastDrainPolicy.this.isCheckBatteryDropState() || IntelligentBatterySaverFastDrainPolicy.this.isFastDrainRestrictionOn()) {
                        IntelligentBatterySaverFastDrainPolicy.this.checkBatteryInfo(message.arg1, message.arg2);
                        break;
                    }
                    break;
                case 8:
                    int i = message.arg1;
                    IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy = IntelligentBatterySaverFastDrainPolicy.this;
                    intelligentBatterySaverFastDrainPolicy.setSysState(i | intelligentBatterySaverFastDrainPolicy.mSysState);
                    break;
                case 9:
                    int i2 = message.arg1;
                    IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy2 = IntelligentBatterySaverFastDrainPolicy.this;
                    intelligentBatterySaverFastDrainPolicy2.setSysState((~i2) & intelligentBatterySaverFastDrainPolicy2.mSysState);
                    break;
                case 10:
                    IntelligentBatterySaverFastDrainPolicy.this.bootCompleted();
                    break;
            }
        }
    }

    public class IntelligentBatterySaverFastDrainAction {
        public String TAG = "IntelligentBatterySaverFastDrainAction";
        public IIntelligentBatterySaverFastDrainCallBack gpsCallBack;
        public Context mContext;
        public IIntelligentBatterySaverFastDrainCallBack mobiledataCallBack;
        public IIntelligentBatterySaverFastDrainCallBack wifiCallBack;

        public IntelligentBatterySaverFastDrainAction(Context context) {
            this.mContext = context;
            this.gpsCallBack = new IIntelligentBatterySaverFastDrainCallBack() { // from class: com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IntelligentBatterySaverFastDrainAction.1
                @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                public void doFastDrainRestriction() {
                    int i = Settings.Secure.getInt(IntelligentBatterySaverFastDrainAction.this.mContext.getContentResolver(), "location_mode", 0);
                    if (i != 0) {
                        if (1 == Settings.System.getInt(IntelligentBatterySaverFastDrainAction.this.mContext.getContentResolver(), "display_night_theme_scheduled", 0) && 1 == Settings.System.getInt(IntelligentBatterySaverFastDrainAction.this.mContext.getContentResolver(), "display_night_theme_scheduled_type", 2)) {
                            return;
                        }
                        Settings.Secure.putInt(IntelligentBatterySaverFastDrainAction.this.mContext.getContentResolver(), "location_mode", 0);
                        Slog.d(IntelligentBatterySaverFastDrainAction.this.TAG, "gpsState = " + i + " gps set disable");
                        IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger.add("gpsState = " + i + " gps set disable");
                        IntelligentBatterySaverFastDrainPolicy.this.saveIntState(i, "disable_gps_by_ibs");
                    }
                }

                @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                public void cancelFastDrainRestriction() {
                    int intState = IntelligentBatterySaverFastDrainPolicy.this.getIntState("disable_gps_by_ibs");
                    if (intState != 0) {
                        if (1 == Settings.System.getInt(IntelligentBatterySaverFastDrainAction.this.mContext.getContentResolver(), "display_night_theme_scheduled", 0) && 1 == Settings.System.getInt(IntelligentBatterySaverFastDrainAction.this.mContext.getContentResolver(), "display_night_theme_scheduled_type", 2)) {
                            return;
                        }
                        Settings.Secure.putInt(IntelligentBatterySaverFastDrainAction.this.mContext.getContentResolver(), "location_mode", intState);
                        Slog.d(IntelligentBatterySaverFastDrainAction.this.TAG, "gpsState = " + intState + " recover gps");
                        IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger.add("recover gps");
                        IntelligentBatterySaverFastDrainPolicy.this.saveIntState(0, "disable_gps_by_ibs");
                    }
                }
            };
            this.wifiCallBack = new IIntelligentBatterySaverFastDrainCallBack() { // from class: com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IntelligentBatterySaverFastDrainAction.2
                public WifiManager wifiManager;
                public boolean wifiState = false;
                public boolean wifiApState = true;

                @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                public void doFastDrainRestriction() {
                    WifiManager wifiManager = (WifiManager) IntelligentBatterySaverFastDrainAction.this.mContext.getSystemService("wifi");
                    this.wifiManager = wifiManager;
                    if (wifiManager != null) {
                        this.wifiState = wifiManager.isWifiEnabled();
                        this.wifiApState = this.wifiManager.isWifiApEnabled();
                        Slog.d(IntelligentBatterySaverFastDrainAction.this.TAG, "Disable wifi check wifiApState = " + this.wifiApState + ", wifiState = " + this.wifiState);
                        IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger.add("wifi wifiState = " + this.wifiState + " wifiApState = " + this.wifiApState);
                        if (this.wifiApState || !this.wifiState) {
                            return;
                        }
                        Slog.d(IntelligentBatterySaverFastDrainAction.this.TAG, "wifi set disable");
                        IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger.add("wifi set disable");
                        this.wifiManager.setWifiEnabled(false);
                        IntelligentBatterySaverFastDrainPolicy.this.saveBooleanState(true, "disable_wifi_by_ibs");
                    }
                }

                @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                public void cancelFastDrainRestriction() {
                    WifiManager wifiManager = (WifiManager) IntelligentBatterySaverFastDrainAction.this.mContext.getSystemService("wifi");
                    this.wifiManager = wifiManager;
                    if (wifiManager != null) {
                        this.wifiState = wifiManager.isWifiEnabled();
                        Slog.d(IntelligentBatterySaverFastDrainAction.this.TAG, "Recover wifi check wifiState = " + this.wifiState + ", operWifiState = " + IntelligentBatterySaverFastDrainPolicy.this.getBooleanState("disable_wifi_by_ibs"));
                        if (!this.wifiState && IntelligentBatterySaverFastDrainPolicy.this.getBooleanState("disable_wifi_by_ibs")) {
                            Slog.d(IntelligentBatterySaverFastDrainAction.this.TAG, "recover wifi");
                            IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger.add("recover wifi");
                            this.wifiManager.setWifiEnabled(true);
                        }
                    }
                    IntelligentBatterySaverFastDrainPolicy.this.saveBooleanState(false, "disable_wifi_by_ibs");
                }
            };
            this.mobiledataCallBack = new IIntelligentBatterySaverFastDrainCallBack() { // from class: com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IntelligentBatterySaverFastDrainAction.3
                public TelephonyManager telephonyManager;
                public WifiManager wifiManager;
                public boolean mobiledataState = false;
                public boolean wifiApState = true;

                @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                public void doFastDrainRestriction() {
                    WifiManager wifiManager = (WifiManager) IntelligentBatterySaverFastDrainAction.this.mContext.getSystemService("wifi");
                    this.wifiManager = wifiManager;
                    if (wifiManager != null) {
                        this.wifiApState = wifiManager.isWifiApEnabled();
                    }
                    IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger.add("wifiApState = " + this.wifiApState);
                    TelephonyManager from = TelephonyManager.from(IntelligentBatterySaverFastDrainAction.this.mContext);
                    this.telephonyManager = from;
                    if (from != null) {
                        this.mobiledataState = from.getDataEnabled();
                        Slog.d(IntelligentBatterySaverFastDrainAction.this.TAG, "Disable mobiledata check wifiApState = " + this.wifiApState + ", mobiledataState = " + this.mobiledataState);
                        IntelligentBatterySaverLogger intelligentBatterySaverLogger = IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger;
                        StringBuilder sb = new StringBuilder();
                        sb.append("mobiledataState = ");
                        sb.append(this.mobiledataState);
                        intelligentBatterySaverLogger.add(sb.toString());
                        if (this.wifiApState || !this.mobiledataState) {
                            return;
                        }
                        Slog.d(IntelligentBatterySaverFastDrainAction.this.TAG, "mobiledata set disable");
                        IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger.add("mobiledata set disable");
                        this.telephonyManager.setDataEnabled(false);
                        IntelligentBatterySaverFastDrainPolicy.this.saveBooleanState(true, "disable_mobile_data_by_ibs");
                    }
                }

                @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                public void cancelFastDrainRestriction() {
                    TelephonyManager from = TelephonyManager.from(IntelligentBatterySaverFastDrainAction.this.mContext);
                    this.telephonyManager = from;
                    if (from != null) {
                        this.mobiledataState = from.getDataEnabled();
                        Slog.d(IntelligentBatterySaverFastDrainAction.this.TAG, "Recover mobiledata check mobiledataState = " + this.mobiledataState + ", operMobileState = " + IntelligentBatterySaverFastDrainPolicy.this.getBooleanState("disable_mobile_data_by_ibs"));
                        if (!this.mobiledataState && IntelligentBatterySaverFastDrainPolicy.this.getBooleanState("disable_mobile_data_by_ibs")) {
                            Slog.d(IntelligentBatterySaverFastDrainAction.this.TAG, "recover mobiledata");
                            IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger.add("recover mobiledata");
                            this.telephonyManager.setDataEnabled(true);
                        }
                    }
                    IntelligentBatterySaverFastDrainPolicy.this.saveBooleanState(false, "disable_mobile_data_by_ibs");
                }
            };
        }

        public void regisiterAction() {
            IntelligentBatterySaverFastDrainPolicy.this.registerIntelligentBatterySaverFastDrainAction("GPS_IBSFastDrainAction", this.gpsCallBack, 1);
            IntelligentBatterySaverFastDrainPolicy.this.registerIntelligentBatterySaverFastDrainAction("wifi_IBSFastDrainAction", this.wifiCallBack, 1);
            IntelligentBatterySaverFastDrainPolicy.this.registerIntelligentBatterySaverFastDrainAction("mobiledata_IBSFastDrainAction", this.mobiledataCallBack, 1);
        }
    }

    public final void saveBooleanState(boolean z, String str) {
        if (this.mSharedPreferences == null && this.mContext != null) {
            Slog.d("IntelligentBatterySaverFastDrainPolicy", "Create shared preferences when save state.");
            this.mSharedPreferences = this.mContext.getSharedPreferences("oper_pref", 0);
        }
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            Slog.d("IntelligentBatterySaverFastDrainPolicy", "saveBooleanState type = " + str + " state = " + z);
            edit.putBoolean(str, z);
            edit.apply();
        }
    }

    public final boolean getBooleanState(String str) {
        if (this.mSharedPreferences == null && this.mContext != null) {
            Slog.d("IntelligentBatterySaverFastDrainPolicy", "Create shared preferences when get state.");
            this.mSharedPreferences = this.mContext.getSharedPreferences("oper_pref", 0);
        }
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences == null) {
            return false;
        }
        boolean z = sharedPreferences.getBoolean(str, false);
        Slog.d("IntelligentBatterySaverFastDrainPolicy", "getBooleanState type = " + str + " state = " + z);
        return z;
    }

    public final void saveIntState(int i, String str) {
        if (this.mSharedPreferences == null && this.mContext != null) {
            Slog.d("IntelligentBatterySaverFastDrainPolicy", "Create shared preferences when save state.");
            this.mSharedPreferences = this.mContext.getSharedPreferences("oper_pref", 0);
        }
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            Slog.d("IntelligentBatterySaverFastDrainPolicy", "saveIntState type = " + str + " state = " + i);
            edit.putInt(str, i);
            edit.apply();
        }
    }

    public final int getIntState(String str) {
        if (this.mSharedPreferences == null && this.mContext != null) {
            Slog.d("IntelligentBatterySaverFastDrainPolicy", "Create shared preferences when get state.");
            this.mSharedPreferences = this.mContext.getSharedPreferences("oper_pref", 0);
        }
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences == null) {
            return 0;
        }
        int i = sharedPreferences.getInt(str, 0);
        Slog.d("IntelligentBatterySaverFastDrainPolicy", "getIntState type = " + str + " state = " + i);
        return i;
    }

    public final boolean needSendBroadCast() {
        boolean z = (!((WifiManager) this.mContext.getSystemService("wifi")).isWifiEnabled() && getBooleanState("disable_wifi_by_ibs")) || (!TelephonyManager.from(this.mContext).isDataEnabled() && getBooleanState("disable_mobile_data_by_ibs"));
        Slog.d("IntelligentBatterySaverFastDrainPolicy", "needSendBroadCast need = " + z);
        return z;
    }
}
