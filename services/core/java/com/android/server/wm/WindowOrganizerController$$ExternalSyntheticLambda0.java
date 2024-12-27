package com.android.server.wm;

import java.util.function.Consumer;

public final /* synthetic */ class WindowOrganizerController$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        if (activityRecord.isVisibleRequested()) {
            activityRecord.ensureActivityConfiguration(true);
        }
    }
}
