package com.android.systemui.dreams.callbacks;

import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.DejankUtils;
import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class DreamStatusBarStateCallback implements Monitor.Callback {
    public final SysuiStatusBarStateController mStateController;

    public DreamStatusBarStateCallback(SysuiStatusBarStateController sysuiStatusBarStateController) {
        this.mStateController = sysuiStatusBarStateController;
    }

    @Override // com.android.systemui.shared.condition.Monitor.Callback
    public final void onConditionsChanged(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("onConditionChanged:", "DreamStatusBarCallback", z);
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStateController;
        if (Log.isLoggable("SbStateController", 3)) {
            statusBarStateControllerImpl.getClass();
            Log.d("SbStateController", "setIsDreaming:" + z);
        }
        if (statusBarStateControllerImpl.mIsDreaming == z) {
            return;
        }
        statusBarStateControllerImpl.mIsDreaming = z;
        synchronized (statusBarStateControllerImpl.mListeners) {
            try {
                String strConcat = statusBarStateControllerImpl.getClass().getSimpleName().concat("#setIsDreaming");
                DejankUtils.startDetectingBlockingIpcs(strConcat);
                ArrayList arrayList = new ArrayList(statusBarStateControllerImpl.mListeners);
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((SysuiStatusBarStateController.RankedListener) obj).mListener.onDreamingChanged(z);
                }
                DejankUtils.stopDetectingBlockingIpcs(strConcat);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
