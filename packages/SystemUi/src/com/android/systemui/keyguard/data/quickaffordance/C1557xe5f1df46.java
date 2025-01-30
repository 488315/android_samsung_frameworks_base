package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1", m277f = "HomeControlsKeyguardQuickAffordanceConfig.kt", m278l = {190}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1 */
/* loaded from: classes.dex */
public final class C1557xe5f1df46 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ HomeControlsKeyguardQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1557xe5f1df46(Continuation continuation, HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig) {
        super(3, continuation);
        this.this$0 = homeControlsKeyguardQuickAffordanceConfig;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        C1557xe5f1df46 c1557xe5f1df46 = new C1557xe5f1df46((Continuation) obj3, this.this$0);
        c1557xe5f1df46.L$0 = (FlowCollector) obj;
        c1557xe5f1df46.L$1 = obj2;
        return c1557xe5f1df46.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig = this.this$0;
                ControlsListingController controlsListingController = (ControlsListingController) homeControlsKeyguardQuickAffordanceConfig.component.getControlsListingController().orElse(null);
                if (controlsListingController == null) {
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE);
                } else {
                    ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
                    HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1 homeControlsKeyguardQuickAffordanceConfig$stateInternal$1 = new HomeControlsKeyguardQuickAffordanceConfig$stateInternal$1(controlsListingController, homeControlsKeyguardQuickAffordanceConfig, null);
                    conflatedCallbackFlow.getClass();
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = ConflatedCallbackFlow.conflatedCallbackFlow(homeControlsKeyguardQuickAffordanceConfig$stateInternal$1);
                }
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
