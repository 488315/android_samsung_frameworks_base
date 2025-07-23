package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.SuspendAnimationKt;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$FloatRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ScrollableKt$semanticsScrollBy$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $offset;
    final /* synthetic */ Ref$FloatRef $previousValue;
    final /* synthetic */ ScrollingLogic $this_semanticsScrollBy;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScrollableKt$semanticsScrollBy$2(ScrollingLogic scrollingLogic, long j, Ref$FloatRef ref$FloatRef, Continuation continuation) {
        super(2, continuation);
        this.$this_semanticsScrollBy = scrollingLogic;
        this.$offset = j;
        this.$previousValue = ref$FloatRef;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScrollableKt$semanticsScrollBy$2 scrollableKt$semanticsScrollBy$2 = new ScrollableKt$semanticsScrollBy$2(this.$this_semanticsScrollBy, this.$offset, this.$previousValue, continuation);
        scrollableKt$semanticsScrollBy$2.L$0 = obj;
        return scrollableKt$semanticsScrollBy$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScrollableKt$semanticsScrollBy$2) create((NestedScrollScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final NestedScrollScope nestedScrollScope = (NestedScrollScope) this.L$0;
            float m84toFloatk4lQ0M = this.$this_semanticsScrollBy.m84toFloatk4lQ0M(this.$offset);
            final Ref$FloatRef ref$FloatRef = this.$previousValue;
            final ScrollingLogic scrollingLogic = this.$this_semanticsScrollBy;
            Function2 function2 = new Function2() { // from class: androidx.compose.foundation.gestures.ScrollableKt$semanticsScrollBy$2.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    float floatValue = ((Number) obj2).floatValue();
                    ((Number) obj3).floatValue();
                    float f = floatValue - Ref$FloatRef.this.element;
                    ScrollingLogic scrollingLogic2 = scrollingLogic;
                    NestedScrollScope nestedScrollScope2 = nestedScrollScope;
                    long m85toOffsettuRUvjQ = scrollingLogic2.m85toOffsettuRUvjQ(scrollingLogic2.reverseIfNeeded(f));
                    NestedScrollSource.Companion.getClass();
                    int i2 = NestedScrollSource.UserInput;
                    ScrollingLogic scrollingLogic3 = ((ScrollingLogic$nestedScrollScope$1) nestedScrollScope2).this$0;
                    Ref$FloatRef.this.element += scrollingLogic2.reverseIfNeeded(scrollingLogic2.m84toFloatk4lQ0M(ScrollingLogic.m80access$performScroll3eAAhYA(scrollingLogic3, scrollingLogic3.outerStateScope, m85toOffsettuRUvjQ, i2)));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (SuspendAnimationKt.animate$default(m84toFloatk4lQ0M, null, function2, this, 12) == coroutineSingletons) {
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
