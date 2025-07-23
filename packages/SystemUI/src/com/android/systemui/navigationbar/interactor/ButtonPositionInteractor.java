package com.android.systemui.navigationbar.interactor;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$2;
import com.android.systemui.util.SettingsHelper;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ButtonPositionInteractor {
    private SettingsHelper.OnChangedCallback callback;
    private final SettingsHelper settingsHelper;

    public ButtonPositionInteractor(SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
    }

    public final void addCallback(final NavBarStoreImpl$initInteractor$2 navBarStoreImpl$initInteractor$2) {
        SettingsHelper.OnChangedCallback onChangedCallback = this.callback;
        if (onChangedCallback != null) {
            this.settingsHelper.unregisterCallback(onChangedCallback);
        }
        SettingsHelper.OnChangedCallback onChangedCallback2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.ButtonPositionInteractor$addCallback$2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Consumer consumer = navBarStoreImpl$initInteractor$2;
                consumer.getClass();
                consumer.accept(Integer.valueOf(this.getSettingsHelper().getNavigationBarAlignPosition()));
            }
        };
        this.callback = onChangedCallback2;
        this.settingsHelper.registerCallback(onChangedCallback2, Settings.Global.getUriFor(SettingsHelper.INDEX_NAVIGATION_BAR_BUTTON_POSITION));
    }

    public final SettingsHelper getSettingsHelper() {
        return this.settingsHelper;
    }
}
