package com.android.systemui.navigationbar.interactor;

import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;

public final class CoverDisplayWidgetInteractor$displayReadyRunnable$1 implements Runnable {
    public final /* synthetic */ CoverDisplayWidgetInteractor this$0;

    public CoverDisplayWidgetInteractor$displayReadyRunnable$1(CoverDisplayWidgetInteractor coverDisplayWidgetInteractor) {
        this.this$0 = coverDisplayWidgetInteractor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NavigationBarController navigationBarController = (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
        if (navigationBarController != null) {
            if (this.this$0.isEnabled()) {
                ((NavigationBarControllerImpl) navigationBarController).mCommandQueueCallbacks.onDisplayReady(1);
                return;
            }
            NavigationBarControllerImpl navigationBarControllerImpl = (NavigationBarControllerImpl) navigationBarController;
            navigationBarControllerImpl.removeNavigationBar(1);
            navigationBarControllerImpl.mCommandQueueCallbacks.onDisplayRemoved(1);
        }
    }
}
