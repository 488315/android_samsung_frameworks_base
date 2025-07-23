package com.android.systemui.navigationbar.interactor;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$4;
import com.android.systemui.util.SettingsHelper;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class EdgeBackGesturePolicyInteractor {
    private SettingsHelper.OnChangedCallback callback;
    private final SettingsHelper settingsHelper;

    public EdgeBackGesturePolicyInteractor(SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
    }

    public final void addCallback(final NavBarStoreImpl$initInteractor$4 navBarStoreImpl$initInteractor$4) {
        SettingsHelper.OnChangedCallback onChangedCallback = this.callback;
        if (onChangedCallback != null) {
            this.settingsHelper.unregisterCallback(onChangedCallback);
        }
        SettingsHelper settingsHelper = this.settingsHelper;
        settingsHelper.setEdgeBackGestureDisablePolicy(0);
        SettingsHelper.OnChangedCallback onChangedCallback2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.EdgeBackGesturePolicyInteractor$addCallback$2$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Consumer consumer = navBarStoreImpl$initInteractor$4;
                consumer.getClass();
                consumer.accept(Integer.valueOf(this.getSettingsHelper().getEdgeBackGestureDisablePolicy()));
            }
        };
        this.callback = onChangedCallback2;
        settingsHelper.registerCallback(onChangedCallback2, Settings.Global.getUriFor(SettingsHelper.INDEX_NAVIGATION_BAR_GESTURE_DISABLED_BY_POLICY));
    }

    public final SettingsHelper getSettingsHelper() {
        return this.settingsHelper;
    }
}
