package com.android.server.wm;

import com.android.internal.util.ToBooleanFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class TaskFragment$$ExternalSyntheticLambda12 implements ToBooleanFunction {
    public final boolean apply(Object obj) {
        ActivityRecord activityRecord;
        WindowState windowState = (WindowState) obj;
        return (windowState.mAttrs.flags & 2) != 0 && (activityRecord = windowState.mActivityRecord) != null && activityRecord.isEmbedded() && (windowState.mActivityRecord.isVisibleRequested() || windowState.mActivityRecord.mVisible);
    }
}
