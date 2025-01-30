package com.android.systemui.people.widget;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PeopleSpaceWidgetProvider_Factory implements Provider {
    public final Provider peopleSpaceWidgetManagerProvider;

    public PeopleSpaceWidgetProvider_Factory(Provider provider) {
        this.peopleSpaceWidgetManagerProvider = provider;
    }

    public static PeopleSpaceWidgetProvider newInstance(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        return new PeopleSpaceWidgetProvider(peopleSpaceWidgetManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PeopleSpaceWidgetProvider((PeopleSpaceWidgetManager) this.peopleSpaceWidgetManagerProvider.get());
    }
}
