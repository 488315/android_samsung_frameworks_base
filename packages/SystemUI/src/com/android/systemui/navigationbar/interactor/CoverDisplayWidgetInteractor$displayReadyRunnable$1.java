package com.android.systemui.navigationbar.interactor;

import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;

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
                ((NavigationBarControllerImpl) navigationBarController).mCommandQueueCallbacks.onDisplayAddSystemDecorations(1);
            } else {
                ((NavigationBarControllerImpl) navigationBarController).mCommandQueueCallbacks.onDisplayRemoveSystemDecorations(1);
            }
        }
    }
}
