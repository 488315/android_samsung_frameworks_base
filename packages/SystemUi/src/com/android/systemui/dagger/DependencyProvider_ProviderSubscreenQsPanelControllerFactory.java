package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.android.systemui.QpRune;
import com.android.systemui.p014qp.SubscreenQsPanelController;
import com.android.systemui.p016qs.InjectionInflationController;
import com.android.systemui.p016qs.QSTileHost;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    public static SubscreenQsPanelController providerSubscreenQsPanelController(DependencyProvider dependencyProvider, Context context, InjectionInflationController injectionInflationController, QSTileHost qSTileHost) {
        dependencyProvider.getClass();
        Display display = ((DisplayManager) context.getSystemService("display")).getDisplay(1);
        return (!QpRune.QUICK_PANEL_SUBSCREEN || display == null) ? new SubscreenQsPanelController(context, injectionInflationController, qSTileHost) : new SubscreenQsPanelController(context.createWindowContext(display, 2017, null), injectionInflationController, qSTileHost);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providerSubscreenQsPanelController(this.module, (Context) this.contextProvider.get(), (InjectionInflationController) this.injectionInflaterProvider.get(), (QSTileHost) this.hostProvider.get());
    }
}
