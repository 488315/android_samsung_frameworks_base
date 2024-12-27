package com.android.systemui.navigationbar.interactor;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$8;
import com.android.systemui.util.SettingsHelper;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

public final class KeyboardButtonPositionInteractor {
    private SettingsHelper.OnChangedCallback callback;
    private final SettingsHelper settingsHelper;

    public KeyboardButtonPositionInteractor(SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
    }

    public final void addCallback(final NavBarStoreImpl$initInteractor$8 navBarStoreImpl$initInteractor$8) {
        SettingsHelper.OnChangedCallback onChangedCallback = this.callback;
        if (onChangedCallback != null) {
            this.settingsHelper.unregisterCallback(onChangedCallback);
        }
        SettingsHelper.OnChangedCallback onChangedCallback2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.KeyboardButtonPositionInteractor$addCallback$2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Consumer consumer = navBarStoreImpl$initInteractor$8;
                Intrinsics.checkNotNull(consumer);
                consumer.accept(Boolean.valueOf(this.getSettingsHelper().isKeyboardButtonOnLeft()));
            }
        };
        this.callback = onChangedCallback2;
        this.settingsHelper.registerCallback(onChangedCallback2, Settings.Secure.getUriFor(SettingsHelper.INDEX_KEYBOARD_BUTTON_POSITION));
    }

    public final SettingsHelper getSettingsHelper() {
        return this.settingsHelper;
    }
}
