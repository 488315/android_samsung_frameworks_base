package com.android.systemui.util;

import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.app.WaitResult;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final class AsyncActivityLauncher {
    public static final int $stable = 8;
    private final IActivityTaskManager activityTaskManager;
    private final Executor backgroundExecutor;
    private final Context context;
    private final Executor mainExecutor;
    private Function1 pendingCallback;

    public AsyncActivityLauncher(Context context, IActivityTaskManager iActivityTaskManager, Executor executor, Executor executor2) {
        this.context = context;
        this.activityTaskManager = iActivityTaskManager;
        this.backgroundExecutor = executor;
        this.mainExecutor = executor2;
    }

    public static /* synthetic */ boolean startActivityAsUser$default(AsyncActivityLauncher asyncActivityLauncher, Intent intent, UserHandle userHandle, Bundle bundle, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            bundle = null;
        }
        return asyncActivityLauncher.startActivityAsUser(intent, userHandle, bundle, function1);
    }

    public final void destroy() {
        this.pendingCallback = null;
    }

    public final boolean startActivityAsUser(final Intent intent, final UserHandle userHandle, final Bundle bundle, Function1 function1) {
        if (this.pendingCallback != null) {
            return false;
        }
        this.pendingCallback = function1;
        intent.setFlags(intent.getFlags() | 268435456);
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.util.AsyncActivityLauncher.startActivityAsUser.1
            @Override // java.lang.Runnable
            public final void run() {
                final WaitResult waitResultStartActivityAndWait = AsyncActivityLauncher.this.activityTaskManager.startActivityAndWait((IApplicationThread) null, AsyncActivityLauncher.this.context.getPackageName(), AsyncActivityLauncher.this.context.getAttributionTag(), intent, (String) null, (IBinder) null, (String) null, 0, 0, (ProfilerInfo) null, bundle, userHandle.getIdentifier());
                Executor executor = AsyncActivityLauncher.this.mainExecutor;
                final AsyncActivityLauncher asyncActivityLauncher = AsyncActivityLauncher.this;
                executor.execute(new Runnable() { // from class: com.android.systemui.util.AsyncActivityLauncher.startActivityAsUser.1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Function1 function12 = asyncActivityLauncher.pendingCallback;
                        if (function12 != null) {
                            WaitResult waitResult = waitResultStartActivityAndWait;
                            waitResult.getClass();
                            function12.mo781invoke(waitResult);
                        }
                    }
                });
            }
        });
        return true;
    }
}
