package com.android.systemui.qs;

import android.content.Context;
import android.util.ArraySet;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecLockScreenTileQueryHelper {
    public final Executor mBgExecutor;
    public final Executor mMainExecutor;

    public SecLockScreenTileQueryHelper(Context context, UserTracker userTracker, Executor executor, Executor executor2) {
        new ArrayList();
        new ArraySet();
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
    }
}
