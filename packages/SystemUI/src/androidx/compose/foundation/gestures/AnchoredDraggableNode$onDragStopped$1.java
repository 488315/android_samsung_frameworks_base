package androidx.compose.foundation.gestures;

import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AnchoredDraggableNode$onDragStopped$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $velocity;
    int label;
    final /* synthetic */ AnchoredDraggableNode<Object> this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.foundation.gestures.AnchoredDraggableNode$onDragStopped$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ long J$0;
        int label;
        final /* synthetic */ AnchoredDraggableNode<Object> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(AnchoredDraggableNode<Object> anchoredDraggableNode, Continuation continuation) {
            super(2, continuation);
            this.this$0 = anchoredDraggableNode;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.J$0 = ((Velocity) obj).packedValue;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(Velocity.m876boximpl(((Velocity) obj).packedValue), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            long j;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j2 = this.J$0;
                AnchoredDraggableNode<Object> anchoredDraggableNode = this.this$0;
                float m879getYimpl = anchoredDraggableNode.orientation == Orientation.Vertical ? Velocity.m879getYimpl(j2) : Velocity.m878getXimpl(j2);
                this.J$0 = j2;
                this.label = 1;
                obj = AnchoredDraggableNode.access$fling(anchoredDraggableNode, m879getYimpl, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                j = j2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                j = this.J$0;
                ResultKt.throwOnFailure(obj);
            }
            float floatValue = ((Number) obj).floatValue();
            float requireOffset = this.this$0.state.requireOffset();
            float minPosition = ((DefaultDraggableAnchors) this.this$0.state.getAnchors()).minPosition();
            if (requireOffset >= ((DefaultDraggableAnchors) this.this$0.state.getAnchors()).maxPosition() || requireOffset <= minPosition) {
                Orientation orientation = this.this$0.orientation;
                float f = orientation == Orientation.Horizontal ? floatValue : 0.0f;
                if (orientation != Orientation.Vertical) {
                    floatValue = 0.0f;
                }
                j = VelocityKt.Velocity(f, floatValue);
            }
            return Velocity.m876boximpl(j);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnchoredDraggableNode$onDragStopped$1(AnchoredDraggableNode<Object> anchoredDraggableNode, long j, Continuation continuation) {
        super(2, continuation);
        this.this$0 = anchoredDraggableNode;
        this.$velocity = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AnchoredDraggableNode$onDragStopped$1(this.this$0, this.$velocity, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AnchoredDraggableNode$onDragStopped$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x004b, code lost:
    
        if (androidx.compose.foundation.gestures.AnchoredDraggableNode.access$fling(r4, r8, r7) == r0) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0070, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x006e, code lost:
    
        if (r5.mo19applyToFlingBMRW4eQ(r3, r8, r7) == r0) goto L33;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L19
            if (r1 == r3) goto L15
            if (r1 != r2) goto Ld
            goto L15
        Ld:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L15:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L71
        L19:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.compose.foundation.gestures.AnchoredDraggableNode<java.lang.Object> r8 = r7.this$0
            long r4 = r7.$velocity
            boolean r1 = r8.isReverseDirection()
            if (r1 == 0) goto L2d
            r1 = -1082130432(0xffffffffbf800000, float:-1.0)
        L28:
            long r4 = androidx.compose.ui.unit.Velocity.m882timesadjELrA(r1, r4)
            goto L30
        L2d:
            r1 = 1065353216(0x3f800000, float:1.0)
            goto L28
        L30:
            androidx.compose.foundation.gestures.Orientation r8 = r8.orientation
            androidx.compose.foundation.gestures.Orientation r1 = androidx.compose.foundation.gestures.Orientation.Vertical
            if (r8 != r1) goto L3b
            float r8 = androidx.compose.ui.unit.Velocity.m879getYimpl(r4)
            goto L3f
        L3b:
            float r8 = androidx.compose.ui.unit.Velocity.m878getXimpl(r4)
        L3f:
            androidx.compose.foundation.gestures.AnchoredDraggableNode<java.lang.Object> r4 = r7.this$0
            androidx.compose.foundation.OverscrollEffect r5 = r4.overscrollEffect
            if (r5 != 0) goto L4e
            r7.label = r3
            java.lang.Object r7 = androidx.compose.foundation.gestures.AnchoredDraggableNode.access$fling(r4, r8, r7)
            if (r7 != r0) goto L71
            goto L70
        L4e:
            androidx.compose.foundation.gestures.Orientation r3 = r4.orientation
            androidx.compose.foundation.gestures.Orientation r4 = androidx.compose.foundation.gestures.Orientation.Horizontal
            r6 = 0
            if (r3 != r4) goto L57
            r4 = r8
            goto L58
        L57:
            r4 = r6
        L58:
            if (r3 != r1) goto L5b
            goto L5c
        L5b:
            r8 = r6
        L5c:
            long r3 = androidx.compose.ui.unit.VelocityKt.Velocity(r4, r8)
            androidx.compose.foundation.gestures.AnchoredDraggableNode$onDragStopped$1$1 r8 = new androidx.compose.foundation.gestures.AnchoredDraggableNode$onDragStopped$1$1
            androidx.compose.foundation.gestures.AnchoredDraggableNode<java.lang.Object> r1 = r7.this$0
            r6 = 0
            r8.<init>(r1, r6)
            r7.label = r2
            java.lang.Object r7 = r5.mo19applyToFlingBMRW4eQ(r3, r8, r7)
            if (r7 != r0) goto L71
        L70:
            return r0
        L71:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.AnchoredDraggableNode$onDragStopped$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
