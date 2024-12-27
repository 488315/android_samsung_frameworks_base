package com.android.systemui.navigationbar.interactor;

import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
