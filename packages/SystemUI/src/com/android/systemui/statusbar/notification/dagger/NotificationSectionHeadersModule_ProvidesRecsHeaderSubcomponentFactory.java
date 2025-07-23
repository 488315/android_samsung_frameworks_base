package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationSectionHeadersModule_ProvidesRecsHeaderSubcomponentFactory implements Provider {
    public final Provider builderProvider;

    public NotificationSectionHeadersModule_ProvidesRecsHeaderSubcomponentFactory(Provider provider) {
        this.builderProvider = provider;
    }

    public static DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentImpl providesRecsHeaderSubcomponent(javax.inject.Provider provider) {
        int i = NotificationSectionHeadersModule.$r8$clinit;
        DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentBuilder sectionHeaderControllerSubcomponentBuilder = (DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentBuilder) provider.get();
        sectionHeaderControllerSubcomponentBuilder.getClass();
        sectionHeaderControllerSubcomponentBuilder.nodeLabel = "recs header";
        sectionHeaderControllerSubcomponentBuilder.headerText = 17042653;
        sectionHeaderControllerSubcomponentBuilder.clickIntentAction = "android.settings.NOTIFICATION_SETTINGS";
        return (DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentImpl) sectionHeaderControllerSubcomponentBuilder.build();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesRecsHeaderSubcomponent(this.builderProvider);
    }
}
