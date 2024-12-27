package com.android.systemui.shared.rotation;

import android.os.RemoteException;
import android.util.Log;
import android.view.WindowManagerGlobal;
import com.android.systemui.shared.rotation.RotationButtonController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class RotationButtonController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RotationButtonController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((RotationButtonController) obj).setRotateSuggestionButtonState(false);
                break;
            case 1:
                ((RotationButtonController) obj).mPendingRotationSuggestion = false;
                break;
            case 2:
                ((RotationButtonController) obj).setRotateSuggestionButtonState(true, false);
                break;
            case 3:
                RotationButtonController rotationButtonController = (RotationButtonController) obj;
                rotationButtonController.getClass();
                try {
                    rotationButtonController.mContext.unregisterReceiver(rotationButtonController.mDockedReceiver);
                } catch (IllegalArgumentException e) {
                    Log.e("RotationButtonController", "Docked receiver already unregistered", e);
                }
                if (rotationButtonController.mRotationWatcherRegistered) {
                    try {
                        WindowManagerGlobal.getWindowManagerService().removeRotationWatcher(rotationButtonController.mRotationWatcher);
                        break;
                    } catch (RemoteException e2) {
                        Log.e("RotationButtonController", "UnregisterListeners caught a RemoteException", e2);
                        return;
                    }
                }
                break;
            default:
                ((RotationButtonController.TaskStackListenerImpl) obj).this$0.setRotateSuggestionButtonState(false);
                break;
        }
    }
}
