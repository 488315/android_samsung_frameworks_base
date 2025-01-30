package com.android.systemui.dreams.callbacks;

import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.DejankUtils;
import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamStatusBarStateCallback implements Monitor.Callback {
    public final SysuiStatusBarStateController mStateController;

    public DreamStatusBarStateCallback(SysuiStatusBarStateController sysuiStatusBarStateController) {
        this.mStateController = sysuiStatusBarStateController;
    }

    @Override // com.android.systemui.shared.condition.Monitor.Callback
    public final void onConditionsChanged(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onConditionChanged:", z, "DreamStatusBarCallback");
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
            DejankUtils.startDetectingBlockingIpcs("StatusBarStateControllerImpl#setIsDreaming");
            Iterator it = new ArrayList(statusBarStateControllerImpl.mListeners).iterator();
            while (it.hasNext()) {
                ((SysuiStatusBarStateController.RankedListener) it.next()).mListener.onDreamingChanged(z);
            }
            DejankUtils.stopDetectingBlockingIpcs("StatusBarStateControllerImpl#setIsDreaming");
        }
    }
}
