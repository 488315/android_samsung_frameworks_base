package androidx.compose.foundation.gestures;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class AnchoredDraggableNode$onDragStopped$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $velocity;
    int label;
    final /* synthetic */ AnchoredDraggableNode<Object> this$0;

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
            return ((AnonymousClass1) create(Velocity.m878boximpl(((Velocity) obj).packedValue), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            long jVelocity;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j = this.J$0;
                AnchoredDraggableNode<Object> anchoredDraggableNode = this.this$0;
                float fM881getYimpl = anchoredDraggableNode.orientation == Orientation.Vertical ? Velocity.m881getYimpl(j) : Velocity.m880getXimpl(j);
                this.J$0 = j;
                this.label = 1;
                obj = AnchoredDraggableNode.access$fling(anchoredDraggableNode, fM881getYimpl, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                jVelocity = j;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                jVelocity = this.J$0;
                ResultKt.throwOnFailure(obj);
            }
            float fFloatValue = ((Number) obj).floatValue();
            float fRequireOffset = this.this$0.state.requireOffset();
            float fMinPosition = ((DefaultDraggableAnchors) this.this$0.state.getAnchors()).minPosition();
            if (fRequireOffset >= ((DefaultDraggableAnchors) this.this$0.state.getAnchors()).maxPosition() || fRequireOffset <= fMinPosition) {
                Orientation orientation = this.this$0.orientation;
                float f = orientation == Orientation.Horizontal ? fFloatValue : 0.0f;
                if (orientation != Orientation.Vertical) {
                    fFloatValue = 0.0f;
                }
                jVelocity = VelocityKt.Velocity(f, fFloatValue);
            }
            return Velocity.m878boximpl(jVelocity);
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

    /* JADX WARN: Code restructure failed: missing block: B:22:0x004b, code lost:
    
        if (androidx.compose.foundation.gestures.AnchoredDraggableNode.access$fling(r4, r8, r7) == r0) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x006e, code lost:
    
        if (r5.mo19applyToFlingBMRW4eQ(r3, r8, r7) == r0) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0070, code lost:
    
        return r0;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnchoredDraggableNode<Object> anchoredDraggableNode = this.this$0;
            long jM884timesadjELrA = Velocity.m884timesadjELrA(anchoredDraggableNode.isReverseDirection() ? -1.0f : 1.0f, this.$velocity);
            Orientation orientation = anchoredDraggableNode.orientation;
            Orientation orientation2 = Orientation.Vertical;
            float fM881getYimpl = orientation == orientation2 ? Velocity.m881getYimpl(jM884timesadjELrA) : Velocity.m880getXimpl(jM884timesadjELrA);
            AnchoredDraggableNode<Object> anchoredDraggableNode2 = this.this$0;
            OverscrollEffect overscrollEffect = anchoredDraggableNode2.overscrollEffect;
            if (overscrollEffect == null) {
                this.label = 1;
            } else {
                Orientation orientation3 = anchoredDraggableNode2.orientation;
                float f = orientation3 == Orientation.Horizontal ? fM881getYimpl : 0.0f;
                if (orientation3 != orientation2) {
                    fM881getYimpl = 0.0f;
                }
                long jVelocity = VelocityKt.Velocity(f, fM881getYimpl);
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
                this.label = 2;
            }
        } else {
            if (i != 1 && i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
