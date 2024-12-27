package com.samsung.android.continuity;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import com.samsung.android.continuity.sem.SemWrapper;

public class SemContinuityManager {
    public static final int BUDS_AUTO_SWITCHING = 2;
    public static final int COPY_AND_PASTE = 8;
    public static final int FILTER_BUDS_AUTO_SWTICHING = 2;
    public static final int FILTER_HANDOFF = 1;
    public static final int HAND_OFF = 4;
    public static final int PHYSICAL_KEYBOARD = 1;
    private static final String TAG = "[MCF_DS_SYS]_Manager";
    private final Context mContext;
    private final ISemContinuityManager mService;
    private final int mSupportedFeature = getContinuityFeature();
    private final int mUserId;

    public SemContinuityManager(Context context, ISemContinuityManager service, int userId) {
        this.mContext = context;
        this.mService = service;
        this.mUserId = userId;
    }

    private static int getContinuityFeature() {
        int continuityFeature =
                SemWrapper.getFloatingFeatureInt("SEC_FLOATING_FEATURE_MCF_SUPPORT_CONTINUITY");
        return Math.max(continuityFeature, 0);
    }

    public static boolean isSupported(int feature) {
        return (getContinuityFeature() & feature) == feature;
    }

    public int getNearbyDeviceCount(int filterType) {
        if (this.mSupportedFeature <= 0) {
            return 0;
        }
        try {
            return this.mService.getNearbyDeviceCount(filterType, this.mUserId);
        } catch (RemoteException e) {
            Log.e(TAG, "getNearbyDeviceCount - " + e.getMessage());
            return 0;
        }
    }
}
