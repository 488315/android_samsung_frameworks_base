package com.android.server.wm;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;

import com.samsung.android.core.SystemHistory;

import java.util.ArrayList;

public final class RefreshRatePolicyLogger {
    public final ArrayList mRefreshRateHistories = new ArrayList();

    public final class RefreshRateHistory {
        public SystemHistory mHistory;
        public WindowState mLastRequester;
        public int mModeId;
        public float mRefreshRate;
        public String mTag;
    }

    public RefreshRatePolicyLogger() {
        int i = 0;
        while (i < 3) {
            ArrayList arrayList = this.mRefreshRateHistories;
            String m =
                    i != 0
                            ? i != 1
                                    ? i != 2
                                            ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                    i, "Unknown_")
                                            : "Max"
                                    : "Min"
                            : "ModeId";
            RefreshRateHistory refreshRateHistory = new RefreshRateHistory();
            refreshRateHistory.mModeId = -1;
            refreshRateHistory.mRefreshRate = -1.0f;
            refreshRateHistory.mTag = m;
            SystemHistory systemHistory = new SystemHistory(15, m);
            refreshRateHistory.mHistory = systemHistory;
            systemHistory.enableLog(false);
            arrayList.add(refreshRateHistory);
            i++;
        }
    }
}
