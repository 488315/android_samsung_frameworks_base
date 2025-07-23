package com.android.systemui.blur.domain.interactor;

import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0070, code lost:
    
        if (r1.emit(r3, r5) == r0) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0072, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003c, code lost:
    
        if (r6 == r0) goto L19;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r6)
            goto L73
        L10:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L18:
            java.lang.Object r1 = r5.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r6)
            goto L3f
        L20:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            r1 = r6
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            java.lang.Object r6 = r5.L$1
            com.android.systemui.blur.di.SecPanelBlurBinding$BlurType r6 = (com.android.systemui.blur.di.SecPanelBlurBinding.BlurType) r6
            com.android.systemui.blur.di.SecPanelBlurBinding$BlurType r4 = com.android.systemui.blur.di.SecPanelBlurBinding.BlurType.QUICK_PANEL
            if (r6 != r4) goto L73
            com.android.systemui.power.domain.interactor.PowerInteractor r6 = r5.$powerInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r6.detailedWakefulness
            r5.L$0 = r1
            r5.label = r3
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.first(r6, r5)
            if (r6 != r0) goto L3f
            goto L72
        L3f:
            com.android.systemui.power.shared.model.WakefulnessModel r6 = (com.android.systemui.power.shared.model.WakefulnessModel) r6
            com.android.systemui.power.shared.model.WakefulnessState r6 = r6.internalWakefulnessState
            com.android.systemui.power.shared.model.WakefulnessState r3 = com.android.systemui.power.shared.model.WakefulnessState.STARTING_TO_WAKE
            if (r6 != r3) goto L73
            java.lang.String r6 = com.android.systemui.blur.domain.interactor.SecCapturedBlurCollapseShaderInteractor.TAG
            com.android.systemui.blur.domain.interactor.SecCapturedBlurCollapseShaderInteractor r3 = r5.this$0
            com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor r3 = r3.secPanelExpansionStateInteractor
            int r3 = r3.getstatusBarState()
            java.lang.String r3 = com.android.systemui.statusbar.StatusBarState.toString(r3)
            java.lang.String r4 = "collapseQsWhileScreenWakingUp case, state = "
            android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r4, r3, r6)
            com.android.systemui.blur.domain.interactor.SecCapturedBlurCollapseShaderInteractor r6 = r5.this$0
            com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor r6 = r6.secPanelExpansionStateInteractor
            int r6 = r6.getstatusBarState()
            java.lang.Integer r3 = new java.lang.Integer
            r3.<init>(r6)
            r6 = 0
            r5.L$0 = r6
            r5.label = r2
            java.lang.Object r5 = r1.emit(r3, r5)
            if (r5 != r0) goto L73
        L72:
            return r0
        L73:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.blur.domain.interactor.SecCapturedBlurCollapseShaderInteractor$collapseQsWhileScreenWakingUp$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
