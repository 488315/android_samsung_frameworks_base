package com.android.systemui.subscreen.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SubScreenQuickPanelHeader;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowView;
import dagger.internal.Provider;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SubScreenQuickPanelModule_ProvideSubScreenQuickPanelHeaderFactory implements Provider {
    public final javax.inject.Provider quickPanelWindowViewProvider;

    public SubScreenQuickPanelModule_ProvideSubScreenQuickPanelHeaderFactory(javax.inject.Provider provider) {
        this.quickPanelWindowViewProvider = provider;
    }

    public static SubScreenQuickPanelHeader provideSubScreenQuickPanelHeader(SubScreenQuickPanelWindowView subScreenQuickPanelWindowView) {
        SubScreenQuickPanelModule.INSTANCE.getClass();
        View findViewById = subScreenQuickPanelWindowView.findViewById(R.id.sub_screen_quick_panel_header);
        Intrinsics.checkNotNull(findViewById);
        return (SubScreenQuickPanelHeader) findViewById;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSubScreenQuickPanelHeader((SubScreenQuickPanelWindowView) this.quickPanelWindowViewProvider.get());
    }
}
