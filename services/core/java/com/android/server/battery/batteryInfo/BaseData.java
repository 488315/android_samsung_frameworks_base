package com.android.server.battery.batteryInfo;

import android.os.FileUtils;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.battery.BattLogBuffer;
import com.android.server.battery.BattUtils;
import com.android.server.battery.BatteryLogger;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BaseData {
    public boolean[] mAuthentificationResults;
    public int mBatteryCount;
    public int mBatteryType;
    public Object[] mCurrentValues;
    public final ArrayList efsPaths = new ArrayList();
    public final ArrayList authPaths = new ArrayList();
    public final String TAG = "[SS][BattInfo]".concat(getClass().getSimpleName());

    public final Object[] readEfsValues() {
        int i = 0;
        if (this.mCurrentValues instanceof Long[]) {
            Long[] lArr = new Long[this.mBatteryCount];
            while (i < this.mBatteryCount) {
                lArr[i] = Long.valueOf(BattUtils.readNodeAsLong((String) this.efsPaths.get(i)));
                i++;
            }
            return lArr;
        }
        String[] strArr = new String[this.mBatteryCount];
        while (i < this.mBatteryCount) {
            strArr[i] = BattUtils.readNode((String) this.efsPaths.get(i), true);
            i++;
        }
        return strArr;
    }

    public final void saveInfoHistory() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.mBatteryCount; i++) {
            sb.append("efsValue:".concat(BattUtils.readNode((String) this.efsPaths.get(i), false)));
            if (!this.authPaths.isEmpty()) {
                sb.append(" ,authValue:".concat(BattUtils.readNode((String) this.authPaths.get(i), false)));
            }
            sb.append("    ");
        }
        String str = "[saveInfoHistory]" + sb.toString();
        String str2 = this.TAG;
        Slog.d(str2, str);
        BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", str2 + " saveInfoHistory", sb.toString());
        BattLogBuffer.addLog(3, str2 + " " + sb.toString());
    }

    public final void setPermissionsEfs(int i) {
        for (int i2 = 0; i2 < this.mBatteryCount; i2++) {
            boolean isExist = BattUtils.isExist((String) this.efsPaths.get(i2));
            String str = this.TAG;
            if (isExist) {
                try {
                    FileUtils.setPermissions((String) this.efsPaths.get(i2), FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, 1000, i);
                } catch (Exception e) {
                    Slog.e(str, "[setPermissionsEfs]Exception");
                    e.printStackTrace();
                }
            } else {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("[setPermissionsEfs]not exist:"), (String) this.efsPaths.get(i2), str);
            }
        }
    }
}
