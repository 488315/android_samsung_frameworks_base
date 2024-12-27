package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.buttons.QSButtonsContainer;
import dagger.internal.Provider;

public final class QSScopeModule_ProvidesQSButtonsContainerFactory implements Provider {
    public final javax.inject.Provider viewProvider;

    public QSScopeModule_ProvidesQSButtonsContainerFactory(javax.inject.Provider provider) {
        this.viewProvider = provider;
    }

    public static QSButtonsContainer providesQSButtonsContainer(View view) {
        QSScopeModule.Companion.getClass();
        return (QSButtonsContainer) view.requireViewById(R.id.qs_button);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesQSButtonsContainer((View) this.viewProvider.get());
    }
}
