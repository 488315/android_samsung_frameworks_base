package com.android.systemui.blur.domain.interactor;

import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.blur.data.repository.SecCapturedBlurRepository;
import com.android.systemui.blur.data.repository.SecCapturedBlurRepositoryImpl;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.statusbar.StatusBarState;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecCapturedBlurCollapseShaderInteractor {
    public static final String TAG;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 collapseQsWhileCapturedViewInvisible = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(0);
    public final ChannelFlowTransformLatest collapseQsWhileScreenWakingUp;
    public final SecPanelExpansionStateInteractor secPanelExpansionStateInteractor;

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
        TAG = Reflection.getOrCreateKotlinClass(SecCapturedBlurCollapseShaderInteractor.class).getSimpleName();
    }

    public SecCapturedBlurCollapseShaderInteractor(SecCapturedBlurRepository secCapturedBlurRepository, PowerInteractor powerInteractor, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor) {
        this.secPanelExpansionStateInteractor = secPanelExpansionStateInteractor;
        this.collapseQsWhileScreenWakingUp = FlowKt.transformLatest(((SecCapturedBlurRepositoryImpl) secCapturedBlurRepository).requestCaptureBlur, new SecCapturedBlurCollapseShaderInteractor$collapseQsWhileScreenWakingUp$1(powerInteractor, this, null));
    }

    public final void sendCollapseQsWhileCapturedViewInvisible() {
        Log.d(TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("collapseQsWhileCapturedViewInvisible case, state = ", StatusBarState.toString(this.secPanelExpansionStateInteractor.getstatusBarState())));
        new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SecCapturedBlurCollapseShaderInteractor$sendCollapseQsWhileCapturedViewInvisible$1(this, null), this.collapseQsWhileCapturedViewInvisible);
    }
}
