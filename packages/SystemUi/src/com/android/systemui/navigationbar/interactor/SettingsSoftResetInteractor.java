package com.android.systemui.navigationbar.interactor;

import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
