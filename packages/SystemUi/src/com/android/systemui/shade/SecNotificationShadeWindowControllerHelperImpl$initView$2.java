package com.android.systemui.shade;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$6;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initView$2", m277f = "SecNotificationShadeWindowControllerHelperImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setAutoCallPickupState}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class SecNotificationShadeWindowControllerHelperImpl$initView$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SecNotificationShadeWindowControllerHelperImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecNotificationShadeWindowControllerHelperImpl$initView$2(SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl, Continuation<? super SecNotificationShadeWindowControllerHelperImpl$initView$2> continuation) {
        super(2, continuation);
        this.this$0 = secNotificationShadeWindowControllerHelperImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecNotificationShadeWindowControllerHelperImpl$initView$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecNotificationShadeWindowControllerHelperImpl$initView$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.this$0;
            KeyguardTransitionInteractor$special$$inlined$filter$6 keyguardTransitionInteractor$special$$inlined$filter$6 = secNotificationShadeWindowControllerHelperImpl.keyguardTransitionInteractor.finishedKeyguardTransitionStep;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initView$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    TransitionStep transitionStep = (TransitionStep) obj2;
                    if (transitionStep.from == KeyguardState.PRIMARY_BOUNCER) {
                        if (transitionStep.f304to == KeyguardState.GONE) {
                            String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
                            SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = SecNotificationShadeWindowControllerHelperImpl.this;
                            secNotificationShadeWindowControllerHelperImpl2.applyBouncer(secNotificationShadeWindowControllerHelperImpl2.getCurrentState());
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (keyguardTransitionInteractor$special$$inlined$filter$6.collect(flowCollector, this) == coroutineSingletons) {
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
