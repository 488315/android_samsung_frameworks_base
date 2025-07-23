package com.android.systemui.mediaprojection.data.repository;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaProjectionManagerRepository$stopProjecting$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $stopReason;
    int label;
    final /* synthetic */ MediaProjectionManagerRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaProjectionManagerRepository$stopProjecting$2(MediaProjectionManagerRepository mediaProjectionManagerRepository, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaProjectionManagerRepository;
        this.$stopReason = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaProjectionManagerRepository$stopProjecting$2(this.this$0, this.$stopReason, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaProjectionManagerRepository$stopProjecting$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LogBuffer logBuffer = this.this$0.logger;
        logBuffer.commit(logBuffer.obtain("MediaProjectionMngrRepo", LogLevel.DEBUG, new MediaProjectionManagerRepository$stopProjecting$2$$ExternalSyntheticLambda0(0), null));
        this.this$0.mediaProjectionManager.stopActiveProjection(this.$stopReason);
        return Unit.INSTANCE;
    }
}
