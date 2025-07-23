package com.android.systemui.navigationbar.interactor;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$3;
import com.android.systemui.util.SettingsHelper;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ButtonToHideKeyboardInteractor {
    private SettingsHelper.OnChangedCallback callback;
    private final SettingsHelper settingsHelper;

    public ButtonToHideKeyboardInteractor(SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
    }

    public final void addCallback(final NavBarStoreImpl$initInteractor$3 navBarStoreImpl$initInteractor$3) {
        SettingsHelper.OnChangedCallback onChangedCallback = this.callback;
        if (onChangedCallback != null) {
            this.settingsHelper.unregisterCallback(onChangedCallback);
        }
        SettingsHelper.OnChangedCallback onChangedCallback2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.ButtonToHideKeyboardInteractor$addCallback$2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Consumer consumer = navBarStoreImpl$initInteractor$3;
                consumer.getClass();
                consumer.accept(Boolean.valueOf(this.isEnabled()));
            }
        };
        this.callback = onChangedCallback2;
        this.settingsHelper.registerCallback(onChangedCallback2, Settings.Global.getUriFor(SettingsHelper.INDEX_NAVIGATIONBAR_BUTTON_TO_HIDE_KEYBOARD));
        this.settingsHelper.registerCallback(this.callback, Settings.Secure.getUriFor(SettingsHelper.INDEX_SHOW_KEYBOARD_BUTTON));
    }

    public final boolean isEnabled() {
        if (this.settingsHelper.isNavigationBarHideKeyboardButtonEnabled()) {
            return true;
        }
        return BasicRune.NAVBAR_MULTI_MODAL_ICON && this.settingsHelper.isShowMultiModalButton();
    }
}
