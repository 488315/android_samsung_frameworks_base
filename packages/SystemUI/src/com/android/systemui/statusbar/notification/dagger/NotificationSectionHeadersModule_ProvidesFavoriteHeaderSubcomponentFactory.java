package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.R;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class NotificationSectionHeadersModule_ProvidesFavoriteHeaderSubcomponentFactory implements Provider {
    public final javax.inject.Provider builderProvider;

    public NotificationSectionHeadersModule_ProvidesFavoriteHeaderSubcomponentFactory(javax.inject.Provider provider) {
        this.builderProvider = provider;
    }

    public static SectionHeaderControllerSubcomponent providesFavoriteHeaderSubcomponent(javax.inject.Provider provider) {
        int i = NotificationSectionHeadersModule.$r8$clinit;
        DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentBuilder sectionHeaderControllerSubcomponentBuilder = (DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentBuilder) provider.get();
        sectionHeaderControllerSubcomponentBuilder.getClass();
        sectionHeaderControllerSubcomponentBuilder.nodeLabel = "favorite header";
        sectionHeaderControllerSubcomponentBuilder.headerText = Integer.valueOf(R.string.notification_section_header_favorite);
        sectionHeaderControllerSubcomponentBuilder.clickIntentAction = "android.settings.NOTIFICATION_SETTINGS";
        SectionHeaderControllerSubcomponent build = sectionHeaderControllerSubcomponentBuilder.build();
        Preconditions.checkNotNullFromProvides(build);
        return build;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesFavoriteHeaderSubcomponent(this.builderProvider);
    }
}
