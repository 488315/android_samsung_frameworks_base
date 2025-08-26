package com.android.systemui.blur.domain.interactor;

import com.android.systemui.QpRune;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes.dex */
public final class SecPanelBackgroundCommonInteractor {
    public final ReadonlyStateFlow maxAlpha;
    public final SharedFlowImpl updateBackgroundColor;

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

    public SecPanelBackgroundCommonInteractor(CoroutineScope coroutineScope, SecBlurSettingsInteractor secBlurSettingsInteractor, SecBlurCustomColorInteractor secBlurCustomColorInteractor, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        this.maxAlpha = FlowKt.stateIn(FlowKt.combine(secBlurSettingsInteractor.blurReduced, secBlurCustomColorInteractor.hasCustomColorApplied, secBlurCustomColorInteractor.configurationChanged, new SecPanelBackgroundCommonInteractor$maxAlpha$1(secQsUiDisplayModeInteractor, secBlurCustomColorInteractor, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Float.valueOf((QpRune.QUICK_PANEL_BLUR_MASSIVE && secQsUiDisplayModeInteractor.isTablet()) ? 0.9f : 0.3f));
        this.updateBackgroundColor = secBlurCustomColorInteractor.updateBackgroundColor;
    }
}
