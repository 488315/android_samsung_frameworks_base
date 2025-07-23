package com.android.systemui.common.usagestats.domain;

import com.android.systemui.common.usagestats.data.repository.UsageStatsRepository;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.time.SystemClock;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class UsageStatsInteractor {
    public final UsageStatsRepository repository;
    public final SystemClock systemClock;
    public final UserTracker userTracker;

    public UsageStatsInteractor(UserTracker userTracker, UsageStatsRepository usageStatsRepository, SystemClock systemClock) {
        this.userTracker = userTracker;
        this.repository = usageStatsRepository;
        this.systemClock = systemClock;
    }
}
