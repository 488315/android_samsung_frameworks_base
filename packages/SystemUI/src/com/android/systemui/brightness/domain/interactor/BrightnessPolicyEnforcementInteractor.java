package com.android.systemui.brightness.domain.interactor;

import com.android.systemui.brightness.data.repository.BrightnessPolicyRepository;
import com.android.systemui.brightness.data.repository.BrightnessPolicyRepositoryImpl;
import com.android.systemui.plugins.ActivityStarter;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BrightnessPolicyEnforcementInteractor {
    public final ActivityStarter activityStarter;
    public final Flow brightnessPolicyRestriction;

    public BrightnessPolicyEnforcementInteractor(BrightnessPolicyRepository brightnessPolicyRepository, ActivityStarter activityStarter) {
        this.activityStarter = activityStarter;
        this.brightnessPolicyRestriction = ((BrightnessPolicyRepositoryImpl) brightnessPolicyRepository).restrictionPolicy;
    }
}
