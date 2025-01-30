package com.android.systemui.navigationbar;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.systemui.R;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavigationBarModule_ProvideNavigationBarFrameFactory implements Provider {
    public final Provider layoutInflaterProvider;

    public NavigationBarModule_ProvideNavigationBarFrameFactory(Provider provider) {
        this.layoutInflaterProvider = provider;
    }

    public static NavigationBarFrame provideNavigationBarFrame(LayoutInflater layoutInflater) {
        NavigationBarFrame navigationBarFrame = (NavigationBarFrame) layoutInflater.inflate(R.layout.navigation_bar_window, (ViewGroup) null);
        Preconditions.checkNotNullFromProvides(navigationBarFrame);
        return navigationBarFrame;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideNavigationBarFrame((LayoutInflater) this.layoutInflaterProvider.get());
    }
}
