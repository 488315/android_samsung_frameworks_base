package com.android.systemui.communal.domain.interactor;

import android.R;
import android.content.res.Resources;
import com.android.systemui.communal.data.repository.CommunalSettingsRepository;
import com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl;
import com.android.systemui.settings.UserTrackerImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
public final class HubOnboardingInteractor {
    public final CommunalPrefsInteractor communalPrefsInteractor;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 shouldShowHubOnboarding;

    public HubOnboardingInteractor(CommunalSceneInteractor communalSceneInteractor, CommunalSettingsRepository communalSettingsRepository, CommunalPrefsInteractor communalPrefsInteractor) throws Resources.NotFoundException {
        this.communalPrefsInteractor = communalPrefsInteractor;
        ((CommunalSettingsRepositoryImpl) communalSettingsRepository).resources.getBoolean(R.bool.config_letterboxIsEducationEnabled);
        this.shouldShowHubOnboarding = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
    }

    public final void setHubOnboardingDismissed() {
        CommunalPrefsInteractor communalPrefsInteractor = this.communalPrefsInteractor;
        BuildersKt.launch$default(communalPrefsInteractor.bgScope, null, null, new CommunalPrefsInteractor$setHubOnboardingDismissed$1(communalPrefsInteractor, ((UserTrackerImpl) communalPrefsInteractor.userTracker).getUserInfo(), null), 3);
    }
}
