package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.QSContainerImpl;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class QSScopeModule_ProvidesQSContainerImplFactory implements Provider {
    public final Provider viewProvider;

    public QSScopeModule_ProvidesQSContainerImplFactory(Provider provider) {
        this.viewProvider = provider;
    }

    public static QSContainerImpl providesQSContainerImpl(View view) {
        QSScopeModule.Companion.getClass();
        QSContainerImpl qSContainerImpl = (QSContainerImpl) view.requireViewById(R.id.quick_settings_container);
        qSContainerImpl.getClass();
        return qSContainerImpl;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesQSContainerImpl((View) this.viewProvider.get());
    }
}
