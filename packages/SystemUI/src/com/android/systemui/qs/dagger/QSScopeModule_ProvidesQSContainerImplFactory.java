package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.QSContainerImpl;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
