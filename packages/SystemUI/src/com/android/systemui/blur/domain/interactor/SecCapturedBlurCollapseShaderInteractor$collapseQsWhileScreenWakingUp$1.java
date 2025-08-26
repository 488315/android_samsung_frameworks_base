package com.android.systemui.blur.domain.interactor;

import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.power.shared.model.WakefulnessState;
import com.android.systemui.shade.ShadeExpansionStateManagerKt;
import com.android.systemui.statusbar.StatusBarState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes.dex */
final class SecCapturedBlurCollapseShaderInteractor$collapseQsWhileScreenWakingUp$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ PowerInteractor $powerInteractor;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ SecCapturedBlurCollapseShaderInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecCapturedBlurCollapseShaderInteractor$collapseQsWhileScreenWakingUp$1(PowerInteractor powerInteractor, SecCapturedBlurCollapseShaderInteractor secCapturedBlurCollapseShaderInteractor, Continuation continuation) {
        super(3, continuation);
        this.$powerInteractor = powerInteractor;
        this.this$0 = secCapturedBlurCollapseShaderInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SecCapturedBlurCollapseShaderInteractor$collapseQsWhileScreenWakingUp$1 secCapturedBlurCollapseShaderInteractor$collapseQsWhileScreenWakingUp$1 = new SecCapturedBlurCollapseShaderInteractor$collapseQsWhileScreenWakingUp$1(this.$powerInteractor, this.this$0, (Continuation) obj3);
        secCapturedBlurCollapseShaderInteractor$collapseQsWhileScreenWakingUp$1.L$0 = (FlowCollector) obj;
        secCapturedBlurCollapseShaderInteractor$collapseQsWhileScreenWakingUp$1.L$1 = (SecPanelBlurBinding.BlurType) obj2;
        return secCapturedBlurCollapseShaderInteractor$collapseQsWhileScreenWakingUp$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x009d, code lost:
    
        if (r1.emit(r2, r7) != r0) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            if (((WakefulnessModel) obj).internalWakefulnessState == WakefulnessState.STARTING_TO_WAKE && ((Number) this.this$0.secPanelExpansionStateInteractor.panelState.$$delegate_0.getValue()).intValue() == 2) {
                MediaSessions$H$$ExternalSyntheticOutline0.m("collapseQsWhileScreenWakingUp case, state = ", StatusBarState.toString(this.this$0.secPanelExpansionStateInteractor.getstatusBarState()), ", panelState = ", ShadeExpansionStateManagerKt.panelStateToString(((Number) this.this$0.secPanelExpansionStateInteractor.panelState.$$delegate_0.getValue()).intValue()), SecCapturedBlurCollapseShaderInteractor.TAG);
                Integer num = new Integer(this.this$0.secPanelExpansionStateInteractor.getstatusBarState());
                this.L$0 = null;
                this.label = 2;
            }
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        flowCollector = (FlowCollector) this.L$0;
        if (((SecPanelBlurBinding.BlurType) this.L$1) == SecPanelBlurBinding.BlurType.QUICK_PANEL) {
            ReadonlyStateFlow readonlyStateFlow = this.$powerInteractor.detailedWakefulness;
            this.L$0 = flowCollector;
            this.label = 1;
            obj = FlowKt.first(readonlyStateFlow, this);
            if (obj != coroutineSingletons) {
                if (((WakefulnessModel) obj).internalWakefulnessState == WakefulnessState.STARTING_TO_WAKE) {
                    MediaSessions$H$$ExternalSyntheticOutline0.m("collapseQsWhileScreenWakingUp case, state = ", StatusBarState.toString(this.this$0.secPanelExpansionStateInteractor.getstatusBarState()), ", panelState = ", ShadeExpansionStateManagerKt.panelStateToString(((Number) this.this$0.secPanelExpansionStateInteractor.panelState.$$delegate_0.getValue()).intValue()), SecCapturedBlurCollapseShaderInteractor.TAG);
                    Integer num2 = new Integer(this.this$0.secPanelExpansionStateInteractor.getstatusBarState());
                    this.L$0 = null;
                    this.label = 2;
                }
            }
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
