package com.android.systemui.subscreen.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SubScreenQuickPanelHeader;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowView;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class SubScreenQuickPanelModule_ProvideSubScreenQuickPanelHeaderFactory implements Provider {
    public final Provider quickPanelWindowViewProvider;

    public SubScreenQuickPanelModule_ProvideSubScreenQuickPanelHeaderFactory(Provider provider) {
        this.quickPanelWindowViewProvider = provider;
    }

    public static SubScreenQuickPanelHeader provideSubScreenQuickPanelHeader(SubScreenQuickPanelWindowView subScreenQuickPanelWindowView) {
        SubScreenQuickPanelModule.INSTANCE.getClass();
        View viewFindViewById = subScreenQuickPanelWindowView.findViewById(R.id.sub_screen_quick_panel_header);
        viewFindViewById.getClass();
        return (SubScreenQuickPanelHeader) viewFindViewById;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSubScreenQuickPanelHeader((SubScreenQuickPanelWindowView) this.quickPanelWindowViewProvider.get());
    }
}
