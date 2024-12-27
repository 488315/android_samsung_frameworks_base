package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class MediaOutputInteractor$getApplicationLabel$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $packageName;
    int label;
    final /* synthetic */ MediaOutputInteractor this$0;

    public MediaOutputInteractor$getApplicationLabel$2(MediaOutputInteractor mediaOutputInteractor, String str, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaOutputInteractor;
        this.$packageName = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaOutputInteractor$getApplicationLabel$2(this.this$0, this.$packageName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaOutputInteractor$getApplicationLabel$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.this$0.packageManager.getApplicationInfo(this.$packageName, 4194816).loadLabel(this.this$0.packageManager);
    }
}
