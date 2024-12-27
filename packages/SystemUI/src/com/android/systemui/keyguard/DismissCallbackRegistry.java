package com.android.systemui.keyguard;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DismissCallbackRegistry {
    public final ArrayList mDismissCallbacks = new ArrayList();
    public final Executor mUiBgExecutor;

    public DismissCallbackRegistry(Executor executor) {
        this.mUiBgExecutor = executor;
    }

    public final void notifyDismissCancelled() {
        for (int size = this.mDismissCallbacks.size() - 1; size >= 0; size--) {
            DismissCallbackWrapper dismissCallbackWrapper = (DismissCallbackWrapper) this.mDismissCallbacks.get(size);
            Executor executor = this.mUiBgExecutor;
            Objects.requireNonNull(dismissCallbackWrapper);
            executor.execute(new DismissCallbackRegistry$$ExternalSyntheticLambda0(dismissCallbackWrapper, 0));
        }
        this.mDismissCallbacks.clear();
    }

    public final void notifyDismissSucceeded() {
        for (int size = this.mDismissCallbacks.size() - 1; size >= 0; size--) {
            DismissCallbackWrapper dismissCallbackWrapper = (DismissCallbackWrapper) this.mDismissCallbacks.get(size);
            Executor executor = this.mUiBgExecutor;
            Objects.requireNonNull(dismissCallbackWrapper);
            executor.execute(new DismissCallbackRegistry$$ExternalSyntheticLambda0(dismissCallbackWrapper, 1));
        }
        this.mDismissCallbacks.clear();
    }
}
