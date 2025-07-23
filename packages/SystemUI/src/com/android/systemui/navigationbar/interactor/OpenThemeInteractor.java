package com.android.systemui.navigationbar.interactor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$11;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class OpenThemeInteractor {
    public final BroadcastDispatcher broadcastDispatcher;
    public OpenThemeInteractor$addCallback$2 broadcastReceiver;
    private SettingsHelper.OnChangedCallback callback;
    public final IntentFilter intentFilter;
    private final SettingsHelper settingsHelper;

    public OpenThemeInteractor(BroadcastDispatcher broadcastDispatcher, SettingsHelper settingsHelper) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.settingsHelper = settingsHelper;
        IntentFilter intentFilter = new IntentFilter();
        this.intentFilter = intentFilter;
        intentFilter.addAction("com.samsung.android.theme.themecenter.THEME_APPLY");
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.OpenThemeInteractor$addCallback$2] */
    public final void addCallback(final NavBarStoreImpl$initInteractor$11 navBarStoreImpl$initInteractor$11) {
        OpenThemeInteractor$addCallback$2 openThemeInteractor$addCallback$2 = this.broadcastReceiver;
        if (openThemeInteractor$addCallback$2 != null) {
            this.broadcastDispatcher.unregisterReceiver(openThemeInteractor$addCallback$2);
        }
        ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.OpenThemeInteractor$addCallback$2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Runnable runnable = navBarStoreImpl$initInteractor$11;
                if (runnable != null) {
                    runnable.run();
                }
            }
        };
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, r3, this.intentFilter, null, null, 0, null, 60);
        this.broadcastReceiver = r3;
        SettingsHelper.OnChangedCallback onChangedCallback = this.callback;
        if (onChangedCallback != null) {
            this.settingsHelper.unregisterCallback(onChangedCallback);
        }
        SettingsHelper.OnChangedCallback onChangedCallback2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.OpenThemeInteractor$addCallback$5
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Runnable runnable = navBarStoreImpl$initInteractor$11;
                if (runnable != null) {
                    runnable.run();
                }
            }
        };
        this.settingsHelper.registerCallback(onChangedCallback2, Settings.System.getUriFor("wallpapertheme_state"));
        this.callback = onChangedCallback2;
    }
}
