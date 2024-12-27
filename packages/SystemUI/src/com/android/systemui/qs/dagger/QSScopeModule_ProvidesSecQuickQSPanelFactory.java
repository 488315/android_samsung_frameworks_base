package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.SecQuickQSPanel;
import dagger.internal.Provider;

public final class QSScopeModule_ProvidesSecQuickQSPanelFactory implements Provider {
    public final javax.inject.Provider viewProvider;

    public QSScopeModule_ProvidesSecQuickQSPanelFactory(javax.inject.Provider provider) {
        this.viewProvider = provider;
    }

    public static SecQuickQSPanel providesSecQuickQSPanel(View view) {
        QSScopeModule.Companion.getClass();
        return (SecQuickQSPanel) view.requireViewById(R.id.quick_qs_panel);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesSecQuickQSPanel((View) this.viewProvider.get());
    }
}
