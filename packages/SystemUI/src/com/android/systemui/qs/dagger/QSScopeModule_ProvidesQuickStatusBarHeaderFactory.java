package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.SecQuickStatusBarHeader;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSScopeModule_ProvidesQuickStatusBarHeaderFactory implements Provider {
    public final Provider viewProvider;

    public QSScopeModule_ProvidesQuickStatusBarHeaderFactory(Provider provider) {
        this.viewProvider = provider;
    }

    public static SecQuickStatusBarHeader providesQuickStatusBarHeader(View view) {
        QSScopeModule.Companion.getClass();
        SecQuickStatusBarHeader secQuickStatusBarHeader = (SecQuickStatusBarHeader) view.requireViewById(R.id.header);
        secQuickStatusBarHeader.getClass();
        return secQuickStatusBarHeader;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesQuickStatusBarHeader((View) this.viewProvider.get());
    }
}
