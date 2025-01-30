package com.android.systemui.statusbar.phone;

import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.AutoHideUiElement;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class AutoHideController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AutoHideController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((AutoHideController) this.f$0).mStatusBar.synchronizeState();
                break;
            case 1:
                AutoHideController autoHideController = (AutoHideController) this.f$0;
                if (autoHideController.isAnyTransientBarShown()) {
                    if (!BasicRune.NAVBAR_ENABLED || !autoHideController.mGameToolsShown) {
                        try {
                            autoHideController.mWindowManagerService.hideTransientBars(autoHideController.mDisplayId);
                        } catch (RemoteException unused) {
                            Log.w("AutoHideController", "Cannot get WindowManager");
                        }
                        AutoHideUiElement autoHideUiElement = autoHideController.mStatusBar;
                        if (autoHideUiElement != null) {
                            autoHideUiElement.hide();
                        }
                        if (!BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
                            AutoHideUiElement autoHideUiElement2 = autoHideController.mNavigationBar;
                            if (autoHideUiElement2 != null) {
                                autoHideUiElement2.hide();
                                break;
                            }
                        } else {
                            autoHideController.mObserver.notify(new AutoHideController$$ExternalSyntheticLambda2());
                            break;
                        }
                    }
                }
                break;
            default:
                ((AutoHideUiElement) this.f$0).synchronizeState();
                break;
        }
    }
}
