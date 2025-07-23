package com.android.systemui.blur.domain.interactor;

import com.android.systemui.blur.data.repository.SecPanelWindowBlurRepository;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.shade.domain.interactor.SecNotificationShadeWindowStateInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecPanelWindowBlurInteractor {
    public static final String TAG;
    public final StateFlow backgroundVisible;
    public final SharedFlowImpl blurInfoData = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
    public final SecBlurSettingsInteractor secBlurSettingsInteractor;
    public final SecPanelWindowBlurRepository secPanelWindowBlurRepository;
    public final ReadonlyStateFlow shouldBlockWindowBlur;
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
        TAG = Reflection.getOrCreateKotlinClass(SecPanelWindowBlurInteractor.class).getSimpleName();
    }

    public SecPanelWindowBlurInteractor(CoroutineScope coroutineScope, SecPanelWindowBlurRepository secPanelWindowBlurRepository, SecBlurSettingsInteractor secBlurSettingsInteractor, SecPanelBackgroundCommonInteractor secPanelBackgroundCommonInteractor, SecPanelBackgroundDisplayInteractor secPanelBackgroundDisplayInteractor, SecBlurSettingsInteractor secBlurSettingsInteractor2, SecNotificationShadeWindowStateInteractor secNotificationShadeWindowStateInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        this.secPanelWindowBlurRepository = secPanelWindowBlurRepository;
        this.secBlurSettingsInteractor = secBlurSettingsInteractor;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(secBlurSettingsInteractor2.minimalBatteryUse, secNotificationShadeWindowStateInteractor.statusBarState, keyguardTransitionInteractor.currentKeyguardState, new SecPanelWindowBlurInteractor$shouldShow$1(this, null));
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3);
        Boolean bool = Boolean.FALSE;
        this.shouldShow = FlowKt.stateIn(combine, coroutineScope, WhileSubscribed$default, bool);
        this.shouldBlockWindowBlur = FlowKt.stateIn(FlowKt.combine(secPanelBackgroundCommonInteractor.maxAlpha, secPanelBackgroundDisplayInteractor.getShouldShow(), secBlurSettingsInteractor.blurReduced, new SecPanelWindowBlurInteractor$shouldBlockWindowBlur$1(null)), coroutineScope, SharingStarted.Companion.Eagerly, bool);
        this.backgroundVisible = secPanelBackgroundDisplayInteractor.getShouldShow();
    }
}
