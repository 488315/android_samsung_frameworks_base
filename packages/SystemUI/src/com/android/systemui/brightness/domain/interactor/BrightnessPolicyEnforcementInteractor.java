package com.android.systemui.brightness.domain.interactor;

import com.android.systemui.brightness.data.repository.BrightnessPolicyRepository;
import com.android.systemui.brightness.data.repository.BrightnessPolicyRepositoryImpl;
import com.android.systemui.plugins.ActivityStarter;
import kotlinx.coroutines.flow.Flow;

public final class BrightnessPolicyEnforcementInteractor {
    public final ActivityStarter activityStarter;
    public final Flow brightnessPolicyRestriction;

    public BrightnessPolicyEnforcementInteractor(BrightnessPolicyRepository brightnessPolicyRepository, ActivityStarter activityStarter) {
        this.activityStarter = activityStarter;
        this.brightnessPolicyRestriction = ((BrightnessPolicyRepositoryImpl) brightnessPolicyRepository).restrictionPolicy;
    }
}
