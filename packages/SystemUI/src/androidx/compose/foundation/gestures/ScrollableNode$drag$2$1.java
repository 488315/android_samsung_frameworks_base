package androidx.compose.foundation.gestures;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.gestures.DragEvent;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
final class ScrollableNode$drag$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $forEachDelta;
    final /* synthetic */ ScrollingLogic $this_with;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScrollableNode$drag$2$1(Function2 function2, ScrollingLogic scrollingLogic, Continuation continuation) {
        super(2, continuation);
        this.$forEachDelta = function2;
        this.$this_with = scrollingLogic;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScrollableNode$drag$2$1 scrollableNode$drag$2$1 = new ScrollableNode$drag$2$1(this.$forEachDelta, this.$this_with, continuation);
        scrollableNode$drag$2$1.L$0 = obj;
        return scrollableNode$drag$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScrollableNode$drag$2$1) create((NestedScrollScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final NestedScrollScope nestedScrollScope = (NestedScrollScope) this.L$0;
            Function2 function2 = this.$forEachDelta;
            final ScrollingLogic scrollingLogic = this.$this_with;
            Function1 function1 = new Function1() { // from class: androidx.compose.foundation.gestures.ScrollableNode$drag$2$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    NestedScrollScope nestedScrollScope2 = nestedScrollScope;
                    long jM396copydBAh8RU$default = Offset.m396copydBAh8RU$default(((DragEvent.DragDelta) obj2).delta, 0.0f, scrollingLogic.orientation == Orientation.Horizontal ? 1 : 2);
                    NestedScrollSource.Companion.getClass();
                    int i2 = NestedScrollSource.UserInput;
                    ScrollingLogic scrollingLogic2 = ((ScrollingLogic$nestedScrollScope$1) nestedScrollScope2).this$0;
                    scrollingLogic2.latestScrollSource = i2;
                    OverscrollEffect overscrollEffect = scrollingLogic2.overscrollEffect;
                    if (overscrollEffect == null || !(scrollingLogic2.scrollableState.getCanScrollForward() || scrollingLogic2.scrollableState.getCanScrollBackward())) {
                        ScrollingLogic.m81access$performScroll3eAAhYA(scrollingLogic2, scrollingLogic2.outerStateScope, jM396copydBAh8RU$default, i2);
                    } else {
                        overscrollEffect.mo20applyToScrollRhakbz0(scrollingLogic2.latestScrollSource, jM396copydBAh8RU$default, scrollingLogic2.performScrollForOverscroll);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (function2.invoke(function1, this) == coroutineSingletons) {
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
