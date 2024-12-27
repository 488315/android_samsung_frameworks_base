package com.android.systemui.navigationbar.interactor;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$3;
import com.android.systemui.util.SettingsHelper;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                Intrinsics.checkNotNull(consumer);
                consumer.accept(Boolean.valueOf(this.isEnabled()));
            }
        };
        this.callback = onChangedCallback2;
        this.settingsHelper.registerCallback(onChangedCallback2, Settings.Global.getUriFor(SettingsHelper.INDEX_NAVIGATIONBAR_BUTTON_TO_HIDE_KEYBOARD));
        this.settingsHelper.registerCallback(this.callback, Settings.Secure.getUriFor(SettingsHelper.INDEX_SHOW_KEYBOARD_BUTTON));
    }

    public final boolean isEnabled() {
        return this.settingsHelper.isNavigationBarHideKeyboardButtonEnabled() || (BasicRune.NAVBAR_MULTI_MODAL_ICON && this.settingsHelper.isShowMultiModalButton());
    }
}
