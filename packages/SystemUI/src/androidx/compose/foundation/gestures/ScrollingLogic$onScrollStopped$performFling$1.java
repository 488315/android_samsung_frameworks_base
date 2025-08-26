package androidx.compose.foundation.gestures;

import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher;
import androidx.compose.ui.unit.Velocity;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
final class ScrollingLogic$onScrollStopped$performFling$1 extends SuspendLambda implements Function2 {
    /* synthetic */ long J$0;
    long J$1;
    int label;
    final /* synthetic */ ScrollingLogic this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScrollingLogic$onScrollStopped$performFling$1(ScrollingLogic scrollingLogic, Continuation continuation) {
        super(2, continuation);
        this.this$0 = scrollingLogic;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScrollingLogic$onScrollStopped$performFling$1 scrollingLogic$onScrollStopped$performFling$1 = new ScrollingLogic$onScrollStopped$performFling$1(this.this$0, continuation);
        scrollingLogic$onScrollStopped$performFling$1.J$0 = ((Velocity) obj).packedValue;
        return scrollingLogic$onScrollStopped$performFling$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScrollingLogic$onScrollStopped$performFling$1) create(Velocity.m878boximpl(((Velocity) obj).packedValue), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x007b, code lost:
    
        if (r0 != r6) goto L23;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object objM584dispatchPreFlingQWom1Mo;
        long j;
        Object objM82doFlingAnimationQWom1Mo;
        long j2;
        long j3;
        long j4;
        Object objM582dispatchPostFlingRZ2iAVY;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long j5 = this.J$0;
            NestedScrollDispatcher nestedScrollDispatcher = this.this$0.nestedScrollDispatcher;
            this.J$0 = j5;
            this.label = 1;
            objM584dispatchPreFlingQWom1Mo = nestedScrollDispatcher.m584dispatchPreFlingQWom1Mo(j5, this);
            if (objM584dispatchPreFlingQWom1Mo != coroutineSingletons) {
                j = j5;
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            j = this.J$0;
            ResultKt.throwOnFailure(obj);
            objM584dispatchPreFlingQWom1Mo = obj;
        } else {
            if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                long j6 = this.J$1;
                long j7 = this.J$0;
                ResultKt.throwOnFailure(obj);
                j2 = j7;
                j4 = j6;
                objM582dispatchPostFlingRZ2iAVY = obj;
                return Velocity.m878boximpl(Velocity.m882minusAH228Gc(j2, Velocity.m882minusAH228Gc(j4, ((Velocity) objM582dispatchPostFlingRZ2iAVY).packedValue)));
            }
            j3 = this.J$1;
            j2 = this.J$0;
            ResultKt.throwOnFailure(obj);
            objM82doFlingAnimationQWom1Mo = obj;
            long j8 = ((Velocity) objM82doFlingAnimationQWom1Mo).packedValue;
            NestedScrollDispatcher nestedScrollDispatcher2 = this.this$0.nestedScrollDispatcher;
            long jM882minusAH228Gc = Velocity.m882minusAH228Gc(j3, j8);
            this.J$0 = j2;
            this.J$1 = j8;
            this.label = 3;
            j4 = j8;
            objM582dispatchPostFlingRZ2iAVY = nestedScrollDispatcher2.m582dispatchPostFlingRZ2iAVY(jM882minusAH228Gc, j4, this);
        }
        long jM882minusAH228Gc2 = Velocity.m882minusAH228Gc(j, ((Velocity) objM584dispatchPreFlingQWom1Mo).packedValue);
        ScrollingLogic scrollingLogic = this.this$0;
        this.J$0 = j;
        this.J$1 = jM882minusAH228Gc2;
        this.label = 2;
        objM82doFlingAnimationQWom1Mo = scrollingLogic.m82doFlingAnimationQWom1Mo(jM882minusAH228Gc2, this);
        if (objM82doFlingAnimationQWom1Mo != coroutineSingletons) {
            j2 = j;
            j3 = jM882minusAH228Gc2;
            long j82 = ((Velocity) objM82doFlingAnimationQWom1Mo).packedValue;
            NestedScrollDispatcher nestedScrollDispatcher22 = this.this$0.nestedScrollDispatcher;
            long jM882minusAH228Gc3 = Velocity.m882minusAH228Gc(j3, j82);
            this.J$0 = j2;
            this.J$1 = j82;
            this.label = 3;
            j4 = j82;
            objM582dispatchPostFlingRZ2iAVY = nestedScrollDispatcher22.m582dispatchPostFlingRZ2iAVY(jM882minusAH228Gc3, j4, this);
        }
        return coroutineSingletons;
    }
}
