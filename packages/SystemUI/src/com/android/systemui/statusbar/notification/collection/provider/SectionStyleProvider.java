package com.android.systemui.statusbar.notification.collection.provider;

import java.util.Set;

/* loaded from: classes3.dex */
public final class SectionStyleProvider {
    public final HighPriorityProvider highPriorityProvider;
    public Set lowPrioritySections;
    public Set silentSections;

    public SectionStyleProvider(HighPriorityProvider highPriorityProvider) {
        this.highPriorityProvider = highPriorityProvider;
    }
}
