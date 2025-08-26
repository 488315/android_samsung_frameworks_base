package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.buttons.QSButtonsContainer;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class QSScopeModule_ProvidesQSButtonsContainerFactory implements Provider {
    public final Provider viewProvider;

    public QSScopeModule_ProvidesQSButtonsContainerFactory(Provider provider) {
        this.viewProvider = provider;
    }

    public static QSButtonsContainer providesQSButtonsContainer(View view) {
        QSScopeModule.Companion.getClass();
        QSButtonsContainer qSButtonsContainer = (QSButtonsContainer) view.requireViewById(R.id.qs_button);
        qSButtonsContainer.getClass();
        return qSButtonsContainer;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesQSButtonsContainer((View) this.viewProvider.get());
    }
}
