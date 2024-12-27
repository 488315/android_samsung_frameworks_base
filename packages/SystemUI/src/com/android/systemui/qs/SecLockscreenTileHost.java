package com.android.systemui.qs;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecLockscreenTileHost {
    public final Executor bgExecutor;
    public final Executor mainExecutor;
    public final QSTileHost qsHost;
    public final String TAG = "LockscreenTileHost";
    public final Object obj = new Object();
    public final List callbacks = new ArrayList();

    public SecLockscreenTileHost(Context context, QSTileHost qSTileHost, UserTracker userTracker, Executor executor, Executor executor2) {
        this.qsHost = qSTileHost;
        this.mainExecutor = executor;
        this.bgExecutor = executor2;
        new SecLockScreenTileQueryHelper(context, userTracker, executor, executor2);
        new Object(this) { // from class: com.android.systemui.qs.SecLockscreenTileHost.1
        };
    }
}
