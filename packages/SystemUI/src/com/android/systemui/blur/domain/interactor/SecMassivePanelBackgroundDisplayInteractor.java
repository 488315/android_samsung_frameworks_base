package com.android.systemui.blur.domain.interactor;

import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes.dex */
public final class SecMassivePanelBackgroundDisplayInteractor implements SecPanelBackgroundDisplayInteractor {
    public final ReadonlyStateFlow shouldShow;

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

    public SecMassivePanelBackgroundDisplayInteractor(CoroutineScope coroutineScope, SecBlurSettingsInteractor secBlurSettingsInteractor, SecBlurCustomColorInteractor secBlurCustomColorInteractor, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        this.shouldShow = FlowKt.stateIn(FlowKt.combine(secBlurSettingsInteractor.blurReduced, secBlurSettingsInteractor.minimalBatteryUse, secBlurCustomColorInteractor.hasCustomColorApplied, new SecMassivePanelBackgroundDisplayInteractor$shouldShow$1(secQsUiDisplayModeInteractor, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.valueOf(secQsUiDisplayModeInteractor.isTablet()));
    }

    @Override // com.android.systemui.blur.domain.interactor.SecPanelBackgroundDisplayInteractor
    public final StateFlow getShouldShow() {
        return this.shouldShow;
    }
}
