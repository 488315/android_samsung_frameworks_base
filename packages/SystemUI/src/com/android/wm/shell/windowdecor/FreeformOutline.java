package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.view.SurfaceControlViewHost;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewHostViewContainer;
import com.android.wm.shell.windowdecor.widget.OutlineView;

/* loaded from: classes3.dex */
public class FreeformOutline {
    public Context mContext;
    public final DesktopModeWindowDecoration mDecoration;
    public AdditionalViewHostViewContainer mFreeformOutline;
    public ActivityManager.RunningTaskInfo mTaskInfo;

    public FreeformOutline(DesktopModeWindowDecoration desktopModeWindowDecoration) {
        this.mDecoration = desktopModeWindowDecoration;
    }

    public final OutlineView getOutlineView() {
        SurfaceControlViewHost surfaceControlViewHost;
        AdditionalViewHostViewContainer additionalViewHostViewContainer = this.mFreeformOutline;
        if (additionalViewHostViewContainer == null || (surfaceControlViewHost = additionalViewHostViewContainer.windowViewHost) == null || !(surfaceControlViewHost.getView() instanceof OutlineView)) {
            return null;
        }
        return (OutlineView) this.mFreeformOutline.windowViewHost.getView();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, int i, boolean z, boolean z2) {
        int i2;
        boolean z3;
        this.mTaskInfo = runningTaskInfo;
        this.mContext = context;
        OutlineView outlineView = getOutlineView();
        if (outlineView != null) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) outlineView.getLayoutParams();
            Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
            layoutParams.width = bounds.width();
            layoutParams.height = bounds.height();
            this.mFreeformOutline.windowViewHost.relayout(layoutParams, true);
            int outlineCaptionHeight = this.mDecoration.getOutlineCaptionHeight();
            int iLoadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(this.mContext.getResources(), R.dimen.mw_freeform_outline_thickness);
            UiModeManager uiModeManager = (UiModeManager) this.mContext.getSystemService("uimode");
            ComponentName componentName = this.mTaskInfo.realActivity;
            boolean z4 = false;
            if (componentName == null) {
                i2 = i;
                z3 = z2;
            } else {
                boolean z5 = uiModeManager.getPackageNightMode(componentName.getPackageName()) == 32;
                if (this.mTaskInfo.configuration.isNightModeActive() || z5) {
                    z3 = z2;
                    z4 = true;
                    i2 = i;
                }
            }
            outlineView.setOutlineInfo(i2, iLoadDimensionPixelSize, z4, z3, outlineCaptionHeight);
            if (z) {
                outlineView.invalidate();
            }
        }
    }
}
