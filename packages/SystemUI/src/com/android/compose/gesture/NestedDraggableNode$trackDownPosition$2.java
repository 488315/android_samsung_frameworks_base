package com.android.compose.gesture;

import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventKt;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerId;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
final class NestedDraggableNode$trackDownPosition$2 extends RestrictedSuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NestedDraggableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedDraggableNode$trackDownPosition$2(NestedDraggableNode nestedDraggableNode, Continuation continuation) {
        super(2, continuation);
        this.this$0 = nestedDraggableNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NestedDraggableNode$trackDownPosition$2 nestedDraggableNode$trackDownPosition$2 = new NestedDraggableNode$trackDownPosition$2(this.this$0, continuation);
        nestedDraggableNode$trackDownPosition$2.L$0 = obj;
        return nestedDraggableNode$trackDownPosition$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NestedDraggableNode$trackDownPosition$2) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0062, code lost:
    
        if (r9 != r0) goto L24;
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0062 -> B:24:0x0065). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        AwaitPointerEventScope awaitPointerEventScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                this.L$0 = awaitPointerEventScope;
                this.label = 1;
                obj = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope, null, this, 2);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else if (i == 1) {
                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            } else {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                List<PointerInputChange> list = ((PointerEvent) obj).changes;
                NestedDraggableNode nestedDraggableNode = this.this$0;
                for (PointerInputChange pointerInputChange : list) {
                    boolean zChangedToDownIgnoreConsumed = PointerEventKt.changedToDownIgnoreConsumed(pointerInputChange);
                    long j = pointerInputChange.id;
                    if (zChangedToDownIgnoreConsumed) {
                        nestedDraggableNode.pointersDown.put(PointerId.m592boximpl(j), PointerType.m599boximpl(pointerInputChange.type));
                    } else if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange)) {
                        nestedDraggableNode.pointersDown.remove(PointerId.m592boximpl(j));
                    }
                }
                if (this.this$0.pointersDown.size() <= 0) {
                    this.this$0.pointersDown.clear();
                    return Unit.INSTANCE;
                }
                this.L$0 = awaitPointerEventScope;
                this.label = 2;
                obj = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope).awaitPointerEvent(PointerEventPass.Main, this);
            }
            PointerInputChange pointerInputChange2 = (PointerInputChange) obj;
            this.this$0.lastFirstDown = Offset.m395boximpl(pointerInputChange2.position);
            this.this$0.pointersDown.put(PointerId.m592boximpl(pointerInputChange2.id), PointerType.m599boximpl(pointerInputChange2.type));
            this.L$0 = awaitPointerEventScope;
            this.label = 2;
            obj = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope).awaitPointerEvent(PointerEventPass.Main, this);
        } catch (Throwable th) {
            this.this$0.pointersDown.clear();
            throw th;
        }
    }
}
