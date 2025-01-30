package com.android.systemui.dreams.dagger;

import com.android.internal.util.Preconditions;
import com.android.systemui.R;
import com.android.systemui.dreams.DreamOverlayContainerView;
import com.android.systemui.dreams.DreamOverlayStatusBarView;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamOverlayModule_ProvidesDreamOverlayStatusBarViewFactory implements Provider {
    public final Provider viewProvider;

    public DreamOverlayModule_ProvidesDreamOverlayStatusBarViewFactory(Provider provider) {
        this.viewProvider = provider;
    }

    public static DreamOverlayStatusBarView providesDreamOverlayStatusBarView(DreamOverlayContainerView dreamOverlayContainerView) {
        DreamOverlayStatusBarView dreamOverlayStatusBarView = (DreamOverlayStatusBarView) Preconditions.checkNotNull((DreamOverlayStatusBarView) dreamOverlayContainerView.findViewById(R.id.dream_overlay_status_bar), "R.id.status_bar must not be null");
        dagger.internal.Preconditions.checkNotNullFromProvides(dreamOverlayStatusBarView);
        return dreamOverlayStatusBarView;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesDreamOverlayStatusBarView((DreamOverlayContainerView) this.viewProvider.get());
    }
}
