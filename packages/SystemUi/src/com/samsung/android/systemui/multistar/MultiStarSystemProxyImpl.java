package com.samsung.android.systemui.multistar;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0;
import com.samsung.systemui.splugins.multistar.PluginMultiStarSystemProxy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MultiStarSystemProxyImpl implements PluginMultiStarSystemProxy {
    public final IActivityManager mIam = ActivityManager.getService();
    public final IWindowManager mIwm = WindowManagerGlobal.getWindowManagerService();
    public final SplitScreen mSplitScreen;

    public MultiStarSystemProxyImpl(Context context, SplitScreen splitScreen) {
        this.mSplitScreen = splitScreen;
    }

    @Override // com.samsung.systemui.splugins.multistar.PluginMultiStarSystemProxy
    public final Rect getStableInsets() {
        Rect rect = new Rect();
        try {
            this.mIwm.getStableInsets(0, rect);
            return rect;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.samsung.systemui.splugins.multistar.PluginMultiStarSystemProxy
    public final void setDividerResizeMode(final boolean z) {
        final SplitScreenController.SplitScreenImpl splitScreenImpl = (SplitScreenController.SplitScreenImpl) this.mSplitScreen;
        ((HandlerExecutor) SplitScreenController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SplitScreenController.SplitScreenImpl splitScreenImpl2 = SplitScreenController.SplitScreenImpl.this;
                boolean z2 = z;
                SplitScreenController.this.mDividerResizeController.mUseGuideViewByMultiStar = z2;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setDividerResizeMode: useGuideView=", z2, "DividerResizeController");
            }
        });
    }

    @Override // com.samsung.systemui.splugins.multistar.PluginMultiStarSystemProxy
    public final void setLongLiveApp(String str) {
        try {
            this.mIam.setLongLiveApp(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.systemui.splugins.multistar.PluginMultiStarSystemProxy
    public final void toggleSplitScreen() {
        SplitScreenController.SplitScreenImpl splitScreenImpl = (SplitScreenController.SplitScreenImpl) this.mSplitScreen;
        ((HandlerExecutor) SplitScreenController.this.mMainExecutor).execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0(splitScreenImpl, 0));
    }

    @Override // com.samsung.systemui.splugins.multistar.PluginMultiStarSystemProxy
    public final void startDismissSplit(boolean z) {
    }
}
