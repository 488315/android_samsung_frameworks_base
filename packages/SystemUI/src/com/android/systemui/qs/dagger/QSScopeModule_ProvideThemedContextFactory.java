package com.android.systemui.qs.dagger;

import android.content.Context;
import android.view.View;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSScopeModule_ProvideThemedContextFactory implements Provider {
    public final Provider viewProvider;

    public QSScopeModule_ProvideThemedContextFactory(Provider provider) {
        this.viewProvider = provider;
    }

    public static Context provideThemedContext(View view) {
        QSScopeModule.Companion.getClass();
        Context context = view.getContext();
        context.getClass();
        return context;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideThemedContext((View) this.viewProvider.get());
    }
}
