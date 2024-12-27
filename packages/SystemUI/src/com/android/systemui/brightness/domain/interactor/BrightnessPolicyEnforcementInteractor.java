package com.android.systemui.brightness.domain.interactor;

import com.android.systemui.brightness.data.repository.BrightnessPolicyRepository;
import com.android.systemui.brightness.data.repository.BrightnessPolicyRepositoryImpl;
import com.android.systemui.plugins.ActivityStarter;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BrightnessPolicyEnforcementInteractor {
    public final ActivityStarter activityStarter;
    public final Flow brightnessPolicyRestriction;

    public BrightnessPolicyEnforcementInteractor(BrightnessPolicyRepository brightnessPolicyRepository, ActivityStarter activityStarter) {
        this.activityStarter = activityStarter;
        this.brightnessPolicyRestriction = ((BrightnessPolicyRepositoryImpl) brightnessPolicyRepository).restrictionPolicy;
    }
}
