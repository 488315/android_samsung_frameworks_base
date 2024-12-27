package com.android.systemui.screenshot.sep;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class SemImageCaptureImpl$semCaptureTask$snapshot$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $taskId;
    int label;
    final /* synthetic */ SemImageCaptureImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SemImageCaptureImpl$semCaptureTask$snapshot$1(SemImageCaptureImpl semImageCaptureImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = semImageCaptureImpl;
        this.$taskId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SemImageCaptureImpl$semCaptureTask$snapshot$1(this.this$0, this.$taskId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SemImageCaptureImpl$semCaptureTask$snapshot$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.this$0.atmService.takeTaskSnapshot(this.$taskId, false);
    }
}
