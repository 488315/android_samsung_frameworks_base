package com.android.server.appop;

import java.util.function.BiConsumer;

public final /* synthetic */ class AppOpsUidStateTrackerImpl$$ExternalSyntheticLambda0
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        ((AppOpsUidStateTrackerImpl) obj)
                .updateUidPendingStateIfNeeded(((Integer) obj2).intValue());
    }
}
