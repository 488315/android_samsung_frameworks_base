package com.android.systemui.blur.domain.interactor;

import com.android.systemui.blur.data.repository.SecCapturedBlurRepository;
import com.android.systemui.blur.data.repository.SecCapturedBlurRepositoryImpl;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes.dex */
public final class SecCapturedBlurCollapseShaderInteractor {
    public static final String TAG;
    public final ChannelFlowTransformLatest collapseQsWhileScreenWakingUp;
    public final SecPanelExpansionStateInteractor secPanelExpansionStateInteractor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = Reflection.getOrCreateKotlinClass(SecCapturedBlurCollapseShaderInteractor.class).getSimpleName();
    }

    public SecCapturedBlurCollapseShaderInteractor(SecCapturedBlurRepository secCapturedBlurRepository, PowerInteractor powerInteractor, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor) {
        this.secPanelExpansionStateInteractor = secPanelExpansionStateInteractor;
        this.collapseQsWhileScreenWakingUp = FlowKt.transformLatest(((SecCapturedBlurRepositoryImpl) secCapturedBlurRepository).requestCaptureBlur, new SecCapturedBlurCollapseShaderInteractor$collapseQsWhileScreenWakingUp$1(powerInteractor, this, null));
    }
}
