package com.android.systemui.statusbar.core;

import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import java.util.Set;
import javax.inject.Provider;

public final class StatusBarInitializer {
    public final Provider collapsedStatusBarFragmentProvider;
    public final Set creationListeners;
    public CentralSurfacesImpl$$ExternalSyntheticLambda0 statusBarViewUpdatedListener;
    public final StatusBarWindowController windowController;

    public interface OnStatusBarViewInitializedListener {
        void onStatusBarViewInitialized(StatusBarFragmentComponent statusBarFragmentComponent);
    }

    public StatusBarInitializer(StatusBarWindowController statusBarWindowController, Provider provider, Set<OnStatusBarViewInitializedListener> set) {
        this.windowController = statusBarWindowController;
        this.collapsedStatusBarFragmentProvider = provider;
        this.creationListeners = set;
    }
}
