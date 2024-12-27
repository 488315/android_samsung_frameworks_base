package com.android.systemui.shade.domain.interactor;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.data.repository.ShadeHeaderClockRepository;

public final class ShadeHeaderClockInteractor {
    public final ActivityStarter activityStarter;
    public final ShadeHeaderClockRepository repository;

    public ShadeHeaderClockInteractor(ShadeHeaderClockRepository shadeHeaderClockRepository, ActivityStarter activityStarter) {
        this.activityStarter = activityStarter;
    }
}
