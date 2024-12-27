package com.android.systemui.navigationbar;

import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideController;

public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda3 implements View.OnTouchListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda3(NavigationBar navigationBar, int i) {
        this.$r8$classId = i;
        this.f$0 = navigationBar;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int i = this.$r8$classId;
        NavigationBar navigationBar = this.f$0;
        switch (i) {
            case 0:
                AutoHideController autoHideController = navigationBar.mAutoHideController;
                if (autoHideController == null) {
                    return false;
                }
                autoHideController.checkUserAutoHide(motionEvent);
                return false;
            case 1:
                navigationBar.getClass();
                int action = motionEvent.getAction() & 255;
                CommandQueue commandQueue = navigationBar.mCommandQueue;
                if (action == 0) {
                    commandQueue.preloadRecentApps();
                    return false;
                }
                if (action == 3) {
                    commandQueue.cancelPreloadRecentApps();
                    return false;
                }
                if (action != 1 || view.isPressed()) {
                    return false;
                }
                commandQueue.cancelPreloadRecentApps();
                return false;
            default:
                return navigationBar.onHomeTouch(view, motionEvent);
        }
    }
}
