package com.android.systemui.util.dagger;

import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.RingerModeTrackerImpl;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepositoryImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface UtilModule {
    AnimationStatusRepository provideAnimationStatus(AnimationStatusRepositoryImpl animationStatusRepositoryImpl);

    RingerModeTracker provideRingerModeTracker(RingerModeTrackerImpl ringerModeTrackerImpl);
}
