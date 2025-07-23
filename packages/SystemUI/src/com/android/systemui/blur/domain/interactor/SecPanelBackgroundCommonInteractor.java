package com.android.systemui.blur.domain.interactor;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecPanelBackgroundCommonInteractor {
    public final ReadonlyStateFlow maxAlpha;
    public final SharedFlowImpl updateBackgroundColor;

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

    public SecPanelBackgroundCommonInteractor(CoroutineScope coroutineScope, SecBlurSettingsInteractor secBlurSettingsInteractor, SecBlurCustomColorInteractor secBlurCustomColorInteractor) {
        this.maxAlpha = FlowKt.stateIn(FlowKt.combine(secBlurSettingsInteractor.blurReduced, secBlurCustomColorInteractor.hasCustomColorApplied, secBlurCustomColorInteractor.configurationChanged, new SecPanelBackgroundCommonInteractor$maxAlpha$1(secBlurCustomColorInteractor, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Float.valueOf(0.3f));
        this.updateBackgroundColor = secBlurCustomColorInteractor.updateBackgroundColor;
    }
}
