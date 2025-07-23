package com.android.systemui.dreams.ui.viewmodel;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToDreamingTransitionViewModel;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DreamViewModel extends FlowDumperImpl {
    public final CommunalInteractor communalInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final Flow dreamAlpha;
    public final Flow dreamOverlayAlpha;
    public final Flow dreamOverlayTranslationX;
    public final ChannelFlowTransformLatest dreamOverlayTranslationY;
    public final FromDreamingTransitionInteractor fromDreamingTransitionInteractor;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final DreamingToLockscreenTransitionViewModel toLockscreenTransitionViewModel;
    public final DreamViewModel$special$$inlined$filter$1 transitionEnded;
    public final UserTracker userTracker;

    public DreamViewModel(ConfigurationInteractor configurationInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, GlanceableHubToDreamingTransitionViewModel glanceableHubToDreamingTransitionViewModel, DreamingToGlanceableHubTransitionViewModel dreamingToGlanceableHubTransitionViewModel, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, FromDreamingTransitionInteractor fromDreamingTransitionInteractor, CommunalInteractor communalInteractor, CommunalSettingsInteractor communalSettingsInteractor, KeyguardUpdateMonitor keyguardUpdateMonitor, UserTracker userTracker, DumpManager dumpManager) {
        super(dumpManager, null, 2, null);
        this.toLockscreenTransitionViewModel = dreamingToLockscreenTransitionViewModel;
        this.fromDreamingTransitionInteractor = fromDreamingTransitionInteractor;
        this.communalInteractor = communalInteractor;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.userTracker = userTracker;
        this.dreamOverlayTranslationX = FlowKt.distinctUntilChanged(FlowKt.merge(dreamingToGlanceableHubTransitionViewModel.dreamOverlayTranslationX, glanceableHubToDreamingTransitionViewModel.dreamOverlayTranslationX));
        this.dreamOverlayTranslationY = FlowKt.transformLatest(((ConfigurationInteractorImpl) configurationInteractor).dimensionPixelSize(R.dimen.dream_overlay_exit_y_offset), new DreamViewModel$special$$inlined$flatMapLatest$1(null, this));
        this.dreamAlpha = dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.merge(dreamingToLockscreenTransitionViewModel.dreamOverlayAlpha, dreamingToGlanceableHubTransitionViewModel.dreamAlpha)), "dreamAlpha");
        this.dreamOverlayAlpha = FlowKt.distinctUntilChanged(FlowKt.merge(dreamingToLockscreenTransitionViewModel.dreamOverlayAlpha, dreamingToGlanceableHubTransitionViewModel.dreamOverlayAlpha, glanceableHubToDreamingTransitionViewModel.dreamOverlayAlpha));
        this.transitionEnded = new DreamViewModel$special$$inlined$filter$1(keyguardTransitionInteractor.transition(Edge.Companion.create$default(Edge.Companion, KeyguardState.DREAMING, null, 2)));
    }

    public final void startTransitionFromDream() {
        boolean z;
        this.communalSettingsInteractor.isV2FlagEnabled();
        if (((Boolean) this.communalInteractor.isCommunalEnabled.$$delegate_0.getValue()).booleanValue()) {
            if (!this.keyguardUpdateMonitor.isEncryptedOrLockdown(((UserTrackerImpl) this.userTracker).getUserId())) {
                z = true;
                this.fromDreamingTransitionInteractor.startToLockscreenOrGlanceableHubTransition(z);
            }
        }
        z = false;
        this.fromDreamingTransitionInteractor.startToLockscreenOrGlanceableHubTransition(z);
    }
}
