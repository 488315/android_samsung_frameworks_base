package com.android.compose.gesture;

import androidx.compose.ui.unit.Velocity;
import com.android.compose.gesture.NestedDraggable;
import com.android.compose.gesture.NestedDraggableNode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;

/* loaded from: classes.dex */
final class NestedDraggableNode$NestedScrollController$flingWithOverscroll$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ CompletableDeferred $flingCompletable;
    /* synthetic */ long J$0;
    Object L$0;
    int label;
    final /* synthetic */ NestedDraggableNode this$0;
    final /* synthetic */ NestedDraggableNode.NestedScrollController this$1;

    /* renamed from: com.android.compose.gesture.NestedDraggableNode$NestedScrollController$flingWithOverscroll$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function1 {
        final /* synthetic */ CompletableDeferred $flingCompletable;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CompletableDeferred completableDeferred, Continuation continuation) {
            super(1, continuation);
            this.$flingCompletable = completableDeferred;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new AnonymousClass1(this.$flingCompletable, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((AnonymousClass1) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CompletableDeferred completableDeferred = this.$flingCompletable;
                this.label = 1;
                if (((CompletableDeferredImpl) completableDeferred).awaitInternal(this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedDraggableNode$NestedScrollController$flingWithOverscroll$2(NestedDraggableNode nestedDraggableNode, NestedDraggableNode.NestedScrollController nestedScrollController, CompletableDeferred completableDeferred, Continuation continuation) {
        super(2, continuation);
        this.this$0 = nestedDraggableNode;
        this.this$1 = nestedScrollController;
        this.$flingCompletable = completableDeferred;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NestedDraggableNode$NestedScrollController$flingWithOverscroll$2 nestedDraggableNode$NestedScrollController$flingWithOverscroll$2 = new NestedDraggableNode$NestedScrollController$flingWithOverscroll$2(this.this$0, this.this$1, this.$flingCompletable, continuation);
        nestedDraggableNode$NestedScrollController$flingWithOverscroll$2.J$0 = ((Velocity) obj).packedValue;
        return nestedDraggableNode$NestedScrollController$flingWithOverscroll$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NestedDraggableNode$NestedScrollController$flingWithOverscroll$2) create(Velocity.m878boximpl(((Velocity) obj).packedValue), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        NestedDraggableNode nestedDraggableNode;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long j = this.J$0;
            NestedDraggableNode nestedDraggableNode2 = this.this$0;
            NestedDraggable.Controller controller = this.this$1.controller;
            float fM933toFloatTH1AsA0$1 = nestedDraggableNode2.m933toFloatTH1AsA0$1(j);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$flingCompletable, null);
            this.L$0 = nestedDraggableNode2;
            this.label = 1;
            Object objOnDragStopped = controller.onDragStopped(fM933toFloatTH1AsA0$1, anonymousClass1, this);
            if (objOnDragStopped == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = objOnDragStopped;
            nestedDraggableNode = nestedDraggableNode2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            nestedDraggableNode = (NestedDraggableNode) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return Velocity.m878boximpl(nestedDraggableNode.m936toVelocityadjELrA$1(((Number) obj).floatValue()));
    }
}
