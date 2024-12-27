package com.android.server;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManagerInternal;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.battery.BattUtils;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BatteryService$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BatteryService$$ExternalSyntheticLambda6(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        ArrayList<? extends Parcelable> arrayList;
        long j;
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 0:
                BatteryService batteryService = (BatteryService) obj;
                synchronized (batteryService.mLock) {
                    i = batteryService.mLastChargingPolicy;
                }
                Iterator it = batteryService.mChargingPolicyChangeListeners.iterator();
                while (it.hasNext()) {
                    ((BatteryManagerInternal.ChargingPolicyChangeListener) it.next())
                            .onChargingPolicyChanged(i);
                }
                return;
            case 1:
                BatteryService batteryService2 = (BatteryService) obj;
                synchronized (batteryService2.mLock) {
                    arrayList = new ArrayList<>(batteryService2.mBatteryLevelsEventQueue);
                    batteryService2.mBatteryLevelsEventQueue.clear();
                }
                Intent intent = new Intent("android.intent.action.BATTERY_LEVEL_CHANGED");
                intent.addFlags(16777216);
                intent.putParcelableArrayListExtra("android.os.extra.EVENTS", arrayList);
                batteryService2.mContext.sendBroadcastAsUser(
                        intent, UserHandle.ALL, "android.permission.BATTERY_STATS");
                batteryService2.mLastBatteryLevelChangedSentMs = SystemClock.elapsedRealtime();
                return;
            default:
                Context context = ((BatteryService.AnonymousClass2) obj).this$0.mContext;
                long currentNetworkTimeMillis = BattUtils.getCurrentNetworkTimeMillis();
                if (currentNetworkTimeMillis < 1577804400000L) {
                    Slog.e("[SS]BattFunctions", "[processLongestPowerOffDuration]wrong bootTime");
                    return;
                }
                try {
                    j =
                            context.getSharedPreferences("battery_service_prefs", 0)
                                    .getLong("shutdown_time", -1L);
                    Slog.d(
                            "[SS]BattUtils",
                            "[loadSharedPreferencesAsLong]preferenceName:battery_service_prefs"
                                + " ,key:shutdown_time ,value:"
                                    + j);
                } catch (Exception e) {
                    Slog.e("[SS]BattUtils", "[loadSharedPreferencesAsLong]Exception");
                    e.printStackTrace();
                    j = -1;
                }
                BatteryService$$ExternalSyntheticOutline0.m(
                        BatteryService$$ExternalSyntheticOutline0.m(
                                "[processLongestPowerOffDuration]bootTime:",
                                currentNetworkTimeMillis,
                                " ,shutdownTime:"),
                        j,
                        "[SS]BattFunctions");
                if (j == -1) {
                    Slog.w(
                            "[SS]BattFunctions",
                            "[processLongestPowerOffDuration]Not Exist saved shutdownTime");
                    return;
                }
                if (currentNetworkTimeMillis < j) {
                    Slog.e(
                            "[SS]BattFunctions",
                            "[processLongestPowerOffDuration]boot time is later than shutdontime");
                    return;
                }
                long j2 = (currentNetworkTimeMillis - j) / 60000;
                long readNodeAsLong =
                        BattUtils.readNodeAsLong("/efs/FactoryApp/longest_power_off_duration");
                BatteryService$$ExternalSyntheticOutline0.m(
                        BatteryService$$ExternalSyntheticOutline0.m(
                                "[processLongestPowerOffDuration]currentPowerOffDuration:",
                                j2,
                                " ,longestPowerOffDuration:"),
                        readNodeAsLong,
                        "[SS]BattFunctions");
                if (readNodeAsLong < j2) {
                    BattUtils.writeNode(j2, "/efs/FactoryApp/longest_power_off_duration");
                    Slog.i(
                            "[SS]BattFunctions",
                            "[processLongestPowerOffDuration]longestPowerOffDuration updated");
                }
                BattUtils.saveSharedPreferencesAsLong(context, -1L);
                return;
        }
    }
}
