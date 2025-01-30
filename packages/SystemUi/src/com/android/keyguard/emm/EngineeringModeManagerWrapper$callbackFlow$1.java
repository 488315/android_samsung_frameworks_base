package com.android.keyguard.emm;

import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.keyguard.emm.EngineeringModeManagerWrapper$callbackFlow$1", m277f = "EngineeringModeManagerWrapper.kt", m278l = {47}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class EngineeringModeManagerWrapper$callbackFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ EngineeringModeManagerWrapper this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EngineeringModeManagerWrapper$callbackFlow$1(EngineeringModeManagerWrapper engineeringModeManagerWrapper, Continuation<? super EngineeringModeManagerWrapper$callbackFlow$1> continuation) {
        super(2, continuation);
        this.this$0 = engineeringModeManagerWrapper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        EngineeringModeManagerWrapper$callbackFlow$1 engineeringModeManagerWrapper$callbackFlow$1 = new EngineeringModeManagerWrapper$callbackFlow$1(this.this$0, continuation);
        engineeringModeManagerWrapper$callbackFlow$1.L$0 = obj;
        return engineeringModeManagerWrapper$callbackFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((EngineeringModeManagerWrapper$callbackFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.emm.EngineeringModeManagerWrapper$callbackFlow$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final EngineeringModeManagerWrapper engineeringModeManagerWrapper = this.this$0;
            final ?? r1 = new KeyguardStateController.Callback() { // from class: com.android.keyguard.emm.EngineeringModeManagerWrapper$callbackFlow$1$callback$1
                @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
                public final void onKeyguardShowingChanged() {
                    ((ChannelCoroutine) ProducerScope.this).mo2872trySendJP2dKIU(Boolean.valueOf(((KeyguardStateControllerImpl) engineeringModeManagerWrapper.keyguardStateController).mShowing));
                }
            };
            ((KeyguardStateControllerImpl) this.this$0.keyguardStateController).addCallback(r1);
            final EngineeringModeManagerWrapper engineeringModeManagerWrapper2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.keyguard.emm.EngineeringModeManagerWrapper$callbackFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((KeyguardStateControllerImpl) EngineeringModeManagerWrapper.this.keyguardStateController).removeCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
