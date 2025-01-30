package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.R;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory */
/* loaded from: classes2.dex */
public final class C2849x39c1fe98 implements Provider {
    public final Provider builderProvider;

    public C2849x39c1fe98(Provider provider) {
        this.builderProvider = provider;
    }

    public static SectionHeaderControllerSubcomponent providesPeopleHeaderSubcomponent(Provider provider) {
        int i = NotificationSectionHeadersModule.$r8$clinit;
        SectionHeaderControllerSubcomponent build = ((SectionHeaderControllerSubcomponent.Builder) provider.get()).nodeLabel("people header").headerText(R.string.notification_section_header_conversations).clickIntentAction("android.settings.CONVERSATION_SETTINGS").build();
        Preconditions.checkNotNullFromProvides(build);
        return build;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesPeopleHeaderSubcomponent(this.builderProvider);
    }
}
