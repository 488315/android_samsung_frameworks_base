package com.android.systemui.dreams.dagger;

import android.view.ViewGroup;
import com.android.internal.util.Preconditions;
import com.android.systemui.R;
import com.android.systemui.dreams.DreamOverlayContainerView;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamOverlayModule_ProvidesDreamOverlayContentViewFactory implements Provider {
    public final Provider viewProvider;

    public DreamOverlayModule_ProvidesDreamOverlayContentViewFactory(Provider provider) {
        this.viewProvider = provider;
    }

    public static ViewGroup providesDreamOverlayContentView(DreamOverlayContainerView dreamOverlayContainerView) {
        ViewGroup viewGroup = (ViewGroup) Preconditions.checkNotNull((ViewGroup) dreamOverlayContainerView.findViewById(R.id.dream_overlay_content), "R.id.dream_overlay_content must not be null");
        dagger.internal.Preconditions.checkNotNullFromProvides(viewGroup);
        return viewGroup;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesDreamOverlayContentView((DreamOverlayContainerView) this.viewProvider.get());
    }
}
