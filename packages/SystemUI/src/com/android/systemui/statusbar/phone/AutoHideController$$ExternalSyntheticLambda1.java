package com.android.systemui.statusbar.phone;

import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class AutoHideController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AutoHideController$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AutoHideUiElement autoHideUiElement;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                AutoHideController autoHideController = (AutoHideController) obj;
                if (autoHideController.isAnyTransientBarShown()) {
                    if (!BasicRune.NAVBAR_ENABLED || !autoHideController.mGameToolsShown) {
                        try {
                            autoHideController.mWindowManagerService.hideTransientBars(autoHideController.mDisplayId);
                        } catch (RemoteException unused) {
                            Log.w("AutoHideController", "Cannot get WindowManager");
                        }
                        AutoHideUiElement autoHideUiElement2 = autoHideController.mStatusBar;
                        if (autoHideUiElement2 != null) {
                            autoHideUiElement2.hide();
                        }
                        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE && (autoHideUiElement = autoHideController.mFlexPanel) != null) {
                            autoHideUiElement.hide();
                        }
                        if (!BasicRune.NAVBAR_POLICY_VISIBILITY) {
                            AutoHideUiElement autoHideUiElement3 = autoHideController.mNavigationBar;
                            if (autoHideUiElement3 != null) {
                                autoHideUiElement3.hide();
                                break;
                            }
                        } else {
                            autoHideController.mObserver.notify(new AutoHideController$$ExternalSyntheticLambda6());
                            break;
                        }
                    }
                }
                break;
            case 1:
                ((AutoHideController) obj).mStatusBar.synchronizeState();
                break;
            case 2:
                ((AutoHideController) obj).mNavigationBar.synchronizeState();
                break;
            case 3:
                ((AutoHideController) obj).mStatusBar.synchronizeState();
                break;
            default:
                ((AutoHideUiElement) obj).synchronizeState();
                break;
        }
    }
}
