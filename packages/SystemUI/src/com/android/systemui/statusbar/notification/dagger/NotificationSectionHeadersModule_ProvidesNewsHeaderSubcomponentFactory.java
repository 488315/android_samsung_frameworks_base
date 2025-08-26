package com.android.systemui.statusbar.notification.dagger;

import android.R;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class NotificationSectionHeadersModule_ProvidesNewsHeaderSubcomponentFactory implements Provider {
    public final Provider builderProvider;

    public NotificationSectionHeadersModule_ProvidesNewsHeaderSubcomponentFactory(Provider provider) {
        this.builderProvider = provider;
    }

    public static DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentImpl providesNewsHeaderSubcomponent(javax.inject.Provider provider) {
        int i = NotificationSectionHeadersModule.$r8$clinit;
        DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentBuilder sectionHeaderControllerSubcomponentBuilder = (DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentBuilder) provider.get();
        sectionHeaderControllerSubcomponentBuilder.getClass();
        sectionHeaderControllerSubcomponentBuilder.nodeLabel = "news header";
        sectionHeaderControllerSubcomponentBuilder.headerText = Integer.valueOf(R.string.sipAddressTypeCustom);
        sectionHeaderControllerSubcomponentBuilder.clickIntentAction = "android.settings.NOTIFICATION_SETTINGS";
        return (DaggerReferenceGlobalRootComponent.SectionHeaderControllerSubcomponentImpl) sectionHeaderControllerSubcomponentBuilder.build();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesNewsHeaderSubcomponent(this.builderProvider);
    }
}
