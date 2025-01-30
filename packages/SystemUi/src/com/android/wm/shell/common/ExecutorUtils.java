package com.android.wm.shell.common;

import android.util.Slog;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ExecutorUtils {
    public static void executeRemoteCall(SplitScreenController splitScreenController, Consumer consumer) {
        if (splitScreenController == null) {
            return;
        }
        ((HandlerExecutor) splitScreenController.mMainExecutor).execute(new ExecutorUtils$$ExternalSyntheticLambda0(consumer, splitScreenController, 2));
    }

    public static void executeRemoteCallWithTaskPermission(RemoteCallable remoteCallable, String str, Consumer consumer, boolean z) {
        if (remoteCallable == null) {
            return;
        }
        remoteCallable.getContext().enforceCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", str);
        if (!z) {
            ((HandlerExecutor) remoteCallable.getRemoteCallExecutor()).execute(new ExecutorUtils$$ExternalSyntheticLambda0(consumer, remoteCallable, 1));
        } else {
            try {
                remoteCallable.getRemoteCallExecutor().executeBlocking(new ExecutorUtils$$ExternalSyntheticLambda0(consumer, remoteCallable, 0));
            } catch (InterruptedException e) {
                Slog.e("ExecutorUtils", "Remote call failed", e);
            }
        }
    }
}
