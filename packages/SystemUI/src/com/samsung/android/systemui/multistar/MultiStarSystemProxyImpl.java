package com.samsung.android.systemui.multistar;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda2;
import com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda6;
import com.samsung.systemui.splugins.multistar.PluginMultiStarSystemProxy;

/* loaded from: classes4.dex */
public class MultiStarSystemProxyImpl implements PluginMultiStarSystemProxy {
    public final IActivityManager mIam = ActivityManager.getService();
    public final IWindowManager mIwm = WindowManagerGlobal.getWindowManagerService();
    public final SplitScreen mSplitScreen;

    public MultiStarSystemProxyImpl(Context context, SplitScreen splitScreen) {
        this.mSplitScreen = splitScreen;
    }

    @Override // com.samsung.systemui.splugins.multistar.PluginMultiStarSystemProxy
    public final void exitSplitScreen() {
        SplitScreenController.SplitScreenImpl splitScreenImpl = (SplitScreenController.SplitScreenImpl) this.mSplitScreen;
        SplitScreenController.this.mMainExecutor.execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda2(splitScreenImpl, 0));
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
    public final void setDividerResizeMode(boolean z) {
        SplitScreenController.SplitScreenImpl splitScreenImpl = (SplitScreenController.SplitScreenImpl) this.mSplitScreen;
        SplitScreenController.this.mMainExecutor.execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda6(splitScreenImpl, z, 0));
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
        SplitScreenController.this.mMainExecutor.execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda2(splitScreenImpl, 2));
    }
}
