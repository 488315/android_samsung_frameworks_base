package com.android.server.battery.batteryInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.battery.BattUtils;
import com.android.server.battery.BatteryLogger;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FirstUseDateData extends BaseData {
    public final ArrayList authFaiExpiredPaths;
    public final Context mContext;
    public DateChangedReceiver mDateChangedReceiver;
    public Handler mHandler;
    public final boolean[] mShouldCheckFaiExpireds;
    public final AnonymousClass1 mUpdateDateRunnable;
    public DateChangedReceiver mWriteFirstUseDateReceiver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.battery.batteryInfo.FirstUseDateData$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:45:0x02d2  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x02f8  */
        /* JADX WARN: Removed duplicated region for block: B:52:0x013b A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:62:0x0174  */
        /* JADX WARN: Type inference failed for: r11v3, types: [java.lang.Object, java.time.LocalDateTime] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 792
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.battery.batteryInfo.FirstUseDateData.AnonymousClass1.run():void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DateChangedReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ FirstUseDateData this$0;

        public /* synthetic */ DateChangedReceiver(FirstUseDateData firstUseDateData, int i) {
            this.$r8$classId = i;
            this.this$0 = firstUseDateData;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    Slog.d(this.this$0.TAG, "[DateChangedReceiver_onReceive]");
                    this.this$0.mHandler.post(new AnonymousClass1(1, this));
                    break;
                default:
                    Slog.d(
                            this.this$0.TAG,
                            "[WriteFirstUseDateReceiver_onReceive]intent:" + intent.getAction());
                    FirstUseDateData firstUseDateData = this.this$0;
                    firstUseDateData.mHandler.removeCallbacks(firstUseDateData.mUpdateDateRunnable);
                    FirstUseDateData firstUseDateData2 = this.this$0;
                    firstUseDateData2.mHandler.post(firstUseDateData2.mUpdateDateRunnable);
                    break;
            }
        }
    }

    public FirstUseDateData(int i, int i2, boolean[] zArr, Context context, Looper looper) {
        boolean[] zArr2;
        String str;
        ArrayList arrayList = new ArrayList();
        this.authFaiExpiredPaths = arrayList;
        this.mUpdateDateRunnable = new AnonymousClass1(0, this);
        this.mBatteryType = i;
        this.mBatteryCount = i2;
        this.mAuthentificationResults = zArr;
        this.mContext = context;
        this.mHandler = new Handler(looper);
        this.mCurrentValues = new String[this.mBatteryCount];
        int i3 = this.mBatteryType;
        if (i3 == 0) {
            this.efsPaths.add("/efs/FactoryApp/batt_beginning_date");
            this.efsPaths.add("/efs/FactoryApp/batt_beginning_date_2nd");
        } else if (i3 == 1) {
            this.efsPaths.add("/efs/FactoryApp/batt_beginning_date");
            this.authPaths.add("/sys/class/power_supply/sec_auth/first_use_date");
            arrayList.add("/sys/class/power_supply/sec_auth/fai_expired");
            this.mShouldCheckFaiExpireds = new boolean[this.mBatteryCount];
        } else if (i3 == 3) {
            this.efsPaths.add("/efs/FactoryApp/batt_beginning_date");
            this.efsPaths.add("/efs/FactoryApp/batt_beginning_date_2nd");
            this.authPaths.add("/sys/class/power_supply/sec_auth/first_use_date");
            this.authPaths.add("/sys/class/power_supply/sec_auth_2nd/first_use_date");
            arrayList.add("/sys/class/power_supply/sec_auth/fai_expired");
            arrayList.add("/sys/class/power_supply/sec_auth_2nd/fai_expired");
            this.mShouldCheckFaiExpireds = new boolean[this.mBatteryCount];
        } else if (i3 == 2) {
            this.efsPaths.add("/efs/FactoryApp/batt_beginning_date");
            this.authPaths.add("/sys/class/power_supply/sbp-fg/first_use_date");
        } else if (i3 == 4) {
            this.efsPaths.add("/efs/FactoryApp/batt_beginning_date");
            this.efsPaths.add("/efs/FactoryApp/batt_beginning_date_2nd");
            this.authPaths.add("/sys/class/power_supply/sbp-fg/first_use_date");
            this.authPaths.add("/sys/class/power_supply/sbp-fg-2/first_use_date");
        }
        if (this.mBatteryType != 0) {
            for (int i4 = 0; i4 < this.mBatteryCount; i4++) {
                boolean z = this.mAuthentificationResults[i4];
                String str2 = this.TAG;
                if (z) {
                    String readNode = BattUtils.readNode((String) this.authPaths.get(i4), false);
                    Slog.d(
                            str2,
                            "[syncAuthAndEfs]sync efs FirstUseDate with auth:"
                                    + readNode
                                    + " ,result:"
                                    + BattUtils.writeNode(
                                            (String) this.efsPaths.get(i4), readNode));
                } else {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            i4, "[syncAuthAndEfs]Authentification false => skip_", str2);
                }
            }
        }
        setPermissionsEfs(1000);
        Object[] readEfsValues = readEfsValues();
        this.mCurrentValues = readEfsValues;
        boolean allMatch =
                Arrays.stream((String[]) readEfsValues)
                        .allMatch(new FirstUseDateData$$ExternalSyntheticLambda0(this, 0));
        String str3 = this.TAG;
        if (allMatch) {
            Slog.i(str3, "[activateFirstUseDateCheckIfRequired]Already all of FirstUseDate Exist");
        } else {
            IntentFilter m =
                    GmsAlarmManager$$ExternalSyntheticOutline0.m(
                            "android.intent.action.TIME_SET",
                            Constants.INTENT_SECSETUPWIZARD_COMPLETE,
                            Constants.INTENT_SETUPWIZARD_COMPLETE);
            DateChangedReceiver dateChangedReceiver = new DateChangedReceiver(this, 1);
            this.mWriteFirstUseDateReceiver = dateChangedReceiver;
            this.mContext.registerReceiver(dateChangedReceiver, m, null, null, 2);
            Slog.i(
                    str3,
                    "[activateFirstUseDateCheckIfRequired]writeFirstUseDateReceiver Registered For"
                        + " FirstUseDate");
            this.mHandler.postDelayed(this.mUpdateDateRunnable, 45000L);
        }
        int i5 = this.mBatteryType;
        if (i5 == 1 || i5 == 3) {
            int i6 = 0;
            while (true) {
                int i7 = this.mBatteryCount;
                zArr2 = this.mShouldCheckFaiExpireds;
                str = this.TAG;
                if (i6 >= i7) {
                    break;
                }
                if (this.mAuthentificationResults[i6]) {
                    String readNode2 =
                            BattUtils.readNode((String) this.authFaiExpiredPaths.get(i6), true);
                    Slog.d(
                            str,
                            "[activateFaiExpiredCheckIfRequired]faiExpiredStr:".concat(readNode2));
                    if (!"1".equals(readNode2)) {
                        zArr2[i6] = true;
                        if (!"0".equals(readNode2)) {
                            BattUtils.writeNode((String) this.authFaiExpiredPaths.get(i6), "0");
                        }
                    }
                } else {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            i6,
                            "[activateFaiExpiredCheckIfRequired]Authentification false => skip_",
                            str);
                }
                i6++;
            }
            BatteryLogger.writeToFile(
                    "/data/log/battery_service/battery_service_main_history",
                    "FaiExpiredCheck",
                    "shouldCheckFaiExpireds:" + Arrays.toString(zArr2));
            if (IntStream.range(0, zArr2.length)
                    .anyMatch(new FirstUseDateData$$ExternalSyntheticLambda1(this, 0))) {
                IntentFilter m2 =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                "android.intent.action.DATE_CHANGED");
                DateChangedReceiver dateChangedReceiver2 = new DateChangedReceiver(this, 0);
                this.mDateChangedReceiver = dateChangedReceiver2;
                this.mContext.registerReceiver(dateChangedReceiver2, m2, null, null, 2);
                Slog.i(
                        str,
                        "[activateFaiExpiredCheckIfRequired]DateChangedReceiver Registered For FAI"
                            + " Expired");
            }
        }
        saveInfoHistory();
    }

    public static boolean isValidDate(String str) {
        return str != null && str.length() == 8 && str.startsWith("20");
    }
}
