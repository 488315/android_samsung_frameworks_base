package com.android.systemui.keyguard;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DismissCallbackRegistry {
    public final ArrayList mDismissCallbacks = new ArrayList();
    public final Executor mUiBgExecutor;

    public DismissCallbackRegistry(Executor executor) {
        this.mUiBgExecutor = executor;
    }

    public final void notifyDismissCancelled() {
        ArrayList arrayList = this.mDismissCallbacks;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            DismissCallbackWrapper dismissCallbackWrapper = (DismissCallbackWrapper) arrayList.get(size);
            Objects.requireNonNull(dismissCallbackWrapper);
            this.mUiBgExecutor.execute(new DismissCallbackRegistry$$ExternalSyntheticLambda0(dismissCallbackWrapper, 0));
        }
        arrayList.clear();
    }

    public final void notifyDismissSucceeded() {
        ArrayList arrayList = this.mDismissCallbacks;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            DismissCallbackWrapper dismissCallbackWrapper = (DismissCallbackWrapper) arrayList.get(size);
            Objects.requireNonNull(dismissCallbackWrapper);
            this.mUiBgExecutor.execute(new DismissCallbackRegistry$$ExternalSyntheticLambda0(dismissCallbackWrapper, 1));
        }
        arrayList.clear();
    }
}
