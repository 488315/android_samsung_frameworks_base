package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ActivityTaskManagerLabelLoader implements RecentTaskLabelLoader {
    public final String TAG = "RecentTaskLabelLoader";
    public final CoroutineDispatcher coroutineDispatcher;
    public final PackageManager packageManager;

    public ActivityTaskManagerLabelLoader(CoroutineDispatcher coroutineDispatcher, PackageManager packageManager) {
        this.coroutineDispatcher = coroutineDispatcher;
        this.packageManager = packageManager;
    }

    public final Object loadLabel(int i, ComponentName componentName, Continuation continuation) {
        return BuildersKt.withContext(this.coroutineDispatcher, new ActivityTaskManagerLabelLoader$loadLabel$2(this, componentName, i, null), continuation);
    }
}
