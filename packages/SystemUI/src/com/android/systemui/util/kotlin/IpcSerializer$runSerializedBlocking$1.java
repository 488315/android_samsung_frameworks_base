package com.android.systemui.util.kotlin;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class IpcSerializer$runSerializedBlocking$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $block;
    int label;
    final /* synthetic */ IpcSerializer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IpcSerializer$runSerializedBlocking$1(IpcSerializer ipcSerializer, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.this$0 = ipcSerializer;
        this.$block = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new IpcSerializer$runSerializedBlocking$1(this.this$0, this.$block, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            IpcSerializer ipcSerializer = this.this$0;
            Function1 function1 = this.$block;
            this.label = 1;
            obj = ipcSerializer.runSerialized(function1, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((IpcSerializer$runSerializedBlocking$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
