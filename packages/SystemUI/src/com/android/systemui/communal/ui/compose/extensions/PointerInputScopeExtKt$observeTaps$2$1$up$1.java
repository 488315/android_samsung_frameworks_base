package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEventPass;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class PointerInputScopeExtKt$observeTaps$2$1$up$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ PointerEventPass $pass;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PointerInputScopeExtKt$observeTaps$2$1$up$1(PointerEventPass pointerEventPass, Continuation continuation) {
        super(2, continuation);
        this.$pass = pointerEventPass;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PointerInputScopeExtKt$observeTaps$2$1$up$1 pointerInputScopeExtKt$observeTaps$2$1$up$1 = new PointerInputScopeExtKt$observeTaps$2$1$up$1(this.$pass, continuation);
        pointerInputScopeExtKt$observeTaps$2$1$up$1.L$0 = obj;
        return pointerInputScopeExtKt$observeTaps$2$1$up$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PointerInputScopeExtKt$observeTaps$2$1$up$1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AwaitPointerEventScope awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
            PointerEventPass pointerEventPass = this.$pass;
            this.label = 1;
            obj = TapGestureDetectorKt.waitForUpOrCancellation(awaitPointerEventScope, pointerEventPass, this);
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
}
