package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.samsung.quicksetting.view.SecQSPanelComposeRoot;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSScopeModule_ProvideSecQSPanelComposeRootFactory implements Provider {
    public final Provider viewProvider;

    public QSScopeModule_ProvideSecQSPanelComposeRootFactory(Provider provider) {
        this.viewProvider = provider;
    }

    public static SecQSPanelComposeRoot provideSecQSPanelComposeRoot(View view) {
        QSScopeModule.Companion.getClass();
        SecQSPanelComposeRoot secQSPanelComposeRoot = (SecQSPanelComposeRoot) view.requireViewById(R.id.sec_quick_panel_compose_root);
        secQSPanelComposeRoot.getClass();
        return secQSPanelComposeRoot;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSecQSPanelComposeRoot((View) this.viewProvider.get());
    }
}
