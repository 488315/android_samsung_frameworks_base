package androidx.compose.ui.platform;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ChainedPlatformTextInputInterceptor$textInputSession$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $session;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ChainedPlatformTextInputInterceptor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChainedPlatformTextInputInterceptor$textInputSession$2(Function2 function2, ChainedPlatformTextInputInterceptor chainedPlatformTextInputInterceptor, Continuation continuation) {
        super(2, continuation);
        this.$session = function2;
        this.this$0 = chainedPlatformTextInputInterceptor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ChainedPlatformTextInputInterceptor$textInputSession$2 chainedPlatformTextInputInterceptor$textInputSession$2 = new ChainedPlatformTextInputInterceptor$textInputSession$2(this.$session, this.this$0, continuation);
        chainedPlatformTextInputInterceptor$textInputSession$2.L$0 = obj;
        return chainedPlatformTextInputInterceptor$textInputSession$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ChainedPlatformTextInputInterceptor$textInputSession$2) create((PlatformTextInputSessionScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1 chainedPlatformTextInputInterceptor$textInputSession$2$scope$1 = new ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1((PlatformTextInputSessionScope) this.L$0, new AtomicReference(null), this.this$0);
            Function2 function2 = this.$session;
            this.label = 1;
            if (function2.invoke(chainedPlatformTextInputInterceptor$textInputSession$2$scope$1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
