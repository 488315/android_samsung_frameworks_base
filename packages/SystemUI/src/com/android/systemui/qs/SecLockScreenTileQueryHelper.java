package com.android.systemui.qs;

import android.content.Context;
import android.util.ArraySet;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SecLockScreenTileQueryHelper {
    public final Executor mBgExecutor;
    public final Executor mMainExecutor;

    public SecLockScreenTileQueryHelper(Context context, UserTracker userTracker, Executor executor, Executor executor2) {
        new ArrayList();
        new ArraySet();
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
    }
}
