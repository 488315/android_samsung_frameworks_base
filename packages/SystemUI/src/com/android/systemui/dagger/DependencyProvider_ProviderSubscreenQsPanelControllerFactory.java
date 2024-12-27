package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.android.systemui.QpRune;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qs.InjectionInflationController;
import com.android.systemui.qs.QSTileHost;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DependencyProvider_ProviderSubscreenQsPanelControllerFactory implements Provider {
    public final javax.inject.Provider contextProvider;
    public final javax.inject.Provider hostProvider;
    public final javax.inject.Provider injectionInflaterProvider;
    public final DependencyProvider module;

    public DependencyProvider_ProviderSubscreenQsPanelControllerFactory(DependencyProvider dependencyProvider, javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.module = dependencyProvider;
        this.contextProvider = provider;
        this.injectionInflaterProvider = provider2;
        this.hostProvider = provider3;
    }

    public static SubscreenQsPanelController providerSubscreenQsPanelController(DependencyProvider dependencyProvider, Context context, InjectionInflationController injectionInflationController, QSTileHost qSTileHost) {
        dependencyProvider.getClass();
        Display display = ((DisplayManager) context.getSystemService("display")).getDisplay(1);
        return (!QpRune.QUICK_SUBSCREEN_PANEL || display == null) ? new SubscreenQsPanelController(context, injectionInflationController, qSTileHost) : new SubscreenQsPanelController(context.createWindowContext(display, 2017, null), injectionInflationController, qSTileHost);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providerSubscreenQsPanelController(this.module, (Context) this.contextProvider.get(), (InjectionInflationController) this.injectionInflaterProvider.get(), (QSTileHost) this.hostProvider.get());
    }
}
