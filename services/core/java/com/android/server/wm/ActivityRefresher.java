package com.android.server.wm;

import android.content.res.Configuration;
import android.os.Handler;

import java.util.ArrayList;

public final class ActivityRefresher {
    public final ArrayList mEvaluators = new ArrayList();
    public final Handler mHandler;
    public final WindowManagerService mWmService;

    public interface Evaluator {
        boolean shouldRefreshActivity(
                ActivityRecord activityRecord,
                Configuration configuration,
                Configuration configuration2);
    }

    public ActivityRefresher(WindowManagerService windowManagerService, Handler handler) {
        this.mWmService = windowManagerService;
        this.mHandler = handler;
    }
}
