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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecCapturedBlurContainerViewModel {
    public final ReadonlyStateFlow fullScreenBlurShowing;
    public final ChannelFlowTransformLatest requestCaptureBlur;
    public final SecCapturedBlurInteractor secCapturedBlurInteractor;
    public final ReadonlyStateFlow shouldBeGone;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        SecCapturedBlurContainerViewModel create();
    }

    static {
        new Companion(null);
    }

    public SecCapturedBlurContainerViewModel(CoroutineScope coroutineScope, SecCapturedBlurInteractor secCapturedBlurInteractor, SecPanelBackgroundCommonInteractor secPanelBackgroundCommonInteractor, SecBlurSettingsInteractor secBlurSettingsInteractor, PowerInteractor powerInteractor, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this.secCapturedBlurInteractor = secCapturedBlurInteractor;
        ReadonlyStateFlow readonlyStateFlow = secCapturedBlurInteractor.fullScreenBlurShowing;
        this.fullScreenBlurShowing = readonlyStateFlow;
        this.requestCaptureBlur = FlowKt.transformLatest(secCapturedBlurInteractor.requestCaptureBlur, new SecCapturedBlurContainerViewModel$requestCaptureBlur$1(powerInteractor, primaryBouncerInteractor, null));
        this.shouldBeGone = FlowKt.stateIn(FlowKt.combine(primaryBouncerInteractor.isShowing, readonlyStateFlow, secPanelBackgroundCommonInteractor.maxAlpha, secBlurSettingsInteractor.blurReduced, new SecCapturedBlurContainerViewModel$shouldBeGone$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.TRUE);
    }
}
