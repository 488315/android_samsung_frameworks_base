package com.android.systemui.media.mediaoutput.controller.media;

import com.android.systemui.media.mediaoutput.controller.media.MediaSessionController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
final class MediaSessionController$ProgressRunner$play$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaSessionController.ProgressRunner this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaSessionController$ProgressRunner$play$2(MediaSessionController.ProgressRunner progressRunner, Continuation continuation) {
        super(2, continuation);
        this.this$0 = progressRunner;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaSessionController$ProgressRunner$play$2 mediaSessionController$ProgressRunner$play$2 = new MediaSessionController$ProgressRunner$play$2(this.this$0, continuation);
        mediaSessionController$ProgressRunner$play$2.L$0 = obj;
        return mediaSessionController$ProgressRunner$play$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaSessionController$ProgressRunner$play$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0052, code lost:
    
        if (r7.mo781invoke(r6) != r0) goto L7;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005c  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0052 -> B:7:0x0013). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineScope coroutineScope;
        CoroutineScope coroutineScope2;
        StandaloneCoroutine standaloneCoroutine;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            coroutineScope = (CoroutineScope) this.L$0;
            if (CoroutineScopeKt.isActive(coroutineScope)) {
            }
            standaloneCoroutine = this.this$0.processingJob;
            if (standaloneCoroutine != null) {
            }
            this.this$0.processingJob = null;
            return Unit.INSTANCE;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            coroutineScope2 = (CoroutineScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            coroutineScope = coroutineScope2;
            if (CoroutineScopeKt.isActive(coroutineScope) || this.this$0.processingJob == null) {
                standaloneCoroutine = this.this$0.processingJob;
                if (standaloneCoroutine != null) {
                    standaloneCoroutine.cancel(null);
                }
                this.this$0.processingJob = null;
                return Unit.INSTANCE;
            }
            this.L$0 = coroutineScope;
            this.label = 1;
            if (DelayKt.delay(100L, this) != coroutineSingletons) {
                coroutineScope2 = coroutineScope;
                Function1 function1 = this.this$0.callback;
                this.L$0 = coroutineScope2;
                this.label = 2;
            }
            return coroutineSingletons;
        }
        coroutineScope2 = (CoroutineScope) this.L$0;
        ResultKt.throwOnFailure(obj);
        Function1 function12 = this.this$0.callback;
        this.L$0 = coroutineScope2;
        this.label = 2;
    }
}
