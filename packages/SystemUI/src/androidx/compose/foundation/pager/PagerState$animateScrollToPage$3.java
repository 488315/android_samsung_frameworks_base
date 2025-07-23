package androidx.compose.foundation.pager;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.SuspendAnimationKt;
import androidx.compose.foundation.gestures.ScrollScope;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$FloatRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PagerState$animateScrollToPage$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ AnimationSpec<Float> $animationSpec;
    final /* synthetic */ int $targetPage;
    final /* synthetic */ float $targetPageOffsetToSnappedPosition;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PagerState this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PagerState$animateScrollToPage$3(PagerState pagerState, int i, float f, AnimationSpec<Float> animationSpec, Continuation continuation) {
        super(2, continuation);
        this.this$0 = pagerState;
        this.$targetPage = i;
        this.$targetPageOffsetToSnappedPosition = f;
        this.$animationSpec = animationSpec;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PagerState$animateScrollToPage$3 pagerState$animateScrollToPage$3 = new PagerState$animateScrollToPage$3(this.this$0, this.$targetPage, this.$targetPageOffsetToSnappedPosition, this.$animationSpec, continuation);
        pagerState$animateScrollToPage$3.L$0 = obj;
        return pagerState$animateScrollToPage$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PagerState$animateScrollToPage$3) create((ScrollScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int i;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final PagerScrollScopeKt$LazyLayoutScrollScope$1 pagerScrollScopeKt$LazyLayoutScrollScope$1 = new PagerScrollScopeKt$LazyLayoutScrollScope$1((ScrollScope) this.L$0, this.this$0);
            int i3 = this.$targetPage;
            float f = this.$targetPageOffsetToSnappedPosition;
            AnimationSpec<Float> animationSpec = this.$animationSpec;
            final PagerState pagerState = this.this$0;
            Function2 function2 = new Function2() { // from class: androidx.compose.foundation.pager.PagerState$animateScrollToPage$3.1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) {
                    int intValue = ((Number) obj4).intValue();
                    PagerState pagerState2 = PagerState.this;
                    ((SnapshotMutableIntStateImpl) pagerState2.programmaticScrollTargetPage$delegate).setIntValue(pagerState2.coerceInPageRange(intValue));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            float f2 = PagerStateKt.DefaultPositionThreshold;
            function2.invoke(pagerScrollScopeKt$LazyLayoutScrollScope$1, new Integer(i3));
            PagerState pagerState2 = pagerScrollScopeKt$LazyLayoutScrollScope$1.$state;
            boolean z = i3 > pagerState2.firstVisiblePage;
            int lastVisibleItemIndex = (pagerScrollScopeKt$LazyLayoutScrollScope$1.getLastVisibleItemIndex() - pagerState2.firstVisiblePage) + 1;
            if (((z && i3 > pagerScrollScopeKt$LazyLayoutScrollScope$1.getLastVisibleItemIndex()) || (!z && i3 < pagerState2.firstVisiblePage)) && Math.abs(i3 - pagerState2.firstVisiblePage) >= 3) {
                if (z) {
                    i = i3 - lastVisibleItemIndex;
                    int i4 = pagerState2.firstVisiblePage;
                    if (i < i4) {
                        i = i4;
                    }
                } else {
                    int i5 = lastVisibleItemIndex + i3;
                    i = pagerState2.firstVisiblePage;
                    if (i5 <= i) {
                        i = i5;
                    }
                }
                pagerScrollScopeKt$LazyLayoutScrollScope$1.snapToItem(i, 0);
            }
            float calculateDistanceTo = pagerScrollScopeKt$LazyLayoutScrollScope$1.calculateDistanceTo(i3) + f;
            final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
            Object animate$default = SuspendAnimationKt.animate$default(calculateDistanceTo, animationSpec, new Function2() { // from class: androidx.compose.foundation.pager.PagerStateKt$animateScrollToPage$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) {
                    float floatValue = ((Number) obj3).floatValue();
                    ((Number) obj4).floatValue();
                    Ref$FloatRef.this.element += pagerScrollScopeKt$LazyLayoutScrollScope$1.scrollBy(floatValue - Ref$FloatRef.this.element);
                    return Unit.INSTANCE;
                }
            }, this, 4);
            if (animate$default != obj2) {
                animate$default = Unit.INSTANCE;
            }
            if (animate$default == obj2) {
                return obj2;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
