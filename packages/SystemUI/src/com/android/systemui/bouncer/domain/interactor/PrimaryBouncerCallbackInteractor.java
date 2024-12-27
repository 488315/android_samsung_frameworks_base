package com.android.systemui.bouncer.domain.interactor;

import com.android.systemui.util.ListenerSet;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class PrimaryBouncerCallbackInteractor {
    public final ListenerSet resetCallbacks = new ListenerSet();
    public final ArrayList expansionCallbacks = new ArrayList();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface PrimaryBouncerExpansionCallback {
        void onExpansionChanged(float f);

        void onFullyHidden();

        void onStartingToHide();

        void onStartingToShow();

        void onVisibilityChanged(boolean z);
    }

    public final void addBouncerExpansionCallback(PrimaryBouncerExpansionCallback primaryBouncerExpansionCallback) {
        if (this.expansionCallbacks.contains(primaryBouncerExpansionCallback)) {
            return;
        }
        this.expansionCallbacks.add(primaryBouncerExpansionCallback);
    }
}
