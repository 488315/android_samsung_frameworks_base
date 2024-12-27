package com.android.systemui.bouncer.domain.interactor;

import com.android.systemui.util.ListenerSet;
import java.util.ArrayList;

public final class PrimaryBouncerCallbackInteractor {
    public final ListenerSet resetCallbacks = new ListenerSet();
    public final ArrayList expansionCallbacks = new ArrayList();

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
