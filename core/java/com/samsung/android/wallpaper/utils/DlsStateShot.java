package com.samsung.android.wallpaper.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/* compiled from: SemWallpaperProperties.java */
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
    private static final String TAG = DlsStateShot.class.getSimpleName();
    private Context mContext;
    private int mDlsStateShot;

    public DlsStateShot(Context context, int userId) {
        this.mContext = context.getApplicationContext();
        this.mDlsStateShot = getDlsState(userId);
    }

    public boolean isDlsEnabled(int which) {
        if (WhichChecker.isSystem(which)) {
            return false;
        }
        int dlsState = this.mDlsStateShot & (-10308);
        Log.d(TAG, "isDlsEnabled: " + dlsState);
        boolean isPhoneOrSub = WhichChecker.isPhone(which) || WhichChecker.isSubDisplay(which);
        if (isPhoneOrSub && hasFlag(dlsState, 16)) {
            return true;
        }
        return WhichChecker.isPhone(which)
                ? (dlsState & 2047) != 0
                : WhichChecker.isSubDisplay(which) && (dlsState & STATE_SUB_MASK) != 0;
    }

    public boolean isSggEnabled(int which) {
        if (WhichChecker.isSystem(which)) {
            return false;
        }
        boolean isPhoneOrSub = WhichChecker.isPhone(which) || WhichChecker.isSubDisplay(which);
        return isPhoneOrSub && hasFlag(this.mDlsStateShot, 16);
    }

    public int getStateCode() {
        return this.mDlsStateShot;
    }

    private static boolean hasFlag(int valueToTest, int flag) {
        return (valueToTest & flag) == flag;
    }

    private int getDlsState(int userId) {
        int dlsState =
                Settings.System.semGetIntForUser(
                        this.mContext.getContentResolver(), "dls_state", 2, userId);
        Log.d(TAG, "getDlsState: " + dlsState + ", userId=" + userId);
        return dlsState;
    }
}
