package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.RotationLockController;
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
/* loaded from: classes3.dex */
final class RotationLockControllerExtKt$isRotationLockEnabled$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ RotationLockController $this_isRotationLockEnabled;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RotationLockControllerExtKt$isRotationLockEnabled$1(RotationLockController rotationLockController, Continuation continuation) {
        super(2, continuation);
        this.$this_isRotationLockEnabled = rotationLockController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RotationLockControllerExtKt$isRotationLockEnabled$1 rotationLockControllerExtKt$isRotationLockEnabled$1 = new RotationLockControllerExtKt$isRotationLockEnabled$1(this.$this_isRotationLockEnabled, continuation);
        rotationLockControllerExtKt$isRotationLockEnabled$1.L$0 = obj;
        return rotationLockControllerExtKt$isRotationLockEnabled$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final RotationLockController.RotationLockControllerCallback rotationLockControllerCallback = new RotationLockController.RotationLockControllerCallback() { // from class: com.android.systemui.util.kotlin.RotationLockControllerExtKt$isRotationLockEnabled$1$rotationLockCallback$1
                @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
                public final void onRotationLockStateChanged(boolean z, boolean z2) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            this.$this_isRotationLockEnabled.addCallback(rotationLockControllerCallback);
            final RotationLockController rotationLockController = this.$this_isRotationLockEnabled;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.kotlin.RotationLockControllerExtKt$isRotationLockEnabled$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Object invoke() {
                    m2319invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: collision with other method in class */
                public final void m2319invoke() {
                    RotationLockController.this.removeCallback(rotationLockControllerCallback);
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
        return ((RotationLockControllerExtKt$isRotationLockEnabled$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
