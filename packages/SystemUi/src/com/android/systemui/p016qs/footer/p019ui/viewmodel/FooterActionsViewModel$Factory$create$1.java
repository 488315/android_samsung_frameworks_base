package com.android.systemui.p016qs.footer.p019ui.viewmodel;

import androidx.lifecycle.DefaultLifecycleObserver;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.telephony.TelephonyListenerManager;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FooterActionsViewModel$Factory$create$1 implements DefaultLifecycleObserver {
    public final /* synthetic */ GlobalActionsDialogLite $globalActionsDialogLite;

    public FooterActionsViewModel$Factory$create$1(GlobalActionsDialogLite globalActionsDialogLite) {
        this.$globalActionsDialogLite = globalActionsDialogLite;
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public final void onDestroy$1() {
        GlobalActionsDialogLite globalActionsDialogLite = this.$globalActionsDialogLite;
        globalActionsDialogLite.mBroadcastDispatcher.unregisterReceiver(globalActionsDialogLite.mBroadcastReceiver);
        TelephonyListenerManager telephonyListenerManager = globalActionsDialogLite.mTelephonyListenerManager;
        ((ArrayList) telephonyListenerManager.mTelephonyCallback.mServiceStateListeners).remove(globalActionsDialogLite.mPhoneStateListener);
        telephonyListenerManager.updateListening();
        globalActionsDialogLite.mGlobalSettings.unregisterContentObserver(globalActionsDialogLite.mAirplaneModeObserver);
        ((ConfigurationControllerImpl) globalActionsDialogLite.mConfigurationController).removeCallback(globalActionsDialogLite);
    }
}
