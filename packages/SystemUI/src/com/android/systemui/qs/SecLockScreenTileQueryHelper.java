package com.android.systemui.qs;

import android.content.Context;
import android.util.ArraySet;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.concurrent.Executor;

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
