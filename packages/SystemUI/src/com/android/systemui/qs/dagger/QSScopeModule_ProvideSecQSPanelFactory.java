package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanel;
import dagger.internal.Provider;

public final class QSScopeModule_ProvideSecQSPanelFactory implements Provider {
    public final javax.inject.Provider viewProvider;

    public QSScopeModule_ProvideSecQSPanelFactory(javax.inject.Provider provider) {
        this.viewProvider = provider;
    }

    public static SecQSPanel provideSecQSPanel(View view) {
        QSScopeModule.Companion.getClass();
        return (SecQSPanel) view.requireViewById(R.id.quick_settings_panel);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSecQSPanel((View) this.viewProvider.get());
    }
}
