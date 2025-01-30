package com.android.systemui.util;

import android.app.IActivityTaskManager;
import android.content.Context;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AsyncActivityLauncher {
    public final IActivityTaskManager activityTaskManager;
    public final Executor backgroundExecutor;
    public final Context context;
    public final Executor mainExecutor;
    public Function1 pendingCallback;

    public AsyncActivityLauncher(Context context, IActivityTaskManager iActivityTaskManager, Executor executor, Executor executor2) {
        this.context = context;
        this.activityTaskManager = iActivityTaskManager;
        this.backgroundExecutor = executor;
        this.mainExecutor = executor2;
    }
}
