package com.android.server.wm;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.inputmethod.ImeTracker;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
class DisplayAreaGroup extends RootDisplayArea {
    public DisplayAreaGroup(WindowManagerService windowManagerService, String str, int i) {
        super(windowManagerService, str, i);
    }

    private boolean isOrientationDifferentFromDisplay(Rect rect) {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent == null) {
            return false;
        }
        Rect bounds = displayContent.getBounds();
        return (rect.width() < rect.height()) != (bounds.width() < bounds.height());
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public boolean canShowTransient() {
        return this instanceof InsetsPolicy.ImmersiveControlTarget;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public int getOrientation(int i) {
        int orientation = super.getOrientation(i);
        return isOrientationDifferentFromDisplay()
                ? ActivityInfo.reverseOrientation(orientation)
                : orientation;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public int getRequestedVisibleTypes() {
        return WindowInsets.Type.defaultVisible();
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public /* bridge */ /* synthetic */ WindowState getWindow() {
        return null;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public /* bridge */ /* synthetic */ void hideInsets(int i, boolean z, ImeTracker.Token token) {}

    @Override // com.android.server.wm.RootDisplayArea
    public boolean isOrientationDifferentFromDisplay() {
        return isOrientationDifferentFromDisplay(getBounds());
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public /* bridge */ /* synthetic */ boolean isRequestedVisible(int i) {
        return super.isRequestedVisible(i);
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public /* bridge */ /* synthetic */ void notifyInsetsControlChanged(int i) {}

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ void onLeashAnimationStarting(
            SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {}

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer,
              // com.android.server.wm.ConfigurationContainer
    public void resolveOverrideConfiguration(Configuration configuration) {
        super.resolveOverrideConfiguration(configuration);
        Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
        if (resolvedOverrideConfiguration.orientation != 0) {
            return;
        }
        Rect bounds = resolvedOverrideConfiguration.windowConfiguration.getBounds();
        if (bounds.isEmpty()) {
            bounds = configuration.windowConfiguration.getBounds();
        }
        if (isOrientationDifferentFromDisplay(bounds)) {
            int i = configuration.orientation;
            if (i == 1) {
                resolvedOverrideConfiguration.orientation = 2;
            } else if (i == 2) {
                resolvedOverrideConfiguration.orientation = 1;
            }
        }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public /* bridge */ /* synthetic */ void setImeInputTargetRequestedVisibility(boolean z) {}

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ boolean shouldDeferAnimationFinish(Runnable runnable) {
        return false;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public /* bridge */ /* synthetic */ void showInsets(int i, boolean z, ImeTracker.Token token) {}
}
