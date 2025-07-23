package com.android.systemui.util.dagger;

import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.RingerModeTrackerImpl;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepositoryImpl;
import com.android.systemui.util.icons.AppCategoryIconProvider;
import com.android.systemui.util.icons.AppCategoryIconProviderImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface UtilModule {
    AppCategoryIconProvider appCategoryIconProvider(AppCategoryIconProviderImpl appCategoryIconProviderImpl);

    AnimationStatusRepository provideAnimationStatus(AnimationStatusRepositoryImpl animationStatusRepositoryImpl);

    RingerModeTracker provideRingerModeTracker(RingerModeTrackerImpl ringerModeTrackerImpl);
}
