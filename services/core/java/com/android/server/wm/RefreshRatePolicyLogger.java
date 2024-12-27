package com.android.server.wm;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import com.samsung.android.core.SystemHistory;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RefreshRatePolicyLogger {
    public final ArrayList mRefreshRateHistories = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
            String m = i != 0 ? i != 1 ? i != 2 ? VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown_") : "Max" : "Min" : "ModeId";
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
