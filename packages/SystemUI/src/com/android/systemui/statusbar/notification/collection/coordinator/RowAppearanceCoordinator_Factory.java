package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RowAppearanceCoordinator_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider mAssistantFeedbackControllerProvider;
    private final javax.inject.Provider mSectionStyleProvider;

    public RowAppearanceCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.contextProvider = provider;
        this.mAssistantFeedbackControllerProvider = provider2;
        this.mSectionStyleProvider = provider3;
    }

    public static RowAppearanceCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new RowAppearanceCoordinator_Factory(provider, provider2, provider3);
    }

    public static RowAppearanceCoordinator newInstance(Context context, AssistantFeedbackController assistantFeedbackController, SectionStyleProvider sectionStyleProvider) {
        return new RowAppearanceCoordinator(context, assistantFeedbackController, sectionStyleProvider);
    }

    @Override // javax.inject.Provider
    public RowAppearanceCoordinator get() {
        return newInstance((Context) this.contextProvider.get(), (AssistantFeedbackController) this.mAssistantFeedbackControllerProvider.get(), (SectionStyleProvider) this.mSectionStyleProvider.get());
    }
}
