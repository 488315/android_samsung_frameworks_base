package com.android.systemui.statusbar.notification.collection.provider;

import java.util.Set;

public final class SectionStyleProvider {
    public final HighPriorityProvider highPriorityProvider;
    public Set lowPrioritySections;
    public Set silentSections;

    public SectionStyleProvider(HighPriorityProvider highPriorityProvider) {
    }
}
