package com.android.systemui.blur.domain.interactor;

import android.util.Log;
import android.view.SemBlurInfo;
import com.android.systemui.blur.QSColorCurve;
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

/* loaded from: classes.dex */
public final class SecPanelWindowBlurInteractor {
    public static final String TAG;
    public final StateFlow backgroundVisible;
    public final SharedFlowImpl blurInfoData = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
    public final SecBlurSettingsInteractor secBlurSettingsInteractor;
    public final SecPanelWindowBlurRepository secPanelWindowBlurRepository;
    public final ReadonlyStateFlow shouldBlockWindowBlur;
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
        TAG = Reflection.getOrCreateKotlinClass(SecPanelWindowBlurInteractor.class).getSimpleName();
    }

    public SecPanelWindowBlurInteractor(CoroutineScope coroutineScope, SecPanelWindowBlurRepository secPanelWindowBlurRepository, SecBlurSettingsInteractor secBlurSettingsInteractor, SecPanelBackgroundCommonInteractor secPanelBackgroundCommonInteractor, SecPanelBackgroundDisplayInteractor secPanelBackgroundDisplayInteractor, SecBlurSettingsInteractor secBlurSettingsInteractor2, SecNotificationShadeWindowStateInteractor secNotificationShadeWindowStateInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        this.secPanelWindowBlurRepository = secPanelWindowBlurRepository;
        this.secBlurSettingsInteractor = secBlurSettingsInteractor;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine = FlowKt.combine(secBlurSettingsInteractor2.minimalBatteryUse, secNotificationShadeWindowStateInteractor.statusBarState, keyguardTransitionInteractor.currentKeyguardState, new SecPanelWindowBlurInteractor$shouldShow$1(this, null));
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3);
        Boolean bool = Boolean.FALSE;
        this.shouldShow = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine, coroutineScope, startedWhileSubscribedWhileSubscribed$default, bool);
        this.shouldBlockWindowBlur = FlowKt.stateIn(FlowKt.combine(secPanelBackgroundCommonInteractor.maxAlpha, secPanelBackgroundDisplayInteractor.getShouldShow(), secBlurSettingsInteractor.blurReduced, new SecPanelWindowBlurInteractor$shouldBlockWindowBlur$1(null)), coroutineScope, SharingStarted.Companion.Eagerly, bool);
        this.backgroundVisible = secPanelBackgroundDisplayInteractor.getShouldShow();
    }

    public final SemBlurInfo.Builder getSemBlurInfoBuilder(int i) {
        SemBlurInfo.Builder builder = new SemBlurInfo.Builder(0);
        StateFlow stateFlow = this.backgroundVisible;
        boolean zBooleanValue = ((Boolean) stateFlow.getValue()).booleanValue();
        SecPanelWindowBlurRepository secPanelWindowBlurRepository = this.secPanelWindowBlurRepository;
        if (!zBooleanValue) {
            QSColorCurve qSColorCurve = secPanelWindowBlurRepository.qsColorCurve;
            builder.setColorCurve(qSColorCurve.saturation, qSColorCurve.curve, qSColorCurve.minX, qSColorCurve.maxX, qSColorCurve.minY, qSColorCurve.maxY);
        }
        ReadonlyStateFlow readonlyStateFlow = this.shouldBlockWindowBlur;
        builder.setRadius(((Boolean) readonlyStateFlow.$$delegate_0.getValue()).booleanValue() ? 0 : i);
        secPanelWindowBlurRepository.windowBlurRadius = ((Boolean) readonlyStateFlow.$$delegate_0.getValue()).booleanValue() ? 0 : i;
        Log.d(TAG, "Window Blur:  " + i + "  shouldBlockBlur: " + readonlyStateFlow.$$delegate_0.getValue() + " isBlurReduced: " + this.secBlurSettingsInteractor.blurReduced.$$delegate_0.getValue() + "  isBackgroundVisible: " + stateFlow.getValue());
        return builder;
    }
}
