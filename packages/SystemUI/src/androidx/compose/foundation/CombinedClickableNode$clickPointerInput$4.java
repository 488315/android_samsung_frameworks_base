package androidx.compose.foundation;

import androidx.compose.foundation.gestures.PressGestureScope;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.geometry.Offset;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class CombinedClickableNode$clickPointerInput$4 extends SuspendLambda implements Function3 {
    /* synthetic */ long J$0;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CombinedClickableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CombinedClickableNode$clickPointerInput$4(CombinedClickableNode combinedClickableNode, Continuation continuation) {
        super(3, continuation);
        this.this$0 = combinedClickableNode;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        long j = ((Offset) obj2).packedValue;
        CombinedClickableNode$clickPointerInput$4 combinedClickableNode$clickPointerInput$4 = new CombinedClickableNode$clickPointerInput$4(this.this$0, (Continuation) obj3);
        combinedClickableNode$clickPointerInput$4.L$0 = (PressGestureScope) obj;
        combinedClickableNode$clickPointerInput$4.J$0 = j;
        return combinedClickableNode$clickPointerInput$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        Object obj3 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PressGestureScope pressGestureScope = (PressGestureScope) this.L$0;
            long j = this.J$0;
            CombinedClickableNode combinedClickableNode = this.this$0;
            if (combinedClickableNode.enabled) {
                this.label = 1;
                MutableInteractionSource mutableInteractionSource = combinedClickableNode.interactionSource;
                if (mutableInteractionSource == null || (obj2 = CoroutineScopeKt.coroutineScope(new AbstractClickableNode$handlePressInteraction$2$1(pressGestureScope, j, mutableInteractionSource, combinedClickableNode, null), this)) != obj3) {
                    obj2 = Unit.INSTANCE;
                }
                if (obj2 == obj3) {
                    return obj3;
                }
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
