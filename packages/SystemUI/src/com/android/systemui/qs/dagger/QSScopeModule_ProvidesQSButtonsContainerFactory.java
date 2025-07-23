package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.qs.buttons.QSButtonsContainer;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
