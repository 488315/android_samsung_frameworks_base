package com.android.server.battery.sleepcharging;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.battery.BattLogBuffer;
import com.android.server.battery.BatteryLogger;
import com.android.server.battery.sleepcharging.PersonalPatternManager;
import com.android.server.battery.sleepcharging.SleepChargingTimeController;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SleepChargingManager {
    public final int THRESHOLD_BATTERY_LEVEL;
    public int mBatteryLevel;
    public long mChargeStartTime;
    public final Handler mDeliveredHandler;
    public final AnonymousClass3 mHandler;
    public boolean mIsBatteryLevelHighEnough;
    public boolean mIsCharging;
    public boolean mIsChargingStable;
    public boolean mIsSleepCharging;
    public boolean mIsUserDismissSleepCharging;
    public int mMinutesToFullCharge;
    public int mPlugType;
    public long mSecondsToFullCharge;
    public final SleepChargingTimeController mSleepChargingTimeController;
    public boolean mTheNextAlarmChagned;
    public HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.battery.sleepcharging.SleepChargingManager$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int val$batteryLevel;
        public final /* synthetic */ int val$plugType;
        public final /* synthetic */ long val$secondsToFullCharge;

        public AnonymousClass1(int i, int i2, long j) {
            this.val$plugType = i;
            this.val$batteryLevel = i2;
            this.val$secondsToFullCharge = j;
        }

        @Override // java.lang.Runnable
        public final void run() {
            SleepChargingManager sleepChargingManager = SleepChargingManager.this;
            int i = this.val$plugType;
            sleepChargingManager.mPlugType = i;
            sleepChargingManager.mBatteryLevel = this.val$batteryLevel;
            sleepChargingManager.mSecondsToFullCharge = this.val$secondsToFullCharge;
            if (i != 0 && sleepChargingManager.mChargeStartTime == 0) {
                sleepChargingManager.mChargeStartTime = SystemClock.elapsedRealtime();
                BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("[updateChargingInfo]Started mChargeStartTime:"), SleepChargingManager.this.mChargeStartTime, "[SS]SleepChargingManager");
            } else if (i == 0 && sleepChargingManager.mChargeStartTime != 0) {
                sleepChargingManager.mChargeStartTime = 0L;
                Slog.d("[SS]SleepChargingManager", "[updateChargingInfo]End mChargeStartTime");
            }
            SleepChargingManager sleepChargingManager2 = SleepChargingManager.this;
            if (sleepChargingManager2.mIsSleepCharging) {
                if (this.val$plugType != 0) {
                    return;
                }
            } else if (this.val$plugType == 0 || sleepChargingManager2.mIsUserDismissSleepCharging || !sleepChargingManager2.mSleepChargingTimeController.isActivated()) {
                return;
            }
            Slog.v("[SS]SleepChargingManager", "[updateChargingInfo]plugType:" + this.val$plugType + " ,batteryLevel:" + this.val$batteryLevel + " ,secondsToFullCharge:" + this.val$secondsToFullCharge);
            removeMessages(11);
            sendEmptyMessage(11);
        }
    }

    /* renamed from: -$$Nest$mprocessConditions, reason: not valid java name */
    public static void m305$$Nest$mprocessConditions(SleepChargingManager sleepChargingManager) {
        boolean z = sleepChargingManager.mPlugType != 0;
        sleepChargingManager.mIsCharging = z;
        if (!z) {
            sleepChargingManager.mIsChargingStable = false;
        } else if (sleepChargingManager.mBatteryLevel == 100) {
            sleepChargingManager.mIsChargingStable = true;
        } else if (sleepChargingManager.mSecondsToFullCharge <= 0 || sleepChargingManager.mChargeStartTime == 0) {
            sleepChargingManager.mIsChargingStable = false;
        } else {
            sleepChargingManager.mIsChargingStable = SystemClock.elapsedRealtime() - sleepChargingManager.mChargeStartTime >= 3000;
        }
        sleepChargingManager.mIsBatteryLevelHighEnough = sleepChargingManager.mBatteryLevel >= sleepChargingManager.THRESHOLD_BATTERY_LEVEL;
        sleepChargingManager.mMinutesToFullCharge = (int) ((sleepChargingManager.mSecondsToFullCharge + 59) / 60);
        StringBuilder sb = new StringBuilder("[processConditions]charging:");
        sb.append(sleepChargingManager.mIsCharging);
        sb.append(" ,stable:");
        sb.append(sleepChargingManager.mIsChargingStable);
        sb.append(" ,levelEnough:");
        sb.append(sleepChargingManager.mIsBatteryLevelHighEnough);
        sb.append(" ,minsToFull:");
        sb.append(sleepChargingManager.mMinutesToFullCharge);
        sb.append(" ,dismiss:");
        sb.append(sleepChargingManager.mIsUserDismissSleepCharging);
        sb.append(" ,alarmChagned:");
        AnyMotionDetector$$ExternalSyntheticOutline0.m("[SS]SleepChargingManager", sb, sleepChargingManager.mTheNextAlarmChagned);
        boolean z2 = sleepChargingManager.mIsSleepCharging;
        Handler handler = sleepChargingManager.mDeliveredHandler;
        SleepChargingTimeController sleepChargingTimeController = sleepChargingManager.mSleepChargingTimeController;
        if (!z2) {
            if (!sleepChargingManager.mIsCharging || !sleepChargingManager.mIsChargingStable || !sleepChargingManager.mIsBatteryLevelHighEnough || sleepChargingManager.mIsUserDismissSleepCharging || !sleepChargingTimeController.isActivated() || !sleepChargingTimeController.isNowSleepChargingTime(sleepChargingManager.mMinutesToFullCharge)) {
                Slog.d("[SS]SleepChargingManager", "[processConditions] OFF -> OFF");
                return;
            }
            Slog.i("[SS]SleepChargingManager", "[processConditions] OFF -> ON");
            sleepChargingManager.mIsSleepCharging = true;
            sleepChargingTimeController.updateSleepChargingEndTime(sleepChargingManager.mMinutesToFullCharge, true);
            Slog.d("[SS]SleepChargingManager", "[sendResultOn]");
            LocalDateTime localDateTime = sleepChargingTimeController.mSleepChargingEndDateTime;
            Message obtainMessage = handler.obtainMessage(1);
            obtainMessage.obj = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            handler.sendMessage(obtainMessage);
            BattLogBuffer.addLog(2, "[ON]" + sleepChargingTimeController.getInfoForOn());
            BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "OFF -> ON", sleepChargingManager.getInfoAll(false));
            return;
        }
        if (!sleepChargingManager.mIsCharging || sleepChargingManager.mIsUserDismissSleepCharging || !sleepChargingTimeController.isActivated() || !sleepChargingTimeController.isNowSleepChargingTime(-1)) {
            Slog.i("[SS]SleepChargingManager", "[processConditions] ON -> OFF");
            sleepChargingManager.mIsSleepCharging = false;
            if (sleepChargingManager.mIsUserDismissSleepCharging) {
                sleepChargingTimeController.getClass();
                Slog.d("[SS]SleepChargingTimeController", "[updateDismissSleepCharging]");
                sleepChargingTimeController.mIsUserDismissSleepCharging = true;
                sleepChargingTimeController.activateNextAlarmChangedReceiver(false);
            }
            sleepChargingTimeController.updateSleepChargingEndTime(-1, false);
            sleepChargingManager.sendResultOff();
            BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "ON -> OFF", sleepChargingManager.getInfoAll(false));
            return;
        }
        if (!sleepChargingManager.mTheNextAlarmChagned) {
            Slog.d("[SS]SleepChargingManager", "[processConditions] ON -> ON");
            return;
        }
        Slog.i("[SS]SleepChargingManager", "[processConditions] ON -> ON (Alarm Changed)");
        sleepChargingTimeController.updateSleepChargingEndTime(-1, true);
        Slog.d("[SS]SleepChargingManager", "[sendResultUpdate]");
        LocalDateTime localDateTime2 = sleepChargingTimeController.mSleepChargingEndDateTime;
        Message obtainMessage2 = handler.obtainMessage(3);
        obtainMessage2.obj = localDateTime2.format(DateTimeFormatter.ofPattern("HH:mm"));
        handler.sendMessage(obtainMessage2);
        BattLogBuffer.addLog(2, "[UPDATE]" + sleepChargingTimeController.getInfoForOn());
    }

    /* JADX WARN: Type inference failed for: r8v2, types: [com.android.server.battery.sleepcharging.SleepChargingManager$3] */
    public SleepChargingManager(Context context, BatteryService.AnonymousClass1 anonymousClass1, int i) {
        Slog.i("[SS]SleepChargingManager", "SleepChargingManager CreatedVERSION:240902");
        BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "SleepChargingManager Created", "VERSION:240902 ,elapsedRealtime:" + SystemClock.elapsedRealtime());
        StringBuilder sb = new StringBuilder("protectionThreshold:");
        sb.append(i);
        BootReceiver$$ExternalSyntheticOutline0.m(sb, " ,TIME_TO_CHARGING_STABLE:3000", "[SS]SleepChargingManager");
        this.mDeliveredHandler = anonymousClass1;
        this.THRESHOLD_BATTERY_LEVEL = i;
        HandlerThread handlerThread = new HandlerThread("SleepChargingWorkerThread");
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        Slog.d("[SS]SleepChargingManager", "[makeHandler]");
        this.mHandler = new Handler(this.mWorkerThread.getLooper()) { // from class: com.android.server.battery.sleepcharging.SleepChargingManager.3
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("[scm_handleMessage]msg:"), message.what, "[SS]SleepChargingManager");
                int i2 = message.what;
                SleepChargingManager sleepChargingManager = SleepChargingManager.this;
                if (i2 == 11) {
                    SleepChargingManager.m305$$Nest$mprocessConditions(sleepChargingManager);
                }
                if (i2 == 12) {
                    if (sleepChargingManager.mIsSleepCharging) {
                        sleepChargingManager.mIsUserDismissSleepCharging = true;
                        SleepChargingManager.m305$$Nest$mprocessConditions(sleepChargingManager);
                        return;
                    }
                    return;
                }
                switch (i2) {
                    case 27:
                        if (sleepChargingManager.mIsSleepCharging) {
                            Slog.e("[SS]SleepChargingManager", "[scm_handleMessage]ON -> OFF (for reset)");
                            sleepChargingManager.mIsSleepCharging = false;
                            sleepChargingManager.sendResultOff();
                        }
                        sleepChargingManager.mIsUserDismissSleepCharging = false;
                        BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "Updated & Reset", sleepChargingManager.getInfoAll(true));
                        StringBuilder sb2 = new StringBuilder("[Updated SleepChargingStatus]");
                        SleepChargingTimeController sleepChargingTimeController = sleepChargingManager.mSleepChargingTimeController;
                        StringBuilder sb3 = new StringBuilder();
                        if (sleepChargingTimeController.mSleepChargingStatus == null) {
                            sb3.append("SleepChargingStatus null");
                        } else {
                            sb3.append(sleepChargingTimeController.mSleepChargingStatus.matchedDate.getDayOfWeek().toString() + ",  ");
                            sb3.append("confidence:" + sleepChargingTimeController.mSleepChargingStatus.confidence + ",  ");
                            sb3.append("sleepTime:" + sleepChargingTimeController.mSleepChargingStatus.bedDateTime + " ~ " + sleepChargingTimeController.mSleepChargingStatus.wakeupDateTime);
                        }
                        sb2.append(sb3.toString());
                        BattLogBuffer.addLog(2, sb2.toString());
                        SleepChargingManager.m305$$Nest$mprocessConditions(sleepChargingManager);
                        break;
                    case 28:
                        SleepChargingManager.m305$$Nest$mprocessConditions(sleepChargingManager);
                        break;
                    case 29:
                        sleepChargingManager.mTheNextAlarmChagned = true;
                        SleepChargingManager.m305$$Nest$mprocessConditions(sleepChargingManager);
                        sleepChargingManager.mTheNextAlarmChagned = false;
                        break;
                }
            }
        };
        HandlerThread handlerThread2 = this.mWorkerThread;
        AnonymousClass3 anonymousClass3 = this.mHandler;
        SleepChargingTimeController sleepChargingTimeController = new SleepChargingTimeController();
        sleepChargingTimeController.mSleepPatterns = new HashMap();
        sleepChargingTimeController.mIsEndCalled = false;
        Slog.d("[SS]SleepChargingTimeController", "SleepChargingTimeController Created");
        Slog.d("[SS]SleepChargingTimeController", "mRuglarUpdateCheckTime:" + SleepChargingTimeController.mRuglarUpdateCheckTime);
        sleepChargingTimeController.mContext = context;
        sleepChargingTimeController.mDeliveredHandler = anonymousClass3;
        PersonalPatternManager personalPatternManager = new PersonalPatternManager();
        Slog.d("[SS]PersonalPatternManager", "PersonalPatternManager Created");
        personalPatternManager.mContext = context;
        sleepChargingTimeController.mPersonalPatternManager = personalPatternManager;
        Slog.d("[SS]SleepChargingTimeController", "[makeHandler]");
        sleepChargingTimeController.mHandler = new Handler(handlerThread2.getLooper()) { // from class: com.android.server.battery.sleepcharging.SleepChargingTimeController.1
            public AnonymousClass1(Looper looper) {
                super(looper);
            }

            /* JADX WARN: Removed duplicated region for block: B:38:0x01e0 A[Catch: Exception -> 0x01e4, TRY_ENTER, TRY_LEAVE, TryCatch #6 {Exception -> 0x01e4, blocks: (B:38:0x01e0, B:109:0x01fa, B:108:0x01f7, B:103:0x01f1), top: B:33:0x00ef, inners: #2 }] */
            /* JADX WARN: Removed duplicated region for block: B:41:0x0237  */
            /* JADX WARN: Removed duplicated region for block: B:50:0x02c3  */
            /* JADX WARN: Removed duplicated region for block: B:53:0x02d5  */
            /* JADX WARN: Removed duplicated region for block: B:63:0x0300  */
            /* JADX WARN: Removed duplicated region for block: B:64:0x02c7  */
            /* JADX WARN: Removed duplicated region for block: B:70:0x02b5  */
            /* JADX WARN: Removed duplicated region for block: B:96:0x01e6 A[LOOP:0: B:77:0x0122->B:96:0x01e6, LOOP_END] */
            /* JADX WARN: Removed duplicated region for block: B:97:0x01de A[SYNTHETIC] */
            @Override // android.os.Handler
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void handleMessage(android.os.Message r24) {
                /*
                    Method dump skipped, instructions count: 796
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.battery.sleepcharging.SleepChargingTimeController.AnonymousClass1.handleMessage(android.os.Message):void");
            }
        };
        sleepChargingTimeController.activateTimeReachedReceiver(true);
        sleepChargingTimeController.mHandler.sendEmptyMessageDelayed(21, 5000L);
        this.mSleepChargingTimeController = sleepChargingTimeController;
        BattLogBuffer.addLog(2, "[Activated]");
    }

    public final String getInfoAll(boolean z) {
        Slog.d("[SS]SleepChargingManager", "[getInfo]includeSleepPatterns:" + z);
        StringBuilder sb = new StringBuilder("[SleepChargingManager]\n");
        sb.append("mIsSleepCharging:" + this.mIsSleepCharging + "\n");
        sb.append("mIsUserDismissSleepCharging:" + this.mIsUserDismissSleepCharging + "\n");
        sb.append("mPlugType:" + this.mPlugType + " ,mIsCharging:" + this.mIsCharging + "\n");
        sb.append("mChargeStartTime:" + this.mChargeStartTime + " ,mIsChargingStable:" + this.mIsChargingStable + "\n");
        sb.append("mBatteryLevel:" + this.mBatteryLevel + " ,mIsBatteryLevelHighEnough:" + this.mIsBatteryLevelHighEnough + "\n");
        sb.append("mSecondsToFullCharge:" + this.mSecondsToFullCharge + " ,mMinutesToFullCharge:" + this.mMinutesToFullCharge + "\n");
        sb.append("\n");
        SleepChargingTimeController sleepChargingTimeController = this.mSleepChargingTimeController;
        sleepChargingTimeController.getClass();
        Slog.d("[SS]SleepChargingTimeController", "[getInfo]includeSleepPatterns:" + z);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("[SleepChargingTimeController]" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + "\n");
        sb2.append("mSleepPatterns.size():" + sleepChargingTimeController.mSleepPatterns.size() + "\n");
        if (z) {
            Iterator it = sleepChargingTimeController.mSleepPatterns.entrySet().iterator();
            while (it.hasNext()) {
                PersonalPatternManager.SleepPattern sleepPattern = (PersonalPatternManager.SleepPattern) ((Map.Entry) it.next()).getValue();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("[BackupOnOffExceptTime]" + sleepPattern.weekType.substring(0, 3));
                sb3.append(" ,time:" + Instant.ofEpochMilli(sleepPattern.bedTimeMillis).atZone(ZoneId.of("UTC")).toLocalTime() + " ~ " + Instant.ofEpochMilli(sleepPattern.wakeupTimeMillis).atZone(ZoneId.of("UTC")).toLocalTime());
                StringBuilder sb4 = new StringBuilder(" ,confidence:");
                sb4.append(sleepPattern.confidence);
                sb3.append(sb4.toString());
                sb3.append(" ,isConfident:" + sleepPattern.isConfident);
                sb3.append("\n");
                sb2.append(sb3.toString());
            }
        }
        SleepChargingTimeController.SleepChargingStatus sleepChargingStatus = sleepChargingTimeController.mSleepChargingStatus;
        if (sleepChargingStatus == null) {
            sb2.append("SleepChargingStatus null\n");
        } else {
            Slog.d("[SS]SleepChargingTimeController", "[getInfo]");
            StringBuilder sb5 = new StringBuilder();
            sb5.append("[SleepChargingStatus]" + sleepChargingStatus.matchedDate + "(" + sleepChargingStatus.matchedDate.getDayOfWeek().toString() + ")\n");
            sb5.append("sleepTime:" + sleepChargingStatus.bedDateTime + " ~ " + sleepChargingStatus.wakeupDateTime + "\n");
            sb5.append("confidence:" + sleepChargingStatus.confidence + ",isConfident:" + sleepChargingStatus.isConfident + "\n");
            sb5.append("sleepChargingStartDateTime:" + sleepChargingStatus.sleepChargingStartDateTime + " ,expectedFullChargeDateTime:" + sleepChargingStatus.expectedFullChargeDateTime + "\n");
            StringBuilder sb6 = new StringBuilder("isNowSessionTime:");
            sb6.append(sleepChargingStatus.isNowSessionTime);
            sb6.append("\n");
            sb5.append(sb6.toString());
            sb5.append("\n");
            sb2.append(sb5.toString());
        }
        sb2.append("mIsTodaySleepChargingFinished:" + sleepChargingTimeController.mIsTodaySleepChargingFinished + "\n");
        sb2.append("mNextAlarmDateTimeWithinSleepTime:" + sleepChargingTimeController.mNextAlarmDateTimeWithinSleepTime + "\n");
        sb2.append("mSavedMinutesToFullCharge:" + sleepChargingTimeController.mSavedMinutesToFullCharge + "\n");
        sb2.append("mSleepChargingEndDateTime:" + sleepChargingTimeController.mSleepChargingEndDateTime + "\n");
        sb2.append("mScheduledUpdateTime:" + sleepChargingTimeController.mScheduledUpdateTime + "\n");
        sb2.append("mScheduledSleepChargingStartTime:" + sleepChargingTimeController.mScheduledSleepChargingStartTime + "\n");
        sb2.append("mScheduledSleepChargingEndTime:" + sleepChargingTimeController.mScheduledSleepChargingEndTime + "\n");
        sb2.append("mScheduledWakeupTime:" + sleepChargingTimeController.mScheduledWakeupTime + "\n");
        sb2.append("\n");
        sb.append(sb2.toString());
        return sb.toString();
    }

    public final void sendResultOff() {
        Slog.d("[SS]SleepChargingManager", "[sendResultOff]");
        Handler handler = this.mDeliveredHandler;
        Message obtainMessage = handler.obtainMessage(2);
        SleepChargingTimeController sleepChargingTimeController = this.mSleepChargingTimeController;
        obtainMessage.arg1 = sleepChargingTimeController.mIsTodaySleepChargingFinished ? 1 : 0;
        handler.sendMessage(obtainMessage);
        BattLogBuffer.addLog(2, "[OFF]mIsCharging:" + this.mIsCharging + " ,mIsUserDismissSleepCharging:" + this.mIsUserDismissSleepCharging + " ,isTodaySleepChargingFinished:" + sleepChargingTimeController.mIsTodaySleepChargingFinished);
    }
}
