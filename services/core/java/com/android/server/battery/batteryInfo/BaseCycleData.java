package com.android.server.battery.batteryInfo;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.util.Slog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.battery.BattUtils;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BaseCycleData extends BaseData {
    public final void addAndSave(long j) {
        String m = DeviceIdleController$$ExternalSyntheticOutline0.m(j, "[addAndSave]addtionalValue:");
        String str = this.TAG;
        Slog.d(str, m);
        for (int i = 0; i < this.mBatteryCount; i++) {
            ((Long[]) this.mCurrentValues)[i] = Long.valueOf(BattUtils.readNodeAsLong((String) this.efsPaths.get(i)) + j);
            BattUtils.writeNode(((Long[]) this.mCurrentValues)[i].longValue(), (String) this.efsPaths.get(i));
            if (!this.authPaths.isEmpty()) {
                if (this.mAuthentificationResults[i]) {
                    BattUtils.writeNode(((Long[]) this.mCurrentValues)[i].longValue(), (String) this.authPaths.get(i));
                } else {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(i, "[addAndSave]Authentification false => skip writing_", str);
                }
            }
        }
        Slog.d(str, "[addAndSave]mCurrentValues:" + Arrays.toString(this.mCurrentValues));
    }

    public final void initializeIfNotExist() {
        for (int i = 0; i < this.mBatteryCount; i++) {
            if (!BattUtils.isExist((String) this.efsPaths.get(i))) {
                Slog.w(this.TAG, AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder("[initializeIfNotExist]create:"), (String) this.efsPaths.get(i), " ,initValue:1"));
                BattUtils.writeNode(1L, (String) this.efsPaths.get(i));
            }
        }
    }

    public final void syncAuthAndEfsForAuth$1(boolean[] zArr) {
        for (int i = 0; i < this.mBatteryCount; i++) {
            boolean z = this.mAuthentificationResults[i];
            String str = this.TAG;
            if (z) {
                long j = 1;
                if (zArr[i]) {
                    long readNodeAsLong$1 = BattUtils.readNodeAsLong$1((String) this.efsPaths.get(i));
                    long readNodeAsLong$12 = BattUtils.readNodeAsLong$1((String) this.authPaths.get(i));
                    long j2 = (readNodeAsLong$12 == 16777215 || readNodeAsLong$12 <= readNodeAsLong$1) ? readNodeAsLong$1 : readNodeAsLong$12;
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("[syncAuthAndEfsForAuth]efsValue:", readNodeAsLong$1, " ,authValue:");
                    m.append(readNodeAsLong$12);
                    m.append(" =>worseValue:");
                    m.append(j2);
                    Slog.d(str, m.toString());
                    if (j2 == -1) {
                        Slog.w(str, "[syncAuthAndEfsForAuth]init worse value: 1");
                    } else {
                        j = j2;
                    }
                    BattUtils.writeNode(j, (String) this.efsPaths.get(i));
                    BattUtils.writeNode(j, (String) this.authPaths.get(i));
                } else {
                    long readNodeAsLong = BattUtils.readNodeAsLong((String) this.authPaths.get(i));
                    if (readNodeAsLong == 16777215 || readNodeAsLong < 0) {
                        Slog.w(str, "[syncAuthAndEfsForAuth]init authValue:1");
                        BattUtils.writeNode(1L, (String) this.authPaths.get(i));
                    } else {
                        j = readNodeAsLong;
                    }
                    Slog.d(str, "[syncAuthAndEfsForAuth]sync efs value with auth:" + j);
                    BattUtils.writeNode(j, (String) this.efsPaths.get(i));
                }
            } else {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "[syncAuthAndEfsForAuth]Authentification false => skip_", str);
            }
        }
    }
}
