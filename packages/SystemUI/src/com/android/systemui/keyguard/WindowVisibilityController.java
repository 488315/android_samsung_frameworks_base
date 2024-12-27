package com.android.systemui.keyguard;

import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.ViewRootImpl;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelper;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

public final class WindowVisibilityController implements VisibilityController {
    public final Choreographer choreographer;
    public final Lazy shadeWindowControllerHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.WindowVisibilityController$shadeWindowControllerHelper$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) WindowVisibilityController.this.shadeWindowControllerLazy.get())).mHelper;
        }
    });
    public final dagger.Lazy shadeWindowControllerLazy;

    public WindowVisibilityController(Choreographer choreographer, dagger.Lazy lazy) {
        this.choreographer = choreographer;
        this.shadeWindowControllerLazy = lazy;
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final void invalidate() {
        ViewRootImpl viewRootImpl;
        WindowRootView windowRootView = ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) this.shadeWindowControllerLazy.get())).mWindowRootView;
        if (windowRootView == null || (viewRootImpl = windowRootView.getViewRootImpl()) == null) {
            return;
        }
        viewRootImpl.setReportNextDraw(false, "BioUnlock");
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final boolean needToBeInvisibleWindow() {
        return true;
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final void registerFrameUpdateCallback(final Function0 function0) {
        this.choreographer.postCallbackDelayed(3, new Runnable() { // from class: com.android.systemui.keyguard.WindowVisibilityController$registerFrameUpdateCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                Function0.this.invoke();
            }
        }, null, 0L);
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final void resetForceInvisible(boolean z) {
        ((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) this.shadeWindowControllerHelper$delegate.getValue())).resetForceInvisible(z);
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final boolean setForceInvisible(SurfaceControl.Transaction transaction) {
        Lazy lazy = this.shadeWindowControllerHelper$delegate;
        if (((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) lazy.getValue())).getCurrentState().forceInvisible) {
            return false;
        }
        ((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) lazy.getValue())).setForceInvisible(false);
        return true;
    }
}
