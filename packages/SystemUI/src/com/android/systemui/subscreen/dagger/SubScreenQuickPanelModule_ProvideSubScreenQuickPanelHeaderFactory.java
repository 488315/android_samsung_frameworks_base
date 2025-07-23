package com.android.systemui.subscreen.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SubScreenQuickPanelHeader;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowView;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SubScreenQuickPanelModule_ProvideSubScreenQuickPanelHeaderFactory implements Provider {
    public final Provider quickPanelWindowViewProvider;

    public SubScreenQuickPanelModule_ProvideSubScreenQuickPanelHeaderFactory(Provider provider) {
        this.quickPanelWindowViewProvider = provider;
    }

    public static SubScreenQuickPanelHeader provideSubScreenQuickPanelHeader(SubScreenQuickPanelWindowView subScreenQuickPanelWindowView) {
        SubScreenQuickPanelModule.INSTANCE.getClass();
        View findViewById = subScreenQuickPanelWindowView.findViewById(R.id.sub_screen_quick_panel_header);
        findViewById.getClass();
        return (SubScreenQuickPanelHeader) findViewById;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSubScreenQuickPanelHeader((SubScreenQuickPanelWindowView) this.quickPanelWindowViewProvider.get());
    }
}
