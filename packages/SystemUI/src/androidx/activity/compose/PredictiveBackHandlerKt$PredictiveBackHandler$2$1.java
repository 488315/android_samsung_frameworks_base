package androidx.activity.compose;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class PredictiveBackHandlerKt$PredictiveBackHandler$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ PredictiveBackHandlerCallback $backCallBack;
    final /* synthetic */ boolean $enabled;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PredictiveBackHandlerKt$PredictiveBackHandler$2$1(PredictiveBackHandlerCallback predictiveBackHandlerCallback, boolean z, Continuation continuation) {
        super(2, continuation);
        this.$backCallBack = predictiveBackHandlerCallback;
        this.$enabled = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PredictiveBackHandlerKt$PredictiveBackHandler$2$1(this.$backCallBack, this.$enabled, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PredictiveBackHandlerKt$PredictiveBackHandler$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.FunctionReferenceImpl] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        OnBackInstance onBackInstance;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PredictiveBackHandlerCallback predictiveBackHandlerCallback = this.$backCallBack;
        boolean z = this.$enabled;
        if (!z && !predictiveBackHandlerCallback.isActive && predictiveBackHandlerCallback.isEnabled && (onBackInstance = predictiveBackHandlerCallback.onBackInstance) != null) {
            onBackInstance.cancel();
        }
        predictiveBackHandlerCallback.isEnabled = z;
        ?? r1 = predictiveBackHandlerCallback.enabledChangedCallback;
        if (r1 != 0) {
            r1.invoke();
        }
        return Unit.INSTANCE;
    }
}
