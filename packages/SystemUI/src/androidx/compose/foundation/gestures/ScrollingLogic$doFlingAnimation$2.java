package androidx.compose.foundation.gestures;

import androidx.compose.foundation.ComposeFoundationFlags;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.unit.Velocity;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$LongRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ScrollingLogic$doFlingAnimation$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $available;
    final /* synthetic */ Ref$LongRef $result;
    long J$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ ScrollingLogic this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScrollingLogic$doFlingAnimation$2(ScrollingLogic scrollingLogic, Ref$LongRef ref$LongRef, long j, Continuation continuation) {
        super(2, continuation);
        this.this$0 = scrollingLogic;
        this.$result = ref$LongRef;
        this.$available = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScrollingLogic$doFlingAnimation$2 scrollingLogic$doFlingAnimation$2 = new ScrollingLogic$doFlingAnimation$2(this.this$0, this.$result, this.$available, continuation);
        scrollingLogic$doFlingAnimation$2.L$0 = obj;
        return scrollingLogic$doFlingAnimation$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScrollingLogic$doFlingAnimation$2) create((NestedScrollScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ScrollingLogic scrollingLogic;
        Ref$LongRef ref$LongRef;
        ScrollingLogic scrollingLogic2;
        long j;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final NestedScrollScope nestedScrollScope = (NestedScrollScope) this.L$0;
            final ScrollingLogic scrollingLogic3 = this.this$0;
            ScrollScope scrollScope = new ScrollScope() { // from class: androidx.compose.foundation.gestures.ScrollingLogic$doFlingAnimation$2$reverseScope$1
                @Override // androidx.compose.foundation.gestures.ScrollScope
                public final float scrollBy(float f) {
                    boolean z = ComposeFoundationFlags.NewNestedFlingPropagationEnabled;
                    ScrollingLogic scrollingLogic4 = ScrollingLogic.this;
                    if (z && Math.abs(f) != 0.0f && ((f > 0.0f && !scrollingLogic4.scrollableState.getCanScrollForward()) || ((f < 0.0f && !scrollingLogic4.scrollableState.getCanScrollBackward()) || !((Boolean) scrollingLogic4.isScrollableNodeAttached.invoke()).booleanValue()))) {
                        throw new FlingCancellationException();
                    }
                    long m83reverseIfNeededMKHz9U = scrollingLogic4.m83reverseIfNeededMKHz9U(scrollingLogic4.m85toOffsettuRUvjQ(f));
                    NestedScrollSource.Companion.getClass();
                    int i2 = NestedScrollSource.SideEffect;
                    ScrollingLogic scrollingLogic5 = ((ScrollingLogic$nestedScrollScope$1) nestedScrollScope).this$0;
                    scrollingLogic5.latestScrollSource = i2;
                    OverscrollEffect overscrollEffect = scrollingLogic5.overscrollEffect;
                    return scrollingLogic4.reverseIfNeeded(scrollingLogic4.m84toFloatk4lQ0M((overscrollEffect == null || !(scrollingLogic5.scrollableState.getCanScrollForward() || scrollingLogic5.scrollableState.getCanScrollBackward())) ? ScrollingLogic.m80access$performScroll3eAAhYA(scrollingLogic5, scrollingLogic5.outerStateScope, m83reverseIfNeededMKHz9U, i2) : overscrollEffect.mo20applyToScrollRhakbz0(scrollingLogic5.latestScrollSource, m83reverseIfNeededMKHz9U, scrollingLogic5.performScrollForOverscroll)));
                }
            };
            scrollingLogic = this.this$0;
            ref$LongRef = this.$result;
            long j2 = this.$available;
            FlingBehavior flingBehavior = scrollingLogic.flingBehavior;
            long j3 = ref$LongRef.element;
            float reverseIfNeeded = scrollingLogic.reverseIfNeeded(scrollingLogic.orientation == Orientation.Horizontal ? Velocity.m878getXimpl(j2) : Velocity.m879getYimpl(j2));
            this.L$0 = scrollingLogic;
            this.L$1 = scrollingLogic;
            this.L$2 = ref$LongRef;
            this.J$0 = j3;
            this.label = 1;
            obj = flingBehavior.performFling(scrollScope, reverseIfNeeded, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
            scrollingLogic2 = scrollingLogic;
            j = j3;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            j = this.J$0;
            ref$LongRef = (Ref$LongRef) this.L$2;
            scrollingLogic = (ScrollingLogic) this.L$1;
            scrollingLogic2 = (ScrollingLogic) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        float reverseIfNeeded2 = scrollingLogic2.reverseIfNeeded(((Number) obj).floatValue());
        ref$LongRef.element = scrollingLogic.orientation == Orientation.Horizontal ? Velocity.m877copyOhffZ5M$default(reverseIfNeeded2, 0.0f, j, 2) : Velocity.m877copyOhffZ5M$default(0.0f, reverseIfNeeded2, j, 1);
        return Unit.INSTANCE;
    }
}
