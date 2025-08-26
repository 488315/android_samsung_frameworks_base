package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanel;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class QSScopeModule_ProvideSecQSPanelFactory implements Provider {
    public final Provider viewProvider;

    public QSScopeModule_ProvideSecQSPanelFactory(Provider provider) {
        this.viewProvider = provider;
    }

    public static SecQSPanel provideSecQSPanel(View view) {
        QSScopeModule.Companion.getClass();
        SecQSPanel secQSPanel = (SecQSPanel) view.requireViewById(R.id.quick_settings_panel);
        secQSPanel.getClass();
        return secQSPanel;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSecQSPanel((View) this.viewProvider.get());
    }
}
