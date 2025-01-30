package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.R;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesAlertingHeaderSubcomponentFactory */
/* loaded from: classes2.dex */
public final class C2847x3fd4641 implements Provider {
    public final Provider builderProvider;

    public C2847x3fd4641(Provider provider) {
        this.builderProvider = provider;
    }

    public static SectionHeaderControllerSubcomponent providesAlertingHeaderSubcomponent(Provider provider) {
        int i = NotificationSectionHeadersModule.$r8$clinit;
        SectionHeaderControllerSubcomponent build = ((SectionHeaderControllerSubcomponent.Builder) provider.get()).nodeLabel("alerting header").headerText(R.string.notification_section_header_alerting).clickIntentAction("android.settings.NOTIFICATION_SETTINGS").build();
        Preconditions.checkNotNullFromProvides(build);
        return build;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesAlertingHeaderSubcomponent(this.builderProvider);
    }
}
