package com.android.systemui.navigationbar.interactor;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CoverDisplayWidgetInteractor {
    private SettingsHelper.OnChangedCallback callback;
    public final CoverDisplayWidgetInteractor$displayReadyRunnable$1 displayReadyRunnable = new CoverDisplayWidgetInteractor$displayReadyRunnable$1(this);
    private final SettingsHelper settingsHelper;

    public CoverDisplayWidgetInteractor(Context context, SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
    }

    public final void addCallback() {
        SettingsHelper.OnChangedCallback onChangedCallback = this.callback;
        if (onChangedCallback != null) {
            this.settingsHelper.unregisterCallback(onChangedCallback);
        }
        SettingsHelper.OnChangedCallback onChangedCallback2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.CoverDisplayWidgetInteractor$addCallback$2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                CoverDisplayWidgetInteractor.this.displayReadyRunnable.run();
            }
        };
        this.callback = onChangedCallback2;
        this.settingsHelper.registerCallback(onChangedCallback2, Settings.Secure.getUriFor(SettingsHelper.INDEX_SHOW_NAVIGATION_FOR_SUBSCREEN));
    }

    public final boolean isEnabled() {
        return this.settingsHelper.isShowNavigationForSubscreen() && SemWindowManager.getInstance().isFolded();
    }
}
