package androidx.compose.foundation.lazy;

import androidx.compose.foundation.gestures.ScrollScope;
import androidx.compose.foundation.lazy.layout.LazyLayoutScrollScopeKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.unit.Density;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyListState$animateScrollToItem$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $index;
    final /* synthetic */ int $scrollOffset;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ LazyListState this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyListState$animateScrollToItem$2(LazyListState lazyListState, int i, int i2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lazyListState;
        this.$index = i;
        this.$scrollOffset = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LazyListState$animateScrollToItem$2 lazyListState$animateScrollToItem$2 = new LazyListState$animateScrollToItem$2(this.this$0, this.$index, this.$scrollOffset, continuation);
        lazyListState$animateScrollToItem$2.L$0 = obj;
        return lazyListState$animateScrollToItem$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LazyListState$animateScrollToItem$2) create((ScrollScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LazyListScrollScopeKt$LazyLayoutScrollScope$1 lazyListScrollScopeKt$LazyLayoutScrollScope$1 = new LazyListScrollScopeKt$LazyLayoutScrollScope$1((ScrollScope) this.L$0, this.this$0);
            int i2 = this.$index;
            int i3 = this.$scrollOffset;
            Density density = ((LazyListMeasureResult) ((SnapshotMutableStateImpl) this.this$0.layoutInfoState).getValue()).density;
            this.label = 1;
            if (LazyLayoutScrollScopeKt.animateScrollToItem(lazyListScrollScopeKt$LazyLayoutScrollScope$1, i2, i3, 100, density, this) == coroutineSingletons) {
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
