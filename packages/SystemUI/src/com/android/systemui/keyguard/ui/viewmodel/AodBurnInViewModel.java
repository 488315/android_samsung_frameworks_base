package com.android.systemui.keyguard.ui.viewmodel;

import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.keyguard.domain.interactor.BurnInInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class AodBurnInViewModel {
    public final String TAG = "AodBurnInViewModel";
    public final AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel;
    public final BurnInInteractor burnInInteractor;
    public final ConfigurationInteractor configurationInteractor;
    public final GoneToAodTransitionViewModel goneToAodTransitionViewModel;
    public final KeyguardClockViewModel keyguardClockViewModel;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel;

    public AodBurnInViewModel(BurnInInteractor burnInInteractor, ConfigurationInteractor configurationInteractor, KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, GoneToAodTransitionViewModel goneToAodTransitionViewModel, AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel, OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel, KeyguardClockViewModel keyguardClockViewModel) {
        this.burnInInteractor = burnInInteractor;
        this.configurationInteractor = configurationInteractor;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.goneToAodTransitionViewModel = goneToAodTransitionViewModel;
        this.aodToLockscreenTransitionViewModel = aodToLockscreenTransitionViewModel;
        this.occludedToLockscreenTransitionViewModel = occludedToLockscreenTransitionViewModel;
        this.keyguardClockViewModel = keyguardClockViewModel;
    }

    public final Flow movement(BurnInParameters burnInParameters) {
        int i = burnInParameters.minViewY;
        int i2 = burnInParameters.topInset;
        if (i < i2) {
            Log.w(this.TAG, "minViewY is below topInset: " + burnInParameters);
            burnInParameters = BurnInParameters.copy$default(burnInParameters, 0, i2, null, 5);
        }
        return FlowKt.distinctUntilChanged(FlowKt.transformLatest(this.configurationInteractor.dimensionPixelSize(R.dimen.keyguard_enter_from_top_translation_y), new AodBurnInViewModel$movement$$inlined$flatMapLatest$1(null, this, burnInParameters)));
    }
}
