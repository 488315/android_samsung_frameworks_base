package com.android.systemui.keyguard;

import com.android.internal.policy.IKeyguardDismissCallback;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DismissCallbackRegistry {
    public final ArrayList mDismissCallbacks = new ArrayList();
    public final Executor mUiBgExecutor;

    public DismissCallbackRegistry(Executor executor) {
        this.mUiBgExecutor = executor;
    }

    public final void addCallback(IKeyguardDismissCallback iKeyguardDismissCallback) {
        android.util.Log.d("DismissCallbackRegistry", "Adding callback: " + iKeyguardDismissCallback);
        this.mDismissCallbacks.add(new DismissCallbackWrapper(iKeyguardDismissCallback));
    }

    public final void notifyDismissCancelled() {
        android.util.Log.d("DismissCallbackRegistry", "notifyDismissCancelled(" + this.mDismissCallbacks.size() + ")");
        for (int size = this.mDismissCallbacks.size() + (-1); size >= 0; size--) {
            DismissCallbackWrapper dismissCallbackWrapper = (DismissCallbackWrapper) this.mDismissCallbacks.get(size);
            Executor executor = this.mUiBgExecutor;
            Objects.requireNonNull(dismissCallbackWrapper);
            executor.execute(new DismissCallbackRegistry$$ExternalSyntheticLambda0(dismissCallbackWrapper, 0));
        }
        this.mDismissCallbacks.clear();
    }

    public final void notifyDismissSucceeded() {
        android.util.Log.d("DismissCallbackRegistry", "notifyDismissSucceeded(" + this.mDismissCallbacks.size() + ")");
        for (int size = this.mDismissCallbacks.size() + (-1); size >= 0; size--) {
            DismissCallbackWrapper dismissCallbackWrapper = (DismissCallbackWrapper) this.mDismissCallbacks.get(size);
            Executor executor = this.mUiBgExecutor;
            Objects.requireNonNull(dismissCallbackWrapper);
            executor.execute(new DismissCallbackRegistry$$ExternalSyntheticLambda0(dismissCallbackWrapper, 1));
        }
        this.mDismissCallbacks.clear();
    }
}
