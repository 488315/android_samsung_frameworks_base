package com.android.systemui.navigationbar.interactor;

import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class OpenThemeInteractor {
    public final BroadcastDispatcher broadcastDispatcher;
    public OpenThemeInteractor$addCallback$2 broadcastReceiver;
    public OpenThemeInteractor$addCallback$5 callback;
    public final IntentFilter intentFilter;
    public final SettingsHelper settingsHelper;

    public OpenThemeInteractor(BroadcastDispatcher broadcastDispatcher, SettingsHelper settingsHelper) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.settingsHelper = settingsHelper;
        IntentFilter intentFilter = new IntentFilter();
        this.intentFilter = intentFilter;
        intentFilter.addAction("com.samsung.android.theme.themecenter.THEME_APPLY");
    }
}
