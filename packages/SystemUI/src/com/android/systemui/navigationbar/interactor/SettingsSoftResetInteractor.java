package com.android.systemui.navigationbar.interactor;

import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
