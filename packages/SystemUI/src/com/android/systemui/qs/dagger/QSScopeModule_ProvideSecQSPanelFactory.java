package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanel;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
