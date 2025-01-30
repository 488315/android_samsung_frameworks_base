package com.android.systemui.screenshot;

import android.app.ActivityTaskManager;
import android.content.Context;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AssistContentRequester {
    public final Executor mCallbackExecutor;
    public final Executor mSystemInteractionExecutor;

    public AssistContentRequester(Context context, Executor executor, Executor executor2) {
        Collections.synchronizedMap(new WeakHashMap());
        ActivityTaskManager.getService();
        context.getApplicationContext().getPackageName();
        this.mCallbackExecutor = executor;
        this.mSystemInteractionExecutor = executor2;
        context.getAttributionTag();
    }
}
