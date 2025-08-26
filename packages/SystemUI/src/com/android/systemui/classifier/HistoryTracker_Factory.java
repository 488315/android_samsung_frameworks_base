package com.android.systemui.classifier;

import com.android.systemui.util.time.SystemClock;
import dagger.internal.Provider;

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
