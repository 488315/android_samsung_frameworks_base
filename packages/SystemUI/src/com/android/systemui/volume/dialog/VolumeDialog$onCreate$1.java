package com.android.systemui.volume.dialog;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialog$onCreate$1 extends SuspendLambda implements Function3 {
    int label;
    final /* synthetic */ VolumeDialog this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialog$onCreate$1(VolumeDialog volumeDialog, Continuation continuation) {
        super(3, continuation);
        this.this$0 = volumeDialog;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new VolumeDialog$onCreate$1(this.this$0, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            VolumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1 volumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1 = new VolumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1(null, "[Volume]dialog", this.this$0);
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(volumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1, this) == coroutineSingletons) {
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
