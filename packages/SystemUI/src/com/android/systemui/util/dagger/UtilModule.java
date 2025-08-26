package com.android.systemui.util.dagger;

import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.RingerModeTrackerImpl;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepositoryImpl;
import com.android.systemui.util.icons.AppCategoryIconProvider;
import com.android.systemui.util.icons.AppCategoryIconProviderImpl;

/* loaded from: classes3.dex */
public interface UtilModule {
    AppCategoryIconProvider appCategoryIconProvider(AppCategoryIconProviderImpl appCategoryIconProviderImpl);

    AnimationStatusRepository provideAnimationStatus(AnimationStatusRepositoryImpl animationStatusRepositoryImpl);

    RingerModeTracker provideRingerModeTracker(RingerModeTrackerImpl ringerModeTrackerImpl);
}
