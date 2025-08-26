package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class RowAppearanceCoordinator_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider mAssistantFeedbackControllerProvider;
    private final Provider mSectionStyleProvider;

    public RowAppearanceCoordinator_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.mAssistantFeedbackControllerProvider = provider2;
        this.mSectionStyleProvider = provider3;
    }

    public static RowAppearanceCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new RowAppearanceCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static RowAppearanceCoordinator newInstance(Context context, AssistantFeedbackController assistantFeedbackController, SectionStyleProvider sectionStyleProvider) {
        return new RowAppearanceCoordinator(context, assistantFeedbackController, sectionStyleProvider);
    }

    public static RowAppearanceCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new RowAppearanceCoordinator_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public RowAppearanceCoordinator get() {
        return newInstance((Context) this.contextProvider.get(), (AssistantFeedbackController) this.mAssistantFeedbackControllerProvider.get(), (SectionStyleProvider) this.mSectionStyleProvider.get());
    }
}
