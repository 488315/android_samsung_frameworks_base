package com.android.server.wm;

import android.content.res.Configuration;
import android.os.Handler;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ActivityRefresher {
    public final ArrayList mEvaluators = new ArrayList();
    public final Handler mHandler;
    public final WindowManagerService mWmService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Evaluator {
        boolean shouldRefreshActivity(ActivityRecord activityRecord, Configuration configuration, Configuration configuration2);
    }

    public ActivityRefresher(WindowManagerService windowManagerService, Handler handler) {
        this.mWmService = windowManagerService;
        this.mHandler = handler;
    }
}
