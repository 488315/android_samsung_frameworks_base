package com.android.systemui.navigationbar.views;

import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideControllerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda10 implements View.OnTouchListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda10(NavigationBar navigationBar, int i) {
        this.$r8$classId = i;
        this.f$0 = navigationBar;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int i = this.$r8$classId;
        NavigationBar navigationBar = this.f$0;
        switch (i) {
            case 0:
                AutoHideControllerImpl autoHideControllerImpl = navigationBar.mAutoHideController;
                if (autoHideControllerImpl == null) {
                    return false;
                }
                autoHideControllerImpl.checkUserAutoHide(motionEvent);
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
