package com.android.systemui.dreams.dagger;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.internal.util.Preconditions;
import com.android.systemui.R;
import com.android.systemui.dreams.DreamOverlayContainerView;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamOverlayModule_ProvidesDreamOverlayContainerViewFactory implements Provider {
    public final Provider layoutInflaterProvider;

    public DreamOverlayModule_ProvidesDreamOverlayContainerViewFactory(Provider provider) {
        this.layoutInflaterProvider = provider;
    }

    public static DreamOverlayContainerView providesDreamOverlayContainerView(LayoutInflater layoutInflater) {
        DreamOverlayContainerView dreamOverlayContainerView = (DreamOverlayContainerView) Preconditions.checkNotNull((DreamOverlayContainerView) layoutInflater.inflate(R.layout.dream_overlay_container, (ViewGroup) null), "R.layout.dream_layout_container could not be properly inflated");
        dagger.internal.Preconditions.checkNotNullFromProvides(dreamOverlayContainerView);
        return dreamOverlayContainerView;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesDreamOverlayContainerView((LayoutInflater) this.layoutInflaterProvider.get());
    }
}
