package androidx.compose.ui.platform;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class WrappedComposition$setContent$1$1$2$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ WrappedComposition this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WrappedComposition$setContent$1$1$2$1(WrappedComposition wrappedComposition, Continuation continuation) {
        super(2, continuation);
        this.this$0 = wrappedComposition;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WrappedComposition$setContent$1$1$2$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WrappedComposition$setContent$1$1$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AndroidComposeView androidComposeView = this.this$0.owner;
            this.label = 1;
            Object boundsUpdatesEventLoop$ui_release = androidComposeView.contentCaptureManager.boundsUpdatesEventLoop$ui_release(this);
            if (boundsUpdatesEventLoop$ui_release != coroutineSingletons) {
                boundsUpdatesEventLoop$ui_release = Unit.INSTANCE;
            }
            if (boundsUpdatesEventLoop$ui_release == coroutineSingletons) {
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
