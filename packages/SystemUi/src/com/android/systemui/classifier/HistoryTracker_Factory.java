package com.android.systemui.classifier;

import com.android.systemui.util.time.SystemClock;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HistoryTracker_Factory implements Provider {
    public final Provider systemClockProvider;

    public HistoryTracker_Factory(Provider provider) {
        this.systemClockProvider = provider;
    }

    public static HistoryTracker newInstance(SystemClock systemClock) {
        return new HistoryTracker(systemClock);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new HistoryTracker((SystemClock) this.systemClockProvider.get());
    }
}
