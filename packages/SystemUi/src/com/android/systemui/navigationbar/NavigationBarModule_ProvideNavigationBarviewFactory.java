package com.android.systemui.navigationbar;

import android.view.LayoutInflater;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavigationBarModule_ProvideNavigationBarviewFactory implements Provider {
    public final Provider frameProvider;
    public final Provider layoutInflaterProvider;

    public NavigationBarModule_ProvideNavigationBarviewFactory(Provider provider, Provider provider2) {
        this.layoutInflaterProvider = provider;
        this.frameProvider = provider2;
    }

    public static NavigationBarView provideNavigationBarview(LayoutInflater layoutInflater, NavigationBarFrame navigationBarFrame) {
        NavigationBarView navigationBarView = (NavigationBarView) layoutInflater.inflate(BasicRune.NAVBAR_ENABLED ? R.layout.samsung_navigation_bar : R.layout.navigation_bar, navigationBarFrame).findViewById(R.id.navigation_bar_view);
        Preconditions.checkNotNullFromProvides(navigationBarView);
        return navigationBarView;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideNavigationBarview((LayoutInflater) this.layoutInflaterProvider.get(), (NavigationBarFrame) this.frameProvider.get());
    }
}
