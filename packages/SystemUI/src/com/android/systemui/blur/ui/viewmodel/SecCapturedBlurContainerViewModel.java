package com.android.systemui.blur.ui.viewmodel;

import com.android.systemui.blur.domain.interactor.SecBlurSettingsInteractor;
import com.android.systemui.blur.domain.interactor.SecCapturedBlurInteractor;
import com.android.systemui.blur.domain.interactor.SecPanelBackgroundCommonInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes.dex */
public final class SecCapturedBlurContainerViewModel {
    public final ReadonlyStateFlow bouncerShowing;
    public final ReadonlyStateFlow fullScreenBlurShowing;
    public final ChannelFlowTransformLatest requestCaptureBlur;
    public final SecCapturedBlurInteractor secCapturedBlurInteractor;
    public final ReadonlyStateFlow shouldBeGone;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        SecCapturedBlurContainerViewModel create();
    }

    static {
        new Companion(null);
    }

    public SecCapturedBlurContainerViewModel(CoroutineScope coroutineScope, SecCapturedBlurInteractor secCapturedBlurInteractor, SecPanelBackgroundCommonInteractor secPanelBackgroundCommonInteractor, SecBlurSettingsInteractor secBlurSettingsInteractor, PowerInteractor powerInteractor, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this.secCapturedBlurInteractor = secCapturedBlurInteractor;
        ReadonlyStateFlow readonlyStateFlow = primaryBouncerInteractor.isShowing;
        this.bouncerShowing = readonlyStateFlow;
        ReadonlyStateFlow readonlyStateFlow2 = secCapturedBlurInteractor.fullScreenBlurShowing;
        this.fullScreenBlurShowing = readonlyStateFlow2;
        this.requestCaptureBlur = FlowKt.transformLatest(secCapturedBlurInteractor.requestCaptureBlur, new SecCapturedBlurContainerViewModel$requestCaptureBlur$1(powerInteractor, this, null));
        this.shouldBeGone = FlowKt.stateIn(FlowKt.combine(readonlyStateFlow, readonlyStateFlow2, secPanelBackgroundCommonInteractor.maxAlpha, secBlurSettingsInteractor.blurReduced, new SecCapturedBlurContainerViewModel$shouldBeGone$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.TRUE);
    }
}
