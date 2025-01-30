package com.android.server.am.mars.database;

import android.net.INetd;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.am.mars.util.UidStateMgr;

/* loaded from: classes.dex */
public class MARsComponentTracker {
    public boolean isEnabledCT;

    public abstract class MARsComponentTrackerHolder {
        public static final MARsComponentTracker INSTANCE = new MARsComponentTracker();
    }

    public MARsComponentTracker() {
        this.isEnabledCT = false;
    }

    public static MARsComponentTracker getInstance() {
        return MARsComponentTrackerHolder.INSTANCE;
    }

    public boolean getEnabled() {
        return this.isEnabledCT;
    }

    public void setEnabled(boolean z) {
        this.isEnabledCT = z;
    }

    public void trackComponent(boolean z, String str, String str2, String str3, int i, int i2, boolean z2) {
        if ("activity".equals(str)) {
            return;
        }
        if ((i == 1 && z2) || str3.equals(str2) || UidStateMgr.getInstance().isUidRunning(i2)) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (z) {
            if (i == 0) {
                MARsDBManager.getInstance().sendUpdateAppStartUpInfoMsgToDBHandler(str3, str2, z, currentTimeMillis);
                return;
            }
            return;
        }
        if (i != 0) {
            MARsDBManager.getInstance().sendUpdateAppStartUpInfoMsgToDBHandler(str3, str2, z, currentTimeMillis);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("markAppStartUpResult: auto run ");
        sb.append(i == 0 ? "off" : "on");
        sb.append(" case : ");
        sb.append(str2);
        sb.append(" start process ");
        sb.append(str3);
        sb.append(" at ");
        sb.append(currentTimeMillis);
        Slog.d("MARsComponentTracker", sb.toString());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int getHostingType(String str) {
        boolean z;
        str.hashCode();
        switch (str.hashCode()) {
            case -1655966961:
                if (str.equals("activity")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case -1618876223:
                if (str.equals(INetd.IF_FLAG_BROADCAST)) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case -1396673086:
                if (str.equals("backup")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            case -987494927:
                if (str.equals("provider")) {
                    z = 3;
                    break;
                }
                z = -1;
                break;
            case 105405:
                if (str.equals("job")) {
                    z = 4;
                    break;
                }
                z = -1;
                break;
            case 92895825:
                if (str.equals("alarm")) {
                    z = 5;
                    break;
                }
                z = -1;
                break;
            case 1418030008:
                if (str.equals("bindService")) {
                    z = 6;
                    break;
                }
                z = -1;
                break;
            case 1849706483:
                if (str.equals("startService")) {
                    z = 7;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                return 0;
            case true:
                return 4;
            case true:
                return 5;
            case true:
                return 3;
            case true:
                return 7;
            case true:
                return 6;
            case true:
                return 2;
            case true:
                return 1;
            default:
                return -1;
        }
    }

    public final boolean isSystem(String str) {
        return str == null || "android".equals(str);
    }

    public void sendCTInfo(int i, int i2, int i3, String str, String str2, String str3, String str4, long j, String str5) {
        String str6;
        String str7;
        if (isSystem(str2)) {
            str7 = str;
            str6 = "system";
        } else {
            str6 = str2;
            str7 = str;
        }
        FrameworkStatsLog.write(FrameworkStatsLog.COMPONENT_TRACKER_REPORTED, i2, isSystem(str7) ? "system" : str7, i3, str6, getHostingType(str3), str4, str5);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int getBottleneckReason(String str) {
        boolean z;
        str.hashCode();
        switch (str.hashCode()) {
            case -2130536998:
                if (str.equals("INPUTD")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 2269492:
                if (str.equals("JANK")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 1661058378:
                if (str.equals("MAINLOOPER")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            case 2004112474:
                if (str.equals("LOCKCONTENTION")) {
                    z = 3;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                return 2;
            case true:
                return 3;
            case true:
                return 0;
            case true:
                return 1;
            default:
                return -1;
        }
    }

    public void sendBHInfo(int i, String str, String str2, int i2) {
        FrameworkStatsLog.write(FrameworkStatsLog.BOTTLENECK_HINT_REPORTED, i, getBottleneckReason(str), i2, str2);
    }
}
