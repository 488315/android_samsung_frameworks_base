package com.android.systemui.navigationbar.interactor;

import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SettingsSoftResetInteractor {
    public final BroadcastDispatcher broadcastDispatcher;
    public SettingsSoftResetInteractor$addCallback$2 broadcastReceiver;
    public final IntentFilter intentFilter;

    public SettingsSoftResetInteractor(BroadcastDispatcher broadcastDispatcher) {
        this.broadcastDispatcher = broadcastDispatcher;
        IntentFilter intentFilter = new IntentFilter();
        this.intentFilter = intentFilter;
        intentFilter.addAction("com.samsung.intent.action.SETTINGS_SOFT_RESET");
    }
}
