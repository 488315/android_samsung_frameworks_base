package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class SecLockscreenTileHost {
    public final Executor bgExecutor;
    public final Executor mainExecutor;
    public final Resources resources;
    public final SecQSTileInstanceManager tileInstanceManager;
    public final UserTracker userTracker;

    public SecLockscreenTileHost(Context context, UserTracker userTracker, Resources resources, Executor executor, Executor executor2, SecQSTileInstanceManager secQSTileInstanceManager) {
        this.resources = resources;
        this.mainExecutor = executor;
        this.bgExecutor = executor2;
        this.tileInstanceManager = secQSTileInstanceManager;
        new ArrayList();
        new SecLockScreenTileQueryHelper(context, userTracker, executor, executor2);
        new Object(this) { // from class: com.android.systemui.qs.SecLockscreenTileHost.1
        };
    }
}
