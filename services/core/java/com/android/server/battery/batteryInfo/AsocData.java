package com.android.server.battery.batteryInfo;

import android.util.Slog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.battery.BattUtils;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AsocData extends BaseData {
    public final void updateAndSet() {
        long readNodeAsLong$1 = BattUtils.readNodeAsLong$1("/sys/class/power_supply/battery/fg_asoc");
        String m = DeviceIdleController$$ExternalSyntheticOutline0.m(readNodeAsLong$1, "[updateAndSet]asocFromDriver:");
        String str = this.TAG;
        Slog.d(str, m);
        for (int i = 0; i < this.mBatteryCount; i++) {
            ((Long[]) this.mCurrentValues)[i] = Long.valueOf(BattUtils.readNodeAsLong$1((String) this.efsPaths.get(i)));
            Slog.d(str, "[updateAndSet]currentValue:" + ((Long[]) this.mCurrentValues)[i]);
            if (((Long[]) this.mCurrentValues)[i].longValue() < 0) {
                if (readNodeAsLong$1 < 0) {
                    ((Long[]) this.mCurrentValues)[i] = -1L;
                } else {
                    ((Long[]) this.mCurrentValues)[i] = 100L;
                }
                BattUtils.writeNode(((Long[]) this.mCurrentValues)[i].longValue(), (String) this.efsPaths.get(i));
                if (!this.authPaths.isEmpty()) {
                    if (this.mAuthentificationResults[i]) {
                        BattUtils.writeNode(((Long[]) this.mCurrentValues)[i].longValue(), (String) this.authPaths.get(i));
                    } else {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(i, "[updateAndSet]Authentification false => skip writing asoc_", str);
                    }
                }
            }
            if (readNodeAsLong$1 >= 0 && readNodeAsLong$1 < ((Long[]) this.mCurrentValues)[i].longValue() && ((Long[]) this.mCurrentValues)[i].longValue() - readNodeAsLong$1 < 10) {
                Long[] lArr = (Long[]) this.mCurrentValues;
                lArr[i] = Long.valueOf(lArr[i].longValue() - 1);
                Slog.i(str, "[updateAndSet]updated asocValue:" + ((Long[]) this.mCurrentValues)[i]);
                BattUtils.writeNode(((Long[]) this.mCurrentValues)[i].longValue(), (String) this.efsPaths.get(i));
                if (!this.authPaths.isEmpty()) {
                    if (this.mAuthentificationResults[i]) {
                        BattUtils.writeNode(((Long[]) this.mCurrentValues)[i].longValue(), (String) this.authPaths.get(i));
                    } else {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(i, "[updateAndSet]Authentification false => skip writing asoc_", str);
                    }
                }
            }
        }
        long asLong = Arrays.stream((Long[]) this.mCurrentValues).mapToLong(new AsocData$$ExternalSyntheticLambda1()).min().getAsLong();
        Slog.d(str, "[updateAndSet]mCurrentValues:" + Arrays.toString(this.mCurrentValues) + "=> minAsoc:" + asLong);
        BattUtils.writeNode(asLong, "/sys/class/power_supply/battery/fg_asoc");
        StringBuilder sb = new StringBuilder("[loggingHistoryInEfs]mCurrentValues[0]:");
        sb.append(((Long[]) this.mCurrentValues)[0]);
        Slog.v(str, sb.toString());
    }
}
