package com.android.systemui.util.dagger;

import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.RingerModeTrackerImpl;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepositoryImpl;

public interface UtilModule {
    AnimationStatusRepository provideAnimationStatus(AnimationStatusRepositoryImpl animationStatusRepositoryImpl);

    RingerModeTracker provideRingerModeTracker(RingerModeTrackerImpl ringerModeTrackerImpl);
}
