package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.R;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory implements Provider {
    public final javax.inject.Provider builderProvider;

    public NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory(javax.inject.Provider provider) {
        this.builderProvider = provider;
    }

    public static SectionHeaderControllerSubcomponent providesPeopleHeaderSubcomponent(javax.inject.Provider provider) {
        int i = NotificationSectionHeadersModule.$r8$clinit;
        DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentBuilder sectionHeaderControllerSubcomponentBuilder = (DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentBuilder) provider.get();
        sectionHeaderControllerSubcomponentBuilder.getClass();
        sectionHeaderControllerSubcomponentBuilder.nodeLabel = "people header";
        sectionHeaderControllerSubcomponentBuilder.headerText = Integer.valueOf(R.string.notification_section_header_conversations);
        sectionHeaderControllerSubcomponentBuilder.clickIntentAction = "android.settings.CONVERSATION_SETTINGS";
        SectionHeaderControllerSubcomponent build = sectionHeaderControllerSubcomponentBuilder.build();
        Preconditions.checkNotNullFromProvides(build);
        return build;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesPeopleHeaderSubcomponent(this.builderProvider);
    }
}
