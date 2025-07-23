package com.samsung.android.powerSolution;

import android.util.Log;

/* loaded from: classes6.dex */
public class powerSolutionManager {
    private static final String TAG = "powerSolutionManager";
    private IpowerSolution service;

    public powerSolutionManager(IpowerSolution ipowerSolution) {
        if (ipowerSolution == null) {
            Log.d(TAG, "IStartService is null");
        } else {
            Log.d(TAG, "powerSolutionManager ++");
            this.service = ipowerSolution;
        }
    }

    public IpowerSolution getMyService() {
        return this.service;
    }
}
