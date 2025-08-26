package com.android.systemui.shared.rotation;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.util.Log;
import android.view.WindowManagerGlobal;
import com.android.systemui.shared.rotation.RotationButtonController;

/* loaded from: classes3.dex */
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
                boolean z = RotationButtonController.OEM_DISALLOW_ROTATION_IN_SUW;
                ((RotationButtonController) obj).setRotateSuggestionButtonState(false);
                break;
            case 1:
                ((RotationButtonController) obj).mPendingRotationSuggestion = false;
                break;
            case 2:
                boolean z2 = RotationButtonController.OEM_DISALLOW_ROTATION_IN_SUW;
                ((RotationButtonController) obj).setRotateSuggestionButtonState(true, false);
                break;
            case 3:
                RotationButtonController rotationButtonController = (RotationButtonController) obj;
                boolean z3 = RotationButtonController.OEM_DISALLOW_ROTATION_IN_SUW;
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
            case 4:
                final RotationButtonController rotationButtonController2 = (RotationButtonController) obj;
                boolean z4 = RotationButtonController.OEM_DISALLOW_ROTATION_IN_SUW;
                rotationButtonController2.getClass();
                final Intent intentRegisterReceiver = rotationButtonController2.mContext.registerReceiver(rotationButtonController2.mDockedReceiver, new IntentFilter("android.intent.action.DOCK_EVENT"));
                rotationButtonController2.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        RotationButtonController rotationButtonController3 = rotationButtonController2;
                        Intent intent = intentRegisterReceiver;
                        boolean z5 = RotationButtonController.OEM_DISALLOW_ROTATION_IN_SUW;
                        rotationButtonController3.getClass();
                        if (intent == null) {
                            return;
                        }
                        rotationButtonController3.mDocked = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0) != 0;
                    }
                });
                break;
            default:
                ((RotationButtonController.TaskStackListenerImpl) obj).this$0.setRotateSuggestionButtonState(false);
                break;
        }
    }
}
