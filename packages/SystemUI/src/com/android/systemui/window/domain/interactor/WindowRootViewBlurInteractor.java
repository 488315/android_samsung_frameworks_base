package com.android.systemui.window.domain.interactor;

import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.window.data.repository.WindowRootViewBlurRepository;
import com.android.systemui.window.data.repository.WindowRootViewBlurRepositoryImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WindowRootViewBlurInteractor {
    public final ReadonlyStateFlow blurRadiusRequestedByShade;
    public final ReadonlyStateFlow isBlurCurrentlySupported;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 isBlurOpaque;
    public final StateFlowImpl isBouncerTransitionInProgress;
    public final WindowRootViewBlurRepository repository;

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

    public WindowRootViewBlurInteractor(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, CommunalInteractor communalInteractor, WindowRootViewBlurRepository windowRootViewBlurRepository) {
        this.repository = windowRootViewBlurRepository;
        Boolean bool = Boolean.FALSE;
        this.isBouncerTransitionInProgress = StateFlowKt.MutableStateFlow(bool);
        WindowRootViewBlurRepositoryImpl windowRootViewBlurRepositoryImpl = (WindowRootViewBlurRepositoryImpl) windowRootViewBlurRepository;
        this.isBlurCurrentlySupported = windowRootViewBlurRepositoryImpl.isBlurSupported;
        this.blurRadiusRequestedByShade = FlowKt.asStateFlow(windowRootViewBlurRepositoryImpl.blurRequestedByShade);
        this.isBlurOpaque = FlowKt.combine(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool), new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool), windowRootViewBlurRepositoryImpl.isBlurOpaque, new WindowRootViewBlurInteractor$isBlurOpaque$1(null));
    }
}
