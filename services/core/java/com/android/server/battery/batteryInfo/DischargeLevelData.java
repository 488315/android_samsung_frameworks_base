package com.android.server.battery.batteryInfo;

import android.util.Slog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.battery.BattUtils;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DischargeLevelData extends BaseCycleData {
    public final void setCycle(FullStatusUsageData fullStatusUsageData) {
        String format;
        long asLong = Arrays.stream((Long[]) this.mCurrentValues).mapToLong(new AsocData$$ExternalSyntheticLambda1()).max().getAsLong();
        String str = "[setCycle]mCurrentValues:" + Arrays.toString(this.mCurrentValues) + "=> maxDischargeLevel:" + asLong;
        String str2 = this.TAG;
        Slog.d(str2, str);
        if (fullStatusUsageData == null) {
            format = String.format("%d", Long.valueOf(asLong / 100));
        } else {
            long asLong2 = Arrays.stream((Long[]) fullStatusUsageData.mCurrentValues).mapToLong(new AsocData$$ExternalSyntheticLambda1()).max().getAsLong();
            Slog.d(str2, "[setCycle]mCurrentValues:" + Arrays.toString(fullStatusUsageData.mCurrentValues) + "=> maxFullStatusUsage:" + asLong2);
            format = String.format("%d %d", Long.valueOf(asLong / 100), Long.valueOf(asLong2 / 720));
        }
        Slog.i(str2, "[setCycle]cycleStr:".concat(format));
        BattUtils.writeNode("/sys/class/power_supply/battery/battery_cycle", format);
    }

    public final void updateEfsFromSBP(long j) {
        String m = DeviceIdleController$$ExternalSyntheticOutline0.m(j, "[updateEfsFromSBP]addtionalValue:");
        String str = this.TAG;
        Slog.d(str, m);
        for (int i = 0; i < this.mBatteryCount; i++) {
            if (this.mAuthentificationResults[i]) {
                ((Long[]) this.mCurrentValues)[i] = Long.valueOf(BattUtils.readNodeAsLong((String) this.authPaths.get(i)) * 100);
            } else {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "[updateEfsFromSBP]Authentification false => use only efs_", str);
                ((Long[]) this.mCurrentValues)[i] = Long.valueOf(BattUtils.readNodeAsLong((String) this.efsPaths.get(i)) + j);
            }
            BattUtils.writeNode(((Long[]) this.mCurrentValues)[i].longValue(), (String) this.efsPaths.get(i));
        }
        Slog.d(str, "[updateEfsFromSBP]mCurrentValues:" + Arrays.toString(this.mCurrentValues));
    }
}
