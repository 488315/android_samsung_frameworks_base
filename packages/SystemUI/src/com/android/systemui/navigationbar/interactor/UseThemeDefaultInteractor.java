package com.android.systemui.navigationbar.interactor;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$12;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class UseThemeDefaultInteractor {
    private SettingsHelper.OnChangedCallback callback;
    private final SettingsHelper settingsHelper;

    public UseThemeDefaultInteractor(SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
    }

    public final void addCallback(final NavBarStoreImpl$initInteractor$12 navBarStoreImpl$initInteractor$12) {
        SettingsHelper.OnChangedCallback onChangedCallback = this.callback;
        if (onChangedCallback != null) {
            this.settingsHelper.unregisterCallback(onChangedCallback);
        }
        SettingsHelper.OnChangedCallback onChangedCallback2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.UseThemeDefaultInteractor$addCallback$2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Runnable runnable = navBarStoreImpl$initInteractor$12;
                if (runnable != null) {
                    runnable.run();
                }
            }
        };
        this.settingsHelper.registerCallback(onChangedCallback2, Settings.Global.getUriFor(SettingsHelper.INDEX_NAVIGATIONBAR_USE_THEME_DEFAULT));
        this.callback = onChangedCallback2;
    }
}
