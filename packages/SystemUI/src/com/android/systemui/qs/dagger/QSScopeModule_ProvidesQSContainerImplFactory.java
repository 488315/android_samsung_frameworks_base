package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.QSContainerImpl;
import dagger.internal.Provider;

public final class QSScopeModule_ProvidesQSContainerImplFactory implements Provider {
    public final javax.inject.Provider viewProvider;

    public QSScopeModule_ProvidesQSContainerImplFactory(javax.inject.Provider provider) {
        this.viewProvider = provider;
    }

    public static QSContainerImpl providesQSContainerImpl(View view) {
        QSScopeModule.Companion.getClass();
        return (QSContainerImpl) view.requireViewById(R.id.quick_settings_container);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesQSContainerImpl((View) this.viewProvider.get());
    }
}
