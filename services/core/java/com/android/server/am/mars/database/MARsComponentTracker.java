package com.android.server.am.mars.database;

import android.net.INetd;
import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MARsComponentTracker {
    public boolean isEnabledCT;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class MARsComponentTrackerHolder {
        public static final MARsComponentTracker INSTANCE;

        static {
            MARsComponentTracker mARsComponentTracker = new MARsComponentTracker();
            mARsComponentTracker.isEnabledCT = false;
            INSTANCE = mARsComponentTracker;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void sendCTInfo(String str, String str2, String str3, int i, String str4, int i2, String str5) {
        boolean z;
        int i3;
        String str6 = str2 == null || "android".equals(str2) ? "system" : str2;
        String str7 = (str == null || "android".equals(str)) ? "system" : str;
        str3.getClass();
        switch (str3.hashCode()) {
            case -1655966961:
                if (str3.equals("activity")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case -1618876223:
                if (str3.equals(INetd.IF_FLAG_BROADCAST)) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case -1396673086:
                if (str3.equals("backup")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            case -987494927:
                if (str3.equals("provider")) {
                    z = 3;
                    break;
                }
                z = -1;
                break;
            case 105405:
                if (str3.equals("job")) {
                    z = 4;
                    break;
                }
                z = -1;
                break;
            case 92895825:
                if (str3.equals("alarm")) {
                    z = 5;
                    break;
                }
                z = -1;
                break;
            case 1418030008:
                if (str3.equals("bindService")) {
                    z = 6;
                    break;
                }
                z = -1;
                break;
            case 1849706483:
                if (str3.equals("startService")) {
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
                i3 = 0;
                break;
            case true:
                i3 = 4;
                break;
            case true:
                i3 = 5;
                break;
            case true:
                i3 = 3;
                break;
            case true:
                i3 = 7;
                break;
            case true:
                i3 = 6;
                break;
            case true:
                i3 = 2;
                break;
            case true:
                i3 = 1;
                break;
            default:
                i3 = -1;
                break;
        }
        FrameworkStatsLog.write(FrameworkStatsLog.COMPONENT_TRACKER_REPORTED, i, str7, i2, str6, i3, str4, str5);
    }
}
