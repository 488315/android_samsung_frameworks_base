package com.android.server.battery.batteryInfo;

import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.battery.BattUtils;
import com.android.server.battery.BatteryLogger;

import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class QrData extends BaseData {
    public final boolean[] mIsQrEquals;

    public QrData(int i, int i2, boolean[] zArr) {
        this.mBatteryType = i;
        this.mBatteryCount = i2;
        this.mAuthentificationResults = zArr;
        this.mCurrentValues = new String[i2];
        if (i == 0) {
            this.efsPaths.add("/efs/FactoryApp/HwParamBattQR");
        } else if (i == 1) {
            this.efsPaths.add("/efs/FactoryApp/HwParamBattQR");
            this.authPaths.add("/sys/class/power_supply/sec_auth/qr_code");
        } else if (i == 3) {
            this.efsPaths.add("/efs/FactoryApp/HwParamBattQR");
            this.efsPaths.add("/efs/FactoryApp/HwParamBattQR_2nd");
            this.authPaths.add("/sys/class/power_supply/sec_auth/qr_code");
            this.authPaths.add("/sys/class/power_supply/sec_auth_2nd/qr_code");
        } else if (i == 2) {
            this.efsPaths.add("/efs/FactoryApp/HwParamBattQR");
            this.authPaths.add("/sys/class/power_supply/sbp-fg/qr_code");
        } else if (i == 4) {
            this.efsPaths.add("/efs/FactoryApp/HwParamBattQR");
            this.efsPaths.add("/efs/FactoryApp/HwParamBattQR_2nd");
            this.authPaths.add("/sys/class/power_supply/sbp-fg/qr_code");
            this.authPaths.add("/sys/class/power_supply/sbp-fg-2/qr_code");
        }
        if (this.mBatteryType != 0) {
            boolean[] zArr2 = new boolean[this.mBatteryCount];
            for (int i3 = 0; i3 < this.mBatteryCount; i3++) {
                boolean z = this.mAuthentificationResults[i3];
                String str = this.TAG;
                if (z) {
                    String readNode = BattUtils.readNode((String) this.efsPaths.get(i3), false);
                    String readNode2 = BattUtils.readNode((String) this.authPaths.get(i3), false);
                    if (readNode.isEmpty() || !readNode.equals(readNode2)) {
                        zArr2[i3] = false;
                    } else {
                        zArr2[i3] = true;
                    }
                    HeimdAllFsService$$ExternalSyntheticOutline0.m(
                            str,
                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                    "[checkQrEquals]efsQr:",
                                    readNode,
                                    " ,authQr:",
                                    readNode2,
                                    " =>Equal:"),
                            zArr2[i3]);
                } else {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            i3, "[checkQrEquals]Authentification false => skip_", str);
                }
            }
            BatteryLogger.writeToFile(
                    "/data/log/battery_service/battery_service_main_history",
                    "Check QR Equals",
                    "isQrEquals:" + Arrays.toString(zArr2));
            this.mIsQrEquals = zArr2;
            String str2 = this.TAG;
            Slog.d(str2, "[syncAuthAndEfs]");
            for (int i4 = 0; i4 < this.mBatteryCount; i4++) {
                if (!this.mAuthentificationResults[i4]) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            i4, "[syncAuthAndEfs]Authentification false => skip_", str2);
                } else if (!zArr2[i4]) {
                    String readNode3 = BattUtils.readNode((String) this.authPaths.get(i4), false);
                    Slog.d(str2, "[syncAuthAndEfs]sync efs QR with auth");
                    BattUtils.writeNode((String) this.efsPaths.get(i4), readNode3);
                }
            }
        } else {
            this.mIsQrEquals = null;
        }
        setPermissionsEfs(1000);
        this.mCurrentValues = readEfsValues();
        saveInfoHistory();
    }
}
