package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.util.ListenerSet;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PrimaryBouncerCallbackInteractor {
    public final ListenerSet resetCallbacks = new ListenerSet();
    public final ArrayList expansionCallbacks = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface PrimaryBouncerExpansionCallback {
        void onExpansionChanged(float f);

        void onFullyHidden();

        void onStartingToHide();

        void onStartingToShow();

        void onVisibilityChanged(boolean z);
    }
}
