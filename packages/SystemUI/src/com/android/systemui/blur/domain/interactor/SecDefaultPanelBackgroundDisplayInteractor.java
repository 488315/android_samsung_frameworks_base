package com.android.systemui.blur.domain.interactor;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.shade.domain.interactor.SecNotificationShadeWindowStateInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecDefaultPanelBackgroundDisplayInteractor implements SecPanelBackgroundDisplayInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
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

    public SecDefaultPanelBackgroundDisplayInteractor(CoroutineScope coroutineScope, SecBlurCustomColorInteractor secBlurCustomColorInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, SecNotificationShadeWindowStateInteractor secNotificationShadeWindowStateInteractor, SecBlurSettingsInteractor secBlurSettingsInteractor) {
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 combine = FlowKt.combine(secBlurSettingsInteractor.blurReduced, secBlurSettingsInteractor.minimalBatteryUse, secNotificationShadeWindowStateInteractor.statusBarState, keyguardTransitionInteractor.currentKeyguardState, secBlurCustomColorInteractor.hasCustomColorApplied, new SecDefaultPanelBackgroundDisplayInteractor$shouldShow$1(this, null));
        SharingStarted.Companion.getClass();
        this.shouldShow = FlowKt.stateIn(combine, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }

    @Override // com.android.systemui.blur.domain.interactor.SecPanelBackgroundDisplayInteractor
    public final StateFlow getShouldShow() {
        return this.shouldShow;
    }
}
