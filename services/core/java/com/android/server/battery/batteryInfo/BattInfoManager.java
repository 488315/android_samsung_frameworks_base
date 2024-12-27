package com.android.server.battery.batteryInfo;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.battery.BattFeatures;
import com.android.server.battery.BattLogBuffer;
import com.android.server.battery.BattUtils;
import com.android.server.battery.BatteryLogger;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BattInfoManager {
    public AsocData mAsocData;
    public boolean[] mAuthentificationResults;
    public final int mBatteryCount;
    public final int mBatteryType;
    public final Context mContext;
    public DischargeLevelData mDischargeLevelData;
    public FirstUseDateData mFirstUseDateData;
    public FullStatusUsageData mFullStatusUsageData;
    public final Handler mHandler;
    public boolean mInitFinished;
    public QrData mQrData;
    public final boolean mSupportsAsoc;
    public final boolean mSupportsFullStatusUsage;
    public HandlerThread mWorkerThread;
    public int mInitCheckStatusCount = 0;
    public long mBatteryUsageSinceLastAsocUpdate = 0;
    public long mCurrentBatteryUsage = 0;
    public long mFullBatteryStartTime = -1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.battery.batteryInfo.BattInfoManager$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BattInfoManager this$0;
        public final /* synthetic */ long val$currentbatteryUsage;

        public /* synthetic */ AnonymousClass2(BattInfoManager battInfoManager, long j, int i) {
            this.$r8$classId = i;
            this.this$0 = battInfoManager;
            this.val$currentbatteryUsage = j;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    DischargeLevelData dischargeLevelData = this.this$0.mDischargeLevelData;
                    long j = this.val$currentbatteryUsage;
                    int i = dischargeLevelData.mBatteryType;
                    if (i == 2 || i == 4) {
                        dischargeLevelData.updateEfsFromSBP(j);
                    } else {
                        dischargeLevelData.addAndSave(j);
                    }
                    if (BattFeatures.FEATURE_SAVE_BATTERY_CYCLE) {
                        this.this$0.processCycle();
                        break;
                    }
                    break;
                default:
                    this.this$0.mFullStatusUsageData.addAndSave(this.val$currentbatteryUsage);
                    if (BattFeatures.FEATURE_SAVE_BATTERY_CYCLE) {
                        this.this$0.processCycle();
                        break;
                    }
                    break;
            }
        }
    }

    public BattInfoManager(Context context) {
        int i;
        int i2;
        boolean z = false;
        this.mContext = context;
        if (BattUtils.isExist("/sys/class/power_supply/sec_auth_2nd/qr_code")) {
            this.mBatteryType = 3;
            this.mBatteryCount = 2;
        } else if (BattUtils.isExist("/sys/class/power_supply/sec_auth/qr_code")) {
            this.mBatteryType = 1;
            this.mBatteryCount = 1;
        } else if (BattUtils.isExist("/sys/class/power_supply/sbp-fg-2/qr_code")) {
            this.mBatteryType = 4;
            this.mBatteryCount = 2;
        } else if (BattUtils.isExist("/sys/class/power_supply/sbp-fg/qr_code")) {
            this.mBatteryType = 2;
            this.mBatteryCount = 1;
        } else {
            this.mBatteryType = 0;
            this.mBatteryCount = 1;
        }
        this.mAuthentificationResults = new boolean[this.mBatteryCount];
        Slog.i("[SS][BattInfo]BattInfoManager", "[BattInfoManager]mBatteryType:" + this.mBatteryType + " ,mBatteryCount:" + this.mBatteryCount);
        StringBuilder sb = new StringBuilder("[BattInfoManager]FEATURE_SAVE_BATTERY_CYCLE:");
        sb.append(BattFeatures.FEATURE_SAVE_BATTERY_CYCLE);
        sb.append(" ,FEATURE_FULL_BATTERY_CYCLE:");
        boolean z2 = BattFeatures.FEATURE_FULL_BATTERY_CYCLE;
        HeimdAllFsService$$ExternalSyntheticOutline0.m("[SS][BattInfo]BattInfoManager", sb, z2);
        boolean z3 = (!BattFeatures.FEATURE_SUPPORT_ASOC || (i2 = this.mBatteryType) == 2 || i2 == 4) ? false : true;
        this.mSupportsAsoc = z3;
        if (z2 && (i = this.mBatteryType) != 2 && i != 4) {
            z = true;
        }
        this.mSupportsFullStatusUsage = z;
        Slog.i("[SS][BattInfo]BattInfoManager", "[BattInfoManager]supportsAsoc:" + z3 + " ,supportsFullStatusUsage:" + z);
        BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "BattInfoManager Created", "mBatteryType:" + this.mBatteryType + " ,mBatteryCount:" + this.mBatteryCount + " ,supportsAsoc:" + z3 + " ,supportsFullStatusUsage:" + z);
        StringBuilder sb2 = new StringBuilder("mBatteryType:");
        sb2.append(this.mBatteryType);
        sb2.append(" ,mBatteryCount:");
        sb2.append(this.mBatteryCount);
        BattLogBuffer.addLog(3, sb2.toString());
        HandlerThread handlerThread = new HandlerThread("BattInfoManagerWorkerThread");
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        Handler handler = new Handler(this.mWorkerThread.getLooper());
        this.mHandler = handler;
        handler.postDelayed(new BattInfoManager$$ExternalSyntheticLambda0(this), 3000L);
    }

    public final long[] getAsocValue() {
        if (!this.mInitFinished) {
            Slog.w("[SS][BattInfo]BattInfoManager", "[getAsocValue]InitFinished False");
            return null;
        }
        if (this.mSupportsAsoc) {
            return Arrays.stream((Long[]) this.mAsocData.readEfsValues()).mapToLong(new AsocData$$ExternalSyntheticLambda1()).toArray();
        }
        Slog.w("[SS][BattInfo]BattInfoManager", "[getAsocValue]unsupported");
        return null;
    }

    public final long[] getDischargeLevel() {
        if (this.mInitFinished) {
            return Arrays.stream((Long[]) this.mDischargeLevelData.readEfsValues()).mapToLong(new AsocData$$ExternalSyntheticLambda1()).toArray();
        }
        Slog.w("[SS][BattInfo]BattInfoManager", "[getDischargeLevel]InitFinished False");
        return null;
    }

    public final long[] getFullStatusUsage() {
        if (!this.mInitFinished) {
            Slog.w("[SS][BattInfo]BattInfoManager", "[getFullStatusUsage]InitFinished False");
            return null;
        }
        if (this.mSupportsFullStatusUsage) {
            return Arrays.stream((Long[]) this.mFullStatusUsageData.readEfsValues()).mapToLong(new AsocData$$ExternalSyntheticLambda1()).toArray();
        }
        Slog.w("[SS][BattInfo]BattInfoManager", "[getFullStatusUsage]unsupported");
        return null;
    }

    public final boolean[] getIcAuthenticationResults() {
        if (!this.mInitFinished) {
            Slog.w("[SS][BattInfo]BattInfoManager", "[getIcAuthenticationResults]InitFinished False");
            return null;
        }
        Slog.d("[SS][BattInfo]BattInfoManager", "[getIcAuthenticationResults]IcAuthenticationResults:" + Arrays.toString(this.mAuthentificationResults));
        return this.mAuthentificationResults;
    }

    public final void processAsoc(long j, long j2) {
        if (!this.mInitFinished) {
            Slog.w("[SS][BattInfo]BattInfoManager", "[processAsoc]InitFinished False");
            return;
        }
        if (j2 == j || j == 0) {
            return;
        }
        if (j2 < j) {
            this.mBatteryUsageSinceLastAsocUpdate = (j - j2) + this.mBatteryUsageSinceLastAsocUpdate;
            BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("[processAsoc]mBatteryUsageSinceLastAsocUpdate:"), this.mBatteryUsageSinceLastAsocUpdate, "[SS][BattInfo]BattInfoManager");
        }
        AsocData asocData = this.mAsocData;
        if (Arrays.stream((Long[]) asocData.mCurrentValues).anyMatch(new AsocData$$ExternalSyntheticLambda0())) {
            Slog.w(asocData.TAG, "[isValueInvalid]Invalid - mCurrentValues:" + Arrays.toString(asocData.mCurrentValues));
        } else if (this.mBatteryUsageSinceLastAsocUpdate < 100) {
            return;
        }
        this.mBatteryUsageSinceLastAsocUpdate = 0L;
        this.mHandler.post(new Runnable() { // from class: com.android.server.battery.batteryInfo.BattInfoManager.1
            @Override // java.lang.Runnable
            public final void run() {
                BattInfoManager.this.mAsocData.updateAndSet();
            }
        });
    }

    public final void processCycle() {
        if (this.mSupportsFullStatusUsage) {
            this.mDischargeLevelData.setCycle(this.mFullStatusUsageData);
        } else {
            this.mDischargeLevelData.setCycle(null);
        }
    }

    public final void processDischargingLevel(long j, long j2, boolean z) {
        if (!this.mInitFinished) {
            Slog.w("[SS][BattInfo]BattInfoManager", "[processDischargingLevel]InitFinished False");
            return;
        }
        if (j == 0) {
            return;
        }
        if (j2 < j) {
            this.mCurrentBatteryUsage = (j - j2) + this.mCurrentBatteryUsage;
            BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("[processDischargingLevel]mCurrentBatteryUsage:"), this.mCurrentBatteryUsage, "[SS][BattInfo]BattInfoManager");
        }
        if (z || this.mCurrentBatteryUsage >= 10) {
            long j3 = this.mCurrentBatteryUsage;
            this.mCurrentBatteryUsage = 0L;
            this.mHandler.post(new AnonymousClass2(this, j3, 0));
        }
    }

    public final void processFullStatusUsage(long j, boolean z) {
        if (!this.mInitFinished) {
            Slog.w("[SS][BattInfo]BattInfoManager", "[processFullStatusUsage]InitFinished False");
            return;
        }
        if (this.mFullBatteryStartTime == -1) {
            if (j == 100) {
                this.mFullBatteryStartTime = SystemClock.elapsedRealtime();
                Slog.i("[SS][BattInfo]BattInfoManager", "[processFullStatusUsage]FullBatteryStartTime:" + this.mFullBatteryStartTime);
                return;
            }
            return;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j2 = (elapsedRealtime - this.mFullBatteryStartTime) / 60000;
        if (z || j != 100 || j2 >= 10) {
            if (j == 100) {
                this.mFullBatteryStartTime = elapsedRealtime;
                Slog.i("[SS][BattInfo]BattInfoManager", "[processFullStatusUsage]time to save. maintains 100%");
            } else {
                this.mFullBatteryStartTime = -1L;
                Slog.i("[SS][BattInfo]BattInfoManager", "[processFullStatusUsage]time to save. end of 100%");
            }
            this.mHandler.post(new AnonymousClass2(this, j2, 1));
        }
    }
}
