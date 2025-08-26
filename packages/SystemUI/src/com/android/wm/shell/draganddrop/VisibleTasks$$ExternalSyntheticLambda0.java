package com.android.wm.shell.draganddrop;

import android.app.ActivityManager;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class VisibleTasks$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ VisibleTasks f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        VisibleTasks visibleTasks = this.f$0;
        visibleTasks.getClass();
        return ((ActivityManager.RunningTaskInfo) obj).displayId != visibleTasks.mDisplayId;
    }
}
