package com.android.systemui.qs.dagger;

import android.content.Context;
import android.view.View;
import dagger.internal.Provider;

public final class QSScopeModule_ProvideThemedContextFactory implements Provider {
    public final javax.inject.Provider viewProvider;

    public QSScopeModule_ProvideThemedContextFactory(javax.inject.Provider provider) {
        this.viewProvider = provider;
    }

    public static Context provideThemedContext(View view) {
        QSScopeModule.Companion.getClass();
        return view.getContext();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideThemedContext((View) this.viewProvider.get());
    }
}
