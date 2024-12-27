package com.android.systemui.dreams.ui.viewmodel;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToDreamingTransitionViewModel;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class DreamViewModel extends FlowDumperImpl {
    public final CommunalInteractor communalInteractor;
    public final Flow dreamAlpha;
    public final Flow dreamOverlayAlpha;
    public final Flow dreamOverlayTranslationX;
    public final ChannelFlowTransformLatest dreamOverlayTranslationY;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final DreamingToLockscreenTransitionViewModel toLockscreenTransitionViewModel;
    public final DreamViewModel$special$$inlined$filter$1 transitionEnded;
    public final UserTracker userTracker;

    public DreamViewModel(ConfigurationInteractor configurationInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, GlanceableHubToDreamingTransitionViewModel glanceableHubToDreamingTransitionViewModel, DreamingToGlanceableHubTransitionViewModel dreamingToGlanceableHubTransitionViewModel, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, CommunalInteractor communalInteractor, KeyguardUpdateMonitor keyguardUpdateMonitor, UserTracker userTracker, DumpManager dumpManager) {
        super(dumpManager, null, 2, null);
        this.toLockscreenTransitionViewModel = dreamingToLockscreenTransitionViewModel;
        this.communalInteractor = communalInteractor;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.userTracker = userTracker;
        this.dreamOverlayTranslationX = FlowKt.distinctUntilChanged(FlowKt.merge(dreamingToGlanceableHubTransitionViewModel.dreamOverlayTranslationX, glanceableHubToDreamingTransitionViewModel.dreamOverlayTranslationX));
        this.dreamOverlayTranslationY = FlowKt.transformLatest(configurationInteractor.dimensionPixelSize(R.dimen.dream_overlay_exit_y_offset), new DreamViewModel$special$$inlined$flatMapLatest$1(null, this));
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 = dreamingToLockscreenTransitionViewModel.dreamOverlayAlpha;
        this.dreamAlpha = dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.merge(keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1, dreamingToGlanceableHubTransitionViewModel.dreamAlpha)), "dreamAlpha");
        this.dreamOverlayAlpha = FlowKt.distinctUntilChanged(FlowKt.merge(keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1, dreamingToGlanceableHubTransitionViewModel.dreamOverlayAlpha, glanceableHubToDreamingTransitionViewModel.dreamOverlayAlpha));
        this.transitionEnded = new DreamViewModel$special$$inlined$filter$1(keyguardTransitionInteractor.transition((Edge) Edge.Companion.create$default(Edge.Companion, KeyguardState.DREAMING, null, 2)));
    }

    public final void startTransitionFromDream() {
        CommunalInteractor communalInteractor = this.communalInteractor;
        if (((Boolean) communalInteractor.isCommunalEnabled.$$delegate_0.getValue()).booleanValue()) {
            if (!this.keyguardUpdateMonitor.isEncryptedOrLockdown(((UserTrackerImpl) this.userTracker).getUserId())) {
                Flags.FEATURE_FLAGS.getClass();
                ((CommunalSceneRepositoryImpl) communalInteractor.communalSceneInteractor.communalSceneRepository).changeScene(CommunalScenes.Communal, null);
                return;
            }
        }
        this.toLockscreenTransitionViewModel.fromDreamingTransitionInteractor.startToLockscreenTransition();
    }
}
