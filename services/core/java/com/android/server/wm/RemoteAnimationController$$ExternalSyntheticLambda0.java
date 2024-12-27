package com.android.server.wm;

import java.util.function.Consumer;

public final /* synthetic */ class RemoteAnimationController$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        if (activityRecord.mIsInputDroppedForAnimation) {
            activityRecord.mIsInputDroppedForAnimation = false;
            activityRecord.updateUntrustedEmbeddingInputProtection();
        }
    }
}
