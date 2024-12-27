package com.android.systemui.navigationbar.interactor;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$4;
import com.android.systemui.util.SettingsHelper;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        settingsHelper.setEdgeBackDisableByPolicy(false);
        SettingsHelper.OnChangedCallback onChangedCallback2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.EdgeBackGesturePolicyInteractor$addCallback$2$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Consumer consumer = navBarStoreImpl$initInteractor$4;
                Intrinsics.checkNotNull(consumer);
                consumer.accept(Boolean.valueOf(this.getSettingsHelper().isEdgeBackDisabledByPolicy()));
            }
        };
        this.callback = onChangedCallback2;
        settingsHelper.registerCallback(onChangedCallback2, Settings.Global.getUriFor(SettingsHelper.INDEX_NAVIGATION_BAR_GESTURE_DISABLED_BY_POLICY));
    }

    public final SettingsHelper getSettingsHelper() {
        return this.settingsHelper;
    }
}
