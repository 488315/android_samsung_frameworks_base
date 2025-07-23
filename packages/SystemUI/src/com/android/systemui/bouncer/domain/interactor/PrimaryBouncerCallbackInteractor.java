package com.android.systemui.bouncer.domain.interactor;

import com.android.systemui.util.ListenerSet;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PrimaryBouncerCallbackInteractor {
    public final ListenerSet resetCallbacks = new ListenerSet();
    public final ArrayList expansionCallbacks = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
