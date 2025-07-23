package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.view.SurfaceControlViewHost;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewHostViewContainer;
import com.android.wm.shell.windowdecor.widget.OutlineView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:10:0x006a, code lost:
    
        if (r2 == false) goto L14;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void relayout(android.app.ActivityManager.RunningTaskInfo r6, android.content.Context r7, int r8, boolean r9) {
        /*
            r5 = this;
            r5.mTaskInfo = r6
            r5.mContext = r7
            com.android.wm.shell.windowdecor.widget.OutlineView r7 = r5.getOutlineView()
            if (r7 == 0) goto L76
            android.view.ViewGroup$LayoutParams r0 = r7.getLayoutParams()
            android.view.WindowManager$LayoutParams r0 = (android.view.WindowManager.LayoutParams) r0
            android.content.res.Configuration r6 = r6.configuration
            android.app.WindowConfiguration r6 = r6.windowConfiguration
            android.graphics.Rect r6 = r6.getBounds()
            int r1 = r6.width()
            r0.width = r1
            int r6 = r6.height()
            r0.height = r6
            com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewHostViewContainer r6 = r5.mFreeformOutline
            android.view.SurfaceControlViewHost r6 = r6.windowViewHost
            r1 = 1
            r6.relayout(r0, r1)
            com.android.wm.shell.windowdecor.DesktopModeWindowDecoration r6 = r5.mDecoration
            int r6 = r6.getOutlineCaptionHeight()
            android.content.Context r0 = r5.mContext
            android.content.res.Resources r0 = r0.getResources()
            r2 = 2131168175(0x7f070baf, float:1.7950644E38)
            int r0 = com.android.wm.shell.windowdecor.WindowDecoration.loadDimensionPixelSize(r0, r2)
            android.content.Context r2 = r5.mContext
            java.lang.String r3 = "uimode"
            java.lang.Object r2 = r2.getSystemService(r3)
            android.app.UiModeManager r2 = (android.app.UiModeManager) r2
            android.app.ActivityManager$RunningTaskInfo r3 = r5.mTaskInfo
            android.content.ComponentName r3 = r3.realActivity
            r4 = 0
            if (r3 == 0) goto L6d
            java.lang.String r3 = r3.getPackageName()
            int r2 = r2.getPackageNightMode(r3)
            r3 = 32
            if (r2 != r3) goto L5f
            r2 = r1
            goto L60
        L5f:
            r2 = r4
        L60:
            android.app.ActivityManager$RunningTaskInfo r5 = r5.mTaskInfo
            android.content.res.Configuration r5 = r5.configuration
            boolean r5 = r5.isNightModeActive()
            if (r5 != 0) goto L6e
            if (r2 == 0) goto L6d
            goto L6e
        L6d:
            r1 = r4
        L6e:
            r7.setOutlineInfo(r8, r0, r6, r1)
            if (r9 == 0) goto L76
            r7.invalidate()
        L76:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.FreeformOutline.relayout(android.app.ActivityManager$RunningTaskInfo, android.content.Context, int, boolean):void");
    }
}
