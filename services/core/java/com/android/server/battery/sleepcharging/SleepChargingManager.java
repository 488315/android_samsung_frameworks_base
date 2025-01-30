package com.android.server.battery.sleepcharging;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.battery.BatteryLogger;
import com.android.server.battery.sleepcharging.SleepChargingTimeController;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.time.LocalDateTime;

/* loaded from: classes.dex */
public final class SleepChargingManager {
    public static final String TAG = "[SS]" + SleepChargingManager.class.getSimpleName();
    public final int THRESHOLD_BATTERY_LEVEL;
    public int mBatteryLevel;
    public long mChargeStartTime;
    public Context mContext;
    public Handler mDeliveredHandler;
    public Handler mHandler;
    public boolean mIsBatteryLevelHighEnough;
    public boolean mIsCharging;
    public boolean mIsChargingStable;
    public boolean mIsSleepCharging;
    public boolean mIsUserDismissSleepCharging;
    public int mMinutesToFullCharge;
    public int mPlugType;
    public long mSecondsToFullCharge;
    public SleepChargingTimeController mSleepChargingTimeController;
    public boolean mTheNextAlarmChagned;
    public HandlerThread mWorkerThread;
    public final int MSG_UPDATE_CHARGING_INFO = 11;
    public final int MSG_UPDATE_DISMISS = 12;
    public final int FULL_CHARGE_BATTERY_LEVEL = 100;
    public final int NOT_USED_MINUTES = -1;
    public final long TIME_TO_CHARGING_STABLE = 3000;

    public SleepChargingManager(Context context, Handler handler, int i) {
        String str = TAG;
        Slog.i(str, "SleepChargingManager CreatedVERSION:231116+");
        BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "SleepChargingManager Created", "VERSION:231116+ ,elapsedRealtime:" + SystemClock.elapsedRealtime());
        Slog.d(str, "protectionThreshold:" + i + " ,TIME_TO_CHARGING_STABLE:3000");
        this.mContext = context;
        this.mDeliveredHandler = handler;
        this.THRESHOLD_BATTERY_LEVEL = i;
        HandlerThread handlerThread = new HandlerThread("SleepChargingWorkerThread");
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        makeHandler();
        this.mSleepChargingTimeController = new SleepChargingTimeController(this.mContext, this.mWorkerThread, this.mHandler);
    }

    public void end() {
        Slog.i(TAG, "[end]");
        BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "SleepChargingManager end", "");
        this.mSleepChargingTimeController.end();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        HandlerThread handlerThread = this.mWorkerThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.mWorkerThread = null;
        }
    }

    public void updateChargingInfo(final int i, final long j, final int i2, final long j2) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.battery.sleepcharging.SleepChargingManager.1
            @Override // java.lang.Runnable
            public void run() {
                SleepChargingManager.this.mPlugType = i;
                SleepChargingManager.this.mChargeStartTime = j;
                SleepChargingManager.this.mBatteryLevel = i2;
                SleepChargingManager.this.mSecondsToFullCharge = j2;
                boolean z = true;
                if (!SleepChargingManager.this.mIsSleepCharging ? i == 0 || SleepChargingManager.this.mIsUserDismissSleepCharging || !SleepChargingManager.this.mSleepChargingTimeController.isActivated() : i != 0) {
                    z = false;
                }
                if (z) {
                    Slog.v(SleepChargingManager.TAG, "[updateChargingInfo]plugType:" + i + " ,chargeStartTime:" + j + " ,batteryLevel:" + i2 + " ,secondsToFullCharge:" + j2);
                    SleepChargingManager.this.mHandler.removeMessages(11);
                    SleepChargingManager.this.mHandler.sendEmptyMessage(11);
                }
            }
        });
    }

    public void updateDismiss() {
        Slog.i(TAG, "[updateDismiss]");
        this.mHandler.post(new Runnable() { // from class: com.android.server.battery.sleepcharging.SleepChargingManager.2
            @Override // java.lang.Runnable
            public void run() {
                SleepChargingManager.this.mHandler.sendEmptyMessage(12);
            }
        });
    }

    public final void makeHandler() {
        Slog.d(TAG, "[makeHandler]");
        this.mHandler = new Handler(this.mWorkerThread.getLooper()) { // from class: com.android.server.battery.sleepcharging.SleepChargingManager.3
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                Slog.i(SleepChargingManager.TAG, "[scm_handleMessage]msg:" + message.what);
                int i = message.what;
                if (i == 11) {
                    SleepChargingManager.this.processConditions();
                    return;
                }
                if (i != 12) {
                    switch (i) {
                        case 27:
                            if (SleepChargingManager.this.mIsSleepCharging) {
                                Slog.e(SleepChargingManager.TAG, "[scm_handleMessage]ON -> OFF (for reset)");
                                SleepChargingManager.this.mIsSleepCharging = false;
                                SleepChargingManager.this.sendResultOff();
                            }
                            SleepChargingManager.this.mIsUserDismissSleepCharging = false;
                            BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "Updated & Reset", SleepChargingManager.this.getInfoAll(true));
                            SleepChargingManager.this.processConditions();
                            break;
                        case 28:
                            SleepChargingManager.this.processConditions();
                            break;
                        case 29:
                            SleepChargingManager.this.mTheNextAlarmChagned = true;
                            SleepChargingManager.this.processConditions();
                            SleepChargingManager.this.mTheNextAlarmChagned = false;
                            break;
                    }
                    return;
                }
                if (SleepChargingManager.this.mIsSleepCharging) {
                    SleepChargingManager.this.mIsUserDismissSleepCharging = true;
                    SleepChargingManager.this.processConditions();
                }
            }
        };
    }

    public final void parseChargingInfo() {
        boolean z = this.mPlugType != 0;
        this.mIsCharging = z;
        if (z) {
            if (this.mBatteryLevel == 100) {
                this.mIsChargingStable = true;
            } else if (this.mSecondsToFullCharge <= 0) {
                this.mIsChargingStable = false;
            } else {
                this.mIsChargingStable = SystemClock.elapsedRealtime() - this.mChargeStartTime >= 3000;
            }
        } else {
            this.mIsChargingStable = false;
        }
        this.mIsBatteryLevelHighEnough = this.mBatteryLevel >= this.THRESHOLD_BATTERY_LEVEL;
        this.mMinutesToFullCharge = (int) ((this.mSecondsToFullCharge + 59) / 60);
    }

    public final void processConditions() {
        parseChargingInfo();
        String str = TAG;
        Slog.d(str, "[processConditions]charging:" + this.mIsCharging + " ,stable:" + this.mIsChargingStable + " ,levelEnough:" + this.mIsBatteryLevelHighEnough + " ,minsToFull:" + this.mMinutesToFullCharge + " ,dismiss:" + this.mIsUserDismissSleepCharging + " ,alarmChagned:" + this.mTheNextAlarmChagned);
        if (this.mIsSleepCharging) {
            if (this.mIsCharging && !this.mIsUserDismissSleepCharging && this.mSleepChargingTimeController.isActivated() && this.mSleepChargingTimeController.isNowSleepChargingTime(-1)) {
                if (this.mTheNextAlarmChagned) {
                    Slog.i(str, "[processConditions] ON -> ON (Alarm Changed)");
                    this.mSleepChargingTimeController.updateSleepChargingEndTime(true, -1);
                    sendResultUpdate();
                    return;
                }
                Slog.d(str, "[processConditions] ON -> ON");
                return;
            }
            Slog.i(str, "[processConditions] ON -> OFF");
            this.mIsSleepCharging = false;
            if (this.mIsUserDismissSleepCharging) {
                this.mSleepChargingTimeController.updateDismissSleepCharging();
            }
            this.mSleepChargingTimeController.updateSleepChargingEndTime(false, -1);
            sendResultOff();
            BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "ON -> OFF", getInfoAll(false));
            return;
        }
        if (this.mIsCharging && this.mIsChargingStable && this.mIsBatteryLevelHighEnough && !this.mIsUserDismissSleepCharging && this.mSleepChargingTimeController.isActivated() && this.mSleepChargingTimeController.isNowSleepChargingTime(this.mMinutesToFullCharge)) {
            Slog.i(str, "[processConditions] OFF -> ON");
            this.mIsSleepCharging = true;
            this.mSleepChargingTimeController.updateSleepChargingEndTime(true, this.mMinutesToFullCharge);
            sendResultOn();
            BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "OFF -> ON", getInfoAll(false));
            return;
        }
        Slog.d(str, "[processConditions] OFF -> OFF");
    }

    public final void sendResultOn() {
        Slog.d(TAG, "[sendResultOn]");
        LocalDateTime sleepChargingEndTime = this.mSleepChargingTimeController.getSleepChargingEndTime();
        Message obtainMessage = this.mDeliveredHandler.obtainMessage(1);
        obtainMessage.obj = SleepChargingTimeController.Util.convertLocalDateTimeToHHmmString(sleepChargingEndTime);
        this.mDeliveredHandler.sendMessage(obtainMessage);
    }

    public final void sendResultUpdate() {
        Slog.d(TAG, "[sendResultUpdate]");
        LocalDateTime sleepChargingEndTime = this.mSleepChargingTimeController.getSleepChargingEndTime();
        Message obtainMessage = this.mDeliveredHandler.obtainMessage(3);
        obtainMessage.obj = SleepChargingTimeController.Util.convertLocalDateTimeToHHmmString(sleepChargingEndTime);
        this.mDeliveredHandler.sendMessage(obtainMessage);
    }

    public final void sendResultOff() {
        Slog.d(TAG, "[sendResultOff]");
        Message obtainMessage = this.mDeliveredHandler.obtainMessage(2);
        obtainMessage.arg1 = this.mSleepChargingTimeController.isTodaySleepChargingFinished() ? 1 : 0;
        this.mDeliveredHandler.sendMessage(obtainMessage);
    }

    public void modifySleepPatternsForTest(final String str, final String str2, final String str3, final String str4) {
        Slog.e(TAG, "[modifySleepPatternsForTest]");
        this.mHandler.post(new Runnable() { // from class: com.android.server.battery.sleepcharging.SleepChargingManager.4
            @Override // java.lang.Runnable
            public void run() {
                SleepChargingManager.this.mSleepChargingTimeController.modifySleepPatternsForTest(str, str2, str3, str4);
            }
        });
    }

    public String getInfoAll(boolean z) {
        Slog.d(TAG, "[getInfo]includeSleepPatterns:" + z);
        StringBuilder sb = new StringBuilder();
        sb.append("[SleepChargingManager]\n");
        sb.append("mIsSleepCharging:" + this.mIsSleepCharging + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("mIsUserDismissSleepCharging:" + this.mIsUserDismissSleepCharging + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("mPlugType:" + this.mPlugType + " ,mIsCharging:" + this.mIsCharging + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("mChargeStartTime:" + this.mChargeStartTime + " ,mIsChargingStable:" + this.mIsChargingStable + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("mBatteryLevel:" + this.mBatteryLevel + " ,mIsBatteryLevelHighEnough:" + this.mIsBatteryLevelHighEnough + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("mSecondsToFullCharge:" + this.mSecondsToFullCharge + " ,mMinutesToFullCharge:" + this.mMinutesToFullCharge + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append(this.mSleepChargingTimeController.getInfo(z));
        return sb.toString();
    }
}
