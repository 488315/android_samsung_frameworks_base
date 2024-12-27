package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class FreeformController$$ExternalSyntheticLambda5
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Task task = (Task) obj;
        return task.isLeafTask() && task.isTopActivityFocusable() && !task.isFreeformForceHidden();
    }
}
