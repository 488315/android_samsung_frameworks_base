package com.android.systemui.navigationbar.interactor;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$14;
import com.android.systemui.util.SettingsHelper;
import java.util.function.Consumer;

public final class OneHandModeInteractor {
    private SettingsHelper.OnChangedCallback callback;
    private final SettingsHelper settingsHelper;

    public OneHandModeInteractor(SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
    }

    public final void addCallback(final NavBarStoreImpl$initInteractor$14 navBarStoreImpl$initInteractor$14) {
        SettingsHelper.OnChangedCallback onChangedCallback = this.callback;
        if (onChangedCallback != null) {
            this.settingsHelper.unregisterCallback(onChangedCallback);
        }
        SettingsHelper.OnChangedCallback onChangedCallback2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.OneHandModeInteractor$addCallback$2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Consumer consumer = navBarStoreImpl$initInteractor$14;
                if (consumer != null) {
                    consumer.accept(this.getSettingsHelper().getOneHandModeRunningInfo());
                }
            }
        };
        this.callback = onChangedCallback2;
        this.settingsHelper.registerCallback(onChangedCallback2, Settings.System.getUriFor(SettingsHelper.INDEX_ONE_HAND_MODE_RUNNING), Settings.System.getUriFor(SettingsHelper.INDEX_ONE_HAND_RUNNING_INFO));
        navBarStoreImpl$initInteractor$14.accept(this.settingsHelper.getOneHandModeRunningInfo());
    }

    public final SettingsHelper getSettingsHelper() {
        return this.settingsHelper;
    }
}
