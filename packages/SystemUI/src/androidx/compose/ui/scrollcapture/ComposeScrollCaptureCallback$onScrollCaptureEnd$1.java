package androidx.compose.ui.scrollcapture;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ComposeScrollCaptureCallback$onScrollCaptureEnd$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Runnable $onReady;
    int label;
    final /* synthetic */ ComposeScrollCaptureCallback this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComposeScrollCaptureCallback$onScrollCaptureEnd$1(ComposeScrollCaptureCallback composeScrollCaptureCallback, Runnable runnable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = composeScrollCaptureCallback;
        this.$onReady = runnable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ComposeScrollCaptureCallback$onScrollCaptureEnd$1(this.this$0, this.$onReady, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ComposeScrollCaptureCallback$onScrollCaptureEnd$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            RelativeScroller relativeScroller = this.this$0.scrollTracker;
            this.label = 1;
            Object scrollBy = relativeScroller.scrollBy(0.0f - relativeScroller.scrollAmount, this);
            if (scrollBy != coroutineSingletons) {
                scrollBy = Unit.INSTANCE;
            }
            if (scrollBy == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        ((SnapshotMutableStateImpl) ((ScrollCapture) this.this$0.listener).scrollCaptureInProgress$delegate).setValue(Boolean.FALSE);
        this.$onReady.run();
        return Unit.INSTANCE;
    }
}
