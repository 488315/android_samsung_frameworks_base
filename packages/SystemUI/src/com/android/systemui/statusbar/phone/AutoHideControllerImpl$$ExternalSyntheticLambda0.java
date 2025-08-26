package com.android.systemui.statusbar.phone;

import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.AutoHideUiElement;

/* loaded from: classes3.dex */
public final /* synthetic */ class AutoHideControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AutoHideControllerImpl$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                AutoHideControllerImpl autoHideControllerImpl = (AutoHideControllerImpl) obj;
                if (autoHideControllerImpl.isAnyTransientBarShown()) {
                    if (!BasicRune.NAVBAR_ENABLED || (!autoHideControllerImpl.mGameToolsShown && !autoHideControllerImpl.mTaskBarSuspend)) {
                        try {
                            autoHideControllerImpl.mWindowManagerService.hideTransientBars(autoHideControllerImpl.mDisplayId);
                        } catch (RemoteException unused) {
                            Log.w("AutoHideController", "Cannot get WindowManager");
                        }
                        AutoHideUiElement autoHideUiElement = autoHideControllerImpl.mStatusBar;
                        if (autoHideUiElement != null) {
                            autoHideUiElement.hide();
                        }
                        if (!BasicRune.NAVBAR_POLICY_VISIBILITY) {
                            AutoHideUiElement autoHideUiElement2 = autoHideControllerImpl.mNavigationBar;
                            if (autoHideUiElement2 != null) {
                                autoHideUiElement2.hide();
                                break;
                            }
                        } else {
                            autoHideControllerImpl.mObserver.notify(new AutoHideControllerImpl$$ExternalSyntheticLambda5());
                            break;
                        }
                    }
                }
                break;
            case 1:
                ((AutoHideControllerImpl) obj).mStatusBar.synchronizeState();
                break;
            case 2:
                ((AutoHideControllerImpl) obj).mNavigationBar.synchronizeState();
                break;
            case 3:
                ((AutoHideControllerImpl) obj).mStatusBar.synchronizeState();
                break;
            default:
                ((AutoHideUiElement) obj).synchronizeState();
                break;
        }
    }
}
