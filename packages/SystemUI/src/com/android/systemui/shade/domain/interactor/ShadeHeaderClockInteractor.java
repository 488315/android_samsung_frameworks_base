package com.android.systemui.shade.domain.interactor;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.data.repository.ShadeHeaderClockRepository;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShadeHeaderClockInteractor {
    public final ActivityStarter activityStarter;
    public final ShadeHeaderClockRepository repository;

    public ShadeHeaderClockInteractor(ShadeHeaderClockRepository shadeHeaderClockRepository, ActivityStarter activityStarter) {
        this.activityStarter = activityStarter;
    }
}
