package com.android.systemui.media.mediaoutput.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class MediaDeviceViewModel$1$1$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    int label;

    public MediaDeviceViewModel$1$1$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        MediaDeviceViewModel$1$1$1 mediaDeviceViewModel$1$1$1 = new MediaDeviceViewModel$1$1$1((Continuation) obj3);
        mediaDeviceViewModel$1$1$1.Z$0 = zBooleanValue;
        return mediaDeviceViewModel$1$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0);
    }
}
