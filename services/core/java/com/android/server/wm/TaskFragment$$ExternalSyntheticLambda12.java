package com.android.server.wm;

import com.android.internal.util.ToBooleanFunction;

public final /* synthetic */ class TaskFragment$$ExternalSyntheticLambda12
        implements ToBooleanFunction {
    public final boolean apply(Object obj) {
        ActivityRecord activityRecord;
        WindowState windowState = (WindowState) obj;
        return (windowState.mAttrs.flags & 2) != 0
                && (activityRecord = windowState.mActivityRecord) != null
                && activityRecord.isEmbedded()
                && (windowState.mActivityRecord.isVisibleRequested()
                        || windowState.mActivityRecord.mVisible);
    }
}
