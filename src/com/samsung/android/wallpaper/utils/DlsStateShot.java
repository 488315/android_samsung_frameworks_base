package com.samsung.android.wallpaper.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/* compiled from: SemWallpaperProperties.java */
/* loaded from: classes6.dex */
class DlsStateShot {
    private static final int STATE_DATA_CLEAR = 32768;
    private static final int STATE_DRESS_ROOM = 1;
    private static final int STATE_FRESH_PACK = 4;
    private static final int STATE_LOCK_STAR = 64;
    private static final int STATE_MAIN_MASK = 2047;
    private static final int STATE_MGS = 32;
    private static final int STATE_REGION_SERVICES = 8;
    private static final int STATE_SGG = 16;
    private static final int STATE_SUB_DRESS_ROOM = 2048;
    private static final int STATE_SUB_FRESH_PACK = 4096;
    private static final int STATE_SUB_LOCK_STAR = 8192;
    private static final int STATE_SUB_MASK = 30720;
    private static final int STATE_SUB_SGG = 16384;
    private static final int STATE_WALLPAPER_SERVICES = 2;
    private static final String TAG = "DlsStateShot";
    private Context mContext;
    private int mDlsStateShot;

    private static boolean hasFlag(int i, int i2) {
        return (i & i2) == i2;
    }

    public DlsStateShot(Context context, int i) {
        this.mContext = context.getApplicationContext();
        this.mDlsStateShot = getDlsState(i);
    }

    public boolean isDlsEnabled(int i) {
        if (WhichChecker.isSystem(i)) {
            return false;
        }
        int i2 = this.mDlsStateShot;
        int i3 = i2 & (-10308);
        Log.d(TAG, "isDlsEnabled: " + i3);
        if ((WhichChecker.isPhone(i) || WhichChecker.isSubDisplay(i)) && hasFlag(i3, 16)) {
            return true;
        }
        return WhichChecker.isPhone(i) ? (i2 & 1980) != 0 : WhichChecker.isSubDisplay(i) && (i2 & 20480) != 0;
    }

    public boolean isSggEnabled(int i) {
        if (WhichChecker.isSystem(i)) {
            return false;
        }
        return (WhichChecker.isPhone(i) || WhichChecker.isSubDisplay(i)) && hasFlag(this.mDlsStateShot, 16);
    }

    public int getStateCode() {
        return this.mDlsStateShot;
    }

    private int getDlsState(int i) {
        int iSemGetIntForUser = Settings.System.semGetIntForUser(this.mContext.getContentResolver(), "dls_state", 2, i);
        Log.d(TAG, "getDlsState: " + iSemGetIntForUser + ", userId=" + i);
        return iSemGetIntForUser;
    }
}
