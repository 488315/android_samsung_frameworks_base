package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.android.systemui.QpRune;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qs.InjectionInflationController;
import com.android.systemui.qs.QSHost;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DependencyProvider_ProviderSubscreenQsPanelControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider hostProvider;
    public final Provider injectionInflaterProvider;
    public final DependencyProvider module;

    public DependencyProvider_ProviderSubscreenQsPanelControllerFactory(DependencyProvider dependencyProvider, Provider provider, Provider provider2, Provider provider3) {
        this.module = dependencyProvider;
        this.contextProvider = provider;
        this.injectionInflaterProvider = provider2;
        this.hostProvider = provider3;
    }

    public static SubscreenQsPanelController providerSubscreenQsPanelController(DependencyProvider dependencyProvider, Context context, InjectionInflationController injectionInflationController, QSHost qSHost) {
        dependencyProvider.getClass();
        Display display = ((DisplayManager) context.getSystemService("display")).getDisplay(1);
        return (!QpRune.QUICK_SUBSCREEN_PANEL || display == null) ? new SubscreenQsPanelController(context, injectionInflationController, qSHost) : new SubscreenQsPanelController(context.createWindowContext(display, 2017, null), injectionInflationController, qSHost);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providerSubscreenQsPanelController(this.module, (Context) this.contextProvider.get(), (InjectionInflationController) this.injectionInflaterProvider.get(), (QSHost) this.hostProvider.get());
    }
}
