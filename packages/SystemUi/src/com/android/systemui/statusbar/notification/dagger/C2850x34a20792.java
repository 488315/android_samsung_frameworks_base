package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.R;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesSilentHeaderSubcomponentFactory */
/* loaded from: classes2.dex */
public final class C2850x34a20792 implements Provider {
    public final Provider builderProvider;

    public C2850x34a20792(Provider provider) {
        this.builderProvider = provider;
    }

    public static SectionHeaderControllerSubcomponent providesSilentHeaderSubcomponent(Provider provider) {
        int i = NotificationSectionHeadersModule.$r8$clinit;
        SectionHeaderControllerSubcomponent build = ((SectionHeaderControllerSubcomponent.Builder) provider.get()).nodeLabel("silent header").headerText(R.string.notification_section_header_gentle).clickIntentAction("android.settings.NOTIFICATION_SETTINGS").build();
        Preconditions.checkNotNullFromProvides(build);
        return build;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesSilentHeaderSubcomponent(this.builderProvider);
    }
}
