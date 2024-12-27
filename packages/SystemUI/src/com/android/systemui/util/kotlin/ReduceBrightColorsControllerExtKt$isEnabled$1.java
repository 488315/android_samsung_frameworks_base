package com.android.systemui.util.kotlin;

import com.android.systemui.qs.ReduceBrightColorsController;
import com.android.systemui.qs.ReduceBrightColorsControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class ReduceBrightColorsControllerExtKt$isEnabled$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ReduceBrightColorsController $this_isEnabled;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReduceBrightColorsControllerExtKt$isEnabled$1(ReduceBrightColorsController reduceBrightColorsController, Continuation continuation) {
        super(2, continuation);
        this.$this_isEnabled = reduceBrightColorsController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ReduceBrightColorsControllerExtKt$isEnabled$1 reduceBrightColorsControllerExtKt$isEnabled$1 = new ReduceBrightColorsControllerExtKt$isEnabled$1(this.$this_isEnabled, continuation);
        reduceBrightColorsControllerExtKt$isEnabled$1.L$0 = obj;
        return reduceBrightColorsControllerExtKt$isEnabled$1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.util.kotlin.ReduceBrightColorsControllerExtKt$isEnabled$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ReduceBrightColorsController.Listener() { // from class: com.android.systemui.util.kotlin.ReduceBrightColorsControllerExtKt$isEnabled$1$callback$1
                @Override // com.android.systemui.qs.ReduceBrightColorsController.Listener
                public void onActivated(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            ((ReduceBrightColorsControllerImpl) this.$this_isEnabled).addCallback(r1);
            final ReduceBrightColorsController reduceBrightColorsController = this.$this_isEnabled;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.kotlin.ReduceBrightColorsControllerExtKt$isEnabled$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Object invoke() {
                    m2318invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: collision with other method in class */
                public final void m2318invoke() {
                    ((ReduceBrightColorsControllerImpl) ReduceBrightColorsController.this).removeCallback(r1);
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((ReduceBrightColorsControllerExtKt$isEnabled$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
