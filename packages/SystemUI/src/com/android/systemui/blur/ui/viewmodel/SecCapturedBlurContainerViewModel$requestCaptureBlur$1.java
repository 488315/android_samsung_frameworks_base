package com.android.systemui.blur.ui.viewmodel;

import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SecCapturedBlurContainerViewModel$requestCaptureBlur$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ PowerInteractor $powerInteractor;
    final /* synthetic */ PrimaryBouncerInteractor $primaryBouncerInteractor;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecCapturedBlurContainerViewModel$requestCaptureBlur$1(PowerInteractor powerInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, Continuation continuation) {
        super(3, continuation);
        this.$powerInteractor = powerInteractor;
        this.$primaryBouncerInteractor = primaryBouncerInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SecCapturedBlurContainerViewModel$requestCaptureBlur$1 secCapturedBlurContainerViewModel$requestCaptureBlur$1 = new SecCapturedBlurContainerViewModel$requestCaptureBlur$1(this.$powerInteractor, this.$primaryBouncerInteractor, (Continuation) obj3);
        secCapturedBlurContainerViewModel$requestCaptureBlur$1.L$0 = (FlowCollector) obj;
        secCapturedBlurContainerViewModel$requestCaptureBlur$1.L$1 = (SecPanelBlurBinding.BlurType) obj2;
        return secCapturedBlurContainerViewModel$requestCaptureBlur$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x007d, code lost:
    
        if (r2.emit(r1, r8) == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0088, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0061  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 3
            r3 = 2
            r4 = 1
            r5 = 0
            if (r1 == 0) goto L2a
            if (r1 == r4) goto L1e
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            goto L19
        L11:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L19:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L8b
        L1e:
            java.lang.Object r1 = r8.L$1
            com.android.systemui.blur.di.SecPanelBlurBinding$BlurType r1 = (com.android.systemui.blur.di.SecPanelBlurBinding.BlurType) r1
            java.lang.Object r2 = r8.L$0
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            kotlin.ResultKt.throwOnFailure(r9)
            goto L4d
        L2a:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
            java.lang.Object r1 = r8.L$1
            com.android.systemui.blur.di.SecPanelBlurBinding$BlurType r1 = (com.android.systemui.blur.di.SecPanelBlurBinding.BlurType) r1
            com.android.systemui.blur.di.SecPanelBlurBinding$BlurType r6 = com.android.systemui.blur.di.SecPanelBlurBinding.BlurType.QUICK_PANEL
            if (r1 != r6) goto L80
            com.android.systemui.power.domain.interactor.PowerInteractor r2 = r8.$powerInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r2.detailedWakefulness
            r8.L$0 = r9
            r8.L$1 = r1
            r8.label = r4
            java.lang.Object r2 = kotlinx.coroutines.flow.FlowKt.first(r2, r8)
            if (r2 != r0) goto L4a
            goto L8a
        L4a:
            r7 = r2
            r2 = r9
            r9 = r7
        L4d:
            com.android.systemui.power.shared.model.WakefulnessModel r9 = (com.android.systemui.power.shared.model.WakefulnessModel) r9
            com.android.systemui.power.shared.model.WakefulnessState r9 = r9.internalWakefulnessState
            com.android.systemui.power.shared.model.WakefulnessState r4 = com.android.systemui.power.shared.model.WakefulnessState.STARTING_TO_WAKE
            java.lang.String r6 = "SecCapturedBlurContainerViewModel"
            if (r9 != r4) goto L61
            java.lang.String r8 = "Skip while STARTING_TO_WAKE QUICK_PANEL captured blur case"
            int r8 = android.util.Log.d(r6, r8)
            kotlin.coroutines.jvm.internal.Boxing.boxInt(r8)
            goto L8b
        L61:
            com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor r9 = r8.$primaryBouncerInteractor
            boolean r9 = r9.isBouncerShowing()
            if (r9 == 0) goto L73
            java.lang.String r8 = "Skip while BouncerShowing captured blur case"
            int r8 = android.util.Log.d(r6, r8)
            kotlin.coroutines.jvm.internal.Boxing.boxInt(r8)
            goto L8b
        L73:
            r8.L$0 = r5
            r8.L$1 = r5
            r8.label = r3
            java.lang.Object r8 = r2.emit(r1, r8)
            if (r8 != r0) goto L8b
            goto L8a
        L80:
            r8.L$0 = r5
            r8.label = r2
            java.lang.Object r8 = r9.emit(r1, r8)
            if (r8 != r0) goto L8b
        L8a:
            return r0
        L8b:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.blur.ui.viewmodel.SecCapturedBlurContainerViewModel$requestCaptureBlur$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
