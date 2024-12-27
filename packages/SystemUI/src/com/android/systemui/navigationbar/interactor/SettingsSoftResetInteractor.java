package com.android.systemui.navigationbar.interactor;

import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;

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
