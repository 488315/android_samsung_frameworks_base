package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.SecQuickQSPanel;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class QSScopeModule_ProvidesSecQuickQSPanelFactory implements Provider {
    public final Provider viewProvider;

    public QSScopeModule_ProvidesSecQuickQSPanelFactory(Provider provider) {
        this.viewProvider = provider;
    }

    public static SecQuickQSPanel providesSecQuickQSPanel(View view) {
        QSScopeModule.Companion.getClass();
        SecQuickQSPanel secQuickQSPanel = (SecQuickQSPanel) view.requireViewById(R.id.quick_qs_panel);
        secQuickQSPanel.getClass();
        return secQuickQSPanel;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesSecQuickQSPanel((View) this.viewProvider.get());
    }
}
