package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.R;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory implements Provider {
    public final Provider builderProvider;

    public NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory(Provider provider) {
        this.builderProvider = provider;
    }

    public static DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentImpl providesPeopleHeaderSubcomponent(javax.inject.Provider provider) {
        int i = NotificationSectionHeadersModule.$r8$clinit;
        DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentBuilder sectionHeaderControllerSubcomponentBuilder = (DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentBuilder) provider.get();
        sectionHeaderControllerSubcomponentBuilder.getClass();
        sectionHeaderControllerSubcomponentBuilder.nodeLabel = "people header";
        sectionHeaderControllerSubcomponentBuilder.headerText = Integer.valueOf(R.string.notification_section_header_conversations);
        sectionHeaderControllerSubcomponentBuilder.clickIntentAction = "android.settings.CONVERSATION_SETTINGS";
        return (DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentImpl) sectionHeaderControllerSubcomponentBuilder.build();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesPeopleHeaderSubcomponent(this.builderProvider);
    }
}
