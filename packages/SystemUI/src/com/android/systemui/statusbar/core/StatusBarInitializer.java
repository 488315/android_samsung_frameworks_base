package com.android.systemui.statusbar.core;

import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import java.util.Set;
import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class StatusBarInitializer {
    public final Provider collapsedStatusBarFragmentProvider;
    public final Set creationListeners;
    public CentralSurfacesImpl$$ExternalSyntheticLambda0 statusBarViewUpdatedListener;
    public final StatusBarWindowController windowController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface OnStatusBarViewInitializedListener {
        void onStatusBarViewInitialized(StatusBarFragmentComponent statusBarFragmentComponent);
    }

    public StatusBarInitializer(StatusBarWindowController statusBarWindowController, Provider provider, Set<OnStatusBarViewInitializedListener> set) {
        this.windowController = statusBarWindowController;
        this.collapsedStatusBarFragmentProvider = provider;
        this.creationListeners = set;
    }
}
