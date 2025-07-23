package com.android.systemui.blur.domain.interactor;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecMassivePanelBackgroundDisplayInteractor implements SecPanelBackgroundDisplayInteractor {
    public final ReadonlyStateFlow shouldShow;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SecMassivePanelBackgroundDisplayInteractor(CoroutineScope coroutineScope, SecBlurSettingsInteractor secBlurSettingsInteractor, SecBlurCustomColorInteractor secBlurCustomColorInteractor) {
        this.shouldShow = FlowKt.stateIn(FlowKt.combine(secBlurSettingsInteractor.blurReduced, secBlurSettingsInteractor.minimalBatteryUse, secBlurCustomColorInteractor.hasCustomColorApplied, new SecMassivePanelBackgroundDisplayInteractor$shouldShow$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
    }

    @Override // com.android.systemui.blur.domain.interactor.SecPanelBackgroundDisplayInteractor
    public final StateFlow getShouldShow() {
        return this.shouldShow;
    }
}
