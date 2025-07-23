package com.android.systemui.statusbar.notification.collection.provider;

import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SectionStyleProvider {
    public final HighPriorityProvider highPriorityProvider;
    public Set lowPrioritySections;
    public Set silentSections;

    public SectionStyleProvider(HighPriorityProvider highPriorityProvider) {
        this.highPriorityProvider = highPriorityProvider;
    }
}
