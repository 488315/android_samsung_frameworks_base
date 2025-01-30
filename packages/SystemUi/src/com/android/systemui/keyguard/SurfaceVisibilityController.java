package com.android.systemui.keyguard;

import android.graphics.HardwareRenderer;
import android.view.SurfaceControl;
import com.android.keyguard.KeyguardViewController;
import dagger.Lazy;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SurfaceVisibilityController implements VisibilityController {
    public final Lazy keyguardSurfaceControllerLazy;
    public final Lazy keyguardViewControllerLazy;

    public SurfaceVisibilityController(Lazy lazy, Lazy lazy2) {
        this.keyguardViewControllerLazy = lazy;
        this.keyguardSurfaceControllerLazy = lazy2;
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final void invalidate() {
        ((KeyguardViewController) this.keyguardViewControllerLazy.get()).getViewRootImpl().setReportNextDraw(false, "BioUnlock");
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final boolean needToBeInvisibleWindow() {
        return false;
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final void registerFrameUpdateCallback(final Function0 function0) {
        ((KeyguardViewController) this.keyguardViewControllerLazy.get()).getViewRootImpl().registerRtFrameCallback(new HardwareRenderer.FrameDrawingCallback() { // from class: com.android.systemui.keyguard.SurfaceVisibilityController$registerFrameUpdateCallback$1
            public final void onFrameDraw(long j) {
                Function0.this.invoke();
            }
        });
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final boolean setForceInvisible(SurfaceControl.Transaction transaction, boolean z) {
        ((KeyguardSurfaceControllerImpl) ((KeyguardSurfaceController) this.keyguardSurfaceControllerLazy.get())).setKeyguardSurfaceVisible(transaction);
        return true;
    }

    @Override // com.android.systemui.keyguard.VisibilityController
    public final void resetForceInvisible(boolean z) {
    }
}
