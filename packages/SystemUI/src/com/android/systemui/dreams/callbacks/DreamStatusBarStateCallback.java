package com.android.systemui.dreams.callbacks;

import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.DejankUtils;
import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DreamStatusBarStateCallback implements Monitor.Callback {
    public final SysuiStatusBarStateController mStateController;

    public DreamStatusBarStateCallback(SysuiStatusBarStateController sysuiStatusBarStateController) {
        this.mStateController = sysuiStatusBarStateController;
    }

    @Override // com.android.systemui.shared.condition.Monitor.Callback
    public final void onConditionsChanged(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onConditionChanged:", "DreamStatusBarCallback", z);
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
                DejankUtils.startDetectingBlockingIpcs("StatusBarStateControllerImpl#setIsDreaming");
                Iterator it = new ArrayList(statusBarStateControllerImpl.mListeners).iterator();
                while (it.hasNext()) {
                    ((SysuiStatusBarStateController.RankedListener) it.next()).mListener.onDreamingChanged(z);
                }
                DejankUtils.stopDetectingBlockingIpcs("StatusBarStateControllerImpl#setIsDreaming");
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
